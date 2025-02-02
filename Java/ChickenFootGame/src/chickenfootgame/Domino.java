/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chickenfootgame;

/**
 *
 * @author Camden
 */
public class Domino
{

    private final int num1;
    private final int num2;

    public Domino(int num1, int num2)
    {
        this.num1 = num1;
        this.num2 = num2;
    }

    public int getNum1()
    {
        return num1;
    }

    public int getNum2()
    {
        return num2;
    }

    public boolean has(int num)
    {
        return (num == num1 || num == num2);
    }

    public int getScore()
    {
        if (num1 == 0 && num2 == 0)
        {
            return 50;
        } else
        {
            return num1 + num2;
        }
    }

    public boolean isDouble()
    {
        return (num1 == num2);
    }
}
