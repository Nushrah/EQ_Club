import java.util.*;
public class EquipmentSet implements Comparable<EquipmentSet>{
    private String set_code;
    private boolean available;
    private Borrowing current_reservation;
    private ArrayList<Request> requests = new ArrayList<>();

    private Period reserved_days = new Period();

    public Period getP(){
        return this.reserved_days;
    }

    public String printAllRequests() {
        if (!this.requests.isEmpty()) {
    
            StringBuilder res = new StringBuilder("Requested period(s): ");
            for (int i = 0; i < requests.size(); i++) {
                Request rq = requests.get(i);
                res.append(rq.getListEQforRQs());
                if (i < requests.size() - 1) {
                    res.append(", ");
                }
            }
            return res.toString();
        } else {
            return null;
        }
    }
    
    
    
    // public void reserveDay(Day startD, int numOfDays){
    //     this.reserved_days.addDaysInPeriod(startD, numOfDays);
    // }

    public int RqoverlapCheck(Period p){
        return this.reserved_days.comparingP(p);
    }

    public EquipmentSet(String code){
        this.set_code=code;
        this.available=true;
    }

    public String getCode(){return set_code;}
    public Period getPeriod() {return reserved_days;}

    public boolean isAvailable(){ return available;}

    public void borrow(){
        this.available=false;
    }

    public String addRes(Borrowing b){
        this.available=false;
        this.current_reservation=b;

        this.reserved_days.addDaysInPeriod(b.getStartDay(), b.getDayNums());

        Club.getMById(b.getMemberId()).borrowEq(b);

        Collections.sort(requests);

        return current_reservation.toString();
    }
    
    public void removeRes(Borrowing b){
        this.available=true;
        this.current_reservation=null;

        this.reserved_days.removeDaysInPeriod(b.getStartDay(), b.getDayNums());

        Club.getMById(b.getMemberId()).removeborrowEq();

        Collections.sort(requests);

    }

    public String addReq(Request rq){

        this.reserved_days.addDaysInPeriod(rq.getStartDay(), rq.getDayNums());

        this.requests.add(rq);
        Club.getMById(rq.getMemberId()).addRequest(rq);
        return rq.toString();
    }
    public void removeReq(Request rq){

        this.reserved_days.removeDaysInPeriod(rq.getStartDay(), rq.getDayNums());

        this.requests.remove(rq);
        Club.getMById(rq.getMemberId()).removeRequest(rq);

    }

    public String getBorrowerId(){
        return this.current_reservation.getMemberId();
    }

    public String getEqCode(){
        String[] EqsParts = this.set_code.split("_");
        return EqsParts[0];
    }

    public int getSetCode(){
        String[] EqsParts = this.set_code.split("_");
        return Integer.parseInt(EqsParts[1]);
    }

    public int getEqNum(){
        int num = (int)this.getEqCode().charAt(1);
        return num;
    }

    public String getResString(){
        return this.current_reservation.toString();
    }

    
    public String toStringforEQ(){
        return this.current_reservation.getCurrentStatusForEQ();
    }

    public String toStringforMemb(){
        return this.current_reservation.getCurrentStatusForMEMB();
    }

    @Override
    public int compareTo(EquipmentSet another) {
        int eqnum = this.getEqNum();
        int another_eqnum = another.getEqNum();
        int output = Integer.compare(eqnum, another_eqnum);

        if (output == 0) {
            return Integer.compare(this.getSetCode(), another.getSetCode());
        }

        return output;
    }

}
