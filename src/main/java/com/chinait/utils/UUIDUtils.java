package com.chinait.utils;

import java.util.UUID;

public class UUIDUtils {
	public static String getUUIDString(){
		return UUID.randomUUID().toString().replace("-", "").toUpperCase();
	}
}
