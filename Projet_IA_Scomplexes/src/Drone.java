public class Drone {
    public boolean state ; // etat du drone : actif ou inactif
    public String typeState ; // type d'etat du drone inactif : en recharge, en analyse
    public String datas ; // données récoltées
    private int posX;
    private int posY;

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
