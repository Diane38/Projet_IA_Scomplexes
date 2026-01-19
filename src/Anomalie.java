public class Anomalie {
    private int posX, posY;
    private String type;
    private int intensity; // entre 0 et 100
    private boolean isDetected;
    public enum InterventionType { ROBOT, HUMAN, NONE }
    private InterventionType interventionNeeded;

    public Anomalie(int posX, int posY, String type, int intensity) {
        this.posX = posX;
        this.posY = posY;
        this.type = type;
        this.intensity = intensity;
        this.isDetected = false;
    }
    public int getPosX() { return posX; }
    public int getPosY() { return posY; }
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
        if (intensity > 0 && intensity < 100) {
            intensity += 0.05;;
        }
        this.interventionNeeded = determineIntervention(type, intensity);
    }

    public void degrade(int amount) {
        intensity -= amount;
    }

    // afficher les informations
    @Override
    public String toString() {
        return String.format(
                "Anomalie: type=%s, pos=(%d,%d), intensity=%d, intervention=%s",
                type, posX, posY, intensity, interventionNeeded
        );
    }

}