public class CmdListMembers {

    public void execute(String[] cmdParts) {
        Club.getInstance().listClubMembers();
    }
}