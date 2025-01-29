public class SystemDate extends Day {

    private static SystemDate instance;
    private static SystemDate previnstance;

    private SystemDate(String sDay){super(sDay);}
    

    // public static void createTheInstance(String sDay){
    //     if (instance==null){
    //         instance = new SystemDate(sDay);
    //     } 
    //     else {
    //         try{
    //             if(str_valid(sDay)){
    //                 String[] sDayParts = sDay.split("-");

    //                 int d = Integer.parseInt(sDayParts[0]) ;
    //                 int y = Integer.parseInt(sDayParts[2]) ;
    //                 int m = MonthNames.indexOf(sDayParts[1])/3+1 ; 

    //                 Day newd= new Day(y,m,d);
    //                 // if(!(newd.isBefore(getInstance()))){
    //                 //     instance.set(sDay);
    //                 //     System.out.println("Done.");
    //                 // }
    //                 // else{
    //                 //     throw new ExDateBeforeCurrentDate();
    //                 // }

                    
    //                 if(newd.compareTo(getInstance())==1){
    //                     SystemDate.previnstance=getInstance();
    //                     instance.set(sDay);
    //                     System.out.println("Done.");
    //                 }
    //                 else{
    //                     throw new ExDateBeforeCurrentDate();
    //                 }
                 
    //             }
    //             else{
    //                 throw new ExInvalidDate();
    //             }
    //         }
    //         catch(ExDateBeforeCurrentDate e){
    //             System.out.println(e.getMessage());
    //         }
    //         catch(ExInvalidDate e){
    //             System.out.println(e.getMessage());
    //         }
            
    //     }
    // }

    public static void createTheInstance(String sDay) throws ExDateBeforeCurrentDate, ExInvalidDate {
        if (instance == null) {
            instance = new SystemDate(sDay);
        } else {
            if (str_valid(sDay)) {
                String[] sDayParts = sDay.split("-");
                int d = Integer.parseInt(sDayParts[0]);
                int y = Integer.parseInt(sDayParts[2]);
                int m = rtrnMonthNameIndex(sDayParts[1]);
                // int m = rtrnMonthNames().indexOf(sDayParts[1]) / 3 + 1;
    
                Day newd = new Day(y, m, d);
                if (newd.compareTo(getInstance()) == 1) {
                    SystemDate.previnstance = getInstance().clone();
                    instance.set(sDay);
                    System.out.println("Done.");
                } else {
                    throw new ExDateBeforeCurrentDate();
                }
            } else {
                throw new ExInvalidDate();
            }
        }
    }
    

    public static SystemDate getInstance() {return instance;}
    public static SystemDate getPrevInstance() {return previnstance;}

    @Override
    public SystemDate clone()
    {
        SystemDate copy = null;
        
            copy = (SystemDate)super.clone();
        
        

        return copy;
    }
    
}