import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GrillePanel extends JPanel {

    private int lignes = 10;
    private int colonnes = 10;
    private int tailleCase = 40;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        ArrayList<Object> grille = createGrille();

        for (int j = 0; j < grille.size(); j++) {
            System.out.println(grille.get(j));
        }

        for (int j = 0; j < colonnes; j++) {
            for (int i = 0; i < lignes; i++) {
        // Exemple : couleur alternée
                if ((i + j) % 2 == 0) {
                    g.setColor(Color.BLUE);
                } else {
                    g.setColor(Color.YELLOW);
                }

                g.fillRect(
                        j * tailleCase,
                        i * tailleCase,
                        tailleCase,
                        tailleCase
                );

                // Bordure noire
                g.setColor(Color.BLACK);
                g.drawRect(
                        j * tailleCase,
                        i * tailleCase,
                        tailleCase,
                        tailleCase
                );
            }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(
                colonnes * tailleCase,
                lignes * tailleCase
        );
    }

    public ArrayList<Object> createGrille(){
        ArrayList<Object> grilleCouleurs = new ArrayList<>();
            for (int i = 0; i < lignes; i++) {
                for (int j = 0; j < colonnes; j++) {
                    grilleCouleurs.add(Color.blue);
                }
            }
        return grilleCouleurs;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Grille colorée");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            frame.add(new GrillePanel());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}