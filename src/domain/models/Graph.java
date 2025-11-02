package domain.models;

import java.util.*;

public class Graph {
    private final Map<String, Vertex> vertices;
    private final List<Edge> edges;
    private final boolean directed;

    public Graph(boolean directed) {
        this.vertices = new HashMap<>();
        this.edges = new ArrayList<>();
        this.directed = directed;
    }

    public Graph() {
        this(false);
    }

    public void addVertex(String id) {
        vertices.putIfAbsent(id, new Vertex(id));
    }

    public void addVertex(Vertex vertex) {
        vertices.putIfAbsent(vertex.getId(), vertex);
    }

    public void addEdge(String from, String to, int weight) {
        if (!vertices.containsKey(from) || !vertices.containsKey(to)) {
            throw new IllegalArgumentException("Both vertices must exist in the graph");
        }

        Edge edge = new Edge(vertices.get(from), vertices.get(to), weight);
        edges.add(edge);

        if (!directed) {
            Edge reverseEdge = new Edge(vertices.get(to), vertices.get(from), weight);
            edges.add(reverseEdge);
        }
    }

    public void addEdge(Edge edge) {
        edges.add(edge);
        if (!directed) {
            edges.add(new Edge(edge.getTo(), edge.getFrom(), edge.getWeight()));
        }
    }

    // Getters
    public Collection<Vertex> getVertices() { return Collections.unmodifiableCollection(vertices.values()); }
    public List<Edge> getEdges() { return Collections.unmodifiableList(edges); }
    public boolean isDirected() { return directed; }
    public int getVertexCount() { return vertices.size(); }
    public int getEdgeCount() { return directed ? edges.size() : edges.size() / 2; }

    public Vertex getVertex(String id) {
        return vertices.get(id);
    }

    public List<Edge> getEdgesFromVertex(Vertex vertex) {
        List<Edge> result = new ArrayList<>();
        for (Edge edge : edges) {
            if (edge.getFrom().equals(vertex)) {
                result.add(edge);
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return String.format("Graph{vertices=%d, edges=%d, directed=%s}",
                vertices.size(), getEdgeCount(), directed);
    }
}