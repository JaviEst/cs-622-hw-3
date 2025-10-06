package file;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileHandler {
  /**
   * Save data to a file.
   * 
   * @param data the data to be saved in the file 
   * @param fileName the file name where the data should be saved to
   */
  public static void saveToFile(String data, String fileName) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
      writer.write(data);
      System.out.println("Saved data to " + fileName);
    } catch (IOException e) {
      System.err.println("Error writing to file: " + e.getMessage());
    }
  }

  /**
   * Read data from a file.
   * 
   * @param fileName the file name from where the data should be read
   */
  public static String readFromFile(String fileName) {
    try {
      return Files.readString(Path.of(fileName));
    } catch (IOException e) {
      System.err.println("Error reading file: " + e.getMessage());
      return "";
    }
  }
}
