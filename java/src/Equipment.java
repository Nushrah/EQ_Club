import java.util.*;

public class Equipment {

    private String e_code;
    private String e_name;
    private ArrayList<EquipmentSet> e_list;
    private int set_nums = 0;
    private static ArrayList<Equipment> allEq_list = new ArrayList<>();

    public String getCode() { return e_code; }
    public String getName() { return e_name; }
    public int getSetNums() { return set_nums; }

    public Equipment(String code, String name) {
        this.e_code = code;
        this.e_name = name;
        this.e_list = new ArrayList<>();
        allEq_list.add(this);
    }

    public static Equipment returnEqbycode(String code) {
        for (Equipment e : allEq_list) {
            if (e.e_code.equals(code)) {
                return e;
            }
        }
        return null;
    }

    public static void addSet(String code) {
        Equipment equipment = Equipment.returnEqbycode(code);
        equipment.set_nums++;
        equipment.e_list.add(new EquipmentSet(code + "_" + equipment.set_nums));
    
    }

    public static void removeSet(String code) {
        Equipment equipment = Equipment.returnEqbycode(code);
        if (equipment != null && !equipment.e_list.isEmpty()) {
            equipment.e_list.remove(equipment.e_list.size() - 1); // Remove last added set
            equipment.set_nums--;
        }
    }

    public EquipmentSet nextFreeEqSet(Period p) {
        for (EquipmentSet eqs : e_list) {
            if (eqs.RqoverlapCheck(p)==1) {
                return eqs;
            }
        }
        return null;
    }

    public static void removeEq(String code) {
        allEq_list.removeIf(eq -> eq.getCode().equals(code));
    }

    public static void listAllEquipment() {
        System.out.printf("%-5s%-15s%7s\n", "Code", "Name", "#sets");
        for (Equipment x : allEq_list) {
            StringBuilder borrowedSets = new StringBuilder();
            for (EquipmentSet eqs : x.e_list) {
                if (!eqs.isAvailable()) {
                    if (borrowedSets.length() > 0) {
                        borrowedSets.append(", ");
                    }
                    borrowedSets.append(eqs.getCode()).append("(").append(eqs.getBorrowerId()).append(")");
                }
            }

            String borrowedSetsString = borrowedSets.length() > 0 
                ? " (Borrowed set(s): " + borrowedSets + ")" 
                : "";

            String formattedSets = String.format("%3s%-3s", "", x.getSetNums());

            System.out.printf(
                "%-5s%-15s%7s%s\n",
                x.getCode(),
                x.getName(),
                formattedSets,
                borrowedSetsString
            );
        }
    }

    public static void listEquipmentStatus(){
        for(Equipment eq:allEq_list){
            System.out.println("["+eq.e_code+" "+eq.e_name+"]");

            if(eq.e_list.isEmpty()){
                System.out.println("  We do not have any sets for this equipment.");

            }

            for(EquipmentSet eqs : eq.e_list) {
                if (!eqs.isAvailable()) {
                    System.out.println("  "+eqs.getCode());
                    System.out.println("    "+eqs.toStringforEQ());
                    if(eqs.printAllRequests() != null){
                        System.out.println("    "+eqs.printAllRequests()) ;
                    }
                }
                else{
                    System.out.println("  "+eqs.getCode());
                    System.out.println("    Current status: Available");
                    if(eqs.printAllRequests() != null){
                        System.out.println("    "+eqs.printAllRequests()) ;
                    }                } 
            }
            System.out.println();   

        }

    }
}

