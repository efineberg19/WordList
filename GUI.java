import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*; //needed for ImageIcon, JFrame, JPanel, JLabel, etc.
import javax.swing.ImageIcon;
import javax.swing.event.*;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.Timer;
import java.util.ArrayList;
import javax.swing.JSlider;

/**
 * Creates a graphical user interface skeleton for running methods
 *  
 * @author Beth Fineberg
 * @version 2.0
 */
public class GUI extends JFrame implements ActionListener
{
    int selectedGame = 0;

    String[] gameNames;           //to be used in combobox for selections
    JComboBox<String> gameSelector;  //pull down menu to select method

    int fileSelected = 0;

    String[] wordFiles;           
    JComboBox<String> fileSelector;

    //Panels are set up to hold other things and can be placed within the MasterPanel
    JPanel masterPanel;         //panel for all components of the display
    JPanel topPanel;            //this panel holds the buttonPanel and instructionsPanel
    JPanel buttonPanel;         //panel for the submit and quit buttons 
    JPanel instructionsPanel;   //panel for JLabel with instructions to user
    JPanel ioPanel;             //panel to hold both input and report output
    JPanel reportPanel;         //panel for JLabel for reporting on the text analysis

    JButton quitButton, submitButton, colorButton, partyButton;
    JLabel instructions;        //label for instructions to user
    JTextArea report;           //area to show all results of analysis
    JScrollPane outputScroll;
    WordProcessor wp = new WordProcessor();

    Font font = new Font("Comic Sans MS", Font.BOLD, 12);
    Timer timer;
    Timer exit;
    boolean partyOn;

    String a = "";
    String b = "";
    String c = "";
    String d = "";
    String m = "";
    String f = "";
    String g = "";
    String h = "";
    String p = "";
    String j = "";
    String k = "";

    String fileName = "";

    JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 10, 2);

    /**
     * GUI constructor
     */
    public GUI(String title)
    {
        masterPanel = (JPanel) this.getContentPane();  //master panel IS the whole window
        masterPanel.setLayout(new BorderLayout());

        //creates slider to determine the number of words to generate
        slider.setMinorTickSpacing(1);
        slider.setMajorTickSpacing(10);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setLabelTable(slider.createStandardLabels(1));

        /*
         * SET UP BUTTON PANEL:
         */
        //SUBMIT button: user clicks when ready to analyze text
        submitButton = new JButton("Search");
        submitButton.addActionListener(this);
        submitButton.setActionCommand("submitButton"); 
        submitButton.setFont(font);

        //quit button
        quitButton = new JButton("Quit");
        quitButton.addActionListener(this);
        quitButton.setActionCommand("quitButton");
        quitButton.setFont(font);

        //color button
        colorButton = new JButton("Color");
        colorButton.addActionListener(this);
        colorButton.setActionCommand("colorButton");
        colorButton.setFont(font);

        //party button - allows colors to randomly flash
        partyButton = new JButton("Party");
        partyButton.addActionListener(this);
        partyButton.setActionCommand("partyButton");
        partyButton.setFont(font);

        buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(
                (int)(Math.random() * 256),
                (int)(Math.random() * 256),
                (int)(Math.random() * 256)));

        //creating timer
        timer = new Timer(25, this);
        timer.addActionListener(this);
        timer.setActionCommand("timer");
        timer.start();

        //create methods selector pull-down menu    
        String[] gameList ={"Home", "Google Search", "Percent Emotion"}; 

        gameSelector = new JComboBox<String>(gameList);  //create the pull-down menu object
        gameSelector.setSelectedIndex(0);                 //sets which one is selected by default
        gameSelector.addActionListener(this);             //this means we can react when a different one is selected
        gameSelector.setActionCommand("gameSelector");  //the command we reacdt to
        gameSelector.setFont(font);

        String[] fileList ={"Actress Names", "All Words", "Connectives", "Countries",
                "First Names", "Last Names", "Math Terms", "Movies from the Past 25 Years",
                "Poets", "Pokemon", "Proper Names", "Trump Tweets"};  //list of names of files to show to user

        fileSelector = new JComboBox<String>(fileList);  //create the pull-down menu object
        fileSelector.setSelectedIndex(0);                 //sets which one is selected by default
        fileSelector.addActionListener(this);             //this means we can react when a different one is selected
        fileSelector.setActionCommand("fileSelector");  //the command we reacdt to
        fileSelector.setFont(font);

        //INSTRUCTIONS PANEL
        instructionsPanel = new JPanel();
        instructionsPanel.setBackground(new Color(
                (int)(Math.random() * 256),
                (int)(Math.random() * 256),
                (int)(Math.random() * 256)));
        instructions = new JLabel("Select the Amount of words you would like to generate.");
        instructions.setFont(font);

        //set font and color
        instructions.setForeground(Color.WHITE);
        instructions.setFont(font); 
        instructionsPanel.add(instructions);
        instructionsPanel.add(slider);   //put the JLabel into the panel

        //top panel holds buttons and instructions
        topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(2,2));
        topPanel.add(buttonPanel);
        topPanel.add(instructionsPanel);

        //Here's where some instructions are and where report will be shown
        report = new JTextArea("Welcome! Use the drop down menu to pick a game to play. You must " 
            + "select a file before you can begin.", 50 , 30);  //30,30 is size of text area in chars
        report.setForeground(Color.GRAY);
        Font font2 = new Font("Comic Sans MS", Font.BOLD, 18);
        report.setFont(font2);
        report.setEditable(false);
        report.setLineWrap(true);
        report.setWrapStyleWord(true);
        //add the report to a scroll pane, so if it gets big, it will scroll:
        outputScroll = new JScrollPane(report);
        //add the scroll pane to the overall panel:
        reportPanel = new JPanel();
        reportPanel.add(outputScroll);


        //add the menu and three buttons to the button panel:
        buttonPanel.add(gameSelector);  //menu pull-down menu
        buttonPanel.add(fileSelector);
        buttonPanel.add(submitButton);  
        buttonPanel.add(quitButton);
        buttonPanel.add(colorButton);
        buttonPanel.add(partyButton);

        //make a panel to add entry boxes and report to:
        ioPanel = new JPanel();
        ioPanel.add(reportPanel);
        ioPanel.setBackground(Color.BLACK);
        /*
         * Put the two panels into the master panel, i.e. the whole window:
         */
        masterPanel.add(topPanel, BorderLayout.NORTH);
        masterPanel.add(ioPanel, BorderLayout.CENTER);

        /* 
         * Wrap-up: set title and size of window and set to be visible in the JFrame
         */
        this.setTitle(title);     
        this.setSize(860, 650);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    /**
     * Actionperformed: when a button is pressed, this is automatically called:
     */
    public void actionPerformed (ActionEvent e)
    {
        if(e.getActionCommand() == "fileSelector")
        {
            fileSelected = fileSelector.getSelectedIndex(); 
            switch(fileSelected)
            {
                //selects name of file and clears ArrayList of read words from previous files
                
                case 0:
                {
                    fileName = "actressnames.txt";
                    wp.clear();
                }
                break;

                case 1:
                {
                    fileName = "allwords.txt";
                    wp.clear();
                }
                break;

                case 2:
                {
                    fileName = "connectives.txt";
                    wp.clear();
                }
                break;

                case 3:
                {
                    fileName = "Countries.txt";
                    wp.clear();
                }
                break;

                case 4:
                {
                    fileName = "Firstnames.txt";
                    wp.clear();
                }
                break;

                case 5:
                {
                    fileName = "Lastnames.txt";
                    wp.clear();
                }
                break;

                case 6:
                {
                    fileName = "MathTerms.txt";
                    wp.clear();
                }
                break;

                case 7:
                {
                    fileName = "Movies25years.txt";
                    wp.clear();
                }
                break;

                case 8:
                {
                    fileName = "poets.txt";
                    wp.clear();
                }
                break;

                case 9:
                {
                    fileName = "pokemon.txt";
                    wp.clear();
                }
                break;

                case 10:
                {
                    fileName = "Propernames.txt";
                    wp.clear();
                }
                break;

                case 11:
                {
                    fileName = "trumptweets.txt";
                    wp.clear();
                }
                break;

                default:
                {
                }
                break;
            }
        }
        else if(e.getActionCommand() == "gameSelector")
        {
            selectedGame = gameSelector.getSelectedIndex();  //set the course number
            switch(selectedGame)
            {
                case 0: //home screen
                {
                    report.setText("Welcome! Use the drop down menu to pick a game to play.");

                }
                break;

                case 1:  //mode1: Google Search
                {
                    wp.read(fileName);
                    //selects specified number of words generated displays them with instructions

                    try
                    {
                        if (slider.getValue() == 1)
                        {
                            a = wp.getWords().get(wp.randomIndex());

                            report.setText("This will go to google first search result " 
                                + "for whatever the computer generated using the " 
                                + "\"I'm feeling lucky\" feature. I have no control "
                                + "over what you may to see. Please do not get me in trouble " 
                                + "if it is bad. \n\nPress submit to see results for: " + a + "\n\nSelect \"Google Search\" from the dropdown menu "
                                + "to play again. Use the slider to choose the amount of words to generate.");
                        }
                        else if (slider.getValue() == 2)
                        {
                            a = wp.getWords().get(wp.randomIndex());
                            b = wp.getWords().get(wp.randomIndex());

                            report.setText("This will go to google first search result " 
                                + "for whatever the computer generated using the " 
                                + "\"I'm feeling lucky\" feature. I have no control "
                                + "over what you may to see. Please do not get me in trouble " 
                                + "if it is bad. \n\nPress submit to see results for: " + a + " " +
                                b + "\n\nSelect \"Google Search\" from the dropdown menu "
                                + "to play again. Use the slider to choose the amount of words to generate.");
                        }
                        else if (slider.getValue() == 3)
                        {
                            a = wp.getWords().get(wp.randomIndex());
                            b = wp.getWords().get(wp.randomIndex());
                            c = wp.getWords().get(wp.randomIndex());

                            report.setText("This will go to google first search result " 
                                + "for whatever the computer generated using the " 
                                + "\"I'm feeling lucky\" feature. I have no control "
                                + "over what you may to see. Please do not get me in trouble " 
                                + "if it is bad. \n\nPress submit to see results for: " + a + " " +
                                b + " " + c + "\n\nSelect \"Google Search\" from the dropdown menu "
                                + "to play again. Use the slider to choose the amount of words to generate.");
                        }
                        else if (slider.getValue() == 4)
                        {
                            a = wp.getWords().get(wp.randomIndex());
                            b = wp.getWords().get(wp.randomIndex());
                            c = wp.getWords().get(wp.randomIndex());
                            d = wp.getWords().get(wp.randomIndex());

                            report.setText("This will go to google first search result " 
                                + "for whatever the computer generated using the " 
                                + "\"I'm feeling lucky\" feature. I have no control "
                                + "over what you may to see. Please do not get me in trouble " 
                                + "if it is bad. \n\nPress submit to see results for: " + a + " " +
                                b + " " + c + " " + d + "\n\nSelect \"Google Search\" from the dropdown menu "
                                + "to play again. Use the slider to choose the amount of words to generate.");
                        }
                        else if (slider.getValue() == 5)
                        {
                            a = wp.getWords().get(wp.randomIndex());
                            b = wp.getWords().get(wp.randomIndex());
                            c = wp.getWords().get(wp.randomIndex());
                            d = wp.getWords().get(wp.randomIndex());
                            m = wp.getWords().get(wp.randomIndex());

                            report.setText("This will go to google first search result " 
                                + "for whatever the computer generated using the " 
                                + "\"I'm feeling lucky\" feature. I have no control "
                                + "over what you may to see. Please do not get me in trouble " 
                                + "if it is bad. \n\nPress submit to see results for: " + a + " " +
                                b + " " + c + " " + d + " " + m + "\n\nSelect \"Google Search\" from the dropdown menu "
                                + "to play again. Use the slider to choose the amount of words to generate.");
                        }
                        else if (slider.getValue() == 6)
                        {
                            a = wp.getWords().get(wp.randomIndex());
                            b = wp.getWords().get(wp.randomIndex());
                            c = wp.getWords().get(wp.randomIndex());
                            d = wp.getWords().get(wp.randomIndex());
                            m = wp.getWords().get(wp.randomIndex());
                            f = wp.getWords().get(wp.randomIndex());

                            report.setText("This will go to google first search result " 
                                + "for whatever the computer generated using the " 
                                + "\"I'm feeling lucky\" feature. I have no control "
                                + "over what you may to see. Please do not get me in trouble " 
                                + "if it is bad. \n\nPress submit to see results for: " + a + " " +
                                b + " " + c + " " + d + " " + m + " " + f + "\n\nSelect \"Google Search\" from the dropdown menu "
                                + "to play again. Use the slider to choose the amount of words to generate.");
                        }
                        else if (slider.getValue() == 7)
                        {
                            a = wp.getWords().get(wp.randomIndex());
                            b = wp.getWords().get(wp.randomIndex());
                            c = wp.getWords().get(wp.randomIndex());
                            d = wp.getWords().get(wp.randomIndex());
                            m = wp.getWords().get(wp.randomIndex());
                            f = wp.getWords().get(wp.randomIndex());
                            g = wp.getWords().get(wp.randomIndex());

                            report.setText("This will go to google first search result " 
                                + "for whatever the computer generated using the " 
                                + "\"I'm feeling lucky\" feature. I have no control "
                                + "over what you may to see. Please do not get me in trouble " 
                                + "if it is bad. \n\nPress submit to see results for: " + a + " " +
                                b + " " + c + " " + d + " " + m + " " + f + " " + g + "\n\nSelect \"Google Search\" from the dropdown menu "
                                + "to play again. Use the slider to choose the amount of words to generate.");
                        }
                        else if (slider.getValue() == 8)
                        {
                            a = wp.getWords().get(wp.randomIndex());
                            b = wp.getWords().get(wp.randomIndex());
                            c = wp.getWords().get(wp.randomIndex());
                            d = wp.getWords().get(wp.randomIndex());
                            m = wp.getWords().get(wp.randomIndex());
                            f = wp.getWords().get(wp.randomIndex());
                            g = wp.getWords().get(wp.randomIndex());
                            h = wp.getWords().get(wp.randomIndex());

                            report.setText("This will go to google first search result " 
                                + "for whatever the computer generated using the " 
                                + "\"I'm feeling lucky\" feature. I have no control "
                                + "over what you may to see. Please do not get me in trouble " 
                                + "if it is bad. \n\nPress submit to see results for: " + a + " " +
                                b + " " + c + " " + d + " " + m + " " + f + " " + g + " " + h + "\n\nSelect \"Google Search\" from the dropdown menu "
                                + "to play again. Use the slider to choose the amount of words to generate.");
                        }
                        else if (slider.getValue() == 9)
                        {
                            a = wp.getWords().get(wp.randomIndex());
                            b = wp.getWords().get(wp.randomIndex());
                            c = wp.getWords().get(wp.randomIndex());
                            d = wp.getWords().get(wp.randomIndex());
                            m = wp.getWords().get(wp.randomIndex());
                            f = wp.getWords().get(wp.randomIndex());
                            g = wp.getWords().get(wp.randomIndex());
                            h = wp.getWords().get(wp.randomIndex());
                            p = wp.getWords().get(wp.randomIndex());

                            report.setText("This will go to google first search result " 
                                + "for whatever the computer generated using the " 
                                + "\"I'm feeling lucky\" feature. I have no control "
                                + "over what you may to see. Please do not get me in trouble " 
                                + "if it is bad. \n\nPress submit to see results for: " + a + " " +
                                b + " " + c + " " + d + " " + m + " " + f + " " + g + " " + h + " " + p + "\n\nSelect \"Google Search\" from the dropdown menu "
                                + "to play again. Use the slider to choose the amount of words to generate.");
                        }
                        else if (slider.getValue() == 10)
                        {
                            a = wp.getWords().get(wp.randomIndex());
                            b = wp.getWords().get(wp.randomIndex());
                            c = wp.getWords().get(wp.randomIndex());
                            d = wp.getWords().get(wp.randomIndex());
                            m = wp.getWords().get(wp.randomIndex());
                            f = wp.getWords().get(wp.randomIndex());
                            g = wp.getWords().get(wp.randomIndex());
                            h = wp.getWords().get(wp.randomIndex());
                            p = wp.getWords().get(wp.randomIndex());
                            j = wp.getWords().get(wp.randomIndex());

                            report.setText("This will go to google first search result " 
                                + "for whatever the computer generated using the " 
                                + "\"I'm feeling lucky\" feature. I have no control "
                                + "over what you may to see. Please do not get me in trouble " 
                                + "if it is bad. \n\nPress submit to see results for: " + a + " " +
                                b + " " + c + " " + d + " " + m + " " + f + " " + g + " " + h + " " + p + " " + j + "\n\nSelect \"Google Search\" from the dropdown menu "
                                + "to play again. Use the slider to choose the amount of words to generate.");
                        }
                        else if (slider.getValue() == 11)
                        {
                            a = wp.getWords().get(wp.randomIndex());
                            b = wp.getWords().get(wp.randomIndex());
                            c = wp.getWords().get(wp.randomIndex());
                            d = wp.getWords().get(wp.randomIndex());
                            m = wp.getWords().get(wp.randomIndex());
                            f = wp.getWords().get(wp.randomIndex());
                            g = wp.getWords().get(wp.randomIndex());
                            h = wp.getWords().get(wp.randomIndex());
                            p = wp.getWords().get(wp.randomIndex());
                            j = wp.getWords().get(wp.randomIndex());
                            k = wp.getWords().get(wp.randomIndex());

                            report.setText("This will go to google first search result " 
                                + "for whatever the computer generated using the " 
                                + "\"I'm feeling lucky\" feature. I have no control "
                                + "over what you may to see. Please do not get me in trouble " 
                                + "if it is bad. \n\nPress submit to see results for: " + a + " " +
                                b + " " + c + " " + d + " " + m + " " + f + " " + g + " " + h + " " + p + " " + j + " " + k + "\n\nSelect \"Google Search\" from the dropdown menu "
                                + "to play again. Use the slider to choose the amount of words to generate.");
                        }
                    }
                    catch(Exception m)
                    {
                        report.setText("You must pick a file before you can begin!");
                        m.printStackTrace();
                    }
                }
                break;

                case 2: //mode2: Mood Percentage
                {
                    wp.clear();
                    
                    //tells user what percent of file is a mood
                    try
                    {
                        wp.read(fileName);
                        String percentageMessage = wp.getMood();
                        
                        report.setText("Please select a file and I will tell you what percentage"
                            + " of the text is of a certain emotion. \n" + percentageMessage 
                            + "\n\n You must select \"Percent Emotion\" again to analyze another file.");
                    }
                    catch (Exception b)
                    {
                        System.out.println(b);
                    }
                }
                break;

                default:
                {
                    wp.read(fileName);
                }
                break;
            }
        }
        else if(e.getActionCommand().equals("submitButton"))
        {
            selectedGame = gameSelector.getSelectedIndex();  //set the course number

            switch(selectedGame)
            {
                case 0:
                break;

                case 1:
                {
                    //sends user to the "I'm Feeling Lucky" website
                    wp.googleSearch(a, b, c, d, m, f, g, h, p, j, k);
                }
                break;
            }
        }
        //QUIT BUTTON CLICKED:
        else if(e.getActionCommand().equals("quitButton")) 
        {
            //says bye when closing button is pressed
            JOptionPane.showMessageDialog(null, "Bye.");

            System.exit(0);
        }
        //generates a new color scheme when button is pressed
        else if(e.getActionCommand().equals("colorButton"))
        {
            buttonPanel.setBackground(new Color(
                    (int)(Math.random() * 256),
                    (int)(Math.random() * 256),
                    (int)(Math.random() * 256)).brighter());
            instructionsPanel.setBackground(new Color(
                    (int)(Math.random() * 256),
                    (int)(Math.random() * 256),
                    (int)(Math.random() * 256)).brighter());
            ioPanel.setBackground(new Color(
                    (int)(Math.random() * 256),
                    (int)(Math.random() * 256),
                    (int)(Math.random() * 256)).brighter());
        }
        //Party button clicked:
        else if(e.getActionCommand().equals("partyButton"))
        {
            partyOn = true;
        }
        //Causes color changes when party button clicked
        else if(e.getActionCommand().equals("timer"))
        {
            if (partyOn)
            {
                buttonPanel.setBackground(new Color(
                        (int)(Math.random() * 256),
                        (int)(Math.random() * 256),
                        (int)(Math.random() * 256)).brighter());

                instructionsPanel.setBackground(new Color(
                        (int)(Math.random() * 256),
                        (int)(Math.random() * 256),
                        (int)(Math.random() * 256)).brighter());

                ioPanel.setBackground(new Color(
                        (int)(Math.random() * 256),
                        (int)(Math.random() * 256),
                        (int)(Math.random() * 256)).brighter());

                report.setForeground(new Color(
                        (int)(Math.random() * 256),
                        (int)(Math.random() * 256),
                        (int)(Math.random() * 256)).brighter());

                instructions.setForeground(new Color(
                        (int)(Math.random() * 256),
                        (int)(Math.random() * 256),
                        (int)(Math.random() * 256)).brighter());
            }
        }
    }
}