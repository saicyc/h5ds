package com.chinait.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * 用于服务器启动时是否授权
 * @author yachao
 *
 */
public class Protect {
	//台湾
	/*private final static String  IPSTRINGPROTECT = "218.32.56.21";
	private final static String  MACSTRPROTECT = "44 A8 42 16 45 52 ";*/
	private final static String  IPSTRINGPROTECT = "192.168.0.100";
	private final static String  MACSTRPROTECT = "34 68 95 AB DA 85 ";
	//台湾阿里
	/*private final static String  IPSTRINGPROTECT = "10.168.136.224";
	private final static String  MACSTRPROTECT = "00 16 3E 00 18 75 ";*/
	public static boolean getProtect() throws SocketException, UnknownHostException{
		// 获得ip
		NetworkInterface netInterface = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
		// 获得Mac地址的byte数组
		byte[] macAddr = netInterface.getHardwareAddress();
		// 循环输出
		String macStr = "";
		for (byte b : macAddr) {
			// 这里的toHexString()是自己写的格式化输出的方法，见下步。
			macStr += toHexString(b)+" ";
		}
		InetAddress ia = InetAddress.getLocalHost();
		String ip = ia.toString().substring(ia.toString().lastIndexOf("/")+1);
		if(MACSTRPROTECT.equalsIgnoreCase(macStr)&&IPSTRINGPROTECT.equals(ip)){
			return true;
		}
		return false;
	}
	private static String toHexString(int integer) {
		// 将得来的int类型数字转化为十六进制数
		String str = Integer.toHexString((int) (integer & 0xff));
		// 如果遇到单字符，前置0占位补满两格
		if (str.length() == 1) {
			str = "0" + str;
		}
		return str;
	}

}
