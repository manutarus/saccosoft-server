package iso;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.nucleus8583.core.Iso8583Message;
import org.nucleus8583.core.Iso8583MessageFactory;

/**
 * 
 * This class is an input output avenue for the iso request and responses
 * connection to the resource switch ips and port given from the ips class
 * messages formated by the xml file using the US-ASCII charset
 */
public class Response {

	static char r;
	String hold = "";
	String result;
	public static void main(String[] args) {
		packHelper ph = new packHelper();
		SaccoPack sp = new SaccoPack();
		Response rp = new Response();
//		
//		System.out.println("Deposit");
//		System.out.println(rp.dResponse
//		(ph.dwComplete("5000", sp.dLayer("1200","210000","5000",
//		"0527153044",
//		 "ST87YT758HRR", "40002650","0120191096953"), 'B')));
//		
//		System.out.println("wt");
//		System.out.println(rp.wResponse
//				(ph.dwComplete("5000", sp.wLayer("1200","9876543211234567","010000","5000",
//				"0527153044",
//				 "ST87YT758HRR", "40002650","12345","12345"), 'F')));
		 System.out.println("Ministatement");
			System.out.println(rp.miniState(ph.bmComplete(sp.bmLayer("380000",
					"0527153044", "ST87YT758HRN", "40002650", "1234567",
					"1234567"))));
		 
	}

	public String ministatement(String result) {
		String reponse = "";
		Vector<String> minis = new Vector<String>();
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

	public static int nthOccurrence(String str, char c, int n) {
		int pos = str.indexOf(c, 0);
		while (n-- > 0 && pos != -1)
			pos = str.indexOf(c, pos + 1);
		return pos;
	}

	public String dResponse(String iso) {
		String output="";
		BufferedInputStream bis = null;
		try {
			Iso8583MessageFactory factory = new Iso8583MessageFactory(
					Commons.xmlPath);
			Iso8583Message msg = factory.createMessage();
			Socket echoSocket = null;
			PrintWriter out = null;
			BufferedReader in = null;
			try {
				echoSocket = new Socket(Commons.ipSwitch, Commons.portSwitch);
				out = new PrintWriter(echoSocket.getOutputStream(), true);
				in = new BufferedReader(new InputStreamReader(
						echoSocket.getInputStream()));

			} catch (UnknownHostException e) {
				System.err.println("unable to connect to:mimi " + Commons.ipSwitch);
				return "18";
				
			} catch (IOException e) {
				Logger.getLogger(Response.class.getName()).log(Level.SEVERE,
						"unable to connect to:mimi " + Commons.ipSwitch, e);
				return "18";
			}
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(
					System.in));
			out.println(iso);
			bis = new BufferedInputStream(echoSocket.getInputStream());
			InputStreamReader isr = new InputStreamReader(bis, "US-ASCII");
			int i = 0;
			boolean yes = true;
			int c;
			while (((c = isr.read())) != 13) {
				i++;
				if (i == 4 || i == 5 || i == 6 || i == 7) {
					yes = false;

				} else {
					yes = true;
				}
				if (yes) {
					hold += ((char) c);
				}
				byte[] done = hold.getBytes();
				msg.unpack(done);
				if (msg.getString(39) != null) {
					if (msg.getString(39).equals("14")) {
						r = ' ';
						hold = "";
						output = "";
						output = msg.getString(39);
						msg.clear();
						break;
					} else if (msg.getString(39).equals("00")) {
						r = ' ';
						hold = "";
						output = "";
                        output = msg.getString(39);
						msg.clear();
						break;
					}
				}
			}
			out.flush();
			out.close();
			in.close();
			stdIn.close();
			echoSocket.close();
			
		} catch (IOException ex) {
			try {
				bis.close();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			Logger.getLogger(Response.class.getName()).log(Level.SEVERE, null,ex);
			
		} 
		try {
			bis.close();
		} catch (IOException e) {
			
			Logger.getLogger(Response.class.getName()).log(Level.SEVERE,null, e);
		}
		return output;
	}

	public String wResponse(String iso) {
		BufferedInputStream bis = null;
		try {
			Iso8583MessageFactory factory = new Iso8583MessageFactory(
					Commons.xmlPath);
			Iso8583Message msg = factory.createMessage();
			Socket echoSocket = null;
			PrintWriter out = null;
			BufferedReader in = null;
			try {
				echoSocket = new Socket(Commons.ipSwitch, Commons.portSwitch);
				out = new PrintWriter(echoSocket.getOutputStream(), true);
				in = new BufferedReader(new InputStreamReader(
						echoSocket.getInputStream()));

			} catch (UnknownHostException e) {
				System.err.println("unable to connect to:mimi " + Commons.ipSwitch);
				return "18";
				
			} catch (IOException e) {
				Logger.getLogger(Response.class.getName()).log(Level.SEVERE,
						"unable to connect to:mimi " + Commons.ipSwitch, e);
				return "18";
			}
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(
					System.in));
			String userInput = iso;
			out.println(userInput);
			bis = new BufferedInputStream(echoSocket.getInputStream());
			InputStreamReader isr = new InputStreamReader(bis, "US-ASCII");
			int i = 0;
			int subs;
			boolean yes = true;
			int c, breakPoint = 0;
			boolean feild39 = false;
			while (((c = isr.read())) != 13) {
				// System.out.println(i +" count");

				i++;
				if (i == 4 || i == 5 || i == 6 || i == 7) {
					yes = false;

				} else {
					yes = true;
				}
				if (yes) {
					hold += ((char) c);
				}
				byte[] done = hold.getBytes();
				msg.unpack(done);
				if (hold.length() == 4) {
					System.out.println(hold);
					breakPoint = (Integer.parseInt(hold));
				}
				if (hold.length() == breakPoint) {
					msg.clear();
					out.flush();
					out.close();
					in.close();
					stdIn.close();
					echoSocket.close();
					subs = hold.indexOf("+") + 1;

					return hold.substring(subs, subs + 33);

				} else if (msg.getString(39) != null) {
					if (msg.getString(39).equals("14")) {
						msg.clear();
						out.flush();
						out.close();
						in.close();
						stdIn.close();
						echoSocket.close();
						return "14";

					}
					else if (msg.getString(39).equals("16")) {
						msg.clear();
						out.flush();
						out.close();
						in.close();
						stdIn.close();
						echoSocket.close();
						return "16";

					}
				}

			}
		} catch (IOException ex) {
			Logger.getLogger(Response.class.getName()).log(Level.SEVERE, null,
					ex);
		} 
			try {
				bis.close();
			} catch (IOException ex) {
				Logger.getLogger(Response.class.getName()).log(Level.SEVERE,null, ex);
			}
		
		return "14";
	}

	public String balance(String iso) {
		BufferedInputStream bis = null;
		try {
			Iso8583MessageFactory factory = new Iso8583MessageFactory(
					Commons.xmlPath);
			Iso8583Message msg = factory.createMessage();
			Socket echoSocket = null;
			PrintWriter out = null;
			BufferedReader in = null;
			try {
				echoSocket = new Socket(Commons.ipSwitch, Commons.portSwitch);
				out = new PrintWriter(echoSocket.getOutputStream(), true);
				in = new BufferedReader(new InputStreamReader(
						echoSocket.getInputStream()));

			} catch (UnknownHostException e) {
				System.err.println("unable to connect to:mimi " + Commons.ipSwitch);
				return "18";
				
			} catch (IOException e) {
				Logger.getLogger(Response.class.getName()).log(Level.SEVERE,
						"unable to connect to:mimi " + Commons.ipSwitch, e);
				return "18";
			}
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(
					System.in));
			String userInput = iso;
			out.println(userInput);
			bis = new BufferedInputStream(echoSocket.getInputStream());
			InputStreamReader isr = new InputStreamReader(bis, "US-ASCII");
			int i = 0;
			boolean yes = true;
			int c;
			while (((c = isr.read())) != 13) {

				i++;
				if (i == 4 || i == 5 || i == 6 || i == 7) {
					yes = false;

				} else {
					yes = true;
				}
				if (yes) {
					hold += ((char) c);

				}
				if (hold.contains("+")) {
					if (hold.length() == (hold.indexOf('+')) + 34) {
						result = hold.substring(hold.indexOf('+') + 1,
								hold.length());
						msg.clear();
						break;
					}
				}
				byte[] done = hold.getBytes();
				msg.unpack(done);

				if (msg.getString(39) != null) {
					if (msg.getString(39).equals("14")) {
						r = ' ';
						hold = "";
						result = "";
						result = "14";
						msg.clear();
						break;
					}
				}
			}
			out.flush();
			out.close();
			in.close();
			stdIn.close();
			echoSocket.close();
		} catch (IOException ex) {
			Logger.getLogger(Response.class.getName()).log(Level.SEVERE, null,
					ex);
		}
			try {
				bis.close();
			} catch (IOException ex) {
				Logger.getLogger(Response.class.getName()).log(Level.SEVERE,null, ex);
			}
		return balanceFormat(result);
	}

	public String miniState(String iso) {
		BufferedInputStream bis = null;
		try {
			Iso8583MessageFactory factory = new Iso8583MessageFactory(
					Commons.xmlPath);
			Iso8583Message msg = factory.createMessage();
			Socket echoSocket = null;
			PrintWriter out = null;
			BufferedReader in = null;
			int breakPoint = 0;
			try {
				echoSocket = new Socket(Commons.ipSwitch, Commons.portSwitch);
				out = new PrintWriter(echoSocket.getOutputStream(), true);
				in = new BufferedReader(new InputStreamReader(
						echoSocket.getInputStream()));

			} catch (UnknownHostException e) {
				System.err.println("unable to connect to:mimi " + Commons.ipSwitch);
				return "18";
				
			} catch (IOException e) {
				Logger.getLogger(Response.class.getName()).log(Level.SEVERE,
						"unable to connect to:mimi " + Commons.ipSwitch, e);
				return "18";
			}
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(
					System.in));
			String userInput = iso;
			out.println(userInput);
			bis = new BufferedInputStream(echoSocket.getInputStream());
			InputStreamReader isr = new InputStreamReader(bis, "US-ASCII");
			int i = 0;
			boolean yes = true;
			boolean feild39 = false;
			int c;
			while (((c = isr.read())) != 13) {

				i++;
				if (i == 4 || i == 5 || i == 6 || i == 7) {
					yes = false;

				} else {
					yes = true;
				}
				if (yes) {
					hold += ((char) c);
				}

				if (hold.length() == 4) {
					breakPoint = (Integer.parseInt(hold));
				}
				if (i == breakPoint) {
					break;
				}
				byte[] done = hold.getBytes();
				if (feild39 == false) {
					msg.unpack(done);
				}
				if (msg.getString(39) != null) {
					feild39 = true;
					if (msg.getString(39).equals("14")) {
						msg.clear();
						out.flush();
						out.close();
						in.close();
						stdIn.close();
						echoSocket.close();
						return "14";

					}
				}
			}
			out.flush();
			out.close();
			in.close();
			stdIn.close();
			echoSocket.close();
		} catch (IOException ex) {
			Logger.getLogger(Response.class.getName()).log(Level.SEVERE, null,
					ex);
		}
			try {
				bis.close();
			} catch (IOException ex) {
				Logger.getLogger(Response.class.getName()).log(Level.SEVERE,
						null, ex);
			}
		return hold.substring((hold.indexOf("/") - 2), hold.length());
	}

	public String loginResponse(String iso) {
		String server = "";
		String lenght = "";
		BufferedInputStream bis = null;
		try {
			Iso8583MessageFactory factory = new Iso8583MessageFactory(
					"web/iso8583.xml");
			Iso8583Message msg = factory.createMessage();
			Socket echoSocket = null;
			PrintWriter out = null;
			BufferedReader in = null;
			try {
				echoSocket = new Socket(Commons.ipSwitch, Commons.portSwitch);

				out = new PrintWriter(echoSocket.getOutputStream(), true);
				in = new BufferedReader(new InputStreamReader(
						echoSocket.getInputStream()));

			} catch (UnknownHostException e) {
				System.err.println("unable to connect to:mimi " + Commons.ipSwitch);
				return "18";
				
			} catch (IOException e) {
				Logger.getLogger(Response.class.getName()).log(Level.SEVERE,
						"unable to connect to:mimi " + Commons.ipSwitch, e);
				return "18";
			}
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(
					System.in));
			String userInput = iso;
			out.println(userInput);
			bis = new BufferedInputStream(echoSocket.getInputStream());
			InputStreamReader isr = new InputStreamReader(bis, "US-ASCII");
			int i = 0;
			boolean cont = true;
			boolean yes = true;
			int c;
			int counter = 0;
			server = "";
			hold = "";
			while (((c = isr.read())) != 13 && cont == true) {

				if (i == 4 || i == 5 || i == 6 || i == 7) {
					yes = false;

				} else {
					yes = true;
				}
				if (yes) {
					hold += ((char) c);
					if (i == 0 || i == 1 || i == 2 || i == 3) {
						lenght += ((char) c);
					}
				}

				if (i > 3) {
					if (i == Integer.parseInt(lenght) + 3) {

						for (int so = (hold.length() - 17); so < (hold.length() - 15); so++) {
							server += hold.charAt(so);
						}
						break;
											}
				}

				counter++;
				r = ' ';
				result = "";
				i++;
				msg.clear();
				counter = 0;
				
			}
			out.flush();
			out.close();
			in.close();
			stdIn.close();
			echoSocket.close();
			System.out.println("done");
		} catch (IOException ex) {
			Logger.getLogger(Response.class.getName()).log(Level.SEVERE, null,
					ex);
		}
			try {
				bis.close();
			} catch (IOException ex) {
				Logger.getLogger(Response.class.getName()).log(Level.SEVERE,
						null, ex);
		}
		return (server);
	}

	public String balanceFormat(String values) {
		if(values.equals("14")){
			return "14";
		}
		else if(values.equals("16")){
			return "16";
		}
		else if(values.equals("18")){
			return "18";
		}
		else{
		String actual = values.substring(0, values.indexOf("+"));
		String avail = values.substring(values.indexOf("+") + 1,
				values.length());
		NumberFormat nf = NumberFormat.getCurrencyInstance().getInstance();
		actual = nf.format(new BigDecimal(actual.substring(0,
				actual.length() - 2)))
				+ "."
				+ actual.substring(actual.length() - 2, actual.length());
		avail = nf
				.format(new BigDecimal(avail.substring(0, avail.length() - 2)))
				+ "." + avail.substring(avail.length() - 2, avail.length());

		return actual + "+" + avail;
		}

	}

	public String fdate(String dateIn) {
		Date date = Calendar.getInstance().getTime();
		DateFormat formatter = new SimpleDateFormat("dd/MM/yy");
		String today = formatter.format(date);
		return today;
	}

}