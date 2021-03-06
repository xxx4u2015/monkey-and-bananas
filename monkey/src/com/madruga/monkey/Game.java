package com.madruga.monkey;

import java.util.Iterator;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.math.MathUtils;

public class Game implements ApplicationListener {
	
	SpriteBatch batch;
	Texture monkeyTexture;
	Texture bananaTexture;
	OrthographicCamera camera;
	Rectangle monkey;
	Rectangle banana;
	Array<Rectangle> bananas;
	long lastDropTime;
	int bananasEaten = 0;
	BitmapFont font;	
	
	@Override
	public void create() {
		monkeyTexture = new Texture(Gdx.files.internal("data/monkey.png"));
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 480, 600);
		batch = new SpriteBatch();
		
		monkey = new Rectangle();
		monkey.x = 480 / 2 - 64 / 2;
		monkey.y = 0;
		monkey.width = 64;
		monkey.height = 60;
		
		bananaTexture = new Texture(Gdx.files.internal("data/banana.png"));
		bananas = new Array<Rectangle>();
		spawnBanana();
		
		font = new BitmapFont();

	}  

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		
		font.setColor(0, 0, 0, 1);
		
		batch.begin();
		batch.draw(monkeyTexture, monkey.x,monkey.y);
		
		for(Rectangle banana: bananas){
			batch.draw(bananaTexture, banana.x, banana.y);
		}
		
		font.draw(batch, "Bananas eaten: " + bananasEaten, 10, 580);
		batch.end();
		
		if(TimeUtils.nanoTime() - lastDropTime > 1000000000)
			spawnBanana();
		
		Iterator<Rectangle> iter = bananas.iterator();
		while(iter.hasNext()){
			Rectangle banana = iter.next();
			banana.y -= 200 * Gdx.graphics.getDeltaTime();
			if(banana.y + 64 < 0)
				iter.remove();
			if(banana.overlaps(monkey)){
				bananasEaten ++;
				iter.remove();
			}
		}
		
		monkey.x = Gdx.input.getX();
		
		if(monkey.x >= 480 - 64)
			monkey.x = 480 - 64;
		
	}
	
	public void spawnBanana(){
		banana = new Rectangle();
		banana.x = MathUtils.random(0, 480 - 64);
		banana.y = 700;
		banana.width = 64;
		banana.height = 64;
		bananas.add(banana);
		lastDropTime = TimeUtils.nanoTime();
	}
	
	@Override
	public void dispose() {
		batch.dispose();
		monkeyTexture.dispose();
		bananaTexture.dispose();
	}
	
	@Override
	public void pause() {

	}
	
	@Override
	public void resume() {

	}
	
	@Override
	public void resize(int width, int height) {

	}

}
