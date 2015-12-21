package org.andengine.util.time;

/**
 * (c) 2010 Nicolas Gramlich
 * (c) 2011 Zynga Inc.
 *
 * @author Nicolas Gramlich
 * @since 16:49:25 - 26.07.2010
 */
public interface TimeConstants {
    // ===========================================================
    // Constants
    // ===========================================================

    int MONTHS_PER_YEAR = 12;

    int DAYS_PER_WEEK = 7;

    int DAYS_PER_MONTH = 30;

    int HOURS_PER_DAY = 24;

    int MINUTES_PER_HOUR = 60;

    int MILLISECONDS_PER_SECOND = 1000;
    int MICROSECONDS_PER_SECOND = 1000 * 1000;
    long NANOSECONDS_PER_SECOND = 1000 * 1000 * 1000;

    long MICROSECONDS_PER_MILLISECOND = MICROSECONDS_PER_SECOND / MILLISECONDS_PER_SECOND;

    long NANOSECONDS_PER_MICROSECOND = NANOSECONDS_PER_SECOND / MICROSECONDS_PER_SECOND;
    long NANOSECONDS_PER_MILLISECOND = NANOSECONDS_PER_SECOND / MILLISECONDS_PER_SECOND;

    float SECONDS_PER_NANOSECOND = 1f / NANOSECONDS_PER_SECOND;
    float MICROSECONDS_PER_NANOSECOND = 1f / NANOSECONDS_PER_MICROSECOND;
    float MILLISECONDS_PER_NANOSECOND = 1f / NANOSECONDS_PER_MILLISECOND;

    float SECONDS_PER_MICROSECOND = 1f / MICROSECONDS_PER_SECOND;
    float MILLISECONDS_PER_MICROSECOND = 1f / MICROSECONDS_PER_MILLISECOND;

    float SECONDS_PER_MILLISECOND = 1f / MILLISECONDS_PER_SECOND;

    int SECONDS_PER_MINUTE = 60;
    int SECONDS_PER_HOUR = SECONDS_PER_MINUTE * MINUTES_PER_HOUR;
    int SECONDS_PER_DAY = SECONDS_PER_HOUR * HOURS_PER_DAY;
    int SECONDS_PER_WEEK = SECONDS_PER_DAY * DAYS_PER_WEEK;
    int SECONDS_PER_MONTH = SECONDS_PER_DAY * DAYS_PER_MONTH;
    int SECONDS_PER_YEAR = SECONDS_PER_MONTH * MONTHS_PER_YEAR;

    // ===========================================================
    // Methods
    // ===========================================================
}
