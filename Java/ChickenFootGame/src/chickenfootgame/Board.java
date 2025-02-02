/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chickenfootgame;

import java.util.ArrayList;

/**
 *
 * @author Camden
 */
public class Board
{

    private ArrayList<Domino> drawPile;
    private ArrayList<Domino> dominosPlayed;
    private ArrayList<Integer> options;
    private int mustPlayType;
    private int mustPlayRemaining;
    private int smallestHandSize;

    public Board()
    {
        this.drawPile = new ArrayList<>();
        this.dominosPlayed = new ArrayList<>();
        this.options = new ArrayList<>();
    }

    public void setUpRound(int highestDomino)
    {
        drawPile.clear();
        dominosPlayed.clear();
        options.clear();

        for (int i = 0; i <= highestDomino; i++)
        {
            for (int j = 0; j <= i; j++)
            {
                drawPile.add(new Domino(i, j));
            }
        }

        mustPlayType = -1;
    }

    public Domino drawDomino()
    {
        if (!drawPile.isEmpty())
        {
            int randNum = (int) (Math.random() * drawPile.size());
            Domino domino = drawPile.get(randNum);
            drawPile.remove(randNum);
            return domino;
        } else
        {
            return new Domino(-1, -1);
        }
    }

    public void play(Domino domino)
    {
        //System.out.println(domino.getNum1() + ", " + domino.getNum2() + " was played");
        dominosPlayed.add(domino);
        if (mustPlayType == -1)
        {
            mustPlayType = domino.getNum1();
            mustPlayRemaining = 6;
        } else
        {
            if (mustPlayRemaining > 0)
            {
                mustPlayRemaining--;
                if (domino.getNum1() == mustPlayType)
                {
                    options.add(domino.getNum2());
                } else
                {
                    options.add(domino.getNum1());
                }
            } else
            {
                System.out.println("ERROR: Must choose a direction");
            }
        }
    }

    public void play(Domino domino, int playOn)
    {
        //System.out.println(domino.getNum1() + ", " + domino.getNum2() + " was played on a " + playOn);
        dominosPlayed.add(domino);
        for (int i = 0; i < options.size(); i++)
        {
            if (options.get(i) == playOn)
            {
                options.remove(i);
            }
        }
        if (domino.getNum1() == playOn)
        {
            if (domino.getNum1() == domino.getNum2())
            {
                mustPlayRemaining = 3;
                mustPlayType = playOn;
            } else
            {
                options.add(domino.getNum2());
            }
        } else
        {
            options.add(domino.getNum1());
        }
    }

    public ArrayList<Integer> getOptions()
    {
        return options;
    }

    public ArrayList<Domino> getDominosPlayed()
    {
        return dominosPlayed;
    }

    public int getMustPlayType()
    {
        return mustPlayType;
    }

    public int getMustPlayRemaining()
    {
        return mustPlayRemaining;
    }

    public boolean canDraw()
    {
        return !drawPile.isEmpty();
    }

    public void printOptions()
    {
        System.out.print("You can play on: ");
        for (int i = 0; i < options.size(); i++)
        {
            System.out.print(options.get(i));
            if (i < options.size() - 1)
            {
                System.out.print(", ");
            }
        }
        System.out.println("");
    }

    public int getSmallestHandSize()
    {
        return smallestHandSize;
    }

    public void setSmallestHandSize(int size)
    {
        smallestHandSize = size;
    }

}
