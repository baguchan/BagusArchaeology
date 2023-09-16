package baguchan.bagus_archaeology.element;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class TeleportElement extends AlchemyElement {
    public TeleportElement(Properties properties) {
        super(properties);
    }


    @Override
    public void projectileHit(Projectile projectile, HitResult hitResult, Item item, float power) {
        if (hitResult instanceof EntityHitResult entityHitResult && entityHitResult.getEntity() instanceof LivingEntity living) {
            if (!living.level().isClientSide) {
                if (living.isPassenger()) {
                    living.stopRiding();
                }

                double d0 = living.getX();
                double d1 = living.getY();
                double d2 = living.getZ();

                int teleportPower = Mth.abs((int) (power * 0.5));
                int teleportPower2 = Mth.abs((int) (power * 0.25));

                Vec3 vec3 = living.position();
                for (int i = 0; i < 16 + teleportPower; ++i) {
                    double d3 = living.getX() + (living.getRandom().nextDouble() - 0.5D) * (16.0D + teleportPower);
                    double d4 = Mth.clamp(living.getY() + (double) (living.getRandom().nextInt((int) (16 + teleportPower)) - (8 + teleportPower2)), (double) living.level().getMinBuildHeight(), (double) (living.level().getMinBuildHeight() + ((ServerLevel) living.level()).getLogicalHeight() - 1));
                    double d5 = living.getZ() + (living.getRandom().nextDouble() - 0.5D) * (16.0D + teleportPower);
                    if (living.isPassenger()) {
                        living.stopRiding();
                    }
                    if (living.randomTeleport(d3, d4, d5, true)) {
                        living.level().gameEvent(GameEvent.TELEPORT, vec3, GameEvent.Context.of(living));
                        SoundEvent soundevent = SoundEvents.CHORUS_FRUIT_TELEPORT;
                        living.level().playSound((Player) null, d0, d1, d2, soundevent, SoundSource.PLAYERS, 1.0F, 1.0F);
                        living.playSound(soundevent, 1.0F, 1.0F);
                    }
                }
            }
        }
    }

    @Override
    public void active(Entity entity, Item item, float power) {
        if (entity instanceof LivingEntity living) {
            if (!living.level().isClientSide) {
                if (living.isPassenger()) {
                    living.stopRiding();
                }

                double d0 = living.getX();
                double d1 = living.getY();
                double d2 = living.getZ();

                int teleportPower = Mth.abs((int) (power * 0.5));
                int teleportPower2 = Mth.abs((int) (power * 0.25));

                Vec3 vec3 = living.position();
                for (int i = 0; i < 16 + teleportPower; ++i) {
                    double d3 = living.getX() + (living.getRandom().nextDouble() - 0.5D) * (16.0D + teleportPower);
                    double d4 = Mth.clamp(living.getY() + (double) (living.getRandom().nextInt((int) (16 + teleportPower)) - (8 + teleportPower2)), (double) living.level().getMinBuildHeight(), (double) (living.level().getMinBuildHeight() + ((ServerLevel) living.level()).getLogicalHeight() - 1));
                    double d5 = living.getZ() + (living.getRandom().nextDouble() - 0.5D) * (16.0D + teleportPower);
                    if (living.isPassenger()) {
                        living.stopRiding();
                    }
                    if (living.randomTeleport(d3, d4, d5, true)) {
                        living.level().gameEvent(GameEvent.TELEPORT, vec3, GameEvent.Context.of(living));
                        SoundEvent soundevent = SoundEvents.CHORUS_FRUIT_TELEPORT;
                        living.level().playSound((Player) null, d0, d1, d2, soundevent, SoundSource.PLAYERS, 1.0F, 1.0F);
                        living.playSound(soundevent, 1.0F, 1.0F);
                    }
                }
            }
        }
    }

    public boolean isUsableConstruct() {
        return false;
    }

    @Override
    public float getProjectileScale() {
        return super.getProjectileScale() * 0.65F;
    }

    @Override
    public float getSelfScale() {
        return super.getSelfScale() * 0.5F;
    }
}
