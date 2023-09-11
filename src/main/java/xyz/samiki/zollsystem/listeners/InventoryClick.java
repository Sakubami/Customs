package xyz.samiki.zollsystem.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import xyz.samiki.zollsystem.ConfigHelper;
import xyz.samiki.zollsystem.ZollSystem;
import xyz.samiki.zollsystem.controllers.PlaceController;

public class InventoryClick implements Listener {
    private int check = 0;

    @EventHandler
    public void makeButtonsDoCoolStuff(InventoryClickEvent e) {

        ItemStack item = e.getCurrentItem();
        Player p = (Player) e.getWhoClicked();

        if (e.getView().getTitle().equalsIgnoreCase("§4Kostenpflichtig §8Passieren?")) {

            if (item != null) {
                if (item.getItemMeta().getDisplayName().equalsIgnoreCase("§aJa")) {

                    ConfigHelper.setBusy(ConfigHelper.getLocByStatus(p), true);
                    PlaceController.open(p);
                    p.playSound(p, Sound.BLOCK_NOTE_BLOCK_HARP, 21 , 1);
                    Bukkit.getScheduler().runTaskLater(ZollSystem.getPlugin(), bukkitTask -> {

                        PlaceController.close(p);
                        ConfigHelper.setBusy(ConfigHelper.getLocByStatus(p), false);
                        ConfigHelper.setStatus(ConfigHelper.getLocByStatus(p), p, false);

                    },100);

                    check = 1;

                    // take money
                    e.getView().close();
                    e.setCancelled(true);
                }

                if (item.getItemMeta().getDisplayName().equalsIgnoreCase("§cNein")) {

                    ConfigHelper.setStatus(ConfigHelper.getLocByStatus(p), p, false);

                    check = 1;

                    e.getView().close();
                    e.setCancelled(true);
                }

                if (item.getItemMeta().getDisplayName().equalsIgnoreCase("§0")) {
                    e.setCancelled(true);
                }

                if (item.getType().equals(Material.GOLD_NUGGET)) {
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void makeButtonsDoStuff(InventoryCloseEvent e) {
        Player p = (Player) e.getPlayer();
        if (check == 0) {
            if (e.getView().getTitle().equalsIgnoreCase("§4Kostenpflichtig §8Passieren?")) {
                ConfigHelper.setStatus(ConfigHelper.getLocByStatus(p), p, false);
            }
        }
    }
}
