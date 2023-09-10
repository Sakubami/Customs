package xyz.samiki.zollsystem.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.samiki.zollsystem.ConfigHelper;
import xyz.samiki.zollsystem.controllers.PlaceController;

import java.net.http.WebSocket;

public class InventoryClick implements Listener {

    @EventHandler
    public void makeButtonsDoCoolStuff(InventoryClickEvent e) {

        ItemStack item = e.getCurrentItem();
        Player p = (Player) e.getWhoClicked();
        if (e.getView().getTitle().equalsIgnoreCase("§4Kostenpflichtig §cPassieren?")) {
            if (item != null) {
                if (item.getItemMeta().getDisplayName().equalsIgnoreCase("§aJa")) {

                    if (ConfigHelper.getLocByStatus(p) != null) {
                        ConfigHelper.setStatus(ConfigHelper.getLocByStatus(p), p, false);
                    }

                    // take money
                    e.getView().close();
                    e.setCancelled(true);
                }

                if (item.getItemMeta().getDisplayName().equalsIgnoreCase("§cNein")) {

                    if (ConfigHelper.getLocByStatus(p) != null) {
                        ConfigHelper.setStatus(ConfigHelper.getLocByStatus(p), p, false);
                    }

                    e.getView().close();
                    e.setCancelled(true);
                }

                if (item.getItemMeta().getDisplayName().equalsIgnoreCase("§0")) {
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void makeButtonsDoStuff(InventoryCloseEvent e) {
        Player p = (Player) e.getPlayer();
        if (e.getView().getTitle().equalsIgnoreCase("§4Kostenpflichtig §cPassieren?")) {
            if (ConfigHelper.getLocByStatus(p) != null) {
                ConfigHelper.setStatus(ConfigHelper.getLocByStatus(p), p, false);
            }
        }
    }

}
