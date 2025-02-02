package marioParty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class NormalGame {
    private final ArrayList<Player> players;
    private int turn;
    private final ArrayList<Tile> board;
    private final LinkedList<Integer> startingTiles;
    private Tile curTile;
    private String pathChoice;
    private boolean finished;


    public boolean isFinished() {
        return finished;
    }

    public NormalGame(int mapNum, int[] strategies) {
        players = new ArrayList<>();
        for (int strategy: strategies) {
            players.add(new Player(5, strategy));
        }
        turn = (int) (Math.random() * 4);

        board = new ArrayList<>();
        startingTiles = new LinkedList<>();
        if (mapNum == 0) {
            pathChoice = "choose";
            startingTiles.add(0);
            board.add(new Tile(0));
            board.add(new Tile(0));
            board.add(new Tile(0));
            board.add(new Tile(0, 3));
            board.add(new Tile(0));
            board.add(new Tile(2));
            board.add(new Tile(0));
            board.add(new Tile(0, 5));
            board.add(new Tile(3));
            board.add(new Tile(2));
            board.add(new Tile(0));
            board.add(new Tile(0));
            board.add(new Tile(3));
            board.add(new Tile(0, 5));
            board.add(new Tile(0));

            // Left
            board.add(new Tile(3));
            board.add(new Tile(3));
            board.add(new Tile(1));
            board.add(new Tile(2));
            board.add(new Tile(0, -5));
            board.add(new Tile(1));

            // Right
            board.add(new Tile(0));
            board.add(new Tile(3));
            board.add(new Tile(1));
            board.add(new Tile(2, 5));
            board.add(new Tile(1));
            board.add(new Tile(6));

            board.add(new Tile(0));
            board.add(new Tile(10, 5));
            startingTiles.add(board.size());

            board.add(new Tile(0));
            board.add(new Tile(1));
            board.add(new Tile(4, "toadHouse"));
            board.add(new Tile(3));
            board.add(new Tile(1));
            board.add(new Tile(0));
            board.add(new Tile(0, 5));
            board.add(new Tile(2));
            board.add(new Tile(8));
            board.add(new Tile(3));
            board.add(new Tile(2));
            board.add(new Tile(0));
            board.add(new Tile(3));
            board.add(new Tile(1));
            board.add(new Tile(1));
            board.add(new Tile(11));
            board.add(new Tile(1, "carousel"));
            board.add(new Tile(7));
            board.add(new Tile(1, "homestretch"));
            board.add(new Tile(2));
            board.add(new Tile(0));
            board.add(new Tile(1, 5));
            board.add(new Tile(1));
            board.add(new Tile(4));
            board.add(new Tile(0));
            board.add(new Tile(2));
            board.add(new Tile(1));
            board.add(new Tile(1, -8));
            board.add(new Tile(6));
            board.add(new Tile(0));
            board.add(new Tile(2));
            board.add(new Tile(0));
            board.add(new Tile(4, 10));
            board.add(new Tile(6));
            board.add(new Tile(0));
            board.add(new Tile(11));
            board.add(new Tile(0));
            board.add(new Tile(12, 10));

            for (int i = 0; i < board.size() - 1; i++) {
                if (i == 14) {
                    addLinks(board.get(i), new int[]{15, 16});
                } else if (i == 20) {
                    addLinks(board.get(i), new int[]{27});
                } else {
                    addLinks(board.get(i), new int[]{i + 1});
                }
            }
        }
        curTile = board.get(startingTiles.remove());
        this.finished = false;
    }
    /*
    0 - green
    1 - blue
    2 - vs
    3 - lucky
    4 - unlucky
    5 - forward
    6 - reverse
    7 - steal
    8 - bowser junior
    9 - bowser
    10 - half-end
    11 - switch
    12 - end
     */

    private void addLinks(Tile tile, int[] links) {
        ArrayList<Tile> nextTiles = new ArrayList<>();
        for (int link: links) {
            nextTiles.add(board.get(link));
        }
        tile.setNextTiles(nextTiles);
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
            if (curTile != null) {
                runEvent(curTile.land(player));
            }
        }
        if (curTile == null) {
            if (!startingTiles.isEmpty()) {
                curTile = board.get(startingTiles.remove());
            } else {
                this.finished = true;
                giveAwards();
            }
        }
        turn += 1;
        if (turn == 4) {
            turn = 0;
        }
    }

    private void runEvent(String event) {
        switch (event) {
            case null -> {

            }
            case "minigame" -> {
                ArrayList<Player> order = new ArrayList<>(players);
                Collections.shuffle(order);
                order.sort(Comparator.comparing(Player::game).reversed());
                order.get(0).adjustScore(10);
                order.get(1).adjustScore(5);
                order.get(2).adjustScore(3);
                order.get(3).adjustScore(1);
            }
            case "miniboss" -> {
                ArrayList<Player> order = new ArrayList<>(players);
                Collections.shuffle(order);
                order.sort(Comparator.comparing(Player::game).reversed());
                order.get(0).adjustScore(10);
                order.get(1).adjustScore(5);
                order.get(2).adjustScore(3);
                order.get(3).adjustScore(1);
            }
            case "boss" -> {
                ArrayList<Player> order = new ArrayList<>(players);
                Collections.shuffle(order);
                order.sort(Comparator.comparing(Player::game).reversed());
                order.get(0).adjustScore(15);
                order.get(1).adjustScore(7);
                order.get(2).adjustScore(3);
                order.get(3).adjustScore(1);
            }
            case "forward" -> {
                Player player = players.get(turn);
                int movement = (int) (Math.random() * 6 + 1);
                while (movement > 0) {
                    curTile = curTile.getNextTile(player, pathChoice);
                    if (curTile == null) {
                        break;
                    }
                    curTile.enter(player);
                    movement--;
                }
            }
            default -> {
            }
        }
    }

    private void giveAwards() {

    }

    public int getWinner() {
        Player winner = players.getFirst();
        for (Player player: players) {
            if (player != winner) {
                if (player.getScore() > winner.getScore()) {
                    winner = player;
                } else if (player.getScore() == winner.getScore()) {
                    System.out.println("Tie");
                }
            }
        }
        return players.indexOf(winner);
    }

    @Override
    public String toString() {
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            text.append(i).append(":").append(players.get(i).getScore()).append(", ");
        }
        text.append("Cart Position: ").append(board.indexOf(curTile)).append("-").append(curTile).append(", Turn: ").append(turn);
        return text.toString();
    }
}
