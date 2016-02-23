package pl.rembol.jme3.evolution.phase.selectplayers;

import com.jme3.asset.AssetManager;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;
import pl.rembol.jme3.evolution.gui.Button;

import java.util.List;

class SelectColorPanel extends Node {

    private final AssetManager assetManager;
    private final PlayerPanel playerPanel;

    SelectColorPanel(AssetManager assetManager, PlayerPanel playerPanel, List<ColorRGBA> colors) {
        this.assetManager = assetManager;
        this.playerPanel = playerPanel;
        Button backScreen = new Button(assetManager, 8192, 8192, new ColorRGBA(0, 0, 0, .9f)) {
            @Override
            public void onClick() {
                close();
            }
        };

        backScreen.setLocalTranslation(-4096, -4096, 10);
        attachChild(backScreen);

        for (ColorRGBA color : colors) {
            SelectColorButton button = new SelectColorButton(color);
            button.setLocalTranslation(colors.indexOf(color) * 64, -64, 11);
            attachChild(button);
        }

    }

    private class SelectColorButton extends Button {
        private ColorRGBA color;

        private SelectColorButton(ColorRGBA color) {
            super(SelectColorPanel.this.assetManager, 64, 64, color);

            this.color = color;
        }

        @Override
        public void onClick() {
            playerPanel.setColor(color);
            close();
        }
    }

    private void close() {
        getParent().detachChild(this);
    }

}
