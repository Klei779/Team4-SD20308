package util;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    // 📅 Hôm nay
    public static Date today() {
        return new Date();
    }

    // 📅 Đầu ngày
    public static Date startOfDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);

        return cal.getTime();
    }

    // 📅 Cuối ngày
    public static Date endOfDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);

        return cal.getTime();
    }

    // 📅 Tuần này
    public static Date[] getWeekRange() {
        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        Date start = startOfDay(cal.getTime());

        cal.add(Calendar.DAY_OF_WEEK, 6);
        Date end = endOfDay(cal.getTime());

        return new Date[]{start, end};
    }

    // 📅 Tháng này
    public static Date[] getMonthRange() {
        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date start = startOfDay(cal.getTime());

        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date end = endOfDay(cal.getTime());

        return new Date[]{start, end};
    }
}