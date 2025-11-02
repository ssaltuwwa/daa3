package application.services;

import java.util.Collections;
import java.util.List;

public class AnalysisResult {
    private final List<GraphAnalysis> graphAnalyses;

    public AnalysisResult(List<GraphAnalysis> graphAnalyses) {
        this.graphAnalyses = Collections.unmodifiableList(graphAnalyses);
    }

    public List<GraphAnalysis> getGraphAnalyses() {
        return graphAnalyses;
    }
}