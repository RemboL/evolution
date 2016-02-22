package pl.rembol.jme3.evolution.traits;

import pl.rembol.jme3.evolution.i18n.TraitNames;

public class PredatorTrait extends Trait {

    @Override
    public String headerText() {
        return TraitNames.PREDATOR.text();
    }

    @Override
    public FoodRequirement food() {
        return FoodRequirement.STANDARD;
    }
}
