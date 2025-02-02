/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package marioParty;

/**
 *
 * @author Camden
 */
public class MarioPartyGame
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        MarioPartyGame game = new MarioPartyGame();
        game.run();
    }

    public void run() {
        NormalGame game = new NormalGame(0, new int[]{0, 0, 0, 0});
        while (!game.isFinished()) {
            System.out.println(game);
            game.playTurn();
        }
        System.out.println(game);
        System.out.println(game.getWinner());
    }
}
