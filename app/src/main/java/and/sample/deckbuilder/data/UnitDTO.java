package and.sample.deckbuilder.data;

public class UnitDTO {
    public String body;
    public String armor;
    public String weapon;
    public String unitName;


    public UnitDTO() {

    }

    public UnitDTO(Body body, Armor armor, Weapon weapon, String unitName) {
        this.body = body.name;
        this.armor = armor.name;
        this.weapon = weapon.name;
        this.unitName = unitName;
    }

}
