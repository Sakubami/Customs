package xyz.samiki.zollsystem.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
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
import xyz.samiki.zollsystem.controllers.ChatController;
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
                    double price = ConfigHelper.getPrice(loc);
                    double balance = ZollSystem.getEconomy().getBalance(p);
                    String owner = ConfigHelper.getOwner(loc);

                    if (balance > price || balance == price) {
                        if (owner != null && ZollSystem.getEconomy().hasAccount(owner)) {
                            ZollSystem.getEconomy().withdrawPlayer(p, price);
                            p.sendMessage(ChatController.generic("Dir wurden §c$" + price + " §7Zoll Berechnet"));

                            ZollSystem.getEconomy().depositPlayer(owner, price);

                            Player ownerPlayer = Bukkit.getServer().getPlayer(owner);
                            if (ownerPlayer != null) {
                                ownerPlayer.sendMessage(ChatController.generic("+ §c$" + price + " §7Zoll"));
                            }

                            p.playSound(p, Sound.BLOCK_NOTE_BLOCK_HARP, 21 , 1);
                            ConfigHelper.setBusy(loc, true);
                            PlaceController.open(p);

                            Bukkit.getScheduler().runTaskLater(ZollSystem.getPlugin(), bukkitTask -> {

                                PlaceController.close(p);
                                ConfigHelper.setBusy(loc, false);
                                ConfigHelper.setStatus(loc, p, false);

                            },100);
                        } else {
                            p.sendMessage(ChatController.error("§4FEHLER: §cDiese Pforte hat einen ungültigen Owner! Bitte kontaktiere den Developer §f- §bsaku §f/ §bsakubami§f - §cper Ticket im Discord mit einem Screenshot dieser Nachricht und deinen Coordinaten"));
                        }
                    } else {
                        p.sendMessage(ChatController.error("Du hast nicht genügend Geld"));
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
            }
        }
    }
}
