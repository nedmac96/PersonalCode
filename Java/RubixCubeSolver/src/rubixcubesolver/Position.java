/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rubixcubesolver;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Camden
 */
public class Position
{

    /*
    White - 0
    Yellow - 1
    Red - 2
    Orange - 3
    Green - 4
    Blue - 5
    
     */
    private int[][] position;

    public Position(int[][] position)
    {
        this.position = position;
    }

    public boolean isValid()
    {
        if (position.length != 6)
        {
            return false;
        }
        for (int i = 0; i < position.length; i++)
        {
            if (position[i].length != 9)
            {
                return false;
            }
            if (position[i][4] != i)
            {
                return false;
            }
        }
        ArrayList<Piece> validCorners = new ArrayList<>();
        validCorners.add(new Piece(0, 2, 4));
        validCorners.add(new Piece(0, 3, 4));
        validCorners.add(new Piece(0, 3, 5));
        validCorners.add(new Piece(0, 2, 5));
        validCorners.add(new Piece(1, 2, 4));
        validCorners.add(new Piece(1, 2, 5));
        validCorners.add(new Piece(1, 3, 4));
        validCorners.add(new Piece(1, 3, 5));
        ArrayList<Piece> validSides = new ArrayList<>();
        validSides.add(new Piece(0, 2, 9));
        validSides.add(new Piece(0, 3, 9));
        validSides.add(new Piece(0, 4, 9));
        validSides.add(new Piece(0, 5, 9));
        validSides.add(new Piece(1, 2, 9));
        validSides.add(new Piece(1, 3, 9));
        validSides.add(new Piece(1, 4, 9));
        validSides.add(new Piece(1, 5, 9));
        validSides.add(new Piece(2, 4, 9));
        validSides.add(new Piece(2, 5, 9));
        validSides.add(new Piece(3, 4, 9));
        validSides.add(new Piece(3, 5, 9));

        ArrayList<Piece> corners = new ArrayList<>();
        corners.add(new Piece(position[0][6], position[2][6], position[5][8]));
        corners.add(new Piece(position[0][0], position[2][8], position[4][6]));
        corners.add(new Piece(position[0][2], position[3][6], position[4][8]));
        corners.add(new Piece(position[0][8], position[3][8], position[5][6]));
        corners.add(new Piece(position[1][0], position[2][0], position[5][2]));
        corners.add(new Piece(position[1][2], position[3][2], position[5][0]));
        corners.add(new Piece(position[1][6], position[2][2], position[4][0]));
        corners.add(new Piece(position[1][8], position[3][0], position[4][2]));
        ArrayList<Piece> sides = new ArrayList<>();
        sides.add(new Piece(position[0][1], position[4][7], 9));
        sides.add(new Piece(position[0][3], position[2][7], 9));
        sides.add(new Piece(position[0][5], position[3][7], 9));
        sides.add(new Piece(position[0][7], position[5][7], 9));
        sides.add(new Piece(position[1][1], position[5][1], 9));
        sides.add(new Piece(position[1][3], position[2][1], 9));
        sides.add(new Piece(position[1][5], position[3][1], 9));
        sides.add(new Piece(position[1][7], position[4][1], 9));
        sides.add(new Piece(position[2][5], position[4][3], 9));
        sides.add(new Piece(position[2][3], position[5][5], 9));
        sides.add(new Piece(position[3][5], position[5][3], 9));
        sides.add(new Piece(position[3][3], position[4][5], 9));

        for (Piece piece : validCorners)
        {
            if (!corners.contains(piece))
            {
                System.out.println(piece);
                return false;
            }
        }
        for (Piece piece : validSides)
        {
            if (!sides.contains(piece))
            {
                System.out.println(piece);
                return false;
            }
        }
        return true;
    }

    /*
    White - 0
    Yellow - 1
    Red - 2
    Orange - 3
    Green - 4
    Blue - 5
     */
    public ArrayList<Position> getPosMoves()
    {
        ArrayList<Position> posMoves = new ArrayList<>();

        int[][] newPos1;
        int[][] newPos2;
        for (int i = 0; i < position.length; i++)
        {

            newPos1 = Arrays.stream(position)
                    .map((int[] row) -> row.clone())
                    .toArray((int length) -> new int[length][]);
            newPos1[i][0] = position[i][6];
            newPos1[i][1] = position[i][3];
            newPos1[i][2] = position[i][0];
            newPos1[i][3] = position[i][7];
            newPos1[i][5] = position[i][1];
            newPos1[i][6] = position[i][8];
            newPos1[i][7] = position[i][5];
            newPos1[i][8] = position[i][2];

            newPos2 = Arrays.stream(position)
                    .map((int[] row) -> row.clone())
                    .toArray((int length) -> new int[length][]);
            newPos2[i][6] = position[i][0];
            newPos2[i][3] = position[i][1];
            newPos2[i][0] = position[i][2];
            newPos2[i][7] = position[i][3];
            newPos2[i][1] = position[i][5];
            newPos2[i][8] = position[i][6];
            newPos2[i][5] = position[i][7];
            newPos2[i][2] = position[i][8];
            switch (i)
            {
                case 0:
                {
                    int[] sides =
                    {
                        5, 3, 4, 2
                    };
                    int[] pos =
                    {
                        6, 7, 8
                    };
                    for (int j = 0; j < sides.length; j++)
                    {
                        for (int k = 0; k < pos.length; k++)
                        {
                            newPos1[sides[j]][pos[k]] = position[sides[(j + 1) % sides.length]][pos[k]];
                            newPos2[sides[(j + 1) % sides.length]][pos[k]] = position[sides[j]][pos[k]];
                        }
                    }
                    break;
                }
                case 1:
                {
                    int[] sides =
                    {
                        2, 4, 3, 5
                    };
                    int[] pos =
                    {
                        0, 1, 2
                    };
                    for (int j = 0; j < sides.length; j++)
                    {
                        for (int k = 0; k < pos.length; k++)
                        {
                            newPos1[sides[j]][pos[k]] = position[sides[(j + 1) % sides.length]][pos[k]];
                            newPos2[sides[(j + 1) % sides.length]][pos[k]] = position[sides[j]][pos[k]];
                        }
                    }
                    break;
                }
                case 2:
                {
                    newPos1[0][0] = position[4][0];
                    newPos1[0][3] = position[4][3];
                    newPos1[0][6] = position[4][6];
                    newPos1[4][0] = position[1][0];
                    newPos1[4][3] = position[1][3];
                    newPos1[4][6] = position[1][6];
                    newPos1[1][0] = position[5][8];
                    newPos1[1][3] = position[5][5];
                    newPos1[1][6] = position[5][2];
                    newPos1[5][2] = position[0][6];
                    newPos1[5][5] = position[0][3];
                    newPos1[5][8] = position[0][0];

                    newPos2[1][0] = position[4][0];
                    newPos2[1][3] = position[4][3];
                    newPos2[1][6] = position[4][6];
                    newPos2[5][8] = position[1][0];
                    newPos2[5][5] = position[1][3];
                    newPos2[5][2] = position[1][6];
                    newPos2[0][0] = position[5][8];
                    newPos2[0][3] = position[5][5];
                    newPos2[0][6] = position[5][2];
                    newPos2[4][6] = position[0][6];
                    newPos2[4][3] = position[0][3];
                    newPos2[4][0] = position[0][0];
                    break;
                }
                case 3:
                {
                    newPos1[1][8] = position[4][0];
                    newPos1[1][5] = position[4][3];
                    newPos1[1][2] = position[4][6];
                    newPos1[5][8] = position[1][8];
                    newPos1[5][5] = position[1][5];
                    newPos1[5][2] = position[1][2];
                    newPos1[0][8] = position[5][8];
                    newPos1[0][5] = position[5][5];
                    newPos1[0][2] = position[5][2];
                    newPos1[4][6] = position[0][2];
                    newPos1[4][3] = position[0][5];
                    newPos1[4][0] = position[0][8];

                    newPos2[0][8] = position[4][0];
                    newPos2[0][5] = position[4][3];
                    newPos2[0][2] = position[4][6];
                    newPos2[4][0] = position[1][8];
                    newPos2[4][3] = position[1][5];
                    newPos2[4][6] = position[1][2];
                    newPos2[1][8] = position[5][8];
                    newPos2[1][5] = position[5][5];
                    newPos2[1][2] = position[5][2];
                    newPos2[5][2] = position[0][2];
                    newPos2[5][5] = position[0][5];
                    newPos2[5][8] = position[0][8];

                    break;
                }
                case 4:
                {
                    newPos1[0][0] = position[3][6];
                    newPos1[0][1] = position[3][3];
                    newPos1[0][2] = position[3][0];
                    newPos1[1][6] = position[2][8];
                    newPos1[1][7] = position[2][5];
                    newPos1[1][8] = position[2][2];
                    newPos1[2][2] = position[0][0];
                    newPos1[2][5] = position[0][1];
                    newPos1[2][8] = position[0][2];
                    newPos1[3][0] = position[1][6];
                    newPos1[3][3] = position[1][7];
                    newPos1[3][6] = position[1][8];

                    newPos2[0][0] = position[2][2];
                    newPos2[0][1] = position[2][5];
                    newPos2[0][2] = position[2][8];
                    newPos2[1][6] = position[3][0];
                    newPos2[1][7] = position[3][3];
                    newPos2[1][8] = position[3][6];
                    newPos2[2][2] = position[1][8];
                    newPos2[2][5] = position[1][7];
                    newPos2[2][8] = position[1][6];
                    newPos2[3][0] = position[0][2];
                    newPos2[3][3] = position[0][1];
                    newPos2[3][6] = position[0][0];
                    break;
                }
                case 5:
                {
                    newPos1[0][6] = position[2][0];
                    newPos1[0][7] = position[2][3];
                    newPos1[0][8] = position[2][6];
                    newPos1[1][0] = position[3][2];
                    newPos1[1][1] = position[3][5];
                    newPos1[1][2] = position[3][8];
                    newPos1[2][0] = position[1][2];
                    newPos1[2][3] = position[1][1];
                    newPos1[2][6] = position[1][0];
                    newPos1[3][2] = position[0][8];
                    newPos1[3][5] = position[0][7];
                    newPos1[3][8] = position[0][6];

                    newPos2[0][6] = position[3][8];
                    newPos2[0][7] = position[3][5];
                    newPos2[0][8] = position[3][2];
                    newPos2[1][0] = position[2][6];
                    newPos2[1][1] = position[2][3];
                    newPos2[1][2] = position[2][0];
                    newPos2[2][0] = position[0][6];
                    newPos2[2][3] = position[0][7];
                    newPos2[2][6] = position[0][8];
                    newPos2[3][2] = position[1][0];
                    newPos2[3][5] = position[1][1];
                    newPos2[3][8] = position[1][2];
                    break;
                }
            }
            Position pos1 = new Position(newPos1);
            posMoves.add(pos1);
            Position pos2 = new Position(newPos2);
            posMoves.add(pos2);
        }
        return posMoves;
    }

    public ArrayList<Position> getPosSideMoves()
    {
        ArrayList<Position> posMoves = new ArrayList<>();
        for (int j = 0; j < position.length; j++)
        {
            for (int i = 1; i < 4; i++)
            {
                Move move = new Move(i, j, this);
                posMoves.add(move.newPosition);
            }
        }
        return posMoves;
    }

    public int[][] getPosition()
    {
        return position;
    }

    public boolean isTheSame(Position pos)
    {
        if (pos == null)
        {
            return false;
        }
        for (int i = 0; i < this.position.length; i++)
        {
            if (!Arrays.equals(pos.position[i], this.position[i]))
            {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 67 * hash + Arrays.deepHashCode(this.position);
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final Position other = (Position) obj;
        return Arrays.deepEquals(this.position, other.position);
    }

    public boolean isSolved()
    {
        for (int i = 0; i < position.length; i++)
        {
            for (int j = 0; j < position[i].length; j++)
            {
                if (position[i][j] != i)
                {
                    return false;
                }
            }
        }
        return true;
    }

    public void printPos()
    {
        for (int[] side : position)
        {
            for (int i = 0; i < 3; i++)
            {
                for (int j = 0; j < 3; j++)
                {
                    System.out.print(toChar(side[i * 3 + j]));
                }
                System.out.println("");
            }
            System.out.println("");
        }
    }

    public char toChar(int color)
    {
        switch (color)
        {
            case 0:
                return 'W';
            case 1:
                return 'Y';
            case 2:
                return 'R';
            case 3:
                return 'O';
            case 4:
                return 'G';
            case 5:
                return 'B';
            default:
                throw new AssertionError();
        }
    }

    public int score()
    {
        if (isSolved())
        {
            return 1000;
        }
        int score = 0;
        for (int[] side : position)
        {
            if (side[1] == side[4])
            {
                score += 2;
            }
            if (side[1] == side[0])
            {
                score++;
            }
            if (side[1] == side[2])
            {
                score++;
            }
            if (side[3] == side[4])
            {
                score += 2;
            }
            if (side[3] == side[0])
            {
                score++;
            }
            if (side[3] == side[6])
            {
                score++;
            }
            if (side[5] == side[4])
            {
                score += 2;
            }
            if (side[5] == side[2])
            {
                score++;
            }
            if (side[5] == side[8])
            {
                score++;
            }
            if (side[7] == side[4])
            {
                score += 2;
            }
            if (side[7] == side[8])
            {
                score++;
            }
            if (side[7] == side[6])
            {
                score++;
            }
            if (side[0] == side[4] && side[1] == side[4] && side[2] == side[4] && side[3] == side[4] && side[5] == side[4] && side[6] == side[4] && side[7] == side[4] && side[8] == side[4])
            {
                score += 5;
            }
        }
        return score;
    }
}
