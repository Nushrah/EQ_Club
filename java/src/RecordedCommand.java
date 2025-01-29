import java.util.*;

public class RecordedCommand {
    private static ArrayList<Command> undoList = new ArrayList<>();
    private static ArrayList<Command> redoList = new ArrayList<>();

    public static void record(Command cmd) {
        undoList.add(cmd); // Add the command to the end of the undo list
        redoList.clear(); // Clear the redo list
    }

    public static void undoOneCommand() {
        if (!undoList.isEmpty()) {
            // Remove the last command from undo list and execute undo
            Command cmd = undoList.remove(undoList.size() - 1);
            cmd.undo();
            // Add the command to the end of the redo list
            redoList.add(cmd);
        } else {
            System.out.println("Nothing to undo.");
        }
    }

    public static void redoOneCommand() {
        if (!redoList.isEmpty()) {
            // Remove the last command from redo list and execute redo
            Command cmd = redoList.remove(redoList.size() - 1);
            cmd.redo();
            // Add the command to the end of the undo list
            undoList.add(cmd);
        } else {
            System.out.println("Nothing to redo.");
        }
    }
}
