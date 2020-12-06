package Santa_Secret_Gift;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

// Extracting from text document e.g Christmas.txt and assign each children min 1 or max 3 presents and write to Deleveries.txt
class AssignPresents {
    // File name where all male presents saved
    String MalePresentsListFile = "Presents_M.txt";
    
    // File name where all female presents saved
    String FemalePresentsListFile = "Presents_F.txt";
    
    // File name where all chilren records saved
    String ChilrenRecordFile = "Christmas.txt";
    
    // File name where all presents will written
    String SantaChristmasListFile = "Deliveries.txt";
    
    // List for store all Male Presents
    List<String> MalePresentsList = new ArrayList<String>();      
    
    // List for store all Female Presents
    List<String> FemalePresentsList = new ArrayList<String>();   

    // Variable for store all children records
    String[] data = new String[100];                         
        
    // Variable for store all list of assigned presents
    String writeData = "";                             
    
    // Variable for total Number of Records
    int noOfRecords = 0; 
    
    // Method for read presents from give file
    void readPresents(String FileName, List<String> list){
       // Variable for read all children record one by one    
        String strLine = "";
        
        // Read Christmas.txt file and store all records in data array
        try {    
            // BufferedReader is a class in Java that reads text from Christmas.txt
            BufferedReader data = new BufferedReader(new FileReader(FileName));
            
            // loop to read one line per cycle                                                                           
            while (strLine != null) {
                if (strLine == null) {
                    break;
                }                                                  
                //readLine() method reads only one noOfRecords(row) at a time
                strLine = data.readLine();
                
                // if null then add data in lists
                if(strLine != null)
                    list.add(strLine);
            }
            // closing file
            data.close();
            
        } catch (FileNotFoundException e) {
            System.err.println(FileName + " file not found");
        } catch (IOException e) {
            System.err.println("Unable to read "+FileName+" file.");
        } 
    }
    
    // Random Number Generator Method for Present pick from Array
    int randomGenerator(String[] random) {                                                                         
        int randomIndex = (int) (Math.random() * random.length);
        return randomIndex;
    }
    
    // Random Number Generator Method Between 1 - 3
    int noOfPresentsAssign() {                                                                                                      
        int min = 1;
        int max = 3;
        int random_int = (int) (Math.random() * (max - min + 1) + min);
        return random_int;
    }
    
    // Create construstor to read all children records from Christmas.txt file
    AssignPresents() throws Exception {
        // Read male presents in list from Presents_M.txt file
        readPresents(MalePresentsListFile, MalePresentsList);
        
        // Read female presents in list from Presents_F.txt file
        readPresents(FemalePresentsListFile, FemalePresentsList);

        // Variable for read all children record one by one    
        String strLine = "";
        
        // Read Christmas.txt file and store all records in data array
        try {    
            // BufferedReader is a class in Java that reads text from Christmas.txt
            BufferedReader childrenRecord = new BufferedReader(new FileReader(ChilrenRecordFile));  
            
            // loop to read one line per cycle                                                                           
            while (strLine != null) {
                if (strLine == null) {
                    break;
                }                                                  
                //readLine() method reads only one noOfRecords(row) at a time
                strLine = childrenRecord.readLine();
                
                // strline data is being stored in data[noOfRecords]
                data[noOfRecords] = strLine;
                
                // Increment no of records counter
                noOfRecords++;
            }
            // closing file
            childrenRecord.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not found");
        } catch (IOException e) {
            System.err.println("Unable to read the file.");
        }
    }
    
    // Present Assign Method
    void presentAssign(String childrenRecord) { 
        // Male Present(Gift) save in array from list
        String[] myPresent_M = MalePresentsList.toArray(new String[MalePresentsList.size()]);  //{"Football", "Toy car", "Toy Dinosaur", "Truck toy", "Basketball", "Watch", "Mobile phone", "X-box"};
        
        // Female Present(Gift) save in array from list
        String[] myPresent_F = FemalePresentsList.toArray(new String[FemalePresentsList.size()]);//{"Hair clips", "Swimming togs", "Kite", "Lego house", "Hair clips", "Doll", "Colouring pencils", "Bangles"};
        
        //temporary variables for store present
        String tempPresent = "";                                                                               
        
        //temporary variables for proper statment
        String finalString = "";                                                                         
        
        // Read No of present assign
        int noOfPresent = noOfPresentsAssign();

        // Declare array for check old present indexes
        int[] oldPresentIndexes = new int[noOfPresent]; 

        // Intialize all array with value -1 to compare with all assign present indexes
        for (int i = 0; i < noOfPresent; i++) {
            oldPresentIndexes[i] = -1;
        }
        
        for (int i = 0; i < noOfPresent; i++) {
            int randomIndex = 0;
            if (childrenRecord.endsWith(", M")) {
                // Read random present index number for Male
                randomIndex = randomGenerator(myPresent_M);
            } else {
                // Read random present index number for Female
                randomIndex = randomGenerator(myPresent_F);
            }
            
            // Variable for present save or not
            boolean saveResponse = true;
            // Check all old present indexes to ignor repitation of present
            for (int j = 0; j < noOfPresent; j++) {
                if (oldPresentIndexes[j] != randomIndex) {
                    saveResponse = true;
                } else { // if new index is match with old assigned indexes of particular record than repeat all steps to assign new present to this record
                    saveResponse = false;
                    i--;
                    break;
                }
            }

            // Present is new and add in children present list
            if (saveResponse == true) {
                // save new present index to old present indexs array
                oldPresentIndexes[i] = randomIndex;
                if (childrenRecord.endsWith(", M")) {
                    // save present name in temp vairable for Male
                    tempPresent = myPresent_M[randomIndex];
                } else {
                    // save present name in temp vairable for Female
                    tempPresent = myPresent_F[randomIndex];
                }
                
                // add present name in present list
                finalString += tempPresent + ", ";
            }
        }
        if (childrenRecord.endsWith(", M")) {
            // Concatinate Children Name with their assinged Presents in writeData varaible
            writeData += childrenRecord.substring(0, childrenRecord.lastIndexOf(", M")) + " (" + finalString.substring(0, finalString.length() - 2) + ")\n";  
        } else {
            // Concatinate Children Name with their assinged Presents in writeData varaible
            writeData += childrenRecord.substring(0, childrenRecord.lastIndexOf(", F")) + " (" + finalString.substring(0, finalString.length() - 2) + ")\n";  
        }        
    }
    
    // Validation Check Method
    public static boolean checkValidation(String checkSting) {
        // checking if a string contains ","
        if (checkSting.contains(",")) {                                                                                                                          
            //breaking the string at ","
            String x = checkSting.substring(0, checkSting.indexOf(","));                                                                                        
            //checking given Validation by Regex and last character is either 'M' or 'F'
            if (x.matches("^[a-z A-Z]*$") && ((checkSting.charAt(checkSting.length() - 1) == 'M' || checkSting.charAt(checkSting.length() - 1) == 'F'))) {     
                return true;                                                                                                                                  
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    // Create presents list method
    void CreateSantaList() {                                                                                                    
        int noOfInvalidRecords = 0;
        for (int i = 0; i < (noOfRecords - 1); i++) {                                                                          
            // Read Children record from 
            String temp = data[i];
            // Check record is valid or not
            Boolean bool = checkValidation(temp);
            // If record is valid then assign random presents
            if (bool == true) {
                // Assign presents to this valid record
                presentAssign(temp);
            } else { // if record is invalid increment invalid records counter
                noOfInvalidRecords++;
            }
        }
        System.out.println("There were " + noOfInvalidRecords + " invalid names in the list.");
        try (PrintWriter out = new PrintWriter(SantaChristmasListFile)) {    
            //Data write to Deliveries.txt
            out.write(writeData);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

public class MainFunction {
    public static void main(String[] args) throws Exception {
        // Create class object and call construstor
        AssignPresents objAssignPresents = new AssignPresents();
        
        // Call method to create Santa’s Christmas List
        objAssignPresents.CreateSantaList();
        
        // Check if Santa’s Christmas List is ready or not
        if (objAssignPresents.writeData == "") {
            System.out.println("There are no Presents List for Santa");
        } else {
            System.out.println("List ready for Santa");
        }
    }
}