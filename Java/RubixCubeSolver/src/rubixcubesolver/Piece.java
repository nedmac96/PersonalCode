/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rubixcubesolver;

import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author Camden
 */
public class Piece
{

    int side1;
    int side2;
    int side3;

    public Piece(int side1, int side2, int side3)
    {
        if (side1 <= side2)
        {
            if (side1 <= side3)
            {
                this.side1 = side1;
                if (side2 <= side3)
                {
                    this.side2 = side2;
                    this.side3 = side3;
                } else
                {
                    this.side2 = side3;
                    this.side3 = side2;
                }
            } else
            {
                this.side2 = side1;
                this.side3 = side2;
                this.side1 = side3;
            }
        } else
        {
            if (side2 <= side3)
            {
                this.side1 = side2;
                if (side1 <= side3)
                {
                    this.side2 = side1;
                    this.side3 = side3;
                } else
                {
                    this.side2 = side3;
                    this.side3 = side1;
                }
            } else
            {
                this.side3 = side1;
                this.side2 = side2;
                this.side1 = side3;
            }
        }
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 37 * hash + this.side1;
        hash = 37 * hash + this.side2;
        hash = 37 * hash + this.side3;
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final Piece other = (Piece) obj;
        if (this.side1 != other.side1)
        {
            return false;
        }
        if (this.side2 != other.side2)
        {
            return false;
        }
        return this.side3 == other.side3;
    }

    @Override
    public String toString()
    {
        return "Piece{" + "side1=" + side1 + ", side2=" + side2 + ", side3=" + side3 + '}';
    }
}
