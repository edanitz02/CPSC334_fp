/**
 * CPSC 450, HW-4
 * 
 * NAME: S. Bowers
 * DATE: Fall 2024
 */ 

package cpsc450;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import java.io.File;
import java.awt.Font;
import java.awt.Color;
import java.awt.BasicStroke;
import static java.lang.System.out;
import java.util.Map;


/**
 * Basic program for running performance tests and generating
 * corresponding graphs for HW-4. 
 */ 
public class HW4 {
  
  /**
   * Create a sparse adjacency matrix consisting of vertices of
   * connected three cycles.
   * @param n The size of the graph in terms of the number of vertices.
   * @returns The adjacency matrix.
   */
  static Graph createSparseAdjMatrix(int n) throws Exception {
    Graph graph = new AdjMatrix(n);
    // create a "forward" line graph
    for (int x = 0; x < n - 1; ++x)
      graph.addEdge(x, x + 1);
    // create a "backward" line graph for every other vertex
    for (int x = n - 1; x > 2; --x)
      graph.addEdge(x, x - 2);
    return graph;
  }

  
  /**
   * Create a sparse adjacency list consisting of vertices of
   * connected three cycles.
   * @param n The size of the graph in terms of the number of vertices.
   * @returns The adjacency list.
   */
  static Graph createSparseAdjList(int n) {
    Graph graph = new AdjList(n);
    // create a "forward" line graph
    for (int x = 0; x < n - 1; ++x) 
      graph.addEdge(x, x + 1);
    // create a "backward" line graph for every other vertex
    for (int x = n - 1; x > 2; --x)
      graph.addEdge(x, x - 2);
    return graph;
  }

  
  /**
   * Create a dense adjacency matrix with all vertices connected to
   * each without self edges. 
   * @param n The size of the graph in terms of the number of vertices.
   * @returns The adjacency matrix.
   */
  static Graph createDenseAdjMatrix(int n) {
    Graph graph = new AdjMatrix(n);
    for (int x = 0; x < n; ++x) 
      for (int y = 0; y < n; ++y)
        if (x != y)
          graph.addEdge(x, y);
    return graph;
  }

  
  /**
   * Create a dense adjacency list with all vertices connected to
   * each without self edges. 
   * @param n The size of the graph in terms of the number of vertices.
   * @returns The adjacency list.
   */
  static Graph createDenseAdjList(int n) {
    Graph graph = new AdjList(n);
    for (int x = 0; x < n; ++x) 
      for (int y = 0; y < n; ++y)
        if (x != y)
          graph.addEdge(x, y);
    return graph;
  }

  
  /**
   * Return the time for BFS on a given graph.
   * @param g The graph to search.
   * @param digraph If true, directed, otherwise undirected.   
   * @returns The time in milliseconds.
   */ 
  static long timeBFS(Graph g, boolean digraph) throws Exception {
    long start = System.currentTimeMillis();
    Map<Integer,Integer> tree = GraphAlgorithms.bfs(g, 0, digraph);    
    long end = System.currentTimeMillis();
    return end - start;
  }

  
  /**
   * Return the time to compute diameter on a given graph.
   * @param g The graph to search.
   * @param digraph If true, directed, otherwise undirected.
   * @returns The time in milliseconds.
   */ 
  static long timeDiameter(Graph g, boolean digraph) throws Exception {
    long start = System.currentTimeMillis();
    int d = GraphAlgorithms.diameter(g, digraph);
    long end = System.currentTimeMillis();
    return end - start;
  }

  
  /**
   * Create a chart
   */  
  static void chart(XYSeries[] series, String title, String file) throws Exception {
    XYSeriesCollection ds = new XYSeriesCollection();
    for (XYSeries s : series)
      ds.addSeries(s);
    // build the chart
    JFreeChart chart = ChartFactory.createXYLineChart(title, "vertices", "time (ms)", ds);
    XYPlot plot = (XYPlot) chart.getPlot();
    plot.setBackgroundPaint(new Color(220, 220, 220));
    // configure the chart
    XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();
    for (int i = 0; i < series.length; ++i) {
      renderer.setSeriesShapesVisible(i, true);
      renderer.setSeriesShapesFilled(i, true);
      renderer.setSeriesStroke(i, new BasicStroke(2.5f));
    }
    // save the result
    int width = 640; // 1024;
    int height = 480; // 768;
    File lineChart = new File(file);
    ChartUtils.saveChartAsPNG(lineChart, chart, width, height);
  }

  
  /**
   * Run the directed graph BFS tests
   */  
  static void runDirectedGraphBFS() throws Exception {
    out.println("----------------------------------------");
    out.println(" SPARSE AND DENSE DIRECTED GRAPH BFS");
    out.println("----------------------------------------");    
    int STEP = 200;
    int END = 2000;
    XYSeries s1a = new XYSeries("Sparse AdjMatrix");
    XYSeries s1b = new XYSeries("Dense AdjMatrix");    
    XYSeries s2a = new XYSeries("Sparse AdjList");
    XYSeries s2b = new XYSeries("Dense AdjList");    
    // adj matrix tests
    s1a.add(0, 0);
    s1b.add(0, 0);
    for (int n = STEP; n <= END; n = n + STEP) {
      Graph g = createSparseAdjMatrix(n);
      long t1 = timeBFS(g, true);
      g = createDenseAdjMatrix(n);
      long t2 = timeBFS(g, true);      
      s1a.add(n, t1);
      s1b.add(n, t2);
      out.println("[adj matrix] time (ms) for " + n + ": " + t1 + ", " + t2);
    }
    // adjacency list tests
    s2a.add(0, 0);
    s2b.add(0, 0);
    for (int n = STEP; n <= END; n = n + STEP) {
      Graph g = createSparseAdjList(n);      
      long t1 = timeBFS(g, true);
      g = createDenseAdjList(n);      
      long t2 = timeBFS(g, true);
      s2a.add(n, t1);
      s2b.add(n, t2);
      out.println("[adj list] time (ms) for " + n + ": " + t1 + ", " + t2);
    }
    XYSeries[] series = {s1a, s1b, s2a, s2b};
    String title = "Directed Breadth First Search (BFS)";
    chart(series, title, "bfs_times.png");
  }

  
  /**
   * Run the directed graph diameter tests.
   */  
  static void runDirectedGraphDiameter() throws Exception {
    out.println("----------------------------------------");
    out.println(" SPARSE AND DENSE DIRECTED GRAPH DIAMETER");
    out.println("----------------------------------------");    
    int STEP = 50;
    int END = 500;
    XYSeries s1a = new XYSeries("Sparse AdjMatrix");
    XYSeries s1b = new XYSeries("Dense AdjMatrix");    
    XYSeries s2a = new XYSeries("Sparse AdjList");
    XYSeries s2b = new XYSeries("Dense AdjList");
    // adj matrix tests
    s1a.add(0, 0);
    s1b.add(0, 0);
    for (int n = STEP; n <= END; n = n + STEP) {
      Graph g = createSparseAdjMatrix(n);
      long t1 = timeDiameter(g, true);
      g = createDenseAdjMatrix(n);
      long t2 = timeDiameter(g, true);
      s1a.add(n, t1);
      s1b.add(n, t2);
      out.println("[adj matrix] time (ms) for " + n + ": " + t1 + ", " + t2);
    }
    // adjacency list tests
    s2a.add(0, 0);
    s2b.add(0, 0) ;
    for (int n = STEP; n <= END; n = n + STEP) {
      Graph g = createSparseAdjList(n);      
      long t1 = timeDiameter(g, true);
      g = createDenseAdjList(n);      
      long t2 = timeDiameter(g, true);
      s2a.add(n, t1);
      s2b.add(n, t2);
      out.println("[adj list] time (ms) for " + n + ": " + t1 + ", " + t2);
    }
    XYSeries[] series = {s1a, s1b, s2a, s2b};
    String title = "Directed Diameter";
    chart(series, title, "diameter_times.png");
  }

  
  public static void main(String [] args) throws Exception {
    System.out.println("Hello World!");
    //runDirectedGraphBFS();
    //runDirectedGraphDiameter();
  }

}
