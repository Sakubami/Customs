package xyz.samiki.zollsystem.controllers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import xyz.samiki.zollsystem.ConfigHelper;

import static xyz.samiki.zollsystem.ConfigHelper.fixLocation;

public class PlaceController {

    public static void create(Location loc, Player p, int price) {

        BlockFace f = p.getFacing();
        World wrld = loc.getWorld();
        int x = loc.getBlockX();
        int y = loc.getBlockY();
        int z = loc.getBlockZ();

        if (!ConfigHelper.checkLocations(loc, p)) {
            ConfigHelper.addLocation(loc, p, price);

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

        String[] parts = fixLocation(loc, p).split("/");
        BlockFace f = ConfigHelper.getDirection(new Location(Bukkit.getServer().getWorld(parts[3]), Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2])));
        World wrld = Bukkit.getServer().getWorld(parts[3]);
        int x = Integer.parseInt(parts[0]);
        int y = Integer.parseInt(parts[1]);
        int z = Integer.parseInt(parts[2]);

        if (ConfigHelper.checkLocations(loc,p)) {
            ConfigHelper.deleteLoc(loc,p);

            if (f == BlockFace.NORTH) {
                wrld.getBlockAt(x,y,z).setType(Material.AIR);
                wrld.getBlockAt(x,y-1,z).setType(Material.AIR);
                wrld.getBlockAt(x,y-2,z).setType(Material.AIR);

                wrld.getBlockAt(x+1,y,z).setType(Material.AIR);
                wrld.getBlockAt(x+1,y-1,z).setType(Material.AIR);
                wrld.getBlockAt(x+1,y-2,z).setType(Material.AIR);

                wrld.getBlockAt(x+2,y,z).setType(Material.AIR);
                wrld.getBlockAt(x+2,y-1,z).setType(Material.AIR);
                wrld.getBlockAt(x+2,y-2,z).setType(Material.AIR);
            }

            if (f == BlockFace.EAST) {
                wrld.getBlockAt(x,y,z).setType(Material.AIR);
                wrld.getBlockAt(x,y-1,z).setType(Material.AIR);
                wrld.getBlockAt(x,y-2,z).setType(Material.AIR);

                wrld.getBlockAt(x,y,z+1).setType(Material.AIR);
                wrld.getBlockAt(x,y-1,z+1).setType(Material.AIR);
                wrld.getBlockAt(x,y-2,z+1).setType(Material.AIR);

                wrld.getBlockAt(x,y,z+2).setType(Material.AIR);
                wrld.getBlockAt(x,y-1,z+2).setType(Material.AIR);
                wrld.getBlockAt(x,y-2,z+2).setType(Material.AIR);
            }

            if (f == BlockFace.SOUTH) {
                wrld.getBlockAt(x,y,z).setType(Material.AIR);
                wrld.getBlockAt(x,y-1,z).setType(Material.AIR);
                wrld.getBlockAt(x,y-2,z).setType(Material.AIR);

                wrld.getBlockAt(x-1,y,z).setType(Material.AIR);
                wrld.getBlockAt(x-1,y-1,z).setType(Material.AIR);
                wrld.getBlockAt(x-1,y-2,z).setType(Material.AIR);

                wrld.getBlockAt(x-2,y,z).setType(Material.AIR);
                wrld.getBlockAt(x-2,y-1,z).setType(Material.AIR);
                wrld.getBlockAt(x-2,y-2,z).setType(Material.AIR);
            }

            if (f == BlockFace.WEST) {
                wrld.getBlockAt(x,y,z).setType(Material.AIR);
                wrld.getBlockAt(x,y-1,z).setType(Material.AIR);
                wrld.getBlockAt(x,y-2,z).setType(Material.AIR);

                wrld.getBlockAt(x,y,z-1).setType(Material.AIR);
                wrld.getBlockAt(x,y-1,z-1).setType(Material.AIR);
                wrld.getBlockAt(x,y-2,z-1).setType(Material.AIR);

                wrld.getBlockAt(x,y,z-2).setType(Material.AIR);
                wrld.getBlockAt(x,y-1,z-2).setType(Material.AIR);
                wrld.getBlockAt(x,y-2,z-2).setType(Material.AIR);
            }

            p.sendMessage(ChatController.succes("Station an Position §f> §b" + parts[0] + "§f.§b" + parts[1] + "§f.§b" + parts[2] + "§f < §a gelöscht"));
        } else {
            p.sendMessage(ChatController.error("Station nicht gefunden"));
        }
    }

    public static void open(Player p) {

        Location loc = ConfigHelper.getLocByStatus(p);
        BlockFace f = ConfigHelper.getDirection(loc);
        World wrld = loc.getWorld();
        int x = loc.getBlockX();
        int y = loc.getBlockY();
        int z = loc.getBlockZ();

        if (f == BlockFace.NORTH) {
            wrld.getBlockAt(x+1,y,z).setType(Material.AIR);
            wrld.getBlockAt(x+1,y-1,z).setType(Material.AIR);

            wrld.getBlockAt(x+2,y,z).setType(Material.AIR);
            wrld.getBlockAt(x+2,y-1,z).setType(Material.AIR);
        }

        if (f == BlockFace.EAST) {
            wrld.getBlockAt(x,y,z+1).setType(Material.AIR);
            wrld.getBlockAt(x,y-1,z+1).setType(Material.AIR);

            wrld.getBlockAt(x,y,z+2).setType(Material.AIR);
            wrld.getBlockAt(x,y-1,z+2).setType(Material.AIR);
        }

        if (f == BlockFace.SOUTH) {
            wrld.getBlockAt(x-1,y,z).setType(Material.AIR);
            wrld.getBlockAt(x-1,y-1,z).setType(Material.AIR);

            wrld.getBlockAt(x-2,y,z).setType(Material.AIR);
            wrld.getBlockAt(x-2,y-1,z).setType(Material.AIR);
        }

        if (f == BlockFace.WEST) {
            wrld.getBlockAt(x,y,z-1).setType(Material.AIR);
            wrld.getBlockAt(x,y-1,z-1).setType(Material.AIR);

            wrld.getBlockAt(x,y,z-2).setType(Material.AIR);
            wrld.getBlockAt(x,y-1,z-2).setType(Material.AIR);
        }

    }

    public static void close(Player p) {

        Location loc = ConfigHelper.getLocByStatus(p);
        BlockFace f = ConfigHelper.getDirection(loc);
        World wrld = loc.getWorld();
        int x = loc.getBlockX();
        int y = loc.getBlockY();
        int z = loc.getBlockZ();

        if (f == BlockFace.NORTH) {
            wrld.getBlockAt(x+1,y,z).setType(Material.BARRIER);
            wrld.getBlockAt(x+1,y-1,z).setType(Material.BARRIER);

            wrld.getBlockAt(x+2,y,z).setType(Material.BARRIER);
            wrld.getBlockAt(x+2,y-1,z).setType(Material.BARRIER);
        }

        if (f == BlockFace.EAST) {
            wrld.getBlockAt(x,y,z+1).setType(Material.BARRIER);
            wrld.getBlockAt(x,y-1,z+1).setType(Material.BARRIER);

            wrld.getBlockAt(x,y,z+2).setType(Material.BARRIER);
            wrld.getBlockAt(x,y-1,z+2).setType(Material.BARRIER);
        }

        if (f == BlockFace.SOUTH) {
            wrld.getBlockAt(x-1,y,z).setType(Material.BARRIER);
            wrld.getBlockAt(x-1,y-1,z).setType(Material.BARRIER);

            wrld.getBlockAt(x-2,y,z).setType(Material.BARRIER);
            wrld.getBlockAt(x-2,y-1,z).setType(Material.BARRIER);
        }

        if (f == BlockFace.WEST) {
            wrld.getBlockAt(x,y,z-1).setType(Material.BARRIER);
            wrld.getBlockAt(x,y-1,z-1).setType(Material.BARRIER);

            wrld.getBlockAt(x,y,z-2).setType(Material.BARRIER);
            wrld.getBlockAt(x,y-1,z-2).setType(Material.BARRIER);
        }
    }
}
