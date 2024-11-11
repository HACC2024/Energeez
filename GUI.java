import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferDouble;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;


/**
 * A Class that models the GUI for the Energy game.
 *
 * @author Marcus Sosa
 */
public class GUI implements ActionListener {

    // Parts of the GUI
    //uses a try catch to add photos
    private BufferedImage green;
    private BufferedImage red;
    private BufferedImage yellow;
    private BufferedImage orange;
    private BufferedImage black;
    private BufferedImage window1;
    private BufferedImage window2;
    private BufferedImage window3;
    private BufferedImage window4;
    private JLabel window;
    private BufferedImage window5;
    private boolean counter2 = true;
    private JLabel computerEdit;
    private JLabel usage;
    private JLabel washingEdit;
    private JLabel TVEdit;
    private JLabel ACEdit;
    private JLabel deviceinfo;
    private JLabel energyText;
    private JLabel bar;
    private JFrame frame;
    private BackgroundPanel outside;
    private BackgroundPanel inside;
    private double currentEnergyUsage;
    private LinkedList<electronic> electronics;
    private JButton button;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton button5;
    private JButton button6;
    private JButton button7;
    private boolean counter = false;
    private Image background1;
    private Image background2;
    private Integer tally;
    /**
     * Constructs a GUI
     *
     * @param electronics A linked list that has the values of electronic objects stored inside.
     */
    public GUI(LinkedList<electronic> electronics) {
        //Initializes variables.
        this.electronics = electronics;
        this.currentEnergyUsage = getEnergyUsed(electronics);
        getIncome(electronics);


        //Creates inside JPanel
        inside = new BackgroundPanel(background2);

        //Creates images
        try {
            //reads background one and saves it to image.
            background1 = ImageIO.read(new File("images/backround energezz.png"));
        } catch (IOException ioe) {
            //Tells user that background 2 is missing then closes program.
            JOptionPane.showMessageDialog(null, "Background Image 1 was not found, closing program.", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        try {
            //reads background one and saves it to image.
            background2 = ImageIO.read(new File("images/inside1.png"));
        } catch (IOException ioe) {
            //Tells user that background 2 is missing then closes program.
            JOptionPane.showMessageDialog(null, "Background Image 2 was not found, closing program.", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        //Attempts to add the bar above the electric panel
        try {
            //reads bar images and saves it to a image object.
            green = ImageIO.read(new File("images/bar_green.png"));
            yellow = ImageIO.read(new File("images/bar_yellow.png"));
            orange = ImageIO.read(new File("images/bar_orange.png"));
            red = ImageIO.read(new File("images/bar_red.png"));
            black = ImageIO.read(new File("images/bar_black.png"));
        } catch (IOException ioe) {
            JOptionPane.showMessageDialog(null, "ERROR : Bar image files are missing.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        //Attempts to add the factory widows
        try {
            //Reads window images and saves it to a image object.
            window1 = ImageIO.read(new File("images/factorywindow.png"));
            window2 = ImageIO.read(new File("images/factorywindow2.png"));
            window3 = ImageIO.read(new File("images/factorywindow3.png"));
            window4 = ImageIO.read(new File("images/factorywindow4.png"));
            window5 = ImageIO.read(new File("images/factorywindow5.png"));
        } catch (IOException ioe) {
            JOptionPane.showMessageDialog(null, "ERROR : factorywindow files are missing.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        //Creates the JFrame for the window.
        frame = new JFrame();

        //Creates button 1, adds action listener to make it function.
        button = new JButton();
        button.addActionListener(this);

        //Creates button 2, adds action listener to make it function.
        button2 = new JButton();
        button2.addActionListener(this);

        //Creates button 3, adds action listener to make it function.
        button3 = new JButton();
        button3.addActionListener(this);

        //Creates button 4, adds action listener to make it function.
        button4 = new JButton();
        button4.addActionListener(this);

        //Creates button 5, adds action listener to make it function.
        button5 = new JButton();
        button5.addActionListener(this);
        //Makes button 5 invisible, as user is supposed to click the door.
        button5.setOpaque(false);
        button5.setContentAreaFilled(false);
        button5.setBorderPainted(false);

        //Creates button 6, adds action listen to make it function
        button6 = new JButton();
        button6.addActionListener(this);
        //makes button6 invisible
        button6.setOpaque(false);
        button6.setContentAreaFilled(false);
        button6.setBorderPainted(false);
        //creates button 7 and adds action listener
        button7 = new JButton();
        button7.addActionListener(this);
        button7.setOpaque(false);
        button7.setContentAreaFilled(false);
        button7.setBorderPainted(false);

        //Creates label for energy being used and income gained from it.
        energyText = new JLabel("Energy currently being used : " + currentEnergyUsage);

        //Sets up outside
        outside = new BackgroundPanel(background1);
        outside.setBorder(BorderFactory.createEmptyBorder(420, 490, 225, 455));
        outside.setLayout(new GridLayout(1, 1));
        outside.add(button5);

        //initializes JFrame
        frame.add(outside, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Click the door to get started!");
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }


    /**
     * Converts a file in .csv format into a Linked Stack of electronic objects.
     *
     * @param inFile is the .csv file being read from.
     * @return a linked stack of electronic objects created from the read file.
     */
    public static LinkedList<electronic> convertToLinkedList(File inFile) {
        // Instantiates a Linked stack
        LinkedList<electronic> electronics = new LinkedList<>();
        // Initializes two readers to read off of the file.
        Scanner Heathcliff;
        Scanner Catherine = null;

        // Temporary variable to store lines when retrieving line by line.
        String line;
        // Storage variables
        String inputName;
        double convertedEnergyUsage = 0.0;
        double convertedIncome = 0.0;
        int convertedAmount = 0;
        // Temporary variables to store doubles and integers before converting.
        String inputEnergyUsage;
        String inputIncome;
        String inputAmount;

        // If the filepath is invalid/file doesn't exist.
        try {
            // Connects Catherine to the file.
            Catherine = new Scanner(inFile);
        } catch (FileNotFoundException fnf) {
            System.out.printf("Error: Cannot open file: %s%n", inFile.getAbsolutePath());
            System.exit(1);
        }
        //Skips first line due to it being useless
        Catherine.nextLine();

        // Runs while file still has a next line.
        while (Catherine.hasNext()) {
            // reads next line.
            line = Catherine.nextLine();
            // Makes Heathcliff read off of the line read by Catherine
            Heathcliff = new Scanner(line);
            Heathcliff.useDelimiter(",");
            // Reads off the line and adds read values as variables.
            inputName = Heathcliff.next();
            inputEnergyUsage = Heathcliff.next();
            inputIncome = Heathcliff.next();
            inputAmount = Heathcliff.next();
            // attempts to convert Energy Usage into a double.
            // uses a try-catch just in-case it doesn't.
            try {
                convertedEnergyUsage = Double.parseDouble(inputEnergyUsage);
                // If successful, uses round() to prevent floating point errors.
                convertedEnergyUsage = Math.round(convertedEnergyUsage * 100.00) / 100.00;
            } catch (NumberFormatException nfe) {
                // tells user what failed to convert.
                System.out.printf("Error : %s could not be converted into a double.", inputEnergyUsage);
            }
            // attempts to convert income into a double.
            // uses a try-catch just in-case it doesn't.
            try {
                convertedIncome = Double.parseDouble(inputIncome);
                // If successful, uses round() to prevent floating point errors.
                convertedIncome = Math.round(convertedIncome * 100.00) / 100.00;
            } catch (NumberFormatException nfe) {
                // tells user what failed to convert.
                System.out.printf("Error : %s could not be converted into a double.", inputIncome);
            }
            // attempts to convert income into a double.
            // uses a try-catch just in-case it doesn't.
            try {
                convertedAmount = Integer.parseInt(inputAmount);
            } catch (NumberFormatException nfe) {
                // tells user what failed to convert.
                System.out.printf("Error : %s could not be converted into a int.", inputAmount);
            }
            // Adds electronic to the linked list.
            electronics.add(new electronic(inputName, convertedEnergyUsage, convertedIncome, convertedAmount));
        }
        //closes reader and returns converted linked list.
        Catherine.close();
        return electronics;
    }

    /**
     * Calculates energy consumed from a linked stack of electronics.
     *
     * @param electronics is the linked stack.
     * @return is the energy being used by all objects in the stack.
     */
    public static double getEnergyUsed(LinkedList<electronic> electronics) {
        //initializes return variable.
        double energyUsage = 0.0;
        //runs for all entries in electronics array
        for (electronic electronic : electronics) {
            //Adds the energy used by the electronic read to energyUsage
            energyUsage += electronic.getEnergyUsage() * electronic.getAmount();
        }
        //Makes sure that there's 3 decimal places, whilst removing floating point errors
        energyUsage = Math.round(energyUsage * 100.000) / 100.000;
        return energyUsage;
    }

    /**
     * Calculates the total income generated from a list of electronic devices.
     *
     * @param electronics A linked list of electronic objects from which the income will be calculated.
     * @return The total income generated from all electronic devices in the list.
     */
    public static double getIncome(LinkedList<electronic> electronics) {
        //initializes return variable.
        double income = 0.0;
        //runs for all entries in electronics array
        for (electronic electronic : electronics) {
            //adds the income gained from the electronic read to income.
            income += electronic.getIncome() * electronic.getAmount();
        }
        //makes sure there are 3 decimal places while getting rid of floating point errors
        income = Math.round(income * 100.000) / 100.000;
        return income;
    }

    //Launches the GUI.
    public static void main(String[] args) {
        // Ensures only 1 program argument, reminds user to use a .csv fle for it.
        if (args.length != 1) {
            System.out.printf("Error : 1 program argument expected, %d given instead.", args.length);
            System.out.println("Please give a .csv file for the program argument.\nClosing program...");
            System.exit(1);
        }
        // Creates a file object based off file provided from program argument.
        File inFile = new File(args[0]);
        // Initializes the GUI, uses a method to convert to linked list and sends to gui.
        new GUI(convertToLinkedList(inFile));
    }

    /**
     * Processes events and does an action based off what event happened.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        //Checks which button was pressed.
        if (e.getSource() == button) {
            //Creates a panel to open on button press.
                if (counter2) {
                    //Sets image up
                    try {
                        //sets tally to the correct electronic
                        tally = 0;
                        //Adds image to be displayed.
                        BufferedImage computerPanel = ImageIO.read(new File("images/dialougeComputer.png"));
                        computerEdit = new JLabel(new ImageIcon(computerPanel));
                        computerEdit.setBounds(0, 0, 400, 756);
                        //adds image to be displayed
                        BufferedImage Icon = ImageIO.read(new File("images/cumputor.png"));
                        JLabel icon = new JLabel(new ImageIcon(Icon));
                        icon.setBounds(165,50,150,150);
                        computerEdit.add(icon);
                        //adds device info
                        deviceinfo = new JLabel("<html><p style=\"width:100px\">" + "Amount of Energy a unit Consumes : " + electronics.get(tally).getEnergyUsage() +" Amount of Units: " + electronics.get(tally).getAmount() + "</p></html>");
                        deviceinfo.setBounds(17,15,150,150);
                        computerEdit.add(deviceinfo);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "ERROR : Computer UI unable to be loaded.", "Error", JOptionPane.ERROR_MESSAGE);
                        System.exit(0);
                    }
                    //adds JLabel  computerEdit to the Panel, revalidates to make it appear.
                    inside.add(computerEdit, BorderLayout.CENTER);
                    //adds value editing buttons
                    button6.setBounds(45,625,110,80);
                    computerEdit.add(button6);
                    button7.setBounds(160,625,115,80);
                    computerEdit.add(button7);
                    //Removes other buttons to ensure no bugs
                    inside.remove(button2);
                    inside.remove(button3);
                    inside.remove(button4);
                    //Revalidation
                    inside.revalidate();
                    inside.repaint();
                    //sets counter2 to false so the next part of the if statement can happen.
                    counter2 = false;
                } else {
                    //deletes computerEdit JLabel and all related information.
                    inside.remove(computerEdit);
                    //Readds the buttons.
                    //Creates button, makes opaque, and hides it on top of the buttons
                    button2.setBounds(378,364,170,57);
                    button2.setOpaque(false);
                    button2.setContentAreaFilled(false);
                    button2.setBorderPainted(false);
                    inside.add(button2);
                    //Creates button, makes opaque, and hides it on top of the buttons
                    button3.setBounds(378,530,170,57);
                    button3.setOpaque(false);
                    button3.setContentAreaFilled(false);
                    button3.setBorderPainted(false);
                    inside.add(button3);
                    //Creates button, makes opaque, and hides it on top of the buttons
                    button4.setBounds(378,602,170,57);
                    button4.setOpaque(false);
                    button4.setContentAreaFilled(false);
                    button4.setBorderPainted(false);
                    inside.add(button4);
                    //revalidates to make them reappear.
                    inside.revalidate();
                    inside.repaint();
                    //sets counter2 to true so the computerEdit gui gets reset and can be retriggered.
                    counter2=true;
                }
        } else if (e.getSource() == button2) {
            if (counter2) {
                //Sets image up
                try {
                    //sets tally to the correct electronic
                    tally = 1;
                    //Adds panel
                    BufferedImage WashingPanel = ImageIO.read(new File("images/dialougeWashing1.png"));
                    washingEdit = new JLabel(new ImageIcon(WashingPanel));
                    washingEdit.setBounds(0, 0, 400, 756);
                    //adds image to be displayed
                    BufferedImage Icon = ImageIO.read(new File("images/Washer.png"));
                    JLabel icon = new JLabel(new ImageIcon(Icon));
                    icon.setBounds(165,40,150,150);
                    washingEdit.add(icon);
                    //adds device info
                    deviceinfo = new JLabel("<html><p style=\"width:100px\">" + "Amount of Energy a unit Consumes : " + electronics.get(tally).getEnergyUsage() +" Amount of Units: " + electronics.get(tally).getAmount() + "</p></html>");
                    deviceinfo.setBounds(17,15,150,150);
                    washingEdit.add(deviceinfo);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "ERROR : Washing Machine UI unable to be loaded.", "Error", JOptionPane.ERROR_MESSAGE);
                    System.exit(0);
                }
                //adds JLabel washingEdit to the Panel, revalidates to make it appear.
                inside.add(washingEdit, BorderLayout.CENTER);
                //adds value editing buttons
                button6.setBounds(45,625,110,80);
                washingEdit.add(button6);
                button7.setBounds(160,625,115,80);
                washingEdit.add(button7);
                //Removes other buttons to ensure no bugs
                inside.remove(button);
                inside.remove(button3);
                inside.remove(button4);
                //Revalidation
                inside.revalidate();
                inside.repaint();
                //sets counter2 to false so the next part of the if statement can happen.
                counter2 = false;
            } else {
                //deletes washingEdit JLabel and all related information.
                inside.remove(washingEdit);
                //Re-adds the buttons.
                //Creates button, makes opaque, and hides it on top of the buttons
                button.setBounds(378,297,170,53);
                button.setOpaque(false);
                button.setContentAreaFilled(false);
                button.setBorderPainted(false);
                inside.add(button);
                //Creates button, makes opaque, and hides it on top of the buttons
                button3.setBounds(378,530,170,57);
                button3.setOpaque(false);
                button3.setContentAreaFilled(false);
                button3.setBorderPainted(false);
                inside.add(button3);
                //Creates button, makes opaque, and hides it on top of the buttons
                button4.setBounds(378,602,170,57);

                inside.add(button4);
                //revalidates to make them reappear.
                inside.revalidate();
                inside.repaint();
                //sets counter2 to true so the computerEdit gui gets reset and can be retriggered.
                counter2=true;
            }
        } else if (e.getSource() == button3) {
            if (counter2) {
                //Sets image up
                try {
                    //sets tally to the correct electronic
                    tally = 2;
                    //Adds panel
                    BufferedImage TVPanel = ImageIO.read(new File("images/dialougeWashing3.png"));
                    //adds labels to show panel and image
                    TVEdit = new JLabel(new ImageIcon(TVPanel));
                    TVEdit.setBounds(0, 0, 400, 756);
                    //adds image to be displayed
                    BufferedImage Icon = ImageIO.read(new File("images/TV.png"));
                    JLabel icon = new JLabel(new ImageIcon(Icon));
                    icon.setBounds(165,50,150,150);
                    TVEdit.add(icon);
                    //adds device info
                    deviceinfo = new JLabel("<html><p style=\"width:100px\">" + "Amount of Energy a unit Consumes : " + electronics.get(tally).getEnergyUsage() +" Amount of Units: " + electronics.get(tally).getAmount() + "</p></html>");
                    deviceinfo.setBounds(17,15,150,150);
                    TVEdit.add(deviceinfo);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "ERROR : TV UI unable to be loaded.", "Error", JOptionPane.ERROR_MESSAGE);
                    System.exit(0);
                }
                //adds JLabel washingEdit to the Panel, revalidates to make it appear.
                inside.add(TVEdit, BorderLayout.CENTER);
                //adds value editing buttons
                button6.setBounds(45,625,110,80);
                TVEdit.add(button6);
                button7.setBounds(160,625,115,80);
                TVEdit.add(button7);
                //Removes other buttons to ensure no bugs
                inside.remove(button);
                inside.remove(button2);
                inside.remove(button4);
                //Revalidation
                inside.revalidate();
                inside.repaint();
                //sets counter2 to false so the next part of the if statement can happen.
                counter2 = false;
            } else {
                //deletes washingEdit JLabel and all related information.
                inside.remove(TVEdit);
                //Re-adds the buttons.
                //Creates button, makes opaque, and hides it on top of the buttons
                button.setBounds(378,297,170,53);
                button.setOpaque(false);
                button.setContentAreaFilled(false);
                button.setBorderPainted(false);
                inside.add(button);
                //Creates button, makes opaque, and hides it on top of the buttons
                button2.setBounds(378,364,170,57);
                button2.setOpaque(false);
                button2.setContentAreaFilled(false);
                button2.setBorderPainted(false);
                inside.add(button2);
                //Creates button, makes opaque, and hides it on top of the buttons
                button4.setBounds(378,602,170,57);
                button4.setOpaque(false);
                button4.setContentAreaFilled(false);
                button4.setBorderPainted(false);
                inside.add(button4);
                //revalidates to make them reappear.
                inside.revalidate();
                inside.repaint();
                //sets counter2 to true so the computerEdit gui gets reset and can be retriggered.
                counter2=true;
            }
        } else if (e.getSource() == button4) {
            if (counter2) {
                //Sets image up
                try {
                    //sets tally to the correct electronic
                    tally = 3;
                    //Adds panel
                    BufferedImage ACPanel = ImageIO.read(new File("images/dialougeWashing4.1.png"));
                    ACEdit = new JLabel(new ImageIcon(ACPanel));
                    //adds image to be displayed
                    BufferedImage Icon = ImageIO.read(new File("images/AC Unit.png"));
                    JLabel icon = new JLabel(new ImageIcon(Icon));
                    icon.setBounds(165,50,150,150);
                    ACEdit.add(icon);
                    ACEdit.setBounds(0, 0, 400, 756);
                    //adds device info
                    deviceinfo = new JLabel("<html><p style=\"width:100px\">" + "Amount of Energy a unit Consumes : " + electronics.get(tally).getEnergyUsage() +" Amount of Units: " + electronics.get(tally).getAmount() + "</p></html>");
                    deviceinfo.setBounds(17,15,150,150);
                    ACEdit.add(deviceinfo);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "ERROR : AC UI unable to be loaded.", "Error", JOptionPane.ERROR_MESSAGE);
                    System.exit(0);
                }
                //adds JLabel washingEdit to the Panel, revalidates to make it appear.
                inside.add(ACEdit, BorderLayout.CENTER);
                //adds value editing buttons
                button6.setBounds(45,625,110,80);
                ACEdit.add(button6);
                button7.setBounds(160,625,115,80);
                ACEdit.add(button7);
                //Removes other buttons to ensure no bugs
                inside.remove(button);
                inside.remove(button2);
                inside.remove(button3);
                //Revalidation
                inside.revalidate();
                inside.repaint();
                //sets counter2 to false so the next part of the if statement can happen.
                counter2 = false;
            } else {
                //deletes washingEdit JLabel and all related information.
                inside.remove(ACEdit);
                //Re-adds the buttons.
                //Creates button, makes opaque, and hides it on top of the buttons
                button.setBounds(378,297,170,53);
                button.setOpaque(false);
                button.setContentAreaFilled(false);
                button.setBorderPainted(false);
                inside.add(button);
                //Creates button, makes opaque, and hides it on top of the buttons
                button3.setBounds(378,530,170,57);
                button3.setOpaque(false);
                button3.setContentAreaFilled(false);
                button3.setBorderPainted(false);
                inside.add(button3);
                //Creates button, makes opaque, and hides it on top of the buttons
                button2.setBounds(378,364,170,57);
                button2.setOpaque(false);
                button2.setContentAreaFilled(false);
                button2.setBorderPainted(false);
                inside.add(button2);
                //revalidates to make them reappear.
                inside.revalidate();
                inside.repaint();
                //sets counter2 to true so the computerEdit gui gets reset and can be retriggered.
                counter2=true;
            }
        } else if (e.getSource() == button5) {
            //Changes background on button press.
            if (counter) {
                frame.setTitle("Outside the Energy Shack.");
                frame.remove(inside);
                frame.add(outside);
                frame.revalidate();
                frame.repaint();
                //switches counter for next time button is pressed
                counter = false;
            } else {
                //sets inside background
                //Creates inside appearances
                inside.setLayout(null);
                inside.setImage(background2);
                //Creates button, makes opaque, and hides it on top of the buttons
                button.setBounds(378,297,170,53);
                button.setOpaque(false);
                button.setContentAreaFilled(false);
                button.setBorderPainted(false);
                inside.add(button);
                //Creates button, makes opaque, and hides it on top of the buttons
                button2.setBounds(378,364,170,57);
                button2.setOpaque(false);
                button2.setContentAreaFilled(false);
                button2.setBorderPainted(false);
                inside.add(button2);
                //Creates button, makes opaque, and hides it on top of the buttons
                button3.setBounds(378,530,170,57);
                button3.setOpaque(false);
                button3.setContentAreaFilled(false);
                button3.setBorderPainted(false);
                inside.add(button3);
                //Creates button, makes opaque, and hides it on top of the buttons
                button4.setBounds(378,602,170,57);
                button4.setOpaque(false);
                button4.setContentAreaFilled(false);
                button4.setBorderPainted(false);
                inside.add(button4);
                //Creates the image of the window based off background.
                window = new JLabel(new ImageIcon(window1));
                window.setBounds(600,0,455,755);
                inside.add(window);
                //Creates the bar.
                //uses a try catch incase bar image is missing
                try {
                    BufferedImage barImage = ImageIO.read(new File("images/bar_green.png"));
                    bar = new JLabel(new ImageIcon(barImage));
                    bar.setBounds(350,30,50,117);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "ERROR : Could not find Green bar image.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                //adds to inside JPanel
                inside.add(bar);
                //Adds text to show amount of power being consumed
                usage = new JLabel("Power being used : " + getEnergyUsed(electronics));
                usage.setBounds(400,30,150,50);
                inside.add(usage);
//                //Adds button 5 to go back outside.
//                inside.add(button5);
                //Removes panel to start inside procedure
                frame.remove(outside);
                //adds the inside
                frame.add(inside);
                frame.setTitle("Inside the Energy Shack.");
                frame.revalidate();
                frame.repaint();
                //switches counter for next time button is pressed
                counter = true;
            }
        } else if (e.getSource() == button6) {
            //Makes a variable to store user input
            int convertedAmount;
            //Asks user for what amount of computers they want to power
            String addedAmount = JOptionPane.showInputDialog(null, "Choose how much Devices you wish to power.", "Device Power Panel", JOptionPane.PLAIN_MESSAGE);
            //Attempts to convert the string of user's input to an integer to store.
            try {
                convertedAmount = Integer.parseInt(addedAmount);
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null, "ERROR : " + addedAmount + " could not be converted into a number or was too big to be stored." +
                        "\n Computer amount has not been changed.", "Error", JOptionPane.ERROR_MESSAGE);
                convertedAmount = electronics.get(tally).getAmount();
            }
            //changes device amount to amount user selected.
            electronics.get(tally).setAmount(convertedAmount);
            //Updates Energy Usage and Income
            currentEnergyUsage = getEnergyUsed(electronics);
            energyText.setText("Energy currently being used : " + currentEnergyUsage);
            //Updates the bar.
            barupdate(getEnergyUsed(electronics));
        } else if (e.getSource() == button7) {
            //Makes a variable to store user input
            double convertedAmount = electronics.get(tally).getAmount();
            //Asks user for what amount of computers they want to power
            String addedAmount = JOptionPane.showInputDialog(null, "Choose how much energy you wish to give to each device.", "Device Power Panel", JOptionPane.PLAIN_MESSAGE);
            //Attempts to convert the string of user's input to an integer to store.
            try {
                convertedAmount = Math.round(Double.parseDouble(addedAmount)*100.00/100.00);
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null, "ERROR : " + addedAmount + " could not be converted into a number or was too big to be stored." +
                        "\n Computer amount has not been changed.", "Error", JOptionPane.ERROR_MESSAGE);
                convertedAmount = Math.round(electronics.get(tally).getEnergyUsage()*100.00/100.00);
            }
            //changes device amount to amount user selected.
            electronics.get(tally).setEnergyUsage(convertedAmount);
            //Updates energy usage and Income
            currentEnergyUsage = getEnergyUsed(electronics);
            energyText.setText("Energy currently being used : " + currentEnergyUsage);
            //updates the bar.
            barupdate(getEnergyUsed(electronics));
        }
    }

    /**
     * Updates the bar displayed inside the energy shack.
     *
     * @param energy is amount of energy being used.
     */
    public void barupdate(double energy) {
        if (energy >= 0) {
            bar.setIcon(new ImageIcon(green));
            window.setIcon(new ImageIcon(window1));
            if (energy >= 250) {
                bar.setIcon(new ImageIcon(yellow));
                window.setIcon(new ImageIcon(window2));
                if (energy >= 500) {
                    bar.setIcon(new ImageIcon(orange));
                    window.setIcon(new ImageIcon(window3));
                    if (energy >= 750) {
                        bar.setIcon(new ImageIcon(red));
                        window.setIcon(new ImageIcon(window4));
                        if (energy >= 1000) {
                            bar.setIcon(new ImageIcon(black));
                            window.setIcon(new ImageIcon(window5));
                        }
                    }
                }
            }
        }
        deviceinfo.setText("<html><p style=\"width:100px\">" + "Amount of Energy a unit Consumes : " + electronics.get(tally).getEnergyUsage() +" Amount of Units: " + electronics.get(tally).getAmount() + "</p></html>");
        usage.setText("Power being used : " + getEnergyUsed(electronics));
    }
}
