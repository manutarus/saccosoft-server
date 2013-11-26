/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iso;

import java.util.Vector;

/**
 * 
 * @author manu
 */
public class packHelper {
	public String dwComplete(String filed4, String text, char replacer) {
		Vector<Integer> startPoint = new Vector<Integer>();
		String result = "";

		char c = ' ';
		int i;
		for (i = 0; i < text.length(); i++) {
			c = text.charAt(i);
			if (i == 4) {
				c = replacer;
			} else if (c == ' ') {
				startPoint.addElement(i);
				c = '0';
			}
			result += c;
		}
		if (i < 10) {
			result = "000" + i + result;
		} else if (i < 100) {
			result = "00" + i + result;
		} else if (i < 1000) {
			result = "0" + i + result;
		} else {
			result = i + result;
			i = 0;
		}

		StringBuilder sb = new StringBuilder(result);
		result = ""
				+ sb.replace(((startPoint.elementAt(0) - filed4.length()) + 3),
						((startPoint.elementAt(0) + startPoint.size()) + 3),
						"0" + trimAmout(filed4) + "0");

		return result;
	}

	/**
	 * 
	 * ensure amount is 12 digits filling the rest by zeros
	 * 
	 * @param amount
	 * @return
	 */
	public String trimAmout(String amount) {

		int length = (12 - (amount.length() + 2));
		if (amount.length() < amount.length() + 1) {
			amount = makeZeros(length) + amount;
		}
		return amount;
	}

	public String makeZeros(int No) {
		String zerro = "";
		for (int i = 1; i <= No; i++) {

			zerro += "0";
		}
		return zerro;
	}

	public String bmComplete(String iso) {

		int length = iso.length();
		if (length < 10) {
			return "000" + length + iso;
		}
		if (length < 100) {
			return "00" + length + iso;
		}
		if (length < 1000) {
			return "0" + length + iso;
		} else {
			return length + iso;
		}
	}
}
