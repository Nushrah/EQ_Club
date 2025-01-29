public class ExRqstOverlap extends Exception{
    public ExRqstOverlap(){
        super("The period overlaps with a current period that the member borrows / requests the equipment.");
    }
}
