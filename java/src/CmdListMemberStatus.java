public class CmdListMemberStatus {
    public void execute(String[] cmdParts) {
        Club.getInstance().listMembStats();
    }
}
