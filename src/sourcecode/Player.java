package sourcecode;

import java.io.Serializable;

public class Player implements Serializable {

    private static final long serialVersionUID = 1L;
    private String name;
    private int point;

    public Player(String name, int point){

        this.name = name;
        this.point = point;

    }
    public String toString(){

        return  name + " : " + point + "\n";

    }
    public String getName() {

        return name;

    }

    public int getPoint() {

        return point;

    }
}
