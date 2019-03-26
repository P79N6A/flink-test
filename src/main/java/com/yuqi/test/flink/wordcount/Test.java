package com.yuqi.test.flink.wordcount;/*
 * Author: park.yq@alibaba-inc.com
 * Date: 2018/12/17 下午4:08
 */

import java.net.InetSocketAddress;
import java.net.Socket;

public class Test {
	public static void main(String[] args) {

		try (Socket socket = new Socket()) {
			socket.connect(new InetSocketAddress("127.0.0.1", 9999), 0);
			System.out.println(socket.getInetAddress());
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println();
	}
}
