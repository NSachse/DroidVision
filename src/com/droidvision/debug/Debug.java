package com.droidvision.debug;

import android.util.Log;

/**
 * Simple debug class to obtain the desired output from all the classes in the app.
 * 
 * @author Nelson Sachse
 * @version 1.0
 *
 */
public class Debug {

	private static boolean showDebug = false;
	private static String nameOfClass = null;

	/**
	 * Get static class
	 */
	private Debug() {
	}

	/**
	 * Show if debug is showing
	 */
	public static boolean isDebugAlive() {
		return showDebug;
	}

	/**
	 * Set the debug alive or dead
	 */
	public static void setDebugAliveOrDead(boolean value) {
		showDebug = value;
	}

	/**
	 * Pass the name of the class to debug
	 * 
	 * @param name of the class
	 */
	public static void setDebugClass(final Class<?> name) {
		nameOfClass = name.getSimpleName();
	}

	/**
	 * Outputs the object
	 * @param obj
	 */
	public static void show(Object obj) {
		if (obj != null) {
			Log.w(nameOfClass, obj.toString());
		}
	}
}
