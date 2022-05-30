package coll.Matrix;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A generic 2D-matrix.
 * @param <E> the cell type.
 */

public class Matrix<T> implements Iterable<T> {


    private int rows,columns;
    private T[] matrix;


    /**
     * Constructs a Matrix.
     *
     * @param rows - the number of rows.
     * @param columns - the number of columns.
     */
    public Matrix(int rows,int columns) {
        this.rows = rows;
        this.columns = columns;
        this.matrix = (T[]) new Object[this.rows * this.columns];

    }

    /**
     * Assigns a value to a given cell, specified by its row, column coordinates.
     *
     * @param row - the row index with 0-based indexing.
     * @param column - the column index with 0-based indexing.
     * @param value - the value to be assigned to the given cell.
     */
    public void insert(int row,int column, T value) {
        this.matrix[column + (row * this.columns)] = value;
    }

    /**
     * Gets the value at a given cell, specified by its row, column coordinates.
     *
     * @param row - the row index with 0-based indexing.
     * @param column - the column index with 0-based indexing.
     * @return the value located at the given cell.
     */
    public T get(int row, int column) {
        return this.matrix[column + (row * this.columns)];
    }

    /**
     * Gets the total number of cells in the matrix.
     *
     * @return an int equal to the total number of cells in the matrix.
     */
    public int size() {
       return this.rows * this.columns;
    }

    /**
     * Converts the matrix to String format.
     *
     * @return a String representation of the matrix.
     */
    @Override
    public String toString() {
       StringBuilder buffer = new StringBuilder();
       for (int row = 0; row < this.rows;row++){
           ArrayList<String> string_vec = new ArrayList<>();
           for (int col = 0; col < this.columns;col++){
                string_vec.add(String.valueOf(get(row,col)));
           }
           buffer.append(string_vec.stream().collect(Collectors.joining("\t"))).append("\n");
       }

       return buffer.toString();
    }

    /**
     * Gets an iterator for the matrix. The iterator follows column-major order.
     *
     * @return an iterator for the matrix.
     */
    @Override
    public Iterator<T> iterator() {
        ArrayList<T> new_matrix = new ArrayList<>();
        for(int col = 0; col < this.columns; col++) {
            for(int row = 0; row < this.rows; row++){
                new_matrix.add(get(row,col));
            }
        }
        return new_matrix.stream().iterator();
    }
}