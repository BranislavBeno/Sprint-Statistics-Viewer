package com.sprint.utils;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * The Class UtilsTest.
 */
class UtilsTest {

  /**
   * Test private constructors for code coverage.
   */
  @Test
  void testPrivateConstructorsForCodeCoverage() {
    Class<Utils> clazz = Utils.class;
    Constructor<?>[] constructors = clazz.getDeclaredConstructors();
    for (Constructor<?> constructor : constructors) {
      constructor.setAccessible(true);
      assertThrows(InvocationTargetException.class, constructor::newInstance);
    }
  }
}
