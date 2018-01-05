package com.iqcloud.common.distributedlock;

import java.util.Date;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iqcloud.common.dto.IQCloudInfoDto;
import com.iqcloud.redis.client.SingleJredisPool;

/*
 * 同步锁测试Demo
 */

@Controller
@RequestMapping(value = "/distributedLockController")
public class DistributedLockController {

	/*
	 * 登录
	 */
	@RequestMapping(value = "/login")
	public @ResponseBody IQCloudInfoDto login(@RequestBody IQCloudInfoDto iqCloudInfoDto, HttpSession session) {
		IQCloudInfoDto iqCloudInfoDto2 = new IQCloudInfoDto();
		iqCloudInfoDto2.setJsonBody("OK");

		final String jsonStr = JSON.toJSONString(iqCloudInfoDto);

		/*
		 * for (int i = 0; i < 25; i++) { try { System.out.println(jsonStr +
		 * "---> " + i); Thread.sleep(1000); } catch (InterruptedException e) {
		 * // TODO Auto-generated catch block e.printStackTrace(); } }
		 * 
		 * synchronized (value) { for (int i = 0; i < 25; i++) { try {
		 * System.out.println(jsonStr + "---> " + i); Thread.sleep(1000); }
		 * catch (InterruptedException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } } }
		 */

		Action action = new Action() {

			@Override
			public void action() {
				// TODO Auto-generated method stub
				for (int i = 0; i < 25; i++) {
					try {
						System.out.println(jsonStr + "---> " + i);
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};

		JSONObject jsonHead = JSON.parseObject(JSON.toJSONString(iqCloudInfoDto.getJsonHead()));
		String key = jsonHead.getString("accessToken");

		DistributedLock distributedLock = new DistributedLock(action, false, "127.0.0.1:2181", key, "TEST");

		// DistributedLock distributedLock = new DistributedLock(action, true,
		// "127.0.0.1:2181", "TEST_ACTION_A", "TEST");
		// DistributedLock distributedLock = new DistributedLock(action, false,
		// "127.0.0.1:2181", "TEST_ACTION_A", "TEST");
		distributedLock.getLock(true);

		return iqCloudInfoDto2;
	}

	/*
	 * 登录
	 */
	@RequestMapping(value = "/login1")
	public @ResponseBody IQCloudInfoDto login1(@RequestBody IQCloudInfoDto iqCloudInfoDto, HttpSession session) {
		IQCloudInfoDto iqCloudInfoDto2 = new IQCloudInfoDto();
		iqCloudInfoDto2.setJsonBody("OK");

		final String jsonStr = JSON.toJSONString(iqCloudInfoDto);

		for (int i = 0; i < 25; i++) {
			try {
				System.out.println(jsonStr + "---> " + i);
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		/*
		 * synchronized (value) { for (int i = 0; i < 10; i++) { try {
		 * System.out.println(jsonStr + "---> " + i); Thread.sleep(1000); }
		 * catch (InterruptedException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } } }
		 * 
		 * Action action = new Action() {
		 * 
		 * @Override public void action() { // TODO Auto-generated method stub
		 * for (int i = 0; i < 25; i++) { try { System.out.println(jsonStr +
		 * "---> " + i); Thread.sleep(1000); } catch (InterruptedException e) {
		 * // TODO Auto-generated catch block e.printStackTrace(); } } } };
		 * 
		 * // DistributedLock distributedLock = new DistributedLock(action,
		 * true, "127.0.0.1:2181", "TEST_ACTION_B", "TEST"); DistributedLock
		 * distributedLock = new DistributedLock(action, false,
		 * "127.0.0.1:2181", "TEST_ACTION_B", "TEST");
		 * distributedLock.getLock();
		 */

		return iqCloudInfoDto2;
	}

	/*
	 * 基于redis锁
	 */
	@RequestMapping(value = "/redisKeyLock")
	public @ResponseBody IQCloudInfoDto redisKeyLock() {
		IQCloudInfoDto iqCloudInfoDto = new IQCloudInfoDto();
		iqCloudInfoDto.setJsonBody("OK");

		Date dateNow = new Date();

		RedisKeyLock redisKeyLock = RedisKeyLock.getInstance(SingleJredisPool.jredisPoolINS.getJedisPool());
		try {
			if (redisKeyLock.lock("redisKeyLock")) {
				for (int i = 0; i < 25; i++) {
					try {
						System.out.println(i + " 当前时间===> " + dateNow.toString());
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		} finally {
			// TODO: handle finally clause
			redisKeyLock.unlock("redisKeyLock");
		}

		return iqCloudInfoDto;
	}
}
