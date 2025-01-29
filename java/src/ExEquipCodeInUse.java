public class ExEquipCodeInUse extends Exception {
    
    public ExEquipCodeInUse(){ super();}
    public ExEquipCodeInUse(String code){ 
        super("Equipment code already in use: "+code+" "+Equipment.returnEqbycode(code).getName());
    }
}
