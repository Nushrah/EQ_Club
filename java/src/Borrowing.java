public class Borrowing implements EqGet{
    
    private Member borrower;
    private EquipmentSet borrowed_eq_s;
    private Day startDay;
    private Day endDay;
    private String eq_name;
    private int daynums;
    // private static ArrayList<Borrowing> all_borrowings=new ArrayList<>();

    public Day getStartDay(){return this.startDay; }
    public int getDayNums(){return this.daynums;}

    public Period rtnPeriod(){
        return new Period(this.startDay,this.daynums);
    }

    public Borrowing(String id, String eq_code) throws ExBrrwOverlap{

        this.startDay=SystemDate.getInstance().clone();
        this.daynums=7;

        Period temp_check = new Period(this.startDay,7);
        this.borrowed_eq_s = Equipment.returnEqbycode(eq_code).nextFreeEqSet(temp_check);

        this.borrower = Club.getMById(id);
        
        this.endDay=this.startDay.addDay(daynums);

        this.eq_name=Equipment.returnEqbycode(eq_code).getName();        
    }

    public Borrowing(String id, String eq_code,int nDays) throws ExBrrwOverlap{
        
        this.startDay= SystemDate.getInstance().clone();
        this.daynums=nDays;
        

        Period temp_check = new Period(this.startDay,nDays);
        this.borrowed_eq_s = Equipment.returnEqbycode(eq_code).nextFreeEqSet(temp_check);
        
        this.borrower = Club.getMById(id);
                
        this.endDay=this.startDay.addDay(nDays);

        this.eq_name=Equipment.returnEqbycode(eq_code).getName();
        
    }

    public EquipmentSet getBorrowEQ(){return this.borrowed_eq_s;}

    public String getMemberId(){
       return this.borrower.getId();
    }

    public String getCurrentStatusForEQ(){
        return "Current status: "+this.borrower.getId()+" "+this.borrower.getName()
            +" borrows for "+this.startDay.toString()+" to "+this.endDay.toString();
    }

    public String getCurrentStatusForMEMB(){
        return "- borrows "+this.borrowed_eq_s.getCode()+" "
            +"("+Equipment.returnEqbycode(this.borrowed_eq_s.getEqCode()).getName()+")"
            +" for "+this.startDay+" to "+this.endDay;
    }

    @Override
    public String toString() {
		return borrower.getId()+" "+borrower.getName()+
            " borrows "+this.borrowed_eq_s.getCode()+" ("+this.eq_name+")"+
            " for "+this.startDay.toString()+" to "+ this.endDay.toString();

    }

    
}
