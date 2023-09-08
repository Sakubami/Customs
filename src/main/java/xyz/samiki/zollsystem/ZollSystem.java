package xyz.samiki.zollsystem;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.samiki.zollsystem.commands.ZollCommand;
import xyz.samiki.zollsystem.listeners.Interact;
import xyz.samiki.zollsystem.listeners.InventoryClick;

public class ZollSystem extends JavaPlugin {
    private static ZollSystem plugin;

    public void onEnable(){
        plugin = this;

        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents( new Interact(), this);
        pm.registerEvents(new InventoryClick(), this);

        getCommand("zoll").setExecutor(new ZollCommand());
    }

    public void onDisable(){

    }

    public static ZollSystem getPlugin() {
        return plugin;
    }
}