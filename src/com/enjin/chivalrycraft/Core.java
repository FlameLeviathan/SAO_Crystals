package com.enjin.chivalrycraft;

import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.BlockIterator;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class Core extends JavaPlugin implements Listener{


	System system;
    CommandHandler commandHandler = new CommandHandler(this);




	@Override
	public void onEnable(){
		getLogger().info(ChatColor.BLUE + "SAO has been Enabled");


		system = new System(this);


            try {
                File fileReader = new File("plugins/SAOCursors/playerdata.yml");
                if (fileReader.exists()) {
                    system.readFileToHashmap((ArrayList<String>) SLAPI.load("plugins/SAOCursors/playerdata.yml"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        system.giveOnlinePlayersCrystals();


        getCommand("karma").setExecutor(commandHandler);
        getCommand("setkarma").setExecutor(commandHandler);
        getCommand("addkarma").setExecutor(commandHandler);
        getCommand("removekarma").setExecutor(commandHandler);
		saveConfig();

			registerEvents(this,  system, this);
	}
	
	@Override
	public void onDisable(){
        system.removeOnlinePlayersCrystals();
        try {
            SLAPI.save(system.writeHashmapToFile(), "plugins/SAOCursors/playerdata.yml");
        } catch (Exception e) {
            e.printStackTrace();
        }
        getLogger().info("SAO has been Disabled");
		saveConfig();
	}

	@EventHandler
	private void onLogin (PlayerJoinEvent event){
        Crystal playerCrystal = new Crystal(event.getPlayer());
        system.playerCrystals.put(event.getPlayer(), playerCrystal);
        final long PERIOD = 500; // Adjust to suit timing
        long lastTime = java.lang.System.currentTimeMillis() - PERIOD;
        long thisTime = java.lang.System.currentTimeMillis();
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        PacketPlayOutEntityDestroy packetPlayOutEntityDestroy = new PacketPlayOutEntityDestroy(playerCrystal.getCrystal().getEntityId());
                        ((CraftPlayer) event.getPlayer()).getHandle().playerConnection.sendPacket(packetPlayOutEntityDestroy);
                    }
                },
                500
        );

    }

    @EventHandler
    private void onLogout (PlayerQuitEvent event){
		system.playerCrystals.get(event.getPlayer()).removeCrystal();
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
