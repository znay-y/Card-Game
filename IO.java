import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class IO {

    public static void print(String output) {
        System.out.println(output);
    }

    public static void print(int value) {
        System.out.println(value);
    }

    public static int INTput(Scanner sc, String message) {
        print(message);
        String input = sc.nextLine();
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            print("Please enter a valid integer");
            return INTput(sc, message);
        }
        return Integer.parseInt(input);
    }

    public static String StringPut(Scanner sc, String message) {
        print(message);
        String input = sc.nextLine();
        return input;
    }

    public static void clear() {
        print("\033[H\033[2J\033[3J");
        System.out.flush();
    }

     public static void csvRemover(String filePath, int lineNumber) {

        Path csvPath = Paths.get(filePath);

        try {

            List<String> lines = new ArrayList<>(Files.readAllLines(csvPath));

            if (lines.isEmpty()) {
                System.out.println("The CSV file is empty.");
                return;
            }

            int choice = lineNumber;


            if (choice < 1 || choice > lines.size()) {
                System.out.println("Error: Line number out of range.");
                return;
            }


            String removedLine = lines.remove(choice - 1);
            //System.out.println("Successfully removed line: " + removedLine);


            Files.write(csvPath, lines);
            //System.out.println("CSV file updated successfully.");
        } catch (IOException e) {
            System.out.println("An I/O error occurred: " + e.getMessage());
        }
    }

}
