package com.github.industrialcraft.pyrobite.ui.window.lookandfeel;

public class WoodenLookAndFeel extends LookAndFeel {

    public WoodenLookAndFeel() {
        super.WINDOW_SIDE_LEFT = getTexture("window/window_side_left.png");
        super.WINDOW_SIDE_RIGHT = getTexture("window/window_side_right.png");
        super.WINDOW_SIDE_CENTER = getTexture("window/window_internal.png");
        super.WINDOW_SIDE_TOP = getTexture("window/window_side_top.png");
        super.WINDOW_SIDE_BOTTOM = getTexture("window/window_side_bottom.png");

        super.BUTTON_BEGIN = getTexture("window/button_begin.png");
        super.BUTTON_CENTER = getTexture("window/button_center.png");
        super.BUTTON_END = getTexture("window/button_end.png");

        super.PROGRESSBAR_BEGIN = getTexture("window/progress_bar_start.png");
        super.PROGRESSBAR_CENTER = getTexture("window/progress_bar_middle.png");
        super.PROGRESSBAR_END = getTexture("window/progress_bar_end.png");
        super.PROGRESSBAR_CONTENT = getTexture("window/progress.png");
    }
}
