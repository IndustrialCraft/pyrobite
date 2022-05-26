package com.github.industrialcraft.pyrobite;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.JsonReader;
import com.github.industrialcraft.pyrobite.test.TestEntity;
import com.github.industrialcraft.pyrobite.test.TestTaskData;
import com.github.industrialcraft.pyrobite.ui.ButtonComponent;
import com.github.industrialcraft.pyrobite.ui.ProgressBarComponent;
import com.github.industrialcraft.pyrobite.ui.UI;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class PyrobiteMain extends ApplicationAdapter {
	private Scene scene;
	private Scheduler scheduler;
	private UI ui;

	@Override
	public void create() {
		new TestEntity();
		new TestTaskData();

		//this.scene = new Scene();
		this.scene = SceneSaverLoader.load(new JsonParser().parse("{\"entities\":[{\"health\":100.0,\"type\":\"TestEntity\",\"tasks\":[{\"type\":\"TestTaskData\",\"ticks\":25}]}]}").getAsJsonObject());
		this.scheduler = new Scheduler();
		this.ui = new UI();
		/*this.ui.addComponent(*/new ButtonComponent(40, 50, 10, "AHOJ!", () -> System.out.println("clicked"));//);
		/*Entity entity = new TestEntity();
		this.scene.add(entity);
		this.scene.getScheduler().addTask(entity, new TestTaskData(), 25);
		System.out.println(SceneSaverLoader.save(scene));*/
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if(!AssetLoader.getInstance().update()){
			new ProgressBarComponent(Color.BLUE, AssetLoader.getInstance().progress(), -80, -5, 160, 10).render(null, this.ui.shapeRenderer, this.ui.getUICamera());
			/*try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}*/
			this.ui.render();
			return;
		}
		this.scheduler.tick();
		this.scene.onTick();
		this.ui.render();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void dispose() {
		scene.dispose();
	}
}