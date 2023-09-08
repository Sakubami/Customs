package xyz.samiki.zollsystem.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import xyz.samiki.zollsystem.ConfigHelper;
import xyz.samiki.zollsystem.controllers.ChatController;

public class ZollCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        Player p = (Player) sender;
        String help = """
                §8==============================
                §6        Benutzung Zollsystem
                §3
                §4 Setup §7- §fStation erstellen
                §4 List §7- §fStationen auflisten
                §c Coming Soon
                §a
                §8==============================
                """;

        try {
            int check = 0;

            if (args[0].equalsIgnoreCase("list")) {
                p.sendMessage("""
                        §8==============================
                        §6           Zoll Locations
                        """);
                p.sendMessage("§6");
                for(String list : ConfigHelper.loadLocations()) {
                    String[] str = list.split("§");
                    String[] parts = str[1].split("/");
                    if (!str[1].contains("0/1000/0/")) {
                        p.sendMessage(ChatController.generic("§f> §b" + parts[0] + "§f.§b" + parts[1] + "§f.§b" + parts[2] + "§f <"));
                    }
                }
                p.sendMessage("§8==============================");
                check = 1;
            }

            if (args[0].equalsIgnoreCase("setup")) {
                Location loc = p.getLocation();
                String location = loc.getBlockX()+"/"+loc.getBlockY()+"/"+loc.getBlockZ()+"/"+loc.getWorld().getName();
                if (!ConfigHelper.checkLocations(location)) {
                    ConfigHelper.addLocation(loc);

                    p.sendMessage(ChatController.succes("Neuer Stand an Position §f> §b" + loc.getBlockX() + "§f.§b" + loc.getBlockY() + "§f.§b" + loc.getBlockZ() + "§f < §a erstellt"));
                } else {
                    p.sendMessage(ChatController.error("Hier ist bereits ein Stand"));
                }
                check = 1;
            }

            if (args[0].equalsIgnoreCase("delete")) {
                Location loc = p.getLocation();
                String location = loc.getBlockX()+"/"+loc.getBlockY()+"/"+loc.getBlockZ()+"/"+loc.getWorld().getName();
                if (ConfigHelper.checkLocations(location)) {
                    ConfigHelper.deleteLoc(loc);
                    p.sendMessage(ChatController.succes("Stand an Position §f> §b" + loc.getBlockX() + "§f.§b" + loc.getBlockY() + "§f.§b" + loc.getBlockZ() + "§f < §a gelöscht"));
                } else {
                    p.sendMessage(ChatController.error("Stand nicht gefunden"));
                }
                check = 1;
            }

            if (check == 0) {
                p.sendMessage(ChatController.error("Dieser Subcommand existiert nicht!"));
            }

        } catch (ArrayIndexOutOfBoundsException e){
            p.sendMessage(help);
        }

        if (sender instanceof ConsoleCommandSender) {
            p.sendMessage(ChatController.error("Bitte nicht in der Konsole ausführen!"));
        }
        return false;
    }
}
