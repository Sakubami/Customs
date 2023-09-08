package xyz.samiki.zollsystem;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;

public class ConfigHelper {
    private static final String path1 = "plugins/Zollsystem/Zollstationen.yml";

    public static void addLocation(Location loc) {
        FileConfiguration config = YamlConfiguration.loadConfiguration(new File(path1));
        for(int i = 0; true; i++) {
            if(!config.contains("stations."+i)) {

                config.set("stations."+i+".id", String.valueOf(i));
                config.set("stations."+i+".location", loc.getBlockX() + "/" + loc.getBlockY() + "/" + loc.getBlockZ() + "/" + loc.getWorld().getName());

                try {
                    config.save(new File(path1));
                }catch (Exception ignored){}
                return;
            }
        }
    }

    public static void deleteLoc(Location loc) {
        FileConfiguration config = YamlConfiguration.loadConfiguration(new File(path1));
        String location = loc.getBlockX()+"/"+loc.getBlockY()+"/"+loc.getBlockZ()+"/"+loc.getWorld().getName();
        for(int i = 0; true; i++) {
            if(config.contains("stations."+i)) {
                if (config.getString("stations."+i+".location").equalsIgnoreCase(location)) {
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
}
