public class ControlCenter {
    public ControlCenter center ; // un seul centre de controle
    public String posBase ;  // position/coordonnées de la base
    public String datasCenter;
    public void setPosBase(String posBase) {
        posBase = this.posBase;
    }
    public String getPosBase(){
        return this.posBase;
    }
    public void setDatasCenter(String datasCenter) {
        datasCenter = this.datasCenter;
    }
    public String getData(){
        return this.datasCenter;
    }

    //TODO : renvoie true si les données ont été reçues dans la base
    public boolean datasReceived(Drone d) {
        if (d.getData() == getData()) {
            return true;
        }
        return false ;
    }

    //TODO : renvoie true si les drones ont reçus leur mise a jour
    // la mise à jour est reçue seulement quand les drones sont a la base
    public boolean updateDrones(Drone d) {
        if (d.getPosDrone() == getPosBase()){
            return true ;
        }
        return false ;
    }
}
