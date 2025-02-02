/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package connectfourgame;

/**
 *
 * @author Camden
 */
public class Slot
{

    private int status = 0;

    public void fillSlot(int color)
    {
        status = color;
    }

    public int getStatus()
    {
        return status;
    }
}
