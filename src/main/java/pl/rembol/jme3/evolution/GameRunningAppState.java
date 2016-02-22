package pl.rembol.jme3.evolution;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.FogFilter;
import com.jme3.renderer.ViewPort;
import com.jme3.shadow.DirectionalLightShadowRenderer;
import com.jme3.system.AppSettings;
import pl.rembol.jme3.evolution.deck.Deck;
import pl.rembol.jme3.evolution.table.Table;

import java.io.IOException;

public class GameRunningAppState extends AbstractAppState {

    int frame = 200;

    private AppSettings settings;

    public GameRunningAppState(AppSettings settings) {
        this.settings = settings;
    }

    @Override
    public void update(float tpf) {
        frame++;


    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        SimpleApplication simpleApp = (SimpleApplication) app;

        simpleApp.getRootNode().attachChild(new Table(app.getAssetManager()));

        try {
            Deck deck = new Deck(app.getAssetManager());
            deck.setLocalTranslation(0, 0, -.5f);
            simpleApp.getRootNode().attachChild(deck);
        } catch (IOException e) {
            e.printStackTrace();
        }

        initLightAndShadows(simpleApp, app.getViewPort());

    }

    private void initLightAndShadows(SimpleApplication simpleApplication, ViewPort viewPort) {
        DirectionalLight directional = new DirectionalLight();
        directional.setDirection(new Vector3f(-2f, -10f, -5f).normalize());
        directional.setColor(ColorRGBA.White.mult(.3f));
        simpleApplication.getRootNode().addLight(directional);
        AmbientLight ambient = new AmbientLight();
        ambient.setColor(ColorRGBA.White.mult(.7f));
        simpleApplication.getRootNode().addLight(ambient);

        final int SHADOWMAP_SIZE = 1024;
        DirectionalLightShadowRenderer dlsr = new DirectionalLightShadowRenderer(simpleApplication.getAssetManager(), SHADOWMAP_SIZE,
                3);
        dlsr.setLight(directional);
        viewPort.addProcessor(dlsr);

        FilterPostProcessor fpp = new FilterPostProcessor(simpleApplication.getAssetManager());

        FogFilter fog = new FogFilter();
        fog.setFogColor(new ColorRGBA(1.0f, 1.0f, 1.0f, 1.0f));
        fog.setFogDistance(400f);
        fog.setFogDensity(1.5f);
        fpp.addFilter(fog);
        viewPort.addProcessor(fpp);
    }

}
