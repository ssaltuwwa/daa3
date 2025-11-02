package domain.services;

import domain.models.*;
import java.util.*;

public class PrimAlgorithm implements MSTAlgorithm {
    private int operationsCount;

    @Override
    public MSTResult findMST(Graph graph) {
        operationsCount = 0;
        long startTime = System.nanoTime();

        if (graph.getVertexCount() == 0) {
            return new MSTResult(Collections.emptyList(), 0, 0, 0);
        }

        Set<Vertex> visited = new HashSet<>();
        List<Edge> mstEdges = new ArrayList<>();
        int totalCost = 0;

        // Use priority queue for efficient minimum edge selection
        PriorityQueue<Edge> minHeap = new PriorityQueue<>(Comparator.comparingInt(Edge::getWeight));
        operationsCount++; // Priority queue creation

        // Start with first vertex
        Vertex start = graph.getVertices().iterator().next();
        visited.add(start);
        operationsCount++;

        // Add all edges from start vertex
        minHeap.addAll(graph.getEdgesFromVertex(start));
        operationsCount += graph.getEdgesFromVertex(start).size();

        while (!minHeap.isEmpty() && visited.size() < graph.getVertexCount()) {
            Edge minEdge = minHeap.poll();
            operationsCount++;

            Vertex nextVertex = getUnvisitedVertex(minEdge, visited);
            if (nextVertex != null) {
                visited.add(nextVertex);
                mstEdges.add(minEdge);
                totalCost += minEdge.getWeight();
                operationsCount += 3;

                // Add edges from the new vertex
                for (Edge edge : graph.getEdgesFromVertex(nextVertex)) {
                    if (!visited.contains(edge.getTo())) {
                        minHeap.add(edge);
                        operationsCount++;
                    }
                }
                operationsCount += graph.getEdgesFromVertex(nextVertex).size();
            }
        }

        long endTime = System.nanoTime();
        return new MSTResult(mstEdges, totalCost, operationsCount, endTime - startTime);
    }

    private Vertex getUnvisitedVertex(Edge edge, Set<Vertex> visited) {
        if (visited.contains(edge.getFrom()) && !visited.contains(edge.getTo())) {
            return edge.getTo();
        } else if (visited.contains(edge.getTo()) && !visited.contains(edge.getFrom())) {
            return edge.getFrom();
        }
        return null;
    }

    @Override
    public String getAlgorithmName() {
        return "Prim's Algorithm";
    }

    @Override
    public String getAlgorithmComplexity() {
        return "O(E log V) with binary heap";
    }
}