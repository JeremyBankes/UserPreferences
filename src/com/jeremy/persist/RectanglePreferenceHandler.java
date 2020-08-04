package com.jeremy.persist;

import java.awt.Rectangle;

import com.jeremy.preferences.CompoundPreferenceHandler;
import com.jeremy.preferences.UserPreferences;

public class RectanglePreferenceHandler implements CompoundPreferenceHandler<Rectangle> {

	@Override
	public Rectangle read(String key, UserPreferences preferences) {
		int x = preferences.get(key + "-rectangle-x", Integer.class);
		int y = preferences.get(key + "-rectangle-y", Integer.class);
		int width = preferences.get(key + "-rectangle-width", Integer.class);
		int height = preferences.get(key + "-rectangle-height", Integer.class);
		return new Rectangle(x, y, width, height);
	}

	@Override
	public void write(String key, UserPreferences preferences, Rectangle value) {
		preferences.put(key + "-rectangle-x", value.x);
		preferences.put(key + "-rectangle-y", value.y);
		preferences.put(key + "-rectangle-width", value.width);
		preferences.put(key + "-rectangle-height", value.height);
	}

	@Override
	public void remove(String key, UserPreferences preferences) {
		preferences.remove(key + "-rectangle-x", Integer.class);
		preferences.remove(key + "-rectangle-y", Integer.class);
		preferences.remove(key + "-rectangle-width", Integer.class);
		preferences.remove(key + "-rectangle-height", Integer.class);
	}

	@Override
	public boolean contains(String key, UserPreferences preferences) {
		if (!preferences.contains(key + "-rectangle-x", Integer.class)) return false;
		if (!preferences.contains(key + "-rectangle-y", Integer.class)) return false;
		if (!preferences.contains(key + "-rectangle-width", Integer.class)) return false;
		if (!preferences.contains(key + "-rectangle-height", Integer.class)) return false;
		return true;
	}

}
