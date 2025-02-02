/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package numbersearch;

/**
 *
 * @author Camden
 */
public class NumberSearch
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        // TODO code application logic here
        NumberSearch search = new NumberSearch();
        search.runSearch();
    }

    public void runSearch()
    {
        int length = 20;
        for (int i = 0; i < length; i++)
        {
            System.out.println(Math.round(Math.random()));
        }
    }

}
