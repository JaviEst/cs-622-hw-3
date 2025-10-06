package file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;



public class TestFileHandler {
  private static final String TEST_FILE_NAME = "test_file.txt";
  private static final String INVALID_PATH_FILE = "/invalid/path/that/does/not/exist/test.txt";
  
  @Before
  public void setUp() {
    // Clean up any existing or remaining test files before each test
    deleteTestFile(TEST_FILE_NAME);
  }
  
  @After
  public void tearDown() {
    // Clean up test files after each test
    deleteTestFile(TEST_FILE_NAME);
  }
  
  private void deleteTestFile(String fileName) {
    try {
      Files.deleteIfExists(Paths.get(fileName));
    } catch (IOException e) {
      // Ignore cleanup errors
    }
  }
  
  @Test
  public void testSaveToFileAndReadFromFileWithValidData() {
    // Test saving valid data to a file
    String testData = "Hello, World! This is a test.";
    FileHandler.saveToFile(testData, TEST_FILE_NAME);
    
    // Verify file was created and contains correct data
    Assert.assertTrue("File should be created", new File(TEST_FILE_NAME).exists());
    String readData = FileHandler.readFromFile(TEST_FILE_NAME);
    Assert.assertEquals("File content should match saved data", testData, readData);
  }
  
  @Test
  public void testSaveToFileAndReadFromFileWithEmptyData() {
    // Test saving empty string to a file
    String emptyData = "";
    FileHandler.saveToFile(emptyData, TEST_FILE_NAME);
    
    // Verify file was created and is empty
    Assert.assertTrue("File should be created", new File(TEST_FILE_NAME).exists());
    String readData = FileHandler.readFromFile(TEST_FILE_NAME);
    Assert.assertEquals("File content should be empty", emptyData, readData);
  }
  
  @Test
  public void testSaveToFileWithInvalidPath() {
    // Test saving to an invalid file path
    String testData = "Test data";
    
    // This should not throw an exception but should handle the error gracefully
    FileHandler.saveToFile(testData, INVALID_PATH_FILE);
    
    // Verify file was not created
    Assert.assertFalse("File should not be created with invalid path", 
                       new File(INVALID_PATH_FILE).exists());
  }
  
  @Test
  public void testReadFromNonExistentFileFile() {
    // Test reading from a file that doesn't exist
    String readData = FileHandler.readFromFile("non_existent_file.txt");
    Assert.assertEquals("Should return empty string for non-existent file", "", readData);
  }
}
