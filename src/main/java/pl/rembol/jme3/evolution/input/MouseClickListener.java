package pl.rembol.jme3.evolution.input;

import com.jme3.app.SimpleApplication;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.Vector2f;
import pl.rembol.jme3.evolution.gui.Clickable;

import java.util.Optional;

import static pl.rembol.jme3.utils.Spatials.getDescendants;

public class MouseClickListener implements ActionListener, AnalogListener {

    public static final String RIGHT_CLICK = "command_rightClick";

    public static final String LEFT_CLICK = "command_leftClick";

    public static final String MOUSE_MOVE = "command_mouseMove";

    private final SimpleApplication simpleApplication;

    private boolean isButtonDown = false;

    private Vector2f dragStartPosition;

    private boolean isDragged = false;

    public MouseClickListener(SimpleApplication simpleApplication) {
        this.simpleApplication = simpleApplication;

        simpleApplication.getInputManager().addMapping(LEFT_CLICK,
                new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        simpleApplication.getInputManager().addListener(this, LEFT_CLICK);

        simpleApplication.getInputManager().addMapping(RIGHT_CLICK,
                new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
        simpleApplication.getInputManager().addListener(this, RIGHT_CLICK);

        simpleApplication.getInputManager().addMapping(MOUSE_MOVE,
                new MouseAxisTrigger(MouseInput.AXIS_X, false),
                new MouseAxisTrigger(MouseInput.AXIS_X, true),
                new MouseAxisTrigger(MouseInput.AXIS_Y, false),
                new MouseAxisTrigger(MouseInput.AXIS_Y, true));
        simpleApplication.getInputManager().addListener(this, MOUSE_MOVE);
    }

    @Override
    public void onAnalog(String name, float value, float tpf) {
        if (isButtonDown
                && dragStartPosition != null
                && dragStartPosition.distance(simpleApplication.getInputManager().getCursorPosition()) > 5f) {
            isDragged = true;
        }
    }

    @Override
    public void onAction(String name, boolean keyPressed, float tpf) {
        if (name.equals(LEFT_CLICK) && keyPressed) {
            isButtonDown = true;
            dragStartPosition = simpleApplication.getInputManager().getCursorPosition().clone();
            isDragged = false;
        }

        if ((name.equals(LEFT_CLICK) || name
                .equals(RIGHT_CLICK)) && !keyPressed) {
            if (!isDragged) {
                if (!(name.equals(LEFT_CLICK) && checkClickableButtons())) {

                    // colide with cards
                }
            }

            isButtonDown = false;
            dragStartPosition = null;
            isDragged = false;
        }


    }

    private boolean checkClickableButtons() {
        Optional<Clickable> clickedButton = getDescendants(simpleApplication.getGuiNode())
                .filter(Clickable.class::isInstance)
                .map(Clickable.class::cast)
                .filter(Clickable::isEnabled)
                .filter(clickable -> clickable.isClicked(simpleApplication.getInputManager().getCursorPosition()))
                .sorted((o1, o2) -> -Float.compare(o1.getLocalTranslation().z, o2.getLocalTranslation().z))
                .findFirst();

        if (clickedButton.isPresent()) {
            clickedButton.get().onClick();
            return true;
        }
        return false;

    }

}
