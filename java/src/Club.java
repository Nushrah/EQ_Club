import java.util.ArrayList;
import java.util.Collections;

public class Club {
    private static Club instance = new Club();
    private ArrayList<Member> allMembers;

    private Club() { 
        allMembers = new ArrayList<>();
    }

    public static Club getInstance() { return instance; }

    public void addMember(Member member) {
        allMembers.add(member);
        Collections.sort(allMembers);
    }

    public static boolean IdIsTaken(String id) {
        for (Member m : Club.getInstance().allMembers) {
            if (id.equals(m.getId())) { // Use equals for string comparison
                return true; // ID is taken
            }
        }
        return false; // ID is not taken after checking all members
    }
    

    public static String findById(String id) {
        for (Member m : Club.getInstance().allMembers) {
            if (id.equals(m.getId())) {
                return m.getName();
            }
        }
        return null;
    }

    public static Member getMById(String id) {
        for (Member m : Club.getInstance().allMembers) {
            if (id.equals(m.getId())) {
                return m;
            }
        }
        return null;
    }
    

    public void listClubMembers() {
        Member.list(allMembers);
    }

    public void listMembStats(){
        for (Member m : Club.getInstance().allMembers){
            m.listborrowings();
            System.out.println();
        }
    }

    public void removeMember(String id) {
        allMembers.removeIf(member -> member.getId().equals(id));
        Collections.sort(allMembers);
    }
}
