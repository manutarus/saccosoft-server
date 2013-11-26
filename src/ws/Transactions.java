package ws;

import iso.Commons;
import iso.Response;
import iso.SaccoPack;
import iso.packHelper;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Vector;

public class Transactions {

	packHelper ph = new packHelper();
	SaccoPack sp = new SaccoPack();
	Response rp = new Response();
	Commons com = new Commons();
	
	
			
			public static void main(String[] args) {

				Transactions tr = new Transactions();
				System.out.println("Withdraw");
//				System.out.println(tr.withdraw("12345", "12345", "500",
//						"2edfu58y4m2"));
				
				System.out.println(tr.deposit("12345", "500", "2edfu58y4m2"));
	}

	public String deposit(String account, String amount, String refNo) {

		String result = rp.dResponse(ph.dwComplete(amount, sp.dLayer("1200",
				"210000", amount, com.dateFull(), refNo, "40002650", account),
				'B'));
		if (result.equals("18")) {
			return result;
		}

		else {
			return result
					+ "+"
					+ java.text.DateFormat.getDateTimeInstance().format(
							Calendar.getInstance().getTime());
		}
	}

	public String withdraw(String id, String account, String amount,
			String refNo) {

		String result = rp
				.balanceFormat((rp.wResponse(ph.dwComplete(amount, sp.wLayer(
						"1200", "9876543211234567", "010000", amount,
						com.dateFull(), refNo, "40002650", id, account), 'F'))));
		if (result.equals("14") || result.equals("16")|| result.equals("18")) {
			return result;
		} else {
			return result
					+ "x"
					+ java.text.DateFormat.getDateTimeInstance().format(
							Calendar.getInstance().getTime());
		}
	}

	public String ministatement(String id, String account, String refNo) {
		String reponse = "";
		Vector<String> minis = new Vector<String>();
		String result = rp.miniState(ph.bmComplete(sp.bmLayer("380000",
				com.dateFull(), refNo, "40002650", id, account)));

		minis = new Vector<String>(Arrays.asList(result.split("(?<=\\G.{72})")));
		Iterator itr = minis.iterator();
		String hold = null;
		while (itr.hasNext()) {
			hold = ((String) itr.next()).replaceAll("\\s+", "*");
			reponse += (hold.substring(0, hold.length() - 1)) + "x";
		}
		reponse += (java.text.DateFormat.getDateTimeInstance().format(Calendar
				.getInstance().getTime()));
		return reponse;

	}

	public String balance(String id, String account, String refNo) {

		return rp.balance(ph.bmComplete(sp.bmLayer("310000", com.dateFull(),
				refNo, "40002650", id, account)))
				+ "x"
				+ java.text.DateFormat.getDateTimeInstance().format(
						Calendar.getInstance().getTime());
	}

	public String login(String username, String password) {

		return (rp.dResponse((sp.MakeIsoLogin("710000", username, password,
				"40002650"))));
	}

}
