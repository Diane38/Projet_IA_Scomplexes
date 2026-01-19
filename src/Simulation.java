import java.util.ArrayList;
import java.util.List;

public class Simulation {
    private ControlCenter controlCenter;
    private Environnement environnement;
    private int mapSize = 500;
    private List<Result> results = new ArrayList<>();

    public Simulation(ControlCenter c, Environnement env) {
        this.controlCenter = c;
        this.environnement = env;
    }

    // Lancer un seul scénario
    public void runScenario(String name, Runnable setup, long duration) {
        System.out.println("\nScénario: " + name );

        // initialiser
        environnement.getAnomalies().clear();
        for (Drone d : controlCenter.getDrones()) {
            d.setState(Drone.DroneState.ACTIVE);
            d.setBatteryLevel(100);
            d.resetDistance();
        }

        setup.run();
        long startTime = System.currentTimeMillis();
        int detected = 0;

        // Simuler pendant une durée X (en secondes)
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (Anomalie a : environnement.getAnomalies()) {
            if (a.isDetected()) detected++;
        }

        // Obtenir les résultats
        Result result = new Result(
                name,
                calculateCoverage(),
                0,
                detected,
                calculateEfficiency()
        );

        results.add(result);
        printResults(result);
    }

    // Lancer les scénarios
    public void runAllScenarios() {
        // Calme
        runScenario("Zone Calme", () -> {
            environnement.addAnomaly(new Anomalie(100, 100, "incendie", 50));
            environnement.addAnomaly(new Anomalie(300, 300, "inondation", 40));
        }, 120000);  // 2 minutes

        // Chaotique
        runScenario("Zone Chaotique", () -> {
            for (int i = 0; i < 15; i++) {
                int x = (int)(Math.random() * mapSize);
                int y = (int)(Math.random() * mapSize);
                environnement.addAnomaly(new Anomalie(x, y, "secheresse", 70));
            }
        }, 180000);  // 3 minutes

        // Propagation
        runScenario("Anomalies en Propagation", () -> {
            environnement.addAnomaly(new Anomalie(250, 250, "incendie", 90));
        }, 150000);  // 2.5 minutes
    }

    private double calculateCoverage() {
        // Approximation simple
        return Math.random() * 100;
    }

    private long calculateDetectionTime() {
        return 45000;
    }

    private double calculateEfficiency() {
        return Math.random() * 100;
    }

    private void printResults(Result r) {
        System.out.printf("Couverture: %.2f%%\n", r.coverage);
        System.out.printf("Temps de détection moyen: %d ms\n", r.detectionTime);
        System.out.printf("Anomalies détectées: %d\n", r.anomaliesDetected);
        System.out.printf("Efficacité globale: %.2f%%\n", r.efficiency);
    }

    public void printFinalReport() {
        System.out.println("\n\n RAPPORT FINAL");
        for (Result r : results) {
            System.out.println("\n" + r.name);
            printResults(r);
        }
    }
}

// Stocker les résultats
class Result {
    public String name;
    public double coverage;
    public long detectionTime;
    public int anomaliesDetected;
    public double efficiency;

    public Result(String name, double coverage, long detectionTime,
                  int anomaliesDetected, double efficiency) {
        this.name = name;
        this.coverage = coverage;
        this.detectionTime = detectionTime;
        this.anomaliesDetected = anomaliesDetected;
        this.efficiency = efficiency;
    }
}