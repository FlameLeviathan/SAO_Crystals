
package com.enjin.chivalrycraft;

import org.bukkit.entity.Player;

/*
 * Implements the player class and adds karma values to regular MC players
 */
public class SAOPlayer{

    int karma = 20;
    int maxKarma = 20;
    int minKarma= 0;
    Player player;

    public SAOPlayer(Player player){
        this.player = player;
    }

    public Player getPlayer(){
        return player;
    }

    /**
     * Returns the amount of karma a player has
     * @return
     */
    public int getKarma(){
        return karma;
    }

    /**
     * Sets the player's karma
     * @param karmaValue
     */
    public void setKarma(int karmaValue){
        karma=karmaValue;
    }

    /**
     * Adds karma to a player
     * To remove karma add a negative number
     * @param karmaValue
     */
    public void addKarma(int karmaValue){
        karma= karma + karmaValue;
    }

    /**
     * Returns the max karma a player can have
     * @return
     */
    public int getMaxKarma() {
        return maxKarma;
    }

    /**
     * Returns the minimum or lowest amount of karma a player can have
     * @return
     */
    public int getMinKarma() {
        return minKarma;
    }

    /**
     * Sets teh max amount of Karma a player can have
     * @param maxKarma
     */
    public void setMaxKarma(int maxKarma) {
        this.maxKarma = maxKarma;
    }

    /**
     * Sets the minimum or lowest amount of karma a player can have
     * @param minKarma
     */
    public void setMinKarma(int minKarma) {
        this.minKarma = minKarma;
    }
}
