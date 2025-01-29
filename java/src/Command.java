interface Command {
    void undo();
    void redo();
}

class RegisterCommand implements Command {
    private String id;
    private String name;

    public RegisterCommand(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public void undo() {
        Club.getInstance().removeMember(id); 
    }

    @Override
    public void redo() {
        new Member(id, name); 
    }
}

class StartNewDayCommand implements Command {
    private String date;

    public StartNewDayCommand(String date) {
        this.date = date;
    }

    @Override
    public void undo() {
        // Revert to the previous instance
        if (SystemDate.getPrevInstance() != null) {
            SystemDate.getInstance().set(SystemDate.getPrevInstance().toString());
        } else {
            System.out.println("No previous date to revert to.");
        }
    }

    @Override
    public void redo() {
        SystemDate.getInstance().set(date); 
    }
}
 
class CreateEquipmentCommand implements Command {
    private String code;
    private String name;

    public CreateEquipmentCommand(String id, String name) {
        this.code = id;
        this.name = name;
    }

    @Override
    public void undo() {
        Equipment.removeEq(code); 
    }

    @Override
    public void redo() {
        new Equipment(code, name); 
    }
}

class ArriveEquipmentSetCommand implements Command {
    private String e_code;
    

    public ArriveEquipmentSetCommand(String code) {
        this.e_code=code;
    }

    @Override
    public void undo() {
        Equipment.removeSet(e_code); 
    }

    @Override
    public void redo() {
        Equipment.addSet(e_code);
    }
}

class BorrowingCommand implements Command {
    private EquipmentSet eqs;
    private Borrowing brw;
    

    public BorrowingCommand(EquipmentSet es, Borrowing b) {
        this.eqs=es;
        this.brw=b;
    }

    @Override
    public void undo() {
        eqs.removeRes(brw);
    }

    @Override
    public void redo() {
        eqs.addRes(brw);
    }
}

class RequestCommand implements Command {
    private EquipmentSet eqs;
    private Request rq;
    

    public RequestCommand(EquipmentSet es, Request r) {
        this.eqs=es;
        this.rq=r;
    }

    @Override
    public void undo() {
        eqs.removeReq(rq);
    }

    @Override
    public void redo() {
        eqs.addReq(rq);
    }
}