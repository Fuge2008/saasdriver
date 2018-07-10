package com.small.saasdriver.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * md5加密
 */
public class Encry_MD5 {

	/**
	 * MD5加密
	 * 
	 * @param message
	 *            要进行MD5加密的字符串
	 * @return 加密结果为32位字符串
	 * @throws UnsupportedEncodingException
	 *             不支持编码异常
	 * @throws NoSuchAlgorithmException
	 */
	public static String getMD5(String message)
			throws UnsupportedEncodingException, NoSuchAlgorithmException {

		MessageDigest messageDigest = null;
		StringBuffer md5StrBuff = new StringBuffer();
		messageDigest = MessageDigest.getInstance("MD5");
		messageDigest.reset();
		messageDigest.update(message.getBytes("UTF-8"));

		byte[] byteArray = messageDigest.digest();
		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(
						Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}
		return md5StrBuff.toString().toUpperCase();// 字母大写
	}

	/**
	 * MD5加密
	 * 
	 * @param message
	 *            要加密的内容
	 * @param charsetname
	 *            编码通畅直接输入("utf-8","gbk")
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 *             不支持编码异常
	 */
	public static String getMD5(String message, String charsetname)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		String resultString = null;
		resultString = new String(message);
		MessageDigest md = MessageDigest.getInstance("MD5");
		if (charsetname == null || "".equals(charsetname)) {
			resultString = byteArrayToHexString(md.digest(resultString
					.getBytes()));
		} else {
			resultString = byteArrayToHexString(md.digest(resultString
					.getBytes(charsetname)));
		}

		return resultString;
	}

	/** 支持md5加密的方法 */
	private static String byteArrayToHexString(byte b[]) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++)
			resultSb.append(byteToHexString(b[i]));

		return resultSb.toString();
	}

	/** 支持md5加密的方法 */
	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n += 256;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	/** 支持md5加密的数组 */
	private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
	
	

}
