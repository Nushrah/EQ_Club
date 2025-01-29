public class CmdRequest {
    public void execute(String[] cmd) {
        try {

            if (cmd.length < 5) {
                throw new ExInsuffCmdArgs();
            }

            int days = Integer.parseInt(cmd[4]);
            if(days<=0){
                throw new Exception("The number of days must be at least 1.");
            }

            

            String m_code = cmd[1];
            String e_code = cmd[2];
            String startD = cmd[3];

            if (!Day.str_valid(startD)){
                throw new ExInvalidDate();
            }

            Day startDate=new Day(startD);

            if(Equipment.returnEqbycode(e_code) == null){
                throw new ExEquipNotFound();
            }

            if(Club.getMById(m_code) == null){
                throw new ExMemberNotFound();
            }

            Period newP = new Period(startDate, days);

            

            //Overlap with member borrows
            // for (EquipmentSet eqs: member.getMemberBorrowedEQs()) {
            //     if(eqs.getEqCode().equals(e_code)) {
                    
            //         if (eqs.RqoverlapCheck(newP) == 0) {
            //             System.out.print("eqs days:");
            //             eqs.getP().toStr();
            //             System.out.println();
            //             System.out.print("newp days:");
            //             newP.toStr();





            //             throw new ExRqsOverlap("b");
            //         }
                    
            //     }
                
            // }

            Member member = Club.getMById(m_code);

            //Overlap with member requests
            for (Request s: member.getMembRequests()) {
                if(s.getEqCode().equals(e_code)) {

                    
                    if (s.rtnPeriod().comparingP(newP) == 0) {
                        throw new ExRqsOverlap();
                    }
                    
                }
                
            }

            //Overlap with member borrow
            if(member.getMemberBorrowedEQ() != null){
                if(member.getMemberBorrowedEQ().rtnPeriod().comparingP(newP) == 0) {
                    throw new ExRqsOverlap();
                }
            }
            

                
            


            if(Equipment.returnEqbycode(e_code).nextFreeEqSet(newP)==null){
                throw new ExNoEqSetAvailable();
            }

            EquipmentSet to_request = Equipment.returnEqbycode(e_code).nextFreeEqSet(newP);
            Request r = new Request(m_code, e_code, startD, days);

            
            String message = to_request.addReq(r);
            

            System.out.println(message);

            RecordedCommand.record(new RequestCommand(to_request, r));


            System.out.println("Done.");
        }
        catch (ExInvalidDate e) {
            System.out.println(e.getMessage());
        }
        catch(NumberFormatException e){
            System.out.println("Please provide an integer for the number of days.");
        }        
        catch(ExRqsOverlap e){
            System.out.println(e.getMessage());
        }
        catch(ExNoEqSetAvailable e){
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
