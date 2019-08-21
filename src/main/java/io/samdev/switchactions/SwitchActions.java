package io.samdev.switchactions;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class SwitchActions extends JavaPlugin
{
	@Override
	public void onEnable()
	{
		saveDefaultConfig();

		Bukkit.getPluginManager().registerEvents(new SwitchListener(this), this);
	}
}
