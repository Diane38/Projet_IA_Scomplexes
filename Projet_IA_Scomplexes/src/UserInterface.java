
import javax.swing.*;
import java.awt.*;

public class UserInterface extends javax.swing.JFrame {
    
    public void initialize() {
        // Initialisation de notre fenetre

        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());

        JPanel panelOutil = new JPanel();
        
        JPanel panelDessin = new JPanel();
        panelDessin.setSize(200, 600);
        //panelDessin.setLayout(new GridLayout(20, 20));
        frame.add(panelDessin, BorderLayout.WEST);
        frame.add(panelOutil, BorderLayout.EAST);

        panelOutil.setBackground(Color.magenta);
        panelDessin.setBackground(Color.cyan);

        frame.setVisible(true);
        frame.setTitle("ProjectAI");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
    }
}
