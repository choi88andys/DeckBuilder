package and.sample.deckbuilder.data;

import android.content.Context;

import java.io.Serializable;

import and.sample.deckbuilder.R;

public class Armor implements Serializable {
    String name;

    int size;
    int weight;
    int health;
    int defense;

    public String toString() {
        String temp;

        temp = "[" + name + "]" +
                "  규모:" + size +
                "  무게:" + weight +
                "  체력:" + health +
                "  방어:" + defense + "%";

        return temp;
    }


    public Armor(Context context, int num, String name) {


        switch (num) {
            case 1:
                name = context.getString(R.string.armor_01_name);
                break;
            case 2:
                name = context.getString(R.string.armor_02_name);
                break;
            case 3:
                name = context.getString(R.string.armor_03_name);
                break;
        }

        if (name.equals(context.getString(R.string.armor_01_name))) {
            this.name = name;
            size = context.getResources().getInteger(R.integer.armor_01_size);
            weight = context.getResources().getInteger(R.integer.armor_01_weight);
            health = context.getResources().getInteger(R.integer.armor_01_health);
            defense = context.getResources().getInteger(R.integer.armor_01_defense);
        } else if (name.equals(context.getString(R.string.armor_02_name))) {
            this.name = name;
            size = context.getResources().getInteger(R.integer.armor_02_size);
            weight = context.getResources().getInteger(R.integer.armor_02_weight);
            health = context.getResources().getInteger(R.integer.armor_02_health);
            defense = context.getResources().getInteger(R.integer.armor_02_defense);
        } else if (name.equals(context.getString(R.string.armor_03_name))) {
            this.name = name;
            size = context.getResources().getInteger(R.integer.armor_03_size);
            weight = context.getResources().getInteger(R.integer.armor_03_weight);
            health = context.getResources().getInteger(R.integer.armor_03_health);
            defense = context.getResources().getInteger(R.integer.armor_03_defense);
        }


    }

}
