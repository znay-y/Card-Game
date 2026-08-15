import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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

    public static void enterPause(Scanner sc) {
        print("\nPress Enter to continue");
        sc.nextLine();
        clear();
    }

    public static void clear() {
        print("\033[H\033[2J\033[3J");
        System.out.flush();
    }

    public static void lineRemover(String filePath, int lineNumber) {
        ArrayList<String> lines = new ArrayList<String>();
        try (Scanner reader = new Scanner(new File(filePath))) {
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                lines.add(data);
            }

        } catch (FileNotFoundException e) {
            print("File not found");
            return;
        }

        lines.remove(lineNumber - 1);

        try (FileWriter writer = new FileWriter(filePath, false)) {
            for (int i = 0; i < lines.size(); i++) {
                writer.write(lines.get(i)+"\n");
            }
        } catch (IOException e) {
            print("There was an error accessing the file");
        }

        return;

    }

}
