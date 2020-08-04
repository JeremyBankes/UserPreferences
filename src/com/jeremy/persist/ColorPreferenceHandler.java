package com.jeremy.persist;

import java.awt.Color;

import com.jeremy.preferences.CompoundPreferenceHandler;
import com.jeremy.preferences.UserPreferences;

public class ColorPreferenceHandler implements CompoundPreferenceHandler<Color> {

	@Override
	public Color read(String key, UserPreferences preferences) {
		return new Color(preferences.get(key + "-color", Integer.class), true);
	}

	@Override
	public void write(String key, UserPreferences preferences, Color value) {
		preferences.put(key + "-color", value.getRGB());
	}

	@Override
	public void remove(String key, UserPreferences preferences) {
		preferences.remove(key + "-color", Integer.class);
	}

	@Override
	public boolean contains(String key, UserPreferences preferences) {
		if (!preferences.contains(key + "-color", Integer.class)) return false;
		return true;
	}

}
