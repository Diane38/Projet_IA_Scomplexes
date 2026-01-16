public class Drone {
    public boolean state ; // etat du drone : actif ou inactif
    public String typeState ; // type d'etat du drone inactif : en recharge, en analyse
    public String datas ; // données récoltées
    public String posDrone ; // position du drone en temps réel

    public void setDatas(String datas) {
         this.datas =datas;
    }
    public String getData(){
        return this.datas ;
    }
    public void setState(boolean state) {
         this.state =state ;
    }
    public boolean getState(){
        return this.state ;
    }
    public void setPosDrone(String posDrone){
         this.posDrone=posDrone;
    }
    public String getPosDrone(){
        return this.posDrone;
    }

    //renvoie true si les données du centre ont été envoyées
    // le drone ne peut envoyer qu'en dehors de la base
    public boolean sendDatas(ControlCenter c) {
        if (getPosDrone() != c.getPosBase()) {
            return true ;
        }
        return false ;
    }
}
