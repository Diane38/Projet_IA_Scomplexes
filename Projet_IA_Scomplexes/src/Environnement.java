import java.util.List;
import java.util.ArrayList;
public class Environnement {
    private int mapWidth;
    private int mapHeight;
    private List<Anomalie> anomalies;
    private long lastUpdateTime;

    public Environnement(int width, int height) {
        this.mapWidth = width;
        this.mapHeight = height;
        this.anomalies = new ArrayList<>();
        this.lastUpdateTime = System.currentTimeMillis();
    }

    // Ajouter une anomalie à la carte
    public void addAnomaly(Anomalie a) {
        anomalies.add(a);
    }

    // Obtenir les anomalies
    public List<Anomalie> getAnomalies() {
        return anomalies;
    }

    // Trouver l'anomalie la plus proche
    public Anomalie findNearbyAnomaly(Drone d, int radius) {
        for (Anomalie a : anomalies) {
            double dist = Math.sqrt(
                    Math.pow(d.getPosX() - a.getPosX(), 2) +
                            Math.pow(d.getPosY() - a.getPosY(), 2)
            );
            if (dist <= radius) {
                return a;
            }
        }
        return null;
    }

    // Simuler l'évolution de l'environnement
    public void update(long deltaTime) {
        // Créer une copie pour modifier sans problème
        List<Anomalie> anomaliesCopy = new ArrayList<>(anomalies);

        for (Anomalie a : anomaliesCopy) {
            a.evolve();
        }

        // Apparition aléatoire
        if (Math.random() < 0.01) {
            createRandomAnomaly();
        }

        // Supprimer les anomalies disparues
        anomalies.removeIf(a -> a.getIntensity() <= 0);

        lastUpdateTime = System.currentTimeMillis();
    }
    // Créer une anomalie aléatoire
    private void createRandomAnomaly() {
        int x = (int)(Math.random() * mapWidth);
        int y = (int)(Math.random() * mapHeight);
        String[] types = {"incendie", "inondation", "secheresse"};
        String type = types[(int)(Math.random() * types.length)];

        Anomalie a = new Anomalie(x, y, type, 100);
        anomalies.add(a);
    }

    // Propagation d'anomalie
    private void spreadAnomaly(Anomalie a) {
        if (Math.random() < 0.1) { // 10% de chance de propagation
            int newX = a.getPosX() + (int)(Math.random() * 5 - 2); // -2 à +2
            int newY = a.getPosY() + (int)(Math.random() * 5 - 2);

            // Vérifier que c'est dans les limites
            if (newX >= 0 && newX < mapWidth && newY >= 0 && newY < mapHeight) {
                Anomalie newAnomaly = new Anomalie(newX, newY, a.getType(), a.getIntensity() / 2);
                anomalies.add(newAnomaly);
            }
        }
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }
}