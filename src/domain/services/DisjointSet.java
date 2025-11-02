package domain.services;

import domain.models.Vertex;
import java.util.HashMap;
import java.util.Map;

public class DisjointSet {
    private final Map<Vertex, Vertex> parent;
    private final Map<Vertex, Integer> rank;

    public DisjointSet() {
        parent = new HashMap<>();
        rank = new HashMap<>();
    }

    public void makeSet(Vertex vertex) {
        parent.put(vertex, vertex);
        rank.put(vertex, 0);
    }

    public Vertex find(Vertex vertex) {
        if (!parent.get(vertex).equals(vertex)) {
            parent.put(vertex, find(parent.get(vertex))); // Path compression
        }
        return parent.get(vertex);
    }

    public void union(Vertex vertex1, Vertex vertex2) {
        Vertex root1 = find(vertex1);
        Vertex root2 = find(vertex2);

        if (!root1.equals(root2)) {
            // Union by rank
            if (rank.get(root1) < rank.get(root2)) {
                parent.put(root1, root2);
            } else if (rank.get(root1) > rank.get(root2)) {
                parent.put(root2, root1);
            } else {
                parent.put(root2, root1);
                rank.put(root1, rank.get(root1) + 1);
            }
        }
    }
}