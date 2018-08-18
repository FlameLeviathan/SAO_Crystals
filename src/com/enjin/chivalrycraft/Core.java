package com.enjin.chivalrycraft;

import java.util.ArrayList;
import java.util.Set;

import crystals.Crystal;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.BlockIterator;

public class Core extends JavaPlugin implements Listener{


	//ItemStack orangeCrystal = new ItemStack(Material.STAINED_GLASS,1,(byte) 4);
	//ItemStack redCrystal = new ItemStack(Material.STAINED_GLASS,1,(byte) 2);

	ArrayList<Crystal> playerCrystals = new ArrayList<Crystal>();

	@Override
	public void onEnable(){
		getLogger().info(ChatColor.BLUE + "SAO has been Enabled");
		Bukkit.getScheduler().runTaskTimer(this, new Runnable() {
		@Override
		public void run(){
			for(Player player : Bukkit.getOnlinePlayers()) {
				playerCrystals.add(new Crystal(player, new ItemStack(Material.STAINED_GLASS_PANE,1,(byte) 5)));
			}
			return;
		}
	},1,1);

		saveConfig();
	}
	
	@Override
	public void onDisable(){
		for(Crystal crystal : playerCrystals){
		    crystal.removeCrystal();
        }
		getLogger().info("SAO has been Disabled");
		saveConfig();
	}
	
	public static void registerEvents(org.bukkit.plugin.Plugin plugin, Listener... listeners) {
		for (Listener listener : listeners) {
		Bukkit.getServer().getPluginManager().registerEvents(listener, plugin);
		}
		}
	
	/*@EventHandler
	public void playerLook(PlayerMoveEvent e){
		//Player player = e.getPlayer();
		//Block b = player.getTargetBlock((Set<Material>) null, 200);
/*		if(b.equals(Material.ARMOR_STAND)){
			player.sendMessage("H");
			Location bLoc = b.getLocation();
			if(bLoc == player.getEyeLocation().add(.0, .4, .0)){
				b.setType(Material.AIR);
			}
		}
		
		
		
	}*/

	@EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
	    Player player = event.getPlayer();
        for(Crystal crystal : playerCrystals){
            if(crystal.getPlayer() == player){
                crystal.updatePosition(player);
            }
        }
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
