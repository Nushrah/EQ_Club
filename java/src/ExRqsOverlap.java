public class ExRqsOverlap extends Exception{
    public ExRqsOverlap(){
        super("The period overlaps with a current period that the member borrows / requests the equipment.");
    }
    public ExRqsOverlap(String b){
        super("The period overlaps with a current period that the member borrows the equipment.");
    }
}
