package xyz.samiki.zollsystem.Inventorys;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import xyz.samiki.zollsystem.controllers.ItemController;

public class Inventorys {

    public static Inventory getConfirmation() {
        Inventory inv = InventoryController.inventoryPrepare(36,"§4Kostenpflichtig §cPassieren?");
        inv.setItem(11, ItemController.getItem(Material.LIME_WOOL,"§aJa"));
        inv.setItem(15, ItemController.getItem(Material.RED_WOOL,"§cNein"));
        return inv;
    }
}
