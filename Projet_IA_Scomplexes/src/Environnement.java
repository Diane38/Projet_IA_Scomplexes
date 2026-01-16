public class Environnement {
    public boolean state ; // etat actuel de l'environnement
    public String[] typeState = {"incendie", "inondation", "secheresse"} ; // type d'etat de l'environnement

    public boolean getState(){
        return this.state;
    }
    public void setState(boolean state){
        state=this.state ;
    }
}
