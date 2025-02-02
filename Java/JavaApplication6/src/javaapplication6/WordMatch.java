/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication6;

/**
 *
 * @author Camden
 */
public class WordMatch
{

    private String secret;

    public WordMatch(String word)
    {
        secret = word;
    }

    public int scoreGuess(String guess)
    {
        int score = 0;
        for (int i = 0; i < secret.length() - guess.length() + 1; i++)
        {
            if (secret.substring(i, i + guess.length()).equals(guess))
            {
                score = score + guess.length() * guess.length();
            }
        }
        return score;
    }

    public String findBetterGuess(String guess1, String guess2)
    {
        if (scoreGuess(guess1) > scoreGuess(guess2))
        {
            return guess1;
        } else if (scoreGuess(guess1) < scoreGuess(guess2))
        {
            return guess2;
        } else
        {
            if (guess1.compareTo(guess2) > 1)
            {
                return guess1;
            } else
            {
                return guess2;
            }
        }

    }
}
