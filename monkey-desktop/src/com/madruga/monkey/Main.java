package com.madruga.monkey;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Monkey and Bananas";
		cfg.useGL20 = true;
		cfg.width = 480;
		cfg.height = 600;
		cfg.resizable = false;
		
		new LwjglApplication(new Game(), cfg);
	}
}
