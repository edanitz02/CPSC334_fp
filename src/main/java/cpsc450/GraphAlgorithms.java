/**
 * CPSC 450, HW-4
 *
 * NAME: Ethan Danitz
 * DATE: Fall 2024
 *
 */ 

package cpsc450;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;


/** 
 * Suite of graph-based algorithms. 
 */
public class GraphAlgorithms {

  /**
   * Performs a breadth-first traversal of a directed graph starting
   * at a source vertex.
   * @param g The graph to search
   * @param s The source vertex to search from
   * @param digraph If false, treat g as undirected
   * @returns A search tree as a map with vertices as keys and
   * search-tree parents as corresponding values, or null if the
   * source vertex is invalid. 
   */
  public static Map<Integer,Integer> bfs(Graph g, int s, boolean digraph) {
    // @TODO: implement this function

    // initialize variables
    boolean[] visited = new boolean[g.vertices()];
    Queue<Integer> Q = new LinkedList<>();
    Map<Integer, Integer> searchTree = new HashMap<>();

    visited[s] = true; // mark s visited
    searchTree.put(s, -1); // set s parent to -1
    Q.add(s); // enqueue s

    while(!Q.isEmpty()) {
      int u = Q.poll(); // set u to the vertex at front of queue

      // gather all vertices adj to u
      Set<Integer> adjs; 
      // if its a digraph, only get the out edges from u
      if(digraph) {
        adjs = g.out(u);
      }
      // else get all adj edges  
      else {
        adjs = g.adj(u);
      }

      for(Integer v : adjs) { // for v adj to u in E
        if(!visited[v]) { // if v is not yet visited
          visited[v] = true; // mark v as visited
          searchTree.put(v, u); // set v's parent to u
          Q.add(v); // add v to Q
        }
      }
    }

    return searchTree;

  }


  /**
   * Checks if a source (src) vertex can reach a destination (dst)
   * vertex (i.e., dst is reachable from src) in a graph using a
   * modified version of the BFS algorithm. Assumes all vertices can
   * trivially reach themselves (even if no self edge).
   * @param g The graph to search
   * @param src The source vertex to search from
   * @param dst The destination vertex (for a path from src to dst)
   * @param digraph If false, treat g as undirected
   * @returns The shortest path from src to dst as a sequence of vertices
   *          (forming the path) or the empty sequence if dst is not
   *          reachable from src
   */
  public static List<Integer> reachable(Graph g, int src, int dst, boolean digraph) {
    // @TODO: implement this function
    // NOTE: You must not call your BFS function to implement reachable

    // initialize variables
    boolean[] visited = new boolean[g.vertices()];
    Queue<Integer> Q = new LinkedList<>();
    List<Integer> shortestPath = new ArrayList<>();
    Map<Integer, Integer> searchTree = new HashMap<>();

    visited[src] = true; // mark src visited
    Q.add(src); // enqueue src
    searchTree.put(src, -1);

    // case where src == dst
    if(src == dst) {
      shortestPath.add(src);
      return shortestPath;
    }

    while(!Q.isEmpty()) {
      int u = Q.poll(); // set u to the vertex at front of queue

      // gather all vertices adj to u
      Set<Integer> adjs; 
      // if its a digraph, only get the out edges from u
      if(digraph) {
        adjs = g.out(u);
      }
      // else get all adj edges  
      else {
        adjs = g.adj(u);
      }

      for(Integer v : adjs) { // for v adj to u in E
        // check adj verts
        if(!visited[v]) { // if v is not yet visited
          visited[v] = true; // mark v as visited
          searchTree.put(v, u); // set v's parent to u
          Q.add(v); // add v to Q
        }

        // check if v is the dst node we are looking for
        if(v == dst) {
          // create path
          do {
            shortestPath.addFirst(v); // so first node added is the last node in path
            v = searchTree.get(v);
          } while(v != -1);

          // return
          return shortestPath;
        }
      }
    }
    return shortestPath;
    
  }

  
  /**
   * Finds all of the connected components in the given graph. Note
   * that for directed graphs, computes the "weakly" connected components.
   * @param g The graph to search
   * @return A mapping from vertices to components where components
   *         are numbered from 0 to n-1 for n discovered components.
   */
  public static Map<Integer,Integer> weakComponents(Graph g) {
    // @TODO: implement this function.
    // NOTE: This function CAN reuse your BFS function above.

    // set up variables
    Map<Integer, Integer> components = new HashMap<>();
    int componentId = 0;
    boolean[] visited = new boolean[g.vertices()];
    Queue<Integer> Q = new LinkedList<>();

    // for all possible vertices in graph
    for(int vertex = 0; vertex < g.vertices(); vertex++) {
      // if graph has that vertex and it hasn't been visited
      if (g.hasVertex(vertex) && !visited[vertex]) {
        List<Integer> component = new ArrayList<>();

        // ALTERED COPY OF BFS ----------------------------
        visited[vertex] = true; // mark s visited
        component.add(vertex);
        Q.add(vertex); // enqueue s

        while(!Q.isEmpty()) {
          int u = Q.poll(); // set u to the vertex at front of queue

          // gather all vertices adj to u
          Set<Integer> adjs; 
          adjs = g.adj(u);

          for(Integer v : adjs) { // for v adj to u in E
            if(!visited[v]) { // if v is not yet visited
              visited[v] = true; // mark v as visited
              component.add(v); // add node to component for weakComponents
              Q.add(v); // add v to Q
            }
          }
        }
        // END OF COPY OF BFS -----------------------


        // Mark all vertices in this component with the same componentId
        for (int v : component) {
          components.put(v, componentId);
        }

        componentId++;
      }
    }

    return components;
  }

  
  /**
   * Finds the longest shortest path in the given graph as a sequence
   * of vertices. The number of vertices returned minus one is the
   * diameter of the graph.
   * @param g The graph to search
   * @param digraph If false, treat g as undirected
   * @return The length (i.e., diameter) of the longest shortest path
   */
  public static int diameter(Graph g, boolean digraph) {
    // @TODO: implement this function
    // NOTE: You must not call your BFS function to implement diameter
    int maxDiameter = 0;

    // going to have to complete BFS on every node in the graph
    for(int vertex = 0; vertex < g.vertices(); vertex++) {
      int s = vertex; // so we can use this in BFS

      boolean[] visited = new boolean[g.vertices()];
      Queue<Integer> Q = new LinkedList<>();
      // for figuring out each layer
      int diameter = -1;
      int nodesInLayer = 0;

      visited[s] = true; // mark s visited
      Q.add(s); // enqueue s
      
      while(!Q.isEmpty()) {
        // if we've gone through all the nodes in a layer, save the current size of Q
        //   as that represents the # of nodes in the next layer
        if(nodesInLayer == 0) {
          nodesInLayer = Q.size();
          // increment layer to show increase in diameter 
          diameter++;
        }

        int u = Q.poll(); // set u to the vertex at front of queue
        nodesInLayer--; // decrement nodesInLayer as you removed a node from Queue

        // gather all vertices adj to u
        Set<Integer> adjs; 
        // if its a digraph, only get the out edges from u
        if(digraph) {
          adjs = g.out(u);
        }
        // else get all adj edges  
        else {
          adjs = g.adj(u);
        }

        for(Integer v : adjs) { // for v adj to u in E
          if(!visited[v]) { // if v is not yet visited
            visited[v] = true; // mark v as visited
            Q.add(v); // add v to Q
          }
        }
      }

      if(diameter > maxDiameter) {
        maxDiameter = diameter;
      }
    }
    
    return maxDiameter;
  }
  // I'm going to revisit this to see if it works
  // works assuming that no node has multiple parents (in a digraph)
  // select a node
  // start by moving backwards to parent node if directed, then complete bfs (track steps)
  // if come across a node w diameter then add that to current calculation to start node and continue
  // 
  
}

