/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package solitare;

import java.io.Serializable;

/**
 *
 * @author Camden
 */
public class Card implements Serializable
{

    private final int id;
    private final int number;
    private final int suit;
    private final boolean isBlack;

    public Card(int id)
    {
        this.id = id;
        this.number = id % 13;
        this.suit = id / 13;
        this.isBlack = id >= 26;

    }

    public int getId()
    {
        return id;
    }

    public int getNumber()
    {
        return number;
    }

    public int getSuit()
    {
        return suit;
    }

    public boolean isBlack()
    {
        return isBlack;
    }

}
