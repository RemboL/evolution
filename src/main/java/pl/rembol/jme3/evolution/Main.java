package pl.rembol.jme3.evolution;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppState;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.plugins.blender.BlenderLoader;
import com.jme3.system.AppSettings;

public class Main extends SimpleApplication {

    public Main(AppState[] appStates) {
        super(appStates);
    }

    public static void main(String[] args) {
        Main app = new Main(new AppState[]{});
        app.setShowSettings(false);
        AppSettings settings = new AppSettings(true);
        settings.put("Width", 1280);
        settings.put("Height", 720);
        settings.put("Title", "Evolution - something-something the card game");
        settings.put("VSync", true);
        settings.put("Samples", 4);
        app.setSettings(settings);

        app.start();
    }

    @Override
    public void simpleInitApp() {
        setDisplayStatView(false);
        setDisplayFps(false);

//        Locale.setDefault(new Locale("pl", "PL"));

        assetManager.registerLoader(BlenderLoader.class, "blend");

//        cam.setLocation(new Vector3f(0f, 6f, -6f));
        cam.setLocation(new Vector3f(0f, 3f, -3f));
        cam.setRotation(new Quaternion().fromAngleAxis(0.8f, Vector3f.UNIT_X));

        this.getStateManager().attach(new GameRunningAppState(settings));

    }

}