import java.util.ArrayList;
import java.util.List;

public class ControlCenter {
    private int posX, posY;
    private String datasCenter;
    private List<Drone> drones;
    private Environnement env;

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

    public ControlCenter(int posX, int posY, Environnement env) {
        this.posX = posX;
        this.posY = posY;
        this.drones = new ArrayList<>();
        this.env = env;
        this.datasCenter = "";
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
            d.updateBattery(deltaTime);
            d.updatePosition();

            if (d.needsRecharge()) {
                d.setState(Drone.DroneState.RETURNING_TO_BASE);
            }
            switch(d.getState()) {
                case RETURNING_TO_BASE:
                    if (d.getPosX() != posX || d.getPosY() != posY) {
                        d.moveTo(posX, posY);  // Aller à la base
                    } else {
                        d.setState(Drone.DroneState.CHARGING);
                        datasReceived(d);
                    }
                    break;

                case CHARGING:
                    if (d.getBatteryLevel() >= 100) {
                        d.setState(Drone.DroneState.ACTIVE);
                    }
                    break;

                case ACTIVE:
                    Anomalie nearbyAnomaly = env.findNearbyAnomaly(d, 100);

                    if (nearbyAnomaly != null) {
                        if (!d.isMoving()) {
                            d.moveTo(nearbyAnomaly.getPosX(), nearbyAnomaly.getPosY());
                        }
                        else if (d.getPosX() == nearbyAnomaly.getPosX() &&
                                d.getPosY() == nearbyAnomaly.getPosY()) {
                            d.setState(Drone.DroneState.ANALYZING);
                            d.analyzeAnomaly(nearbyAnomaly);
                            nearbyAnomaly.setDetected(true);
                        }
                    } else if (!d.isMoving()) {
                        randomExploration(d, env.getMapWidth());
                    }
                    break;

                case ANALYZING:
                    d.setState(Drone.DroneState.ACTIVE);
                    break;
            }
        }
        env.update(deltaTime);
    }
    public void randomExploration(Drone d, int mapSize) {
        int newX = (int)(Math.random() * mapSize);
        int newY = (int)(Math.random() * mapSize);
        d.moveTo(newX, newY);
    }

    /*private void moveTowardBase(Drone d) {
        if (d.getPosX() != posX || d.getPosY() != posY) {
            d.moveTo(posX, posY);
        }
    }*/

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
