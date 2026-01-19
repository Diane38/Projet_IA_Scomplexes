import java.util.ArrayList;
import java.util.List;

public class Drone {
    public enum DroneState { ACTIVE, INACTIVE, CHARGING, ANALYZING, RETURNING_TO_BASE } ;
    private DroneState state;
    public String datas ; // données récoltées
    private int posX;
    private int posY;
    private int batteryLevel;        // 0 à 100 (ou temps en secondes)
    private long activationTime;     // quand le drone a été activé
    private static final int MAX_ACTIVE_TIME = 30 * 60 * 1000; // 30 min en ms
    private static final int RECHARGE_TIME = 10 * 60 * 1000;   // 10 min en ms
    private int droneId;
    private int destX;
    private int destY;
    private boolean isMoving = false;
    private List<Anomalie> anomalieKnown;
    Environnement env = new Environnement(500, 500);
    ControlCenter c = new ControlCenter(400, 400, env);

    public Drone(int droneId) {
        anomalieKnown= new ArrayList<>();
        this.droneId = droneId;
        this.state = DroneState.INACTIVE;  // ← Initialiser l'état
        this.batteryLevel = 100;
        this.datas = "";
        this.posX = 0;
        this.posY = 0;
        this.activationTime = System.currentTimeMillis();
    }

    public void setState(DroneState state) {
        this.state = state;
    }
    public DroneState getState() {
        return this.state;
    }
    public void setDatas(String datas) {
         this.datas = datas;
    }
    public String getData(){
        return this.datas;
    }
    public void setPosDrone(int posX, int posY){
         this.posX=posX;
         this.posY=posY;
    }
    public int getPosX(){
        return this.posX;
    }
    public int getPosY(){
        return this.posY;
    }
    public void setBatteryLevel(int batteryLevel) {this.batteryLevel = batteryLevel;}
    public int getBatteryLevel() {return batteryLevel;}
    public int getDroneId() {return droneId;}

    //renvoie true si les données du centre ont été envoyées
    // le drone ne peut envoyer qu'en dehors de la base
    public boolean sendDatas(ControlCenter c) {
        if (getPosX() != c.getPosX() || getPosY() != c.getPosY()) {
            return true ;
        }
        return false ;
    }

    // si la batterie du drone est vide, ou qu'il est actif depuis plus
    // de 30 minutes, il doit recharger
    public boolean needsRecharge() {
        return batteryLevel <= 0 || (System.currentTimeMillis() - activationTime) > MAX_ACTIVE_TIME;
    }
    // analyse d'une anomalie : le drone récolte les données
    public void analyzeAnomaly(Anomalie a) {
        this.state = DroneState.ANALYZING;
        anomalieKnown.add(a);
        // ajouter les données de l'anomalie
        this.datas = a.toString();
        returnToBase(c);
        //System.out.println(a.toString());
    }

    // le drone revient à la base
    public void returnToBase(ControlCenter c) {
        this.state = DroneState.RETURNING_TO_BASE;
        moveTo(c.getPosX(), c.getPosY());
    }

    // simule la perte de batterie au fur et à mesure du temps
    public void updateBattery(long deltaTime) {
        if (state == DroneState.CHARGING) {
            batteryLevel = Math.min(100, batteryLevel + (int)(deltaTime / 1000));
        } else if (state == DroneState.ACTIVE) {
            batteryLevel -= (int)(deltaTime / 1000) / 60;
        }
    }

    // Déplacer le drone à sa destination
    public void moveTo(int targetX, int targetY) {
        this.destX = targetX;
        this.destY = targetY;
        this.isMoving = true;
    }

    // Avancer vers la destination
    public void updatePosition() {
        if (!isMoving) return;
        if (posX == destX && posY == destY) {
            isMoving = false;
            return;
        }
        if (posX < destX) posX++;
        else if (posX > destX) posX--;

        if (posY < destY) posY++;
        else if (posY > destY) posY--;
    }
    
    public boolean isMoving() {
        return isMoving;
    }

    public List<Anomalie> getAnomalieKnown() {
        return anomalieKnown;
    }
}
