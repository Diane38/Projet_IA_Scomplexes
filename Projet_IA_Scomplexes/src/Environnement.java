import java.util.ArrayList;
import java.util.List;

public class Environnement {

    private List<Anomalie> anomalies;
    private long lastUpdateTime;

    public Environnement(){
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
            if (!a.isDetected()) {
                return a;
            }
        }
        return null;
    }
}
