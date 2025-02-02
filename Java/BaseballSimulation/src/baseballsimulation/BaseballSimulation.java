/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package baseballsimulation;

import java.util.ArrayList;

/**
 *
 * @author Camden
 */
public class BaseballSimulation
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        // TODO code application logic here
        BaseballSimulation simulation = new BaseballSimulation();
        simulation.run();
    }

    public void run()
    {
        ArrayList<InningState> inningStates = new ArrayList<>();
        for (int i = 0; i < 25; i++)
        {
            inningStates.add(new InningState(i));
        }
        ArrayList<GameState> gameStates = new ArrayList<>();
        for (int i = 0; i < 25; i++)
        {
            inningStates.add(new InningState(i));
        }
    }

}
