package com.jeremy.preferences;

import java.awt.Color;
import java.awt.Insets;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.prefs.Preferences;

import com.jeremy.persist.ColorPreferenceHandler;
import com.jeremy.persist.InsetsPreferenceHandler;
import com.jeremy.persist.ListPreferenceHandler;
import com.jeremy.persist.RectanglePreferenceHandler;

public class UserPreferences {

	private Preferences preferences;

	private HashMap<Class<?>, CompoundPreferenceHandler<Object>> handlers = new HashMap<>();

	public UserPreferences(Class<?> save) {
		preferences = Preferences.userNodeForPackage(save);
		register(Color.class, new ColorPreferenceHandler());
		register(Insets.class, new InsetsPreferenceHandler());
		register(List.class, new ListPreferenceHandler());
		register(Rectangle.class, new RectanglePreferenceHandler());
	}

	public void put(String key, Object value) {
		if (value instanceof String) {
			preferences.put(key, (String) value);
		} else if (value instanceof Boolean) {
			preferences.putBoolean(key, (Boolean) value);
		} else if (value instanceof Integer) {
			preferences.putInt(key, (Integer) value);
		} else if (value instanceof Long) {
			preferences.putLong(key, (Long) value);
		} else if (value instanceof Float) {
			preferences.putDouble(key, (Float) value);
		} else if (value instanceof Double) {
			preferences.putDouble(key, (Double) value);
		} else if (handlers.containsKey(value.getClass())) {
			CompoundPreferenceHandler<Object> handler = handlers.get(value.getClass());
			handler.write(key, this, value);
		} else {
			Optional<Class<?>> optionalHandler = handlers.keySet().stream().filter(other -> other.isAssignableFrom(value.getClass())).findAny();
			if (optionalHandler.isPresent()) {
				CompoundPreferenceHandler<Object> handler = handlers.get(optionalHandler.get());
				handler.write(key, this, value);
			} else throw new IllegalArgumentException("no compound preference handler for class: " + value.getClass().getName());
		}
	}

	public <V> V get(String key, Class<V> valueClass, V defaultValue) {
		if (valueClass == String.class) {
			return valueClass.cast(preferences.get(key, (String) defaultValue));
		} else if (valueClass == Boolean.class) {
			return valueClass.cast(preferences.getBoolean(key, defaultValue == null ? false : (Boolean) defaultValue));
		} else if (valueClass == Integer.class) {
			return valueClass.cast(preferences.getInt(key, defaultValue == null ? 0 : (Integer) defaultValue));
		} else if (valueClass == Long.class) {
			return valueClass.cast(preferences.getLong(key, defaultValue == null ? 0 : (Long) defaultValue));
		} else if (valueClass == Float.class) {
			return valueClass.cast(preferences.getDouble(key, defaultValue == null ? 0.0f : (Float) defaultValue));
		} else if (valueClass == Double.class) {
			return valueClass.cast(preferences.getDouble(key, defaultValue == null ? 0.0 : (Double) defaultValue));
		} else if (handlers.containsKey(valueClass)) {
			CompoundPreferenceHandler<Object> handler = handlers.get(valueClass);
			if (!handler.contains(key, this)) return defaultValue;
			return valueClass.cast(handler.read(key, this));
		} else {
			Optional<Class<?>> optionalHandler = handlers.keySet().stream().filter(other -> other.isAssignableFrom(valueClass)).findAny();
			if (optionalHandler.isPresent()) {
				CompoundPreferenceHandler<Object> handler = handlers.get(optionalHandler.get());
				if (!handler.contains(key, this)) return defaultValue;
				return valueClass.cast(handler.read(key, this));
			} else throw new IllegalArgumentException("no compound preference handler for class: " + valueClass.getName());
		}
	}

	public void remove(String key, Class<?> valueClass) {
		if (handlers.containsKey(valueClass)) {
			handlers.get(valueClass).remove(key, this);
		} else {
			preferences.remove(key);
		}
	}

	public boolean contains(String key, Class<?> valueClass) {
		if (handlers.containsKey(valueClass)) return handlers.get(valueClass).contains(key, this);
		return preferences.get(key, null) != null;
	}

	@SuppressWarnings("unchecked")
	public <V> void register(Class<? extends V> valueClass, CompoundPreferenceHandler<? extends V> handler) {
		handlers.put(valueClass, (CompoundPreferenceHandler<Object>) handler);
	}

	public <V> V get(String key, Class<V> valueClass) {
		return get(key, valueClass, null);
	}

}
