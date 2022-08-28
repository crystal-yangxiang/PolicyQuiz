package PerfectPolicyQuiz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.Graphics;

public class UIComponentLibrary
{
    public static JButton CreateJButton(String name, int sizeX, int sizeY, int posX, int posY, ActionListener listener, Frame frame, SpringLayout layout)
    {
        JButton myButton = new JButton(name); //Creates button and sets text
        myButton.setFont(new Font("Arial", Font.BOLD, 11));
        myButton.addActionListener(listener); //Adds action listener to register click events
        myButton.setPreferredSize(new Dimension(sizeX,sizeY)); //Sets button size
        layout.putConstraint(SpringLayout.WEST,myButton,posX, SpringLayout.WEST,  frame); //Sets button's X Coordinates
        layout.putConstraint(SpringLayout.NORTH,myButton,posY, SpringLayout.NORTH,  frame); //Sets button's Y Coordinates
        frame.add(myButton); //Adds button to frame
        return myButton; //Returns completed button to caller.
    }

    public static JTextField CreateAJTextField(int size, int posX, int posY, Frame frame, SpringLayout layout)
    {
        JTextField myTextField = new JTextField(size); //Creates JTextField and sets size
        layout.putConstraint(SpringLayout.WEST,myTextField,posX, SpringLayout.WEST,  frame);//Sets text field's X Coordinates
        layout.putConstraint(SpringLayout.NORTH,myTextField,posY, SpringLayout.NORTH,  frame);//Sets text field's Y Coordinates
        frame.add(myTextField); //Adds text field to frame
        return  myTextField; //Returns completed text field to caller
    }
    public static JTextArea CreateAJTextArea(int sizeX,int sizeY, int posX, int posY, Frame frame, SpringLayout layout)
    {
        JTextArea myTextArea = new JTextArea(sizeX,sizeY); //Creates JTextField and sets size
        layout.putConstraint(SpringLayout.WEST,myTextArea,posX, SpringLayout.WEST,  frame);//Sets text field's X Coordinates
        layout.putConstraint(SpringLayout.NORTH,myTextArea,posY, SpringLayout.NORTH,  frame);//Sets text field's Y Coordinates
        myTextArea.setWrapStyleWord(true);
        myTextArea.setLineWrap(true);
        //myTextArea.setText();
        frame.add(myTextArea); //Adds text field to frame

        return  myTextArea; //Returns completed text field to caller
    }
    public static JLabel CreateAJLabel(String text, int posX, int posY, Frame frame, SpringLayout layout)
    {
        JLabel myLabel = new JLabel(text); //Creates label and sets text
        layout.putConstraint(SpringLayout.WEST,myLabel,posX, SpringLayout.WEST,  frame);//Sets labels's X Coordinates
        layout.putConstraint(SpringLayout.NORTH,myLabel,posY, SpringLayout.NORTH,  frame);//Sets label's Y Coordinates
        frame.add(myLabel); // Adds label to frame
        return myLabel; //Returns completed label to caller.
    }

    public static void CreateRectangle(int x, int y, int width, int height, Color color, Frame frame, SpringLayout myLayout)
    {
        LayoutManager tempLayout = frame.getLayout();
        JLabel myLabel = new JLabel(""); //Creates label and sets text
        myLayout.putConstraint(SpringLayout.WEST,myLabel,x, SpringLayout.WEST, frame);//Sets labels's X Coordinates
        myLayout.putConstraint(SpringLayout.NORTH,myLabel,y, SpringLayout.NORTH,  frame);//Sets label's Y Coordinates

        myLabel.setPreferredSize(new Dimension(width,height));
        myLabel.setOpaque(true);
        myLabel.setBackground(color);
        frame.add(myLabel); // Adds label to frame
    }
}
