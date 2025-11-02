package domain.services;

import domain.models.Graph;
import domain.models.MSTResult;

public interface MSTAlgorithm {
    MSTResult findMST(Graph graph);
    String getAlgorithmName();
    String getAlgorithmComplexity();
}