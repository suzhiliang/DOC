package com.iqcloud.common.util;

import java.util.UUID;

public class UUIDUtils
{
	public static final String getUUID(){
		UUID uuid = UUID.randomUUID();
		String sUuid = uuid.toString();
		String sNewUuid = sUuid.replaceAll("-", "");
		sNewUuid = sNewUuid.toUpperCase();
		return sNewUuid;
	}
}