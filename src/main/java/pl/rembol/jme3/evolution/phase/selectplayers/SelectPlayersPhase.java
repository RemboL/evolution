package pl.rembol.jme3.evolution.phase.selectplayers;

import com.jme3.app.SimpleApplication;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.system.AppSettings;
import pl.rembol.jme3.evolution.GameRunningAppState;
import pl.rembol.jme3.evolution.gui.BackScreen;
import pl.rembol.jme3.evolution.phase.Phase;
import pl.rembol.jme3.evolution.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SelectPlayersPhase extends Phase {

    private static final int MIN_PLAYERS = 2;

    private static final int MAX_PLAYERS = 4;

    private List<PlayerPanel> playerPanels = new ArrayList<>();
    private SimpleApplication simpleApp;
    private AppSettings settings;
    private GameRunningAppState gameRunningAppState;
    private AddPlayerButton addPlayerButton;
    private FinishButton finishButton;

    @Override
    public void initialize(SimpleApplication simpleApp, AppSettings settings, GameRunningAppState gameRunningAppState) {
        this.simpleApp = simpleApp;
        this.settings = settings;
        this.gameRunningAppState = gameRunningAppState;
        simpleApp.getGuiNode().attachChild(new BackScreen(simpleApp.getAssetManager(), settings, new ColorRGBA(0f, 0f, 0f, .9f)));
        finishButton = initFinishButton();

        for (int i = 0; i < MIN_PLAYERS; ++i) {
            addPlayer();
        }

        invalidatePlayerPanels();
    }

    private void removeAddPlayerButton() {
        if (addPlayerButton != null) {
            simpleApp.getGuiNode().detachChild(addPlayerButton);
            addPlayerButton = null;
        }
    }

    private void addAddPlayerButton() {
        if (addPlayerButton != null) {
            removeAddPlayerButton();
        }

        addPlayerButton = new AddPlayerButton(simpleApp.getAssetManager(), this);
        addPlayerButton.setLocalTranslation(settings.getWidth() / 2 - 220, 500 - playerPanels.size() * 64, 1);
        simpleApp.getGuiNode().attachChild(addPlayerButton);
    }

    private void invalidatePlayerPanels() {
        removeAddPlayerButton();

        for (PlayerPanel playerPanel : playerPanels) {
            int index = playerPanels.indexOf(playerPanel);
            playerPanel.setLocalTranslation(settings.getWidth() / 2 - 212, 500 - index * 64, 1);
            playerPanel.setNumber(index + 1);
        }

        if (playerPanels.size() < MAX_PLAYERS) {
            addAddPlayerButton();
        }

        if (finishButton.getParent() != null) {
            simpleApp.getGuiNode().detachChild(finishButton);
        }
        if (playerPanels.size() <= MAX_PLAYERS && playerPanels.size() >= MIN_PLAYERS) {
            simpleApp.getGuiNode().attachChild(finishButton);
        }
    }

    void addPlayer() {
        List<ColorRGBA> availableColors = getAvailableColors();

        PlayerPanel playerPanel = new PlayerPanel(simpleApp.getAssetManager(), this, availableColors.get(FastMath.nextRandomInt(0, availableColors.size() - 1)));
        simpleApp.getGuiNode().attachChild(playerPanel);
        playerPanels.add(playerPanel);

        invalidatePlayerPanels();
    }

    void remove(PlayerPanel playerPanel) {
        simpleApp.getGuiNode().detachChild(playerPanel);
        playerPanels.remove(playerPanel);

        invalidatePlayerPanels();
    }

    List<ColorRGBA> getAvailableColors() {
        List<ColorRGBA> allColors = new ArrayList<>();
        allColors.add(ColorRGBA.Blue);
        allColors.add(ColorRGBA.Green);
        allColors.add(ColorRGBA.Red);
        allColors.add(ColorRGBA.Yellow);
        allColors.add(ColorRGBA.Orange);
        allColors.add(ColorRGBA.Brown);
        allColors.add(ColorRGBA.Pink);
        allColors.add(ColorRGBA.White);

        playerPanels.stream()
                .map(PlayerPanel::getColor)
                .forEach(allColors::remove);

        return allColors;
    }

    private FinishButton initFinishButton() {
        FinishButton finishButton = new FinishButton(simpleApp.getAssetManager(), this);

        finishButton.setLocalTranslation(settings.getWidth() - 160, 32, 1);

        return finishButton;
    }

    void finish() {
        gameRunningAppState.setPlayers(playerPanels.stream().map(playerPanel -> new Player(playerPanel.getColor())).collect(Collectors.toList()));
    }
}
