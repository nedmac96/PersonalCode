/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monopoly;

/**
 *
 * @author Camden
 */
public class Place
{

    private int number;
    private int startRent;
    private String group;
    private int level;

    public Place(int number, int startRent, String group, int level)
    {
        this.number = number;
        this.startRent = startRent;
        this.group = group;
        this.level = level;
    }

    public void setLevel(int level)
    {
        this.level = level;
    }

    public int getNumber()
    {
        return number;
    }

    public int getStartRent()
    {
        return startRent;
    }

    public String getGroup()
    {
        return group;
    }

    public int getLevel()
    {
        return level;
    }

    public int calculateRent()
    {
        switch (level)
        {
            case 1:
                return startRent;
            case 2:
                return startRent * 2;
            case 3:
                return startRent * 5;
            case 4:
                return startRent * 15;
            case 5:
                return startRent * 45;
            case 6:
                return startRent * 80;
            case 7:
                return startRent * 125;
            default:
                return 0;
        }
    }
}
