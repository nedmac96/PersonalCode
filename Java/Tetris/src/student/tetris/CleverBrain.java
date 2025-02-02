// Virginia Tech Honor Code Pledge:
//
// As a Hokie, I will conduct myself with honor and integrity at all times.
// I will not lie, cheat, or steal, nor will I accept the actions of those
// who do.
// -- Chloe Chappelle (cchappe4)

import student.tetris.*;
//-------------------------------------------------------------------------

/**
 * Write a one-sentence summary of your class here. Follow it with additional
 * details about its purpose, what abstraction it represents, and how to use it.
 *
 * @author Chloe Chappelle (cchappe4)
 * @version 2022.08.06
 */
public class CleverBrain implements Brain
{
    //~ Fields ................................................................

    //~ Constructor ...........................................................
    // ----------------------------------------------------------
    /**
     * Initializes a newly created CleverBrain object.
     */
    public CleverBrain()
    {
        super();
        /*# Do any work to initialize your class here. */
    }

    //~ Methods ...............................................................
    public Move bestMove(Board board, Piece piece, int heightLimit)
    {
        double bestRating = 10000;
        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 20; j++)
            {

            }
        }
        Move move = new Move(piece);
        return move;
    }

    public double rateBoard(Board board)
    {
        double averageHeight = calcAverageColumnHeight(board);
        int numBlocks = calcNumBlocks(board);
        return averageHeight + (numBlocks / 2);
    }

    /**
     * Calculates the average columns height using the getColumnHeights() method
     * from the Board class.
     *
     * @param board The board to find the average columns heights for.
     *
     * @return The average column height of the board (a double).
     */
    public double calcAverageColumnHeight(Board board)
    {
        // get all the column heights
        int[] columnHeights = board.getColumnHeights();

        // iterate over the columns to find the average
        int sum = 0;
        for (int i = 0; i < columnHeights.length; i++)
        {
            sum += columnHeights[i];
        }

        // sum of the column heights divided by the number of columns
        return sum / (double) columnHeights.length;
    }

    /**
     * Calculates the total number of blocks on the board.
     *
     * @param board The board to get the number of blocks from.
     *
     * @return The total number of blocks on the board (an int).
     */
    public int calcNumBlocks(Board board)
    {
        // get the number of blocks per row
        int[] blocksRows = board.getBlocksInAllRows();

        // iterate over the rows to find the total number of blocks
        int sumBlocks = 0;
        for (int i = 0; i < blocksRows.length; i++)
        {
            sumBlocks += blocksRows[i];
        }

        return sumBlocks;
    }
}
