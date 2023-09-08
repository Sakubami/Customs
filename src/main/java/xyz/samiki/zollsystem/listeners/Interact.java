package xyz.samiki.zollsystem.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import xyz.samiki.zollsystem.ConfigHelper;
import xyz.samiki.zollsystem.Inventorys.Inventorys;
import xyz.samiki.zollsystem.controllers.ChatController;

public class Interact implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        if (e.getHand().equals(EquipmentSlot.HAND) && e.getClickedBlock() != null) {
            Location loc = e.getClickedBlock().getLocation();
            Material mat = e.getClickedBlock().getType();

            if (mat.equals(Material.SEA_LANTERN)) {
                if ((e.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
                    for(int i = 0; i < ConfigHelper.loadLocations().size(); i++) {
                        if (loc.equals(ConfigHelper.getLoc(String.valueOf(i)))) {
                            Inventorys.getConfirmation();
                            break;
                        }
                    }
                }
            }
        }
    }
}
