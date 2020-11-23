import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        //
        // according to official Java documentation,
        // "in Java Swing programs,
        //  an initial thread schedules the GUI creation task
        //  by invoking javax.swing.SwingUtilities.invokeLater"
        //
        SwingUtilities.invokeLater(
                () -> new FloodFillGame(true)
        );
    } // end of main()
}