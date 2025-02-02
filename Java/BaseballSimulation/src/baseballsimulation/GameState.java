/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package baseballsimulation;

import java.util.ArrayList;

/**
 *
 * @author Camden
 */
public class GameState
{

    private InningState state;
    private int runDiff;
    private int inning;
    private boolean isTop;
    private double evaluation;

    public GameState(InningState state, int runDiff, int inning, boolean isTop)
    {
        this.state = state;
        this.runDiff = runDiff;
        this.inning = inning;
        this.isTop = isTop;
        this.evaluation = -2;
    }

    public double getEvaluation(ArrayList<GameState> positions, ArrayList<Player> homeTeam, ArrayList<Player> awayTeam, int homeBatterId, int awayBatterId)
    {
        final double SINGLE_RUNNER_2B_PROB = 0.6;
        final double DOUBLE_RUNNER_3B_PROB = 0.5;
        final double GROUND_OUT_GIDP_PROB = 0.556;
        final double FLY_OUT_RUNNER_ADVANCE_PROB = 0.5;
        final double STOLEN_BASE_PROB = 0.79;
        if (evaluation != -2)
        {
            return evaluation;
        }
        if (inning == 9 && !isTop && runDiff > 0)
        {
            return 1;
        }
        if (state.getId() == 24)
        {
            if (inning == 9 && !isTop && runDiff < 0)
            {
                return 0;
            } else
            {

            }
        }
        return 0.0;
    }

}
