package and.sample.deckbuilder.data;

import android.content.Context;

import java.io.Serializable;

public class Unit implements Serializable {
    public Body body;
    public Armor armor;
    public Weapon weapon;
    public String name;

    //final int unitMaxSize = 100;
    public int size = 0;
    int weight = 0;
    int strength = 0;

    int attack = 0;
    int delay = 0;
    boolean swiftAttack = false;

    int health = 0;
    int defense = 0;


    public Unit() {
        init();
    }

    public Unit(Body body, Armor armor, Weapon weapon) {
        this.body = body;
        this.armor = armor;
        this.weapon = weapon;
    }

    public Unit(UnitDTO unitDTO, Context context) {
        setParts(context, unitDTO);
        setName(unitDTO.unitName);
    }

    private void init() {
        size = 0;
        weight = 0;
        strength = 0;

        attack = 0;
        delay = 0;
        swiftAttack = false;

        health = 0;
        defense = 0;
    }

    void setParts(Context context, UnitDTO unitDTO) {
        body = new Body(context, 0, unitDTO.body);
        armor = new Armor(context, 0, unitDTO.armor);
        weapon = new Weapon(context, 0, unitDTO.weapon);

    }

    public void setName(String name) {
        this.name = name;
    }


    public void setData() {
        init();

        if (body != null) {
            size += body.size;
            strength += body.strength;
            health += body.health;
            delay += body.delay;
            defense += body.defense;
        }

        if (armor != null) {
            size += armor.size;
            weight += armor.weight;
            health += armor.health;
            defense += armor.defense;
        }

        if (weapon != null) {
            size += weapon.size;
            weight += weapon.weight;
            attack += weapon.attack;
            delay += weapon.delay;
            swiftAttack = weapon.swiftAttack;
        }

    }


    public int getWeight() {
        return weight;
    }

    public int getStrength() {
        return strength;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public Armor getArmor() {
        return armor;
    }

    public void setArmor(Armor armor) {
        this.armor = armor;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }
}
