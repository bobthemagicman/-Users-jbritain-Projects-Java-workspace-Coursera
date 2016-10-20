import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int gridSize;
    private final int rowSize;
    private final boolean[] openCells;

    private final WeightedQuickUnionUF grid;
    private final WeightedQuickUnionUF virtTop;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        this.rowSize = n;
        this.gridSize = rowSize * rowSize;

        grid = new WeightedQuickUnionUF(gridSize + 2);
        virtTop = new WeightedQuickUnionUF(gridSize + 1);

        openCells = new boolean[gridSize];
        for (int x = 0; x < gridSize; x++) {
            openCells[x] = false;
        }
    }

    public void open(int row, int col) {
        validate(row, col);
        if (isOpen(row, col)) {
            return;
        }

        int cell = findCell(row, col);
        openCells[cell - 1] = true;

        // above
        final int cellAbove = cell - rowSize;
        if (cellAbove > 0 && isOpen(row - 1, col)) {
            grid.union(cell, cellAbove);
            virtTop.union(cell, cellAbove);
            openCells[cellAbove - 1] = true;
            unionTopAndBottomRowsToVirtualSite(cellAbove);
        }

        // below
        final int cellBelow = cell + rowSize;
        if (cellBelow <= gridSize && isOpen(row + 1, col)) {
            grid.union(cell, cellBelow);
            virtTop.union(cell, cellBelow);
            openCells[cellBelow - 1] = true;
            unionTopAndBottomRowsToVirtualSite(cellBelow);
        }

        // left
        if (cell % rowSize != 1 && (col - 1 > 0 && isOpen(row, col - 1))) {
            grid.union(cell, cell - 1);
            virtTop.union(cell, cell - 1);
            openCells[cell - 2] = true;
        }

        // right
        if (cell % rowSize != 0 && (col + 1 <= gridSize && isOpen(row, col + 1))) {
            grid.union(cell, cell + 1);
            virtTop.union(cell, cell + 1);
            openCells[cell] = true;
        }

        unionTopAndBottomRowsToVirtualSite(cell);
    }

    private void unionTopAndBottomRowsToVirtualSite(int cell) {
        // top row
        if (cell <= rowSize) {
            grid.union(cell, 0);
            virtTop.union(cell, 0);
        }

        // bottom row

        if (cell > (rowSize * (rowSize - 1)) && cell <= gridSize) {
            grid.union(cell, gridSize + 1);
        }
    }

    public boolean isOpen(int row, int col) {
        validate(row, col);

        return openCells[findCell(row, col) - 1];
    }

    public boolean isFull(int row, int col) {
        validate(row, col);
        final int cell = findCell(row, col);

        return grid.connected(0, cell) && virtTop.connected(cell, 0);
    }

    public boolean percolates() {

        return grid.connected(0, gridSize + 1);
    }

    private int findCell(int row, int col) {
        return rowSize * (row - 1) + col;
    }

    private void validate(final int row, final int col) {
        if (row <= 0 || col <= 0) {
            throw new IndexOutOfBoundsException();
        }

        if (row > rowSize || col > rowSize) {
            throw new IndexOutOfBoundsException();
        }
    }

    public static void main(String[] args) {
    }
}