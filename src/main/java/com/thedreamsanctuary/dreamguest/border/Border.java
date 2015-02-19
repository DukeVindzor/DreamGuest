package com.thedreamsanctuary.dreamguest.border;

import java.io.Serializable;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.thedreamsanctuary.dreamguest.util.Vector2D;
import com.thedreamsanctuary.dreamguest.util.WorldNotLoadedException;



public class Border implements Serializable {
	private static final long serialVersionUID = -1188011590624877570L;
	private static final String permissionBase = "dreamguest.border.enter.";
	private final Vector2D loc1;
    private final Vector2D loc2;
	private UUID worldID;
	private String name;
	
	public Border(String name, Vector2D loc1, Vector2D loc2, UUID worldID) {
		this.loc1 = Vector2D.getMinimum(loc1, loc2);
		this.loc2 = Vector2D.getMaximum(loc1, loc2);
		this.worldID = worldID;
		this.name = name;
	}
	
	public Border(String name, Location loc1, Location loc2) {
		this(name, loc1.getX(), loc1.getZ(), loc2.getX(), loc2.getZ(), loc1.getWorld().getUID());
	}
	
	public Border(String name, double x1, double z1, double x2, double z2, UUID worldID){
		this(name, new Vector2D(x1, z1), new Vector2D(x2, z2), worldID);
	}
	
	public String getName(){
		return name;
	}
	
	public Vector2D getStart(){
		return loc1;
	}
	
	public Vector2D getEnd(){
		return loc2;
	}
	
	public UUID getWorldID(){
		return worldID;
	}
	
	 public World getWorld() throws WorldNotLoadedException
	    {
	        final World zonesWorld = Bukkit.getWorld(this.worldID);
	        if (zonesWorld != null)
	        {
	            return zonesWorld;
	        }
	        else
	        {
	            throw new WorldNotLoadedException(this.worldID);
	        }
	    }
	
	public String getPermission(){
		return permissionBase + this.getName().replaceAll(" ", "");
	}
	
	public boolean isInside(final Block block){
		if(!block.getWorld().getUID().toString().equals(this.worldID.toString()))
        {
            return false;
        }
    	final Vector2D position = new Vector2D(block.getX(), block.getZ());
        return position.isInAB(this.loc1, this.loc2);
	}
	
	public boolean isInside(final int x, final int z){
		final Vector2D position = new Vector2D(x, z);
        return position.isInAB(this.loc1, this.loc2);
	}
	
	public boolean isInside(Location loc){
		 if(!loc.getWorld().getUID().toString().equals(this.worldID.toString())){
			 return false;
		 }
		 final Vector2D position = new Vector2D(loc.getX(), loc.getZ());
		 return position.isInAB(this.loc1, this.loc2);
	}
	
	public boolean isInside(Player p){
		if(!p.getWorld().getUID().equals(this.worldID)){
			return false;
		}
		return this.isInside(p.getLocation());
		
	}
	
	public boolean allowedInside(Player p){
		if(p.hasPermission(permissionBase + this.name)){
			return true;
		}
		return false;
	}
	
	@Override
    public String toString() {
        return name + ":(" + this.loc1 +")(" + this.loc2 + ") in the world " + Bukkit.getWorld(worldID).getName();      
    }

    public String toColoredString() {
        return ChatColor.GREEN + name + ChatColor.GRAY + ":(" + this.loc1.toColoredString() + ChatColor.GRAY +
                ")(" + this.loc2.toColoredString() + ChatColor.GRAY + ") in the world " + ChatColor.GREEN  + Bukkit.getWorld(worldID).getName();      
    }
}
