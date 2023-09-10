package xyz.samiki.zollsystem.controllers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import xyz.samiki.zollsystem.ConfigHelper;

import static xyz.samiki.zollsystem.ConfigHelper.fixLocation;

public class PlaceController {

    public static void create(Location loc, Player p) {

        BlockFace f = p.getFacing();
        World wrld = loc.getWorld();
        int x = loc.getBlockX();
        int y = loc.getBlockY();
        int z = loc.getBlockZ();

        if (!ConfigHelper.checkLocations(loc,p)) {
            ConfigHelper.addLocation(loc,p);

            if (f == BlockFace.NORTH) {
                wrld.getBlockAt(x,y,z-1).setType(Material.QUARTZ_BRICKS);
                wrld.getBlockAt(x,y+1,z-1).setType(Material.TARGET);

                wrld.getBlockAt(x+1,y-1,z-1).setType(Material.SMOOTH_QUARTZ);
                wrld.getBlockAt(x+1,y,z-1).setType(Material.BARRIER);
                wrld.getBlockAt(x+1,y+1,z-1).setType(Material.BARRIER);

                wrld.getBlockAt(x+2,y-1,z-1).setType(Material.SMOOTH_QUARTZ);
                wrld.getBlockAt(x+2,y,z-1).setType(Material.BARRIER);
                wrld.getBlockAt(x+2,y+1,z-1).setType(Material.BARRIER);
            }

            if (f == BlockFace.EAST) {
                wrld.getBlockAt(x+1,y,z).setType(Material.QUARTZ_BRICKS);
                wrld.getBlockAt(x+1,y+1,z).setType(Material.TARGET);

                wrld.getBlockAt(x+1,y-1,z+1).setType(Material.SMOOTH_QUARTZ);
                wrld.getBlockAt(x+1,y,z+1).setType(Material.BARRIER);
                wrld.getBlockAt(x+1,y+1,z+1).setType(Material.BARRIER);

                wrld.getBlockAt(x+1,y-1,z+2).setType(Material.SMOOTH_QUARTZ);
                wrld.getBlockAt(x+1,y,z+2).setType(Material.BARRIER);
                wrld.getBlockAt(x+1,y+1,z+2).setType(Material.BARRIER);
            }

            if (f == BlockFace.SOUTH) {
                wrld.getBlockAt(x,y,z+1).setType(Material.QUARTZ_BRICKS);
                wrld.getBlockAt(x,y+1,z+1).setType(Material.TARGET);

                wrld.getBlockAt(x-1,y-1,z+1).setType(Material.SMOOTH_QUARTZ);
                wrld.getBlockAt(x-1,y,z+1).setType(Material.BARRIER);
                wrld.getBlockAt(x-1,y+1,z+1).setType(Material.BARRIER);

                wrld.getBlockAt(x-2,y-1,z+1).setType(Material.SMOOTH_QUARTZ);
                wrld.getBlockAt(x-2,y,z+1).setType(Material.BARRIER);
                wrld.getBlockAt(x-2,y+1,z+1).setType(Material.BARRIER);
            }

            if (f == BlockFace.WEST) {
                wrld.getBlockAt(x-1,y,z).setType(Material.QUARTZ_BRICKS);
                wrld.getBlockAt(x-1,y+1,z).setType(Material.TARGET);

                wrld.getBlockAt(x-1,y-1,z-1).setType(Material.SMOOTH_QUARTZ);
                wrld.getBlockAt(x-1,y,z-1).setType(Material.BARRIER);
                wrld.getBlockAt(x-1,y+1,z-1).setType(Material.BARRIER);

                wrld.getBlockAt(x-1,y-1,z-2).setType(Material.SMOOTH_QUARTZ);
                wrld.getBlockAt(x-1,y,z-2).setType(Material.BARRIER);
                wrld.getBlockAt(x-1,y+1,z-2).setType(Material.BARRIER);
            }

            String[] parts = fixLocation(loc,p).split("/");
            p.sendMessage(ChatController.succes("Neue Station an Position §f> §b" + parts[0] + "§f.§b" + parts[1] + "§f.§b" + parts[2] + "§f < §a erstellt"));
        } else {
            p.sendMessage(ChatController.error("Hier ist bereits eine Station"));
        }
    }

    public static void delete(Location loc, Player p) {

        BlockFace f = p.getFacing();
        World wrld = loc.getWorld();
        int x = loc.getBlockX();
        int y = loc.getBlockY();
        int z = loc.getBlockZ();

        String[] parts = fixLocation(loc,p).split("/");
        if (ConfigHelper.checkLocations(loc,p)) {
            ConfigHelper.deleteLoc(loc,p);
            p.sendMessage(ChatController.succes("Station an Position §f> §b" + parts[0] + "§f.§b" + parts[1] + "§f.§b" + parts[2] + "§f < §a gelöscht"));
        } else {
            p.sendMessage(ChatController.error("Station nicht gefunden"));
        }
    }

    public static void open(Location loc, Player p) {

    }

    public static void close(Location loc, Player p) {

    }
}
