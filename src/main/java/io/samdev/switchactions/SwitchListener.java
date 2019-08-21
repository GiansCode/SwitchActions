package io.samdev.switchactions;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import static io.samdev.switchactions.util.Logging.severe;
import static java.util.stream.Collectors.toSet;

public class SwitchListener implements Listener
{
	SwitchListener(SwitchActions plugin)
	{
		this.enabledWorlds = plugin.getConfig().getStringList("enabled_worlds").stream()
			.map(Bukkit::getWorld)
			.filter(Objects::nonNull)
			.collect(toSet());

		ConfigurationSection actionsSection = plugin.getConfig().getConfigurationSection("swap_actions");

		if (actionsSection == null)
		{
			severe("swap_actions section is missing from config");
			return;
		}

		this.actions = SwitchAction.parse(actionsSection);
	}

	private final Set<World> enabledWorlds;
	private List<SwitchAction> actions;

	private final ItemStack airStack = new ItemStack(Material.AIR);

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onSwitch(PlayerSwapHandItemsEvent event)
	{
		Player player = event.getPlayer();

		if (!enabledWorlds.contains(player.getWorld()))
		{
			return;
		}

		ItemStack stack = event.getOffHandItem();

		if (stack == null)
		{
			stack = airStack;
		}

		for (SwitchAction action : actions)
		{
			if (action.isMaterial(stack.getType()))
			{
				handleSwap(player, action, stack, event);
				return;
			}
		}
	}

	private void handleSwap(Player player, SwitchAction action, ItemStack stack, PlayerSwapHandItemsEvent event)
	{
		if (!action.isIgnoreMeta() && !stack.hasItemMeta())
		{
			return;
		}

		if (action.isRequirePermission() && !player.hasPermission(action.getPermission()))
		{
			return;
		}

		event.setCancelled(action.isCancel());
		action.executeActions(player);
	}
}
