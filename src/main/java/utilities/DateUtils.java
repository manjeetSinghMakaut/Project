package utilities;

import java.util.Date;

public class DateUtils {
	
	// Gets current date and time as a string
	// Replaces colons with underscores so it can be used in file names
	public static String getTimeStamp() {
		Date date = new Date();
		return date.toString().replaceFirst(":", "_").replaceAll(":", "_");
	}

}
