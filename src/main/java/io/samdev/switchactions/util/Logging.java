package io.samdev.switchactions.util;

import io.samdev.switchactions.SwitchActions;
import org.bukkit.plugin.java.JavaPlugin;

public final class Logging
{
	private static final SwitchActions plugin = JavaPlugin.getPlugin(SwitchActions.class);

	public static void severe(String msg)
	{
		plugin.getLogger().severe(msg);
	}
}
