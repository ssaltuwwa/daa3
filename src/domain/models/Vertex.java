package domain.models;

import java.util.Objects;

public class Vertex {
    private final String id;
    private final String name;

    public Vertex(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Vertex(String id) {
        this(id, id);
    }

    public String getId() { return id; }
    public String getName() { return name; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vertex)) return false;
        Vertex vertex = (Vertex) o;
        return Objects.equals(id, vertex.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return name != null ? name : id;
    }
}