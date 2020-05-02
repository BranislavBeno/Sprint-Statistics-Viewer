package com.sprint.utils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Calendar;

/**
 * The Class Utils.
 */
public class Utils {

	/**
	 * Utility classes should not have public constructors.
	 */
	private Utils() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Convert time stamp to local date time.
	 *
	 * @param ts the ts
	 * @return the local date time
	 */
	public static LocalDateTime convertTimeStampToLocalDateTime(final Timestamp ts) {
		// Get local time zone id
		ZoneId zone = ZoneId.of(Calendar.getInstance().getTimeZone().getID());
		// Get time offset according to local time zone
		ZoneOffset zoneOffSet = zone.getRules().getOffset(LocalDateTime.now());
		// Get offset in hours
		int hourOffset = zoneOffSet.getTotalSeconds() / 3600;

		return ts.toLocalDateTime().minusHours(hourOffset);
	}
}
