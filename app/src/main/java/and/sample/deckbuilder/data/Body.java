package and.sample.deckbuilder.data;

import android.content.Context;

import java.io.Serializable;

import and.sample.deckbuilder.R;

public class Body implements Serializable {
    String name;

    int size;
    int strength;
    int health;

    int delay = 0;
    int defense = 0;


    public String toString() {
        String base = "[" + name + "]" +
                "  규모:" + size +
                "  근력:" + strength +
                "  체력:" + health;

        String op1 = "", op2 = "";
        if (delay > 0) {
            op1 = "  딜레이: +" + delay;
        } else if (delay < 0) {
            op1 = "  딜레이: " + delay;
        }

        if (defense > 0) {
            op2 = "  방어: +" + defense + "%";
        } else if (defense < 0) {
            op2 = "  방어: " + defense + "%";
        }


        return base + op1 + op2;
    }


    public Body(Context context, int num, String name) {

        switch (num) {
            case 1:
                name = context.getString(R.string.body_01_name);
                break;
            case 2:
                name = context.getString(R.string.body_02_name);
                break;
            case 3:
                name = context.getString(R.string.body_03_name);
                break;
        }

        if (name.equals(context.getString(R.string.body_01_name))) {
            this.name = name;
            size = context.getResources().getInteger(R.integer.body_01_size);
            strength = context.getResources().getInteger(R.integer.body_01_strength);
            health = context.getResources().getInteger(R.integer.body_01_health);
            delay = context.getResources().getInteger(R.integer.body_01_delay);
        } else if (name.equals(context.getString(R.string.body_02_name))) {
            this.name = name;
            size = context.getResources().getInteger(R.integer.body_02_size);
            strength = context.getResources().getInteger(R.integer.body_02_strength);
            health = context.getResources().getInteger(R.integer.body_02_health);
            defense = context.getResources().getInteger(R.integer.body_02_defense);
        } else if (name.equals(context.getString(R.string.body_03_name))) {
            this.name = name;
            size = context.getResources().getInteger(R.integer.body_03_size);
            strength = context.getResources().getInteger(R.integer.body_03_strength);
            health = context.getResources().getInteger(R.integer.body_03_health);
        }



    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public int getStrength() {
        return strength;
    }

    public int getHealth() {
        return health;
    }

    public int getDelay() {
        return delay;
    }

    public int getDefense() {
        return defense;
    }
}
