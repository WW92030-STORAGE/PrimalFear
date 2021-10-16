package primal.mod.items;

import net.minecraft.command.ICommandSender;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import primal.mod.Main;
import primal.mod.init.ItemInit;
import primal.mod.util.IModel;
import primal.mod.util.Reference;

public class PrimalFear extends Item implements IModel {
	
	private int range;
	public PrimalFear(String name, int rho) {
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(CreativeTabs.COMBAT);
		range = rho;
		
		
		ItemInit.ITEMS.add(this);
	}
	
	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(this, 0, "inventory");
	}
	
	@Override
	public boolean hasEffect(ItemStack is) {
		return true;
	}
	
	public boolean isActive(Entity e) {
		return Reference.active.containsKey(e);
	}
	
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		ActionResult<ItemStack> res = super.onItemRightClick(world, player, hand);
		ItemStack is = res.getResult();
		
		long time = System.nanoTime();
		long stored = time;
		if (isActive(player)) stored = Reference.active.get(player);
		if (time - stored >= Reference.cooldown * 1000000000) Reference.active.remove(player);
		
		if (isActive(player)) return res;
		
		try {
			ICommandSender ics = new ICommandSender() {
				@Override
				public String getName() {
					return "";
				}

				@Override
				public boolean canUseCommand(int permission, String command) {
					return true;
				}

				@Override
				public World getEntityWorld() {
					return world;
				}

				@Override
				public MinecraftServer getServer() {
					return world.getMinecraftServer();
				}

				@Override
				public boolean sendCommandFeedback() {
					return false;
				}

				@Override
				public BlockPos getPosition() {
					return new BlockPos((int) (player.posX), (int) (player.posY), (int) (player.posZ));
				}

				@Override
				public Vec3d getPositionVector() {
					return new Vec3d((player.posX), (player.posY), (player.posZ));
				}
			};
			
			String slowness = "effect @e[r=" + range + ",type=!player] slowness " + Reference.stuntime + " 100 true";
			String weakness = "effect @e[r=" + range + ",type=!player] weakness " + Reference.stuntime + " 100 true";

			player.world.getMinecraftServer().getCommandManager().executeCommand(ics, slowness);
			player.world.getMinecraftServer().getCommandManager().executeCommand(ics, weakness);
			
			player.getCooldownTracker().setCooldown(is.getItem(), Reference.cooldown * 20);
		}
		catch (Exception e) {
			System.out.println(e);
		}
		
		double theta = 0;
		double phi = 0;
		double step = 10; // must divide into 180
		double dx, dy, dz;
		double cp, sp, ct, st;
		double thetarad, phirad;
		
		world.playSound((EntityPlayer) null, player.posX, player.posY, player.posZ, net.minecraft.util.SoundEvent.REGISTRY.getObject(new ResourceLocation("entity.lightning.thunder")), 
				SoundCategory.NEUTRAL, 10, 1);
		
		for (int i = 0; i < 1 + 180 / step; i++) {
			theta = 0;
			for (int j = 0; j < 360 / step; j++) {
				thetarad = theta * Reference.RAD;
				phirad = phi * Reference.RAD;
				cp = Math.cos(phirad);
				sp = Math.sin(phirad);
				ct = Math.cos(thetarad);
				st = Math.sin(thetarad);
				
				dx = range * ct * sp;
				dz = range * st * sp;
				dy = range * cp;
				
				world.spawnParticle(EnumParticleTypes.FLAME, dx + player.posX, dy + player.posY, dz + player.posZ, 0, 0, 0);

				
				theta += step;
			}
			phi += step;
		}
		
		return res;
	}

}
