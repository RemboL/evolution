package pl.rembol.jme3.evolution.card;

import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Quad;
import com.jme3.texture.Texture;
import pl.rembol.jme3.evolution.traits.Trait;

import java.util.List;

public class Card extends Node {

    private final AssetManager assetManager;

    private Node upperSide;

    private Node lowerSide;

    public Card(AssetManager assetManager, List<Trait> traits) {
        this(assetManager);

        if (traits.size() >= 1) {
            initTrait(upperSide, traits.get(0));
        }

        if (traits.size() >= 2) {
            initTrait(lowerSide, traits.get(1));
        }

    }

    private void initTrait(Node sideNode, Trait trait) {
        addHeaderText(sideNode, trait.headerText());

        switch (trait.food()) {
            case STANDARD:
                addFoodIcon(sideNode, 1);
                break;
            case DOUBLE:
                addFoodIcon(sideNode, 2);
                break;
            case NONE:
                break;
        }
    }

    private Card(AssetManager assetManager) {
        super("card");
        this.assetManager = assetManager;

        upperSide = new Node("upper side");
        upperSide.rotate(0f, FastMath.PI, 0f);
        attachChild(upperSide);

        lowerSide = new Node("lower side");
        lowerSide.rotate(0f, FastMath.PI, 0f);
        lowerSide.rotate(0f, 0f, FastMath.PI);
        attachChild(lowerSide);


        Geometry back = createBack();
        attachChild(back);

        Geometry front = createFront();
        attachChild(front);
    }

    private void addFoodIcon(Node sideNode, int quantity) {
        Quad foodMesh = new Quad(.15f, .15f);
        Geometry foodGeometry = new Geometry("Food", foodMesh);
        Texture foodTexture = assetManager.loadTexture("textures/food-" + quantity + ".png");
        Material foodMaterial = createMaterial(assetManager, foodTexture);
        foodGeometry.setMaterial(foodMaterial);
        foodGeometry.setLocalTranslation(.15f, .35f, 0.001f);
        foodGeometry.setShadowMode(RenderQueue.ShadowMode.Off);
        foodMaterial.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        foodGeometry.setQueueBucket(RenderQueue.Bucket.Transparent);
        sideNode.attachChild(foodGeometry);
    }

    private void addHeaderText(Node sideNode, String... texts) {
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);

        BitmapFont guiFont = assetManager.loadFont("texts/Default.fnt");


        for (int i = 0; i < texts.length; ++i) {
            BitmapText textLine = new BitmapText(guiFont);
            textLine.setSize(.1f);
            textLine.setText(texts[i]);
            textLine.setColor(ColorRGBA.Black);
            textLine.setShadowMode(RenderQueue.ShadowMode.Off);

            if (textLine.getLineWidth() > .4) {
                textLine.scale(.4f / textLine.getLineWidth(), 1, 1);
                textLine.setLocalTranslation(-.2f, .44f - i * .1f, .002f);
            } else {
                textLine.setLocalTranslation(-textLine.getLineWidth() / 2, .44f - i * .1f, .002f);
            }
            sideNode.attachChild(textLine);
        }
    }

    private Geometry createBack() {
        Quad backMesh = new Quad(.6f, 1f);
        Geometry cardGeometry = new Geometry("Card", backMesh);
        Texture backTexture = assetManager.loadTexture(
                "textures/back.png");
        Material cardMaterial = createMaterial(assetManager, backTexture);
        cardGeometry.setMaterial(cardMaterial);

        cardGeometry.setLocalTranslation(-.3f, -.5f, 0f);

        cardGeometry.setShadowMode(RenderQueue.ShadowMode.Cast);
        return cardGeometry;
    }

    private Geometry createFront() {
        Quad frontMesh = new Quad(.6f, 1f);
        Geometry frontGeometry = new Geometry("Card", frontMesh);
        Texture frontTexture = assetManager.loadTexture(
                "textures/front.png");
        Material cardMaterial = createMaterial(assetManager, frontTexture);
        frontGeometry.setMaterial(cardMaterial);

        frontGeometry.setLocalTranslation(.3f, -.5f, 0f);
        frontGeometry.rotate(0f, FastMath.PI, 0f);

        frontGeometry.setShadowMode(RenderQueue.ShadowMode.Cast);
        return frontGeometry;
    }

    private Material createMaterial(AssetManager assetManager, Texture texture) {
        Material cardMaterial = new Material(assetManager,
                "Common/MatDefs/Light/Lighting.j3md");
        cardMaterial.setTexture("DiffuseMap", texture);
        cardMaterial.setColor("Diffuse", ColorRGBA.White);
        cardMaterial.setColor("Specular", ColorRGBA.White);
        cardMaterial.setColor("Ambient", ColorRGBA.White);
        cardMaterial.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        return cardMaterial;
    }


}
