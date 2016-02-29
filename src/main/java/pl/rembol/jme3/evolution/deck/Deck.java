package pl.rembol.jme3.evolution.deck;

import com.google.gson.Gson;
import com.jme3.asset.AssetManager;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import org.apache.commons.io.IOUtils;
import pl.rembol.jme3.evolution.card.Card;
import pl.rembol.jme3.evolution.deck.json.CardDTO;
import pl.rembol.jme3.evolution.deck.json.DeckDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Deck extends Node {

    private static final float CARD_BREADTH = 0.003f;

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

        Collections.shuffle(cards);

        setLocalRotation(new Quaternion().fromAngleAxis(-FastMath.HALF_PI, Vector3f.UNIT_X));

        for (int i = 0; i < cards.size(); i++) {
            cards.get(i).setLocalTranslation(0,  0, CARD_BREADTH * i);
        }
        cards.forEach(this::attachChild);

    }

    public Card removeCard() {
        if (cards.isEmpty()) {
            return null;
        }
        Card card = cards.get(cards.size() - 1);
        cards.remove(card);
        return card;

    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

}
