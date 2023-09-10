package xyz.samiki.zollsystem;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockFadeEvent;

import java.io.File;
import java.util.ArrayList;

public class ConfigHelper {
    private static final String path1 = "plugins/Zollsystem/Zollstationen.yml";

    public static void addLocation(Location loc, Player p) {
        FileConfiguration config = YamlConfiguration.loadConfiguration(new File(path1));
        for(int i = 0; true; i++) {
            if(!config.contains("stations."+i)) {

                config.set("stations."+i+".id", String.valueOf(i));
                config.set("stations."+i+".location", fixLocation(loc,p));
                config.set("stations."+i+".direction", p.getFacing().toString());
                config.set("stations."+i+".status", false);

                try {
                    config.save(new File(path1));
                } catch (Exception ignored) {}
                return;
            }
        }
    }

    public static void deleteLoc(Location loc, Player p) {
        FileConfiguration config = YamlConfiguration.loadConfiguration(new File(path1));
        for(int i = 0; true; i++) {
            if(config.contains("stations."+i)) {
                if (config.getString("stations."+i+".location").equalsIgnoreCase(fixLocation(loc,p))) {

                    config.set("stations."+i+".location", 0 + "/" + 1000 + "/" + 0 + "/" + loc.getWorld().getName());

                    try {
                        config.save(new File(path1));
                    } catch (Exception ignored) {}
                    return;
                }
            }
        }
    }

    public static void setStatus(Location loc, Player p, boolean status ) {
        FileConfiguration config = YamlConfiguration.loadConfiguration(new File(path1));
        for(int i = 0; true; i++) {
            if(config.contains("stations."+i)) {
                if (config.getString("stations."+i+".location").equalsIgnoreCase(loc.getBlockX() + "/" + loc.getBlockY() + "/" + loc.getBlockZ() + "/" + loc.getWorld().getName())) {

                    if (status) {
                        config.set("stations."+i+".status", "CLICKED-" + p.getUniqueId());
                    } else {
                        config.set("stations."+i+".status", false);
                    }

                    try {
                        config.save(new File(path1));
                    } catch (Exception ignored) {}
                    return;
                }
            }
        }
    }

    public static ArrayList<String> loadLocations() {
        ArrayList<String> list = new ArrayList<>();
        FileConfiguration config = YamlConfiguration.loadConfiguration(new File(path1));

        for (int i = 0; true; i++) {
            if (!config.contains("stations." + i))
                return list;

            String name = config.getString("stations."+i+".id");
            String location = config.getString("stations."+i+".location");
            String dir = config.getString("stations."+i+".direction");
            String status = config.getString("stations."+i+".status");
            String line = name+"%"+location+"%"+dir+"%"+status;
            list.add(line);
        }
    }

    public static boolean checkLocations(Location loc, Player p) {
        for(String list : loadLocations()) {
            String[] str = list.split("%");
            if (str[1].equalsIgnoreCase(fixLocation(loc,p))) {
                return true;
            }
        }
        return false;
    }

    public static Location getLocByStatus(Player p) {
        for(String list : loadLocations()) {
            String[] str = list.split("%");
            if (str[3].contains("CLICKED-" + p.getUniqueId())) {
                String[] parts = str[1].split("/");
                return new Location(Bukkit.getServer().getWorld(parts[3]), Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
            }
        }
        return null;
    }

    public static BlockFace getDirection(Player p) {
        for(String list : loadLocations()) {
            String[] str = list.split("%");
            if (str[3].equalsIgnoreCase("CLICKED-" + p.getUniqueId())) {
                return BlockFace.valueOf(str[2]);
            }
        }
        return null;
    }

    public static Location getLoc(String id) {
        for(String list : loadLocations()) {
            String[] str = list.split("%");
            if (str[0].equalsIgnoreCase(id)) {
                String[] parts = str[1].split("/");
                return new Location(Bukkit.getServer().getWorld(parts[3]), Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
            }
        }
        return null;
    }

    public static String fixLocation(Location loc, Player p) {
        int x = loc.getBlockX();
        int y = loc.getBlockY() +1;
        int z = loc.getBlockZ();

        if (p.getFacing().equals(BlockFace.NORTH)) {
            z--;
        }
        if (p.getFacing().equals(BlockFace.EAST)) {
            x++;
        }
        if (p.getFacing().equals(BlockFace.SOUTH)) {
            z++;
        }
        if (p.getFacing().equals(BlockFace.WEST)) {
            x--;
        }
        return x+"/"+y+"/"+z+"/"+loc.getWorld().getName();
    }
}
