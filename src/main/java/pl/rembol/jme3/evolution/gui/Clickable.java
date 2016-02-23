package pl.rembol.jme3.evolution.gui;

import com.jme3.math.Vector2f;
import com.jme3.scene.Node;

public abstract class Clickable extends Node {

    private final int width;
    private final int height;

    private boolean enabled = true;

    public Clickable(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public boolean isClicked(Vector2f cursorPosition) {
        return getWorldTranslation().x <= cursorPosition.x &&
                getWorldTranslation().x + width >= cursorPosition.x &&
                getWorldTranslation().y <= cursorPosition.y &&
                getWorldTranslation().y + height >= cursorPosition.y;
    }

    public abstract void onClick();

    public void disable() {
        enabled = false;
    }

    public void enable() {
        enabled = true;
    }

    public boolean isEnabled() {
        return enabled;
    }
}