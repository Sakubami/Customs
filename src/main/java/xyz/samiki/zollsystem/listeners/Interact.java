package xyz.samiki.zollsystem.listeners;

import net.haraxx.coresystem.builder.Chat;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import xyz.samiki.zollsystem.ConfigHelper;
import xyz.samiki.zollsystem.Inventorys.Inventorys;

import java.util.Objects;

public class Interact implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        if (Objects.equals(e.getHand(), EquipmentSlot.HAND) && e.getClickedBlock() != null) {
            Location loc = e.getClickedBlock().getLocation();
            Material mat = e.getClickedBlock().getType();
            if (mat.equals(Material.TARGET) && e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if (ConfigHelper.checkLocation(loc)) {
                    if (ConfigHelper.isEnabled()) {
                        if (ConfigHelper.checkBusy(loc)) {
                            if (!ConfigHelper.checkPlayer(p)) {
                                if (ConfigHelper.checkStatus(loc)) {
                                    ConfigHelper.setStatus(loc, p, true);
                                    p.openInventory(Inventorys.getConfirmation(1));
                                    ConfigHelper.setPlayerStatus(p, true);
                                } else p.sendMessage(Chat.format("Jemand benutzt dieses Tor bereits"));
                                e.setCancelled(true);
                            } else  p.sendMessage(Chat.format("Bitte warte einen Moment"));
                        } else  p.sendMessage(Chat.format("Dieses Tor ist bereits offen"));
                    } else  p.sendMessage(Chat.format("§4Diese Pforte wurde vorrübergehend geschlossen!"));
                }
            }
        }
    }
}
