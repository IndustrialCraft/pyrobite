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
import com.google.gson.JsonParser;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class PyrobiteMain extends ApplicationAdapter {
	private Scheduler scheduler;
	private UI ui;

	// TODO: REMOVE
	public Window window;

	@Override
	public void create() {
		Gdx.input.setInputProcessor(new InputManager());


		new TestTaskData();

		//this.scene = new Scene();

		SceneSaverLoader.load(new JsonParser().parse("{\"entities\": []}").getAsJsonObject());
		Entity entity = new TestEntity(new Position(-100, -100));
		Scene.getInstance().add(entity);
		Scene.getInstance().setCameraEntity(entity);



		this.scheduler = new Scheduler();
		this.ui = new UI();

		window = new Window("Debug", 10, 10, 400, 160);
		window.add(new WinLabel("Welcome to pyrobite!", 10, 135));
		window.add(new WinLabel("Press '`' on your keyboard.", 10, 110));
		window.add(new WinButton("Dismiss",
				window.getWidth() - 100,
				10,
				80,
				() ->
				{this.ui.removeComponent(window);}));

		this.ui.addComponent(window);
		this.ui.addComponent(new TerminalComponent());
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if(!AssetLoader.getInstance().update()){
			/*new ProgressBarComponent(Color.BLUE, AssetLoader.getInstance().progress(),
					-800,
					-50,
					1600,
					100).render(null, this.ui.shapeRenderer, this.ui.getUICamera());
			return;*/
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
}