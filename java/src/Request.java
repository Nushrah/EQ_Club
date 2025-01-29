public class Request implements Comparable<Request>{
    private Member requester;
    private EquipmentSet requested_eq_s;
    private Day startDay;
    private Day endDay;
    private String eq_name;
    private String eq_code;
    private int daynums;
    // private static ArrayList<Borrowing> all_borrowings=new ArrayList<>();


    public Request(String id, String eq_code,String startD,int nDays) throws ExBrrwOverlap{
        this.startDay= new Day(startD).clone();
        this.daynums=nDays;

        Period temp_check = new Period(this.startDay,this.daynums);
        this.requested_eq_s = Equipment.returnEqbycode(eq_code).nextFreeEqSet(temp_check);
        // if(this.requested_eq_s.overlapCheck(temp_check)==0){
        //     throw new ExBrrwOverlap();
        // }

        this.eq_code=eq_code;

        this.requester = Club.getMById(id);
        
        this.endDay=this.startDay.addDay(nDays);
        
        this.eq_name=Equipment.returnEqbycode(eq_code).getName();
        
    }

    public Period rtnPeriod(){
        return new Period(this.startDay,this.daynums);
    }

    public String getEqCode(){return this.eq_code;}

    public Day getStartDay(){return this.startDay;}
    
    public int getDayNums(){return this.daynums;}

    public String getMemberId(){
       return this.requester.getId();
    }

    public String getListEQforRQs(){
        return this.startDay.toString()+" to "+this.endDay.toString();
    }

    public String getStatusForMEMB(){
        return "- requests "+this.requested_eq_s.getCode()+" "
            +"("+Equipment.returnEqbycode(this.requested_eq_s.getEqCode()).getName()+")"
            +" for "+this.startDay+" to "+this.endDay;
    }

    @Override
    public String toString() {
		return requester.getId()+" "+requester.getName()+
            " requests "+this.requested_eq_s.getCode()+" ("+this.eq_name+")"+
            " for "+this.startDay.toString()+" to "+ this.endDay.toString();

    }

    @Override
    public int compareTo(Request another) {
        int eqnum = this.requested_eq_s.getEqNum();
        int another_eqnum = another.requested_eq_s.getEqNum();
        int output = Integer.compare(eqnum, another_eqnum);

        if (output == 0) {
            int res = Integer.compare(this.requested_eq_s.getSetCode(), another.requested_eq_s.getSetCode());
            if(res==0){
                return this.startDay.compareTo(another.startDay);
            }
        }

        return output;
    }

}
