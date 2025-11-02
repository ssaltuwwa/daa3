# Transportation Network Optimization — Minimum Spanning Tree

> **University Assignment — Design and Analysis of Algorithms**  
> Find an optimal road network (minimum total cost) connecting all city districts using **Prim's** and **Kruskal's** algorithms.

---

## 📋 Project Overview

This project implements **Prim's** and **Kruskal's** algorithms to solve the Minimum Spanning Tree (MST) problem for city transportation networks. The goal is to select the set of roads that connects all districts with the **minimum total construction cost**.

---

## 🎯 Problem Statement

The city administration needs to construct roads connecting all districts such that:

- Every district is reachable from any other district;
- The total construction cost is minimized;
- The solution is modeled as a **weighted undirected graph**.

---

## 🏗️ Project Architecture (Clean Architecture)

```
src/
├── presentation/cli/                # User Interface Layer
│   └── Main.java                    # CLI application entry point
│
├── application/services/            # Business Logic Layer
│   ├── GraphAnalysisService.java    # Coordinates MST analysis
│   ├── GraphAnalysis.java           # Stores algorithm results
│   └── AnalysisResult.java          # Output data structure
│
├── domain/                          # Core Domain Layer
│   ├── models/                      # Business entities
│   │   ├── Graph.java               # Graph data structure
│   │   ├── Vertex.java              # District representation
│   │   ├── Edge.java                # Road representation
│   │   └── MSTResult.java           # Algorithm results
│   │
│   └── services/                    # Business rules
│       ├── MSTAlgorithm.java        # Algorithm interface
│       ├── PrimAlgorithm.java       # Prim's implementation
│       ├── KruskalAlgorithm.java    # Kruskal's implementation
│       └── DisjointSet.java         # Union-Find data structure
│
└── infrastructure/                  # External Concerns
    ├── fileio/
    │   └── JsonDataHandler.java     # JSON file operations
    └── logging/
        └── AnalysisLogger.java      # Structured logging
```

---

## 🚀 Algorithms Implementation

### Prim's Algorithm
- **Time Complexity**: `O(E log V)` using a binary heap (priority queue)
- **Space Complexity**: `O(V)`
- **Approach**: Greedy — grows the MST from an arbitrary starting vertex by always choosing the lightest edge crossing the cut
- **Best for**: **Dense** graphs

### Kruskal's Algorithm
- **Time Complexity**: `O(E log E)` with Union–Find (path compression + union by rank)
- **Space Complexity**: `O(E)`
- **Approach**: Sort edges by weight and add them if they don’t form cycles
- **Best for**: **Sparse** graphs

---

## 📊 Features

### Core Functionality
- ✅ MST calculation using **both** algorithms
- ✅ **Total cost** optimization
- ✅ Graph modeling with vertices and edges
- ✅ **JSON** input/output support

### Analysis & Metrics
- ✅ Operation counting (comparisons, unions, PQ ops)
- ✅ Execution time measurement (milliseconds)
- ✅ Algorithm comparison & verification
- ✅ Detailed logging with timestamps

### Data Management
- ✅ Flexible graph creation & manipulation
- ✅ Weighted **undirected** graphs
- ✅ File-based data persistence
- ✅ Sample input/output datasets

---

## 🛠️ Installation & Setup

### Prerequisites
- **Java JDK 8+**
- **IntelliJ IDEA** (recommended) or any Java IDE

### Clone & Prepare
```bash
# Clone the repository
git clone https://github.com/ssaltuwwa/daa3.git

# Navigate to the project directory
cd daa3

# Create data directories (if needed)
mkdir -p data/input data/output
```

### IDE Setup (IntelliJ IDEA)
1. Open IntelliJ IDEA
2. **Open or Import** → select the project directory
3. The `daa3assign.iml` file will automatically configure the project

### 🏃‍♂️ How to Run

**Method 1 — Via IDE**
1. Open the project in IntelliJ IDEA
2. Navigate to `src/presentation/cli/Main.java`
3. Run the **Main** class
4. Check console output and `data/output/ass_3_output.json`

**Method 2 — Command Line**
```bash
# Compile all Java files
find src -name "*.java" > sources.txt
javac -d out @sources.txt

# Run the application
java -cp out presentation.cli.Main
```

---

## 📁 File Structure

### Input Files
- `data/input/ass_3_input.json` — Sample graph data in JSON format

### Output Files
- `data/output/ass_3_output.json` — Algorithm results and analysis

### Configuration
- `daa3assign.iml` — IntelliJ IDEA module configuration
- `.gitignore` — Git ignore rules for Java projects

---

## 📝 Sample Input Format

```json
{
  "graphs": [
    {
      "id": 1,
      "nodes": ["A", "B", "C", "D", "E"],
      "edges": [
        {"from": "A", "to": "B", "weight": 4},
        {"from": "A", "to": "C", "weight": 3},
        {"from": "B", "to": "C", "weight": 2}
      ]
    }
  ]
}
```

> ℹ️ The project supports multiple graphs per run. Each graph is processed independently.

---

## 📊 Expected Output

```json
{
  "results": [
    {
      "graph_id": 1,
      "input_stats": {
        "vertices": 5,
        "edges": 7
      },
      "prim": {
        "mst_edges": [
          {"from": "A", "to": "C", "weight": 3},
          {"from": "C", "to": "B", "weight": 2}
        ],
        "total_cost": 16,
        "operations_count": 38,
        "execution_time_ms": 4.00
      },
      "kruskal": {
        "mst_edges": [
          {"from": "B", "to": "C", "weight": 2},
          {"from": "A", "to": "C", "weight": 3}
        ],
        "total_cost": 16,
        "operations_count": 53,
        "execution_time_ms": 1.63
      }
    }
  ]
}
```

> ✅ Both algorithms must produce the **same total MST cost** (edge sets may differ if multiple optimal MSTs exist).

---

## 🔬 Performance Analysis

**Metrics Collected**
- **Operation Count**: Key algorithmic operations (comparisons, unions, PQ ops)
- **Execution Time**: Measured in milliseconds
- **Memory Efficiency**: Proxied via operation counting
- **Algorithm Verification**: Cross-validate Prim vs Kruskal totals

**Comparison Features**
- Side-by-side algorithm performance
- Time complexity validation
- Operation efficiency analysis
- Scalability assessment

**Empirical Insights**
- Prim is often faster for **dense** graphs
- Kruskal tends to be more efficient for **sparse** graphs
- Operation count correlates with theoretical complexity

---

## 🧪 Testing & Validation

**Algorithm Verification**
- Both algorithms yield identical **MST total cost**
- Verify **MST properties**: `V - 1` edges & connectivity
- Cross-compare Prim and Kruskal results

**Sample Test Cases**
- **Graph 1**: 5 vertices, 7 edges (moderate)
- **Graph 2**: 4 vertices, 5 edges (simple)
- **Edge cases**: single vertex, disconnected graphs (expect graceful errors)

---

## 👨‍💻 Technical Implementation Details

**Design Patterns Used**
- **Strategy**: `MSTAlgorithm` interface for interchangeable algorithms
- **Factory**: Graph creation from JSON data
- **Singleton**: `AnalysisLogger` for centralized logging
- **Builder**: For complex object construction (where applicable)

**Data Structures**
- **PriorityQueue**: For Prim’s edge selection
- **Disjoint Set (Union-Find)**: For Kruskal’s cycle detection
- **Adjacency List**: Graph representation
- **HashMap / HashSet**: Fast lookups and membership checks

**Error Handling**
- Descriptive **JSON parsing** errors
- **Graph validation** (vertex existence, positive weights)
- **File I/O** exceptions with user-friendly feedback
- Algorithm-specific **edge cases** (e.g., disconnected graphs)
