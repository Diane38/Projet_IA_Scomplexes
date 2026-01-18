import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main {
   public static void main(String[] args) {
       SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Projet IA Scomplexes");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            frame.add(new UserInterface());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setResizable(false);
            frame.setVisible(true);
        });
    }
}