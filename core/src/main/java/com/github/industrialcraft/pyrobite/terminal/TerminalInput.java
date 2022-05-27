package com.github.industrialcraft.pyrobite.terminal;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.github.industrialcraft.pyrobite.terminal.TerminalComponent;

public class TerminalInput implements InputProcessor {

    private TerminalComponent component;
    public boolean isBackspaceHeldDown;

    public TerminalInput(TerminalComponent component) {
        this.component = component;
    }

    @Override
    public boolean keyDown(int keycode) {

        isBackspaceHeldDown = keycode == Input.Keys.BACKSPACE;

        if (keycode == Input.Keys.ENTER) {
            this.component.submit();
        }

        if (keycode == Input.Keys.BACKSPACE) {
            this.component.backspace();
        }


        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.BACKSPACE) {
            isBackspaceHeldDown = false;
        }

        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        component.charPressed(character);
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
