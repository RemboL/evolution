package pl.rembol.jme3.evolution.deck;

import pl.rembol.jme3.evolution.deck.json.TraitDTO;
import pl.rembol.jme3.evolution.traits.*;

public class TraitFactory {

    public Trait create(TraitDTO traitDTO) {
        switch (traitDTO) {
            case discard_tail:
                return new DiscardTailTrait();
            case pasture:
                return new PastureTrait();
            case fat_layer:
                return new FatLayerTrait();
            case large_body_mass:
                return new LargeBodyMassTrait();
            case predator:
                return new PredatorTrait();
            case parasite:
                return new ParasiteTrait();
            case cooperation:
                return new CooperationTrait();
            case symbiosis:
                return new SymbiosisTrait();
            case venomous:
                return new VenomousTrait();
            case swimming:
                return new SwimmingTrait();
            case hibernation:
                return new HibernationTrait();
            case camouflage:
                return new CamouflageTrait();
            case quickness:
                return new QuicknessTrait();
            case burrowing:
                return new BurrowingTrait();
            case piracy:
                return new PiracyTrait();
            case communication:
                return new CommunicationTrait();
            case keen_eyesight:
                return new KeenEyesightTrait();
            case mimicry:
                return new MimicryTrait();
            case carrion_feeder:
                return new CarrionFeederTrait();
        }
        return null;
    }

}
