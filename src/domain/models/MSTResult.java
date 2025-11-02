package domain.models;

import java.util.List;

public class MSTResult {
    private final List<Edge> mstEdges;
    private final int totalCost;
    private final int operationsCount;
    private final long executionTimeNs;

    public MSTResult(List<Edge> mstEdges, int totalCost, int operationsCount, long executionTimeNs) {
        this.mstEdges = mstEdges;
        this.totalCost = totalCost;
        this.operationsCount = operationsCount;
        this.executionTimeNs = executionTimeNs;
    }

    // Getters
    public List<Edge> getMstEdges() { return mstEdges; }
    public int getTotalCost() { return totalCost; }
    public int getOperationsCount() { return operationsCount; }
    public long getExecutionTimeNs() { return executionTimeNs; }
    public double getExecutionTimeMs() { return executionTimeNs / 1_000_000.0; }

    public String getFormattedExecutionTime() {
        return String.format("%.3f", getExecutionTimeMs());
    }
}