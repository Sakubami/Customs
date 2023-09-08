package xyz.samiki.zollsystem.controllers;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import xyz.samiki.zollsystem.ConfigHelper;

public class PlaceController {

    public void create(Location loc, Player p) {

        BlockFace f = p.getFacing();
        World wrld = loc.getWorld();
        int x = loc.getBlockX();
        int y = loc.getBlockY();
        int z = loc.getBlockZ() -1;
        ConfigHelper.addLocation(loc);

        if (f == BlockFace.NORTH) {
            wrld.getBlockAt(x-1,y-1,z).setType(Material.WHITE_STAINED_GLASS);
            wrld.getBlockAt(x-1,y,z).setType(Material.CHEST);
            wrld.getBlockAt(x-1,y+1,z).setType(Material.WHITE_STAINED_GLASS);

            wrld.getBlockAt(x,y,z).setType(Material.QUARTZ_BRICKS);
            wrld.getBlockAt(x,y+1,z).setType(Material.TARGET);

            wrld.getBlockAt(x+1,y-1,z).setType(Material.BARRIER);
            wrld.getBlockAt(x+1,y,z).setType(Material.BARRIER);
            wrld.getBlockAt(x+1,y+1,z).setType(Material.BARRIER);

            wrld.getBlockAt(x+2,y-1,z).setType(Material.BARRIER);
            wrld.getBlockAt(x+2,y,z).setType(Material.BARRIER);
            wrld.getBlockAt(x+2,y+1,z).setType(Material.BARRIER);
        }

    }
}
