package xyz.samiki.zollsystem.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import xyz.samiki.zollsystem.ConfigHelper;
import xyz.samiki.zollsystem.controllers.ChatController;
import xyz.samiki.zollsystem.controllers.PlaceController;

public class ZollCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        Player p = (Player) sender;
        Location loc = p.getLocation();
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
                        §6           Zoll Stationen
                        """);
                p.sendMessage("§6");
                for(String list : ConfigHelper.loadLocations()) {
                    String[] str = list.split("§");
                    String[] parts = str[1].split("/");
                    if (!str[1].contains("0/1000/0/")) {
                        p.sendMessage(ChatController.generic("§f> §b" + parts[0] + "§f . §b" + parts[1] + "§f . §b" + parts[2] + "§f <"));
                    }
                }
                p.sendMessage("§8==============================");
                check = 1;
            }

            if (args[0].equalsIgnoreCase("setup")) {
                PlaceController.create(loc,p);
                check = 1;
            }

            if (args[0].equalsIgnoreCase("delete")) {
                PlaceController.delete(loc,p);
                check = 1;
            }

            if (check == 0) {
                p.sendMessage(ChatController.error("Dieser Subcommand existiert nicht!"));
            }

        } catch (ArrayIndexOutOfBoundsException e){
            p.sendMessage(help);
        } catch (ClassCastException e) {
            System.out.println("Nicht in der Konsole ausführbar");
        }

        return false;
    }
}
