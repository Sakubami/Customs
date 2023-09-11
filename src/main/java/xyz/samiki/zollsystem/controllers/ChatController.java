package xyz.samiki.zollsystem.controllers;

import xyz.samiki.zollsystem.ConfigHelper;

public class ChatController {

    public static String generic(String s) {
        return "§7[§6Haraxx§7.§6net§7] §7"+ s;
    }

    public static String error(String s) {
        return "§7[§6Haraxx§7.§6net§7] §c"+ s;
    }

    public static String succes(String s) {
        return "§7[§6Haraxx§7.§6net§7] §a"+ s;
    }

    public static String enabled() {
        if (ConfigHelper.isEnabled()) {
            return "§aAktiviert";
        } else {
            return "§cDeaktiviert";
        }
    }
}
