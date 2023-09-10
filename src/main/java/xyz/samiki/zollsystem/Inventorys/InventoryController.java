package xyz.samiki.zollsystem.Inventorys;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import xyz.samiki.zollsystem.controllers.ItemController;

public class InventoryController {

    public static org.bukkit.inventory.Inventory inventoryPrepare(int size, String title) {
        Inventory inv = Bukkit.createInventory(null, size, title);

        for (int i = 0; i < inv.getSize(); i++) {
            inv.setItem(i, ItemController.getItem(Material.LIGHT_GRAY_STAINED_GLASS_PANE,"§0"));
        }

        for (int i = inv.getSize()-9; i < inv.getSize(); i++) {
            inv.setItem(i, ItemController.getItem(Material.GRAY_STAINED_GLASS_PANE,"§0"));
        }
        return inv;
    }
}
