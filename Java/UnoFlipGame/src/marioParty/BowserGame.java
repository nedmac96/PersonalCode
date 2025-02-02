package marioParty;

import java.util.ArrayList;
import java.util.LinkedList;

public class BowserGame {
    private ArrayList<Player> players;
    private int turn;
    private ArrayList<Tile> board;
    private int cartPosition;
    private int bowserPosition;
    private LinkedList<Integer> startingTiles;
    private Tile curTile;
    private String bowserTile;
    private String pathChoice;
    private boolean finished;


    public BowserGame(int mapNum, int[] strategies) {
        players = new ArrayList<>();
        for (int strategy: strategies) {
            players.add(new Player(6, strategy));
        }
        turn = 0;

        board = new ArrayList<>();
        if (mapNum == 0) {
            for (int i = 0; i < 100; i++) {
                board.add(new Tile(0));
            }
        }
        cartPosition = 0;
        bowserPosition = 0;
    }

    public void playTurn() {
        Player player = players.get(turn);
        int movement = player.roll(curTile);
        while (movement > 1) {
            curTile = curTile.getNextTile(player, pathChoice);
            if (curTile == null) {
                break;
            }
            curTile.enter(player);
            movement--;
        }
        if (movement == 1 && curTile != null) {
            curTile = curTile.getNextTile(player, pathChoice);
            curTile.land(player);
        }
        if (curTile == null) {
            if (!startingTiles.isEmpty()) {
                curTile = board.get(startingTiles.remove());
            } else {
                this.finished = true;
                System.out.println("finish not supported for bowser");
            }
        }
        turn += 1;
        if (turn == 5) {
            turn = 0;
        }
    }

    private void applyTile(int turn, int position) {
        if (turn == 4) {
            if (bowserPosition == cartPosition) {
                bowserMiniGame();
            }
            return;
        } else {
            Tile tile = board.get(position);

        }
    }

    private void bowserMiniGame() {
        System.out.println("Bowser mini game not supported");
    }

    @Override
    public String toString() {
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            text.append(i).append(":").append(players.get(i).getScore()).append(", ");
        }
        text.append("Cart Position: ").append(cartPosition).append("-").append(board.get(cartPosition)).append(", BowserPosition: ").append(bowserPosition).append(", Turn: ").append(turn);
        return text.toString();
    }
}
