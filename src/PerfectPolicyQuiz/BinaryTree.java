package PerfectPolicyQuiz;

class BinaryTreeNode
{
    int key;
    String name;

    BinaryTreeNode leftChild;
    BinaryTreeNode rightChild;

    // constructor
    BinaryTreeNode(int key, String name)
    {
        this.key = key;
        this.name = name;
    }

    public String toString()
    {
        return name + " has the key " + key;
    }
}

public class BinaryTree
{

    BinaryTreeNode root;

    public void addBinaryTreeNode(int key, String name) {

        // Create a new Node and initialize it
        BinaryTreeNode newNode = new BinaryTreeNode(key, name); //

        // If there is no root this becomes root
        if (root == null)
        {
            root = newNode;
        }
        else
        {
            // Set root as the Node we will start
            // with as we traverse the tree
            BinaryTreeNode focusNode = root; // where i am

            // Future parent for our new Node
            BinaryTreeNode parent;

            while (true)
            {
                // root is the top parent so we start
                // there
                parent = focusNode;

                // Check if the new node should go on
                // the left side of the parent node
                if (key < focusNode.key)
                {
                    // Switch focus to the left child
                    focusNode = focusNode.leftChild;

                    // If the left child has no children
                    if (focusNode == null)
                    {
                        // then place the new node on the left of it
                        parent.leftChild = newNode;
                        return; // All Done
                    }
                }
                else
                {
                    // If we get here put the node on the right
                    focusNode = focusNode.rightChild;

                    // If the right child has no children
                    if (focusNode == null)
                    {
                        // then place the new node on the right of it
                        parent.rightChild = newNode;
                        return; // All Done
                    }
                }
            }
        }
    }

    // All nodes are visited in ascending order
    // Recursion is used to go to one node and
    // then go to its child nodes and so forth
    String traverseString;

    public String preOrderTraverseTree(BinaryTreeNode focusNode)
    {
        String traverseString = "";
        if (focusNode != null) {

            System.out.println(focusNode);
            traverseString = traverseString + focusNode.name + ",";

            traverseString = traverseString + preOrderTraverseTree(focusNode.leftChild);
            //tempString += preorderTraverseTree(focusNode.leftChild);
            traverseString = traverseString + preOrderTraverseTree(focusNode.rightChild);
        }

        return traverseString;
    }

    public String PrintPreOrder()
    {
        String traverseString = "";

        traverseString = traverseString + preOrderTraverseTree(root);

        return traverseString;
    }

    public String inOrderTraverseTree(BinaryTreeNode focusNode)
    {
        String traverseString = "";
        if (focusNode != null)
        {
            // Traverse the left node
            traverseString = traverseString + inOrderTraverseTree(focusNode.leftChild);

            // Visit the currently focused on node
            System.out.println(focusNode);
            traverseString = traverseString + focusNode.name + ",";

            // Traverse the right node
            traverseString = traverseString + inOrderTraverseTree(focusNode.rightChild);
        }
        return  traverseString;
    }

    public  String PrintInOrder()
    {
        String traverseString = "";

        traverseString = traverseString + inOrderTraverseTree(root);

        return traverseString;
    }


    public String postOrderTraverseTree(BinaryTreeNode focusNode)
    {
        String traverseString = "";
        if (focusNode != null)
        {
            traverseString = traverseString + postOrderTraverseTree(focusNode.leftChild);
            traverseString = traverseString + postOrderTraverseTree(focusNode.rightChild);

            System.out.println(focusNode);
            traverseString = traverseString + ", " + focusNode.name;

        }
        return traverseString;
    }

    public String PrintPostOrder()
    {
        String traverseString = "";
        traverseString = traverseString + postOrderTraverseTree(root);
        return "Post-Order: " + traverseString + "===> Tail";
    }

    public BinaryTreeNode findNode(int key)
    {
        // Start at the top of the tree
        BinaryTreeNode focusNode = root;

        // While we haven't found the Node
        // keep looking
        while (focusNode.key != key)
        {
            // If we should search to the left
            if (key < focusNode.key)
            {
                // Shift the focus Node to the left child
                focusNode = focusNode.leftChild;
            }
            else
            {
                // Shift the focus Node to the right child
                focusNode = focusNode.rightChild;
            }

            // The node wasn't found
            if (focusNode == null)
                return null;
        }
        return focusNode;
    }

    //public void addBinaryTreeNode(String questionToString) {}


//    public static void main(String[] args)
//    {
//        BinaryTree theTree = new BinaryTree();
//
//        theTree.addNode(50, "Boss");
//
//        theTree.addNode(25, "Vice President");
//
//        theTree.addNode(15, "Office Manager");
//
//        theTree.addNode(30, "Secretary");
//
//        theTree.addNode(75, "Sales Manager");
//
//        theTree.addNode(85, "Salesman 1");
//
//        // Different ways to traverse binary trees
//
//        theTree.inOrderTraverseTree(theTree.root);
//
//        // theTree.preorderTraverseTree(theTree.root);
//
//        // theTree.postOrderTraverseTree(theTree.root);
//
//        // Find the node with key 75
//
//        System.out.println("\nNode with the key 75");
//
//        System.out.println(theTree.findNode(75));
//
//    }
}


