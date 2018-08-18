package com.enjin.chivalrycraft;

import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.BlockIterator;

public class Core extends JavaPlugin implements Listener{
	
	@Override
	public void onEnable(){
		getLogger().info(ChatColor.BLUE + "SAO Has been Enabled");
		Bukkit.getScheduler().runTaskTimer(this, new Runnable() {
		@Override
		public void run(){
			for(Player player : Bukkit.getOnlinePlayers()) {
				crystals.GreenCrystal.playerCrystal(player);
			}
			return;
		}
	},1,1);
		registerEvents(this,
				new crystals.GreenCrystal(this),
				new crystals.OrangeCrystal(this),
				new crystals.RedCrystal(this),this);
		saveConfig();
	}
	
	@Override
	public void onDisable(){
		crystals.GreenCrystal.as.remove();
		crystals.GreenCrystal.as2.remove();
		getLogger().info("SAO Has been Disabled");
		saveConfig();
	}
	
	public static void registerEvents(org.bukkit.plugin.Plugin plugin, Listener... listeners) {
		for (Listener listener : listeners) {
		Bukkit.getServer().getPluginManager().registerEvents(listener, plugin);
		}
		}
	
	@EventHandler
	public void playerLook(PlayerMoveEvent e){
		//Player player = e.getPlayer();
		//Block b = player.getTargetBlock((Set<Material>) null, 200);
/*		if(b.equals(Material.ARMOR_STAND)){
			player.sendMessage("H");
			Location bLoc = b.getLocation();
			if(bLoc == player.getEyeLocation().add(.0, .4, .0)){
				b.setType(Material.AIR);
			}
		}*/
		
		
		
	}
	
    public final Block getTargetBlock(Player player, int range) {
        BlockIterator iter = new BlockIterator(player, range);
        Block lastBlock = iter.next();
        while (iter.hasNext()) {
            lastBlock = iter.next();
            if (lastBlock.getType() == Material.AIR) {
                continue;
            }
            break;
        }
        return lastBlock;
    }

}
