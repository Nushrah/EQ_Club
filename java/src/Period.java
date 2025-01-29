import java.util.ArrayList;

public class Period {
    private ArrayList<Day> days; //first and last day inclusive

    public Period(){
        this.days=new ArrayList<>();
    }
    public Period(Day startD, int numOfDays){
        this.days=new ArrayList<>();
        Day currentDay = startD.clone();

        for (int i = 0; i <= numOfDays; i++) { 
            days.add(currentDay); // Add the current day to the list
            currentDay = currentDay.addDay(1); 
        }
    }
    
    public void addDaysInPeriod(Day startD, int numOfDays){
        Day currentDay = startD.clone();

        for (int i = 0; i <= numOfDays; i++) {
            days.add(currentDay); 
            currentDay = currentDay.addDay(1); // Move to the next day
        }
    }

    public void removeDaysInPeriod(Day startD, int numOfDays) {
        Day currentDay = startD.clone();
    
        for (int i = 0; i <= numOfDays; i++) {
            days.remove(currentDay);
            currentDay = currentDay.addDay(1);
        }    
    }
    

    public boolean isDayInPeriod(Day startD){
        for(Day d:this.days){
            if (d.compareTo(startD)==0){
                return true;
            }
        }
        return false;
    }

    
    public int comparingP(Period another){
        // Iterate through all days in this Period
        for (Day day : this.days) {
            // Check if any day in this Period exists in the other Period
            if (another.isDayInPeriod(day)) {
                return 0; // Overlap found
            }
        }
        return 1; // No overlap at all
    }

    
    public void toStr(){
        for(Day d:days){
            System.out.print(d.toString()+", ");
        }
    }

}    