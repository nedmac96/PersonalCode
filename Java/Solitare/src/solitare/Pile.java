/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package solitare;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Camden
 */
public class Pile implements Serializable
{

    private int hiddenCardsNum;
    private ArrayList<Card> cards;

    public Pile(ArrayList<Card> deck, int hiddenCardsNum)
    {
        this.hiddenCardsNum = hiddenCardsNum;
        this.cards = new ArrayList<>();
        for (int i = 0; i < hiddenCardsNum + 1; i++)
        {
            cards.add(deck.get(i));
        }
    }

    public int getHiddenCardsNum()
    {
        return hiddenCardsNum;
    }

    public ArrayList<Card> getCards()
    {
        return cards;
    }

    public void revealCard()
    {
        this.hiddenCardsNum--;
    }

    public void addCard(Card card)
    {
        this.cards.add(card);
    }

}
