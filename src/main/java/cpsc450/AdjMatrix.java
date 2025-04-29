/**
 * CPSC 450, Fall 2024
 * 
 * NAME: Ethan Danitz
 * DATE: Fall 2024
 */

 package cpsc450;

 import java.util.HashSet;
 import java.util.Set;
 
 /**
  * Adjacency Matrix implementation of the Graph interface. 
  */
 public class AdjMatrix implements Graph {
 
   private int vertexCount;      // total number of vertices
   private int edgeCount;        // running count of number of edges
   private boolean matrix[];     // storage for the matrix as "flattened" 2D array
 
   /**
    * Create an adjacency matrix (graph) given a specific (fixed)
    * number of vertices.
    * @param vertices The number of vertices in the graph. 
    */ 
   public AdjMatrix(int vertices) throws GraphException {
     // TODO: Implement
     // make sure input is valid
     if(vertices <= 0){
       throw new GraphException("invalid vertex count");
     }
     // set vertex count and initilize 'matrix'
     vertexCount = vertices;
     edgeCount = 0;
     matrix = new boolean[vertexCount * vertexCount];
   }
 
   @Override
   public void addEdge(int x, int y) {
     // TODO: Implement
     if(x < vertexCount && y < vertexCount && x >= 0 && y >= 0) {
       int index = vertexCount * x + y;
 
       matrix[index] = true;
       edgeCount++;
     }
   }
 
   @Override
   public void removeEdge(int x, int y) {
     // TODO: Implement
     if(x < vertexCount && y < vertexCount && x >= 0 && y >= 0) {
       int index = vertexCount * x + y;
 
       if(matrix[index]) {
         matrix[index] = false;
         edgeCount--;
       }
     }
   }
 
   @Override
   public Set<Integer> out(int x) {
     Set<Integer> outs = new HashSet<>();
     // start x at the start of the nodes to check for out nodes
     if(x < vertexCount && x >= 0) {
       // start x at the start of the nodes to check for out nodes
       Integer outNode = x *= vertexCount;
       for(int i = 0; i < vertexCount; i++, outNode++) {
         // needs to do modulus for indexes in rows that aren't 0
         if(matrix[outNode]) outs.add(outNode % vertexCount);
       }
     }
     return outs;
   }
 
   @Override
   public Set<Integer> in(int x) {
     Set<Integer> ins = new HashSet<>();
     if(x < vertexCount && x >= 0) {
       // check all columns x
       Integer inNode = x;
       for(int i = 0; i < vertexCount; i++, inNode += vertexCount) {
         // returns i for the row because that's an in node
         if(matrix[inNode]) ins.add(i);
       }
     }
     return ins;
   }
 
   @Override
   public Set<Integer> adj(int x) {
     Set<Integer> adjs = out(x);
     Set<Integer> insToAdd = in(x);
     for(Integer node : insToAdd) {
       if(!adjs.contains(node)) adjs.add(node);
     }
     return adjs;
   }
 
   @Override
   public boolean hasEdge(int x, int y) {
     if(x < vertexCount && y < vertexCount && x >= 0 && y >= 0) {  
       int index = vertexCount * x + y;
       return matrix[index];
     }
     return false;
   }
 
   @Override
   public boolean hasVertex(int x) {
     return vertexCount > 0 && x >= 0 && x < vertexCount;
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
 