package infrastructure.fileio;

import domain.models.*;
import application.services.AnalysisResult;
import application.services.GraphAnalysis;
import infrastructure.logging.AnalysisLogger;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.*;

public class JsonDataHandler {
    private final AnalysisLogger logger;

    public JsonDataHandler() {
        this.logger = AnalysisLogger.getInstance();
    }

    public List<Graph> loadGraphsFromFile(String filePath) throws IOException {
        logger.info("Loading graphs from: " + filePath);
        String content = new String(Files.readAllBytes(Paths.get(filePath)));
        return parseGraphsFromJson(content);
    }

    public void saveAnalysisResults(AnalysisResult result, String filePath) throws IOException {
        logger.info("Saving results to: " + filePath);
        String json = convertToOutputJson(result);
        Files.write(Paths.get(filePath), json.getBytes());
    }

    private List<Graph> parseGraphsFromJson(String json) {
        List<Graph> graphs = new ArrayList<>();

        Pattern graphPattern = Pattern.compile(
                "\\{\\s*\"id\"\\s*:\\s*(\\d+).*?\"nodes\"\\s*:\\s*\\[(.*?)\\].*?\"edges\"\\s*:\\s*\\[(.*?)\\]\\s*\\}",
                Pattern.DOTALL
        );

        Matcher graphMatcher = graphPattern.matcher(json);

        while (graphMatcher.find()) {
            int graphId = Integer.parseInt(graphMatcher.group(1));
            String nodesStr = graphMatcher.group(2);
            String edgesStr = graphMatcher.group(3);

            Graph graph = createGraphFromData(graphId, nodesStr, edgesStr);
            graphs.add(graph);

            logger.debug("Parsed graph " + graphId + " with " + graph.getVertexCount() + " vertices and " + graph.getEdgeCount() + " edges");
        }

        return graphs;
    }

    private Graph createGraphFromData(int graphId, String nodesStr, String edgesStr) {
        Graph graph = new Graph(false); // Undirected for MST

        // Parse nodes
        List<String> nodeIds = parseStringArray(nodesStr);
        for (String nodeId : nodeIds) {
            graph.addVertex(nodeId);
        }

        // Parse edges
        List<EdgeData> edgeDataList = parseEdges(edgesStr);
        for (EdgeData edgeData : edgeDataList) {
            graph.addEdge(edgeData.from, edgeData.to, edgeData.weight);
        }

        return graph;
    }

    private List<String> parseStringArray(String arrayStr) {
        List<String> result = new ArrayList<>();
        Pattern pattern = Pattern.compile("\"([^\"]+)\"");
        Matcher matcher = pattern.matcher(arrayStr);

        while (matcher.find()) {
            result.add(matcher.group(1));
        }
        return result;
    }

    private List<EdgeData> parseEdges(String edgesStr) {
        List<EdgeData> edges = new ArrayList<>();
        Pattern edgePattern = Pattern.compile(
                "\\{\\s*\"from\"\\s*:\\s*\"([^\"]+)\".*?\"to\"\\s*:\\s*\"([^\"]+)\".*?\"weight\"\\s*:\\s*(\\d+)\\s*\\}"
        );

        Matcher edgeMatcher = edgePattern.matcher(edgesStr);

        while (edgeMatcher.find()) {
            String from = edgeMatcher.group(1);
            String to = edgeMatcher.group(2);
            int weight = Integer.parseInt(edgeMatcher.group(3));
            edges.add(new EdgeData(from, to, weight));
        }
        return edges;
    }

    private String convertToOutputJson(AnalysisResult result) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n  \"results\": [\n");

        List<GraphAnalysis> analyses = result.getGraphAnalyses();
        for (int i = 0; i < analyses.size(); i++) {
            GraphAnalysis analysis = analyses.get(i);
            sb.append(convertGraphAnalysisToJson(analysis));
            if (i < analyses.size() - 1) {
                sb.append(",");
            }
            sb.append("\n");
        }

        sb.append("  ]\n}");
        return sb.toString();
    }

    private String convertGraphAnalysisToJson(GraphAnalysis analysis) {
        StringBuilder sb = new StringBuilder();
        sb.append("    {\n");
        sb.append("      \"graph_id\": ").append(analysis.getGraphId()).append(",\n");
        sb.append("      \"input_stats\": {\n");
        sb.append("        \"vertices\": ").append(analysis.getGraph().getVertexCount()).append(",\n");
        sb.append("        \"edges\": ").append(analysis.getGraph().getEdgeCount()).append("\n");
        sb.append("      },\n");
        sb.append("      \"prim\": ").append(convertMSTResultToJson(analysis.getPrimResult())).append(",\n");
        sb.append("      \"kruskal\": ").append(convertMSTResultToJson(analysis.getKruskalResult())).append("\n");
        sb.append("    }");
        return sb.toString();
    }

    private String convertMSTResultToJson(MSTResult result) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        sb.append("        \"mst_edges\": [\n");

        List<Edge> edges = result.getMstEdges();
        for (int i = 0; i < edges.size(); i++) {
            Edge edge = edges.get(i);
            sb.append("          {\"from\": \"").append(edge.getFrom().getId()).append("\", ");
            sb.append("\"to\": \"").append(edge.getTo().getId()).append("\", ");
            sb.append("\"weight\": ").append(edge.getWeight()).append("}");
            if (i < edges.size() - 1) sb.append(",");
            sb.append("\n");
        }

        sb.append("        ],\n");
        sb.append("        \"total_cost\": ").append(result.getTotalCost()).append(",\n");
        sb.append("        \"operations_count\": ").append(result.getOperationsCount()).append(",\n");
        sb.append("        \"execution_time_ms\": ").append(String.format("%.2f", result.getExecutionTimeMs())).append("\n");
        sb.append("      }");
        return sb.toString();
    }

    private static class EdgeData {
        final String from;
        final String to;
        final int weight;

        EdgeData(String from, String to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }
    }
}