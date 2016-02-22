package pl.rembol.jme3.evolution.table;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.FastMath;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;

public class Table extends Node {

    public Table(AssetManager assetManager) {
        super("table");


        Box tableMesh = new Box(5f, .1f, 5f);
        Geometry tableGeometry = new Geometry("Table", tableMesh);
        Material tableMaterial = new Material(assetManager,
                "Common/MatDefs/Misc/Unshaded.j3md");
        Texture tableTexture = assetManager.loadTexture(
                "textures/wood.jpg");
        tableMaterial.setTexture("ColorMap", tableTexture);
        tableGeometry.setMaterial(tableMaterial);

        tableGeometry.setLocalTranslation(0f, -.1f, 0f);

        tableGeometry.setShadowMode(RenderQueue.ShadowMode.Receive);
        tableGeometry.rotate(0f, FastMath.HALF_PI, 0f);

        attachChild(tableGeometry);
    }
}
