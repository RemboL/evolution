package pl.rembol.jme3.evolution.phase.dealcards;

import com.jme3.app.SimpleApplication;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;
import pl.rembol.jme3.evolution.GameRunningAppState;
import pl.rembol.jme3.evolution.phase.Phase;
import pl.rembol.jme3.evolution.player.Player;

public class DealCardsPhase extends Phase {

    private static final float HANDS_DISTANCE = 3f;

    private SimpleApplication simpleApp;
    private AppSettings settings;
    private GameRunningAppState gameRunningAppState;

    @Override
    public void initialize(SimpleApplication simpleApp, AppSettings settings, GameRunningAppState gameRunningAppState) {
        this.simpleApp = simpleApp;
        this.settings = settings;
        this.gameRunningAppState = gameRunningAppState;

        for (int i = 0; i < gameRunningAppState.getPlayers().size(); ++i) {
            Player player = gameRunningAppState.getPlayers().get(i);

            float angle = FastMath.TWO_PI * i / gameRunningAppState.getPlayers().size();
            float cos = FastMath.cos(angle);
            float sin = FastMath.sin(angle);


            player.initHand(
                    new Vector3f(cos * HANDS_DISTANCE, .01f, sin * HANDS_DISTANCE),
                    new Quaternion().fromAngleAxis(-FastMath.HALF_PI, Vector3f.UNIT_X)
                            .mult(new Quaternion().fromAngleAxis(-angle - FastMath.HALF_PI, Vector3f.UNIT_Z)));
        }

        DealCardsControl cardsControl = new DealCardsControl(gameRunningAppState.deck());

        for (int i = 0; i < 6; ++i) {
            gameRunningAppState.getPlayers().forEach(cardsControl::add);
        }

        Node node = new Node("Deal cards node");
        node.addControl(cardsControl);
        simpleApp.getRootNode().attachChild(node);


    }


}
