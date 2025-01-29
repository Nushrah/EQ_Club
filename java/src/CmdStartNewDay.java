public class CmdStartNewDay {

    // public void execute(String[] cmdParts) {
    //     try{
            
    //         if (cmdParts.length < 2) {
    //             throw new ExInsuffCmdArgs();
    //         }
     
    //         String Date = cmdParts[1];
    //         SystemDate.createTheInstance(Date); 
     
    //         RecordedCommand.record(new StartNewDayCommand(Date)); 
    //     }
    //     catch(ExInsuffCmdArgs e){
    //         System.out.println(e.getMessage());
    //     }
        
        
    // }

    public void execute(String[] cmdParts) {
        try {
            if (cmdParts.length < 2) {
                throw new ExInsuffCmdArgs();
            }
    
            String Date = cmdParts[1];
            SystemDate.createTheInstance(Date); // This will throw if date is invalid
            RecordedCommand.record(new StartNewDayCommand(Date)); // Only executes if the date is valid
        } catch (ExInsuffCmdArgs | ExInvalidDate | ExDateBeforeCurrentDate e) {
            System.out.println(e.getMessage());
        }
    }
    
    
}