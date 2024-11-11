import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Energy_Gauge {
    public static void main(String[] args) {
    //Ensures 2 arguments, requests user to put energy.csv as arguments.
    if (args.length != 2) {
        System.out.printf("Error: Expecting 1 program argument. Found %d instead.%n", args.length);
        System.out.printf("Please enter a csv file as the program argument.%n");
        System.out.print("First Argument read in, second argument write in");
        System.exit(1);
    }
     // Maximum electronics list size.
     final int MAX_SIZE = 100;
    
     // Stores the names of each electronic.
     String[] electronicNames = new String[MAX_SIZE];
     // Stores the quantity of each item.
     Integer[] electronicQuantities = new Integer[MAX_SIZE];
     //Stores the power consumption of each item.
     Integer[] electronicPower = new Integer[MAX_SIZE];
      
     //used to determine where to add the next item.
     int size = 0;
     //used to store user choice to open menu
     int choice = 0;
     // Choice for ending program
    final int QUIT = 5;
    
    // Attempt to read in the file name provided from the first program argument
    // and populate the arrays with items and quantities.   
    // The program can crash in two known ways:
    //   1) There are too many items in the file to be stored in the array.
    //   2) The CSV is not in the expected format.
    try {
      // Read in the CSV file and store the item name and quantity in the appropriate arrays.
      // Update size to know how many items are actually in the list.
      size = readFromFile(args[0], electronicNames, electronicQuantities, electronicPower);
    }
    // When there are too many items (> MAX_SIZE) in the CSV file.
    catch (ArrayIndexOutOfBoundsException aiob) {
      System.out.printf("Error: Too many items in the input file: %s%n", args[0]);
      System.out.printf("Please limit to %d items.%n", MAX_SIZE);
      System.exit(1);
    }
    // When the file is not properly in CSV format.
    catch (NoSuchElementException nse) {
      System.out.printf("Error: Could not read in the input file: %s%n", args[0]);
      System.out.println("Please check input file for errors in CSV formatting.");
      System.out.println("Possible errors could be empty data values, or blank lines.");
      System.exit(1);
    }
    // Keep running the program until the user wants to quits via the menu.
    // If the user types in something outside 1 - 4, keep asking.
    while (choice != QUIT) {
      
        // Get the user's choice.: 1) Add 2) Delete 3) Display 4) Quit
        choice = displayMenu();
        switch (choice) {
          case 1:
            size = add(electronicNames, electronicQuantities, size, electronicPower, MAX_SIZE);
            break;
          case 2:
            size = delete(electronicNames, electronicQuantities, electronicPower, size, MAX_SIZE);
            break;
          case 3:
            display(electronicNames, electronicQuantities, electronicPower, size);
            break;
          case 4:
            System.out.println("Not here yet - EDIT");
          case 5:
            // User wants to quit. The loop will exit.
            break;
          default:
            System.out.println("Error: Please enter an option in the range from 1 to 4.");
            break;
        }
      }
        
      // The user chose to quit, write out the grocery list to file.
      writeToFile(args[1], electronicNames, electronicQuantities, electronicPower, size);
    }
  public static int readFromFile(String inputFilename, String[] electronicNamesParam, Integer[] electronicQuantitiesParam, Integer[] electronicPowerParam)
    throws ArrayIndexOutOfBoundsException, NoSuchElementException {
                                
    // The file to read.
    File inFile = new File(inputFilename);
    // Read input from the file.
    Scanner fileReader = null;
    
    // How many items are read in from inputFilename.
    int initialSize = 0;
    
    // Temporarily stores an entire line from the file.
    String inputLine = "";
    // Store the name of the item.
    String name = "";
    // Temporarily stores the quantity.
    // Reads in the quantity as a String first then converts.
    String stringQuantity = "";
    // Successful conversion of the above variable will be stored here.
    int quantity = 0;
    //Stores the name of the item's store
    String store = "";
    // Reads in the Power as a String first then converts.
    String stringPower = "";
    // Successful conversion of the above variable will be stored here.
    int power = 0;
    
    // Attempt to open the file for reading.
    try {
      fileReader = new Scanner(inFile);
    }
    catch (FileNotFoundException fnf) {
      System.out.printf("Error: File not found %s%n", inFile.getAbsolutePath());
      // Since the file could not be opened the electrnic list is empty.
      return 0;
    }
    
    // Indicate the program is proceeding.
    System.out.printf("Read from file: %s%n", inFile.getAbsolutePath());
    
    // Grab the first line which is the headers.
    // Doesn't actually do anything with it...
    String headers = fileReader.nextLine();
    
    // Read all lines in the file and store item name and quantity information.
    while (fileReader.hasNextLine()) {
      // Get a line of data.
      inputLine = fileReader.nextLine();
      
      // Prepare to parse a line in the CSV file.
      Scanner electronicLineReader = new Scanner(inputLine);
      // Values are separated by commas.
      electronicLineReader.useDelimiter(",");
      
      // Retrieve the name and quantity from the line.
      name = electronicLineReader.next();
      stringQuantity = electronicLineReader.next();
      stringPower = electronicLineReader.next();
      
      // Attempt to parse the quantity to an int.
      try {
        quantity = Integer.parseInt(stringQuantity);
      }
      catch (NumberFormatException nfe) {
        // Indicate the parse failed.
        System.out.printf("Error: Unable to parse quantity \"%s\" for item %s, using 1 instead.%n", stringQuantity, name);
        // Use 1 as a defensive measure.
        quantity = 1;
      }
      try {
        //Parses power usage to an Integer.
        power = Integer.parseInt(stringPower);
      }
      catch (NumberFormatException nfe) {
        // Indicate the parse failed.
        System.out.printf("Error: Unable to parse power \"%s\" for item %s, using 1 instead.%n", stringPower, name);
        // Use 1 as a defensive measure.
        power = 1;
      }
      
      // Error correction.
      // Ensure all quantities are positive.
      quantity = Math.abs(quantity);
      
      // Add the grocery item to the arrays.
      electronicNamesParam[initialSize] = name;
      electronicQuantitiesParam[initialSize] = quantity;
      electronicPowerParam[initialSize] = power;
      
      // Increase size after an item has been added.
      initialSize++;
    }
    
    // Done reading.
    fileReader.close();
    
    // Reading done, return how many items were stored.
    return initialSize;
  }
      /**
   * Displays the menu for the program and returns user's choice
   * 
   * @return the choice of the user (if error, returns 0)
   *         1 to add a electronic,
   *         2 to delete a electronic,
   *         3 to display the electronics,
   *         4 to edit an electronic,
   *         5 to quit (writes out the electronic list)
   */
  public static int displayMenu() {
    // Read input from the keyboard.
    Scanner reader = new Scanner(System.in);
    // Temporarily store the user's menu choice.
    String stringChoice = "";
    // The converted value.
    int choiceOfUser = 0;
   
    // Display the menu.
    System.out.println();
    System.out.println("\tGROCERY LIST MENU");
    System.out.println("\t Enter 1 to Add");
    System.out.println("\t Enter 2 to Delete");
    System.out.println("\t Enter 3 to Display");
    System.out.println("\t Enter 4 to Edit");
    System.out.println("\t Enter 5 to Quit");
    System.out.print("\tEnter your choice: ");
   
    // Get the user's choice as a String.
    stringChoice = reader.nextLine();
      
    // In case the user does not enter an integer.
    try {
      // Attempt to convert the String to an integer.
      choiceOfUser = Integer.parseInt(stringChoice);
    }
    // The user did not enter an integer.
    catch (NumberFormatException nfe) {
      System.out.printf("Error: %s is not an integer.%n", stringChoice);
    }
      
    System.out.println();
    // Return the user's choice, or 0 if an error.
    return choiceOfUser;
  }
  /**
   * Adds a electronic item to an array. Prompts the user for item name and quantity.
   * 
   * @param electronicNamesParam is the electrnic item names
   * @param electronicQuantitiesParam is the quantities of the electronics items  
   * @param electronicPowerParam is the amount of power the electronics consume
   * @param size is the size of the list
   * @param MAX_SIZE is the maximum number of electronics that can be stored
   * @return new size of the  list
   */
  public static int add(String[] electronicNamesParam, Integer[] electronicQuantitiesParam, int size, Integer[] electronicPowerParam, final int MAX_SIZE) {
  
    // Special case if the arrays are full.
    if (size == MAX_SIZE) {
      System.out.printf("Error: Electronic list is full with %d items.%n", size);
      System.out.printf("Please delete an item to add more.%n");
      return size;
    }
  
    // Read input from the keyboard.
    Scanner keyboard = new Scanner(System.in);
    // Used to store the name the user typed.
    String inputName = "";
    // Used to temporarily store the quantity the user typed.
    // Store the quantity as a string first, then convert.
    String stringQuantity = "";
    // Successful conversion of the above variable will be stored here.
    int quantity = 0;
    //Used to store the amount of power the user typed
    String powerName = "";
    //Stored covnersion of powerName
    int powerConsume = 0;
    // Get the name, quantity, and store name from the user.
    System.out.print("Enter name of item: ");
    inputName = keyboard.nextLine();
    System.out.print("Enter quantity of items: ");
    stringQuantity = keyboard.nextLine();
    System.out.print("Enter amount of power item consumes: ");
    powerName = keyboard.nextLine();
      
    // Attempt to convert the quantity from a String to int.
    // 0 will be used if parse fails.
    try {
      quantity = Integer.parseInt(stringQuantity);
      powerConsume = Integer.parseInt(powerName);
    }
    catch (NumberFormatException exception) {
      System.out.println("ERROR: " + stringQuantity + " is not an integer!");
    }
    
    // Ensure the quantity is positive, no negative quantities - take the absolute value.
    quantity = Math.abs(quantity);
            
    // Add the grocery item to the arrays.
    electronicNamesParam[size] = inputName;
    electronicQuantitiesParam[size] = quantity;
    electronicPowerParam[size] = powerConsume;
   
    // Give feedback to user to let them know what is being addded.
    System.out.println("Added row #" + (size + 1) + ": " + electronicNamesParam[size] + " " + electronicQuantitiesParam[size] + " " + electronicPowerParam[size]);
   
    // Increase size after an item has been added.
    size++;
      
    // Adding done, return the new total amount of items stored in the arrays.
    return size;
  }

  /**
   * Deletes a electronic item from paralley arrays.
   * Asks the user which row to delete an item and shifts elements to the left.
   * Does nothing if the user enters a non-integer.
   * 
   * @param electronicNamesParam is the electronic item names
   * @param electronicQuantitiesParam is the quantities of the electrnic items
   * @param electronicPowerParam is the power consumption of the items
   * @param size is the size of the list
   * @param MAX_SIZE is the maximum number of electronics that can be stored
   * @return new size of the list
   */
  public static int delete(String[] electronicNamesParam, Integer[] electronicQuantitiesParam, Integer[] electronicPowerParam, int size, final int MAX_SIZE) {
    //Checks if list is empty, if it's not runs the delete function, else warns the user.
    if (size>0) {
      // Read input from the keyboard.
      Scanner reader = new Scanner(System.in);
      
      // Used to store the users choice which row to delete.
      // Stores the input as a String, then parses and stores in inputRow.
      String stringInputRow = "";
      int inputRow = 0;
      
      // The actual index of the element to delete.
      // Need to offset by 1 for user friendliness.
      // Row 1 for the user is index 0 in the array.
      int index = 0;
              
      // Get user input.
      System.out.print("Enter the row number of the item you wish to delete: ");
      
      // In case the input from the user cannot be parsed to an integer.
      try {
        
        // Get the user's choice for which row to delete.
        stringInputRow = reader.next();
        inputRow = Integer.parseInt(stringInputRow);
        
        // Check for negative integers, do nothing if negative.
        if (inputRow <= 0) {
          System.out.println("Error: The row number cannot be negative or zero.");
        }
        // Check for row number too big, do nothing if trying to delete a row that's too high.
        else if (inputRow > size + 1) {
          System.out.printf("Error: Entered row (%d) exceeds last row (%d).%n", inputRow, size + 1);
        } 
        else {
          // Convert row to an index in the array by subtracting 1.
          index = inputRow - 1;
          // Give feedback to user to let them know what is being deleted
          System.out.println("Deleting row #" + inputRow + ": " + electronicNamesParam[index] + " " + electronicQuantitiesParam[index] + " " + electronicPowerParam[index]);
          //Checks if the list is full AND if the row to be deleted is the last row. If so, replaces the deleted row with null instead of shifting.
          if (index==MAX_SIZE-1 && size==MAX_SIZE) {  
            //Replaces values with null instead of shifting them.
            electronicNamesParam[index] = null;
            electronicQuantitiesParam[index] = null;
            electronicPowerParam[index] = null;
          } else {
            // Delete item by shifting items on the right of the item to the left.
            // Start the shift at the specific index.
            for (int i = index; i < size; i++) {
              // Take the element on the right and store it on the left.
              electronicNamesParam[i] = electronicNamesParam[i + 1];
              electronicQuantitiesParam[i] = electronicQuantitiesParam[i + 1];
              electronicPowerParam[i] = electronicPowerParam[i+1];
            }
          }
          
          // Subtract one from the size (one item deleted from list)
          size--;
        }
      }
      catch (NumberFormatException nfe) {
        System.out.printf("Error: Entered row (%s) is not an integer.%n", stringInputRow);
      }
    } else {
      System.out.println("List is empty, Unable to delete any rows.");
    }
    // Return the new state of the number of items in the grocery list.
    return size;
  }
  /**
   * Displays the power usage of everything by multiplication.
   * Uses given values in the csv.
   * 
   * @param electronicNamesParam is the electronic item names
   * @param electronicQuantitiesParam is the quantities of the electrnic items
   * @param electronicPowerParam is the power consumption of the items
   * @param size is the size of the list
   */
  public static void display(String[] electronicNamesParam, Integer[] electronicQuantitiesParam, Integer[] electronicPowerParam, int size) {
    //detects if the list has any entries. Displays list if so, otherwise warns user.
    if (size>0) {
      //Initializes Power Consummage variable
      int power = 0;
      // Loop through each array add the amount of power the item consumes
      for (int i = 0; i < size; i++) {
        //Multiplies the amount of power consumed by an item to the item quantity
        power = power + electronicQuantitiesParam[i]*electronicPowerParam[i];
      }
      //Prints out the finished equation.
      System.out.printf("%d Power is being used.", power);
    } else {
      //Tells user the list is empty.
      System.out.println("No list detected/list is empty, please add or check if the correct file was opened.");
    }
  }
  /**
   * Displays the power usage of everything by multiplication.
   * Uses given values in the csv.
   * 
   * @param outputFilename is the file to write to
   * @param electronicNamesParam is the electronic item names
   * @param electronicQuantitiesParam is the quantities of the electrnic items
   * @param electronicPowerParam is the power consumption of the items
   * @param size is the size of the list
   */
  public static void writeToFile(String outputFilename, String[] electronicNamesParam,  Integer[] electronicQuantitiesParam, Integer[] electronicPowerParam, int size) {
    // File handle to write out to a file.
    File outFile = new File(outputFilename);
    
    // In case the file cannot be written to.
    try {
      // Does the actual writing.
      // Overwrite the file everytime.
      FileWriter electronicWriter = new FileWriter(outFile, false);
      
      // Output the headers.
      // Use String.format to use %n
      String headers = String.format("%s%n", "Name,Quantity,Power");
      electronicWriter.write(headers);
      
      // Go through the arrays and output the name and quantity with a comma in between.
      for (int i = 0; i < size; i++) {
        String outputLine = String.format("%s,%d,%d%n", electronicNamesParam[i], electronicQuantitiesParam[i], electronicPowerParam[i]);
        electronicWriter.write(outputLine);
      }
      
      // Writing done.
      electronicWriter.close();
      
      // Let the user know writing is done.
      System.out.printf("Wrote to file: %s%n", outFile.getAbsolutePath());
    }
    catch (IOException ioe) {
      System.out.printf("Error: Could not write to file: %s%n", outFile.getAbsolutePath());
    }
  }
}
