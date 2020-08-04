package com.jeremy.preferences;

public interface CompoundPreferenceHandler<V> {

	public abstract V read(String key, UserPreferences preferences);

	public abstract void write(String key, UserPreferences preferences, V value);

	public abstract void remove(String key, UserPreferences preferences);

	public abstract boolean contains(String key, UserPreferences preferences);

}
