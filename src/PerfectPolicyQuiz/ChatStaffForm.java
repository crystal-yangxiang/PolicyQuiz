package PerfectPolicyQuiz;
/****************************************************************
 PROGRAM:   program name
 AUTHOR:    your name
 LOGON ID:  Z??????  (your Z number here)
 DUE DATE:  due date of program

 FUNCTION:  a short paragraph stating the purpose of the
 program.

 INPUT:     location of input, i.e.  standard input, a file on
 disk

 OUTPUT:    location and type of output, i.e.  a report
 containing a detail record for each city processed
 containing city id, Celsius temperature, Fahrenheit
 temperature and wind chill temperature.

 NOTES:     any relevant information that would be of
 additional help to someone looking at the program.
 ****************************************************************/

//CHAT RELATED ---------------------------

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
//----------------------------------------

public class ChatStaffForm extends Frame implements ActionListener, WindowListener
{
    //CHAT RELATED ---------------------------
    private Socket socket = null;
    private DataInputStream console = null;
    private DataOutputStream streamOut = null;
    private ChatStaffFormThread chatStaffFormThread = null;
    private String serverName = "localhost";
    private int serverPort = 4444;

    //----------------------------------------

    JLabel lblTitle, lblStaffName, lblEnterAnswer, lblTopic, lblQuestion,
            lbl1, lbl2, lbl3, lbl4, lbl5, lblAnswer,lblMessage;
    JButton btnSubmit, btnExit, btnConnect;
    JTextField txtStaffName, txtTopic, txt1, txt2, txt3, txt4, txt5, txtAnswer;
    JTextArea txtQuestion;

    SpringLayout myLayout = new SpringLayout();

    String correctAns = "0";


    public static void main(String[] args)
    {
        Frame myFrame = new ChatStaffForm();
        myFrame.setSize(400, 450);
        myFrame.setLocation(830, 160);
        myFrame.setResizable(false);
        myFrame.setVisible(true);
    }

    public ChatStaffForm()
    {
        //Frame myFrame = new ChatStaffForm();
        this.setSize(450, 450);
        this.setLocation(830, 160);
        this.setResizable(false);
        this.setVisible(true);

        setTitle("Policy Question");
        setBackground(Color.LIGHT_GRAY);

        setLayout(myLayout);

        SetupLabel();
        SetupTextField();
        SetupButton();
        SetupTextArea();

        this.addWindowListener(this);

        //CHAT RELATED ---------------------------
        getParameters();
        //----------------------------------------
    }

    private void SetupLabel()
    {
        lblTitle = UIComponentLibrary.CreateAJLabel("Policy Question", 0, 0, this, myLayout);
        lblTitle.setOpaque(true);
        lblTitle.setPreferredSize(new Dimension(400, 30));
        lblTitle.setBackground(Color.pink);
        lblTitle.setForeground(Color.white);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));

        lblStaffName = UIComponentLibrary.CreateAJLabel("Staff Name: ", 8, 40, this, myLayout);
        lblStaffName.setFont(new Font("Arial", Font.PLAIN, 12));

        lblEnterAnswer = UIComponentLibrary.CreateAJLabel("Enter your answer and click Submit", 8, 65, this, myLayout);
        lblEnterAnswer.setOpaque(true);
        lblEnterAnswer.setPreferredSize(new Dimension(367,20));
        lblEnterAnswer.setBackground(Color.pink);
        lblEnterAnswer.setForeground(Color.white);

        lblTopic = UIComponentLibrary.CreateAJLabel("Topic: ", 10, 90, this, myLayout);
        lblTopic.setFont(new Font("Arial", Font.PLAIN, 12));

        lblQuestion = UIComponentLibrary.CreateAJLabel("Question: ", 10, 115, this, myLayout);
        lblQuestion.setFont(new Font("Arial", Font.PLAIN, 12));

        lbl1 = UIComponentLibrary.CreateAJLabel("1: ", 10, 180, this, myLayout);
        lbl1.setFont(new Font("Arial", Font.PLAIN, 12));

        lbl2 = UIComponentLibrary.CreateAJLabel("2: ", 10, 205, this, myLayout);
        lbl2.setFont(new Font("Arial", Font.PLAIN, 12));

        lbl3 = UIComponentLibrary.CreateAJLabel("3: ", 10, 230, this, myLayout);
        lbl3.setFont(new Font("Arial", Font.PLAIN, 12));

        lbl4 = UIComponentLibrary.CreateAJLabel("4: ", 10, 255, this, myLayout);
        lbl4.setFont(new Font("Arial", Font.PLAIN, 12));

        lbl5 = UIComponentLibrary.CreateAJLabel("5: ", 10, 280, this, myLayout);
        lbl5.setFont(new Font("Arial", Font.PLAIN, 12));

        lblAnswer = UIComponentLibrary.CreateAJLabel("Your Answer: ", 10, 315, this, myLayout);
        lblAnswer.setFont(new Font("Arial", Font.PLAIN, 12));

        lblMessage = UIComponentLibrary.CreateAJLabel("Message...                                               .", 10, 360, this, myLayout);
        lblMessage.setText("<html>" + socket + "<html>");

    }

    private void SetupButton()
    {
        btnSubmit = UIComponentLibrary.CreateJButton("Submit", 90,20, 270, 320,this,this, myLayout);
        btnExit = UIComponentLibrary.CreateJButton("Exit", 90, 20 , 270, 370,this,this,myLayout);
        btnConnect = UIComponentLibrary.CreateJButton("Connect", 90, 20, 270, 340, this, this,myLayout);

    }

    private void SetupTextField()
    {
        txtStaffName = UIComponentLibrary.CreateAJTextField(31, 80, 37,this,myLayout);
        txtTopic = UIComponentLibrary.CreateAJTextField(31, 80, 90, this,myLayout);
        txt1 = UIComponentLibrary.CreateAJTextField(31, 80, 176, this,myLayout);
        txt2 = UIComponentLibrary.CreateAJTextField(31, 80, 201, this,myLayout);
        txt3 = UIComponentLibrary.CreateAJTextField(31, 80, 226, this,myLayout);
        txt4 = UIComponentLibrary.CreateAJTextField(31, 80, 251, this,myLayout);
        txt5 = UIComponentLibrary.CreateAJTextField(31, 80, 276, this,myLayout);
        txtAnswer = UIComponentLibrary.CreateAJTextField(7, 110, 314, this, myLayout);
    }
    private void SetupTextArea()
    {
        txtQuestion = UIComponentLibrary.CreateAJTextArea(3,31, 80,115, this,myLayout);
    }

    /**
     *
     * @param serverName
     * @param serverPort
     */
    public void connect(String serverName, int serverPort)
    {
        println("Establishing connection. Please wait ...");
        try
        {
            socket = new Socket(serverName, serverPort);
            println("Connected: " + socket);
            open();
        }
        catch (UnknownHostException uhe)
        {
            println("Host unknown: " + uhe.getMessage());
        }
        catch (IOException ioe)
        {
            println("Unexpected exception: " + ioe.getMessage());
        }
    }

    private void send()
    {
        try
        {
//            streamOut.writeUTF(txtTopic.getText() + "#" + txtQuestion.getText() + "#" + txt1.getText() + "#"
//                    + txt2.getText() + "#" + txt3.getText() + "#" + txt4.getText() + "#" + txt5.getText() + "#" + txtStaffName.getText() + "#" + txtAnswer.getText());
//            streamOut.flush();
//            txtAnswer.setText("");
            if (txtAnswer.getText().equals(correctAns))
            {
                lblMessage.setText("Correct");
                streamOut.writeUTF("Correct");
                streamOut.flush();
            }
            else
            {
                lblMessage.setText("Incorrect");
                streamOut.writeUTF("Incorrect");
                streamOut.flush();
            }
        }
        catch (IOException ioe)
        {
            println("Sending error: " + ioe.getMessage());
            close();
        }
    }

    public void handle(String msg)
    {
        if (msg.equals(".bye"))
        {
            println("Good bye. Press EXIT button to exit ...");
            close();
        }
        else
        {
            System.out.println("Handle: " + msg);
            println(msg);

            String[] data = msg.split(": ");

            if (data.length > 3)
            {
                txtTopic.setText(data[1]);

                txtQuestion.setText(data[2]);
                txt1.setText(data[3]);
                txt2.setText(data[4]);
                txt3.setText(data[5]);
                txt4.setText(data[6]);
                txt5.setText(data[7]);
                correctAns = data[8];
            }
        }
    }

    public void open()
    {
        try
        {
            streamOut = new DataOutputStream(socket.getOutputStream());
            chatStaffFormThread = new ChatStaffFormThread(this, socket);
        }
        catch (IOException ioe)
        {
            println("Error opening output stream: " + ioe);
        }
    }

    public void close()
    {
        try
        {
            if (streamOut != null)
            {
                streamOut.close();
            }
            if (socket != null)
            {
                socket.close();
            }
        }
        catch (IOException ioe)
        {
            println("Error closing ...");
        }
        chatStaffFormThread.close();
        chatStaffFormThread.stop();
    }

    void println(String msg)
    {
        //display.appendText(msg + "\n");
        lblMessage.setText(msg);
        //String[] data = msg.split(": ");
        //lblMessage.setText(data[0]);
        //lblMessage.setText("<html>" + msg + "<html>");
    }

    public void getParameters()
    {
//        serverName = getParameter("host");
//        serverPort = Integer.parseInt(getParameter("port"));

        serverName = "localhost";
        serverPort = 4444;
    }

    //  <editor-fold defaultstate="collapsed" desc="WindowListener">
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == btnSubmit)
        {
            send();
            txtAnswer.requestFocus();

        }

        if (e.getSource() == btnExit)
        {
            //txtWord1.setText(".bye");
            // txtWord2.setText("");
            //send();
            System.exit(0);
        }

        if (e.getSource() == btnConnect)
        {
            connect(serverName, serverPort);
        }
    }

//    <editor-fold defaultstate="collapsed" desc="WindowListener">
    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e)
    {
        System.exit(0);
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

//     </editor-fold>

}
