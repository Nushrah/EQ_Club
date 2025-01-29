public class ExDateBeforeCurrentDate extends Exception {
    public ExDateBeforeCurrentDate(){
        super("Invalid new day.  The new day has to be later than the current date "
        + SystemDate.getInstance().toString()+".");
    }
}
