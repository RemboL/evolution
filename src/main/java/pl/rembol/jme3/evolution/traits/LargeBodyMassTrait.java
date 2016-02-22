package pl.rembol.jme3.evolution.traits;

import pl.rembol.jme3.evolution.i18n.TraitNames;

public class LargeBodyMassTrait extends Trait {

    @Override
    public String headerText() {
        return TraitNames.LARGE_BODY_MASS.text();
    }

    @Override
    public FoodRequirement food() {
        return FoodRequirement.STANDARD;
    }
}
