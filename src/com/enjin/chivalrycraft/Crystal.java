package com.enjin.chivalrycraft;

import org.bukkit.ChatColor;
import org.bukkit.entity.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


public class Crystal {

	static boolean hasCrystal = false;
	Player player;



	/*Core c;
	public Crystal(Core c){
		this.c = c;
	}*/
    ArmorStand crystalDisplay;

    public Crystal(Player player){
        if(player.isOnline()){
            if(hasCrystal == false){
                this.player = player;

                crystalDisplay = (ArmorStand) player.getWorld().spawnEntity(player.getLocation(), EntityType.ARMOR_STAND);


                crystalDisplay.setVisible(false);
                crystalDisplay.setSmall(true);
                crystalDisplay.setMaxHealth(1000);
                crystalDisplay.setHealth(1000);
                crystalDisplay.setCustomName(ChatColor.GREEN + "♦");
                crystalDisplay.setCustomNameVisible(true);

                //Slime slime = (Slime) player.getWorld().spawnEntity(player.getLocation(), EntityType.SLIME);
                //slime.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0 , false, false));
                //slime.setSize(0);


                player.setPassenger(crystalDisplay);

                //slime.setPassenger(crystalDisplay);
            }
        }
    }

    /**
     * Removes the crystal from the player's head
     */
    public void removeCrystal(){
        crystalDisplay.remove();
    }

    /**
     * Takes a Chat color and applies that color to the crystal
     * @param color
     */
    public void setCrystalColor(ChatColor color){
        crystalDisplay.setCustomName(color + "♦");
    }

    public void setCrystalColor(String color){
        switch (color.toUpperCase()){
            case "GREEN":
                crystalDisplay.setCustomName(ChatColor.GREEN + "♦");
                break;
            case "ORANGE":
                crystalDisplay.setCustomName(ChatColor.GOLD + "♦");
                break;
            case "RED":
                crystalDisplay.setCustomName(ChatColor.RED + "♦");
                break;
            default:
                crystalDisplay.setCustomName(ChatColor.GREEN + "♦");
                break;

        }

    }


    /**
     * Returns the player who has the crystal over their head
     * @return
     */
/*    public SAOPlayer getPlayer(){
        return player;
    }*/

    /**
     * Returns the armor stand "crystal"
     * @return
     */
    public ArmorStand getCrystal(){
        return crystalDisplay;
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
