package pl.rembol.jme3.evolution.phase.selectplayers;

import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;
import pl.rembol.jme3.evolution.gui.Button;

class PlayerPanel extends Node {

    private final BitmapText text;
    private final ChangeColorButton selectColorButton;
    private final SelectPlayersPhase selectPlayersPhase;
    private ColorRGBA color;


    PlayerPanel(AssetManager assetManager, SelectPlayersPhase selectPlayersPhase, ColorRGBA color) {
        this.selectPlayersPhase = selectPlayersPhase;
        this.color = color;

        text = initText(assetManager);
        selectColorButton = initColorButton(assetManager);
        initRemoveButton(assetManager);
    }

    private BitmapText initText(AssetManager assetManager) {
        BitmapFont guiFont = assetManager.loadFont("texts/Default.fnt");

        BitmapText text = new BitmapText(guiFont);
        text.setSize(64f);
        text.setColor(ColorRGBA.White);
        text.setLocalTranslation(0, 64, 0);
        attachChild(text);
        return text;
    }

    private ChangeColorButton initColorButton(AssetManager assetManager) {
        ChangeColorButton selectColorButton = new ChangeColorButton(assetManager, selectPlayersPhase, this, color);
        selectColorButton.setLocalTranslation(64, 8, 0);
        attachChild(selectColorButton);
        return selectColorButton;
    }

    private Button initRemoveButton(AssetManager assetManager) {
        RemovePlayerButton removePlayerButton = new RemovePlayerButton(assetManager, this);
        removePlayerButton.setLocalTranslation(384, 8, 0);
        attachChild(removePlayerButton);
        return removePlayerButton;
    }

    void setNumber(int number) {
        text.setText("" + number + ".");
    }

    ColorRGBA getColor() {
        return color;
    }

    void setColor(ColorRGBA color) {
        this.color = color;
        selectColorButton.setColor(color);
    }

    void remove() {
        selectPlayersPhase.remove(this);
    }
}
