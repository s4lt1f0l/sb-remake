package com.swiftmod.forge;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.Level;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


public class Hyperion extends SwordItem {
    public static final int TELEPORT_DISTANCE =  10;
    public static final int DURATION_SECOND = 5;
    public static final int COOLDOWN_SECOND = 10;
    public static final int TICK_PER_SECOND = 20;

    public static final Set<UUID> shieldedPlayers = new HashSet<>();
    public Hyperion(Tiers tier,int attackDamage, float attackSpeed, Item.Properties properties) {
        super(tier,attackDamage,attackSpeed,properties);
    }

    public void explodeTNTAtPlayer(Level world,Player player) {
        if (!world.isClientSide())
            world.explode(player,player.getX(),player.getY(),player.getZ(),4.0F,Level.ExplosionInteraction.NONE);
    }

    public void applyAbsorptionShield(Level world, Player player) {
        if(!world.isClientSide())
            player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION,DURATION_SECOND*TICK_PER_SECOND,1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use (Level world, Player player, InteractionHand hand) {
        shieldedPlayers.add(player.getUUID());
        TeleportUtils.teleport(world,player,TELEPORT_DISTANCE);
        applyAbsorptionShield(world,player);
        explodeTNTAtPlayer(world,player);
        player.getCooldowns().addCooldown(player.getMainHandItem().getItem(), COOLDOWN_SECOND);
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }
}
