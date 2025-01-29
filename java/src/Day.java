public class Day implements Cloneable, Comparable<Day>{
	
	private int year;
	private int month;
	private int day;
	
	//Constructor
	public Day(int y, int m, int d) {
		this.year=y;
		this.month=m;
		this.day=d;		
	}

	public void set(String sDay)
    {
        String[] sDayParts = sDay.split("-");

        this.day = Integer.parseInt(sDayParts[0]) ;
        this.year = Integer.parseInt(sDayParts[2]) ;
        this.month = rtrnMonthNameIndex(sDayParts[1]); 
    }

    public Day(String sDay) {set(sDay);}

    private static String MonthNames = "JanFebMarAprMayJunJulAugSepOctNovDec";
	public static int rtrnMonthNameIndex(String month){
		return MonthNames.indexOf(month)/3+1;
	}

	// check if a given year is a leap year
	static public boolean isLeapYear(int y)
	{
		if (y%400==0)
			return true;
		else if (y%100==0)
			return false;
		else if (y%4==0)
			return true;
		else
			return false;
	}
	
	// check if y,m,d valid
	static public boolean valid(int y, int m, int d)
	{
		if (m<1 || m>12 || d<1) return false;
		switch(m){
			case 1: case 3: case 5: case 7:
			case 8: case 10: case 12:
					 return d<=31; 
			case 4: case 6: case 9: case 11:
					 return d<=30; 
			case 2:
					 if (isLeapYear(y))
						 return d<=29; 
					 else
						 return d<=28; 
		}
		return false;
	}

	static public boolean str_valid(String date) {
		if (date == null || date.isEmpty()) return false;
	
		String[] parts = date.split("-");
		if (parts.length != 3) return false;
	
		try {
			int day = Integer.parseInt(parts[0]);
			String monthStr = parts[1];
			int year = Integer.parseInt(parts[2]);
	
			// Get month number from the month string
			int index = MonthNames.indexOf(monthStr);
			if (index == -1 || monthStr.length() != 3) return false; // Invalid month
			int month = (index / 3) + 1; // Convert to 1-based month number
	
			// Use the `valid` function to check the date validity
			return valid(year, month, day);
		} catch (NumberFormatException e) {
			return false; // Non-numeric day or year
		}
	}

	// public boolean isBefore(Day date){
		
    //     if (this.year < date.year) {return true;}
    //     if (this.year > date.year) {return false;}

    //     if (this.month < date.month) {return true;}
    //     if (this.month > date.month) {return false;}

	// 	return this.day < date.day;
	// }

	private static int concat_day(Day day){
		String y=Integer.toString(day.year);
		String m=Integer.toString(day.month);
		String d=Integer.toString(day.day);

		String res=y+m+d;
		return Integer.parseInt(res);


	}



	public Day addDay(int d) {
		// Clone system date
		Day result = this.clone();
	
		// Days in each month for non-leap years
		int[] daysInMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	
		while (d > 0) {
			// Adjust for leap years
			if (result.month == 2 && Day.isLeapYear(result.year)) {
				daysInMonth[1] = 29;
			} else {
				daysInMonth[1] = 28;
			}
	
			// Increment the day
			result.day++;
	
			// Check if we need to roll over to the next month
			if (result.day > daysInMonth[result.month - 1]) {
				result.day = 1; // Reset to the first day of the next month
				result.month++;
	
				// Check if we need to roll over to the next year
				if (result.month > 12) {
					result.month = 1; // Reset to January
					result.year++;
				}
			}
	
			// Decrement the days to add
			d--;
		}
	
		return result;
	}
	


	// Return a string for the day like dd MMM yyyy
    @Override
	public String toString() {
		return day + "-" 
                   + MonthNames.substring((month-1)*3,(month*3))+"-"+year;
	}
  
    @Override
    public Day clone()
    {
        Day copy = null;
        try 
        {
            copy = (Day)super.clone();
        }
        catch(CloneNotSupportedException e){
            e.printStackTrace();
        }

        return copy;
    }

	@Override
	public int compareTo(Day day){
		if(concat_day(this)==concat_day(day)){
			return 0;
		}
		else if(concat_day(this)>concat_day(day)){
			return 1;
		}
		else{
			return -1;
		}
	}

}