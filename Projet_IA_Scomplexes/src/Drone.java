public class Drone {
    public enum DroneState { ACTIVE, INACTIVE, CHARGING, ANALYZING, RETURNING_TO_BASE } ;
    private DroneState state;
    public String datas ; // données récoltées
    private String id;
    private int batteryLevel;        // 0 à 100 (ou temps en secondes)
    private long activationTime;     // quand le drone a été activé
    private static final int MAX_ACTIVE_TIME = 30 * 60 * 1000; // 30 min en ms
    private static final int RECHARGE_TIME = 10 * 60 * 1000;   // 10 min en ms
    private boolean isMoving = false;

    public Drone(String id) {
        this.id = id;
        this.state = DroneState.INACTIVE;
        this.batteryLevel = 100;
        this.activationTime = System.currentTimeMillis();
        datas = "";
    }

    public void setState(DroneState state) {
        // Implémentation pour changer l'état du drone
        this.state=state;
    }
    public DroneState getState() {
        return this.state;
    }
    public void setDatas(String datas) {
         this.datas =datas;
    }
    public String getData(){
        return this.datas ;
    }
    public void setBatteryLevel(int batteryLevel) {this.batteryLevel = batteryLevel;}
    public int getBatteryLevel() {return batteryLevel;}
    public String getDroneId() {return id;}
    public boolean needsRecharge() {
        return batteryLevel <= 0 || (System.currentTimeMillis() - activationTime) > MAX_ACTIVE_TIME;
    }
    public void updateBattery(long deltaTime) {
        if (state == DroneState.CHARGING) {
            batteryLevel = Math.min(100, batteryLevel + (int)(deltaTime / 1000));
        } else if (state == DroneState.ACTIVE) {
            batteryLevel -= (int)(deltaTime / 1000) / 60;
        }
    }
    public boolean isMoving() {
        return isMoving;
    }

     // le drone revient à la base
    public void returnToBase() {
        this.state = DroneState.RETURNING_TO_BASE;
    }

     // analyse d'une anomalie : le drone récolte les données
    public void analyzeAnomaly(Anomalie a) {
        this.state = DroneState.ANALYZING;
        // ajouter les données de l'anomalie
        this.datas = a.toString();
    }

}