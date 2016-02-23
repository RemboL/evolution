package pl.rembol.jme3.evolution.phase.selectplayers;

import com.jme3.asset.AssetManager;
import com.jme3.ui.Picture;
import pl.rembol.jme3.evolution.gui.Button;

class AddPlayerButton extends Button {
    private final SelectPlayersPhase selectPlayersPhase;

    AddPlayerButton(AssetManager assetManager, SelectPlayersPhase selectPlayersPhase) {
        super(assetManager, 440, 48);
        this.selectPlayersPhase = selectPlayersPhase;

        Picture picture = new Picture("Add icon");
        picture.setImage(assetManager, "buttons/add.png", true);
        picture.setWidth(48);
        picture.setHeight(48);
        picture.setLocalTranslation(196, 0, 0);
        attachChild(picture);

    }

    @Override
    public void onClick() {
        selectPlayersPhase.addPlayer();
    }
}
