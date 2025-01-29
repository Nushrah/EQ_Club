public class CmdBorrow {
    private int day;
    public void execute(String[] cmd) {
        try {

            if (cmd.length < 3) {
                throw new ExInsuffCmdArgs();
            }
            

            String m_code = cmd[1];
            String e_code = cmd[2];

            if (cmd.length == 3) {
                day = 7;
            } else {
                day = Integer.parseInt(cmd[3]);
            }
            if(day<=0){
                throw new Exception("The number of days must be at least 1.");
            }


            Day startDate = SystemDate.getInstance().clone();
            // Day endDate = startDate.addDay(day);

            if(Equipment.returnEqbycode(e_code) == null){
                throw new ExEquipNotFound();
            }

            if(Club.getMById(m_code) == null){
                throw new ExMemberNotFound();
            }

            
            Period newP = new Period(startDate, day);

            Member member = Club.getMById(m_code);
            //Overlap with member requests
            for (Request s: member.getMembRequests()) {
                if(s.getEqCode().equals(e_code)) {

                    
                    if (s.rtnPeriod().comparingP(newP) == 0) {
                        throw new ExBrrwOverlap();
                    }
                    
                }
                
            }

            if(Club.getMById(m_code).borrowDuplicateType(e_code)){
                throw new ExCurrentlyBorrowingSameEq();
            }
            
            if(Equipment.returnEqbycode(e_code).nextFreeEqSet(newP)==null){
                throw new ExNoEqSetAvailable();
            }

            
            EquipmentSet to_borrow = Equipment.returnEqbycode(e_code).nextFreeEqSet(newP);
            Borrowing b;

            if(cmd.length==3){
                // int days = 7;
                b = new Borrowing(m_code,e_code);
            }
            else{
                int days = Integer.parseInt(cmd[3]);
                b = new Borrowing(m_code,e_code,days);
            }


            String message = to_borrow.addRes(b);
            

            System.out.println(message);

            RecordedCommand.record(new BorrowingCommand(to_borrow, b));


            System.out.println("Done.");
        }
        catch(NumberFormatException e){
            System.out.println("Please provide an integer for the number of days.");
        }
        catch(ExBrrwOverlap e){
            System.out.println(e.getMessage());
        }
        catch(ExNoEqSetAvailable e){
            System.out.println(e.getMessage());
        }
        catch(ExCurrentlyBorrowingSameEq e){
            System.out.println(e.getMessage());
        }      
        catch (ExEquipNotFound e){
            System.out.println(e.getMessage());
        }
        catch(ExMemberNotFound e){
            System.out.println(e.getMessage());
        }
        catch(ExInsuffCmdArgs e){
            System.out.println(e.getMessage());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }  
        

    }
    
    
}
