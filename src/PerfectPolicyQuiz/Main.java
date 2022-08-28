package PerfectPolicyQuiz;

import org.apache.commons.lang3.StringUtils;

public class Main implements IApp{

    public static void main(String[] args)
    {
        new MainScreen();

        IApp app = new Main();
        ShutdownInterceptor shutdownInterceptor = new ShutdownInterceptor(app);
        Runtime.getRuntime().addShutdownHook(shutdownInterceptor);
        app.start();
    }

    public void start() {

        try {
            System.out.println("Sleeping for 5 seconds before shutting down");
            Thread.sleep(5000);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void shutDown() {
        // Do a graceful shutdown here
        System.out.println("Shutdown is called");
    }
}
