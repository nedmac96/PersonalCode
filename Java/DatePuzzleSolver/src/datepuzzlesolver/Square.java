/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datepuzzlesolver;

/**
 *
 * @author Camden
 */
public class Square
{

    private int id;
    private String name;
    private final String defaultStatus;
    private String currentStatus;
    private Piece piece;

    public Square(int id)
    {
        this.id = id;
        if (id < 14)
        {
            switch (id)
            {
                case 0:
                    name = "Jan";
                    break;
                case 1:
                    name = "Feb";
                    break;
                case 2:
                    name = "Mar";
                    break;
                case 3:
                    name = "Apr";
                    break;
                case 4:
                    name = "May";
                    break;
                case 5:
                    name = "Jun";
                    break;
                case 7:
                    name = "Jul";
                    break;
                case 8:
                    name = "Aug";
                    break;
                case 9:
                    name = "Sep";
                    break;
                case 10:
                    name = "Oct";
                    break;
                case 11:
                    name = "Nov";
                    break;
                case 12:
                    name = "Dec";
                    break;
                default:
                    name = "x";
                    break;
            }

        } else
        {
            if (id < 42)
            {
                name = "" + (id - 13);
            } else if (id > 43 && id < 47)
            {
                name = "" + (id - 15);
            } else
            {
                name = "x";
            }
        }
        piece = null;
        if (name.equals("x"))
        {
            defaultStatus = "blocked";
            piece = new Piece(9);
        } else
        {
            defaultStatus = "empty";
        }
        currentStatus = defaultStatus;

    }

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public Piece getPiece()
    {
        return piece;
    }

    public String getDefaultStatus()
    {
        return defaultStatus;
    }

    public String getCurrentStatus()
    {
        return currentStatus;
    }

    public void fillSquare(Piece piece)
    {
        this.piece = piece;
        currentStatus = "blocked";
    }

    public void emptySquare()
    {
        currentStatus = defaultStatus;
    }
}
