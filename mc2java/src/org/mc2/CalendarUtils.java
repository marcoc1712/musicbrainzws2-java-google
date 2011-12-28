package org.mc2;

import java.util.Calendar;
import java.util.TimeZone;

/**
 *
 * @author marco
 */
public class CalendarUtils {


    public static String calcDuration(Long durms){
        
        if (durms ==null) return "";

        Calendar cal = Calendar.getInstance();
        TimeZone tx = TimeZone.getDefault();
        int offset = tx.getOffset(durms);
        cal.setTimeInMillis(durms-offset);

        String dur;
       
        if (durms>3600000) //1 h.
        {
             dur = String.format("%1$tH:%1$tM:%1$tS", cal);
        }
        else
        {
            dur = String.format("%1$tM:%1$tS", cal);
        }

        return dur;
    }
}
