package pl.rembol.jme3.evolution.traits;

abstract public class Trait {
    abstract public String headerText();

    public FoodRequirement food() {
        return FoodRequirement.NONE;
    }
}
