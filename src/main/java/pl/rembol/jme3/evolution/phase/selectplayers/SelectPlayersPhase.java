package pl.rembol.jme3.evolution.phase.selectplayers;

import com.jme3.app.SimpleApplication;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;
import pl.rembol.jme3.evolution.GameRunningAppState;
import pl.rembol.jme3.evolution.gui.BackScreen;
import pl.rembol.jme3.evolution.phase.Phase;
import pl.rembol.jme3.evolution.phase.dealcards.DealCardsPhase;
import pl.rembol.jme3.evolution.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SelectPlayersPhase extends Phase {

    private static final int MIN_PLAYERS = 2;

    private static final int MAX_PLAYERS = 4;

    private static final float HANDS_DISTANCE = 3f;

    private List<PlayerPanel> playerPanels = new ArrayList<>();
    private SimpleApplication simpleApp;
    private AppSettings settings;
    private GameRunningAppState gameRunningAppState;
    private AddPlayerButton addPlayerButton;
    private FinishButton finishButton;

    private Node playerPhaseNode;

    @Override
    public void initialize(SimpleApplication simpleApp, AppSettings settings, GameRunningAppState gameRunningAppState) {
        this.simpleApp = simpleApp;
        this.settings = settings;
        this.gameRunningAppState = gameRunningAppState;

        playerPhaseNode = new Node("Select Players Phase");
        simpleApp.getGuiNode().attachChild(playerPhaseNode);

        playerPhaseNode.attachChild(new BackScreen(simpleApp.getAssetManager(), settings, new ColorRGBA(0f, 0f, 0f, .9f)));
        finishButton = initFinishButton();

        for (int i = 0; i < MIN_PLAYERS; ++i) {
            addPlayer();
        }

        invalidatePlayerPanels();
    }

    private void removeAddPlayerButton() {
        if (addPlayerButton != null) {
            playerPhaseNode.detachChild(addPlayerButton);
            addPlayerButton = null;
        }
    }

    private void addAddPlayerButton() {
        if (addPlayerButton != null) {
            removeAddPlayerButton();
        }

        addPlayerButton = new AddPlayerButton(simpleApp.getAssetManager(), this);
        addPlayerButton.setLocalTranslation(settings.getWidth() / 2 - 220, 500 - playerPanels.size() * 64, 1);
        playerPhaseNode.attachChild(addPlayerButton);
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
            playerPhaseNode.detachChild(finishButton);
        }
        if (playerPanels.size() <= MAX_PLAYERS && playerPanels.size() >= MIN_PLAYERS) {
            playerPhaseNode.attachChild(finishButton);
        }
    }

    void addPlayer() {
        List<ColorRGBA> availableColors = getAvailableColors();

        PlayerPanel playerPanel = new PlayerPanel(simpleApp.getAssetManager(), this, availableColors.get(FastMath.nextRandomInt(0, availableColors.size() - 1)));
        playerPhaseNode.attachChild(playerPanel);
        playerPanels.add(playerPanel);

        invalidatePlayerPanels();
    }

    void remove(PlayerPanel playerPanel) {
        playerPhaseNode.detachChild(playerPanel);
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
        List<Player> playerList = new ArrayList<>();

        for (int i = 0; i < playerPanels.size(); ++i) {
            Player player = new Player(gameRunningAppState, playerPanels.get(i).getColor());

            float angle = FastMath.TWO_PI * i / playerPanels.size();
            float cos = FastMath.cos(angle);
            float sin = FastMath.sin(angle);


            player.initHand(
                    new Vector3f(cos * HANDS_DISTANCE, .01f, sin * HANDS_DISTANCE),
                    new Quaternion().fromAngleAxis(-FastMath.HALF_PI, Vector3f.UNIT_X)
                            .mult(new Quaternion().fromAngleAxis(-angle - FastMath.HALF_PI, Vector3f.UNIT_Z)));

            playerList.add(player);
        }

        gameRunningAppState.setPlayers(playerList);
        playerPhaseNode.getParent().detachChild(playerPhaseNode);

        new DealCardsPhase().initialize(simpleApp, settings, gameRunningAppState);

    }
}
