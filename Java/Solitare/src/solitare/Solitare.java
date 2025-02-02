/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package solitare;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 *
 * @author Camden
 */
public class Solitare
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        // TODO code application logic here
        int code = 1211420;
        System.out.println(String.valueOf(code));
//Solitare game = new Solitare();
        //game.runGame();
    }

    public void runGame()
    {
        boolean threeCards = false;
        int maxDeals = 1;
        int wins = 0;
        for (int i = 0; i < maxDeals; i++)
        {
            //Create File
            try
            {
                File myObj = new File("/tmp/solitareGame.ser");
                if (myObj.createNewFile())
                {
                    System.out.println("File created: " + myObj.getName());
                } else
                {
                    System.out.println("File already exists.");
                }
            } catch (IOException e)
            {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }

            ArrayList<Card> deck = createDeck();
            ArrayList<Pile> stacks = dealPiles(deck);
            ArrayList<Card> drawPile = new ArrayList<>();
            int deckSize = deck.size();
            for (int j = 0; j < deckSize; j++)
            {
                drawPile.add(deck.get(0));
                deck.remove(0);
            }
            int[] goalPiles =
            {
                0, 0, 0, 0
            };
            int drawPilePosition = 0;
            ArrayList<ArrayList<Card>> board;
            //Delete File
            File myObj = new File("/tmp/solitareGame.ser");
            if (myObj.delete())
            {
                System.out.println("Deleted the file: " + myObj.getName());
            } else
            {
                System.out.println("Failed to delete the file.");
            }
        }
    }

    public ArrayList<Pile> dealPiles(ArrayList<Card> deck)
    {
        ArrayList<Pile> stacks = new ArrayList<>();
        for (int j = 0; j < 7; j++)
        {
            stacks.add(new Pile(deck, j));
            for (int k = 0; k < j + 1; k++)
            {
                deck.remove(0);
            }
        }
        return stacks;
    }

    public ArrayList<Card> createDeck()
    {
        ArrayList<Card> deck = new ArrayList<>();
        for (int i = 0; i < 52; i++)
        {
            while (deck.size() <= i)
            {
                int randomNum = (int) Math.floor(Math.random() * 52) + 1;
                Card cardToTest = new Card(randomNum);
                if (!deck.contains(cardToTest))
                {
                    deck.add(cardToTest);
                }
            }
        }
        return deck;
    }

    public void addBoardToFile(ArrayList<Pile> stacks, ArrayList<Card> drawPile, int[] goalPiles, int drawPilePosition)
    {
        try
        {
            FileOutputStream fileOut = new FileOutputStream("/tmp/solitareGame.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(stacks);
            out.writeObject(drawPile);
            out.writeObject(goalPiles);
            out.writeObject(drawPilePosition);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in /tmp/solitareGame.ser");
        } catch (IOException i)
        {
            i.printStackTrace();
        }
    }

    public boolean isNewPosition(ArrayList<Pile> currentStacks, ArrayList<Card> currentDrawPile, int[] currentGoalPiles, int currentDrawPilePosition)
    {
        ArrayList<Pile> stacks = null;
        ArrayList<Card> drawPile = null;
        int[] goalPiles =
        {
            0, 0, 0, 0
        };
        int drawPilePosition = 0;
        try
        {
            FileInputStream fileIn = new FileInputStream("/tmp/solitareGame.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            stacks = (ArrayList<Pile>) in.readObject();
            drawPile = (ArrayList<Card>) in.readObject();
            goalPiles = (int[]) in.readObject();
            drawPilePosition = (int) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i)
        {
            i.printStackTrace();
            return false;
        } catch (ClassNotFoundException c)
        {
            System.out.println("Class not found");
            c.printStackTrace();
            return false;
        }
        return false;
    }

}
