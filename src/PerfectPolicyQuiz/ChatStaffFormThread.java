package PerfectPolicyQuiz;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ChatStaffFormThread extends Thread
{
    private Socket socket = null;
    private ChatStaffForm chatStaffForm = null;
    private DataInputStream streamIn = null;

    public ChatStaffFormThread(ChatStaffForm _chatStaffForm, Socket _socket)
    {
        chatStaffForm = _chatStaffForm;
        socket = _socket;
        open();
        start();
    }

    public void open()
    {
        try
        {
            streamIn = new DataInputStream(socket.getInputStream());
        }
        catch (IOException ioe)
        {
            System.out.println("Error getting input stream: " + ioe);
            //client2.stop();
            chatStaffForm.close();
        }
    }

    public void close()
    {
        try
        {
            if (streamIn != null)
            {
                streamIn.close();
            }
        }
        catch (IOException ioe)
        {
            System.out.println("Error closing input stream: " + ioe);
        }
    }

    public void run()
    {
        while (true)
        {
            try
            {
                chatStaffForm.handle(streamIn.readUTF());
            }
            catch (IOException ioe)
            {
                System.out.println("Listening error: " + ioe.getMessage());
                //client2.stop();
                chatStaffForm.close();
            }
        }
    }
}

