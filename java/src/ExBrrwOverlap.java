public class ExBrrwOverlap extends Exception{
    public ExBrrwOverlap(){
        super("The period overlaps with a current period that the member requests the equipment.");
    }
}
