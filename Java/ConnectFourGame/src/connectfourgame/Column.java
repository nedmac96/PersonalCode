/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package connectfourgame;

/**
 *
 * @author Camden
 */
public class Column
{

    private Slot[] slots = new Slot[6];

    public Column()
    {
        for (int i = 0; i < slots.length; i++)
        {
            slots[i] = new Slot();
        }
    }

    public void addToken(int color)
    {
        boolean tokenPlaced = false;
        int i = 0;
        while (!tokenPlaced)
        {
            if (slots[i].getStatus() == 0)
            {
                slots[i].fillSlot(color);
                tokenPlaced = true;
            }
            i++;
        }
    }

    public void removeToken()
    {
        boolean tokenRemoved = false;
        int i = 5;
        while (!tokenRemoved)
        {
            if (slots[i].getStatus() != 0)
            {
                slots[i].fillSlot(0);
                tokenRemoved = true;
            }
            i--;
        }
    }

    public int getSlot(int slot)
    {
        return slots[slot].getStatus();
    }

    public boolean isNotFull()
    {
        return slots[5].getStatus() == 0;
    }
}
