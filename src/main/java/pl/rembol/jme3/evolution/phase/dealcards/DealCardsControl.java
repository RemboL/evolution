package pl.rembol.jme3.evolution.phase.dealcards;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;
import pl.rembol.jme3.evolution.deck.Deck;
import pl.rembol.jme3.evolution.player.Hand;
import pl.rembol.jme3.evolution.player.Player;

import java.util.LinkedList;
import java.util.Queue;

public class DealCardsControl extends AbstractControl {

    private static final float DELAY = .4f;

    private Queue<Player> dealingQueue = new LinkedList<>();

    private final Deck deck;

    private float t = 0;

    DealCardsControl(Deck deck) {
        this.deck = deck;
    }

    void add(Player player) {
        dealingQueue.add(player);
    }

    @Override
    protected void controlUpdate(float tpf) {
        t += tpf;

        if (t > DELAY) {
            Player player = dealingQueue.poll();
            Hand hand = player.hand();
            hand.addCard(deck.removeCard());
            t = 0;
        }

        if (deck.isEmpty() || dealingQueue.isEmpty()) {
            getSpatial().getParent().detachChild(getSpatial());
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {

    }
}
