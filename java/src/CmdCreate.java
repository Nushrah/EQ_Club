public class CmdCreate {
    
    public void execute(String[] cmd) {
        try {

            if (cmd.length < 3) {
                throw new ExInsuffCmdArgs();
            }

            String code = cmd[1];
            String name = cmd[2];

            if(Equipment.returnEqbycode(code) != null){
                throw new ExEquipCodeInUse(code);
            }
            else{
                new Equipment(code, name);
                RecordedCommand.record(new CreateEquipmentCommand(code, name));
                System.out.println("Done.");
            }
            
        }
        catch (ExInsuffCmdArgs e) {
            System.out.println(e.getMessage());
        }
        catch (ExEquipCodeInUse e){
            System.out.println(e.getMessage());
        }

    }
}    


