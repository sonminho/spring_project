package multi.campus.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DateCalculator {
	public String getYesterday() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date today = new Date();
		
		Calendar cal = new GregorianCalendar(Locale.KOREA);
		cal.setTime(today);
		cal.add(Calendar.DATE, -1);
		
		return sdf.format(cal.getTime());
	}
}
