package Minesweeper.graphs;

import java.util.Set;
import java.util.HashSet;

public class Vertex<E> {
    protected E value;
    private HashSet<Vertex<E>> neighbors;

    /**
     * Instance of vertex
     * @param value
     */
    public Vertex(E value) {
        this.value = value;
        neighbors = new HashSet<>();
    }

    /**
     * retrieves value of vertex
     * @return
     *      value of vertex
     */
    public E getValue() {
        return value;
    }

    /**
     * connects vertices together 
     * @param neighbor
     *      vertex immediately next this vertex
     */
    public void connect(Vertex<E> neighbor) {
        neighbors.add(neighbor);
    }

    /**
     * Detirmines if vertex is connected to another
     * @param neighbor
     *      vertex being compared 
     * @return
     *      true if neighbor is connected, false if not
     */
    public boolean connected(Vertex<E> neighbor) {
        return neighbors.contains(neighbor);
    }

    /**
     * Retrieves neighbors of this vertex
     * @return
     *      All neoghbors of this vertex
     */
    public Set<Vertex<E>> getNeighbors() {
        return neighbors;
    }

    /**
     * Determines if two vertices are equal
     * @param o
     *      Object being compared
     * @return
     *      true if vertices are equal, false otherwise
     */
    @Override
    @SuppressWarnings ("unchecked")
    public boolean equals(Object o){
        try{
            Vertex<E> vo = (Vertex<E>)o;
            return vo.value == this.value;
        }catch(Exception e){
            System.err.println("invalid equals call in Vertex");
        }
       
        return false;
    }

    /**
     * used implicitly by java for hashcode
     */
    @Override
    public String toString(){
        return "Generic Vertex:" + this.value;
    }
    
}
