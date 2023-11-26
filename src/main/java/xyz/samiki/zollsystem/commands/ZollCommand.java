package xyz.samiki.zollsystem.commands;

import net.haraxx.coresystem.builder.Chat;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
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
                §4 Create §7- §fStation erstellen
                §4 Delete §7- §fStation löschen
                §4 List §7- §fStationen auflisten
                §4 Enabled §7- §fPforten umschalten
                §a
                §f AKTUELL -> §""" + ChatController.enabled() + """
                §a
                §8==============================
                """;

        if (p.isOp()) {
            try {
                int check = 0;

                if (args[0].equalsIgnoreCase("list")) {
                    p.sendMessage("""
                        §8==============================
                        §6           Zoll Stationen
                        """);
                    p.sendMessage("§6");
                    for(String list : ConfigHelper.loadLocations()) {
                        String[] str = list.split("%");
                        String[] parts = str[1].split("/");
                        if (!str[1].contains("0/1000/0/")) {
                            p.sendMessage(ChatController.generic("§f> §b" + parts[0] + "§f . §b" + parts[1] + "§f . §b" + parts[2] + "§f <"));
                        }
                    }
                    if (ConfigHelper.loadLocations().isEmpty()) {
                        p.sendMessage("§cNoch keine Stationen vorhanden");
                    }
                    p.sendMessage("§8==============================");
                    check = 1;
                }

                if (args[0].equalsIgnoreCase("create")) {
                    try {
                        PlaceController.create(loc, p);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        p.sendMessage(Chat.format("Create"));
                    }

                    check = 1;
                }

                if (args[0].equalsIgnoreCase("delete")) {
                    PlaceController.delete(loc,p);
                    check = 1;
                }

                if (args[0].equalsIgnoreCase("enabled")) {
                    try {
                        ConfigHelper.setEnabled(Boolean.parseBoolean(args[1]));
                        if (args[1].equalsIgnoreCase("true")) {
                            p.sendMessage(ChatController.error("§7Zollpforten wurden §ageöffnet"));
                        } else if (args[1].equalsIgnoreCase("false")) {
                            p.sendMessage(ChatController.error("§7Zollpforten wurden §cgeschlossen"));
                        } else {
                            p.sendMessage(ChatController.generic("§4Enabled §f[§aTrue §f/ §cfalse§f]"));
                            p.sendMessage(ChatController.generic("§f AKTUELL -> " + ChatController.enabled()));
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        p.sendMessage(ChatController.generic("§4Enabled §f[§aTrue §f/ §cfalse§f]"));
                        p.sendMessage(ChatController.generic("§fAKTUELL -> " + ChatController.enabled()));
                    }

                    check = 1;
                }

                if (check == 0) {
                    p.sendMessage(ChatController.error("Dieser Subcommand existiert nicht"));
                }

            } catch (ArrayIndexOutOfBoundsException e){
                p.sendMessage(help);
            } catch (ClassCastException e) {
                System.out.println("Nicht in der Konsole ausführbar");
            }
        } else {
            p.sendMessage(ChatController.error("Dieser Command kann nur von einem Server Operator Ausgeführt werden"));
        }
        return false;
    }
}
