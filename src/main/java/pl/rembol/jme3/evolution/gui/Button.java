package pl.rembol.jme3.evolution.gui;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Spatial;

public abstract class Button extends Clickable {

    private Spatial button;

    protected final AssetManager assetManager;

    public Button(AssetManager assetManager, int width, int height) {
        this(assetManager, width, height, new ColorRGBA(.5F, .5F, .5F, .4F));
    }

    public Button(AssetManager assetManager, int width, int height, ColorRGBA color) {
        super(width, height);

        this.assetManager = assetManager;

        ButtonShade windowShade = new ButtonShade(assetManager, width, height, 5);
        attachChild(windowShade);
        button = windowShade.initButton(color);
    }

    public void setColor(ColorRGBA color) {
        button.setMaterial(initMaterial(color));
    }

    private Material initMaterial(ColorRGBA colorRGBA) {
        Material mat = new Material(assetManager, "Common/MatDefs/Gui/Gui.j3md");
        mat.setColor("Color", colorRGBA);
        mat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        return mat;
    }

}
