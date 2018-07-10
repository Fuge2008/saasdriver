package com.small.saasdriver.utils;

import java.io.Closeable;
import java.io.IOException;

/** 关闭流 */
public class IOUtils {
	public static boolean close(Closeable io) {
		if (io != null) {
			try {
				io.close();
			} catch (IOException e) {
				LogUtil.e(e+"");
			}
		}
		return true;
	}
}
