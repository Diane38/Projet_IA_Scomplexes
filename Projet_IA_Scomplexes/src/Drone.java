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


    public void setState(DroneState state) {
        this.state = state;
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
        // ajouter les données de l'anomalie
        this.datas = a.toString();
    }

    // le drone revient à la base
    public void returnToBase(ControlCenter c) {
        this.state = DroneState.RETURNING_TO_BASE;
        this.posX = c.getPosX();
        this.posY = c.getPosY();
    }

    // simule la perte de batterie au fur et à mesure du temps
    public void updateBattery(long deltaTime) {
        if (state == DroneState.CHARGING) {
            batteryLevel = Math.min(100, batteryLevel + (int)(deltaTime / 1000));
        } else if (state == DroneState.ACTIVE) {
            batteryLevel -= (int)(deltaTime / 1000) / 60;
        }
    }

    public int getBatteryLevel() {
        return batteryLevel;
    }
}
