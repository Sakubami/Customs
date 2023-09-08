package xyz.samiki.zollsystem.controllers;

public class ChatController {

    public static String generic(String s) {
        String newmsg = "§7[§6Haraxx§7.§6net§7] §7"+ s;
        return newmsg;
    }

    public static String error(String s) {
        String newmsg = "§7[§6Haraxx§7.§6net§7] §c"+ s;
        return newmsg;
    }

    public static String succes(String s) {
        String newmsg = "§7[§6Haraxx§7.§6net§7] §a"+ s;
        return newmsg;
    }
}
