/**
 * CPSC 450, HW-4
 * 
 * NAME: S. Bowers
 * DATE: Fall 2024
 *
 * Basic unit tests breadth-first search and associated algorithms.
 */

package cpsc450;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Map;


class HW4Test {

  //======================================================================
  // Adjacency Matrix Tests
  //======================================================================
  
  //----------------------------------------------------------------------
  // Basic Directed Graph BFS Tests
  
  @Test
  void adjMatrixDirectedNoEdgeGraphBFS() throws Exception {
    Graph g = new AdjMatrix(2);
    Map<Integer,Integer> tree = GraphAlgorithms.bfs(g, 0, true);
    assertEquals(1, tree.size());
    assertEquals(-1, tree.get(0));
  }

  @Test
  void adjMatrixDirectedAllReachableBFS() throws Exception {
    Graph g = new AdjMatrix(4);
    g.addEdge(0, 1);
    g.addEdge(0, 2);
    g.addEdge(2, 3);
    g.addEdge(1, 0);
    Map<Integer,Integer> tree = GraphAlgorithms.bfs(g, 0, true);
    assertEquals(4, tree.size());
    assertEquals(-1, tree.get(0));
    assertEquals(0, tree.get(1));
    assertEquals(0, tree.get(2));
    assertEquals(2, tree.get(3));
    tree = GraphAlgorithms.bfs(g, 1, true);
    assertEquals(4, tree.size());
    assertEquals(1, tree.get(0));
    assertEquals(-1, tree.get(1));
    assertEquals(0, tree.get(2));
    assertEquals(2, tree.get(3));        
  }

  @Test
  void adjMatrixDirectedSomeReachableBFS() throws Exception {
    Graph g = new AdjMatrix(5);
    g.addEdge(0, 1);
    g.addEdge(0, 3);
    g.addEdge(1, 2);
    g.addEdge(1, 4);
    g.addEdge(3, 1);
    g.addEdge(3, 4);
    Map<Integer,Integer> tree = GraphAlgorithms.bfs(g, 3, true);
    assertEquals(4, tree.size());
    assertFalse(tree.containsKey(0));
    assertEquals(3, tree.get(1));
    assertEquals(1, tree.get(2));
    assertEquals(-1, tree.get(3));
    assertEquals(3, tree.get(4));
  }

  @Test
  void adjMatrixDirectedNoneReachableBFS() throws Exception {
    Graph g = new AdjMatrix(5);
    g.addEdge(0, 1);
    g.addEdge(0, 3);
    g.addEdge(1, 2);
    g.addEdge(1, 4);
    g.addEdge(3, 1);
    g.addEdge(3, 4);
    Map<Integer,Integer> tree = GraphAlgorithms.bfs(g, 4, true);
    assertEquals(1, tree.size());
    assertEquals(-1, tree.get(4));
  }

  //----------------------------------------------------------------------
  // Basic Undirected Graph BFS Tests

  @Test
  void adjMatrixUndirectedNoEdgeGraphBFS() {
    Graph g = new AdjMatrix(2);
    Map<Integer,Integer> tree = GraphAlgorithms.bfs(g, 0, false);
    assertEquals(1, tree.size());
    assertEquals(-1, tree.get(0));
  }

  @Test
  void adjMatrixUndirectedAllReachableBFS() {
    Graph g = new AdjMatrix(5);
    g.addEdge(0, 1);
    g.addEdge(0, 2);
    g.addEdge(1, 4);
    g.addEdge(1, 3);
    g.addEdge(3, 4);
    Map<Integer,Integer> tree = GraphAlgorithms.bfs(g, 0, false); 
    assertEquals(5, tree.size());
    assertEquals(-1, tree.get(0));
    assertEquals(0, tree.get(1));
    assertEquals(0, tree.get(2));
    assertEquals(1, tree.get(3));
    assertEquals(1, tree.get(4));
    // reverse the edgs
    g = new AdjMatrix(5);
    g.addEdge(1, 0);
    g.addEdge(2, 0);
    g.addEdge(4, 1);
    g.addEdge(3, 1);
    g.addEdge(4, 3);
    tree = GraphAlgorithms.bfs(g, 0, false);
    assertEquals(5, tree.size());
    assertEquals(-1, tree.get(0));
    assertEquals(0, tree.get(1));
    assertEquals(0, tree.get(2));
    assertEquals(1, tree.get(3));
    assertEquals(1, tree.get(4));
  }

  @Test
  void adjMatrixUndirectedSomeReachableBFS() {
    Graph g = new AdjMatrix(5);
    g.addEdge(0, 1);
    g.addEdge(2, 3);
    g.addEdge(2, 4);
    g.addEdge(3, 4);
    Map<Integer,Integer> tree = GraphAlgorithms.bfs(g, 0, true);
    assertEquals(2, tree.size());
    assertEquals(-1, tree.get(0));
    assertEquals(0, tree.get(1));
    tree = GraphAlgorithms.bfs(g, 2, false);
    assertEquals(3, tree.size());
    assertEquals(-1, tree.get(2));
    assertEquals(2, tree.get(3));
    assertEquals(2, tree.get(4));    
  }

  //----------------------------------------------------------------------
  // Basic Directed Graph Reachability Tests

  @Test  
  void adjMatrixDirectedSourceIsReachableFromSource() {
    Graph g = new AdjMatrix(2);
    g.addEdge(0, 1);
    List<Integer> path = GraphAlgorithms.reachable(g, 0, 0, true);
    assertEquals(1, path.size());
    assertTrue(path.contains(0));
  }

  @Test  
  void adjMatrixDirectedOneHopPathIsReachable() {
    Graph g = new AdjMatrix(2);
    g.addEdge(1, 0);
    List<Integer> path = GraphAlgorithms.reachable(g, 1, 0, true);
    assertEquals(2, path.size());
    assertEquals(1, path.get(0));
    assertEquals(0, path.get(1));
  }

  @Test
  void adjMatrixDirectedTwoHopPathIsReachable() {
    Graph g = new AdjMatrix(4);
    g.addEdge(0, 1);
    g.addEdge(1, 2);
    g.addEdge(2, 3);
    g.addEdge(1, 3);
    List<Integer> path = GraphAlgorithms.reachable(g, 0, 3, true);
    assertEquals(3, path.size());
    assertEquals(0, path.get(0));
    assertEquals(1, path.get(1));
    assertEquals(3, path.get(2));    
  }

  @Test  
  void adjMatrixDirectedPathIsReachableThroughCycle() {
    Graph g = new AdjMatrix(4);
    g.addEdge(0, 1);
    g.addEdge(1, 2);
    g.addEdge(2, 1);
    g.addEdge(2, 3);
    List<Integer> path = GraphAlgorithms.reachable(g, 0, 3, true);
    assertEquals(4, path.size());
    assertEquals(0, path.get(0));
    assertEquals(1, path.get(1));
    assertEquals(2, path.get(2));    
    assertEquals(3, path.get(3));    
  }

  @Test
  void adjMatrixDirectedMultiplePathsOneShortestReachable() {
    Graph g = new AdjMatrix(5);
    g.addEdge(0, 1);
    g.addEdge(0, 2);
    g.addEdge(1, 3);
    g.addEdge(2, 3);
    g.addEdge(2, 4);
    g.addEdge(3, 4);
    List<Integer> path = GraphAlgorithms.reachable(g, 0, 4, true);
    assertEquals(3, path.size());
    assertEquals(0, path.get(0));
    assertEquals(2, path.get(1));
    assertEquals(4, path.get(2));    
  }
  
  @Test  
  void adjMatrixDirectedPathIsNotReachable() {
    Graph g = new AdjMatrix(4);
    g.addEdge(1, 0);
    g.addEdge(0, 4);
    g.addEdge(3, 4);
    List<Integer> path = GraphAlgorithms.reachable(g, 1, 3, true);
    assertEquals(0, path.size());
  }
  
  //----------------------------------------------------------------------
  // Basic Undirected Graph Reachability Tests

  @Test  
  void adjMatrixUndirectedSourceIsReachableFromSource() {
    Graph g = new AdjMatrix(2);
    g.addEdge(0, 1);
    List<Integer> path = GraphAlgorithms.reachable(g, 0, 0, false);
    assertEquals(1, path.size());
    assertTrue(path.contains(0));
  }

  @Test  
  void adjMatrixUndirectedOneHopPathIsReachable() {
    Graph g = new AdjMatrix(2);
    g.addEdge(1, 0);
    List<Integer> path = GraphAlgorithms.reachable(g, 0, 1, false);
    assertEquals(2, path.size());
    assertEquals(0, path.get(0));
    assertEquals(1, path.get(1));
  }

  @Test
  void adjMatrixUndirectedTwoHopPathIsReachable() {
    Graph g = new AdjMatrix(4);
    g.addEdge(1, 0);
    g.addEdge(1, 2);
    g.addEdge(2, 3);
    g.addEdge(3, 1);
    List<Integer> path = GraphAlgorithms.reachable(g, 0, 3, false);
    assertEquals(3, path.size());
    assertEquals(0, path.get(0));
    assertEquals(1, path.get(1));
    assertEquals(3, path.get(2));    
  }

  @Test
  void adjMatrixUndirectedMultiplePathsOneShortestReachable() {
    Graph g = new AdjMatrix(5);
    g.addEdge(0, 1);
    g.addEdge(0, 2);
    g.addEdge(1, 3);
    g.addEdge(2, 3);
    g.addEdge(2, 4);
    g.addEdge(3, 4);
    List<Integer> path = GraphAlgorithms.reachable(g, 0, 4, true);
    assertEquals(3, path.size());
    assertEquals(0, path.get(0));
    assertEquals(2, path.get(1));
    assertEquals(4, path.get(2));    
  }
  
  @Test  
  void adjMatrixUndirectedPathIsNotReachable() {
    Graph g = new AdjMatrix(4);
    g.addEdge(0, 2);
    g.addEdge(2, 0);
    g.addEdge(3, 4);
    List<Integer> path = GraphAlgorithms.reachable(g, 0, 3, false);
    assertEquals(0, path.size());
  }
  
  //----------------------------------------------------------------------
  // Basic Weakly (Undirected) Connected Component Tests

  @Test
  void adjMatrixOneNodeComponent() {
    Graph g = new AdjMatrix(1);
    Map<Integer,Integer> components = GraphAlgorithms.weakComponents(g);
    assertEquals(1, components.size());
    assertEquals(0, components.get(0));
  }

  @Test
  void adjMatrixTwoNodeComponent() {
    Graph g = new AdjMatrix(2);
    g.addEdge(1, 0);
    Map<Integer,Integer> components = GraphAlgorithms.weakComponents(g);
    assertEquals(2, components.size());
    assertEquals(0, components.get(0));
    assertEquals(0, components.get(1));
  }

  @Test
  void adjMatrixTwoNodeTwoComponents() {
    Graph g = new AdjMatrix(2);
    Map<Integer,Integer> components = GraphAlgorithms.weakComponents(g);
    assertEquals(2, components.size());
    assertTrue(components.get(0) != components.get(1));
  }

  @Test
  void adjMatrixSevenNodeThreeComponents() {
    Graph g = new AdjMatrix(7);
    g.addEdge(3, 0);
    g.addEdge(3, 1);
    g.addEdge(1, 0);
    g.addEdge(5, 4);
    g.addEdge(4, 6);
    g.addEdge(6, 5);
    Map<Integer,Integer> components = GraphAlgorithms.weakComponents(g);
    assertEquals(7, components.size());
    assertEquals(0, components.get(0));
    assertEquals(0, components.get(1));
    assertEquals(1, components.get(2));    
    assertEquals(0, components.get(3));
    assertEquals(2, components.get(4));
    assertEquals(2, components.get(5));
    assertEquals(2, components.get(6));
  }
    
  //----------------------------------------------------------------------
  // Basic Directed Graph Diameter Tests

  @Test
  void adjMatrixDirectedDiameterNoEdgeGraph() {
    Graph g = new AdjMatrix(1);
    assertEquals(0, GraphAlgorithms.diameter(g, true));
  }

  @Test
  void adjMatrixDirectedDiameterOneEdgeGraph() {
    Graph g = new AdjMatrix(2);
    g.addEdge(0, 1);
    assertEquals(1, GraphAlgorithms.diameter(g, true));
  }

  @Test
  void adjMatrixDirectedDiameterTwoOneEdgePaths() {
    Graph g = new AdjMatrix(3);
    g.addEdge(0, 1);
    g.addEdge(2, 1);
    assertEquals(1, GraphAlgorithms.diameter(g, true));
  }

  @Test
  void adjMatrixDirectedDiameterTwoGraphWithTwoPaths() {
    Graph g = new AdjMatrix(3);
    g.addEdge(0, 1);
    g.addEdge(1, 2);
    g.addEdge(0, 2);
    assertEquals(1, GraphAlgorithms.diameter(g, true));
  }

  @Test
  void adjMatrixDirectedDiameterThreeSixVertexGraph() {
    Graph g = new AdjMatrix(6);
    g.addEdge(1, 0);
    g.addEdge(1, 2);
    g.addEdge(1, 3);
    g.addEdge(2, 4);
    g.addEdge(3, 2);
    g.addEdge(4, 5);
    assertEquals(3, GraphAlgorithms.diameter(g, true));
  }
  
  //----------------------------------------------------------------------
  // Basic Undirected Graph Diameter Tests

  @Test
  void adjMatrixUndirectedDiameterNoEdgeGraph() {
    Graph g = new AdjMatrix(1);
    assertEquals(0, GraphAlgorithms.diameter(g, false));
  }

  @Test
  void adjMatrixUndirectedDiameterOneEdgeGraph() {
    Graph g = new AdjMatrix(2);
    g.addEdge(0, 1);
    assertEquals(1, GraphAlgorithms.diameter(g, false));
  }

  @Test
  void adjMatrixUndirectedDiameterTwoOneEdgePaths() {
    Graph g = new AdjMatrix(3);
    g.addEdge(0, 1);
    g.addEdge(2, 1);
    assertEquals(2, GraphAlgorithms.diameter(g, false));
  }

  @Test
  void adjMatrixUndirectedDiameterTwoGraphWithTwoPaths() {
    Graph g = new AdjMatrix(3);
    g.addEdge(0, 1);
    g.addEdge(1, 2);
    g.addEdge(0, 2);
    assertEquals(1, GraphAlgorithms.diameter(g, false));
  }

  @Test
  void adjMatrixUndirectedDiameterThreeSixVertexGraph() {
    Graph g = new AdjMatrix(6);
    g.addEdge(1, 0);
    g.addEdge(1, 2);
    g.addEdge(1, 3);
    g.addEdge(2, 4);
    g.addEdge(3, 2);
    g.addEdge(4, 5);
    assertEquals(4, GraphAlgorithms.diameter(g, false));
  }


  //======================================================================
  // Adjacency List Tests
  //======================================================================
  
  //----------------------------------------------------------------------
  // Basic Directed Graph BFS Tests
  
  @Test
  void adjListDirectedNoEdgeGraphBFS() throws Exception {
    Graph g = new AdjList(2);
    Map<Integer,Integer> tree = GraphAlgorithms.bfs(g, 0, true);
    assertEquals(1, tree.size());
    assertEquals(-1, tree.get(0));
  }

  @Test
  void adjListDirectedAllReachableBFS() throws Exception {
    Graph g = new AdjList(4);
    g.addEdge(0, 1);
    g.addEdge(0, 2);
    g.addEdge(2, 3);
    g.addEdge(1, 0);
    Map<Integer,Integer> tree = GraphAlgorithms.bfs(g, 0, true);
    assertEquals(4, tree.size());
    assertEquals(-1, tree.get(0));
    assertEquals(0, tree.get(1));
    assertEquals(0, tree.get(2));
    assertEquals(2, tree.get(3));
    tree = GraphAlgorithms.bfs(g, 1, true);
    assertEquals(4, tree.size());
    assertEquals(1, tree.get(0));
    assertEquals(-1, tree.get(1));
    assertEquals(0, tree.get(2));
    assertEquals(2, tree.get(3));        
  }

  @Test
  void adjListDirectedSomeReachableBFS() throws Exception {
    Graph g = new AdjList(5);
    g.addEdge(0, 1);
    g.addEdge(0, 3);
    g.addEdge(1, 2);
    g.addEdge(1, 4);
    g.addEdge(3, 1);
    g.addEdge(3, 4);
    Map<Integer,Integer> tree = GraphAlgorithms.bfs(g, 3, true);
    assertEquals(4, tree.size());
    assertFalse(tree.containsKey(0));
    assertEquals(3, tree.get(1));
    assertEquals(1, tree.get(2));
    assertEquals(-1, tree.get(3));
    assertEquals(3, tree.get(4));
  }

  @Test
  void adjListDirectedNoneReachableBFS() throws Exception {
    Graph g = new AdjList(5);
    g.addEdge(0, 1);
    g.addEdge(0, 3);
    g.addEdge(1, 2);
    g.addEdge(1, 4);
    g.addEdge(3, 1);
    g.addEdge(3, 4);
    Map<Integer,Integer> tree = GraphAlgorithms.bfs(g, 4, true);
    assertEquals(1, tree.size());
    assertEquals(-1, tree.get(4));
  }

  //----------------------------------------------------------------------
  // Basic Undirected Graph BFS Tests

  @Test
  void adjListUndirectedNoEdgeGraphBFS() {
    Graph g = new AdjList(2);
    Map<Integer,Integer> tree = GraphAlgorithms.bfs(g, 0, false);
    assertEquals(1, tree.size());
    assertEquals(-1, tree.get(0));
  }

  @Test
  void adjListUndirectedAllReachableBFS() {
    Graph g = new AdjList(5);
    g.addEdge(0, 1);
    g.addEdge(0, 2);
    g.addEdge(1, 4);
    g.addEdge(1, 3);
    g.addEdge(3, 4);
    Map<Integer,Integer> tree = GraphAlgorithms.bfs(g, 0, false); 
    assertEquals(5, tree.size());
    assertEquals(-1, tree.get(0));
    assertEquals(0, tree.get(1));
    assertEquals(0, tree.get(2));
    assertEquals(1, tree.get(3));
    assertEquals(1, tree.get(4));
    // reverse the edgs
    g = new AdjList(5);
    g.addEdge(1, 0);
    g.addEdge(2, 0);
    g.addEdge(4, 1);
    g.addEdge(3, 1);
    g.addEdge(4, 3);
    tree = GraphAlgorithms.bfs(g, 0, false);
    assertEquals(5, tree.size());
    assertEquals(-1, tree.get(0));
    assertEquals(0, tree.get(1));
    assertEquals(0, tree.get(2));
    assertEquals(1, tree.get(3));
    assertEquals(1, tree.get(4));
  }

  @Test
  void adjListUndirectedSomeReachableBFS() {
    Graph g = new AdjList(5);
    g.addEdge(0, 1);
    g.addEdge(2, 3);
    g.addEdge(2, 4);
    g.addEdge(3, 4);
    Map<Integer,Integer> tree = GraphAlgorithms.bfs(g, 0, true);
    assertEquals(2, tree.size());
    assertEquals(-1, tree.get(0));
    assertEquals(0, tree.get(1));
    tree = GraphAlgorithms.bfs(g, 2, false);
    assertEquals(3, tree.size());
    assertEquals(-1, tree.get(2));
    assertEquals(2, tree.get(3));
    assertEquals(2, tree.get(4));    
  }

  //----------------------------------------------------------------------
  // Basic Directed Graph Reachability Tests

  @Test  
  void adjListDirectedSourceIsReachableFromSource() {
    Graph g = new AdjList(2);
    g.addEdge(0, 1);
    List<Integer> path = GraphAlgorithms.reachable(g, 0, 0, true);
    assertEquals(1, path.size());
    assertTrue(path.contains(0));
  }

  @Test  
  void adjListDirectedOneHopPathIsReachable() {
    Graph g = new AdjList(2);
    g.addEdge(1, 0);
    List<Integer> path = GraphAlgorithms.reachable(g, 1, 0, true);
    assertEquals(2, path.size());
    assertEquals(1, path.get(0));
    assertEquals(0, path.get(1));
  }

  @Test
  void adjListDirectedTwoHopPathIsReachable() {
    Graph g = new AdjList(4);
    g.addEdge(0, 1);
    g.addEdge(1, 2);
    g.addEdge(2, 3);
    g.addEdge(1, 3);
    List<Integer> path = GraphAlgorithms.reachable(g, 0, 3, true);
    assertEquals(3, path.size());
    assertEquals(0, path.get(0));
    assertEquals(1, path.get(1));
    assertEquals(3, path.get(2));    
  }

  @Test  
  void adjListDirectedPathIsReachableThroughCycle() {
    Graph g = new AdjList(4);
    g.addEdge(0, 1);
    g.addEdge(1, 2);
    g.addEdge(2, 1);
    g.addEdge(2, 3);
    List<Integer> path = GraphAlgorithms.reachable(g, 0, 3, true);
    assertEquals(4, path.size());
    assertEquals(0, path.get(0));
    assertEquals(1, path.get(1));
    assertEquals(2, path.get(2));    
    assertEquals(3, path.get(3));    
  }

  @Test
  void adjListDirectedMultiplePathsOneShortestReachable() {
    Graph g = new AdjList(5);
    g.addEdge(0, 1);
    g.addEdge(0, 2);
    g.addEdge(1, 3);
    g.addEdge(2, 3);
    g.addEdge(2, 4);
    g.addEdge(3, 4);
    List<Integer> path = GraphAlgorithms.reachable(g, 0, 4, true);
    assertEquals(3, path.size());
    assertEquals(0, path.get(0));
    assertEquals(2, path.get(1));
    assertEquals(4, path.get(2));    
  }
  
  @Test  
  void adjListDirectedPathIsNotReachable() {
    Graph g = new AdjList(4);
    g.addEdge(1, 0);
    g.addEdge(0, 4);
    g.addEdge(3, 4);
    List<Integer> path = GraphAlgorithms.reachable(g, 1, 3, true);
    assertEquals(0, path.size());
  }
  
  //----------------------------------------------------------------------
  // Basic Undirected Graph Reachability Tests

  @Test  
  void adjListUndirectedSourceIsReachableFromSource() {
    Graph g = new AdjList(2);
    g.addEdge(0, 1);
    List<Integer> path = GraphAlgorithms.reachable(g, 0, 0, false);
    assertEquals(1, path.size());
    assertTrue(path.contains(0));
  }

  @Test  
  void adjListUndirectedOneHopPathIsReachable() {
    Graph g = new AdjList(2);
    g.addEdge(1, 0);
    List<Integer> path = GraphAlgorithms.reachable(g, 0, 1, false);
    assertEquals(2, path.size());
    assertEquals(0, path.get(0));
    assertEquals(1, path.get(1));
  }

  @Test
  void adjListUndirectedTwoHopPathIsReachable() {
    Graph g = new AdjList(4);
    g.addEdge(1, 0);
    g.addEdge(1, 2);
    g.addEdge(2, 3);
    g.addEdge(3, 1);
    List<Integer> path = GraphAlgorithms.reachable(g, 0, 3, false);
    assertEquals(3, path.size());
    assertEquals(0, path.get(0));
    assertEquals(1, path.get(1));
    assertEquals(3, path.get(2));    
  }

  @Test
  void adjListUndirectedMultiplePathsOneShortestReachable() {
    Graph g = new AdjList(5);
    g.addEdge(0, 1);
    g.addEdge(0, 2);
    g.addEdge(1, 3);
    g.addEdge(2, 3);
    g.addEdge(2, 4);
    g.addEdge(3, 4);
    List<Integer> path = GraphAlgorithms.reachable(g, 0, 4, true);
    assertEquals(3, path.size());
    assertEquals(0, path.get(0));
    assertEquals(2, path.get(1));
    assertEquals(4, path.get(2));    
  }
  
  @Test  
  void adjListUndirectedPathIsNotReachable() {
    Graph g = new AdjList(4);
    g.addEdge(0, 2);
    g.addEdge(2, 0);
    g.addEdge(3, 4);
    List<Integer> path = GraphAlgorithms.reachable(g, 0, 3, false);
    assertEquals(0, path.size());
  }
  
  //----------------------------------------------------------------------
  // Basic Weakly (Undirected) Connected Component Tests

  @Test
  void adjListOneNodeComponent() {
    Graph g = new AdjList(1);
    Map<Integer,Integer> components = GraphAlgorithms.weakComponents(g);
    assertEquals(1, components.size());
    assertEquals(0, components.get(0));
  }

  @Test
  void adjListTwoNodeComponent() {
    Graph g = new AdjList(2);
    g.addEdge(1, 0);
    Map<Integer,Integer> components = GraphAlgorithms.weakComponents(g);
    assertEquals(2, components.size());
    assertEquals(0, components.get(0));
    assertEquals(0, components.get(1));
  }

  @Test
  void adjListTwoNodeTwoComponents() {
    Graph g = new AdjList(2);
    Map<Integer,Integer> components = GraphAlgorithms.weakComponents(g);
    assertEquals(2, components.size());
    assertTrue(components.get(0) != components.get(1));
  }

  @Test
  void adjListSevenNodeThreeComponents() {
    Graph g = new AdjList(7);
    g.addEdge(3, 0);
    g.addEdge(3, 1);
    g.addEdge(1, 0);
    g.addEdge(5, 4);
    g.addEdge(4, 6);
    g.addEdge(6, 5);
    Map<Integer,Integer> components = GraphAlgorithms.weakComponents(g);
    assertEquals(7, components.size());
    assertEquals(0, components.get(0));
    assertEquals(0, components.get(1));
    assertEquals(1, components.get(2));    
    assertEquals(0, components.get(3));
    assertEquals(2, components.get(4));
    assertEquals(2, components.get(5));
    assertEquals(2, components.get(6));
  }
    
  //----------------------------------------------------------------------
  // Basic Directed Graph Diameter Tests

  @Test
  void adjListDirectedDiameterNoEdgeGraph() {
    Graph g = new AdjList(1);
    assertEquals(0, GraphAlgorithms.diameter(g, true));
  }

  @Test
  void adjListDirectedDiameterOneEdgeGraph() {
    Graph g = new AdjList(2);
    g.addEdge(0, 1);
    assertEquals(1, GraphAlgorithms.diameter(g, true));
  }

  @Test
  void adjListDirectedDiameterTwoOneEdgePaths() {
    Graph g = new AdjList(3);
    g.addEdge(0, 1);
    g.addEdge(2, 1);
    assertEquals(1, GraphAlgorithms.diameter(g, true));
  }

  @Test
  void adjListDirectedDiameterTwoGraphWithTwoPaths() {
    Graph g = new AdjList(3);
    g.addEdge(0, 1);
    g.addEdge(1, 2);
    g.addEdge(0, 2);
    assertEquals(1, GraphAlgorithms.diameter(g, true));
  }

  @Test
  void adjListDirectedDiameterThreeSixVertexGraph() {
    Graph g = new AdjList(6);
    g.addEdge(1, 0);
    g.addEdge(1, 2);
    g.addEdge(1, 3);
    g.addEdge(2, 4);
    g.addEdge(3, 2);
    g.addEdge(4, 5);
    assertEquals(3, GraphAlgorithms.diameter(g, true));
  }
  
  //----------------------------------------------------------------------
  // Basic Undirected Graph Diameter Tests

  @Test
  void adjListUndirectedDiameterNoEdgeGraph() {
    Graph g = new AdjList(1);
    assertEquals(0, GraphAlgorithms.diameter(g, false));
  }

  @Test
  void adjListUndirectedDiameterOneEdgeGraph() {
    Graph g = new AdjList(2);
    g.addEdge(0, 1);
    assertEquals(1, GraphAlgorithms.diameter(g, false));
  }

  @Test
  void adjListUndirectedDiameterTwoOneEdgePaths() {
    Graph g = new AdjList(3);
    g.addEdge(0, 1);
    g.addEdge(2, 1);
    assertEquals(2, GraphAlgorithms.diameter(g, false));
  }

  @Test
  void adjListUndirectedDiameterTwoGraphWithTwoPaths() {
    Graph g = new AdjList(3);
    g.addEdge(0, 1);
    g.addEdge(1, 2);
    g.addEdge(0, 2);
    assertEquals(1, GraphAlgorithms.diameter(g, false));
  }

  @Test
  void adjListUndirectedDiameterThreeSixVertexGraph() {
    Graph g = new AdjList(6);
    g.addEdge(1, 0);
    g.addEdge(1, 2);
    g.addEdge(1, 3);
    g.addEdge(2, 4);
    g.addEdge(3, 2);
    g.addEdge(4, 5);
    assertEquals(4, GraphAlgorithms.diameter(g, false));
  }

  
  //======================================================================
  // TODO: Design and create the following unit tests below. Note that
  // each graph below must be different. You must also draw and
  // include each graph (correctly labeled to the unit test) in your
  // write up. By "larger graph" I mean a graph with at least 10
  // vertices and many more edges. Your test graphs must be
  // "interesting" and you need to state why you think they are
  // interesting for the test cases in your write up.
  //
  //  1. Create 2 BFS tests using much larger graphs. One test should
  //     define and use a directed graph, the other undirected. 

  @Test
  void adjMatrixUndirectedWebBFS() {
    Graph g = new AdjMatrix(10);
    g.addEdge(0, 1);
    g.addEdge(0, 2);
    g.addEdge(0, 3);
    g.addEdge(0, 4);
    g.addEdge(0, 5);
    g.addEdge(0, 6);
    g.addEdge(0, 7);
    g.addEdge(0, 8);
    g.addEdge(0, 9);
    g.addEdge(0, 0);
    g.addEdge(1, 6);
    g.addEdge(1, 7);
    g.addEdge(1, 8);
    g.addEdge(1, 9);
    g.addEdge(1, 0);
    g.addEdge(1, 6);
    g.addEdge(1, 7);
    g.addEdge(1, 8);
    g.addEdge(1, 9);
    g.addEdge(4, 5);
    Map<Integer,Integer> tree = GraphAlgorithms.bfs(g, 0, false); 
    assertEquals(10, tree.size());
    assertEquals(-1, tree.get(0));
    assertEquals(0, tree.get(1));
    assertEquals(0, tree.get(2));
    assertEquals(0, tree.get(3));
    assertEquals(0, tree.get(4));
    assertEquals(0, tree.get(5));
    assertEquals(0, tree.get(6));
    assertEquals(0, tree.get(7));
    assertEquals(0, tree.get(8));
    assertEquals(0, tree.get(9));
  }

  @Test
  void adjMatrixDirectedGraphBFS() {
    Graph g = new AdjMatrix(10);
    g.addEdge(0, 1);
    g.addEdge(1, 2);
    g.addEdge(2, 3);
    g.addEdge(3, 4);
    g.addEdge(4, 5);
    g.addEdge(1, 6);
    g.addEdge(6, 7);
    g.addEdge(7, 8);
    g.addEdge(8, 9);
    g.addEdge(2, 9);  
    g.addEdge(3, 7);
    Map<Integer, Integer> tree = GraphAlgorithms.bfs(g, 0, true);
    assertEquals(10, tree.size());

    assertEquals(-1, tree.get(0));
    assertEquals(0, tree.get(1));
    assertEquals(1, tree.get(2));
    assertEquals(2, tree.get(3));
    assertEquals(3, tree.get(4));
    assertEquals(4, tree.get(5));
    assertEquals(1, tree.get(6));
    assertEquals(6, tree.get(7));
    assertEquals(7, tree.get(8));
    assertEquals(2, tree.get(9));
  }

  
  //  2. Create 2 Reachability (Shortest Path) tests using much larger
  //     graphs. One test should define and use a directed graph, the
  //     other undirected.

  @Test
  void adjListDirectedMultiplePathsShortestReachable() {
    Graph g = new AdjList(10);
    g.addEdge(0, 1);
    g.addEdge(0, 2);
    g.addEdge(1, 3);
    g.addEdge(1, 4);
    g.addEdge(2, 5);
    g.addEdge(3, 6);
    g.addEdge(4, 6);
    g.addEdge(5, 6);
    g.addEdge(6, 7);
    g.addEdge(7, 8);
    g.addEdge(8, 9);
    g.addEdge(2, 8); 
    List<Integer> path = GraphAlgorithms.reachable(g, 0, 9, true);
    // Shortest path should be: 0 -> 2 -> 8 -> 9
    assertEquals(4, path.size());
    assertEquals(0, path.get(0));
    assertEquals(2, path.get(1));
    assertEquals(8, path.get(2));
    assertEquals(9, path.get(3));
  }

  @Test
  void adjMatrixUndirectedMultiplePathsShortestReachable() {
    Graph g = new AdjMatrix(10);
    g.addEdge(0, 1);
    g.addEdge(0, 2);
    g.addEdge(1, 3);
    g.addEdge(1, 4);
    g.addEdge(2, 5);
    g.addEdge(3, 6);
    g.addEdge(4, 7);
    g.addEdge(5, 8);
    g.addEdge(6, 9);
    g.addEdge(7, 9);
    g.addEdge(8, 9);
    g.addEdge(0, 6);
    List<Integer> path = GraphAlgorithms.reachable(g, 0, 9, false);
    // Shortest path should be: 0 -> 6 -> 9
    assertEquals(3, path.size());
    assertEquals(0, path.get(0));
    assertEquals(6, path.get(1));
    assertEquals(9, path.get(2));
  }
  
  //  3. Create 1 Weakly Connected Component test using a much larger
  //     graph.
  
  @Test
  void adjMatrixTwelveNodeSingleComponents() {
    Graph g = new AdjMatrix(12);
    g.addEdge(0, 0);
    g.addEdge(1, 1);
    g.addEdge(2, 2);
    g.addEdge(3, 3);
    g.addEdge(4, 4);
    g.addEdge(5, 5);
    g.addEdge(6, 6);
    g.addEdge(7, 7);
    g.addEdge(8, 8);
    g.addEdge(9, 9);
    g.addEdge(10, 10);
    g.addEdge(11, 11);
    Map<Integer, Integer> components = GraphAlgorithms.weakComponents(g);
    assertEquals(12, components.size());

    assertEquals(0, components.get(0));
    assertEquals(1, components.get(1));
    assertEquals(2, components.get(2));
    assertEquals(3, components.get(3));
    assertEquals(4, components.get(4));
    assertEquals(5, components.get(5));
    assertEquals(6, components.get(6));
    assertEquals(7, components.get(7));
    assertEquals(8, components.get(8));
    assertEquals(9, components.get(9));
    assertEquals(10, components.get(10));
    assertEquals(11, components.get(11));
  }

  //  4. Create 2 Diameter tests using much larger graphs. One test
  //     should define and use a directed graph, the other undirected.
  
  @Test
  void adjMatrixUndirectedDiameterTwelveNodeGraph() {
    Graph g = new AdjMatrix(12);
    g.addEdge(0, 1);
    g.addEdge(1, 2);
    g.addEdge(2, 3);
    g.addEdge(3, 4);
    g.addEdge(4, 5);
    g.addEdge(5, 6);
    g.addEdge(6, 7);
    g.addEdge(7, 8);
    g.addEdge(8, 9);
    g.addEdge(9, 10);
    g.addEdge(10, 11);
    g.addEdge(4, 8);

    assertEquals(8, GraphAlgorithms.diameter(g, false));
  }

  @Test
  void adjMatrixDirectedDiameterFifteenNodeGraph() {
    Graph g = new AdjMatrix(15);
    g.addEdge(0, 1);
    g.addEdge(1, 2);
    g.addEdge(2, 3);
    g.addEdge(0, 4);
    g.addEdge(4, 5);
    g.addEdge(5, 2);
    g.addEdge(6, 7);
    g.addEdge(0, 8);
    g.addEdge(4, 9);
    g.addEdge(9, 10);
    g.addEdge(2, 11);
    g.addEdge(11, 12);
    g.addEdge(12, 13);
    g.addEdge(13, 14);
    g.addEdge(4, 8);

    assertEquals(6, GraphAlgorithms.diameter(g, true));
  }

  //======================================================================


  
}
