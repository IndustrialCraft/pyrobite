package com.github.industrialcraft.pyrobite;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.github.industrialcraft.pyrobite.entity.Entity;
import com.github.industrialcraft.pyrobite.input.InputManager;
import com.github.industrialcraft.pyrobite.location.Position;
import com.github.industrialcraft.pyrobite.scene.Scene;
import com.github.industrialcraft.pyrobite.scene.SceneSaverLoader;
import com.github.industrialcraft.pyrobite.terminal.ui.window.components.WinLabel;
import com.github.industrialcraft.pyrobite.terminal.ui.window.components.WinProgressbar;
import com.github.industrialcraft.pyrobite.test.TestEntity;
import com.github.industrialcraft.pyrobite.test.TestTaskData;
import com.github.industrialcraft.pyrobite.terminal.ui.ProgressBarComponent;
import com.github.industrialcraft.pyrobite.terminal.TerminalComponent;
import com.github.industrialcraft.pyrobite.terminal.ui.UI;
import com.github.industrialcraft.pyrobite.terminal.ui.window.Window;
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
		this.ui.addComponent(new TerminalComponent());

		window = new Window("Debug", -800, -50, 1000, 500);
		window.add(new WinProgressbar(0,0, 400));

		this.ui.addComponent(window);

		//this.ui.addComponent(new ProgressBarComponent(Color.ORANGE, 0.5f, -800, -50, 1600, 100));

		 /*this.ui.addComponent(
				new ButtonComponent(40, 50, 10, "AHOJ!", () -> System.out.println("clicked"))
		 );*/

		/*Entity entity = new TestEntity();
		this.scene.add(entity);
		this.scene.getScheduler().addTask(entity, new TestTaskData(), 25);
		System.out.println(SceneSaverLoader.save(scene));*/

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