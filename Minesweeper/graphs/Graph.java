package Minesweeper.graphs;

import java.util.List;

public interface Graph <E> {

    void add(E value);
    boolean contains(E value);
    int size();
    void connectDirected(E a, E b);
    void connectUndirected(E a, E b);
    boolean connected(E a, E b);

    default boolean bfSearch (E start, E ends) {
        throw new UnsupportedOperationException("BFP not implemented");
    }

    default List<E> bfPath (E start, E end) {
        throw new UnsupportedOperationException("BFP not implemented");
    }


}

