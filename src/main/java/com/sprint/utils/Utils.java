package com.sprint.utils;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collector;

import org.apache.commons.math3.stat.regression.SimpleRegression;

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

	/**
	 * Gather last update time stamp.
	 *
	 * @param timeStamp the time stamp
	 * @return the string
	 */
	public static String convertTimeStampToString(LocalDateTime timeStamp) {
		return timeStamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
	}

	/**
	 * Last N.
	 *
	 * @param <T> the generic type
	 * @param n   the n
	 * @return the collector
	 */
	public static <T> Collector<T, ?, List<T>> lastN(int n) {
		return Collector.<T, Deque<T>, List<T>>of(ArrayDeque::new, (acc, t) -> {
			if (acc.size() == n)
				acc.pollFirst();
			acc.add(t);
		}, (acc1, acc2) -> {
			while (acc2.size() < n && !acc1.isEmpty()) {
				acc2.addFirst(acc1.pollLast());
			}
			return acc2;
		}, ArrayList::new);
	}

	public static List<String> computeLinearTrend(List<Integer> list) {
		// Initialize output list
		List<String> prediction = new ArrayList<>();

		// Creating regression object, passing true to have intercept term
		SimpleRegression simpleRegression = new SimpleRegression(true);

		// Passing data to the model
		double[][] data = new double[list.size()][2];
		int idx = 0;
		for (int val : list) {
			data[idx][0] = idx;
			data[idx++][1] = val;
		}
		simpleRegression.addData(data);

		// Making and saving prediction
		for (int i = 0; i < list.size(); i++) {
			DecimalFormat f = new DecimalFormat("###.00");
			String predict = f.format(simpleRegression.predict(i));
			prediction.add(predict);
		}

		return prediction;
	}
}
