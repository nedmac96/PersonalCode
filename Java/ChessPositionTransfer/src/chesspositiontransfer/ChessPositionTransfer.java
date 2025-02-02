/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package chesspositiontransfer;

/**
 *
 * @author Camden
 */
public class ChessPositionTransfer
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        // TODO code application logic here
        String position1 = "6k1/p1R2p2/5qpp/1r1P4/1p2B3/P2Q2Pb/5P1P/6K1";
        String position2 = "";
        for (int i = 0; i < position1.length(); i++)
        {
            char value = position1.charAt(i);
            if (value == 'r')
            {
                position2 = position2 + 'T';
            } else if (value == 'b')
            {
                position2 = position2 + 'V';
            } else if (value == 'n')
            {
                position2 = position2 + 'M';
            } else if (value == 'q')
            {
                position2 = position2 + 'W';
            } else if (value == 'k')
            {
                position2 = position2 + 'L';
            } else if (value == 'p')
            {
                position2 = position2 + 'O';
            } else if (value == 'P' || value == 'Q' || value == 'K' || value == 'N' || value == 'B' || value == 'R')
            {
                position2 = position2 + value;
            } else if (value != '/')
            {
                for (int j = 0; j < Integer.parseInt(value + ""); j++)
                {
                    position2 = position2 + "x";
                }
            }
        }
        System.out.println(position2);
    }
}
