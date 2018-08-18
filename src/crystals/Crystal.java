package crystals;

import com.enjin.chivalrycraft.Core;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftArmorStand;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import java.util.Timer;
import java.util.TimerTask;

public class Crystal implements Listener {

	static boolean hasCrystal = false;
	Player player;
	ItemStack crystal;
    Shard shardPhaseOne;
    Shard shardPhaseTwo;


/*	Core c;
	public Crystal(Core c){
		this.c = c;
	}*/

    public Crystal(Player player, ItemStack crystal){
        if(player.isOnline()){
            if(hasCrystal == false){
                this.player = player;
                this.crystal = crystal;
                shardPhaseOne = new Shard(crystal, 1);
                shardPhaseTwo = new Shard(crystal, 2);

                shardPhaseOne.armorStand.teleport(player.getEyeLocation().add(0, .4, 0));
                shardPhaseTwo.armorStand.teleport(player.getEyeLocation().add(0,.4,0));

            }
        }
    }

    public void updatePosition(Player player){
        shardPhaseOne.armorStand.teleport(player.getEyeLocation().add(0, .4, 0));
        shardPhaseTwo.armorStand.teleport(player.getEyeLocation().add(0,.4,0));
    }

    public void removeCrystal(){
        shardPhaseOne.armorStand.remove();
        shardPhaseTwo.armorStand.remove();
    }

    public Player getPlayer(){
        return player;
    }

    public ItemStack getCrystal(){
        return crystal;
    }


	
/*	public static void createCrystal(ArmorStand as, Player player, ItemStack crystal){
		if(player.isOnline()){
			if(hasCrystal == false){
				hasCrystal = true;
				Location loc = player.getEyeLocation().add(vec);
				as = loc.getWorld().spawn(loc, ArmorStand.class);

				as.setHelmet(crystal);
				as.setHeadPose(new EulerAngle(0, 0, 45*//*-35.5*//*));

				
				//AS Section
				as.setCustomName("Crystal");
				as.setCustomNameVisible(false);
				as.setSmall(true);
				as.setVisible(true);
				as.setGravity(false);

				
			}
		}
	}*/
	
/*	public static void playerCrystal(Player p, ItemStack crystal){
		Location loc = p.getEyeLocation();

        Shard shardPhaseOne = new Shard(crystal, 1);
        Shard shardPhaseTwo = new Shard(crystal, 2);

		if(hasCrystal == false){
            shardPhaseOne.armorStand.teleport(loc.add(0,.4,0));
            shardPhaseTwo.armorStand.teleport(loc.add(0,.4,0));
		}else{
			armorStand.teleport(loc);
            armorStand2.teleport(loc);

		}
	}*/
	
/*	@EventHandler
	public void onCrystalDamage(EntityDamageByEntityEvent e){
		if(e.getEntity() instanceof ArmorStand){
			if(e.getDamager() instanceof Projectile){
		            Entity damageSource = e.getDamager();
		            //only arrows
		            if(damageSource instanceof Arrow)
		            {
		                final Arrow arrow = (Arrow)damageSource;
		                if(e.getEntity().getCustomName().equals(armorStand.getCustomName()))
		                    //cancel the damage
		                    e.setCancelled(true);
		                    //teleport the arrow a single block farther along its flight path
		                    //note that .6 and 12 were the unexplained recommended values for speed and spread, reflectively, in the bukkit wiki
		                    final Vector velo = arrow.getVelocity();
		                    arrow.remove();
		                    e.getEntity().getWorld().spawnArrow(arrow.getLocation().add((velo.normalize()).multiply(2)), velo.multiply(100), 1.75f, 0);
		            }
			}
		}
	}*/
	
	/*@EventHandler
	public void onPlayerMove(PlayerMoveEvent e){
		final Player player = e.getPlayer();
		//Location loc = new Location(player.getWorld(),0,0,0);
        //as.teleport(loc);
		armorStand.teleport(player.getEyeLocation().add(vec));
        armorStand2.teleport(player.getEyeLocation().add(vec));
*//*        new Timer().schedule(new TimerTask(){
        	@Override
        	public void run() {
        		as.teleport(player.getEyeLocation().add(0, .4, 0));
        	}
        }, 10);*//*
        
        
		
	}*/
}
