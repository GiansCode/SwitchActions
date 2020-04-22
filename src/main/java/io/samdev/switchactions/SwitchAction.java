package io.samdev.switchactions;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

class SwitchAction
{
	static List<SwitchAction> parse(ConfigurationSection config)
	{
		List<SwitchAction> actions = new ArrayList<>();

		for (String section : config.getKeys(false))
		{
			actions.add(parse(section, requireNonNull(config.getConfigurationSection(section))));
		}

		return actions;
	}

	private static SwitchAction parse(String sectionName, ConfigurationSection config)
	{
		Material material = Material.matchMaterial(sectionName);
		boolean cancel = config.getBoolean("cancel");
		boolean ignoreMeta = config.getBoolean("ignore_meta");
		boolean requirePermission = config.getBoolean("require_permission");
		List<String> actions = config.getStringList("actions");

		return new SwitchAction(material, cancel, ignoreMeta, requirePermission, actions);
	}

	private final Material material;
	private final boolean cancel;
	private final boolean ignoreMeta;
	private final boolean requirePermission;
	private final List<String> actions;

	private final String permission;

	private SwitchAction(Material material, boolean cancel, boolean ignoreMeta, boolean requirePermission, List<String> actions)
	{
		this.material = material;
		this.cancel = cancel;
		this.ignoreMeta = ignoreMeta;
		this.requirePermission = requirePermission;
		this.actions = actions;

		this.permission = requirePermission ? "switchactions.type." + material.name().toLowerCase() : null;
	}

	boolean isMaterial(Material material)
	{
		return this.material == null || this.material == material;
	}

	boolean isCancel()
	{
		return cancel;
	}

	boolean isIgnoreMeta()
	{
		return ignoreMeta;
	}

	boolean isRequirePermission()
	{
		return requirePermission;
	}

	String getPermission()
	{
		return permission;
	}

	void executeActions(Player player)
	{
		SwitchActions.getInstance().getActionUtil().executeActions(player, actions);
	}
}
