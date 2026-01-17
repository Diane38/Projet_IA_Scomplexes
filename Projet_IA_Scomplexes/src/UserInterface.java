
import javax.swing.*;
import java.awt.*;

public class UserInterface extends JPanel {

    private int DIM_HEIGHT = 640;
    private int DIM_WIDTH = 640;
    private int SQ_SIZE = 40;
    

    boolean black = true;

    public void initialize() {
        // Initialisation de notre fenetre

        //Main frame de l'application
        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());
        
        //Panels de dessin
        JPanel panelDessin = new JPanel();
        JPanel panelOutil = new JPanel();

        //Panel dessin
        panelDessin.add(this);
        panelDessin.setPreferredSize(new Dimension(DIM_WIDTH, DIM_HEIGHT));


        
        
        //Ajout des panels a la frame
        frame.add(panelDessin, BorderLayout.CENTER);
        frame.add(panelOutil, BorderLayout.EAST);

        //Couleur des panels pour les identifier
        panelOutil.setBackground(Color.magenta);
        //panelDessin.paintComponents(getGraphics());

        //Finalisation de la fenetre
        //frame.add(new UserInterface());
        frame.setVisible(true);
        frame.setTitle("ProjectAI");
        frame.setSize(DIM_WIDTH, DIM_HEIGHT);
        //frame.pack();
        frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
    }
    public Dimension getPreferredSize() {
        return new Dimension(DIM_WIDTH, DIM_HEIGHT);
    }

        @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < DIM_HEIGHT; i += SQ_SIZE) {
            if (black) {
                black = false;
            } else {
                black = true;
            }
            for (int j = 0; j < DIM_WIDTH; j += SQ_SIZE) {
                if (black) {
                    g.setColor(Color.WHITE);
                    g.fillRect(j, i, SQ_SIZE, SQ_SIZE);
                    black = false;
                } else {
                    g.setColor(Color.BLACK);
                    g.fillRect(j, i, SQ_SIZE, SQ_SIZE);
                    black = true;
                }
            }
        }
    }
}
