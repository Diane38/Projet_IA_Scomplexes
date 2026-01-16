public class Anomalie {
    public Anomalie[] anomalies ;
    public int intensite ; // niveau d'intensité de l'anomalie : priorité de 1 à 3
    public boolean state ; // verifie si il y a anomalie ou non (ptet dans environnement aussi)
    public boolean intervention ; // défini le type d'intervention nécessaire : false si humain, true si robot
    public String posAnomalie ; // position de l'anomalie

    public boolean getState(){
        return this.state ;
    }
    public void setState(boolean state){
        state=this.state ;
    }

    public String getPosAnomalie(){
        return this.posAnomalie;
    }
    public void setPosAnomalie(String posAnomalie){
        posAnomalie= this.posAnomalie;
    }

    public int intensite(){
        return this.intensite ;
    }
    public void setIntensite(int intensite){
        intensite = this.intensite;
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
