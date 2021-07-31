package id.pahlevikun.cookingtimer.common.helper

import android.content.Context
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.LocalDate
import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat
import java.text.SimpleDateFormat
import java.util.*


object DateTimeHelper {

    private val indonesiaLocale = Locale("in")
    private val usLocale = Locale("us")

    private const val FULL_DATE_TIME_FORMAT_PATTERN = "EEEE, dd MMMM yyyy, h:mm a"
    private val fullDateTimeFormatter = DateTimeFormat
        .forPattern(FULL_DATE_TIME_FORMAT_PATTERN)
        .withLocale(indonesiaLocale)

    private const val DATE_TIME_FORMAT_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"
    private val dateTimeFormatter = DateTimeFormat
        .forPattern(DATE_TIME_FORMAT_PATTERN)
        .withLocale(usLocale)

    private const val SHORT_TRANSACTION_TIME_FORMAT_PATTERN = "HH:mm"
    private val shortTransactionTimeFormatter = DateTimeFormat
        .forPattern(SHORT_TRANSACTION_TIME_FORMAT_PATTERN)
        .withLocale(indonesiaLocale)

    private const val DATE_TIME_FULL_FORMAT_PATTERN = "dd MMMM yyyy HH:mm"
    private val dateTimeFullFormatter = DateTimeFormat
        .forPattern(DATE_TIME_FULL_FORMAT_PATTERN)
        .withZone(DateTimeZone.forID("Asia/Jakarta"))
        .withLocale(usLocale)

    private const val DATE_TIME_FORMAT_PATTERN_DEFAULT = "yyyy-MM-dd HH:mm:ss"
    private val dateTimeFormatterDefault = DateTimeFormat
        .forPattern(DATE_TIME_FORMAT_PATTERN_DEFAULT)
        .withZone(DateTimeZone.forID("Asia/Jakarta"))
        .withLocale(indonesiaLocale)

    private const val LOCAL_DATE_FORMAT_PATTERN = "yyyy-MM-dd"
    private val localDateFormatter = DateTimeFormat
        .forPattern(LOCAL_DATE_FORMAT_PATTERN)
        .withLocale(indonesiaLocale)

    fun getDateFromIso8601TimeDefault(iso8601time: String): DateTime =
        dateTimeFormatterDefault.parseDateTime(iso8601time)

    fun getLongTime(iso8601time: String): Long {
        val time = getDateFromIso8601TimeDefault(iso8601time).millis
        return time
    }

    fun parseDateTime(iso8601time: String): String {
        val time = getDateFromIso8601TimeDefault(iso8601time).millis
        return fullDateTimeFormatter.print(time)
    }

    fun getFullDate(
        context: Context,
        iso8601time: String
    ): String {
        return dateTimeFullFormatter
            .apply { withLocale(LocalizationHelper.getLocale(context)) }
            .print(getDateFromIso8601Time(context, iso8601time))
    }

    fun getFormattedFullDefaultTime(iso8601time: String): String {
        if (iso8601time.isEmpty()) {
            return ""
        }
        return fullDateTimeFormatter.print(getDateFromIso8601TimeDefault(iso8601time))
    }

    fun getDateFromIso8601Time(context: Context, iso8601time: String): DateTime =
        dateTimeFormatter
            .apply { withLocale(LocalizationHelper.getLocale(context)) }
            .parseDateTime(iso8601time)

    fun getCurrentDateInIso8601String(): String = dateTimeFormatter.print(DateTime())

    fun isToday(context: Context, dateIso8601: String): Boolean {
        if (dateIso8601.isBlank()) {
            return false
        }
        return getDateFromIso8601Time(context, dateIso8601).toLocalDate() == LocalDate()
    }

    fun isCurrentHour(context: Context, dateIso8601: String): Boolean {
        if (dateIso8601.isBlank()) {
            return false
        }
        val currentTime = LocalDateTime()
        val time = getDateFromIso8601Time(context, dateIso8601)
        if (time.toLocalDate() == LocalDate()) {
            if (time.hourOfDay == currentTime.hourOfDay) {
                return true
            }
        }
        return false
    }

    fun isCurrentMinute(context: Context, dateIso8601: String): Boolean {
        if (dateIso8601.isBlank()) {
            return false
        }
        val currentTime = LocalDateTime()
        val time = getDateFromIso8601Time(context, dateIso8601)
        return time.minuteOfHour == currentTime.minuteOfHour
    }

    fun isCurrentSecond(context: Context, dateIso8601: String): Boolean {
        if (dateIso8601.isBlank()) {
            return false
        }
        val currentTime = LocalDateTime()
        val time = getDateFromIso8601Time(context, dateIso8601)
        return time.secondOfMinute == currentTime.secondOfMinute
    }

    fun isYesterday(context: Context, dateIso8601: String): Boolean {
        if (dateIso8601.isBlank()) {
            return false
        }
        return getDateFromIso8601Time(context, dateIso8601).toLocalDate() == LocalDate.now()
            .minusDays(1)
    }

    fun getIso8601String(dateTime: DateTime): String = dateTimeFormatter.print(dateTime)

    fun getFullDateTimeFormattedDate(context: Context, dateIso8601: String): String {
        if (dateIso8601.isEmpty()) {
            return ""
        }
        val time = getDateFromIso8601Time(context, dateIso8601).millis
        return fullDateTimeFormatter.print(time)
    }

    fun getFormattedTime(timestamp: Long): String =
        shortTransactionTimeFormatter.print(timestamp)

    fun getYesterdayAsIso8601(): String {
        val calendar = Calendar.getInstance()
        val todayDate = calendar.get(Calendar.DATE)
        calendar.set(Calendar.DATE, todayDate - 1)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        calendar.set(Calendar.HOUR_OF_DAY, 23)

        val dateTime = calendar.timeInMillis
        return dateTimeFormatter.print(dateTime)
    }

    fun getTodayAsIso8601(): String {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        calendar.set(Calendar.HOUR_OF_DAY, 23)

        val dateTime = calendar.timeInMillis
        return dateTimeFormatter.print(dateTime)
    }

    fun getDefaultTodayTime(): String {
        val current = LocalDateTime.now()
        return dateTimeFormatterDefault.print(current)
    }

    fun getCurrentTimeStamp(): String {
        return (System.currentTimeMillis() / 1000).toString()
    }

    /**
     * Format date object to another format.
     *
     * @param date the date, want to be formatted
     * @param dateFormat the format date pattern
     * @param timeZone timezone to use
     * @return formatted date
     */
    fun formatDate(
        date: Date,
        dateFormat: String = LOCAL_DATE_FORMAT_PATTERN,
        timeZone: TimeZone = TimeZone.getDefault()
    ): String {
        val formattedDate = SimpleDateFormat(dateFormat, Locale.getDefault())
        formattedDate.timeZone = timeZone
        return formattedDate.format(date)
    }

    /**
     * This method is specified for E-Dokterku BE
     * E-Dokterku BE didn't returning is8601 date
     *
     * @param date is yyyy-MM-dd HH-mm-ss
     */
    fun getDateTime(date: String, format: String = "yyyy-MM-dd HH-mm-ss"): DateTime {
        if (date.isBlank()) return DateTime()
        val formatter = DateTimeFormat.forPattern(format)
        return formatter.parseDateTime(date)
    }

    fun getCurrentHour(): Int {
        val calendar = Calendar.getInstance()
        return calendar.get(Calendar.HOUR_OF_DAY)
    }
}