package xyz.samiki.zollsystem.Inventorys;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import xyz.samiki.zollsystem.controllers.ItemController;

public class Inventorys {

    public static Inventory getConfirmation(int price) {
        Inventory inv = InventoryController.inventoryPrepare(36,"§4Kostenpflichtig §7Passieren?");
        inv.setItem(11, ItemController.getItem(Material.LIME_WOOL,"§aJa"));
        inv.setItem(15, ItemController.getItem(Material.RED_WOOL,"§cNein"));
        inv.setItem(13, ItemController.getItem(Material.GOLD_NUGGET,"§6" + price + "$"));
        return inv;
    }
}
