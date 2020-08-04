package com.jeremy.persist;

import java.util.ArrayList;
import java.util.List;

import com.jeremy.preferences.CompoundPreferenceHandler;
import com.jeremy.preferences.UserPreferences;

public class ListPreferenceHandler implements CompoundPreferenceHandler<List<?>> {

	@Override
	public List<?> read(String key, UserPreferences preferences) {
		List<Object> result = new ArrayList<>();
		try {
			Class<?> classType = Class.forName(preferences.get(String.format("%s-list-class", key), String.class));
			String itemKey;
			for (int i = 0; preferences.contains(itemKey = String.format("%s-list-item-%d", key, i), classType); i++) {
				result.add(preferences.get(itemKey, classType));
			}
		} catch (ClassNotFoundException exception) {
			exception.printStackTrace();
		}
		return result;
	}

	@Override
	public void write(String key, UserPreferences preferences, List<?> value) {
		if (value.size() > 0) {
			String className = value.get(0).getClass().getName();
			preferences.put(String.format("%s-list-class", key), className);
			for (int i = 0, len = value.size(); i < len; i++) {
				preferences.put(String.format("%s-list-item-%d", key, i), value.get(i));
			}
		}
	}

	@Override
	public void remove(String key, UserPreferences preferences) {
		try {
			Class<?> classType = Class.forName(preferences.get(String.format("%s-list-class", key), String.class));
			String itemKey;
			for (int i = 0; preferences.contains(itemKey = String.format("%s-list-item-%d", key, i), classType); i++) {
				preferences.remove(itemKey, classType);
			}
		} catch (ClassNotFoundException exception) {
			exception.printStackTrace();
		}
	}

	@Override
	public boolean contains(String key, UserPreferences preferences) {
		return preferences.contains(String.format("%s-list-class", key), String.class);
	}

}
