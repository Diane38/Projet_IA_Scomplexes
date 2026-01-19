import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class UserInterface extends JPanel {
    private int mapSize = 500;
    private int tailleCase = 30;
    private ControlCenter controlCenter;
    private Environnement environnement;
    private List<Anomalie> anomalies;
    private List<Drone> drones;
    private boolean running = true;

    public UserInterface(ControlCenter c, Environnement env) {
        this.controlCenter = c;
        this.environnement = env;
        this.drones = c.getDrones();
        this.anomalies = env.getAnomalies();

        // Lancer la boucle de simulation
        //startSimulation();
    }

    private void startSimulation() {
        new Thread(() -> {
            while (running) {
                // Mettre à jour la simulation
                controlCenter.updateSimulation(100);  // 100ms
                environnement.update(100);

                // Redessiner
                SwingUtilities.invokeLater(() -> repaint());

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Fond blanc
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        drawGrid(g);
        drawCentre(g);
        drawAnomalies(g);
        drawDrones(g);
        drawInfo(g);
    }
    private void drawGrid(Graphics g) {
        g.setColor(new Color(200, 200, 200));
        int cols = mapSize / tailleCase;
        int rows = mapSize / tailleCase;

        for (int i = 0; i <= rows; i++) {
            g.drawLine(0, i * tailleCase, mapSize, i * tailleCase);
        }
        for (int j = 0; j <= cols; j++) {
            g.drawLine(j * tailleCase, 0, j * tailleCase, mapSize);
        }
    }

    private void drawAnomalies(Graphics g) {
        List<Anomalie> anomaliesCopy = new ArrayList<>(anomalies);

        for (Anomalie a : anomaliesCopy) {
            int x = (a.getPosX() / tailleCase) * tailleCase;
            int y = (a.getPosY() / tailleCase) * tailleCase;
            int intensity = a.getIntensity();

            // Couleur selon le type
            switch(a.getType()) {
                case "incendie":
                    g.setColor(new Color(255, 0, 0, 100 + intensity));  // Rouge
                    break;
                case "inondation":
                    g.setColor(new Color(0, 0, 255, 100 + intensity));  // Bleu
                    break;
                case "secheresse":
                    g.setColor(new Color(255, 200, 0, 100 + intensity)); // Orange
                    break;
                default:
                    g.setColor(Color.GRAY);
            }

            g.fillRect(x, y, tailleCase, tailleCase);

            // Afficher l'intensité
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 10));
            g.drawString(intensity + "", x + 5, y + 15);
        }
    }

    private void drawDrones(Graphics g) {
        for (Drone d : drones) {
            int x = (d.getPosX() / tailleCase) * tailleCase;
            int y = (d.getPosY() / tailleCase) * tailleCase;

            // Couleur selon l'état
            switch(d.getState()) {
                case ACTIVE:
                    g.setColor(Color.GREEN);
                    break;
                case CHARGING:
                    g.setColor(Color.YELLOW);
                    break;
                case ANALYZING:
                    g.setColor(Color.ORANGE);
                    break;
                case RETURNING_TO_BASE:
                    g.setColor(Color.BLUE);
                    break;
                default:
                    g.setColor(Color.GRAY);
            }

            // Dessiner le drone (carré)
            g.fillRect(x + 5, y + 5, tailleCase - 10, tailleCase - 10);
            g.setColor(Color.BLACK);
            g.drawRect(x + 5, y + 5, tailleCase - 10, tailleCase - 10);

            // ID du drone
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 8));
            g.drawString("D" + d.getDroneId(), x + 10, y + 15);
        }
    }
    private void drawCentre(Graphics g){
        int centerX = (controlCenter.getPosX() / tailleCase) * tailleCase;
        int centerY = (controlCenter.getPosY() / tailleCase) * tailleCase;

        g.setColor(Color.CYAN);
        g.fillRect(centerX + 5, 5 + centerY, tailleCase - 10, tailleCase - 10);
        g.setColor(Color.BLACK);
        g.drawRect(centerX + 5, centerY + 5, tailleCase - 10, tailleCase - 10);

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 8));
        g.drawString("CC", centerX + 10, centerY + 15);
    }

    private void drawInfo(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 12));

        int yOffset = mapSize + 20;
        int activeCount = (int) drones.stream()
                .filter(d -> d.getState() == Drone.DroneState.ACTIVE)
                .count();
        int chargingCount = (int) drones.stream()
                .filter(d -> d.getState() == Drone.DroneState.CHARGING)
                .count();

        g.drawString("Drones actifs: " + activeCount, 10, yOffset);
        g.drawString("Drones en recharge: " + chargingCount, 10, yOffset + 20);
        g.drawString("Anomalies détectées: " + anomalies.size(), 10, yOffset + 40);
        g.drawString("Données du centre: " + controlCenter.getData().split("\n").length + " entrées", 10, yOffset + 60);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(
                mapSize,
                mapSize + 100
        );
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Créer l'environnement et le centre de contrôle
            Environnement env = new Environnement(500, 500);
            ControlCenter c = new ControlCenter(400, 400, env);

            // Ajouter 7 drones
            for (int i = 0; i < 7; i++) {
                Drone d = new Drone(i);

                // Disperser les drones
                int offsetX = (i % 3) * 80;
                int offsetY = (i / 3) * 80;
                d.setPosDrone(250 + offsetX - 80, 250 + offsetY - 80);

                d.setState(Drone.DroneState.ACTIVE);
                c.addDrone(d);
            }

            // type d'anomalie avec intensité affichée
            Anomalie test= new Anomalie (100, 100, "incendie", 80);
            env.addAnomaly(test);
            env.addAnomaly(new Anomalie(375, 375, "inondation", 60));
            env.spreadAnomaly(test);

            UserInterface ui = new UserInterface(c, env);
            ui.startSimulation();

            JFrame frame = new JFrame("Projet IA Complexes");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(ui);
            frame.pack();
            frame.setSize(850, 950);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
