package com.enjin.chivalrycraft;

import me.iloveani.parties.Parties;
import me.iloveani.parties.PartiesManager;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class System implements Listener {
    //Class Variables
    //playerWhoHit, playerHit
    HashMap<SAOPlayer, SAOPlayer> attackedPlayers = new HashMap<>();
    HashMap<Player, Crystal> playerCrystals = new HashMap<>();
    HashMap<SAOPlayer, Integer> saoPlayers= new HashMap<>();
    int greenMin = 20;
    int orangeMin = 11;
    int redMin = 0;

    Core core;
    public System(Core core){
        this.core = core;
    }

    @EventHandler
    private void onPlayerHit(EntityDamageByEntityEvent event){
        int karmaLossForAttackingPlayer = 1;
        int delay = 10;
        int period = 1;

        //TODO: Cancel damage on cursors
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player){
            SAOPlayer playerWhoHit = getSaoPlayer((Player) event.getDamager());
            SAOPlayer playerHit = getSaoPlayer((Player) event.getEntity());
            Parties parties = new Parties();
            PartiesManager partiesManager = new PartiesManager(parties);
            if(partiesManager.getMember(playerWhoHit.getPlayer()).getParty() == partiesManager.getMember(playerHit.getPlayer()).getParty()){
                return;
            }


            karmaLossTracking(karmaLossForAttackingPlayer, delay, period, playerWhoHit, playerHit);

            for(SAOPlayer playerWhoHitFirst : attackedPlayers.keySet()){
                if(attackedPlayers.get(playerWhoHitFirst).equals(playerWhoHit)){
                    //TODO: Nothing because the player was provoked
                }
            }


        }

    }

    @EventHandler
    public void onCrouch(PlayerToggleSneakEvent event){
        Player player = event.getPlayer();
        if(player.isSneaking()) {
            playerCrystals.get(getSaoPlayer(player)).getCrystal().setCustomNameVisible(false);
        } else{
            playerCrystals.get(getSaoPlayer(player)).getCrystal().setCustomNameVisible(true);
        }
    }

    @EventHandler
    private void onPlayerDeath(EntityDeathEvent event){
        int karmaLossForKillingPlayer = 10;
        int delay = 10;
        int period = 1;

        //TODO: Cancel damage on cursors
        if (event.getEntity().getKiller() instanceof Player && event.getEntity() instanceof Player){
            SAOPlayer playerWhoHit = getSaoPlayer((Player) event.getEntity().getKiller());
            SAOPlayer playerHit = getSaoPlayer((Player) event.getEntity());
            Parties parties = new Parties();
            PartiesManager partiesManager = new PartiesManager(parties);
            if(partiesManager.getMember(playerWhoHit.getPlayer()).getParty() == partiesManager.getMember(playerHit.getPlayer()).getParty()){
                if(partiesManager.getMember(playerWhoHit.getPlayer()).getParty().getPartySettings().friendlyFire)
                    return;
            }

            event.getEntity().getKiller().sendMessage("Works");
            karmaLossTracking(karmaLossForKillingPlayer, delay, period, playerWhoHit, playerHit);
            getPlayerCrystal(playerHit).removeCrystal();

            for(SAOPlayer playerWhoHitFirst : attackedPlayers.keySet()){
                if(attackedPlayers.get(playerWhoHitFirst).equals(playerWhoHit)){
                    //TODO: Nothing because the player was provoked
                }
            }


        }

    }

    private void karmaLossTracking(int karmaLoss, int delay, int period, SAOPlayer playerWhoHit, SAOPlayer playerHit) {
        if(attackedPlayers.containsKey(playerHit)){
            if (attackedPlayers.get(playerHit).equals(playerWhoHit)){
                //Add to attackedPlayers and add a timer for 10 sec? (Variable this for config)
                attackedPlayers.put(playerWhoHit, playerHit);
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        attackedPlayers.remove(playerWhoHit, playerHit);
                        timer.cancel();
                    }
                }, delay, period);
                updatePlayerCrystal(playerWhoHit);
            }

        }else{
            // Reduce Karma by set amount (Config this amount)
            attackedPlayers.put(playerWhoHit, playerHit);
            if((playerWhoHit.getKarma()-karmaLoss) < playerWhoHit.getMinKarma()/*playerWhoHit.getMinKarma()*/){
                playerWhoHit.setKarma(playerWhoHit.getMinKarma());
            }else {
                playerWhoHit.addKarma(-karmaLoss);
            }
            playerWhoHit.getPlayer().sendMessage(playerWhoHit.getKarma() + "");
            updatePlayerCrystal(playerWhoHit);
        }
    }

    /**
     * Updates a player's crystal color if conditions are met
     * @param player SAOPlayer to update the crystal of
     */
    public void updatePlayerCrystal(SAOPlayer player){
        int karmaAmount = player.getKarma();
        Crystal crystal = getPlayerCrystal(player);

        if(karmaAmount >= redMin && karmaAmount < orangeMin){
            crystal.setCrystalColor("RED");
            return;
        }

        if(karmaAmount >= orangeMin && karmaAmount < greenMin){
            crystal.setCrystalColor("ORANGE");
            return;
        }

        if(karmaAmount >= greenMin && karmaAmount <= player.getMaxKarma()){
            crystal.setCrystalColor("GREEN");
            return;
        }
    }

    /**
     * Add a crystal to all online players
     */
    public void giveOnlinePlayersCrystals(){
        for(Player player : Bukkit.getOnlinePlayers()) {
            if(getSaoPlayer(player)== null || !saoPlayers.containsKey(getSaoPlayer(player))) {
                SAOPlayer saoPlayer = new SAOPlayer(player);
                Crystal playerCrystal = new Crystal(player);
                saoPlayers.put(saoPlayer, saoPlayer.getKarma());
                playerCrystals.put(player, playerCrystal);
                updatePlayerCrystal(saoPlayer);
                PacketPlayOutEntityDestroy packetPlayOutEntityDestroy = new PacketPlayOutEntityDestroy(playerCrystal.getCrystal().getEntityId());
                ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packetPlayOutEntityDestroy);
                //core.entityHider.hideEntity(player, playerCrystal.getCrystal());
            }
            else{
                Crystal playerCrystal = new Crystal(player);
                playerCrystals.put(player, playerCrystal);
                updatePlayerCrystal(getSaoPlayer(player));
                PacketPlayOutEntityDestroy packetPlayOutEntityDestroy = new PacketPlayOutEntityDestroy(playerCrystal.getCrystal().getEntityId());
                ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packetPlayOutEntityDestroy);
            }
        }
    }

    /**
     * Remove crystals from all online players
     */
    public void removeOnlinePlayersCrystals(){
        for(Player player : Bukkit.getOnlinePlayers()){

            playerCrystals.get(player).removeCrystal();
        }
    }

    /**
     * Returns the crystal of the player
     * @param player Player you want to get the crystal of
     * @return Crystal of specified player
     */
    public Crystal getPlayerCrystal(SAOPlayer player){
        //if(playerCrystals.containsKey(player)) {
            return playerCrystals.get(player.getPlayer());
/*        }
        return null;*/
    }

    public SAOPlayer getSaoPlayer(Player player){
        for(SAOPlayer saoPlayer : saoPlayers.keySet()){
            if(saoPlayer.getPlayer() == player){
                return saoPlayer;
            }
        }
        return null;
    }

    /**
     * Writes the saoPlayers Hashmap to an arraylist
     */
    public ArrayList writeHashmapToFile(){
        ArrayList<String> info = new ArrayList<>();

        for(SAOPlayer player : saoPlayers.keySet()){
            info.add(player.getPlayer().getName() + ":"+player.getKarma());
        }

        return info;
    }

    /**
     * Takes an array list and transfers the player to the saoPlayers hashmap
     * @param info
     */
    public void readFileToHashmap(ArrayList<String> info) {
        for (String string : info) {
            String[] splitString = string.split(":");

            if (Bukkit.getPlayer(splitString[0]) != null) {
                Player player = Bukkit.getPlayer(splitString[0]);
                if (!saoPlayers.containsKey(getSaoPlayer(player))) {
                    SAOPlayer saoPlayer;
                    saoPlayers.put(saoPlayer = new SAOPlayer(Bukkit.getPlayer(splitString[0])), Integer.parseInt(splitString[1]));

                    saoPlayer.setKarma(Integer.parseInt(splitString[1]));
                }
            }
        }
    }

    public String karmaMessage(SAOPlayer player){
        String message = "";
        int Karma = player.getKarma();
        for(int i = 0; i < player.getMaxKarma(); i++){
            if(i > player.getKarma()) {
                message = message + ChatColor.GRAY + "|";
                continue;
            }
            if(i >= redMin && i < orangeMin){
                message = message + ChatColor.RED +  "|";
                continue;
            }
            if(i >= orangeMin && i < greenMin){
                message = message + ChatColor.GOLD +  "|";
                continue;

            }
            if(i >= greenMin && i <= player.getMaxKarma()){
                message = message + ChatColor.GREEN +  "|";
                continue;
            }
        }
        return message;
    }

    public ChatColor karmaColor(SAOPlayer player){

        int karma = player.getKarma();
            if(karma >= redMin && karma < orangeMin){
                return ChatColor.RED ;
            }
            if(karma >= orangeMin && karma < greenMin){
                return ChatColor.GOLD ;
            }
            if(karma > greenMin && karma <= player.getMaxKarma()){
                return ChatColor.GREEN ;
            }
        return null;
    }
}
