package xyz.samiki.zollsystem.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.net.http.WebSocket;

public class InventoryClick implements Listener {

    @EventHandler
    public void makeButtonsDoCoolStuff(InventoryClickEvent e) {

        ItemStack item = e.getCurrentItem();
        Player p = (Player) e.getWhoClicked();
        if (getTitle(e,"Tageszeitung") || getTitle(e,"Wirtschaft") || getTitle(e,"Admin")) {
            if (item != null) {
                if (item.getItemMeta().getDisplayName().equalsIgnoreCase("§aJa")) {
                    // do stuff
                    Inventory inv = p.getInventory();
                    inv.removeItem(new ItemStack(Material.GOLD_BLOCK ,2));
                    e.setCancelled(true);
                }

                if (item.getItemMeta().getDisplayName().equalsIgnoreCase("§cNein")) {
                    e.getView().close();
                    e.setCancelled(true);
                }
            }
        }
    }

    public static boolean getTitle(InventoryClickEvent e, String s) {
        return e.getView().getTitle().equalsIgnoreCase(s);
    }
}
