package pl.rembol.jme3.evolution.phase.selectplayers;

import com.jme3.asset.AssetManager;
import com.jme3.ui.Picture;
import pl.rembol.jme3.evolution.gui.Button;

class FinishButton extends Button {
    private final SelectPlayersPhase selectPlayersPhase;

    FinishButton(AssetManager assetManager, SelectPlayersPhase selectPlayersPhase) {
        super(assetManager, 128, 64);
        this.selectPlayersPhase = selectPlayersPhase;

        Picture picture = new Picture("Finish icon");
        picture.setImage(assetManager, "buttons/ok.png", true);
        picture.setWidth(48);
        picture.setHeight(48);
        picture.setLocalTranslation(40, 8, 0);
        attachChild(picture);
    }

    @Override
    public void onClick() {
        selectPlayersPhase.finish();
    }
}
