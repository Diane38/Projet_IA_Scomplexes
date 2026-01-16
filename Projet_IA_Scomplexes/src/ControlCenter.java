public class ControlCenter {
    public String posBase ;  // position/coordonnées de la base
    public String datasCenter;
    public void setPosBase(String posBase) {
        this.posBase=posBase;
    }
    public String getPosBase(){
        return this.posBase;
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
        if (d.getPosDrone() == getPosBase()){
            return true ;
        }
        return false ;
    }
}
