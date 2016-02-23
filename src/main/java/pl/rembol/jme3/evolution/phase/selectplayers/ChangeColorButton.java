package pl.rembol.jme3.evolution.phase.selectplayers;

import com.jme3.asset.AssetManager;
import com.jme3.math.ColorRGBA;
import pl.rembol.jme3.evolution.gui.Button;

import java.util.List;

class ChangeColorButton extends Button {

    private static final int WIDTH = 320;
    private static final int HEIGHT = 48;
    private final AssetManager assetManager;
    private final SelectPlayersPhase selectPlayersPhase;
    private final PlayerPanel playerPanel;
    private ColorRGBA color;

    ChangeColorButton(AssetManager assetManager, SelectPlayersPhase selectPlayersPhase, PlayerPanel playerPanel, ColorRGBA color) {

        super(assetManager, WIDTH, HEIGHT, color);
        this.assetManager = assetManager;
        this.selectPlayersPhase = selectPlayersPhase;
        this.playerPanel = playerPanel;
        this.color = color;

    }

    @Override
    public void onClick() {
        List<ColorRGBA> availableColors = selectPlayersPhase.getAvailableColors();
        availableColors.add(color);

        attachChild(new SelectColorPanel(assetManager, playerPanel, availableColors));
    }

    public void setColor(ColorRGBA color) {
        this.color = color;
        super.setColor(color);
    }
}
