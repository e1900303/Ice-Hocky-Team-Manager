package sourcecode;

import java.io.Serializable;

public class Date implements Serializable {

    private static final long serialVersionUID = 1L;


    private int day, month, year;


    public Date(String string) {

        String[] parts = string.split("\\.");
        day = Integer.parseInt(parts[0]);
        month = Integer.parseInt(parts[1]);
        year = Integer.parseInt(parts[2]);

    }

    @Override
    public String toString() {

        return day + "." + month + "." + year;

    }


    public boolean equals(Date object) {

        if (this.day == object.day && this.month == object.month && this.year == object.year) {

            return true;

        }
        else {

            return false;

        }
    }

}