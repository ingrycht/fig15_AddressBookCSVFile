//create GUI to pass the data information 
//the order of imports is important to get the information
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Dimension;//represent a hight ad width

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.Formatter;//(used to create csv file)
import java.util.Scanner;//scanner is used to read the file



public class AddressGUI extends JFrame{
    //create and array to put the label, textField and Buttons together
    private String[] labelsText = {"Firstname", "Lastname", "Address 1", "Address 2",
        "Address 3", "PhoneNumber", "Email"};
    private JLabel[] labels = new JLabel[labelsText.length];
    private JTextField[] fields = new JTextField[labelsText.length];
    
    //create navButton for next/previus/first/last
    private String[] navButtonsText = {"First", "Previus", "Next", "Last"};
    //create 2 arrays for buttons
    private JButton[] navButtons = new JButton[navButtonsText.length];
    
    //create the buttons for Operations buttons = add record/update record/ delete/ save as/ clear/ quit
    private String[] opsButtonsText = {"Add Record", "Update Record", "Delete Record",
            "Clear", "Quit", "Save", "Save As...", "Open File...", "Delete All"};
    private JButton[] opsButtons = new JButton[opsButtonsText.length];
    
    //create the JPanels
    private JPanel navPanel = new JPanel();
    private JPanel displayPanel = new JPanel();
    private JPanel opsPanel = new JPanel();
    private JTextField indexTextField, maxIndexTextField;
    private JLabel ofLabel;
    
    //create the fileChooser
    private JFileChooser fileChooser = new JFileChooser();
    private File file;
    
    //create a boolean variable to ask the user to save the file before close the window
    private boolean dataChanged;//false by default
    
    //create ArrayList instance variable
    private ArrayList<Address> addressList = new ArrayList<>(); 
    
    //create instance variable to handle the events in the navButtons
    private int currentIndex = 0;
    
    //create the constructor passing the data created in Test.java
    public AddressGUI(ArrayList list){
        //passing the data created in Test.java
        addressList = list;
        
        //create ans instance of the ButtonsHandlers
        NavButtonHandler navHandler = new NavButtonHandler();
        
        //create 3 panels with background color to make it visible temporary
        
        //create for loop to get the text on navButtonsText to create the buttons
        for (int lcv = 0; lcv < navButtons.length; lcv++){
            navButtons[lcv] = new JButton(navButtonsText[lcv]);
            //add the navHandler to the ActionListener
            navButtons[lcv].addActionListener(navHandler);
            navPanel.add(navButtons[lcv]);
            if(lcv == 1){
                //it will hold the number zero
                indexTextField = new JTextField(4);
                navPanel.add(indexTextField);
                ofLabel = new JLabel(" of ");
                navPanel.add(ofLabel);
                maxIndexTextField = new JTextField(4);
                maxIndexTextField.setEditable(false);
                navPanel.add(maxIndexTextField);
            }//end if
        }//end for about navButtons
        //navPanel.setBackground(Color.red);
        //BorderLayout.NORTH = place a component at the top of the container with 15 pixels
        add(navPanel, BorderLayout.NORTH);
        
        //set the layout to displayPanel 7 lines with 2 columns and with 5 gaps between them
        displayPanel.setLayout(new GridLayout(7, 2, 5, 5));
        //for loop to get the text on the displayPanel
        for(int lcv = 0; lcv < labelsText.length; lcv++){
            labels[lcv] = new JLabel(labelsText[lcv]);
            displayPanel.add(labels[lcv]);
            fields[lcv] = new JTextField(20);
            displayPanel.add(fields[lcv]);
        }//end for about navButtonsText
        //all the remain spaces for the center
        //displayPanel.setBackground(Color.GREEN);
        add(displayPanel, BorderLayout.CENTER);
       
        //create a gridlayout with 3 rows, 3 columns and 5, 5 pixels gap
        opsPanel.setLayout(new GridLayout(3, 3, 5, 5));
        
        //create handle to the opsButtons
        OpsButtonHandler opsHandler = new OpsButtonHandler();
        //create for loop to get the text on opsButtonsText to create the buttons
        for (int lcv = 0; lcv < opsButtonsText.length; lcv++){
            opsButtons[lcv] = new JButton(opsButtonsText[lcv]);
            //add actionListener to handle the buttons
            opsButtons[lcv].addActionListener(opsHandler);
            opsPanel.add(opsButtons[lcv]);
        }//end for about navButtons
        //BorderLayout.SOUTH = place a component at the botton of the container with 15 pixels
        //opsPanel.setBackground(Color.MAGENTA);
        add(opsPanel, BorderLayout.SOUTH);
        
        //set the size, exit on close and visible
        setBounds(100, 25, 425, 400);
        setMinimumSize(new Dimension(425, 400));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        //refer to the index 0 on the ArrayList
        displayRecord(0);
        
        //add inner class to ask the user to save the data before close
        this.addWindowListener(//create inner class for windowAdapter class
                new WindowAdapter(){
                    public void windowClosing(WindowEvent e){
                        int result;
                        if (dataChanged == true){
                            result = JOptionPane.showConfirmDialog(AddressGUI.this, 
                                    "do you want to save your file?",
                                    "Save Data", JOptionPane.YES_NO_CANCEL_OPTION);
                            if(result == JOptionPane.YES_OPTION)
                                if(file == null){
                                    opsHandler.saveAs();//method saveAs created bellow clearTextBoxes method
                                    System.exit(0);
                                }
                                else
                                    opsHandler.save();
                            else if (result == JOptionPane.NO_OPTION)
                                setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                            else if (result == JOptionPane.CANCEL_OPTION)
                                setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                        }//end if
                    }//end method windowClosing
                }//end inner class WindowAdapter
        );
    }//end constructor
    //display all the records
    public void displayRecord(int index){
        //index will be the index number from the ArrayList
        //create arrayList, pass it to the GUI
        //to prevent error when press the Previus button with index 0
        if (index < 0)
            index = 0;
        //to prevent error when press the Next button with last index of the array
        else if (index >= addressList.size())
            index = addressList.size()-1;
        if (index < 0)
            return;//leave method if there is no record to display
        
        Address record = addressList.get(index);
        fields[0].setText(record.getFirstName());
        fields[1].setText(record.getLastName());
        fields[2].setText(record.getAddress1());
        fields[3].setText(record.getAddress2());
        fields[4].setText(record.getAddress3());
        fields[5].setText(record.getPhone());
        fields[6].setText(record.getEmail());
        //populate the maxField
        int max = addressList.size()-1;
        //pass empty String + max
        maxIndexTextField.setText("" + max);
        //populate the index Field
        indexTextField.setText("" + index);
        //it is to handle the currentIndex typed from users
        currentIndex = index;
    }//end method displayRecord
    //create the inner class to get the action events
    //start with the navButtons
    private class NavButtonHandler implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent event){
            //if the user select the "First" Button
            if(event.getSource() == navButtons[0])
                displayRecord(0);
            //if the user select the "Previus" Button
            else if(event.getSource() == navButtons[1])
                displayRecord(--currentIndex);
            //if the user select the "Next" Button
            else if(event.getSource() == navButtons[2])
                displayRecord(++currentIndex);
            //if the user select the "Last" Button
            else if(event.getSource() == navButtons[3])
                displayRecord(addressList.size()-1);
                
        }//end method actionPerformed
    }//end inner class NavButtonHandler

    //create the inner class to get the action events
    //deal with the Botton buttons
    public class OpsButtonHandler implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent event){
            //System.out.println("Hello");only for test all the buttons
            //add new record
            if (event.getSource() == opsButtons[0]){
                addRecord();//method addRecord created bellow
            }//end if for ADD NEW RECORD
            //update Record
            else if (event.getSource() == opsButtons[1]){
                updateRecord();//method updateRecord created bellow addRecord method
            }//ed if for UPDATE BUTTON
            //delete record
            else if (event.getSource() == opsButtons[2]){
                deleteRecord();//method deleteRecord created bellow updateRecord method
            }//end if for clear button
            //Clear Button
            else if (event.getSource() == opsButtons[3]){
                clearTextBoxes();//method clearTextBoxes created bellow deleteRecord method
            }//end if for Clear Button
            //Quit Button
            else if (event.getSource() == opsButtons[4]){
                System.exit(0);//exit the program
            }//end if for Quit Button
            //Save Button
            else if (event.getSource() == opsButtons[5]){
                if(file == null)
                    saveAs();//method save created bellow clearTextBoxes method
                else
                    save();
            }//end if for SAVE Button
            //Save As Button
            else if (event.getSource() == opsButtons[6]){
                saveAs();//method saveAs created bellow save method
            }//end if for SAVE Button
            //Open File Button
            else if (event.getSource() == opsButtons[7]){
                openFile();//method openFile created bellow saveAs method
            }//end if for Open File Button
            //Delete All Button
            else if (event.getSource() == opsButtons[8]){
                addressList.clear();
                setTitle("");
                maxIndexTextField.setText("0");
                clearTextBoxes();
            }//end if for Delete All Button
            
            
        }//end method actionPerformed
        
        //create method to ADD NEW RECORD BUTTON
        public void addRecord(){
            //create Address object
            Address add = new Address();
            //set the new record
            add.setFirstName(fields[0].getText());
            add.setLastName(fields[1].getText());
            add.setAddress1(fields[2].getText());
            add.setAddress2(fields[3].getText());
            add.setAddress3(fields[4].getText());
            add.setPhone(fields[5].getText());
            add.setEmail(fields[6].getText());
            //after create the set, need to PASS it into the ArrayList
            addressList.add(add);
            //display the new record
            displayRecord(addressList.size() - 1);
            updateTitle();
        }//end method addRecord
        
        //create method to UPDATE THE RECORD
        public void updateRecord(){
            //taking an exist record and change the value for it
            Address add = addressList.get(currentIndex);
            
            add.setFirstName(fields[0].getText());
            add.setLastName(fields[1].getText());
            add.setAddress1(fields[2].getText());
            add.setAddress2(fields[3].getText());
            add.setAddress3(fields[4].getText());
            add.setPhone(fields[5].getText());
            add.setEmail(fields[6].getText());
            //doesnt need to display the record because it already exist
            updateTitle();
        }//end method updateRecord
        
        //create the method to DELETE THE RECORD
        public void deleteRecord(){
            //get the index number to delete it
            //addressList.size in that case is not working correctly because there is no specific size
            //in that case we used isEmpty method wll work properly
            if(!addressList.isEmpty()){
                addressList.remove(currentIndex);
                displayRecord(currentIndex);
            }
            //clear the text boxes if the all the records was deleted
            if(addressList.isEmpty())
                clearTextBoxes();
            updateTitle();
        }//end method deleteRecord
        
        //create the method to CLEAR THE TEXT BOX
        public void clearTextBoxes(){
            //clear fields (Text Box)
            for (int lcv = 0; lcv < fields.length; lcv++){
                fields[lcv].setText("");
            }//end for loop
        }//end method clearTextBoxes
        
        //create the method to SAVE the FILE
        //here is the code to save the file even frim save as method
        public void save(){
            try{
                //make the file ready to write
                FileWriter fw = new FileWriter(file);
                Formatter output = new Formatter(fw);
                //prepar to save the file
                for (Address a : addressList){
                    output.format("%s, %s, %s, %s, %s, %s, %s\n", a.getFirstName(), a.getLastName(),
                    a.getAddress1(), a.getAddress2(), a.getAddress3(), a.getPhone(), a.getEmail());
                }
                fw.close();
                output.flush();
                output.close();
                setTitle(file.getPath());
                dataChanged = true;
            }/*end try block*/catch(IOException ioe){
                JOptionPane.showMessageDialog(AddressGUI.this, "Cannot Open File");
            }//end catch block
        }//end method save
        
        //create the method to SAVE AS the FILE
        public void saveAs(){
            //create variable to open the window to save the file
            //display the fileChooser
            int result = fileChooser.showSaveDialog(AddressGUI.this);
            
            //check if the user has create the file/select the file name
            //approve_Option check if the user press the save button
            if(result == JFileChooser.APPROVE_OPTION){
                file = fileChooser.getSelectedFile();
                save();
            }//end if approve_option
            
        }//end method saveAs
        
        //create Open File method
        public void openFile(){
            //create variable to open the window to save the file
            //display the fileChooser
            int result = fileChooser.showOpenDialog(AddressGUI.this);
            
            //check if the user has create the file/select the file name
            //approve_Option check if the user press the save button
            if(result == JFileChooser.APPROVE_OPTION){
                file = fileChooser.getSelectedFile();
            
            }//end if approve_option
            try{
                FileReader fr = new FileReader(file);
                //create scanner object to read the file
                Scanner input = new Scanner(fr);
                //clear the arraylist to open the file
                addressList.clear();
                while(input.hasNext()){
                    //get the intire line of the record
                    String record = input.nextLine();
                    //split that in indivual records
                    String[] values = record.split(", ");
                    //create a new Address object wit the vaues in correct place
                    Address rec = new Address();
                    rec.setFirstName(values[0]);
                    rec.setLastName(values[1]);
                    rec.setAddress1(values[2]);
                    rec.setAddress2(values[3]);
                    rec.setAddress3(values[4]);
                    rec.setPhone(values[5]);
                    rec.setEmail(values[6]);
                    
                    //add it to arrayList
                    addressList.add(rec);
                    //start to display it from index 0
                    
                }//end while
                displayRecord(0);
                fr.close();
                input.close();
                setTitle(file.getPath());
                dataChanged = true;
            }catch(FileNotFoundException nfe){
                
            }//end catch FileNotFoundException
            catch(IOException ioe){
            }//end catch IOException
        }//end method openFile
        
        //method to title the windows when it is not saved yet
        private void updateTitle(){
            String title;
            if (file!= null)
                title = file.getPath() + "[not saved]";
            else 
                title = "[not saved]";
            
            setTitle(title);
            dataChanged = true;
        }//end method updateTitle
    }//end inner class OpsButtonHandler
}//end class AddressGUI