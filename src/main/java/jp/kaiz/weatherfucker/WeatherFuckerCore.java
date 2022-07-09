package jp.kaiz.weatherfucker;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class WeatherFuckerCore extends JavaPlugin implements Listener {
    private static final String name = ChatColor.GRAY + "[☀" + ChatColor.AQUA + "Weather" + ChatColor.RED + "Fucker" + ChatColor.GRAY + "☀] " + ChatColor.RESET;

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);
        this.getServer().getConsoleSender().sendMessage(name + "WeatherFucker is enabled.");
    }

    @Override
    public void onDisable() {
        this.getServer().getConsoleSender().sendMessage(name + "WeatherFucker is disabled.");
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event) {
        if (event.toWeatherState()) {
            this.getServer().getConsoleSender().sendMessage(name + "Change to \uD83C\uDF27\uD83C\uDF27rain\uD83C\uDF27\uD83C\uDF27 was blocked.");
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onThunderChange(ThunderChangeEvent event) {
        if (event.toThunderState()) {
            this.getServer().getConsoleSender().sendMessage(name + "Change to \uD83C\uDF29\uD83C\uDF29thunder\uD83C\uDF29\uD83C\uDF29 was blocked.");
            event.setCancelled(true);
        }
    }
}
