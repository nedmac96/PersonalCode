package marioParty;

import java.util.ArrayList;

public class Player {
    private final int strategy;
    private ArrayList<Integer> dice;
    private int score;

    public Player(int score, int strategy) {
        this.strategy = strategy;
        this.score = score;
        dice = new ArrayList<>();
    }

    public void addDie(int die) {
        if (dice.size() == 2) {
            dice.set(0, die);
        } else {
            dice.add(die);
        }
    }
    /*
    0 - 1-6
    1 - 1-3
    2 - 4-6
    3 - 0-1
    4 - 1-6 slow
    5 - 2 * 1-6
    6 - 1-3 slow
     */
    public void chooseDie() {
        int die = (int) (Math.random() * 5);
        addDie(die);
    }

    public int roll(Tile curTile) {
        return (int) (Math.random() * 6 + 1);
    }

    public int getScore() {
        return score;
    }

    public void lucky() {
        score += ((int) (Math.random() * 3 + 1)) * 2;
    }

    public void adjustScore(int change) {
        score += change;
        if (score < 0) {
            score = 0;
        }
    }

    public int game() {
        return (int) (Math.random() * 10) + 1;
    }
}
