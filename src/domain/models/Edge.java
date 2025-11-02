package domain.models;

import java.util.Objects;

public class Edge {
    private final Vertex from;
    private final Vertex to;
    private final int weight;

    public Edge(Vertex from, Vertex to, int weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    public Edge(Vertex from, Vertex to) {
        this(from, to, 1);
    }

    public Vertex getFrom() { return from; }
    public Vertex getTo() { return to; }
    public int getWeight() { return weight; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Edge)) return false;
        Edge edge = (Edge) o;
        return weight == edge.weight &&
                Objects.equals(from, edge.from) &&
                Objects.equals(to, edge.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, weight);
    }

    @Override
    public String toString() {
        return String.format("%s - %s (%d)", from, to, weight);
    }
}