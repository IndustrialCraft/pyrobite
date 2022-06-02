package com.github.industrialcraft.pyrobite;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.github.industrialcraft.pyrobite.entity.Entity;
import com.github.industrialcraft.pyrobite.input.InputManager;
import com.github.industrialcraft.pyrobite.location.Position;
import com.github.industrialcraft.pyrobite.scene.Scene;
import com.github.industrialcraft.pyrobite.scene.SceneSaverLoader;
import com.github.industrialcraft.pyrobite.ui.window.components.WinButton;
import com.github.industrialcraft.pyrobite.ui.window.components.WinLabel;
import com.github.industrialcraft.pyrobite.test.TestEntity;
import com.github.industrialcraft.pyrobite.test.TestTaskData;
import com.github.industrialcraft.pyrobite.terminal.TerminalComponent;
import com.github.industrialcraft.pyrobite.ui.UI;
import com.github.industrialcraft.pyrobite.ui.window.Window;
import com.github.industrialcraft.pyrobite.ui.window.lookandfeel.LookAndFeelConstants;
import com.google.gson.JsonParser;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class PyrobiteMain extends ApplicationAdapter {

	private static PyrobiteMain instance;

	private Scheduler scheduler;
	public UI ui;

	@Override
	public void create() {

		Gdx.input.setInputProcessor(new InputManager());
		new TestTaskData();

		SceneSaverLoader.load(new JsonParser().parse("{\"entities\": []}").getAsJsonObject());
		Entity entity = new TestEntity(new Position(-100, -100));
		Scene.getInstance().add(entity);
		Scene.getInstance().setCameraEntity(entity);
		LookAndFeelConstants.init();

		this.scheduler = new Scheduler();
		this.ui = new UI();

		instance = this;
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if(!AssetLoader.getInstance().update()){
			return;
		}

		this.scheduler.tick();
		Scene.getInstance().onTick();
		this.ui.render();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		this.ui.resize(width, height);
	}

	@Override
	public void dispose() {
		Scene.getInstance().dispose();
	}

	public static PyrobiteMain getInstance() {
		return instance;
	}
}