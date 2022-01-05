package sourcecode;

import java.io.Serializable;
import java.util.ArrayList;

public class Match implements Serializable {

    private static final long serialVersionUID = 1L;

    private String opponent, result;
    private Date date;
    private ArrayList<Player> players = new ArrayList<>();


    public Date getDate() {

        return date;

    }


    public ArrayList<Player> getPlayers() {

        return players;

    }

    public Match(Date date, String opponent, String result) {

        this.date = date;
        this.opponent = opponent;
        this.result = result;

    }

    public void addPlayer(Player player) {

        this.players.add(player);

    }


    @Override
    public String toString() {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Date:\t" + this.date + "\n");
        stringBuilder.append("Opponent:\t" + this.opponent + "\n");
        stringBuilder.append("Final result:\t" + this.result + "\n");
        stringBuilder.append("Player & Point\n");
        for (Player p : this.players) {

            stringBuilder.append(p.toString() + "\n");

        }

        return stringBuilder.toString();

    }
}