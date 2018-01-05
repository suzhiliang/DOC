package com.iqcloud.common.distributedlock;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class DistributedLock implements Watcher {

	private ZooKeeper zk = null;

	/*
	 * 锁的根目录
	 */
	private final String LOCK_ROOT_PATH = "/LOCKS_ROOT";

	/*
	 * zookeeper连接地址
	 */
	private String zookeeperConnection;

	private String selfPath;
	private String waitPath;
	private String parentPath;
	private String subPath;
	private boolean isDistributed;
	private boolean canRun = false;
	private Action action = null;

	public DistributedLock(Action theAction, boolean theDistributed, String theZookeeperConnection, String parentNode,
			String theSubNode) {
		action = theAction;
		isDistributed = theDistributed;
		zookeeperConnection = theZookeeperConnection;
		try {
			zk = new ZooKeeper(zookeeperConnection, 6000, this);
		} catch (IOException e) {
			e.printStackTrace();
		}

		parentPath = LOCK_ROOT_PATH + "/" + parentNode;
		subPath = parentPath + "/" + theSubNode;
	}

	/**
	 * 获取锁
	 * 
	 * @Title: getLock
	 * @Description:
	 * @param exitWhenGetNotLock:
	 *            如果获取不到锁，是否直接退出
	 * @return
	 * @return: boolean
	 */
	public boolean getLock(final boolean exitWhenGetNotLock) {
		// 创建相关节点
		try {
			if (null == zk.exists(LOCK_ROOT_PATH, false)) {
				zk.create(LOCK_ROOT_PATH, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			}

			if (null == zk.exists(parentPath, false)) {
				zk.create(parentPath, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			}

			if (null == zk.exists(subPath, false)) {
				selfPath = zk.create(subPath, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
			}

			System.out.println("创建锁路径:" + selfPath);

			if (isDistributed) {// 同步执行
				boolean bok = checkMinPath();
				if (bok) {
					canRun = true;
				} else {
					if (exitWhenGetNotLock) {// 获取不到锁，直接不执行，return
						return false;
					}
				}
				while (true) {
					if (canRun) {
						boolean bok1 = checkMinPath();
						if (bok1) {// 获取所成功
							getLockSuccess();
							return bok;
						}
						break;
					} else {
						System.out.println("同步等待...");
						Thread.sleep(1000);
					}
				}
				return true;
			} else {// 异步执行
				boolean bok = checkMinPath();
				if (!bok) {
					if (exitWhenGetNotLock) {// 获取不到锁，直接不执行，return
						return false;
					}
				}

				// 开个线程跑
				Thread theThread = new Thread() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							boolean bok = checkMinPath();
							if (bok) {
								canRun = true;
							} else {
								if (exitWhenGetNotLock) {// 获取不到锁，直接不执行，return
									return;
								}
							}

							while (true) {
								if (canRun) {
									boolean bok1 = checkMinPath();
									if (bok1) {// 获取所成功
										getLockSuccess();
									}
									break;
								} else {
									System.out.println("同步等待...");
									Thread.sleep(1000);
								}
							}
						} catch (KeeperException e) {
							e.printStackTrace();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				};

				theThread.start();
				return true;
			}
		} catch (KeeperException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return false;
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return false;
		}
	}

	/**
	 * 获取锁成功
	 */
	public void getLockSuccess() throws KeeperException, InterruptedException {
		if (zk.exists(this.selfPath, false) == null) {
			System.out.println("本节点已不在了...");
			releaseConnection();
			return;
		}

		System.out.println("获取锁成功，抓紧处理业务！");
		try {
			if (null != action) {
				action.action();
			}
		} finally {
			System.out.println("删除本节点：" + selfPath);
			zk.delete(this.selfPath, -1);
			releaseConnection();
		}
	}

	/**
	 * 关闭ZK连接
	 */
	public void releaseConnection() {
		if (this.zk != null) {
			try {
				this.zk.close();
			} catch (InterruptedException e) {
			}
		}
		System.out.println("释放连接");
	}

	/**
	 * 检查自己是不是最小的节点
	 * 
	 * @return
	 */
	public boolean checkMinPath() throws KeeperException, InterruptedException {
		System.out.println("checkMinPath");
		List<String> subNodes = zk.getChildren(parentPath, false);
		Collections.sort(subNodes);
		int index = subNodes.indexOf(selfPath.substring(parentPath.length() + 1));
		switch (index) {
		case -1: {
			System.out.println("本节点已不在了..." + selfPath);
			return false;
		}
		case 0: {
			System.out.println("子节点中，我已经是老大" + selfPath);
			return true;
		}
		default: {
			this.waitPath = parentPath + "/" + subNodes.get(index - 1);
			System.out.println("获取子节点中，排在我前面的" + waitPath);
			try {
				zk.getData(waitPath, true, new Stat());
				return false;
			} catch (KeeperException e) {
				if (zk.exists(waitPath, false) == null) {
					System.out.println("子节点中，排在我前面的" + waitPath + "已失踪，幸福来得太突然?");
					return checkMinPath();
				} else {
					throw e;
				}
			}
		}
		}
	}

	/*
	 * 判断是否可以干活
	 */
	public boolean getCanDoing() {
		try {
			return checkMinPath();
		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public void process(WatchedEvent event) {
		if (event == null) {
			return;
		}

		Event.KeeperState keeperState = event.getState();
		Event.EventType eventType = event.getType();
		if (Event.KeeperState.SyncConnected == keeperState) {
			if (Event.EventType.None == eventType) {
				System.out.println("成功连接上ZK服务器");
			} else if (event.getType() == Event.EventType.NodeDeleted && event.getPath().equals(waitPath)) {
				System.out.println("收到情报，排我前面的家伙已挂，我是不是可以出山了？");
				if (isDistributed) {// 同步执行
					canRun = true;
				} else {
					try {
						if (checkMinPath()) {
							canRun = true;
						}
					} catch (KeeperException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		} else if (Event.KeeperState.Disconnected == keeperState) {
			System.out.println("与ZK服务器断开连接");
		} else if (Event.KeeperState.AuthFailed == keeperState) {
			System.out.println("权限检查失败");
		} else if (Event.KeeperState.Expired == keeperState) {
			System.out.println("会话失效");
		}
	}
}
