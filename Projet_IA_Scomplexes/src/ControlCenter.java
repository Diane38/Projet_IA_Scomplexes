public class ControlCenter {
    private int posX ;
    private int posY;
    public String datasCenter;
    public void setPosX(int posX) {
        this.posX=posX;
    }
    public void setPosY(int posY) {
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

    //renvoie true si les données ont été reçues dans la base
    public boolean datasReceived(Drone d) {
        if (d.getData() == getData()) {
            return true;
        }
        return false ;
    }

    //renvoie true si les drones ont reçus leur mise a jour
    // la mise à jour est reçue seulement quand les drones sont a la base
    public boolean updateDrones(Drone d) {
        if (d.getPosY() == getPosY() && d.getPosX() == getPosX()){
            return true ;
        }
        return false ;
    }
}
