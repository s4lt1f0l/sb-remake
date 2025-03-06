package com.swiftmod.forge;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class TeleportUtils {
    public static boolean teleport(Level world, Player player, int TELEPORT_DISTANCE) {
        HitResult teleportHitResult = player.pick(TELEPORT_DISTANCE, 0.0F, false);
        if (!(teleportHitResult.getType() == HitResult.Type.MISS || teleportHitResult.getType() == HitResult.Type.BLOCK))
            return false;
        Vec3 telportVec = teleportHitResult.getLocation();
        BlockPos teleportPos = BlockPos.containing(telportVec);
        if (world.isClientSide) {
            spawnTrail(world, player.position(), teleportPos.getCenter());
        }
        else {
            Direction playerDirection = player.getDirection();
            if (player.getXRot() > 60 && player.getXRot() <= 90) // looking down
                player.teleportTo(telportVec.x, telportVec.y+1, telportVec.z);
            else if (player.getXRot() >= 0 && player.getXRot() <= 60) // look forward
                player.teleportTo(telportVec.x-playerDirection.getStepX()*0.85, telportVec.y, telportVec.z-playerDirection.getStepZ()*0.85);
            else
                player.teleportTo(telportVec.x, telportVec.y, telportVec.z);
        }
        return true;
    }
    public static void spawnTrail(Level level, Vec3 from, Vec3 to) {
        Vec3 dir = from.vectorTo(to).normalize();
        double dist = from.distanceTo(to);
        for (int i = 0; i < dist; i++) {
            Vec3 point = from.add(dir.multiply(i, i, i));
            level.addParticle(ParticleTypes.DRAGON_BREATH, point.x, point.y, point.z,
                    level.random.nextGaussian() * 0.05, level.random.nextGaussian() * 0.05, level.random.nextGaussian() * 0.05);
        }
    }
}
