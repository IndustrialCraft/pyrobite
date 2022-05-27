package com.github.industrialcraft.pyrobite.input;

import com.badlogic.gdx.InputProcessor;

import java.util.ArrayList;

public class InputManager implements InputProcessor {

    private static final ArrayList<InputProcessor> inputs = new ArrayList<>();

    public static void addInput(InputProcessor processor) {
        inputs.add(processor);
    }

    @Override
    public boolean keyDown(int keycode) {
        inputs.forEach((a) -> a.keyDown(keycode));
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        inputs.forEach((a) -> a.keyUp(keycode));
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        inputs.forEach((a) -> a.keyTyped(character));
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        inputs.forEach((a) -> a.touchDown(screenX, screenY, pointer, button));
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        inputs.forEach((a) -> a.touchUp(screenX, screenY, pointer, button));
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        inputs.forEach((a) -> a.touchDragged(screenX, screenY, pointer));
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        inputs.forEach((a) -> a.mouseMoved(screenX, screenY));
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        inputs.forEach((a) -> a.scrolled(amountX, amountY));
        return false;
    }
}
