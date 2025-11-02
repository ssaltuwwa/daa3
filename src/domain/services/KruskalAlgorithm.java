package domain.services;

import domain.models.*;
import java.util.*;

public class KruskalAlgorithm implements MSTAlgorithm {
    private int operationsCount;

    @Override
    public MSTResult findMST(Graph graph) {
        operationsCount = 0;
        long startTime = System.nanoTime();

        List<Edge> mstEdges = new ArrayList<>();
        int totalCost = 0;

        // Get unique edges (avoid duplicates in undirected graph)
        List<Edge> uniqueEdges = getUniqueEdges(graph);
        operationsCount += uniqueEdges.size();

        // Sort edges by weight
        uniqueEdges.sort(Comparator.comparingInt(Edge::getWeight));
        operationsCount += (int) (uniqueEdges.size() * Math.log(uniqueEdges.size())); // Sort operations

        DisjointSet disjointSet = new DisjointSet();

        // Initialize disjoint sets
        for (Vertex vertex : graph.getVertices()) {
            disjointSet.makeSet(vertex);
            operationsCount++;
        }

        // Process edges in sorted order
        for (Edge edge : uniqueEdges) {
            operationsCount++;

            if (mstEdges.size() == graph.getVertexCount() - 1) {
                break; // MST complete
            }

            Vertex fromRoot = disjointSet.find(edge.getFrom());
            Vertex toRoot = disjointSet.find(edge.getTo());
            operationsCount += 2;

            if (!fromRoot.equals(toRoot)) {
                mstEdges.add(edge);
                totalCost += edge.getWeight();
                disjointSet.union(edge.getFrom(), edge.getTo());
                operationsCount += 3;
            }
        }

        long endTime = System.nanoTime();
        return new MSTResult(mstEdges, totalCost, operationsCount, endTime - startTime);
    }

    private List<Edge> getUniqueEdges(Graph graph) {
        Set<String> edgeKeys = new HashSet<>();
        List<Edge> uniqueEdges = new ArrayList<>();

        for (Edge edge : graph.getEdges()) {
            String key1 = edge.getFrom().getId() + "-" + edge.getTo().getId();
            String key2 = edge.getTo().getId() + "-" + edge.getFrom().getId();

            if (!edgeKeys.contains(key1) && !edgeKeys.contains(key2)) {
                uniqueEdges.add(edge);
                edgeKeys.add(key1);
            }
        }
        return uniqueEdges;
    }

    @Override
    public String getAlgorithmName() {
        return "Kruskal's Algorithm";
    }

    @Override
    public String getAlgorithmComplexity() {
        return "O(E log E) with union-find";
    }
}