public class ExIdInUse extends Exception {
    public ExIdInUse(){ super();}
    public ExIdInUse(String id){
        super("Member ID already in use: "+id+" "+Club.findById(id));
        
    }
}
