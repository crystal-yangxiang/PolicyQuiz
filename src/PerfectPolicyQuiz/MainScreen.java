package PerfectPolicyQuiz;

//import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;

/****************************************************************
 PROGRAM:   Perfect Policy Quiz
 AUTHOR:    Yang XIANG
 STUDENT ID:  472841231
 DUE DATE:  27th May 2022

 FUNCTION: This Perfect Policy Quiz is to deliver quick quiz questions to the staff.
            The quality team manager selects a quiz question and send question data
            to staff to answer. It enable quality team manager to search quiz by
            typing key words in the search text field. Quiz display table can by
            sorted and displayed by 3 sorting algorithms. Connent and send questions
            to staff side and staff enable to answer the question and submit back.
            Results of answers will indicate to staff once submitting. The total incorrect
            answer count will be calculated and displayed in the linked list text area.

 ****************************************************************/

//https://examples.javacodegeeks.com/core-java/apache/commons/lang3/stringutils/org-apache-commons-lang3-stringutils-example/
//https://www.javatips.net/api/org.apache.commons.lang.stringutils
//https://www.codebyamir.com/blog/using-stringutils-from-apache-commons-lang-3
//https://www.delftstack.com/howto/java/stringutils-in-java/


import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Locale;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;


public class MainScreen extends JFrame implements ActionListener, WindowListener, MouseListener // 5 methods
{

    SpringLayout myLayout = new SpringLayout();
    JLabel lblTitle, lblSearchTopic, lblPolicyQuestions, lblQnNum, lblTopic, lblSubTopic,lblSortBy,
           lblCorrectAns, lblLinkedList, lblBinaryTree, lblPreOrder, lblInOrder, lblPostOrder,lblTopic2, lblSubTopic2,
            lblQn, lblA, lblB, lblC, lblD, lblE, lblMessage;
    JButton btnSortByQnNum, btnSortByTopic, btnSortBySubTopic, btnExit, btnConnect, btnSend, btnPreOrderDisplay, btnInOrderDisplay,
            btnPostOrderDisplay, btnDisplay, btnSave;
    JTextField txtSearchTopic, txtCorrectAns, txtTopic2, txtQnNum, txtTopic2A, txtA, txtB, txtC, txtD, txtE;
    JTextArea txtLinkedList, txtBinaryTree, txtQn;
    JTable quizTable;
    MyModel quizModel;
    TableRowSorter rowSorter;

    ChatServer myServer;

    int rowID = 0;
    ArrayList<Object[]> dataValues = new ArrayList();
    ArrayList<Object[]> searchDataValue = new ArrayList<>();

    //HashMap<Integer, String> hm;
    HashMap<Integer, String> hm = new HashMap<>();
    DList dList = new DList();
    BinaryTree theTree = new BinaryTree();

    boolean FirstQn = true;
    String NumOfAnswers = "0";
    String NumOfCorrectAnswers = "0";
    String CurrentQn = "0";


    //CHAT RELATED ---------------------------
    private Socket socket = null;
    private DataInputStream console = null;
    private DataOutputStream streamOut = null;
    private MainScreenThread mainScreenThread = null;
    private String serverName = "localhost";
    private int serverPort = 4444;
    //-------------------------------------------------


    public MainScreen()
    {
        //set up screen layout and listeners
        setTitle("Perfect Policy Quiz");
        setSize(800,660);
        setLocation(50,17);
        //addWindowListenerToForm();
        setLayout(myLayout);

        SetupLabel();
        SetupButton();
        SetupTextField();
        SetupTextArea();

        myServer = new ChatServer(4444);
        connect(serverName,serverPort);

//        hm = new HashMap<Integer, String>();
//        hm.put(Integer.parseInt(lblQn.getText().toString()) ,txtBinaryTree.getText().toString());
//        writeFile(hm);

        //new ChatStaffForm();

        QuizTable(myLayout);
        //sendDList.head.append();

        quizTable.addMouseListener(this);
//        DrawRect(Graphics g);

        setVisible(true);// always do last

        //System.out.println(QuestionToString(1));

        System.out.println(StringUtils.isBlank(""));
        System.out.println(StringUtils.abbreviateMiddle("This is Java", "**", 4));

        //CHAT RELATED ---------------------------
        getParameters();
        //-----------------------------

    }

    private void SetupLabel()
    {
        lblTitle = UIComponentLibrary.CreateAJLabel("Perfect Policy Quiz", 0, 0, this, myLayout);
        lblTitle.setOpaque(true);
        lblTitle.setPreferredSize(new Dimension(800,30));
        lblTitle.setBackground(Color.pink);
        lblTitle.setForeground(Color.white);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));

        lblSearchTopic = UIComponentLibrary.CreateAJLabel("Search Topic: ", 8, 40, this, myLayout);
        lblSearchTopic.setOpaque(true);
        lblSearchTopic.setPreferredSize(new Dimension(90,20));
        lblSearchTopic.setBackground(Color.pink);
        lblSearchTopic.setForeground(Color.white);

        lblPolicyQuestions = UIComponentLibrary.CreateAJLabel("Policy Questions", 8, 68, this, myLayout);
        lblPolicyQuestions.setOpaque(true);
        lblPolicyQuestions.setPreferredSize(new Dimension(367,20));
        lblPolicyQuestions.setBackground(Color.pink);
        lblPolicyQuestions.setForeground(Color.white);
        lblPolicyQuestions.setHorizontalAlignment(SwingConstants.CENTER);

        lblLinkedList = UIComponentLibrary.CreateAJLabel("Linked List", 8, 340, this, myLayout);
        lblLinkedList.setOpaque(true);
        lblLinkedList.setPreferredSize(new Dimension(769,20));
        lblLinkedList.setBackground(Color.pink);
        lblLinkedList.setForeground(Color.white);
        lblLinkedList.setHorizontalAlignment(SwingConstants.CENTER);

        lblBinaryTree = UIComponentLibrary.CreateAJLabel("BinaryTree", 8, 440, this, myLayout);
        lblBinaryTree.setOpaque(true);
        lblBinaryTree.setPreferredSize(new Dimension(769,20));
        lblBinaryTree.setBackground(Color.pink);
        lblBinaryTree.setForeground(Color.white);
        lblBinaryTree.setHorizontalAlignment(SwingConstants.CENTER);

//        lblQnNum = UIComponentLibrary.CreateAJLabel("#", 18, 88, this, myLayout);
//        lblTopic = UIComponentLibrary.CreateAJLabel("Topic", 45, 88, this, myLayout);
//        lblSubTopic = UIComponentLibrary.CreateAJLabel("Sub Topic", 140, 88, this, myLayout);
        lblSortBy = UIComponentLibrary.CreateAJLabel("Sort by:", 18, 290, this, myLayout);
        lblCorrectAns = UIComponentLibrary.CreateAJLabel("Correct Ans: ", 420, 315, this, myLayout);
        lblSubTopic2 = UIComponentLibrary.CreateAJLabel("Topic: ", 400, 47, this, myLayout);
        lblSubTopic2.setFont(new Font("Arial", Font.PLAIN, 12));
        lblSubTopic2 = UIComponentLibrary.CreateAJLabel("SubTopic: ", 400, 70, this, myLayout);
        lblQn = UIComponentLibrary.CreateAJLabel("Qn: ", 400, 95, this, myLayout);

        lblA= UIComponentLibrary.CreateAJLabel("A: ", 400, 160, this, myLayout);
        lblB = UIComponentLibrary.CreateAJLabel("B: ", 400, 187, this, myLayout);
        lblC = UIComponentLibrary.CreateAJLabel("C: ", 400, 214, this, myLayout);
        lblD = UIComponentLibrary.CreateAJLabel("D: ", 400, 241, this, myLayout);
        lblE = UIComponentLibrary.CreateAJLabel("E: ", 400, 268 , this, myLayout);

        lblPreOrder = UIComponentLibrary.CreateAJLabel("Pre-Order", 20, 530, this, myLayout);
        lblPreOrder.setOpaque(true);
        lblPreOrder.setPreferredSize(new Dimension(90,20));
        lblPreOrder.setBackground(Color.pink);
        lblPreOrder.setForeground(Color.white);
        lblPreOrder.setHorizontalAlignment(SwingConstants.CENTER);

        lblInOrder = UIComponentLibrary.CreateAJLabel("In-Order", 260, 530, this, myLayout);
        lblInOrder.setOpaque(true);
        lblInOrder.setPreferredSize(new Dimension(90,20));
        lblInOrder.setBackground(Color.pink);
        lblInOrder.setForeground(Color.white);
        lblInOrder.setHorizontalAlignment(SwingConstants.CENTER);

        lblPostOrder = UIComponentLibrary.CreateAJLabel("Post-Order", 510, 530, this, myLayout);
        lblPostOrder.setOpaque(true);
        lblPostOrder.setPreferredSize(new Dimension(90,20));
        lblPostOrder.setBackground(Color.pink);
        lblPostOrder.setForeground(Color.white);
        lblPostOrder.setHorizontalAlignment(SwingConstants.CENTER);

        lblMessage = UIComponentLibrary.CreateAJLabel("Message", 20, 580, this, myLayout);
        lblMessage.setOpaque(true);
        lblMessage.setPreferredSize(new Dimension(750,30));
        lblMessage.setBackground(Color.pink);
        lblMessage.setForeground(Color.white);
    }

    private  void SetupButton()
    {
        // Each line calls the CreateJButton method from the UIComponentLibrary to establish each Button
        btnSortByQnNum = UIComponentLibrary.CreateJButton("Qn #",85,20,70,290,this,this,myLayout);
        btnSortByTopic = UIComponentLibrary.CreateJButton("Topic",85,20,153,290,this,this,myLayout);
        btnSortBySubTopic = UIComponentLibrary.CreateJButton("SubTopic",85,20,235,290,this,this,myLayout);
        btnExit = UIComponentLibrary.CreateJButton("Exit",175,20,8,315,this,this,myLayout);
        btnConnect = UIComponentLibrary.CreateJButton("Connect",175,20,205,315,this,this,myLayout);
        btnSend = UIComponentLibrary.CreateJButton("Send",140,20,600,315,this,this,myLayout);
        btnPreOrderDisplay = UIComponentLibrary.CreateJButton("Display",90,20,20,550,this,this,myLayout);
        btnInOrderDisplay = UIComponentLibrary.CreateJButton("Display",90,20,260,550,this,this,myLayout);
        btnPostOrderDisplay = UIComponentLibrary.CreateJButton("Display",90,20,510,550,this,this,myLayout);
        btnDisplay = UIComponentLibrary.CreateJButton("Display",90,20,630,530,this,this,myLayout);
        btnSave = UIComponentLibrary.CreateJButton("Save",90,20,630,550,this,this,myLayout);
    }

    private void SetupTextField()
    {
        txtSearchTopic = UIComponentLibrary.CreateAJTextField(30, 107, 40,this,myLayout);
        txtSearchTopic.addActionListener(this);
        txtCorrectAns = UIComponentLibrary.CreateAJTextField(6, 500, 315,this,myLayout);
        txtTopic2 = UIComponentLibrary.CreateAJTextField(36, 450, 47,this,myLayout);
        txtTopic2A = UIComponentLibrary.CreateAJTextField(36, 450, 67,this,myLayout);
        txtQnNum = UIComponentLibrary.CreateAJTextField(3, 400, 120, this,myLayout);
        txtA = UIComponentLibrary.CreateAJTextField(36, 450, 160,this,myLayout);
        txtB = UIComponentLibrary.CreateAJTextField(36, 450, 187,this,myLayout);
        txtC = UIComponentLibrary.CreateAJTextField(36, 450, 214,this,myLayout);
        txtD = UIComponentLibrary.CreateAJTextField(36, 450, 241,this,myLayout);
        txtE = UIComponentLibrary.CreateAJTextField(36, 450, 268,this,myLayout);
    }

    private  void SetupTextArea()
    {
        txtLinkedList = UIComponentLibrary.CreateAJTextArea(4,85,8,360,this, myLayout);
        txtBinaryTree = UIComponentLibrary.CreateAJTextArea(4,85,8,460,this,myLayout);
        txtQn = UIComponentLibrary.CreateAJTextArea(4,36,450,90,this,myLayout); //36
       // txtQn.setSize(new Dimension(10,10));
        txtQn.setLineWrap(true);
    }

    public void displayQuestion(int index)
    {
        txtTopic2.setText((String) dataValues.get(index)[1]);
        txtTopic2A.setText((String) dataValues.get(index)[2]);
        txtQn.setText((String) dataValues.get(index)[3]);
        txtA.setText((String) dataValues.get(index)[4]);
        txtB.setText((String) dataValues.get(index)[5]);
        txtC.setText((String) dataValues.get(index)[6]);
        txtD.setText((String) dataValues.get(index)[7]);
        txtE.setText((String) dataValues.get(index)[8]);
        txtCorrectAns.setText((String) dataValues.get(index)[9]);
        txtQnNum.setText((String) dataValues.get(index)[0]);
    }

    public void QuizTable(SpringLayout myPanelLayout) // copy pasta to my project
    {
        // Create a panel to hold all other components
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        add(topPanel);

        // Create column names
        String columnNames[] = {"#", "Topic", "SubTopic"};  // headings

        dataValues = readFile();
//Data File -> dataValues -> model ->quizModel (change word to quiz) -> quizTable

        // constructor of JTable model
        searchDataValue = new ArrayList<>(dataValues);
        quizModel = new MyModel(searchDataValue, columnNames);

        // Create a new table instance
        quizTable = new JTable(quizModel); // work perfectly

        // Configure some of JTable's paramters
        quizTable.isForegroundSet();
        quizTable.setShowHorizontalLines(true);
        quizTable.setRowSelectionAllowed(true);
        quizTable.setColumnSelectionAllowed(true);
        //quizTable.getColumnModel().getColumn(0).setPreferredWidth();
        //quizTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        add(quizTable);
        quizTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        quizTable.getColumnModel().getColumn(0).setPreferredWidth(40);
        quizTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        quizTable.getColumnModel().getColumn(2).setPreferredWidth(225);

        // Change the text and background colours
        quizTable.setSelectionForeground(Color.white);
        quizTable.setSelectionBackground(Color.magenta);

        // Add the table to a scrolling pane, size and locate
        JScrollPane scrollPane = quizTable.createScrollPaneForTable(quizTable);
        topPanel.add(scrollPane, BorderLayout.CENTER);
        topPanel.setPreferredSize(new Dimension(367, 200)); // change numbers to fit my project screen
        myPanelLayout.putConstraint(SpringLayout.WEST, topPanel, 8, SpringLayout.WEST, this);
        myPanelLayout.putConstraint(SpringLayout.NORTH, topPanel, 85, SpringLayout.NORTH, this);
    }

    //---------------------------------------------------------------------------------------------------
    // Source: http://www.dreamincode.net/forums/topic/231112-from-basic-jtable-to-a-jtable-with-a-tablemodel/
    // class that extends the AbstractTableModel
    //---------------------------------------------------------------------------------------------------

    public void StringUtilsTest(){
        System.out.println(StringUtils.isBlank(""));
        System.out.println(StringUtils.abbreviateMiddle("This is Java", "**", 4));
    }


    public  void mousePressed(MouseEvent e) {}

    public  void mouseReleased(MouseEvent e) {}

    public  void mouseEntered(MouseEvent e) {}

    public  void mouseExited(MouseEvent e) { }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == btnSortByQnNum) { //sort by question number button
            bubbleSort(dataValues);
            quizModel.fireTableDataChanged();
        }
        if(e.getSource() == btnSortByTopic) { //sort by question topic button
            insertionSort(dataValues);
            quizModel.fireTableDataChanged();
        }
        if(e.getSource() == btnSortBySubTopic) {
            selectionSort(dataValues);
            quizModel.fireTableDataChanged();
        }
        if(e.getSource() ==  btnExit) {
            System.exit(0);
        }
        if(e.getSource() ==  txtSearchTopic) {
            searchTopic();
        }
        if (e.getSource() == btnSend) {
            dList.head.append(new Node(QuestionToString(rowID)));
            txtLinkedList.setText(dList.toString());

            theTree.addBinaryTreeNode(Integer.parseInt(dataValues.get(rowID)[0].toString()), QuestionToString(rowID));
            txtBinaryTree.setText(theTree.PrintInOrder());

            Object[] data = dataValues.get(rowID);


            send();
            hm.put(Integer.parseInt(txtQnNum.getText()), txtTopic2.getText());
        }
        if (e.getSource() == btnPreOrderDisplay)
        {
            txtBinaryTree.setText(theTree.PrintPreOrder());
        }
        if (e.getSource() == btnInOrderDisplay) {
            txtBinaryTree.setText(theTree.PrintInOrder());
        }
        if (e.getSource() == btnPostOrderDisplay) {
            txtBinaryTree.setText(theTree.PrintPostOrder());
        }
        if (e.getSource() == btnConnect)
        {
            //connect(serverName,serverPort);
            new ChatStaffForm();
        }
        if (e.getSource() == btnSave)
        {
            writeFile();
        }
    }

    ArrayList<Object[]> al = new ArrayList();
    String fileName = "PerfectPoliciesQuiz_Sample.txt";

    public ArrayList<Object[]> readFile()
    {
        try {
            FileInputStream finStream = new FileInputStream(fileName);
            DataInputStream dinStream = new DataInputStream(finStream);
            BufferedReader br = new BufferedReader(new InputStreamReader(dinStream));

            String line= br.readLine();

            for (int i=0; i<10; i++)
            {
                line = br.readLine();
            }

            while (line != null)
            {
                Object[] temp = new Object[10];
                for (int i=0; i<10; i++)
                {
                    temp[i] = line;
                    line = br.readLine();
                }
                al.add(temp);
            }
            br.close();
            dinStream.close();
            finStream.close();
        } catch (Exception e) {
            System.err.println("Error Reading File: " + e.getMessage());
        }
        return al;
    }

    public void writeFile(){
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter("Hash.txt"));
            bw.write(hm.toString());
            bw.close();
            JOptionPane.showMessageDialog(null, "Saved");
        }
        catch (Exception e)
        {
            System.err.println("Error" + e.getMessage());
        }
    }

    //final static String outputFilePath = "C://";

//    public void writeFile(HashMap<Integer, String> map) {
//        try {
//            File file = new File("Hash.txt");
//            FileOutputStream fos = new FileOutputStream(file);
//            ObjectOutputStream oos = new ObjectOutputStream(fos);
//
//            oos.writeObject(map);
//            oos.flush();
//            oos.close();
//            fos.close();
//        }
//        catch (Exception e) {
//        }
//    }



    //private void writeFile() {
        //FileDialog file = new FileDialog(this, "Save File!", FileDialog.SAVE);
        //file.setDirectory("C://");
       // file.setVisible(true);
        //String filePath = file.getDirectory() + file.getFile();

//        hm.put(Integer.parseInt(dataValues.get(rowID)[0].toString()), QuestionToString(rowID));
//        File file = new File(outputFilePath);
//
//        BufferedWriter bw = null;
//
//
//        try {
//            bw = new BufferedWriter(new FileWriter(file));
//            for(Map.Entry<Integer, String> entry : hm.entrySet())
//            {
//                bw.write(entry.getKey() + ":" + entry.getValue());
//                bw.newLine();
//            }
//
//            bw.flush();
//            bw.close();
//        }
//        catch (IOException e)
//        {
//            System.err.println("Error" + e.getMessage() );
//        }
//
//    }

    public void bubbleSort(ArrayList<Object[]> dataValues)
    {
        for(int j=0; j<searchDataValue.size(); j++)
        {
            for(int i=j+1; i<searchDataValue.size(); i++)
            {
                if((searchDataValue.get(i)[0]).toString().compareToIgnoreCase(searchDataValue.get(j)[0].toString())<0)
                {
                    Object[] words = searchDataValue.get(j);
                    searchDataValue.set(j, searchDataValue.get(i));
                    searchDataValue.set(i, words);
                }
            }
            System.out.println(searchDataValue.get(j)[0] + " - " + searchDataValue.get(j)[1]);
        }
    }
    // structure learnt from https://www.programiz.com/dsa/insertion-sort
    public void insertionSort(ArrayList<Object[]> dataValues)
    {
        int j; //the number of items sorted so far
        //int key; // the item to be inserted
        int i;

        for (j = 1; j<searchDataValue.size(); j++ ) // start with 1 (not 0)
        {
            Object[] key = searchDataValue.get(j);
            for (i = j - 1;(i >= 0)  && (searchDataValue.get(i)[1].toString().compareToIgnoreCase(key[1].toString())> 0); i--) //smaller values are moving up
            {
                searchDataValue.set(i+1, searchDataValue.get(i));
            }
            searchDataValue.set(i + 1, key); // put the key in its proper location
        }
    }

    //structure learnt from https://www.programiz.com/dsa/selection-sort
    public void selectionSort(ArrayList<Object[]> dataValues)
    {
        int i, j, first;
        for (i = 0; i < searchDataValue.size() - 1; i++)
        {
            first = i; // initialize to subscript of first element
            for (j = i + 1 ; j < searchDataValue.size(); j++)//locate smallest element between positions 1 and i
            {
                if(searchDataValue.get(j)[2].toString().compareToIgnoreCase(searchDataValue.get(first)[2].toString())<0)
                    first = j;
            }
            Object[] temp = searchDataValue.get(i);
            searchDataValue.set(i, searchDataValue.get(first));
            searchDataValue.set(first, temp);
        }
    }

    public String QuestionToString(int id)
    {
        Object[] myObjects = searchDataValue.get(id);

        String questionId = myObjects[0]+"";
        String questionTopic = myObjects[1]+"";
        String subTopicQuestionTopic = myObjects[2]+"";

        return questionId + " " + questionTopic + " " + subTopicQuestionTopic;
    }


    public void searchTopic()
    {
        rowSorter = new TableRowSorter<>(quizModel);
        quizTable.setRowSorter(rowSorter);
        txtSearchTopic.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                search(txtSearchTopic.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                search(txtSearchTopic.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                search(txtSearchTopic.getText());
            }
            public void search(String str) {
                if (str.length() == 0 ) {
                    rowSorter.setRowFilter(null);
                }
                else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + str));// "(?!)" ingore cases
                }
            }
        });
    }


    public  void mouseClicked(MouseEvent e) {
        rowID = quizTable.getSelectedRow();
        displayQuestion(rowID);
    }

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
            //Object[] data = dataValues.get(rowID);
            String sendString = txtTopic2.getText() + ": " + txtQn.getText() + ": " + txtA.getText() + ": " +
                    txtB.getText() + ": " + txtC.getText() + ": "+ txtD.getText()+ ": " + txtE.getText() + ": " +
                    txtCorrectAns.getText();
            streamOut.writeUTF(sendString);
            streamOut.flush();
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
            println(msg);
            String[] data = msg.split(": ");

            if (data.length < 4)
            {
                if (data[1].equalsIgnoreCase("Incorrect"))
                {
                    dList.head.next.incorrectAnswers++;
                }
                dList.head.next.responses++;

                txtLinkedList.setText(dList.toString());
            }
        }
    }

    public void open()
    {
        try
        {
            streamOut = new DataOutputStream(socket.getOutputStream());
            mainScreenThread = new MainScreenThread(this, socket);
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
        mainScreenThread.close();
        mainScreenThread.stop();
    }

    void println(String msg)
    {
        //display.appendText(msg + "\n");
        //lblMessage.setText(msg);
        lblMessage.setText(msg);
    }

    public void getParameters()
    {
//        serverName = getParameter("host");
//        serverPort = Integer.parseInt(getParameter("port"));

        serverName = "localhost";
        serverPort = 4444;
    }

    @Override
    public void windowOpened(WindowEvent e) {}

    @Override
    public void windowClosing(WindowEvent e) {}

    @Override
    public void windowClosed(WindowEvent e) {}

    @Override
    public void windowIconified(WindowEvent e) {}

    @Override
    public void windowDeiconified(WindowEvent e) {}

    @Override
    public void windowActivated(WindowEvent e) {}

    @Override
    public void windowDeactivated(WindowEvent e) {}
}
