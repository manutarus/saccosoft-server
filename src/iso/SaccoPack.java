/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iso;

import org.nucleus8583.core.Iso8583Message;
import org.nucleus8583.core.Iso8583MessageFactory;

/**
 *
 * @author manu
 */
public class SaccoPack {
	

    public String wLayer(String mti, String field2, String field3,
            String field4, String field7, String field37, String field41, String field52, String field102) {
        Iso8583MessageFactory factory = new Iso8583MessageFactory(Commons.xmlPath);
        Iso8583Message msg = factory.createMessage();
        msg.setMti(mti);
        msg.set(2, field2);
        msg.set(3, field3);
        msg.set(4, field4);
        msg.set(7, field7);
        msg.set(37, field37);
        msg.set(41, field41);
        msg.set(52, field52);
        msg.set(102, field102);
        byte[] packed = msg.pack();
        msg.clear();
        return new String(packed);
    }
    
    public String dLayer(String mti,String field3,
            String field4, String field7, String field37, String field41, String field102) {
        Iso8583MessageFactory factory = new Iso8583MessageFactory(Commons.xmlPath);
        Iso8583Message msg = factory.createMessage();
        msg.setMti(mti);
        msg.set(3, field3);
        msg.set(4, field4);
        msg.set(7, field7);
        msg.set(37, field37);
        msg.set(41, field41);
        msg.set(102, field102);
        byte[] packed = msg.pack();
        msg.clear();
        return new String(packed);
    }
    
     
    public String bmLayer(String field3,
            String field7, String field37, String field41,String field52, String field102) {
        Iso8583MessageFactory factory = new Iso8583MessageFactory(Commons.xmlPath);
        Iso8583Message msg = factory.createMessage();
        msg.setMti("1200");
        msg.set(3, field3);
        msg.set(7, field7);
        msg.set(37, field37);
        msg.set(41, field41);
        msg.set(52, field52);
        msg.set(102, field102);
        byte[] packed = msg.pack();
        msg.clear();
        return new String(packed);
    }
    
    public String MakeIsoLogin(String field3,
            String field33, String field34, String field41) {
        String iso;
        String finalIso = "";
       /// replacer = 'F';
        Iso8583MessageFactory factory = new Iso8583MessageFactory(Commons.xmlPath);
        Iso8583Message msg = factory.createMessage();
        msg.setMti("1200");
        msg.set(3, field3);
        msg.set(33, field33);
        msg.set(34, field34);
        msg.set(41, field41);

        byte[] packed = msg.pack();

        msg.clear();
        //return new String(packed);
        iso = new String(packed);

        iso.length();
        if (iso.length() < 9) {
            iso = "000" + iso.length() + iso;
        } else if (iso.length() < 99) {
            iso = "00" + iso.length() + iso;
        } else if (iso.length() < 999) {
            iso = "0" + iso.length() + iso;
        }
        for (int i = 0; i < iso.length(); i++) {
            if (i == 8) {
                finalIso += '2';
            } else {
                finalIso += iso.charAt(i);
            }
        }

        return finalIso;

    }
  
}
