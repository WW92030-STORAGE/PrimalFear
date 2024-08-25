package com.primal.items;


import com.primal.util.Reference;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Interface;

import java.util.List;

public class PrimalItem extends Item {
    public static final boolean DEBUG = true;
    double distance;

    public PrimalItem(Properties properties, double dist) {
        super(properties);
        distance = dist;
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    public boolean isActive(Entity e) {
        return Reference.active.containsKey(e);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        Vec3 diagonal = new Vec3(distance + 1, distance + 1, distance + 1);

        InteractionResultHolder<ItemStack> res = super.use(level, player, hand);
        ItemStack i = res.getObject();
        if(!level.isClientSide() && hand == InteractionHand.MAIN_HAND) {
            if (isActive(player)) return res;

            Vec3 v1 = player.position().subtract(diagonal);
            Vec3 v2 = player.position().add(diagonal);

            List<Entity> list = level.getEntities(player, new AABB(v1, v2));
            if (DEBUG) System.out.println(list);

            for (Entity e : list) {
                boolean isMonster = e.getClass().isAssignableFrom(Mob.class) || Mob.class.isAssignableFrom(e.getClass());

                boolean isEnemy = false;
                Class<?>[] io = e.getClass().getInterfaces();
                for (Class<?> c : io) {
                    if (c.equals(Enemy.class)) {
                        isEnemy = true;
                        break;
                    }
                }

                if (!isEnemy && !isMonster) {
                    System.out.println(e + " IS NOT ENEMY");
                    continue;
                }

                if (e.position().distanceToSqr(player.position()) > distance * distance) {
                    System.out.println(e + " OUT OF BOUNDS");
                    continue;
                }

                ((Mob)e).addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 8, 64, false, false));
                ((Mob)e).addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 8, 64, false, false));
            }

            // Reference.active.put(player, System.nanoTime());
            // player.getCooldowns().addCooldown(i.getItem(), (int)(Reference.COOLDOWN * 20));
        }

        return super.use(level, player, hand);
    }
}