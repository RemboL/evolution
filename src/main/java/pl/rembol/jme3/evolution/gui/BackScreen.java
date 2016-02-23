package pl.rembol.jme3.evolution.gui;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.system.AppSettings;

public class BackScreen extends Node {

    public BackScreen(AssetManager assetManager, AppSettings appSettings, ColorRGBA color) {
        Mesh mesh = new QuadrangleMesh(
                new Vector3f(0, 0, 0),
                new Vector3f(appSettings.getWidth(), 0, 0),
                new Vector3f(appSettings.getWidth(), appSettings.getHeight(), 0),
                new Vector3f(0, appSettings.getHeight(), 0));
        Material material = new Material(assetManager, "Common/MatDefs/Gui/Gui.j3md");
        material.setColor("Color", color);
        material.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);

        Spatial spatial = new Geometry("shade", mesh);
        spatial.setQueueBucket(RenderQueue.Bucket.Gui);
        spatial.setCullHint(CullHint.Never);
        spatial.setMaterial(material);

        attachChild(spatial);
    }


}
