package xyz.samiki.zollsystem;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;

public class ConfigHelper {
    private static final String path1 = "plugins/Zollsystem/Zollstationen.yml";

    public static void addLocation(Location loc, Player p) {

        String[] parts = fixLocation(loc,p).split("/");

        FileConfiguration config = YamlConfiguration.loadConfiguration(new File(path1));
        for(int i = 0; true; i++) {
            if(!config.contains("stations."+i)) {

                config.set("stations."+i+".id", String.valueOf(i));
                config.set("stations."+i+".location", parts[0]+"/"+parts[1]+"/"+parts[2]+"/"+parts[3]);

                try {
                    config.save(new File(path1));
                }catch (Exception ignored){}
                return;
            }
        }
    }

    public static void deleteLoc(Location loc, Player p) {

        String fixedLoc = fixLocation(loc,p);

        FileConfiguration config = YamlConfiguration.loadConfiguration(new File(path1));
        for(int i = 0; true; i++) {
            if(config.contains("stations."+i)) {
                if (config.getString("stations."+i+".location").equalsIgnoreCase(fixedLoc)) {
                    config.set("stations."+i+".location", 0 + "/" + 1000 + "/" + 0 + "/" + loc.getWorld().getName());
                    try {
                        config.save(new File(path1));
                    }catch (Exception ignored){}
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
            String line = name+"§"+location;
            list.add(line);
        }
    }

    public static boolean checkLocations(String loc) {
        for(String list : loadLocations()) {
            String[] str = list.split("§");
            if (str[1].equalsIgnoreCase(loc)) {
                return true;
            }
        }
        return false;
    }

    public static Location getLoc(String id) {
        for(String list : loadLocations()) {
            String[] str = list.split("§");
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
