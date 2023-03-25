package Minesweeper.graphs;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class AdjacencyGraph<E> implements Graph<E> {

    private Map<E, Vertex<E>> vertices;
   
    /**
     * Instance of AdjacencyGraph
     */
    public AdjacencyGraph() {
        vertices = new HashMap<>();
    }

    /**
     * Adds value to AdjacencyGraph
     */
    @Override
    public void add(E value) {
        Vertex<E> vertex = new Vertex<>(value);
        vertices.put(value, vertex);
    }

    /**
     * Determines if AdjacencyGraph contains a specific element or not
     * @return
     *      true if in graph, false otherwise
     */
    @Override
    public boolean contains(E value) {
        return vertices.containsKey(value);
    }

    /**
     * retruns size of graph
     * @return
     *      size of graph
     */
    @Override
    public int size() {
        return vertices.size();
    }

    /**
     * Connects verticies in one direction relationship
     * @param a
     *      value of 1st vertex
     * @param b
     *      value of 2nd vertex
     */
    @Override
    public void connectDirected(E a, E b) {
        Vertex<E> vertexA = vertices.get(a);
        Vertex<E> vertexB = vertices.get(b);
        vertexA.connect(vertexB);
    }

    /**
     * Connects verticies in two direction relationship
     * @param a
     *      value of 1st vertex
     * @param b
     *      value of 2nd vertex
     */
    @Override
    public void connectUndirected(E a, E b) {
        Vertex<E> vertexA = vertices.get(a);
        Vertex<E> vertexB = vertices.get(b);
        vertexA.connect(vertexB);
        vertexB.connect(vertexA);
        
    }

    /**
     * Checks to see if 2 verticies are connected
     * @param a
     *      value of 1st vertex
     * @param b
     *      value of 2nd vertex
     * @return
     *      true if connected, false otherwise
     */
    @Override
    public boolean connected(E a, E b) {
        Vertex<E> vertexA = vertices.get(a);
        Vertex<E> vertexB = vertices.get(b);
        return vertexA.connected(vertexB);
    }

    /**
     * Searches graph for specific point from a specified starting point
     * @param start
     *      starting point
     * @param end
     *      vertex value being searched for
     */
    @Override
    public boolean bfSearch (E start, E end) {

        Vertex <E> s = new Vertex <>(start); // Creates vertex of starting vertex
        Vertex <E> e = new Vertex <>(end); // Creates vertex of end Vertex

        LinkedList<Vertex<E>> q = new LinkedList<>();
        Set <Vertex<E>> set = new HashSet<>();

        q.add(s); // Adds Starting vertex to queue 
        set.add(s); // Adds Starting point to visited set

        while (!q.isEmpty()) {
            Vertex<E> v = q.poll();

            if (s == e)
                return true;

                else {

                    for (Vertex<E> n: v.getNeighbors()) {

                        if (!set.contains(n)) {
                            set.add (n);
                            q.add(n);
                        }
                    }
                }    
        }

        return false;
    }


    /**
     * Creates a map of vertices and their predicesors that are needed to create a path using breadth first search
     * @param start
     *      starting vertex hashcode
     * @param end
     *      ending vertex hash code
     * @return
     *      List of verticies used to create the path
     */
    @Override
    public List<E> bfPath (E start, E end) {

        Vertex <E> s = vertices.get(start); // Creates vertex of starting vertex
        Vertex <E> e = vertices.get(end); // Creates vertex of end Vertex

        Queue<Vertex<E>> q = new LinkedList<>();
        Map <Vertex<E>, Vertex<E>> predicessors = new HashMap<>();

        q.add(s); 
        predicessors.put(s, null); 

        while (!q.isEmpty()) {
            Vertex<E> v = q.poll();

            if (v == e)
                break;

                else {
                    for (Vertex<E> n: v.getNeighbors()) {
                        if (!predicessors.containsKey(n)) {
                            predicessors.put (n, v);
                            q.add(n);
                        }
                    }
                }    
        }

        return makePath(predicessors, e);
    }


    /**
     * Creates a path from starting vertex to ending vertex
     * @param predecessors
     *      hash map of values that direct vertices to their predicesors
     * @param end
     *      ending vertex hash code
     * @return
     *      List of verticies used to create the path
     */
    private List<E> makePath (Map <Vertex<E>, Vertex<E>> predecessors, Vertex<E> end) {

        if (predecessors.containsKey(end)) {
            List <E> path = new LinkedList<>();
            Vertex <E> current = end;

            while (current != null) {
                path.add(0, current.getValue());
                current = predecessors.get(current);
            }

            return path;
        }

        return null;    
    }

    public Map <E, Vertex<E>> getVerticies() {
        return vertices;
    }


}


