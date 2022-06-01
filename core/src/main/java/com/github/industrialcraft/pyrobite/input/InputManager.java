package com.github.industrialcraft.pyrobite.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.github.industrialcraft.pyrobite.PyrobiteMain;
import com.github.industrialcraft.pyrobite.terminal.TerminalComponent;
import com.github.industrialcraft.pyrobite.ui.window.Window;
import com.github.industrialcraft.pyrobite.ui.window.components.WinButton;

import java.util.ArrayList;

public class InputManager implements InputProcessor {

    private static final ArrayList<InputProcessor> inputs = new ArrayList<>();
    private Window terminalW;
    private TerminalComponent terminalComponent;

    public InputManager() {
        terminalComponent = new TerminalComponent(0, 0);
    }

    public static void addInput(InputProcessor processor) {
        inputs.add(processor);
    }

    public static void removeInput(InputProcessor input) {
        inputs.remove(input);
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
        if (character == '`') {
            if (!PyrobiteMain.getInstance().ui.components().contains(terminalW)) {
                terminalW = new Window("{Terminal}", 10, 10, 1200, 800);
                terminalW.add(terminalComponent);
                terminalW.add(new WinButton("Close", terminalW.getWidth() - 60, 0, 50, terminalW::dispose));

                terminalComponent.setActive(true);

                PyrobiteMain.getInstance().ui.addComponent(terminalW);
                return false;
            }
            else {
                terminalComponent.setActive(false);
                terminalW.dispose();
                return false;
            }
        }

        inputs.forEach((a) -> a.keyTyped(character));
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        try {
            inputs.forEach((a) -> a.touchDown(screenX, screenY, pointer, button));
        }
        catch (Exception e) {

        }
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
