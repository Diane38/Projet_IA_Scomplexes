public class Drone {
    public enum DroneState { ACTIVE, INACTIVE, CHARGING, ANALYZING, RETURNING_TO_BASE } ;
    private DroneState state;
    public String datas ; // données récoltées
    private int posX;
    private int posY;

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
}
