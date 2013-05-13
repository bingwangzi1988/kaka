package org.eredlab.g4.ccl.json;

public class TestJson {

	public static void main(String[] args) {
		String string = "[{\"xmid\":\"1000143024\",\"sfdlbm\":\"01\",\"xmmc\":\"ÑÎËáÍ·æßËûÃÀõ¥Æ¬1\"},{\"xmid\":\"1000143023\",\"sfdlbm\":\"01\",\"xmmc\":\"ÑÎËáÍ·æßËûÃÀõ¥Æ¬(±¡Ä¤ÒÂ)3\"}]";
		string = string.substring(1, string.length() - 1);
		System.out.println(string);
		String[] strings = string.split("},");
		for (int i = 0; i < strings.length; i++) {
			if (i != strings.length - 1) {
				strings[i] += "}";
			}
			System.out.println(strings[i]);
		}
	}

}
