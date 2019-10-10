package and.sample.deckbuilder.adapter;

import java.util.ArrayList;

import and.sample.deckbuilder.data.Armor;
import and.sample.deckbuilder.data.Body;
import and.sample.deckbuilder.data.Weapon;

public class PartsGroup {
    public String groupTitle;

    public ArrayList<Body> bodies = new ArrayList<>();
    public ArrayList<Armor> armors = new ArrayList<>();
    public ArrayList<Weapon> weapons = new ArrayList<>();

}
