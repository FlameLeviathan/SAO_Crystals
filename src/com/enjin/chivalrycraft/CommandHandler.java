package com.enjin.chivalrycraft;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandHandler implements CommandExecutor {
    Core core;


    public CommandHandler(Core c){
        core = c;
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command cmd, String label, String[] args) {

        if(commandSender instanceof Player){
            Player player = (Player)commandSender;
            SAOPlayer saoPlayer = core.system.getSaoPlayer(player);

            if(cmd.getName().equalsIgnoreCase("karma")){
                if(args.length == 1){
                    if(Bukkit.getPlayer(args[0]) != null) {
                        if(player.hasPermission("saocursors.viewotherskarma")) {
                            sendKarmaAmountMsg(Bukkit.getPlayer(args[0]), player);
                            return true;
                        }
                    }else{
                        player.sendMessage(message("&cProper usage: /karma optional:<player>"));
                    }
                } else {
                    if(player.hasPermission("saocursors.viewkarma")) {
                        sendKarmaAmountMsg(player, player);
                        return true;
                    } else{
                        player.sendMessage(message("&cYou do not have permission to run this command!"));
                    }
                }

            } else

            if(cmd.getName().equalsIgnoreCase("setkarma")){
                //setkarma <player> <amount>
                if (player.hasPermission("saocursors.setkarma")) {
                    if (args.length == 2) {
                        if(Bukkit.getPlayer(args[0]) != null) {
                            try {
                                core.system.getSaoPlayer(Bukkit.getPlayer(args[0])).setKarma(Integer.parseInt(args[1]));
                                core.system.updatePlayerCrystal(core.system.getSaoPlayer(Bukkit.getPlayer(args[0])));
                                player.sendMessage(message("&aYou have set &d" + Bukkit.getPlayer(args[0]).getName() + "&a's karma to &b"+ Integer.parseInt(args[1]) + "&a!"));
                                return true;
                            }catch (Exception e){
                                player.sendMessage(message("&cPlease make sure you typed a valid player's name and entered an integer"));
                                return true;
                            }
                        } else{
                            player.sendMessage(message("&cThat player could not be found or is not online!"));
                            return true;
                        }
                    }else {
                        player.sendMessage(message("&cProper usage: /setkarma <player> <amount>"));
                        return true;
                    }
                } else{
                    player.sendMessage(message("&cYou do not have permission to run this command!"));
                    return true;
                }
            } else

            if(cmd.getName().equalsIgnoreCase("addkarma")){
                //setkarma <player> <amount>
                if (player.hasPermission("saocursors.addkarma")) {
                    if (args.length == 2) {
                        if(Bukkit.getPlayer(args[0]) != null) {
                            try {
                                core.system.getSaoPlayer(Bukkit.getPlayer(args[0])).addKarma(Integer.parseInt(args[1]));
                                core.system.updatePlayerCrystal(core.system.getSaoPlayer(Bukkit.getPlayer(args[0])));
                                player.sendMessage(message("&aYou have added &b "+ Integer.parseInt(args[1]) + "&a karma to &d" + Bukkit.getPlayer(args[0]) + "&a!"));
                                return true;
                            }catch (Exception e){
                                player.sendMessage(message("&cPlease make sure you typed a valid player's name and entered an integer"));
                                return true;
                            }
                        } else{
                            player.sendMessage(message("&cThat player could not be found or is not online!"));
                            return true;
                        }
                    }else {
                        player.sendMessage(message("&cProper usage: /addkarma <player> <amount>"));
                        return true;
                    }
                } else{
                    player.sendMessage(message("&cYou do not have permission to run this command!"));
                    return true;
                }
            } else

            if(cmd.getName().equalsIgnoreCase("removekarma")){
                //setkarma <player> <amount>
                if (player.hasPermission("saocursors.removekarma")) {
                    if (args.length == 2) {
                        if(Bukkit.getPlayer(args[0]) != null) {
                            try {
                                core.system.getSaoPlayer(Bukkit.getPlayer(args[0])).addKarma((-Integer.parseInt(args[1])));
                                core.system.updatePlayerCrystal(core.system.getSaoPlayer(Bukkit.getPlayer(args[0])));
                                player.sendMessage(message("&aYou have taken &b "+ Integer.parseInt(args[1]) + "&a karma from &d" + Bukkit.getPlayer(args[0]) + "&a!"));
                                return true;
                            }catch (Exception e){
                                player.sendMessage(message("&cPlease make sure you typed a valid player's name and entered an integer"));
                                return true;
                            }
                        } else{
                            player.sendMessage(message("&cThat player could not be found or is not online!"));
                            return true;
                        }
                    }else {
                        player.sendMessage(message("&cProper usage: /removekarma <player> <amount>"));
                        return true;
                    }
                } else{
                    player.sendMessage(message("&cYou do not have permission to run this command!"));
                    return true;
                }
            }

        }
        return false;
    }
    
    public void sendKarmaAmountMsg(Player checkingKarmaAmount, Player searchingKarmaAmount){
        SAOPlayer saoPlayer = core.system.getSaoPlayer(checkingKarmaAmount);
        searchingKarmaAmount.sendMessage(ChatColor.GOLD + "---------------------------------");
        searchingKarmaAmount.sendMessage(ChatColor.AQUA + saoPlayer.getPlayer().getName() + "'s Karma:" + core.system.karmaColor(saoPlayer) +"" + saoPlayer.getKarma() +  ChatColor.GREEN +"/" + saoPlayer.getMaxKarma());
        searchingKarmaAmount.sendMessage(core.system.karmaMessage(saoPlayer));
        searchingKarmaAmount.sendMessage(ChatColor.GOLD + "---------------------------------");
    }

    public String message(String string){
        string = ChatColor.translateAlternateColorCodes('&', string);
        return string;
    }
}
