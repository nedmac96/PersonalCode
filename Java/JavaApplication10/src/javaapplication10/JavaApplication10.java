/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication10;

/**
 *
 * @author Camden
 */
public class JavaApplication10
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        // TODO code application logic here
        int[] counts =
        {
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
        };
        int numHands = 0;
        final int totalHands1 = 1000000000;
        final int totalHands2 = 100;
        int player1Spades;
        int player2Spades;
        int player3Spades;
        int player4Spades;
        int cardInDeck;
        int spadesInDeck;
        for (int h = 0; h < totalHands2; h++)
        {
            for (int i = 0; i < totalHands1; i++)
            {
                player1Spades = 0;
                player2Spades = 0;
                player3Spades = 0;
                player4Spades = 0;
                cardInDeck = 52;
                spadesInDeck = 13;
                while (cardInDeck != 0)
                {
                    int j = 1;
                    for (int k = 0; k < 4; k++)
                    {
                        double currentCard = 1 + Math.floor(Math.random() * cardInDeck);
                        cardInDeck--;
                        if (currentCard < spadesInDeck + 1)
                        {
                            spadesInDeck--;
                            switch (j)
                            {
                                case 1 ->
                                    player1Spades++;
                                case 2 ->
                                    player2Spades++;
                                case 3 ->
                                    player3Spades++;
                                default ->
                                    player4Spades++;
                            }
                        }
                        j++;
                    }
                }

                int tempCount = player1Spades;
                counts[tempCount]++;

                tempCount = player2Spades;
                counts[tempCount]++;

                tempCount = player3Spades;
                counts[tempCount]++;

                tempCount = player4Spades;
                counts[tempCount]++;
            }
            System.out.println(counts[11] + " " + counts[12] + " " + counts[13]);
        }
        for (int i = 0; i < counts.length; i++)
        {
            System.out.println(i + ": " + counts[i]);
        }
        System.out.println("");
        for (int i = 0; i < counts.length; i++)
        {
            System.out.println(i + ": " + (((counts[i] * 1.0) / (totalHands1 * 0.04)) / (totalHands2 * 1.0)));
        }

    }
}
