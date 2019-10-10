package and.sample.deckbuilder.data;

import android.content.Context;

import java.io.Serializable;

import and.sample.deckbuilder.R;

public class Weapon implements Serializable {
    String name;

    int size;
    int weight;
    int attack;
    int delay;
    boolean swiftAttack;

    public String toString() {
        String temp;
        String swift = "";
        if (swiftAttack) {
            swift = "  속공";
        }
        temp = "[" + name + "]" +
                "  규모:" + size +
                "  무게:" + weight +
                "  공격:" + attack +
                "  딜레이:" + delay +
                swift;

        return temp;
    }

    public Weapon(Context context, int num, String name) {

        switch (num) {
            case 1:
                name = context.getString(R.string.weapon_01_name);
                break;
            case 2:
                name = context.getString(R.string.weapon_02_name);
                break;
            case 3:
                name = context.getString(R.string.weapon_03_name);
                break;
        }

        if (name.equals(context.getString(R.string.weapon_01_name))) {
            this.name = name;
            size = context.getResources().getInteger(R.integer.weapon_01_size);
            weight = context.getResources().getInteger(R.integer.weapon_01_weight);
            attack = context.getResources().getInteger(R.integer.weapon_01_attack);
            delay = context.getResources().getInteger(R.integer.weapon_01_delay);
            swiftAttack = context.getResources().getBoolean(R.bool.weapon_01_swift);
        } else if (name.equals(context.getString(R.string.weapon_02_name))) {
            this.name = name;
            size = context.getResources().getInteger(R.integer.weapon_02_size);
            weight = context.getResources().getInteger(R.integer.weapon_02_weight);
            attack = context.getResources().getInteger(R.integer.weapon_02_attack);
            delay = context.getResources().getInteger(R.integer.weapon_02_delay);
            swiftAttack = context.getResources().getBoolean(R.bool.weapon_02_swift);
        } else if (name.equals(context.getString(R.string.weapon_03_name))) {
            this.name = name;
            size = context.getResources().getInteger(R.integer.weapon_03_size);
            weight = context.getResources().getInteger(R.integer.weapon_03_weight);
            attack = context.getResources().getInteger(R.integer.weapon_03_attack);
            delay = context.getResources().getInteger(R.integer.weapon_03_delay);
            swiftAttack = context.getResources().getBoolean(R.bool.weapon_03_swift);
        }
    }

}
