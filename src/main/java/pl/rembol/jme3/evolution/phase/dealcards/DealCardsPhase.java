package pl.rembol.jme3.evolution.phase.dealcards;

import com.jme3.app.SimpleApplication;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;
import pl.rembol.jme3.evolution.GameRunningAppState;
import pl.rembol.jme3.evolution.phase.Phase;

public class DealCardsPhase extends Phase {

    private SimpleApplication simpleApp;
    private AppSettings settings;
    private GameRunningAppState gameRunningAppState;

    @Override
    public void initialize(SimpleApplication simpleApp, AppSettings settings, GameRunningAppState gameRunningAppState) {
        this.simpleApp = simpleApp;
        this.settings = settings;
        this.gameRunningAppState = gameRunningAppState;

        DealCardsControl cardsControl = new DealCardsControl(gameRunningAppState.deck());

        for (int i = 0; i < 6; ++i) {
            gameRunningAppState.getPlayers().forEach(cardsControl::add);
        }

        gameRunningAppState.deck().addControl(cardsControl);
    }


}
