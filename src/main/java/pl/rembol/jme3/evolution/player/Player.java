package pl.rembol.jme3.evolution.player;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import pl.rembol.jme3.evolution.GameRunningAppState;

public class Player {

    private GameRunningAppState gameRunningAppState;
    private final ColorRGBA colorRGBA;

    private Hand hand;

    public Player(GameRunningAppState gameRunningAppState, ColorRGBA colorRGBA) {
        this.gameRunningAppState = gameRunningAppState;
        this.colorRGBA = colorRGBA;
    }

    public void initHand(Vector3f position, Quaternion rotation) {
        hand = new Hand();
        hand.setLocalRotation(rotation);
        hand.move(position);
        gameRunningAppState.simpleApplication().getRootNode().attachChild(hand);
    }

    public Hand hand() {
        return hand;
    }
}
