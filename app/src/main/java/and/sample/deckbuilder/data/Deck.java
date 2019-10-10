package and.sample.deckbuilder.data;

import java.util.ArrayList;

public class Deck {
    public String name = "A_deck";
    public int troopsSize = 0;
    public ArrayList<Unit> units = new ArrayList<>();
    public String key;


    public Deck() {
    }

    public Deck(String name, int troopsSize, ArrayList<Unit> units) {
        this.name = name;
        //this.unitCount = unitCount;
        this.troopsSize = troopsSize;
        this.units = units;

    }


}
