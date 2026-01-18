import java.util.ArrayList;
import java.util.List;

public class ControlCenter {
    private String datasCenter;
    private List<Drone> drones;

    public ControlCenter() {
        this.datasCenter = "";
        this.drones = new ArrayList<>();
    }

    // ajoute un drone à la liste
    public void addDrone(Drone d) {
        drones.add(d);
    }

    public void removeDrone(Drone d) {
        drones.remove(d);
    }

    public List<Drone> getDrones() {
        return drones;
    }

    //renvoie true si les données ont été reçues dans la base
    public void datasReceived(Drone d) {
        if(drones.contains(d)) {
            this.datasCenter += d.getData();
        }
    }
}
