package com.github.industrialcraft.pyrobite;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.github.industrialcraft.pyrobite.input.InputManager;
import com.github.industrialcraft.pyrobite.scene.Scene;
import com.github.industrialcraft.pyrobite.scene.SceneSaverLoader;
import com.github.industrialcraft.pyrobite.test.TestEntity;
import com.github.industrialcraft.pyrobite.test.TestTaskData;
import com.github.industrialcraft.pyrobite.ui.ButtonComponent;
import com.github.industrialcraft.pyrobite.ui.ProgressBarComponent;
import com.github.industrialcraft.pyrobite.terminal.TerminalComponent;
import com.github.industrialcraft.pyrobite.ui.UI;
import com.google.gson.JsonParser;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class PyrobiteMain extends ApplicationAdapter {

	private static Scene scene;
	private Scheduler scheduler;
	private UI ui;

	public static void setScene(Scene scene1) {
		scene = scene1;
	}

	@Override
	public void create() {
		Gdx.input.setInputProcessor(new InputManager());

		new TestEntity();
		new TestTaskData();

		//this.scene = new Scene();

		scene = SceneSaverLoader.load(new JsonParser().parse("{\"entities\":[{\"health\":100.0,\"type\":\"TestEntity\",\"tasks\":[{\"type\":\"TestTaskData\",\"ticks\":25}]}]}").getAsJsonObject());
		this.scheduler = new Scheduler();
		this.ui = new UI();

		this.ui.addComponent(new TerminalComponent());
		this.ui.addComponent(new ProgressBarComponent(Color.ORANGE, 0.5f, -800, -50, 1600, 100));
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
			this.ui.render();
			new ProgressBarComponent(Color.BLUE, AssetLoader.getInstance().progress(),
					-800,
					-50,
					1600,
					100).render(null, this.ui.shapeRenderer, this.ui.getUICamera());
			return;
		}

		this.scheduler.tick();
		this.scene.onTick();
		this.ui.render();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		this.ui.resize(width, height);
	}

	@Override
	public void dispose() {
		scene.dispose();
	}
}