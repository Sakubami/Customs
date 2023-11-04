package xyz.samiki.zollsystem;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import xyz.samiki.zollsystem.controllers.ChatController;

import java.io.File;
import java.util.*;

public class ConfigHelper {
    private static final Set<OfflinePlayer> players = new HashSet<>();
    private static final String path1 = "plugins/Zollsystem/Zollstationen.yml";
    private static final String path2 = "plugins/Zollsystem/Zollsystem.yml";
    private static final String path3 = "plugins/Zollsystem/Spieler.yml";

    public static void addLocation(Location loc, Player p) {
        FileConfiguration config = YamlConfiguration.loadConfiguration(new File(path1));
        for(int i = 0; true; i++) {
            if(!config.contains("stations."+i)) {

                config.set("stations."+i+".id", String.valueOf(i));
                config.set("stations."+i+".location", fixLocation(loc,p));
                config.set("stations."+i+".direction", p.getFacing().toString());
                config.set("stations."+i+".status", false);
                config.set("stations."+i+".busy", false);

                try {
                    config.save(new File(path1));
                } catch (Exception ignored) {}
                return;
            }
        }
    }

    public static void initiatePlayers() {
        FileConfiguration config = YamlConfiguration.loadConfiguration(new File(path3));

        players.addAll(Arrays.asList(Bukkit.getServer().getOfflinePlayers()));
        players.addAll(Bukkit.getServer().getOnlinePlayers());

        int i = 0;
        for(OfflinePlayer player : players) {
            if(!config.contains("players."+i)) {

                config.set("players."+i+".id", player.getUniqueId().toString().replace("!!java.util.UUID",""));
                config.set("players."+i+".status", false);
            }
            i++;
        }

        try {
            config.save(new File(path3));
        } catch (Exception ignored) {}

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

    public static void setStatus(Location loc, Player p, boolean status) {
        FileConfiguration config = YamlConfiguration.loadConfiguration(new File(path1));
        for(int i = 0; true; i++) {
            if(config.contains("stations."+i)) {
                if (config.getString("stations."+i+".location").equalsIgnoreCase(loc.getBlockX() + "/" + loc.getBlockY() + "/" + loc.getBlockZ() + "/" + loc.getWorld().getName())) {

                    if (status) {
                        config.set("stations."+i+".status", p.getDisplayName());
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

    public static void setPlayerStatus(Player p, boolean status) {
        FileConfiguration config = YamlConfiguration.loadConfiguration(new File(path3));
        int i = 0;
        for(String list : loadPlayers()) {
            String[] str = list.split("%");
            if (str[0].contains(p.getUniqueId().toString())) {
                if (status) {
                    config.set("players."+i+".status", true);
                } else {
                    config.set("players."+i+".status", false);
                }

                try {
                    config.save(new File(path3));
                } catch (Exception ignored) {}
                return;
            }
            i++;
        }
    }

    public static void setBusy(Location loc, boolean busy) {
        FileConfiguration config = YamlConfiguration.loadConfiguration(new File(path1));
        for(int i = 0; true; i++) {
            if(config.contains("stations."+i)) {
                if (config.getString("stations."+i+".location").equalsIgnoreCase(loc.getBlockX() + "/" + loc.getBlockY() + "/" + loc.getBlockZ() + "/" + loc.getWorld().getName())) {

                    if (busy) {
                        config.set("stations."+i+".busy", true);
                    } else {
                        config.set("stations."+i+".busy", false);
                    }

                    try {
                        config.save(new File(path1));
                    } catch (Exception ignored) {}
                    return;
                }
            }
        }
    }

    public static void setEnabled(boolean status) {
        FileConfiguration config = YamlConfiguration.loadConfiguration(new File(path2));
        config.set("enabled", status);
        try {
            config.save(new File(path2));
        } catch (Exception ignored) {}
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
            String busy = config.getString("stations."+i+".busy");
            String line = name+"%"+location+"%"+dir+"%"+status+"%"+busy;
            list.add(line);
        }
    }

    public static ArrayList<String> loadPlayers() {
        ArrayList<String> list = new ArrayList<>();
        FileConfiguration config = YamlConfiguration.loadConfiguration(new File(path3));

        for (int i = 0; true; i++) {

            if (!config.contains("players."+i)) return list;

            String name = config.getString("players."+i+".id");
            String status = config.getString("players."+i+".status");

            String line = name+"%"+status;
            list.add(line);
        }
    }

    public static boolean loadEnabled() {
        FileConfiguration config = YamlConfiguration.loadConfiguration(new File(path2));
        return Boolean.parseBoolean(config.getString("enabled"));
    }

    public static boolean isEnabled() {
        return loadEnabled();
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

    public static boolean checkLocation(Location loc) {
        for(String list : loadLocations()) {
            String[] str = list.split("%");
            if (str[1].equalsIgnoreCase(loc.getBlockX() + "/" + loc.getBlockY() + "/" + loc.getBlockZ() + "/" + loc.getWorld().getName())) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkCLick(Location loc) {
        for(String list : loadLocations()) {
            String[] str = list.split("%");
            if (str[1].equalsIgnoreCase(convertLocation(loc))) {
                return true;
            }
        }
        return false;
    }

    public static double getPrice(Location loc) {
        for(String list : loadLocations()) {
            String[] str = list.split("%");
            if (str[1].equalsIgnoreCase(loc.getBlockX() + "/" + loc.getBlockY() + "/" + loc.getBlockZ() + "/" + loc.getWorld().getName())) {
                return Double.parseDouble(str[5]);
            }
        }
        return 0;
    }

    public static boolean checkPlayer(Player p) {
        for(String list : loadPlayers()) {
            String[] str = list.split("%");
            if (str[0].contains(p.getUniqueId().toString())) {
                return Boolean.parseBoolean(str[1]);
            }
        }
        return true;
    }

    public static String getOwner(Location loc) {
        for(String list : loadLocations()) {
            String[] str = list.split("%");
            if (str[1].equalsIgnoreCase(loc.getBlockX() + "/" + loc.getBlockY() + "/" + loc.getBlockZ() + "/" + loc.getWorld().getName())) {
                return str[6];
            }
        }
        return null;
    }

    public static boolean checkBusy(Location loc) {
        for(String list : loadLocations()) {
            String[] str = list.split("%");
            if (str[1].equalsIgnoreCase(loc.getBlockX() + "/" + loc.getBlockY() + "/" + loc.getBlockZ() + "/" + loc.getWorld().getName())) {
                if (str[4].equalsIgnoreCase("true")) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean checkStatus(Location loc) {
        for(String list : loadLocations()) {
            String[] str = list.split("%");
            if (str[1].equalsIgnoreCase(loc.getBlockX() + "/" + loc.getBlockY() + "/" + loc.getBlockZ() + "/" + loc.getWorld().getName())) {
                if (str[3].equalsIgnoreCase("false")) {
                    return true;
                }
            }
        }
        return false;
    }

    public static Location getLocByStatus(Player p) {
        for(String list : loadLocations()) {
            String[] str = list.split("%");
            if (str[3].equalsIgnoreCase(p.getDisplayName())) {
                String[] parts = str[1].split("/");
                return new Location(Bukkit.getServer().getWorld(parts[3]), Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
            }
        }
        return null;
    }

    public static BlockFace getDirection(Location loc) {
        for(String list : loadLocations()) {
            String[] str = list.split("%");
            if (str[1].equalsIgnoreCase(loc.getBlockX() + "/" + loc.getBlockY() + "/" + loc.getBlockZ() + "/" + loc.getWorld().getName())) {
                return BlockFace.valueOf(str[2]);
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

    public static String convertLocation(Location loc) {
        int x = loc.getBlockX();
        int y = loc.getBlockY();
        int z = loc.getBlockZ();
        return x+"/"+y+"/"+z+"/"+loc.getWorld().getName();
    }
}
