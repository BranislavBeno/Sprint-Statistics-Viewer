package com.sprint.utils;

import org.apache.commons.math3.stat.regression.SimpleRegression;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collector;

public class Utils {

  private Utils() {
    throw new IllegalStateException("Utility class");
  }

  public static LocalDateTime convertTimeStampToLocalDateTime(final Timestamp ts) {
    return ts.toLocalDateTime();
  }

  public static String convertTimeStampToString(LocalDateTime timeStamp) {
    return timeStamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
  }

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
    var simpleRegression = new SimpleRegression(true);

    // Passing data to the model
    var data = new double[list.size()][2];
    var idx = 0;
    for (int val : list) {
      data[idx][0] = idx;
      data[idx++][1] = val;
    }
    simpleRegression.addData(data);

    // Making and saving prediction
    for (var i = 0; i < list.size(); i++) {
      var f = new DecimalFormat("###.00");
      String predict = f.format(simpleRegression.predict(i));
      prediction.add(predict);
    }

    return prediction;
  }
}
