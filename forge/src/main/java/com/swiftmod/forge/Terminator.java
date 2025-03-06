package com.swiftmod.forge;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;


public class Terminator extends BowItem {
    public Terminator(Item.Properties properties) {
        super(properties);
    }

    public static class TermArrow extends AbstractArrow {
        public TermArrow(EntityType<? extends AbstractArrow> entityType, Level world) {
            super(entityType, world);
        }

        public TermArrow(Level world, LivingEntity shooter) {
            super(EntityType.ARROW, shooter, world);
        }

        @Override
        protected ItemStack getPickupItem() {
            return ItemStack.EMPTY;
        }

        @Override
        protected void onHitEntity(EntityHitResult entityHitResult) {
            if (entityHitResult.getType() == HitResult.Type.ENTITY) {
                if (entityHitResult.getEntity() instanceof EnderMan enderman) {
                    if (enderman.level() instanceof ServerLevel serverLevel)
                        enderman.hurt(serverLevel.damageSources().generic(), 4.0F);
                }
                else {
                    super.onHitEntity(entityHitResult);
                }
            }
        }
    }

    @Override
    public UseAnim getUseAnimation(ItemStack arg) {
        return UseAnim.NONE;
    }

    public static void shootArrow(Level world,Player player) {
        if (!world.isClientSide) {
            TermArrow termArrow1 = new TermArrow(world, player);
            TermArrow termArrow2 = new TermArrow(world, player);
            TermArrow termArrow3 = new TermArrow(world, player);

            termArrow1.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 3.0F, 1.0F);
            termArrow2.shootFromRotation(player, player.getXRot(), player.getYRot()+6, 0.0F, 3.0F, 1.0F);
            termArrow3.shootFromRotation(player, player.getXRot(), player.getYRot()-6, 0.0F, 3.0F, 1.0F);
            player.swing(InteractionHand.MAIN_HAND,true);

            world.addFreshEntity(termArrow1);
            world.addFreshEntity(termArrow2);
            world.addFreshEntity(termArrow3);
        }
        if (!player.getCooldowns().isOnCooldown(player.getMainHandItem().getItem()))
            player.getCooldowns().addCooldown(player.getMainHandItem().getItem(), 6);
    }

    @SubscribeEvent
    public void handleEndermanTeleport(LivingHurtEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof EnderMan enderman) {
            if (event.getSource().getEntity() instanceof TermArrow) {

            }
        }
    }
    @Override
    public InteractionResultHolder<ItemStack> use (Level world, Player player, InteractionHand hand) {
        shootArrow(world,player);

        return InteractionResultHolder.success(player.getItemInHand(hand));
    }
}
