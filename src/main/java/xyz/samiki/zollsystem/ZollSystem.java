package xyz.samiki.zollsystem;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.samiki.zollsystem.commands.ZollCommand;
import xyz.samiki.zollsystem.listeners.Interact;
import xyz.samiki.zollsystem.listeners.InventoryClick;

import java.util.Arrays;

public class ZollSystem extends JavaPlugin {
    private static Economy econ = null;
    private static ZollSystem plugin;

    public void onEnable(){
        plugin = this;
        ConfigHelper.initiatePlayers();

        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents( new Interact(), this);
        pm.registerEvents(new InventoryClick(), this);

        getCommand("zoll").setExecutor(new ZollCommand());

        if (!setupEconomy() ) {
            getServer().getPluginManager().disablePlugin(this);
            System.out.println("DISABLED DUE TO NO VAULT FOUND");
        }
    }

    public void onDisable(){

    }
    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return true;
    }

    public static ZollSystem getPlugin() {
        return plugin;
    }
    public static Economy getEconomy() { return econ; }

    }