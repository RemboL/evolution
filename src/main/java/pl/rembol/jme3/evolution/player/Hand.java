package pl.rembol.jme3.evolution.player;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import pl.rembol.jme3.evolution.card.Card;

import java.util.ArrayList;
import java.util.List;

public class Hand extends Node {

    private static final float HAND_CARD_OFFSET = .3f;

    private static final float HAND_CARD_TILT = .02f;

    private static final float DECK_THROW_HEIGHT = 1.5f;

    private List<Card> cards = new ArrayList<>();

    public void addCard(Card newCard) {
        cards.forEach(card -> card.setTargetLocation(
                Vector3f.UNIT_X.mult(-HAND_CARD_OFFSET / 2),
                Vector3f.UNIT_X.mult(HAND_CARD_OFFSET / 2),
                Vector3f.UNIT_X.mult(HAND_CARD_OFFSET * cards.indexOf(card))
                        .addLocal(Vector3f.UNIT_X.mult(-HAND_CARD_OFFSET / 2 * (cards.size() + 1))),
                new Quaternion().fromAngleAxis(HAND_CARD_TILT, Vector3f.UNIT_Y), 1f));

        newCard.setLocalTranslation(worldToLocal(newCard.getWorldTranslation(), null));
        newCard.setLocalRotation(getLocalRotation().inverse().mult(newCard.getWorldRotation()));


        attachChild(newCard);
        cards.add(newCard);
        newCard.setTargetLocation(
                Vector3f.UNIT_Z.mult(DECK_THROW_HEIGHT),
                Vector3f.UNIT_Z.mult(DECK_THROW_HEIGHT),
                Vector3f.UNIT_X.mult(HAND_CARD_OFFSET * cards.indexOf(newCard))
                        .addLocal(Vector3f.UNIT_X.mult(-HAND_CARD_OFFSET / 2 * cards.size())),
                new Quaternion().fromAngleAxis(HAND_CARD_TILT, Vector3f.UNIT_Y), 2f);

    }

}
