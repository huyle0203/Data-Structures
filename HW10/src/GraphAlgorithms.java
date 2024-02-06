import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Queue;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.HashMap;

/**
 * Your implementation of various different graph algorithms.
 *
 * @author HUY LE
 * @version 1.0
 * @userid hduc6
 * @GTID 903845849
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * You may import/use java.util.Set, java.util.List, java.util.Queue, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for BFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the bfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> bfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null || !graph.getAdjList().containsKey(start)) {
            throw new IllegalArgumentException("No null input or start needs to exist in graph");
        }
        Queue<Vertex<T>> queue = new LinkedList<>();
        Set<Vertex<T>> visitedSet = new HashSet<>();
        List<Vertex<T>> visited = new ArrayList<>();
        queue.add(start);
        visitedSet.add(start);
        while (!queue.isEmpty()) {
            Vertex<T> v = queue.remove();
            visited.add(v);
            for (VertexDistance<T> verDis : graph.getAdjList().get(v)) {
                if (!visitedSet.contains(verDis.getVertex())) {
                    queue.add(verDis.getVertex());
                    visitedSet.add(verDis.getVertex());
                }
            }
        }
        return visited;
    }

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * *NOTE* You MUST implement this method recursively, or else you will lose
     * all points for this method.
     *
     * You may import/use java.util.Set, java.util.List, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null || !graph.getAdjList().containsKey(start)) {
            throw new IllegalArgumentException("No null input or start needs to exist in graph");
        }
        Set<Vertex<T>> visitedSet = new HashSet<>();
        List<Vertex<T>> visited = new ArrayList<>();
        dfs(start, graph, visitedSet, visited);
        return visited;
    }
 
    /**
     * Helper method for dfs for recursion
     *
     * @param <T> the generic typing of the data
     * @param vertex the vertex to begin the dfs on
     * @param graph the graph to search through
     * @param visitedSet the set of visited vertex
     * @param visited the list that is returned
     */
    private static <T> void dfs(Vertex<T> vertex, Graph<T> graph,
                                Set<Vertex<T>> visitedSet, List<Vertex<T>> visited) {
        visited.add(vertex);
        visitedSet.add(vertex);
        for (VertexDistance<T> verDis : graph.getAdjList().get(vertex)) {
            if (!visitedSet.contains(verDis.getVertex())) {
                dfs(verDis.getVertex(), graph, visitedSet, visited);
            }
        }
    }

    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     *
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing
     * infinity) if no path exists.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Map, and java.util.Set and any class that
     * implements the aforementioned interfaces, as long as your use of it
     * is efficient as possible.
     *
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     *
     * 1) Check if all of the vertices have been visited.
     * 2) Check if the PQ is empty yet.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from start to every
     * other node in the graph
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */

    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
                                                        Graph<T> graph) {
        if (start == null || graph == null || !graph.getAdjList().containsKey(start)) {
            throw new IllegalArgumentException("No null input or start needs to exist in graph");
        }
        //initialize PQ --> for vertices based on start vertex -> curr distance
        Queue<VertexDistance<T>> pq = new PriorityQueue<>();
        Set<Vertex<T>> visitedSet = new HashSet<>();
        //result map to store shortest distances
        Map<Vertex<T>, Integer> result = new HashMap<>();
        //initialize result map with initial distances
        for (Vertex<T> v : graph.getVertices()) {
            result.put(v, Integer.MAX_VALUE);
        }
        //add start vertex to PQ w distance = 0
        pq.add(new VertexDistance<>(start, 0));
        //main loop
        int graphVertexSize = graph.getVertices().size();
        while (!pq.isEmpty() && visitedSet.size() < graphVertexSize) {
            //dequeue a vertex from PQ
            VertexDistance<T> temp = pq.remove();
            //update distance of neighboring vertices if theres a shorter path
            if (!(visitedSet.contains(temp.getVertex()))) {
                visitedSet.add(temp.getVertex());
                result.put(temp.getVertex(), temp.getDistance());
                for (VertexDistance<T> verDis : graph.getAdjList().get(temp.getVertex())) {
                    //int distance = temp.getDistance() + verDis.getDistance();
                    if (!(visitedSet.contains(verDis.getVertex()))) {
                        pq.add(new VertexDistance<>(verDis.getVertex(), verDis.getDistance() + temp.getDistance()));
                    }
                }
            }
        }
        return result;
    }

    /**
     * Runs Kruskal's algorithm on the given graph and returns the Minimal
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     *
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     *
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     *
     * You may assume that there will only be one valid MST that can be formed.
     *
     * Kruskal's will also require you to use a Disjoint Set which has been
     * provided for you. A Disjoint Set will keep track of which vertices are
     * connected given the edges in your current MST, allowing you to easily
     * figure out whether adding an edge will create a cycle. Refer
     * to the DisjointSet and DisjointSetNode classes that
     * have been provided to you for more information.
     *
     * An MST should NOT have self-loops or parallel edges.
     *
     * By using the Disjoint Set provided, you can avoid adding self-loops and
     * parallel edges into the MST.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Set, and any class that implements the aforementioned
     * interfaces.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param graph the graph we are applying Kruskals to
     * @return the MST of the graph or null if there is no valid MST
     * @throws IllegalArgumentException if any input is null
     */
    public static <T> Set<Edge<T>> kruskals(Graph<T> graph) {
        if (graph == null) {
            throw new IllegalArgumentException("No null input or start needs to exist in graph");
        }
        DisjointSet<Vertex<T>> disSet = new DisjointSet<>();
        Set<Edge<T>> mst = new HashSet<>();
        PriorityQueue<Edge<T>> pq =  new PriorityQueue<>(graph.getEdges());
        while (mst.size() < 2 * (graph.getVertices().size() - 1) && !pq.isEmpty()) {
            Edge<T> edge = pq.remove();
            Vertex<T> u = edge.getU();
            Vertex<T> v = edge.getV();
            if (!disSet.find(u).equals(disSet.find(v))) {
                mst.add(edge);
                mst.add(new Edge<>(v, u, edge.getWeight()));
                disSet.union(u, v);
            }
        }
        if (mst.size() < 2 * (graph.getVertices().size() - 1)) {
            return null;
        } else {
            return mst;
        }
    }
}
