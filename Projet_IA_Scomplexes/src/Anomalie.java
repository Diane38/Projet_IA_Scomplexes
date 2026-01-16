public class Anomalie {
    private int posX, posY;
    private String type;
    private int intensity; // entre 0 et 100


    public int getPosX() { return posX; }
    public int getPosY() { return posY; }
    public String getType() { return type; }
    public int getIntensity() { return intensity; }
    public void setIntensity(int intensity) { this.intensity = intensity; }

    // défini une anomalie
    public Anomalie(int posX, int posY, String type, int intensity) {
        this.posX = posX;
        this.posY = posY;
        this.type = type;
        this.intensity = intensity;
    }
}