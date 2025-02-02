/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rubixcubesolver;

import java.util.Arrays;

/**
 *
 * @author Camden
 */
public class Move
{

    private int rotations;
    private int side;
    public Position newPosition;

    public Move(int rotations, int side, Position pos)
    {
        this.rotations = rotations;
        this.side = side;

        int[][] sidePieces = new int[4][3];
        int[] sides = new int[4];

        switch (side)
        {
            case 0:
                sides[0] = 2;
                sides[1] = 4;
                sides[2] = 3;
                sides[3] = 5;

                for (int[] sideSet : sidePieces)
                {
                    sideSet[0] = 6;
                    sideSet[1] = 7;
                    sideSet[2] = 8;
                }
                break;
            case 1:
                sides[0] = 2;
                sides[1] = 5;
                sides[2] = 3;
                sides[3] = 4;

                for (int[] sideSet : sidePieces)
                {
                    sideSet[0] = 2;
                    sideSet[1] = 1;
                    sideSet[2] = 0;
                }
                break;
            case 2:
                sides[0] = 0;
                sides[1] = 5;
                sides[2] = 1;
                sides[3] = 4;

                sidePieces[0][0] = 0;
                sidePieces[0][1] = 3;
                sidePieces[0][2] = 6;

                sidePieces[1][0] = 8;
                sidePieces[1][1] = 5;
                sidePieces[1][2] = 2;

                sidePieces[2][0] = 0;
                sidePieces[2][1] = 3;
                sidePieces[2][2] = 6;

                sidePieces[3][0] = 0;
                sidePieces[3][1] = 3;
                sidePieces[3][2] = 6;
                break;
            case 3:
                sides[0] = 0;
                sides[1] = 4;
                sides[2] = 1;
                sides[3] = 5;

                sidePieces[0][0] = 8;
                sidePieces[0][1] = 5;
                sidePieces[0][2] = 2;

                sidePieces[1][0] = 8;
                sidePieces[1][1] = 5;
                sidePieces[1][2] = 2;

                sidePieces[2][0] = 8;
                sidePieces[2][1] = 5;
                sidePieces[2][2] = 2;

                sidePieces[3][0] = 0;
                sidePieces[3][1] = 3;
                sidePieces[3][2] = 6;
                break;
            case 4:
                sides[0] = 0;
                sides[1] = 2;
                sides[2] = 1;
                sides[3] = 3;

                sidePieces[0][0] = 2;
                sidePieces[0][1] = 1;
                sidePieces[0][2] = 0;

                sidePieces[1][0] = 8;
                sidePieces[1][1] = 5;
                sidePieces[1][2] = 2;

                sidePieces[2][0] = 6;
                sidePieces[2][1] = 7;
                sidePieces[2][2] = 8;

                sidePieces[3][0] = 0;
                sidePieces[3][1] = 3;
                sidePieces[3][2] = 6;
                break;
            case 5:
                sides[0] = 0;
                sides[1] = 3;
                sides[2] = 1;
                sides[3] = 2;

                sidePieces[0][0] = 6;
                sidePieces[0][1] = 7;
                sidePieces[0][2] = 8;

                sidePieces[1][0] = 8;
                sidePieces[1][1] = 5;
                sidePieces[1][2] = 2;

                sidePieces[2][0] = 2;
                sidePieces[2][1] = 1;
                sidePieces[2][2] = 0;

                sidePieces[3][0] = 0;
                sidePieces[3][1] = 3;
                sidePieces[3][2] = 6;
                break;
            default:
                throw new AssertionError();
        }
        int[][] position = pos.getPosition();
        int[][] newPos = Arrays.stream(position)
                .map((int[] row) -> row.clone())
                .toArray((int length) -> new int[length][]);
        for (int i = 0; i < sides.length; i++)
        {
            int fromIndex = i - rotations;
            if (fromIndex < 0)
            {
                fromIndex += 4;
            }
            for (int j = 0; j < 3; j++)
            {
                newPos[sides[i]][sidePieces[i][j]] = position[sides[fromIndex]][sidePieces[fromIndex][j]];
            }
        }
        this.newPosition = new Position(newPos);
    }

    public void printMove()
    {
        switch (side)
        {
            case 0 ->
                System.out.print("W");
            case 1 ->
                System.out.print("Y");
            case 2 ->
                System.out.print("R");
            case 3 ->
                System.out.print("O");
            case 4 ->
                System.out.print("G");
            case 5 ->
                System.out.print("B");
            default ->
                throw new AssertionError();
        }
        switch (rotations)
        {
            case 0:
                System.out.println("X");
                break;
            case 1:
                System.out.println("R");
                break;
            case 2:
                System.out.println("2");
                break;
            case 3:
                System.out.println("L");
                break;
            default:
                throw new AssertionError();
        }
    }
}
