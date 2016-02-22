package pl.rembol.jme3.evolution.traits;

import pl.rembol.jme3.evolution.i18n.TraitNames;

public class ParasiteTrait extends Trait {

    @Override
    public String headerText() {
        return TraitNames.PARASITE.text();
    }

    @Override
    public FoodRequirement food() {
        return FoodRequirement.DOUBLE;
    }
}
