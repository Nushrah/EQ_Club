public class CmdArrive {
    public void execute(String[] cmd) {
        try {

            if (cmd.length < 2) {
                throw new ExInsuffCmdArgs();
            }

            String code = cmd[1];

            if(Equipment.returnEqbycode(code) == null){
                throw new ExEquipNotFound(code);
            }
            
            Equipment.addSet(code);

            RecordedCommand.record(new ArriveEquipmentSetCommand(code));
            System.out.println("Done.");
        }
        catch (ExEquipNotFound e){
            System.out.println(e.getMessage());
        }
        catch(ExInsuffCmdArgs e){
            System.out.println(e.getMessage());
        }   
        

    }
}
