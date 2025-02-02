/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datepuzzlesolver;

/**
 *
 * @author Camden
 */
public class Piece
{

    private int id;
    private String name;
    private int height;
    private int width;
    private int rotation;
    private int[] squaresUsed;
    private boolean canBeFlipped;
    private boolean canBeRotated;
    private int symmetry;

    public Piece(int id)
    {
        this.id = id;
        rotation = 0;
        switch (id)
        {
            case 0:
                name = "+";
                height = 3;
                width = 3;
                canBeFlipped = false;
                canBeRotated = false;
                symmetry = 2;
                squaresUsed = new int[]
                {
                    1, 7, 8, 9, 15
                };
                break;
            case 1:
                name = "C";
                height = 3;
                width = 2;
                canBeFlipped = false;
                canBeRotated = true;
                symmetry = 0;
                squaresUsed = new int[]
                {
                    0, 1, 7, 14, 15
                };
                break;
            case 2:
                name = "S";
                height = 3;
                width = 3;
                canBeFlipped = true;
                canBeRotated = true;
                symmetry = 1;
                squaresUsed = new int[]
                {
                    1, 2, 8, 14, 15
                };
                break;
            case 3:
                name = "L";
                height = 4;
                width = 2;
                canBeFlipped = true;
                canBeRotated = true;
                symmetry = 0;
                squaresUsed = new int[]
                {
                    0, 7, 14, 21, 22
                };
                break;
            case 4:
                name = "P";
                height = 3;
                width = 2;
                canBeFlipped = true;
                canBeRotated = true;
                symmetry = 0;
                squaresUsed = new int[]
                {
                    0, 1, 7, 8, 14
                };
                break;
            case 5:
                name = "s";
                height = 2;
                width = 3;
                canBeFlipped = true;
                canBeRotated = true;
                symmetry = 1;
                squaresUsed = new int[]
                {
                    1, 2, 7, 8
                };
                break;
            case 6:
                name = "T";
                height = 2;
                width = 3;
                canBeFlipped = false;
                canBeRotated = true;
                symmetry = 0;
                squaresUsed = new int[]
                {
                    0, 1, 2, 8
                };
                break;
            case 7:
                name = "l";
                height = 3;
                width = 2;
                canBeFlipped = true;
                canBeRotated = true;
                symmetry = 0;
                squaresUsed = new int[]
                {
                    0, 7, 14, 15
                };
                break;
            case 8:
                name = "O";
                height = 2;
                width = 2;
                canBeFlipped = false;
                canBeRotated = false;
                symmetry = 2;
                squaresUsed = new int[]
                {
                    0, 1, 7, 8
                };
                break;
            case 10:
                name = "_";
            default:
                name = "x";
        }
    }

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public int getHeight()
    {
        return height;
    }

    public int getWidth()
    {
        return width;
    }

    public int getRotation()
    {
        return rotation;
    }

    public int[] getSquaresUsed()
    {
        return squaresUsed;
    }

    public boolean canBeFlipped()
    {
        return canBeFlipped;
    }

    public boolean canBeRotated()
    {
        return canBeRotated;
    }

    public int getSymmetry()
    {
        return symmetry;
    }

    public void rotate()
    {
        // Rotate squares used
        int[] currentSquares = squaresUsed;
        for (int i = 0; i < squaresUsed.length; i++)
        {
            squaresUsed[i] = (height - (currentSquares[i] / 7)) + ((currentSquares[i] % 7) * 7) - 1;
        }

        // Switch height and width
        height = height + width;
        width = height - width;
        height = height - width;

        // Determine can be rotated
        rotation++;
        if (rotation == 3)
        {
            canBeRotated = false;
        } else if (rotation == 1 && symmetry == 1)
        {
            canBeRotated = false;
        }
    }

    public void flip()
    {
        // Flip squares used
        int[] currentSquares = squaresUsed;
        for (int i = 0; i < squaresUsed.length; i++)
        {
            squaresUsed[i] = (((height - (currentSquares[i] / 7)) - 1) * 7) + (currentSquares[i] % 7);
        }

        // Reset rotations
        rotation = 0;
        if (symmetry != 2)
        {
            canBeRotated = true;
        }

    }

}
