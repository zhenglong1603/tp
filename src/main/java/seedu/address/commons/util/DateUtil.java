package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

/**
 * A centralized class for Date-Time related functions
 */
public class DateUtil {
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-uuuu HH:mm")
            .withResolverStyle(ResolverStyle.STRICT);
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-uuuu")
            .withResolverStyle(ResolverStyle.STRICT);
    public static final DateTimeFormatter DATE_DISPLAY_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-uuuu")
            .withResolverStyle(ResolverStyle.STRICT);
    public static final DateTimeFormatter DATE_TIME_DISPLAY_FORMATTER =
            DateTimeFormatter.ofPattern("dd-MM-uuuu, hh:mm a");
    public static final String INVALID_DATETIME_MESSAGE = "Sorry! Please use the format "
            + "dd-MM-yyyy HH:mm and also check if it is a valid date-time.";
    public static final String INVALID_DATE_MESSAGE = "Sorry! Please use the format"
            + "dd-MM-yyyy and also check if it is a valid date.";

    /**
     * Convert date into a more presentable format
     *
     * @param  date
     * @return String containing date in "dd MMMM yyyy" format
     */
    public static String getDisplayableDate(LocalDate date) {
        requireNonNull(date);
        return date.format(DATE_DISPLAY_FORMATTER);
    }

    /**
     * Convert dateTime into a more presentable format
     *
     * @param dateTime
     * @return String containing dateTime in "dd MMMM yyyy, hh:mm a" format
     */
    public static String getDisplayableDateTime(LocalDateTime dateTime) {
        requireNonNull(dateTime);
        return dateTime.format(DATE_TIME_DISPLAY_FORMATTER);
    }

    public static DateTimeFormatter getDateTimeFormatter() {
        return DATE_TIME_FORMATTER;
    }

    public static DateTimeFormatter getDateFormatter() {
        return DATE_FORMATTER;
    }

    public static DateTimeFormatter getDisplayDateFormatter() {
        return DATE_DISPLAY_FORMATTER;
    }

    /**
     * Check if date is current date
     */
    public static boolean isToday(LocalDate date) {
        return date != null && date.equals(LocalDate.now());
    }
}

