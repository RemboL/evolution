package pl.rembol.jme3.evolution.gui;

import com.jme3.asset.AssetManager;

public class Button extends Clickable {

    public Button(AssetManager assetManager, int width, int height) {
        super(width, height);

        WindowShade windowShade = new WindowShade(assetManager, width, height, 5);
        attachChild(windowShade);
        windowShade.initOuterShades();
    }

}
