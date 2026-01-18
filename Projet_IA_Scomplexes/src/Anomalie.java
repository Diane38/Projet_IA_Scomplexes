public class Anomalie {
    private String type;
    private int intensity; // entre 0 et 100
    private boolean isDetected;
    public enum InterventionType { ROBOT, HUMAN, NONE }
    private InterventionType interventionNeeded;

    public Anomalie(String type, int intensity) {
        this.type = type;
        this.intensity = intensity;
        this.isDetected = false;
    }

    public String getType() { return type; }
    public int getIntensity() { return intensity; }
    public void setIntensity(int intensity) { this.intensity = intensity; }
    public boolean isDetected() { return this.isDetected;}
    public void setDetected(boolean detected) {this.isDetected = detected;}
    public InterventionType getInterventionNeeded() {return interventionNeeded;}

     // Déterminer le type d'intervention basé sur le type et l'intensité
    private InterventionType determineIntervention(String type, int intensity) {
        if (intensity < 30) {
            return InterventionType.NONE;
        }

        switch(type) {
            case "incendie":
                return intensity > 70 ? InterventionType.ROBOT : InterventionType.HUMAN;
            case "inondation":
                return intensity > 60 ? InterventionType.ROBOT : InterventionType.HUMAN;
            case "secheresse":
                return InterventionType.HUMAN;  // Toujours humain
            default:
                return InterventionType.NONE;
        }
    }

    // simuler l'évolution de l'anomalie
    public void evolve() {
        if (intensity > 0) {
            intensity -= 5;
        }
        this.interventionNeeded = determineIntervention(type, intensity);
    }
}
