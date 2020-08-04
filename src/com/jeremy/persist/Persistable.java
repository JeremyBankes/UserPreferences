package com.jeremy.persist;

import com.jeremy.preferences.UserPreferences;

public interface Persistable {

	public abstract void load(UserPreferences preferences);

	public abstract void save(UserPreferences preferences);

}
