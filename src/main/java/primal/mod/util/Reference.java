package primal.mod.util;

import java.util.HashMap;

import net.minecraft.entity.Entity;

public class Reference {
	public static final String MODID = "primal";
	public static final String NAME = "PRIMAL FEAR";
	public static final String VERSION = "1.0.0";
	public static final String COMMON = "primal.mod.proxy.CommonProxy";
	public static final String CLIENT = "primal.mod.proxy.ClientProxy";
	
	public static final double RAD = Math.PI / 180.0;
	public static final double EPSILON = 0.000000001;
	
	public static int cooldown = 30;
	public static int stuntime = 8;
	
	public static HashMap<Entity, Long> active = new HashMap<Entity, Long>();
}
