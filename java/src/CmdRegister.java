public class CmdRegister {

    public void execute(String[] cmd) {
        try {

            if (cmd.length < 3) {
                throw new ExInsuffCmdArgs();
            }

            String id = cmd[1];
            String name = cmd[2];

            if (Club.IdIsTaken(id)) {
                throw new ExIdInUse(id);
            } else {
                new Member(id, name);
                RecordedCommand.record(new RegisterCommand(id, name));
                System.out.println("Done.");
            }

        } catch (ExInsuffCmdArgs e) {
            System.out.println(e.getMessage());
        } catch (ExIdInUse e) {
            System.out.print(e.getMessage());
            System.out.println();
    
        }

    }
}