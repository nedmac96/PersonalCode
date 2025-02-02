/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package unoflipgame;

import java.util.ArrayList;

/**
 *
 * @author Camden
 */
public class Deck
{

    /*
    Number:
    1 to 9 are numbers
    10 is +1 or +5
    11 is reverse
    12 is skip or you get to go again
    13 is flip
    14 is wild
    15 is wild draw 2 or draw until you get color
    
    Color:
    0 is red or pink
    1 is green or teal
    2 is blue or purple
    3 is yellow or orange
    4 is wild
     */
    private ArrayList<Card> drawPile;
    private ArrayList<Card> playPile;
    private boolean isLight;
    private boolean isClockDir;
    private int smallestHandSize;

    public Deck()
    {
        drawPile = new ArrayList<>();
        playPile = new ArrayList<>();
    }

    public void setUpRound()
    {
        drawPile.clear();
        playPile.clear();
        isLight = true;
        isClockDir = true;

        int[] darkNumbers =
        {
            3, 2, 11, 10, 15, 7, 10, 13, 5, 2, 9, 12, 1, 5, 11, 7, 5, 11, 3, 4, 7, 3, 10, 14, 8, 3, 7, 9,
            13, 5, 12, 10, 2, 13, 8, 9, 7, 4, 15, 5, 6, 2, 11, 9, 11, 10, 6, 6, 1, 7, 9, 4, 3, 15, 13, 7,
            12, 12, 8, 6, 2, 8, 10, 1, 11, 9, 11, 12, 12, 3, 4, 11, 13, 5, 6, 6, 4, 14, 1, 9, 6, 7, 5, 4,
            14, 12, 8, 1, 1, 10, 13, 10, 8, 9, 12, 15, 6, 2, 1, 2, 5, 4, 1, 8, 14, 13, 3, 13, 4, 8, 3, 2
        };
        int[] darkColors =
        {
            0, 2, 3, 2, 4, 0, 2, 3, 1, 0, 3, 0, 3, 2, 2, 1, 2, 1, 0, 0, 1, 2, 3, 4, 0, 2, 2, 2,
            3, 3, 1, 1, 2, 0, 0, 1, 3, 1, 4, 0, 3, 1, 0, 1, 0, 3, 1, 3, 3, 0, 3, 2, 1, 4, 0, 3,
            2, 2, 3, 0, 1, 2, 1, 2, 3, 0, 2, 1, 3, 3, 1, 1, 2, 3, 0, 1, 3, 4, 1, 0, 2, 2, 0, 3,
            4, 0, 1, 1, 2, 0, 2, 0, 1, 2, 3, 4, 2, 3, 0, 3, 1, 2, 0, 2, 4, 1, 3, 1, 0, 3, 1, 0
        };
        for (int i = 0; i < 4; i++)
        {
            for (int j = 1; j < 15; j++)
            {
                if (j != 14)
                {
                    drawPile.add(new Card(j, i, darkNumbers[(i * 14 + (j - 1)) * 2], darkColors[(i * 14 + (j - 1)) * 2]));
                    drawPile.add(new Card(j, i, darkNumbers[(i * 14 + (j - 1)) * 2 + 1], darkColors[(i * 14 + (j - 1)) * 2 + 1]));
                } else
                {
                    drawPile.add(new Card(14, 4, darkNumbers[(i * 14 + (j - 1)) * 2], darkColors[(i * 14 + (j - 1)) * 2]));
                    drawPile.add(new Card(15, 4, darkNumbers[(i * 14 + (j - 1)) * 2 + 1], darkColors[(i * 14 + (j - 1)) * 2 + 1]));
                }
            }
        }
    }

    public ArrayList<Card> getDrawPile()
    {
        return drawPile;
    }

    public ArrayList<Card> getPlayPile()
    {
        return playPile;
    }

    public Card drawCard()
    {
        if (!drawPile.isEmpty())
        {
            int randNum = (int) (Math.random() * drawPile.size());
            Card card = drawPile.get(randNum);
            drawPile.remove(randNum);
            if (drawPile.isEmpty())
            {
                if (playPile.size() > 2)
                {
                    while (playPile.size() > 2)
                    {
                        playPile.get(1).reset();
                        drawPile.add(playPile.get(1));
                        playPile.remove(1);
                    }
                } else
                {
                    //System.out.println("Error: no cards remaining in draw pile or extra is play pile");
                }
            }
            return card;
        } else
        {
            return new Card(-1, -1, -1, -1);
        }
    }

    public void play(Card card)
    {
        if (isLight)
        {
            playPile.add(card);
        } else
        {
            playPile.add(0, card);
        }

    }

    public boolean getIsLight()
    {
        return isLight;
    }

    public Card getTopCard()
    {
        if (isLight)
        {
            return playPile.get(playPile.size() - 1);
        } else
        {
            return playPile.get(0);
        }
    }

    public int getSmallestHandSize()
    {
        return smallestHandSize;
    }

    public void setSmallestHandSize(int size)
    {
        smallestHandSize = size;
    }

    public void flip()
    {
        isLight = !isLight;
    }

    public void reverse()
    {
        isClockDir = !isClockDir;
    }

    public boolean isClockDir()
    {
        return isClockDir;
    }

    public void printTopCard()
    {
        System.out.println("The top card is a " + getTopCard().getName(isLight));
    }

}
