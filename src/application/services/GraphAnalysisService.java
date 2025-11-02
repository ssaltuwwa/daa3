package application.services;

import domain.models.*;
import domain.services.*;
import infrastructure.fileio.JsonDataHandler;
import infrastructure.logging.AnalysisLogger;

import java.util.*;

public class GraphAnalysisService {
    private final PrimAlgorithm primAlgorithm;
    private final KruskalAlgorithm kruskalAlgorithm;
    private final JsonDataHandler jsonDataHandler;
    private final AnalysisLogger logger;

    public GraphAnalysisService() {
        this.primAlgorithm = new PrimAlgorithm();
        this.kruskalAlgorithm = new KruskalAlgorithm();
        this.jsonDataHandler = new JsonDataHandler();
        this.logger = AnalysisLogger.getInstance();
    }

    public void performCompleteAnalysis(String inputFilePath, String outputFilePath) {
        try {
            logger.info("Starting MST analysis for transportation network");

            // Load input data
            List<Graph> graphs = jsonDataHandler.loadGraphsFromFile(inputFilePath);
            logger.info("Loaded " + graphs.size() + " graphs from " + inputFilePath);

            // Perform analysis
            AnalysisResult analysisResult = analyzeGraphs(graphs);

            // Save results
            jsonDataHandler.saveAnalysisResults(analysisResult, outputFilePath);
            logger.info("Results saved to " + outputFilePath);

            // Display summary
            displayAnalysisSummary(analysisResult);

        } catch (Exception e) {
            logger.error("Analysis failed: " + e.getMessage());
            throw new RuntimeException("Analysis failed", e);
        }
    }

    private AnalysisResult analyzeGraphs(List<Graph> graphs) {
        List<GraphAnalysis> graphAnalyses = new ArrayList<>();

        for (int i = 0; i < graphs.size(); i++) {
            Graph graph = graphs.get(i);
            logger.info("Analyzing graph " + (i + 1) + ": " + graph);

            GraphAnalysis analysis = analyzeSingleGraph(i + 1, graph);
            graphAnalyses.add(analysis);

            logger.info("Completed analysis for graph " + (i + 1));
        }

        return new AnalysisResult(graphAnalyses);
    }

    private GraphAnalysis analyzeSingleGraph(int graphId, Graph graph) {
        // Run Prim's algorithm
        MSTResult primResult = primAlgorithm.findMST(graph);

        // Run Kruskal's algorithm
        MSTResult kruskalResult = kruskalAlgorithm.findMST(graph);

        // Verify both algorithms produce same cost
        if (primResult.getTotalCost() != kruskalResult.getTotalCost()) {
            logger.warning("MST cost mismatch between algorithms for graph " + graphId);
        }

        return new GraphAnalysis(graphId, graph, primResult, kruskalResult);
    }

    private void displayAnalysisSummary(AnalysisResult result) {
        logger.info("\n" + "=".repeat(80));
        logger.info("MINIMUM SPANNING TREE ANALYSIS SUMMARY");
        logger.info("=".repeat(80));

        for (GraphAnalysis analysis : result.getGraphAnalyses()) {
            logger.info(String.format(
                    "Graph %d: %d districts, %d possible roads | MST Cost: %d",
                    analysis.getGraphId(),
                    analysis.getGraph().getVertexCount(),
                    analysis.getGraph().getEdgeCount(),
                    analysis.getPrimResult().getTotalCost()
            ));

            logger.info(String.format(
                    "  Prim: %d operations, %s ms | Kruskal: %d operations, %s ms",
                    analysis.getPrimResult().getOperationsCount(),
                    analysis.getPrimResult().getFormattedExecutionTime(),
                    analysis.getKruskalResult().getOperationsCount(),
                    analysis.getKruskalResult().getFormattedExecutionTime()
            ));
        }

        // Performance comparison
        logger.info("\nPERFORMANCE COMPARISON:");
        compareAlgorithmsPerformance(result);
    }

    private void compareAlgorithmsPerformance(AnalysisResult result) {
        long totalPrimTime = 0;
        long totalKruskalTime = 0;
        int totalPrimOps = 0;
        int totalKruskalOps = 0;

        for (GraphAnalysis analysis : result.getGraphAnalyses()) {
            totalPrimTime += analysis.getPrimResult().getExecutionTimeNs();
            totalKruskalTime += analysis.getKruskalResult().getExecutionTimeNs();
            totalPrimOps += analysis.getPrimResult().getOperationsCount();
            totalKruskalOps += analysis.getKruskalResult().getOperationsCount();
        }

        logger.info(String.format("Total Prim operations: %,d", totalPrimOps));
        logger.info(String.format("Total Kruskal operations: %,d", totalKruskalOps));
        logger.info(String.format("Average Prim time: %.3f ms", totalPrimTime / (result.getGraphAnalyses().size() * 1_000_000.0)));
        logger.info(String.format("Average Kruskal time: %.3f ms", totalKruskalTime / (result.getGraphAnalyses().size() * 1_000_000.0)));
    }
}