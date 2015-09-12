package com.thedreamsanctuary.dreamguest.border;

import java.io.Serializable;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.thedreamsanctuary.dreamguest.util.WorldNotLoadedException;

public class Border implements Serializable {
	private static final long serialVersionUID = -1188011590624877570L;
	private static final String PERMISSION_BASE = "dreamguest.border.enter.";
	private final Vector loc1;
    private final Vector loc2;
	private UUID worldID;
	private String name;
	
	public Border(String name, Vector loc1, Vector loc2, UUID worldID) {
		this.loc1 = Vector.getMinimum(loc1, loc2);
		this.loc2 = Vector.getMaximum(loc1, loc2);
		this.worldID = worldID;
		this.name = name;
	}
	
	public Border(String name, Location loc1, Location loc2) {
		this(name, loc1.getX(), loc1.getZ(), loc2.getX(), loc2.getZ(), loc1.getWorld().getUID());
	}
	
	public Border(String name, double x1, double z1, double x2, double z2, UUID worldID) {
		this(name, new Vector(x1, 0, z1), new Vector(x2, 0, z2), worldID);
	}
	
	public String getName() {
		return name;
	}
	
	public Vector getStart() {
		return loc1;
	}
	
	public Vector getEnd() {
		return loc2;
	}
	
	public UUID getWorldID() {
		return worldID;
	}
	
	public World getWorld() throws WorldNotLoadedException {
	    World zonesWorld = Bukkit.getWorld(this.worldID);
	    if (zonesWorld != null) {
	        return zonesWorld;
	    }
	    else {
	        throw new WorldNotLoadedException(this.worldID);
	    }
	 }
	
	public String getPermission() {
		return PERMISSION_BASE + this.getName().replaceAll(" ", "");
	}
	
	/**Checks if block is inside border
	 * 
	 * @param Block 		Block you want to check
	 * @return				True if block is inside, false otherwise
	 */
	public boolean isInside(Block block) {
		if (!block.getWorld().getUID().toString().equals(this.worldID.toString())) {
            return false;
        }
    	Vector position = new Vector(block.getX(), 0, block.getZ());
        return isContained(position);
	}
	
	/**Checks if the coordinate is inside border
	 * 
	 * @param x 		x-position
	 * @param y			y-position
	 * @return			True if coordinate is inside, false otherwise
	 */
	public boolean isInside(int x, int z) {
		Vector position = new Vector(x, 0, z);
        return isContained(position);
	}
	
	/**Checks if location is inside border
	 * 
	 * @param location	 	Location you want to check
	 * @return				True if location is inside, false otherwise
	 */
	public boolean isInside(Location location) {
		 if (!location.getWorld().getUID().toString().equals(this.worldID.toString())) {
			 return false;
		 }
		 Vector position = new Vector(location.getX(), 0, location.getZ());
		 return isContained(position);
	}
	
	/**Checks if player is inside border
	 * 
	 * @param player 		Player you want to check
	 * @return				True if player is inside, false otherwise
	 */
	public boolean isInside(Player player) {
		if (!player.getWorld().getUID().equals(worldID)) {
			return false;
		}
		return this.isInside(player.getLocation());
	}
	
	/**Checks if player has permission to be inside border
	 * 
	 * @param player 		Player you want to check
	 * @return				True if player has permission, false otherwise
	 */
	public boolean allowedInside(Player player) {
		return player.hasPermission(PERMISSION_BASE + name);
	}
	
	/**Checks if vector is contained been loc1 and loc2 in the xz-plane
	 * 
	 * @param position 		Vector you wish to check if it is contained between loc1 and loc2
	 * @return				True if vector is contained, false otherwise
	 */
	private boolean isContained(Vector position) {
        return position.getX() >= this.loc1.getX() && position.getX() <= this.loc2.getX() && position.getZ() >= this.loc1.getZ() && position.getZ() <= this.loc2.getZ();
    }
	
	/**Gives a colorful description of the specified vector
	 * 
	 * @param vector 		Vector you want a description of
	 * @return				A string description of specified vector
	 */
	static private String getColoredString(Vector vector) {
		return ChatColor.GREEN.toString() + vector.getX() + ChatColor.GRAY + ", " + ChatColor.GREEN + vector.getZ();
	}
	
	/**Gives a colorful description of the border
	 * 
	 * @return				A string description of the border
	 */
	public String toColoredString() {
        return ChatColor.GREEN + name + ChatColor.GRAY + ":(" + getColoredString(this.loc1) + ChatColor.GRAY +
               ")(" + getColoredString(this.loc2) + ChatColor.GRAY + ") in the world " + ChatColor.GREEN  + Bukkit.getWorld(worldID).getName();      
    }
	
	@Override
    public String toString() {
        return name + ":(" + this.loc1 +")(" + this.loc2 + ") in the world " + Bukkit.getWorld(worldID).getName();      
    }
}

