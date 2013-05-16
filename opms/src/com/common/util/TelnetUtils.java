package com.common.util;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.net.telnet.TelnetClient;

public class TelnetUtils {

	TelnetClient client = new TelnetClient("VT100");
	StringBuffer buffer = new StringBuffer();
	InputStream inputStream = null; 
	OutputStream outputStream = null;

	private static List<String> defaultPromt = new ArrayList<String>();
	private static List<String> user = new ArrayList<String>();
	private static List<String> pass = new ArrayList<String>();

	static {
		defaultPromt.add("#");
		defaultPromt.add(">");
		defaultPromt.add("y)");
		user.add("login:");
		pass.add("password:");
	}

	public String execCmd(String cmd) throws Exception {
		sendCommand(cmd);
		String rspMsg = getResult();
		client.disconnect();
		return rspMsg;
	}

	public static void main(String[] args) throws Exception {
		TelnetUtils a = new TelnetUtils("127.0.0.1", 60000, null, null);

		Scanner s = null;
		s = new Scanner(System.in);
		String str = "start";
		System.out.print("osgi>");
		while (!str.equals("disconnect")) {
			str = s.nextLine();
			a.sendCommand(str);
			System.out.print(a.getResult());
		}
		s.close();

		a.close();
	}

	public TelnetUtils(String hostname, int port, String username,
			String password) throws Exception {
		conn(hostname, port);
		this.inputStream = this.client.getInputStream();
		this.outputStream = this.client.getOutputStream();
		if (username != null) {
			login(username, password);
		}
	}

	/**
	 * 关闭连接
	 * 
	 * @throws Exception
	 */
	public void close() throws Exception {
		this.client.disconnect();
	}

	private void conn(String hostname, int port) throws Exception {
		this.client.connect(hostname, port);
	}

	private void login(String username, String password) throws Exception {
		sendCommand(username, user);
		List<String> temp = new ArrayList<String>();
		temp.add(":");
		String result = getResult(temp);

		if (!(result.trim().endsWith("word:"))) {
			throw new Exception("Invalid user:" + username);
		}
		temp.add("#");
		temp.add(">");
		sendCommand(password, pass);
		result = getResult(temp);

		if ((result.trim().endsWith("word:"))
				|| (result.trim().endsWith("ogin:"))) {
			throw new Exception("Invalid username or password:" + username
					+ " " + password);
		}
	}

	public void sendCommand(String command) throws Exception {
		sendCommand(command, defaultPromt);
	}

	public String getResult() throws Exception {
		return getResult(defaultPromt);
	}

	public void sendCommand(String command, List<String> wantedEndString)
			throws Exception {
		waitForString(wantedEndString);
		this.buffer.delete(0, this.buffer.length());
		this.outputStream.write((command + "\n").getBytes());
		this.outputStream.flush();
	}

	public String getResult(List<String> endString) throws Exception {
		waitForString(endString);
		return this.buffer.toString();
	}

	private void waitForString(List<String> wantedEndString) throws Exception {
		int aword = 0;
		boolean matchOne = false;
		while (!(matchOne)) {
			for (int i = 0; i < wantedEndString.size(); ++i) {
				if ((this.buffer.toString().trim()
						.endsWith((String) wantedEndString.get(i)))
						&& (this.inputStream.available() == 0)) {
					matchOne = true;
				}
			}
			if (matchOne) {
				return;
			}
			aword = this.inputStream.read();
			if (aword < 0) {
				throw new Exception(this.buffer.toString());
			}
			this.buffer.append((char) aword);
		}
	}

	public boolean isClosed() {
		return (!(this.client.isConnected()));
	}

}
