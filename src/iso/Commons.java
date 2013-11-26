
package iso;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * ips and the ports for resources connections
 */
public class Commons {
   //public static String ipSwitch ="localhost";
   public static String ipSwitch ="41.215.23.242";
    public static int portSwitch =8650;
    public static String xmlPath ="WebContent/xml/iso8583.xml";
    
    SimpleDateFormat sdf2, sdf3, sdf4, sdf5, sdf6;
    public String date() {
        Date date3 = new Date();
        sdf2 = new SimpleDateFormat("MM");
        sdf3 = new SimpleDateFormat("dd");
        sdf4 = new SimpleDateFormat("kk");
        sdf5 = new SimpleDateFormat("mm");
        sdf6 = new SimpleDateFormat("ss");
        return (sdf2.format(date3)) + (sdf3.format(date3))+ (sdf4.format(date3))
                + (sdf5.format(date3))+ (sdf6.format(date3));

    }
    
     public String dateFull() {
        Date date3 = new Date();
        sdf2 = new SimpleDateFormat("MM");
        sdf3 = new SimpleDateFormat("dd");
        sdf4 = new SimpleDateFormat("kk");
        sdf5 = new SimpleDateFormat("mm");
        sdf6 = new SimpleDateFormat("ss");
        return (sdf2.format(date3)) + (sdf3.format(date3))+ (sdf4.format(date3))
                + (sdf5.format(date3))+ (sdf6.format(date3));

    }
}
