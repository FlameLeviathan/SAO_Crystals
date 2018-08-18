package crystals;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import com.enjin.chivalrycraft.Core;

public class OrangeCrystal implements Listener {
	public static ArmorStand as = null;
	public static ArmorStand as2 = null;
	static boolean hasCrystal = false;

	
	Core c;
	public OrangeCrystal(Core c){
		this.c = c;
	}
	
	public static void createGreenCrystal(Player player){
		if(player.isOnline()){
			if(hasCrystal == false){
				hasCrystal = true;
				Location loc = player.getEyeLocation().add(.0, .4, .0);
				as = loc.getWorld().spawn(loc, ArmorStand.class);

				as.setHelmet(new ItemStack(Material.STAINED_GLASS,1,(byte) 4));
				as.setHeadPose(new EulerAngle(0, 45, -45));

				
				//AS Section
				as.setCustomName("Crystal");
				as.setCustomNameVisible(false);
				as.setSmall(true);
				as.setVisible(false);
				as.setGravity(false);

				
			}
		}
	}
	
	public static void playerCrystal(Player p){
		Location loc = p.getEyeLocation().add(.0, .4, .0);
		if(hasCrystal == false){
			createGreenCrystal(p);
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
}
