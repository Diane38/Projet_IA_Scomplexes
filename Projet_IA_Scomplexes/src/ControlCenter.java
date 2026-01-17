import java.util.ArrayList;
import java.util.List;

public class ControlCenter {
    private int posX, posY;
    private String datasCenter;
    private List<Drone> drones;
    private Environnement environnement;

    public void setPosBase(int posX, int posY){
        this.posX=posX;
        this.posY=posY;
    }
    public int getPosX(){
        return this.posX;
    }
    public int getPosY(){
        return this.posY;
    }
    public void setDatasCenter(String datasCenter) {
        this.datasCenter=datasCenter;
    }
    public String getData(){
        return this.datasCenter;
    }

    public ControlCenter(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
        this.drones = new ArrayList<>();
    }

    // ajoute un drone à la liste
    public void addDrone(Drone d) {
        drones.add(d);
    }

    public List<Drone> getDrones() {
        return drones;
    }

    //renvoie true si les données ont été reçues dans la base
    public void datasReceived(Drone d) {
        if (d.sendDatas(this)) {
            this.datasCenter += d.getData() + "\n";
        }
    }

    //renvoie true si les drones ont reçus leur mise a jour
    // la mise à jour est reçue seulement quand les drones sont a la base
    public void updateDrones(Drone d) {
        if (d.getPosX() == this.posX && d.getPosY() == this.posY) {
            // Envoyer les mises à jour au drone
            d.setDatas(this.datasCenter);
            d.setState(Drone.DroneState.ACTIVE);
        }
    }

    // mise à jour la simulation
    public void updateSimulation(long deltaTime) {
        for (Drone d : drones) {
            // si batterie faible, retour à la base
            if (d.needsRecharge()) {
                d.setState(Drone.DroneState.RETURNING_TO_BASE);
            }

            if (d.getState() == Drone.DroneState.RETURNING_TO_BASE) {
                moveTowardBase(d);
                // Si arrivé à la base, réception des données
                if (d.getPosX() == posX && d.getPosY() == posY) {
                    d.setState(Drone.DroneState.CHARGING);
                    datasReceived(d);
                }
            }
            else if (d.getState() == Drone.DroneState.CHARGING) {
                // Recharger le drone
                // Si batterie pleine
                if (d.getBatteryLevel() >= 100) {
                    d.setState(Drone.DroneState.ACTIVE);
                }
            }
            else if (d.getState() == Drone.DroneState.ACTIVE) {
                Anomalie nearbyAnomaly = environnement.findNearbyAnomaly(d, 50);
                if (nearbyAnomaly != null && d.getState() != Drone.DroneState.ANALYZING) {
                    d.setState(Drone.DroneState.ANALYZING);
                    d.analyzeAnomaly(nearbyAnomaly);
                } else if (d.getState() == Drone.DroneState.ANALYZING) {
                    d.setState(Drone.DroneState.ACTIVE);
                }
            }
        }
        environnement.update(deltaTime);
    }

    private void moveTowardBase(Drone d) {
        // approcher le drone de la base
        int dx = posX - d.getPosX();
        int dy = posY - d.getPosY();

        int newX = d.getPosX() + Integer.compare(dx, 0);
        int newY = d.getPosY() + Integer.compare(dy, 0);

        d.setPosDrone(newX, newY);
    }

    // assigner des zones à la patrouille de drones
    public void assignPatrolZones(int gridSize) {
        int droneIndex = 0;
        for (int x = 0; x < gridSize; x += 10) {
            for (int y = 0; y < gridSize; y += 10) {
                if (droneIndex < drones.size()) {
                    Drone d = drones.get(droneIndex);
                    d.setPosDrone(x, y);
                    droneIndex++;
                }
            }
        }
    }

    // déplacer le drone le plus proche d'une anomalie
    public void sendDrone(Anomalie a) {
        Drone closest = null;
        double minDistance = Double.MAX_VALUE;

        for (Drone d : drones) {
            if (d.getState() == Drone.DroneState.ACTIVE) {
                double distance = calculateDistance(d, a);
                if (distance < minDistance) {
                    minDistance = distance;
                    closest = d;
                }
            }
        }

        if (closest != null) {
            closest.setPosDrone(a.getPosX(), a.getPosY());
            closest.setState(Drone.DroneState.ACTIVE);
        }
    }

    // calcul de la distance entre le drone et l'anomalie
    private double calculateDistance(Drone d, Anomalie a) {
        int dx = d.getPosX() - a.getPosX();
        int dy = d.getPosY() - a.getPosY();
        return Math.sqrt(dx*dx + dy*dy);
    }
}
