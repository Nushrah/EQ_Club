public class ExCurrentlyBorrowingSameEq extends Exception{
    public ExCurrentlyBorrowingSameEq(){ 
        super("The member is currently borrowing a set of this equipment. He/she cannot borrow one more at the same time.");
    }
}
