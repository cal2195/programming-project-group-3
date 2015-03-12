package programmingproject;



/**
 *
 * @author Dan
 */
public class DateTime
{

	public static int SECONDS_PER_DAY = 86400;
	public static int SECONDS_PER_HOUR = 3600;
	public static int[] DAYS_IN_MONTH = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

	public static final long[] SECONDS_TILL_MONTH_STARTS = {0,2678400,5097600,7776000, 10368000, 
		13046400, 15638400, 18316800, 20995200, 23587200, 26265600, 28857600};

	public static void main(String[] args) {
		/*
		String[] tests = {"2013-04-20 01:41:51", "2013-07-30 13:01:00", "2013-12-31 23:59:59",
				"2013-01-16 21:31:00", "2013-05-01 16:51:55", "2013-09-30 11:34:52",
				"2013-02-28 12:11:12",  "2013-06-05 20:12:12", "2013-10-02 10:32:07",
				"2013-03-31 00:00:00", "2013-07-15 01:42:53", "2013-11-23 00:11:01",
				"2013-04-01 15:59:42", "2013-08-26 22:56:18", "2013-12-31 09:50:30"
				};
		for(int i = 0; i < tests.length; i++)
		{
			String test = tests[i];
			System.out.println(test);
			long dumb = dateTimeToSecs(test);
			System.out.println(dumb);
			String backToString = secsToDateTime(dumb);
			System.out.println(backToString);
			System.out.println(" ");
		}
		String backToString = secsToDateTime(86400);
		System.out.println(backToString);
        */        
		
		int year, months, days, hours, minutes, seconds;
		year = 2013;
		months = 1;
		days = 1;
		hours = 0;
		minutes = 0;
		seconds = 0;
		
		while(months < 13){
			seconds++;
			if(seconds == 60)
			{
				seconds = 0;
				minutes++;
				if(minutes == 60)
				{
					minutes = 0;
					hours++;
					if(hours == 24)
					{
						hours = 0;
						days++;
						if(days > DAYS_IN_MONTH[months - 1])
						{
							/*
							System.out.println("" + year + "-" + String.format("%02d", months) + "-" 
					+ String.format("%02d", days) + " " + String.format("%02d", hours) 
					+ ":" + String.format("%02d", minutes) + ":" + String.format("%02d", seconds));
							*/
							days = 1;
							months++;
							/*
							System.out.println("" + year + "-" + String.format("%02d", months) + "-" 
					+ String.format("%02d", days) + " " + String.format("%02d", hours) 
					+ ":" + String.format("%02d", minutes) + ":" + String.format("%02d", seconds));
							System.out.println("");
							*/
						}
					}
				}
			}
			String date = "" + year + "-" + String.format("%02d", months) + "-" 
					+ String.format("%02d", days) + " " + String.format("%02d", hours) 
					+ ":" + String.format("%02d", minutes) + ":" + String.format("%02d", seconds);
			//System.out.println(date);
			long tempSecs = DateTime.dateTimeToSecs(date);
			String calculatedDate = DateTime.secsToDateTime(tempSecs);
			if(!date.equals(calculatedDate))
			{
				//prints any dates that differ between "real" dates and calculated datetime
				System.out.println(date);
				System.out.println(calculatedDate);
				System.out.println("");
			}
			
		}
				

		System.out.println("Fini!");
		
	}


	public static long dateTimeToSecs(String dateTime){

		String[] dateTimeArr = dateTime.split(" ");  		
		String[] date = dateTimeArr[0].split("-");
		String[] time = dateTimeArr[1].split(":");

		int months = Integer.parseInt(date[1]);
		int days =  Integer.parseInt(date[2]);
		int hours =  Integer.parseInt(time[0]);
		int minutes =  Integer.parseInt(time[1]);
		int seconds =  Integer.parseInt(time[2]);

		long secondsTillMonth = SECONDS_TILL_MONTH_STARTS[months - 1] ;
		long secondsOfDays =  (days-1)*SECONDS_PER_DAY;
		long secondsSince2013 = secondsTillMonth + secondsOfDays + hours*SECONDS_PER_HOUR + minutes*60 + seconds;

		return secondsSince2013;
	}

	public static String secsToDateTime(long secs){

		String dateTime = "2013-";
		//finds month
		int i = 0;
		while(i < 12 && SECONDS_TILL_MONTH_STARTS[i] <= secs)
		{
		//	long secondsSoFar = SECONDS_TILL_MONTH_STARTS[i];
			i++;
		}
		dateTime += String.format("%02d", i);
		dateTime += "-";
		secs -= SECONDS_TILL_MONTH_STARTS[i - 1];

		//finds day
		i = 0;
		while(i*SECONDS_PER_DAY <=  secs)
		{
			i++;
		}
		secs -= SECONDS_PER_DAY*(i-1);
		dateTime += String.format("%02d", i);
		dateTime += " ";

		//finds hours
		i = 0;
		while(i*SECONDS_PER_HOUR <=  secs)
		{
			i++;
		}
		secs -= SECONDS_PER_HOUR*(i-1);
		dateTime += String.format("%02d", i-1);
		dateTime += ":";

		//finds minutes
		i = 0;
		while(i*60 <=  secs)
		{
			i++;
		}
		secs -= 60*(i-1);
		dateTime += String.format("%02d", i-1);
		dateTime += ":";

		dateTime += String.format("%02d", secs);

		return dateTime;
	}

}
