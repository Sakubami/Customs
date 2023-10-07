package xyz.samiki.zollsystem.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.checkerframework.checker.nullness.qual.NonNull;
import xyz.samiki.zollsystem.ConfigHelper;
import xyz.samiki.zollsystem.Inventorys.Inventorys;
import xyz.samiki.zollsystem.controllers.ChatController;

import java.util.Objects;

public class Interact implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        if (Objects.equals(e.getHand(), EquipmentSlot.HAND) && e.getClickedBlock() != null) {
            Location loc = e.getClickedBlock().getLocation();
            Material mat = e.getClickedBlock().getType();
            if (mat.equals(Material.TARGET) && e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && ConfigHelper.checkCLick(loc)) {
                if (ConfigHelper.isEnabled()) {
                    if (ConfigHelper.checkBusy(loc)) {
                        if (!ConfigHelper.checkPlayer(p)) {
                            if (ConfigHelper.checkStatus(loc)) {
                                e.setCancelled(true);
                                ConfigHelper.setStatus(loc, p, true);
                                p.openInventory(Inventorys.getConfirmation(ConfigHelper.getPrice(loc)));
                                ConfigHelper.setPlayerStatus(p, true);
                            } else p.sendMessage(ChatController.error("Jemand benutzt dieses Tor bereits"));
                        } else  p.sendMessage(ChatController.error("Bitte warte einen Moment"));
                    } else  p.sendMessage(ChatController.error("Dieses Tor ist bereits offen"));
                } else  p.sendMessage(ChatController.error("§4Diese Pforte wurde vorrübergehend geschlossen!"));
            }
        }
    }
}
