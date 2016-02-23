package pl.rembol.jme3.evolution.phase.selectplayers;

import com.jme3.asset.AssetManager;
import com.jme3.ui.Picture;
import pl.rembol.jme3.evolution.gui.Button;

class RemovePlayerButton extends Button {
    private final PlayerPanel playerPanel;

    RemovePlayerButton(AssetManager assetManager, PlayerPanel playerPanel) {
        super(assetManager, 48, 48);

        Picture picture = new Picture("Remove icon");
        picture.setImage(assetManager, "buttons/remove.png", true);
        picture.setWidth(48);
        picture.setHeight(48);
        attachChild(picture);

        this.playerPanel = playerPanel;
    }

    @Override
    public void onClick() {
        playerPanel.remove();
    }
}
