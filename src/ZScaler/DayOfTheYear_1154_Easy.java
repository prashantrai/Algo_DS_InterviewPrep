package ZScaler;

public class DayOfTheYear_1154_Easy {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	/*
    Time: O(1)
    Space: O(1)
    */
    public static int dayOfYear(String date) {
        /*String[] s = S.split("-");
        int year = Integer.parseInt(s[0]);
        int month = Integer.parseInt(s[1]);
        int date = Integer.parseInt(s[2]); */
        
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(5, 7));
        int day = Integer.parseInt(date.substring(8));

        int[] daysInMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        // Check for leap year and update February days
        if (isLeapYear(year)) {
            daysInMonth[1] = 29;
        }

        int dayOfYear = day;
        for (int i = 0; i < month - 1; i++) {
            dayOfYear += daysInMonth[i];
        }

        return dayOfYear;
    }
    
    /*
    The correct leap year rule is:
        A year is a leap year if it's divisible by 4
        However, if it's divisible by 100, it's NOT a leap year
        UNLESS it's also divisible by 400, then it IS a leap year
    */
    private static boolean isLeapYear(int year) {
        if(year % 400 == 0) return true;
        if(year % 100 == 0) return false;
        if(year % 4 == 0) return true;
        return false;
    }

}
