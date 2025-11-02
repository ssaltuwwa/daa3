package presentation.cli;

import application.services.GraphAnalysisService;
import infrastructure.logging.AnalysisLogger;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        AnalysisLogger logger = AnalysisLogger.getInstance();

        try {
            printBanner();

            String inputFile = "data/input/ass_3_input.json";
            String outputFile = "data/output/ass_3_output.json";

            // Create necessary directories
            createDirectories();

            logger.info("Starting Transportation Network Optimization Analysis");
            logger.info("Input: " + inputFile);
            logger.info("Output: " + outputFile);

            // Perform analysis
            GraphAnalysisService analysisService = new GraphAnalysisService();
            analysisService.performCompleteAnalysis(inputFile, outputFile);

            logger.info("Analysis completed successfully!");

        } catch (Exception e) {
            System.err.println("Fatal error: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static void printBanner() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("      TRANSPORTATION NETWORK OPTIMIZATION ANALYSIS");
        System.out.println("           Minimum Spanning Tree Algorithms");
        System.out.println("=".repeat(80));
        System.out.println("Algorithms: Prim's & Kruskal's");
        System.out.println("Metrics: Execution Time, Operation Count, Total Cost");
        System.out.println("=".repeat(80) + "\n");
    }

    private static void createDirectories() {
        new File("data/input").mkdirs();
        new File("data/output").mkdirs();
    }
}