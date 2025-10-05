package jp.kaiz.weatherfucker;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class WeatherFuckerCore extends JavaPlugin implements Listener {
    private static final String name = ChatColor.GRAY + "[☀" + ChatColor.AQUA + "Weather" + ChatColor.RED + "Fucker" + ChatColor.GRAY + "☀] " + ChatColor.RESET;

    private boolean isEnabled = true;

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);
        this.getServer().getConsoleSender().sendMessage(name + "WeatherFucker is enabled.");

        this.getCommand("weatherfucker").setExecutor(this);
    }

    @Override
    public void onDisable() {
        this.getServer().getConsoleSender().sendMessage(name + "WeatherFucker is disabled.");
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event) {
        if (this.isEnabled && event.toWeatherState()) {
            this.getServer().getConsoleSender().sendMessage(name + "Change to \uD83C\uDF27\uD83C\uDF27rain\uD83C\uDF27\uD83C\uDF27 was blocked.");
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onThunderChange(ThunderChangeEvent event) {
        if (this.isEnabled && event.toThunderState()) {
            this.getServer().getConsoleSender().sendMessage(name + "Change to \uD83C\uDF29\uD83C\uDF29thunder\uD83C\uDF29\uD83C\uDF29 was blocked.");
            event.setCancelled(true);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("weatherfucker")) {
            if (args.length != 1) {
                return false;
            }

            switch (args[0].toLowerCase()) {
                case "enable":
                    if (this.isEnabled) {
                        sender.sendMessage(name + "WeatherFucker is already enabled.");
                    } else {
                        this.isEnabled = true;
                        sender.sendMessage(name + "WeatherFucker has been enabled.");

                        this.getServer().getWorlds().forEach(world -> {
                            if (world.hasStorm()) {
                                world.setStorm(false);
                            }
                            if (world.isThundering()) {
                                world.setThundering(false);
                            }
                        });
                    }
                    return true;
                case "disable":
                    if (!this.isEnabled) {
                        sender.sendMessage(name + "WeatherFucker is already disabled.");
                    } else {
                        this.isEnabled = false;
                        sender.sendMessage(name + "WeatherFucker has been disabled.");
                    }
                    return true;
                default:
                    break;
            }
        }
        return false;
    }

    private static final List<String> SUBS = Arrays.asList("enable", "disable");

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            List<String> completions = new ArrayList<>();
            StringUtil.copyPartialMatches(args[0], SUBS, completions);
            Collections.sort(completions);
            return completions;
        }
        return Collections.emptyList();
    }
}
