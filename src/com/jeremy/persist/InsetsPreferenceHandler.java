package com.jeremy.persist;

import java.awt.Insets;

import com.jeremy.preferences.CompoundPreferenceHandler;
import com.jeremy.preferences.UserPreferences;

public class InsetsPreferenceHandler implements CompoundPreferenceHandler<Insets> {

	@Override
	public Insets read(String key, UserPreferences preferences) {
		int x = preferences.get(key + "-insets-x", Integer.class);
		int y = preferences.get(key + "-insets-y", Integer.class);
		int width = preferences.get(key + "-insets-width", Integer.class);
		int height = preferences.get(key + "-insets-height", Integer.class);
		return new Insets(x, y, width, height);
	}

	@Override
	public void write(String key, UserPreferences preferences, Insets value) {
		preferences.put(key + "-insets-top", value.top);
		preferences.put(key + "-insets-left", value.left);
		preferences.put(key + "-insets-bottom", value.bottom);
		preferences.put(key + "-insets-right", value.right);
	}

	@Override
	public void remove(String key, UserPreferences preferences) {
		preferences.remove(key + "-insets-top", Integer.class);
		preferences.remove(key + "-insets-left", Integer.class);
		preferences.remove(key + "-insets-bottom", Integer.class);
		preferences.remove(key + "-insets-right", Integer.class);
	}

	@Override
	public boolean contains(String key, UserPreferences preferences) {
		if (!preferences.contains(key + "-insets-top", Integer.class)) return false;
		if (!preferences.contains(key + "-insets-left", Integer.class)) return false;
		if (!preferences.contains(key + "-insets-bottom", Integer.class)) return false;
		if (!preferences.contains(key + "-insets-right", Integer.class)) return false;
		return true;
	}

}
