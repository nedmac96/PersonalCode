/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warcardgame;

import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Camden
 */
public class Players
{

    private String playerName;
    private boolean AI = false;
    public Queue<CardDeck> playerCards = new LinkedList<>();
    private CardDeck cardPlayed;

    public CardDeck getCardPlayed()
    {
        return cardPlayed;
    }

    public void setCardPlayed(CardDeck cardPlayed)
    {
        this.cardPlayed = cardPlayed;
    }

    public Queue<CardDeck> getPlayerCards()
    {
        return playerCards;
    }

    public String getPlayerName()
    {
        return playerName;
    }

    public void setPlayerName(String playerName)
    {
        this.playerName = playerName;
    }

    public boolean isAI()
    {
        return AI;
    }

    public void setAI(boolean AI)
    {
        this.AI = AI;
    }

}
