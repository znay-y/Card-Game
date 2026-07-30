import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.Arrays;
import java.io.IOException;


public class csvReader {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        File myObj = new File("saves/profiles.csv");
        // try-with-resources: Scanner will be closed automatically
        try (Scanner myReader = new Scanner(myObj)) {
            
            System.out.println("\n\nCSV file contents:");
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] people = data.split(",");
                
                System.out.println(Arrays.toString(people));
                for (int i =0; i<people.length; i++) {
                    String saveID = people[0];
                    String name = people[1];
                    String chips = people[2];
            System.out.println(name+ " has "+chips+" chips with save ID: "+saveID);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
        }

        String name = IO.StringPut(sc, "enter a name");
        String saveid = IO.StringPut(sc, "enter a save ID");
        String chips = IO.StringPut(sc, "enter a chip amount");
        String newLine = saveid+","+name+","+chips;

        try{
        FileWriter write = new FileWriter("saves/profiles.csv", true);
write.write("\n"+newLine);
write.close();
        }catch(IOException e){
            System.out.println("An error occurred.");
        }
        
    }
}
