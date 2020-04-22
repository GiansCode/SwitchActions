package io.samdev.switchactions;

import io.samdev.actionutil.ActionUtil;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class SwitchActions extends JavaPlugin
{
	
	private static SwitchActions instance;
	private ActionUtil actionUtil;
	
	@Override
	public void onEnable()
	{
		instance = this;
		saveDefaultConfig();

		actionUtil = ActionUtil.init(this);
		
		Bukkit.getPluginManager().registerEvents(new SwitchListener(this), this);
	}

	public ActionUtil getActionUtil() {
		return actionUtil;
	}

	public static SwitchActions getInstance() {
		return instance;
	}
}
