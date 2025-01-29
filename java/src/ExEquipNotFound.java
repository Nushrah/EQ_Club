public class ExEquipNotFound extends Exception{
    public ExEquipNotFound(){ 
        super("Equipment record not found.");
    }
    public ExEquipNotFound(String code){ 
        super("Missing record for Equipment "+code+".  Cannot mark this item arrival.");
    }
}
