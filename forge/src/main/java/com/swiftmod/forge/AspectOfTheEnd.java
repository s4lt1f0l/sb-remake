package com.swiftmod.forge;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.Nullable;
import java.util.List;


public class AspectOfTheEnd extends SwordItem {
    private static final int TELEPORT_DISTANCE = 7;
    private static final int ETHERWARP_DISTANCE = 46;
    private static final int ETHERWARP_COOLDOWN = 20;
    private static final Logger log = LoggerFactory.getLogger(AspectOfTheEnd.class);

    public AspectOfTheEnd(Tier tier, int attackDamage, float attackSpeed, Properties properties) {
        super(tier, attackDamage, attackSpeed, properties);
    }

    private boolean etherwarp(Level world, Player player, int ETHERWARP_DISTANCE) {
        BlockHitResult warpHitResult = (BlockHitResult) player.pick(ETHERWARP_DISTANCE, 0.0F, false);
        if (warpHitResult.getType() == HitResult.Type.BLOCK && warpHitResult.getType() != HitResult.Type.MISS) {
            BlockPos warpBlockPos = warpHitResult.getBlockPos();
            if (world.getBlockState(warpBlockPos.above()).isAir() && world.getBlockState(warpBlockPos.above(2)).isAir()) {
                if (world.isClientSide)
                    TeleportUtils.spawnTrail(world, player.position(), warpBlockPos.getCenter());
                else {
                    player.teleportTo(warpBlockPos.getX() + 0.5, warpBlockPos.getY() + 1, warpBlockPos.getZ() + 0.5);
                    player.sendSystemMessage(Component.literal("WARP SUCCESS"));
                    player.getCooldowns().addCooldown(this, ETHERWARP_COOLDOWN);
                }
                return true;
            } else {
                player.sendSystemMessage(Component.literal("BLOCK IS OCCUPIED, WARP FAILED"));
            }
        }
        return false;
    }

    @Override
    public InteractionResultHolder<ItemStack> use (Level world, Player player, InteractionHand hand) {
        if (player.isShiftKeyDown()) {
            etherwarp(world, player, ETHERWARP_DISTANCE);
        } else {
            TeleportUtils.teleport(world, player, TELEPORT_DISTANCE);
        }
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> list, TooltipFlag flag) {
        list.add(Component.literal("§6Ability: Instant Transmission §e§lRIGHT CLICK"));
        list.add(Component.literal("§7Teleport §a%d blocks§7 ahead of".formatted(TELEPORT_DISTANCE)));
        list.add(Component.literal("§7you"));
        list.add(Component.literal("§6Ability: Ether Transmission §e§lSNEAK RIGHT CLICK"));
        list.add(Component.literal("§7Teleport to your targeted block"));
        list.add(Component.literal("§7up to §a%d blocks §7away".formatted(ETHERWARP_DISTANCE)));
    }
}

