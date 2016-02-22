package pl.rembol.jme3.evolution.deck;

import com.google.gson.Gson;
import com.jme3.asset.AssetManager;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.control.AbstractControl;
import org.apache.commons.io.IOUtils;
import pl.rembol.jme3.evolution.card.Card;
import pl.rembol.jme3.evolution.deck.json.CardDTO;
import pl.rembol.jme3.evolution.deck.json.DeckDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Deck extends Node {

    private List<Card> cards = new ArrayList<>();

    public Deck(AssetManager assetManager) throws IOException {
        super("Deck");

        TraitFactory traitFactory = new TraitFactory();
        DeckDTO deckDTO = new Gson().fromJson(IOUtils.toString(getClass().getResourceAsStream("/deck/standard_deck.json")), DeckDTO.class);

        for (CardDTO cardDTO : deckDTO.getCards()) {
            for (int i = 0; i < cardDTO.getCount(); ++i) {
                cards.add(new Card(assetManager, cardDTO.getTraits().stream().map(traitFactory::create).filter(trait -> trait != null).collect(Collectors.toList())));

            }
        }

        cards.forEach(card -> card.rotate(-FastMath.HALF_PI, 0, 0));
        for (int i = 0; i < cards.size(); i++) {
            cards.get(i).setLocalTranslation(0, .01f * i, 0);


            cards.get(i).addControl(new ShuffleControl(i));

        }

        cards.forEach(this::attachChild);

    }

    private static class ShuffleControl extends AbstractControl {

        private final float delay;

        private float cycle = 0;

        private ShuffleControl(int delay) {
            this.delay = delay * .3f;
        }

        @Override
        protected void controlUpdate(float tpf) {
            cycle += tpf;

            if (cycle > 10) {
                cycle -= 10;
            }

            if (cycle > delay && cycle <= delay + FastMath.PI) {
                getSpatial().setLocalTranslation(FastMath.sin((cycle - delay) * 2), delay * .1f + (1 - FastMath.cos((cycle - delay) * 2)) * .8f, (1 - FastMath.cos((cycle - delay) * 2)) * -.5f);
                getSpatial().rotate(0, -tpf * 2, 0);
            } else {
                getSpatial().setLocalTranslation(0, delay * .1f, 0);
                getSpatial().setLocalRotation(new Quaternion().fromAngleAxis(-FastMath.HALF_PI, Vector3f.UNIT_X));
                getSpatial().rotate(0, 0, FastMath.PI);
            }

        }

        @Override
        protected void controlRender(RenderManager rm, ViewPort vp) {

        }
    }

}
