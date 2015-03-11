package programmingproject;

/**
 *
 * @author Dan
 */
public class DateTime
{

    public static int SECONDS_PER_DAY = 86400;
    public static int SECONDS_PER_HOUR = 3600;
    public static final long[] SECONDS_TILL_MONTH_STARTS =
    {
        0, 2678400, 5097600, 7776000, 10368000,
        13046400, 15638400, 18316800, 20995200, 23587200, 26265600, 28857600
    };

    public static void main(String[] args)
    {

        String[] tests =
        {
            "2013-04-20 01:41:51", "2013-07-30 13:01:00", "2013-12-31 23:59:59"
        };
        for (int i = 0; i < tests.length; i++)
        {
            String test = tests[i];
            System.out.println(test);
            long dumb = dateTimeToSecs(test);
            System.out.println(dumb);
            String backToString = secsToDateTime(dumb);
            System.out.println(backToString);
            System.out.println(" ");
        }
    }

    public static long dateTimeToSecs(String dateTime)
    {
        long totalSeconds = 0;

        String[] dateTimeArr = dateTime.split(" ");
        String[] date = dateTimeArr[0].split("-");
        String[] time = dateTimeArr[1].split(":");

        int months = Integer.parseInt(date[1]);
        int days = Integer.parseInt(date[2]);
        int hours = Integer.parseInt(time[0]);
        int minutes = Integer.parseInt(time[1]);
        int seconds = Integer.parseInt(time[2]);

        long secondsSince2013 = SECONDS_TILL_MONTH_STARTS[months - 1] + days * SECONDS_PER_DAY + hours * SECONDS_PER_HOUR + minutes * 60 + seconds;

        return secondsSince2013;
    }

    public static String secsToDateTime(long secs)
    {

        String dateTime = "2013-";
        //finds month
        int i = 0;
        while (i < 12 && SECONDS_TILL_MONTH_STARTS[i] < secs)
        {
            i++;
        }
        dateTime += String.format("%02d", i);
        dateTime += "-";
        secs -= SECONDS_TILL_MONTH_STARTS[i - 1];

        //finds day
        i = 0;
        while (i * SECONDS_PER_DAY < secs)
        {
            i++;
        }
        secs -= SECONDS_PER_DAY * (i - 1);
        dateTime += String.format("%02d", i - 1);
        dateTime += " ";

        //finds hours
        i = 0;
        while (i * SECONDS_PER_HOUR < secs)
        {
            i++;
        }
        secs -= SECONDS_PER_HOUR * (i - 1);
        dateTime += String.format("%02d", i - 1);
        dateTime += ":";

        //finds minutes
        i = 0;
        while (i * 60 <= secs)
        {
            i++;
        }
        secs -= 60 * (i - 1);
        dateTime += String.format("%02d", i - 1);
        dateTime += ":";

        dateTime += String.format("%02d", secs);

        return dateTime;
    }

}
