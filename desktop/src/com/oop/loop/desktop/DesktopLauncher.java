package com.oop.loop.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.oop.loop.Loops;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title="Loops";
		config.width = 600;
		config.height = 600;
		new LwjglApplication(new Loops(), config);
	}
}
