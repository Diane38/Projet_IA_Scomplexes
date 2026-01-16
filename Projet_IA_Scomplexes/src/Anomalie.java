public class Anomalie {
    public int intensite ; // niveau d'intensité de l'anomalie : priorité de 1 à 3
    public boolean state ; // verifie si il y a anomalie ou non (ptet dans environnement aussi)
    public boolean intervention ; // défini le type d'intervention nécessaire : false si humain, true si robot
    private int posX ; // position de l'anomalie
    private int posY;

    public boolean getState(){
        return this.state ;
    }
    public void setState(boolean state){
        this.state =state ;
    }

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

    public int intensite(){
        return this.intensite ;
    }
    public void setIntensite(int intensite){
         this.intensite =intensite;
    }

    public boolean getIntervention(){
        return this.intervention;
    }
    public void setIntervention(boolean intervention){
        if (state==true) {
            // si il y a anomalie, il faut une intervention
            intervention = getIntervention();
        }
    }
    // TODO : type d'intervention à définir si il y a anomalie


}
