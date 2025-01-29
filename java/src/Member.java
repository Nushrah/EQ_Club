import java.util.ArrayList;
import java.util.Collections;

public class Member implements Comparable<Member>{
    private String id;
    private String name;
    private Day joinDate;
    private Borrowing borrowed_eq;
    private ArrayList<Request> request_eq = new ArrayList<>();


    public Member(String id, String name) {
        this.id = id;
        this.name = name;
        this.joinDate =  SystemDate.getInstance().clone();
        Club.getInstance().addMember(this);
    }

    public void borrowEq(Borrowing es){
        this.borrowed_eq=es;
        
    }
    public void removeborrowEq(){
        this.borrowed_eq=null;
    }
    public void addRequest(Request rq){
        request_eq.add(rq);
        Collections.sort(request_eq);
    }
    public void removeRequest(Request rq){
        request_eq.remove(rq);
        Collections.sort(request_eq);
    }

    public void listborrowings(){
        System.out.println("["+this.id+" "+this.name+"]");
        if((this.borrowed_eq==null) && (this.request_eq.isEmpty())){
            System.out.println("No record.");
        }
        else{
            if(this.borrowed_eq !=null){
                System.out.println(borrowed_eq.getCurrentStatusForMEMB());
            }
            
        }
        for(Request rq:request_eq){
            if(rq.getMemberId().equals(this.id)){
                System.out.println(rq.getStatusForMEMB());
            }
        } 
    }
    

    public boolean borrowDuplicateType(String e_code){
        
        if(this.borrowed_eq !=null){
            if(this.borrowed_eq.getBorrowEQ().getEqCode().equals(e_code)){
                return true;
            }
        }
        
        return false;
    }


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Day getJoinDate() {
        return joinDate;
    }

    public ArrayList<Request> getMembRequests () {return this.request_eq;}
    
    public Borrowing getMemberBorrowedEQ () {
        if(this.borrowed_eq==null){
            return null;
        }
        return borrowed_eq;

    }

    private int borroweqAvailability(){
        if(this.borrowed_eq==null){
            return 0;
        }
        return 1;
    }

    public static void list(ArrayList<Member> allMembers) {
        System.out.printf("%-5s%-9s%11s%11s%13s\n", "ID", "Name", "Join Date", "#Borrowed", "#Requested");
        for (Member x : allMembers) {
            System.out.printf("%-5s%-9s%11s%7d%13d\n", x.getId(), x.getName(),
                    x.getJoinDate(), x.borroweqAvailability(), x.request_eq.size());
        }
    }

    @Override
    public int compareTo(Member another) {
        int num = extractNumericId(this.id);
        int nextnum = extractNumericId(another.id);
        int output = Integer.compare(num, nextnum);

        if (output == 0) {
            return this.id.compareTo(another.id);
        }

        return output;
    }

    private static int extractNumericId(String id) {
        String num = id.replaceAll("[^0-9]", "");
        return Integer.parseInt(num);
    }
}