package com.iqcloud.redis.client;

import com.iqcloud.common.util.FileControl;

public class RedisConfigInfo
{
	private Integer maxTotal;
	private Integer maxIdle;
	private Integer minIdle;
	private Integer timeBetweenEvictionRunsMillis;
	private Integer maxWaitMillis;
	private String host;
	private Integer port;
	private String masterName;
	private String slaveInfo;

	/*public RedisConfigInfo(){
		Properties p = new Properties();
		InputStream is = RedisConfigInfo.class.getResourceAsStream(this.fileName);
		try {
			p.load(is);
			String maxTotals = p.getProperty("redis.maxTotle");
			if (maxTotals != null)
				this.maxTotal = Integer.valueOf(Integer.parseInt(maxTotals));
			else {
				this.maxTotal = null;
			}
			
			String minIdles = p.getProperty("redis.minIdle");
			if (minIdles != null)
				this.minIdle = Integer.valueOf(Integer.parseInt(minIdles));
			else {
				this.minIdle = null;
			}
			
			String maxIdles = p.getProperty("redis.maxIdle");
			if (this.maxIdle != null)
				this.maxIdle = Integer.valueOf(Integer.parseInt(maxIdles));
			else {
				this.maxIdle = null;
			}
      
			String timeBetweenEvictionRunsMilliss = p.getProperty("redis.timeBetweenEvictionRunsMillis");
			if (timeBetweenEvictionRunsMilliss != null)
				this.timeBetweenEvictionRunsMillis = Integer.valueOf(Integer.parseInt(timeBetweenEvictionRunsMilliss));
			else {
				this.timeBetweenEvictionRunsMillis = null;
			}
			
			String maxWaitMilliss = p.getProperty("redis.maxWaitMilliss");
			if (maxWaitMilliss != null)
				this.maxWaitMillis = Integer.valueOf(Integer.parseInt(maxWaitMilliss));
			else {
				this.maxWaitMillis = null;
			}
			
			String masterName = p.getProperty("redis.masterName");
			if (masterName != null)
				this.masterName = masterName;
			else {
				this.masterName = null;
			}
			
			String slaveInfo = p.getProperty("redis.slaveInfo");
			if (slaveInfo != null)
				this.slaveInfo = slaveInfo;
			else {
				this.slaveInfo = null;
			}
			
			String ports = p.getProperty("redis.port");
			if (ports != null)
				this.port = Integer.valueOf(Integer.parseInt(ports));
			else {
				this.port = null;
			}
			String hosts = p.getProperty("redis.host");
			this.host = hosts;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}*/
	
	public RedisConfigInfo(){

		String maxTotals = FileControl.getPath("redis", "maxTotle");
		if (maxTotals != null)
			this.maxTotal = Integer.valueOf(Integer.parseInt(maxTotals));
		else {
			this.maxTotal = null;
		}
		
		String minIdles = FileControl.getPath("redis", "minIdle");
		if (minIdles != null)
			this.minIdle = Integer.valueOf(Integer.parseInt(minIdles));
		else {
			this.minIdle = null;
		}
		
		String maxIdles = FileControl.getPath("redis", "maxIdle");
		if (this.maxIdle != null)
			this.maxIdle = Integer.valueOf(Integer.parseInt(maxIdles));
		else {
			this.maxIdle = null;
		}
  
		String timeBetweenEvictionRunsMilliss = FileControl.getPath("redis", "timeBetweenEvictionRunsMillis");
		if (timeBetweenEvictionRunsMilliss != null)
			this.timeBetweenEvictionRunsMillis = Integer.valueOf(Integer.parseInt(timeBetweenEvictionRunsMilliss));
		else {
			this.timeBetweenEvictionRunsMillis = null;
		}
		
		String maxWaitMilliss = FileControl.getPath("redis", "maxWaitMilliss");
		if (maxWaitMilliss != null)
			this.maxWaitMillis = Integer.valueOf(Integer.parseInt(maxWaitMilliss));
		else {
			this.maxWaitMillis = null;
		}
		
		String masterName = FileControl.getPath("redis", "masterName");
		if (masterName != null)
			this.masterName = masterName;
		else {
			this.masterName = null;
		}
		
		String slaveInfo = FileControl.getPath("redis", "slaveInfo");
		if (slaveInfo != null)
			this.slaveInfo = slaveInfo;
		else {
			this.slaveInfo = null;
		}
		
		String ports = FileControl.getPath("redis", "port");
		if (ports != null)
			this.port = Integer.valueOf(Integer.parseInt(ports));
		else {
			this.port = null;
		}
		String hosts = FileControl.getPath("redis", "host");
		this.host = hosts;
		
	}

	public Integer getMaxTotal() {
		return this.maxTotal;
	}

	public void setMaxTotal(Integer maxTotal) {
		this.maxTotal = maxTotal;
	}

	public Integer getMaxIdle() {
		return this.maxIdle;
	}

	public void setMaxIdle(Integer maxIdle) {
		this.maxIdle = maxIdle;
	}

	public Integer getMinIdle() {
		return this.minIdle;
	}

	public void setMinIdle(Integer minIdle) {
		this.minIdle = minIdle;
	}

	public Integer getTimeBetweenEvictionRunsMillis() {
		return this.timeBetweenEvictionRunsMillis;
	}

	public void setTimeBetweenEvictionRunsMillis(Integer timeBetweenEvictionRunsMillis){
		this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
	}

	public Integer getMaxWaitMillis() {
		return this.maxWaitMillis;
	}

	public void setMaxWaitMillis(Integer maxWaitMillis) {
		this.maxWaitMillis = maxWaitMillis;
	}

	public String getHost() {
		return this.host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getPort() {
		return this.port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getMasterName() {
		return this.masterName;
	}

	public void setMasterName(String masterName) {
		this.masterName = masterName;
	}

	public String getSlaveInfo() {
		return this.slaveInfo;
	}

	public void setSlaveInfo(String slaveInfo) {
		this.slaveInfo = slaveInfo;
	}
}
