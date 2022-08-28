package PerfectPolicyQuiz;


import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class MainScreenThread extends Thread
{
    private Socket socket = null;
    private MainScreen mainScreen = null;
    private DataInputStream streamIn = null;

    public MainScreenThread(MainScreen _mainScreen, Socket _socket)
    {
        mainScreen = _mainScreen;
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
            //client1.stop();
            mainScreen.close();
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
                mainScreen.handle(streamIn.readUTF());
            }
            catch (IOException ioe)
            {
                System.out.println("Listening error: " + ioe.getMessage());
                //client1.stop();
                mainScreen.close();
            }
        }
    }
}

