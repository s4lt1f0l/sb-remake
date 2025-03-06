package com.swiftmod.forge.init;

import com.swiftmod.forge.Terminator;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class OnPlayerAttackEvent {
    @SubscribeEvent
    public static void onPlayerAttack(AttackEntityEvent event ) {
        Player player = event.getEntity();
        Level world = player.level();
        if(!world.isClientSide() && player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof Terminator) {
            Terminator.shootArrow(world,player);
        }
    }

    @SubscribeEvent
    public static void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        Player player = event.getEntity();
        Level world = player.level();
        if(!world.isClientSide() && player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof Terminator) {
            Terminator.shootArrow(world,player);
        }
    }

    @SubscribeEvent
    public static void onLeftClickEmpty(PlayerInteractEvent.LeftClickEmpty event) {
        Player player = event.getEntity();
        Level world = player.level();
        if(player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof Terminator) {
            Terminator.shootArrow(world,player);
        }
    }
}
