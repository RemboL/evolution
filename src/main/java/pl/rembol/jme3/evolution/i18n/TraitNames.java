package pl.rembol.jme3.evolution.i18n;

import java.util.ResourceBundle;

public enum TraitNames {

    DISCARD_TAIL,
    PASTURE,
    FAT_LAYER,
    LARGE_BODY_MASS,
    PREDATOR,
    PARASITE,
    COOPERATION,
    SYMBIOSIS,
    VENOMOUS,
    SWIMMING,
    HIBERNATION,
    CAMOUFLAGE,
    QUICKNESS,
    BURROWING,
    PIRACY,
    COMMUNICATION,
    KEEN_EYESIGHT,
    MIMICRY,
    CARRION_FEEDER;

    private static ResourceBundle resourceBundle;

    static {
        resourceBundle = ResourceBundle.getBundle("texts/captions", new UTF8Control());
    }

    public String text() {
        return resourceBundle.getString("trait." + name().toLowerCase() + ".name");
    }

}
