package xyz.samiki.zollsystem;

import net.haraxx.coresystem.modules.CoreModule;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import xyz.samiki.zollsystem.commands.ZollCommand;
import xyz.samiki.zollsystem.listeners.Interact;
import xyz.samiki.zollsystem.listeners.InventoryClick;

import java.util.Arrays;

public class ZollSystem extends CoreModule {
    private static ZollSystem plugin;

    public void onEnable(){
        plugin = this;
        BukkitScheduler scheduler = Bukkit.getScheduler();
        ConfigHelper.initiatePlayers();
        scheduler.scheduleSyncRepeatingTask(this, ConfigHelper::initiatePlayers, 1, 20000);

        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents( new Interact(), this);
        pm.registerEvents(new InventoryClick(), this);

        getCommand("zoll").setExecutor(new ZollCommand());
    }

    public void onDisable() {
    }

    public static ZollSystem getPlugin() {
        return plugin;
    }
}