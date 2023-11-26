package xyz.samiki.zollsystem.listeners;

import net.haraxx.coresystem.builder.Chat;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Barrel;
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
                    Location loc = ConfigHelper.getLocByStatus(p);
                    if (p.getInventory().contains(Material.GOLD_BLOCK)) {
                        for (int i = 0; i < p.getInventory().getSize(); i++) {
                            ItemStack invitem = p.getInventory().getItem(i);
                            if (invitem != null && invitem.getType().equals(Material.GOLD_BLOCK)) {
                                p.getInventory().removeItem(new ItemStack(Material.GOLD_BLOCK, 1));
                                break;
                            }
                        }
                        p.playSound(p, Sound.BLOCK_NOTE_BLOCK_HARP, 21 , 1);
                        ConfigHelper.setBusy(loc, true);
                        PlaceController.open(p);

                        Location newloc = new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY() -1, loc.getBlockZ());

                        if (p.getWorld().getBlockAt(newloc).getType().equals(Material.BARREL)) {
                            Barrel chest = (Barrel) e.getWhoClicked().getWorld().getBlockAt(newloc).getState();
                            if (chest.getInventory().getItem(26) == null) {
                                chest.getInventory().addItem(new ItemStack(Material.GOLD_BLOCK));
                            }
                        }

                        Bukkit.getScheduler().runTaskLater(ZollSystem.getPlugin(), bukkitTask -> {

                            PlaceController.close(p);
                            ConfigHelper.setBusy(loc, false);
                            ConfigHelper.setStatus(loc, p, false);
                            ConfigHelper.setPlayerStatus(p, false);
                        },60);
                    } else {
                        p.sendMessage(Chat.format("du hast nicht genügend Gold"));
                    }

                    e.getView().close();
                    e.setCancelled(true);
                }

                if (item.getItemMeta().getDisplayName().equalsIgnoreCase("§cNein")) {
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
        if (e.getView().getTitle().equalsIgnoreCase("§4Kostenpflichtig §8Passieren?")) {
            Location loc = ConfigHelper.getLocByStatus(p);
            if (ConfigHelper.checkBusy(loc)) {
                ConfigHelper.setStatus(loc, p, false);
                Bukkit.getScheduler().runTaskLater(ZollSystem.getPlugin(), bukkitTask -> {
                    ConfigHelper.setPlayerStatus(p, false);
                },60);
            }
        }
    }
}
