/**
 * CPSC 450, HW-4
 * 
 * NAME: Ethan Danitz
 * DATE: Fall, 2024 
 */

 package cpsc450;

 import java.util.HashMap;
 import java.util.HashSet;
 import java.util.Map;
 import java.util.Set;
 
 
 /**
  * Basic adjacency List implementation of the Graph interface.
  */
 public class AdjList implements Graph {
 
   private int vertexCount;                     // total number of vertices
   private int edgeCount;                       // running count of number of edges
   private Map<Integer,Set<Integer>> outEdges;  // storage for the out edges
   private Map<Integer,Set<Integer>> inEdges;   // storage for the in edges
 
   /**
    * Create an adjacency list (graph) given a specific (fixed) number
    * of vertices.
    * @param vertices The number of vertices of the graph.
    */
   public AdjList(int vertices) throws GraphException {
     if(vertices <= 0) {
       throw new GraphException("invalid vertex count");
     } 
     vertexCount = vertices;
     edgeCount = 0;
   }
 
   @Override
   public void addEdge(int x, int y) {
     // valid inputs
     if(x >= 0 && y >= 0 && x < vertexCount && y < vertexCount) {
       // if no hashmap exists
       if(edgeCount == 0) {
         outEdges = new HashMap<>();
         inEdges = new HashMap<>();
       }
 
       Integer outNodeValue = y;
       Integer inNodeValue = x;
 
       // add out edge if key doesn't exist
       if(!outEdges.containsKey(x)) {
         Set<Integer> outSet = new HashSet<>();
         outSet.add(outNodeValue);
         // add to out edge Hash map
         outEdges.put(x, outSet);
       } else { // key already exists
         outEdges.get(x).add(outNodeValue);
       }
 
       // add in edge if key doesn't exist
       if(!inEdges.containsKey(y)) {
         Set<Integer> inSet = new HashSet<>();
         inSet.add(inNodeValue);
         // add to in edge Hash map
         inEdges.put(y, inSet);
       } else { // key already exists
         inEdges.get(y).add(inNodeValue);
       }
 
       edgeCount++;
     }
   }
 
   @Override
   public void removeEdge(int x, int y) {
     // check to see if edge exists
     if(x >= 0 && y >= 0 && x < vertexCount && y < vertexCount) {
       // edge exists, now time to remove it
       if(outEdges.containsKey(x) && outEdges.get(x).contains(y)) {
         outEdges.get(x).remove(y);
         // maybe implement: remove outEdges.get(x) if Set is now empty
         inEdges.get(y).remove(x);
         // implement same as above
         // decrement edge count
         edgeCount--;
       }
     }
   }
 
   @Override
   public Set<Integer> out(int x) {
     Set<Integer> outs = new HashSet<>();
     if(edgeCount > 0 && outEdges.containsKey(x)) {
       for(Integer nodes : outEdges.get(x)) {
         outs.add(nodes);
       }
     }
     return outs;
   }
 
   @Override
   public Set<Integer> in(int x) {
     Set<Integer> ins = new HashSet<>();
     if(edgeCount > 0 && inEdges.containsKey(x)) {
       for(Integer nodes : inEdges.get(x)) {
         ins.add(nodes);
       }
     }
     return ins;
   }
 
   @Override
   public Set<Integer> adj(int x) {
     Set<Integer> adjs = out(x);
     for(Integer nodes : in(x)) {
       adjs.add(nodes);
     }
     return adjs;
   }
 
   @Override
   public boolean hasEdge(int x, int y) {
     if(edgeCount > 0 && outEdges.containsKey(x)) {
       Set<Integer> outNodes = outEdges.get(x);
       for(Integer check : outNodes) {
         if(y == check) return true;
       }
     }
     return false;
   }
 
   @Override
   public boolean hasVertex(int x) {
     return vertexCount > 0 && x < vertexCount && x >= 0;
   }
 
   @Override
   public int vertices() {
     return vertexCount;
   }
   
   @Override
   public int edges() {
     return edgeCount;
   }
   
 }
 