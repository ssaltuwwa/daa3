package application.services;

import domain.models.Graph;
import domain.models.MSTResult;

public class GraphAnalysis {
    private final int graphId;
    private final Graph graph;
    private final MSTResult primResult;
    private final MSTResult kruskalResult;

    public GraphAnalysis(int graphId, Graph graph, MSTResult primResult, MSTResult kruskalResult) {
        this.graphId = graphId;
        this.graph = graph;
        this.primResult = primResult;
        this.kruskalResult = kruskalResult;
    }

    // Getters
    public int getGraphId() { return graphId; }
    public Graph getGraph() { return graph; }
    public MSTResult getPrimResult() { return primResult; }
    public MSTResult getKruskalResult() { return kruskalResult; }
}