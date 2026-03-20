package Week5.Day2;

public class Logger {


    private static Logger instance;

    // Private constructor
    private Logger() {
        System.out.println("Logger instance created.");
    }

    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    public static void main(String[] args) {
        Logger log1 = Logger.getInstance();
        Logger log2 = Logger.getInstance();

        if (log1 == log2) {
            System.out.println("Both references point to the same instance.");
        }
    }
}