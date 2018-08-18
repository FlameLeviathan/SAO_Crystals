package crystals;

import com.enjin.chivalrycraft.Core;
import org.bukkit.Location;
import org.bukkit.Material;
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
	public static ArmorStand as = null;
	public static ArmorStand as2 = null;
	static boolean hasCrystal = false;
	static Vector vec = new Vector(0,5,0);


	Core c;
	public Crystal(Core c){
		this.c = c;
	}
	
	public static void createCrystal(Player player, ItemStack crystal){
		if(player.isOnline()){
			if(hasCrystal == false){
				hasCrystal = true;
				Location loc = player.getEyeLocation().add(vec);
				as = loc.getWorld().spawn(loc, ArmorStand.class);

				as.setHelmet(crystal);
				as.setHeadPose(new EulerAngle(0, 45, -35.5));

				
				//AS Section
				as.setCustomName("Crystal");
				as.setCustomNameVisible(false);
				as.setSmall(true);
				as.setVisible(false);
				as.setGravity(false);

				
			}
		}
	}
	
	public static void playerCrystal(Player p, ItemStack crystal){
		Location loc = p.getEyeLocation().add(vec);
		if(hasCrystal == false){
			createCrystal(p, crystal);
		}else{
			as.teleport(loc);

		}
	}
	
	@EventHandler
	public void onCrystalDamage(EntityDamageByEntityEvent e){
		if(e.getEntity() instanceof ArmorStand){
			if(e.getDamager() instanceof Projectile){
		            Entity damageSource = e.getDamager();
		            //only arrows
		            if(damageSource instanceof Arrow)
		            {
		                final Arrow arrow = (Arrow)damageSource;
		                if(e.getEntity().getCustomName().equals(as.getCustomName()))
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
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e){
		final Player player = e.getPlayer();
		Location loc = new Location(player.getWorld(),0,0,0);
        as.teleport(loc);
        new Timer().schedule(new TimerTask(){
        	@Override
        	public void run() {
        		as.teleport(player.getEyeLocation().add(0, .4, 0));
        	}
        }, 1000);
        
        
		
	}
}
