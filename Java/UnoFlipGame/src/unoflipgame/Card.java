/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package unoflipgame;

/**
 *
 * @author Camden
 */
public class Card
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
    1 is red or pink
    2 is green or teal
    3 is blue or purple
    4 is yellow or orange
     */
    private int lightNumber;
    private int darkNumber;
    private int lightColor;
    private int darkColor;

    public Card(int lightNumber, int lightColor, int darkNumber, int darkColor)
    {
        this.lightNumber = lightNumber;
        this.darkNumber = darkNumber;
        this.lightColor = lightColor;
        this.darkColor = darkColor;
    }

    public int getColor(boolean isLight)
    {
        if (isLight)
        {
            return lightColor;
        } else
        {
            return darkColor;
        }
    }

    public int getNumber(boolean isLight)
    {
        if (isLight)
        {
            return lightNumber;
        } else
        {
            return darkNumber;
        }
    }

    public int getScore(boolean isLight)
    {
        if (isLight)
        {
            if (lightNumber < 10)
            {
                return lightNumber;
            } else if (lightNumber == 10)
            {
                return 10;
            } else if (lightNumber == 11)
            {
                return 20;
            } else if (lightNumber == 12)
            {
                return 20;
            } else if (lightNumber == 13)
            {
                return 20;
            } else if (lightNumber == 14)
            {
                return 40;
            } else if (lightNumber == 15)
            {
                return 50;
            } else
            {
                System.out.println("Invalid card");
                return 0;
            }
        } else
        {
            if (darkNumber < 10)
            {
                return darkNumber;
            } else if (darkNumber == 10)
            {
                return 20;
            } else if (darkNumber == 11)
            {
                return 20;
            } else if (darkNumber == 12)
            {
                return 30;
            } else if (darkNumber == 13)
            {
                return 20;
            } else if (darkNumber == 14)
            {
                return 40;
            } else if (darkNumber == 15)
            {
                return 60;
            } else
            {
                System.out.println("Invalid card");
                return 0;
            }
        }
    }

    public boolean canPlay(Card card, boolean isLight)
    {
        return lightNumber == 14 || lightNumber == 15 || lightColor == card.getColor(isLight) || lightNumber == card.getNumber(isLight);
    }

    public void printCard()
    {
        System.out.print("Light Side: " + getName(true));
        System.out.println("    Dark Side: " + getName(false));
    }

    public String getName(boolean isLight)
    {
        String color = "";
        String number;
        if (isLight)
        {
            switch (lightColor)
            {
                case 0 ->
                    color = "Red    ";
                case 1 ->
                    color = "Green  ";
                case 2 ->
                    color = "Blue   ";
                case 3 ->
                    color = "Yellow ";
                default ->
                {
                }
            }
            number = switch (lightNumber)
            {
                case 10 ->
                    "+1     ";
                case 11 ->
                    "Reverse";
                case 12 ->
                    "Skip   ";
                case 13 ->
                    "Flip   ";
                case 14 ->
                    "Wild          ";
                case 15 ->
                    "Wild   +2     ";
                default ->
                    lightNumber + "      ";
            };
        } else
        {
            switch (darkColor)
            {
                case 0 ->
                    color = "Pink   ";
                case 1 ->
                    color = "Teal   ";
                case 2 ->
                    color = "Purple ";
                case 3 ->
                    color = "Orange ";
                default ->
                {
                }
            }
            number = switch (darkNumber)
            {
                case 10 ->
                    "+5";
                case 11 ->
                    "Reverse";
                case 12 ->
                    "Skip All";
                case 13 ->
                    "Flip";
                case 14 ->
                    "Wild";
                case 15 ->
                    "Wild Draw Color";
                default ->
                    "" + darkNumber;
            };
        }
        return color + number;
    }

    public void reset()
    {
        if (lightNumber > 13)
        {
            lightColor = 4;
        }
        if (darkNumber > 13)
        {
            darkColor = 4;
        }
    }

    public void setColor(int color, boolean isLight)
    {
        if (isLight)
        {
            lightColor = color;
        } else
        {
            darkColor = color;
        }
    }

}
