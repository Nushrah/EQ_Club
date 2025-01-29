public class ExWrongCommand extends Exception {

    public ExWrongCommand(){
		super("Unknown command - ignored.");
    }
}
