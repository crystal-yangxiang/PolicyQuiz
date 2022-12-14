package PerfectPolicyQuiz;


// A Node is a node in a doubly-linked list.
class Node
{              // class for nodes in a doubly-linked list

    Node prev;              // previous Node in a doubly-linked list
    Node next;              // next Node in a doubly-linked list
    String myString;
    int incorrectAnswers = 0;
    int responses = 0;
    //public char data;       // data stored in this Node

    Node()
    {                // constructor for head Node
        prev = this;           // of an empty doubly-linked list
        next = this;
        myString = new String();
        myString = "";
//        myWords.Word1 = "New";
//        myWords.Word2 = "Words";
        // data = 'H';           // not used except for printing data in list head
    }

    Node(String string)
    {       // constructor for a Node with data
        prev = null;
        next = null;
        myString = new String(string);
        //this.data = data;     // set argument data to instance variable data
    }

    public void append(Node newNode)
    {  // attach newNode after this Node
        newNode.prev = this;//  this-the node current in
        newNode.next = next;
        if (next != null)
        {
            next.prev = newNode;
        }
        next = newNode;
        System.out.println("Node with data " + newNode.myString
                + " appended after Node with data " + myString);
    }

    public void insert(Node newNode)
    {  // attach newNode before this Node
        newNode.prev = prev;
        newNode.next = this;
        prev.next = newNode;;
        prev = newNode;
        System.out.println("Node with data " + newNode.myString
                + " inserted before Node with data " + myString);
    }

    public void remove()
    {              // remove this Node
        next.prev = prev;                 // bypass this Node
        prev.next = next;
        System.out.println("Node with data " + myString + " removed");
    }
    public String toString(){
        return this.myString + " - " + this.myString;
    }
}

class DList
{

    Node head;

    public DList()
    {
        head = new Node();
    }

    public DList(String s1)
    {
        head = new Node(s1);
    }

    public Node find(String str)
    {          // find Node containing x
        for (Node current = head.next; current != head; current = current.next)
        {
            if (current.myString.compareToIgnoreCase(str) == 0)
            {        // is x contained in current Node?
                System.out.println("Data " + str + " found");
                return current;      //find         // return Node containing x
            }
        }
        System.out.println("Data " + str + " not found");
        return null; //not find
    }

    //This Get method Added by Matt C
    public Node get(int i)
    {
        Node current = this.head;
        if (i < 0 || current == null)
        {
            throw new ArrayIndexOutOfBoundsException();
        }
        while (i > 0)
        {
            i--;
            current = current.next;
            if (current == null)
            {
                throw new ArrayIndexOutOfBoundsException();
            }
        }
        return current;
    }

    public String toString()
    {
        String str = "";
        if (head.next == head)
        {             // list is empty, only header Node
            return "List Empty";
        }
        str = "HEAD  ";
        for (Node current = head.next; current != head && current != null; current = current.next)
        {
            str = str + " ===> " + current.myString + " - Incorrect["+current.incorrectAnswers+"] from Responses["+current.responses+"]";
        }
        return str;
    }

    public void print()
    {                  // print content of list
        if (head.next == head)
        {             // list is empty, only header Node
            System.out.println("list empty");
            return;
        }
        System.out.print("list content = ");
        for (Node current = head.next; current != head; current = current.next)
        {
            System.out.print(" " + current.myString);
        }
        System.out.println("");
    }

//    public static void main(String[] args) {
//        DList dList = new DList();              // create an empty dList
//        dList.print();
//
//        dList.head.append(new Node("1","2"));       // add Node with data '1'
//        dList.print();
//        dList.head.append(new Node("3", "4"));       // add Node with data '2'
//        dList.print();
//        dList.head.append(new Node("5","6"));       // add Node with data '3'
//        dList.print();
//        dList.head.insert(new Node("A","B"));       // add Node with data 'A'
//        dList.print();
//        dList.head.insert(new Node("C","D"));       // add Node with data 'B'
//        dList.print();
//        dList.head.insert(new Node("E","F"));       // add Node with data 'C'
//        dList.print();
//
//        Node nodeA = dList.find("A");           // find Node containing 'A'
//        nodeA.remove();                         // remove that Node
//        dList.print();
//
//        Node node2 = dList.find("3");           // find Node containing '2'
//        node2.remove();                           // remove that Node
//        dList.print();
//
//        Node nodeB = dList.find("5");            // find Node containing 'B'
//        nodeB.append(new Node("Linked","List"));   // add Node with data X
//        dList.print();

}

