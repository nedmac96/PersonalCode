package marioParty;

import java.util.ArrayList;

public class Tile {
    private final int type;
    private final int points;
    private final String event;
    private ArrayList<Tile> nextTiles = new ArrayList<>();

    public Tile(int type) {
        this.type = type;
        this.points = 0;
        this.event = null;
    }

    public Tile(int type, int points) {
        this.type = type;
        this.points = points;
        this.event = null;
    }

    public Tile(int type, String event) {
        this.type = type;
        this.points = 0;
        this.event = event;
    }

    public void setNextTiles(ArrayList<Tile> nextTiles) {
        this.nextTiles = nextTiles;
    }

    public Tile getNextTile(Player player, String pathChoice) {
        if (nextTiles.size() == 1) {
            return nextTiles.getFirst();
        } else if (nextTiles.isEmpty()) {
            return null;
        } else {
            if (pathChoice.equals("random")) {
                return nextTiles.get((int) (Math.random() * nextTiles.size()));
            }
            System.out.println("Non-random path choice not supported");
            return nextTiles.get((int) (Math.random() * nextTiles.size()));
        }
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
    public String land(Player player) {
        String retval = null;
        switch (type) {
            case 0 -> {
            }
            case 1 -> player.chooseDie();
            case 2 -> {
                retval = "minigame";
            }
            case 3-> {
                player.lucky();
            }
            case 4-> {
                player.adjustScore(-5);
            }
            case 5 -> {
                retval = "forward";
            }
            case 6 -> {
                retval = "reverse";
            }
            case 7 -> {
                retval = "steal";
            }
            default -> System.out.println("Type " + type + " of tile not supported");
        }

        enter(player);
        return retval;
    }

    public void enter(Player player) {
        player.adjustScore(points);
    }

    @Override
    public String toString() {
        return "" + type;
    }
}
