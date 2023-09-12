package baguchan.bagus_archaeology.element;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;

import java.util.List;

public class SoulElement extends AlchemyElement {
    public SoulElement(Properties properties) {
        super(properties);
    }

    @Override
    public void projectileHit(Projectile projectile, HitResult hitResult, float power) {
        List<Entity> list1 = projectile.level().getEntitiesOfClass(Entity.class, (new AABB(projectile.blockPosition()).inflate(2.0F * power)));
        if (projectile.level().isClientSide()) {
            projectile.level().addParticle(ParticleTypes.SONIC_BOOM, projectile.getX(), projectile.getY(), projectile.getZ(), 0, 0, 0);
        } else {
            if (!list1.isEmpty()) {
                for (Entity hurtEntity : list1) {
                    if (hurtEntity.isAttackable()) {
                        float distance = hurtEntity.distanceTo(hurtEntity);
                        projectile.hurt(hurtEntity.damageSources().sonicBoom(projectile.getOwner() != null ? projectile.getOwner() : projectile), (power * 4F / distance));

                    }
                }
            }
        }
        projectile.playSound(SoundEvents.WARDEN_SONIC_BOOM, 1.5F, 1.0F);
    }

    @Override
    public void active(Entity entity, float power) {
        if (power > 10) {
            if (entity.level().isClientSide()) {
                entity.level().addParticle(ParticleTypes.SONIC_BOOM, entity.getX(), entity.getY(), entity.getZ(), 0, 0, 0);
            } else {
                if (entity instanceof LivingEntity living) {
                    List<Entity> list1 = living.level().getEntitiesOfClass(Entity.class, (new AABB(living.blockPosition()).inflate(power)));

                    if (!list1.isEmpty()) {
                        for (Entity hurtEntity : list1) {
                            if (hurtEntity.isAttackable()) {
                                float distance = hurtEntity.distanceTo(hurtEntity);
                                living.hurt(entity.damageSources().sonicBoom(living), (power * 4F / distance));

                            }
                        }
                    }
                }
            }
            entity.playSound(SoundEvents.WARDEN_SONIC_BOOM, 1.5F, 1.0F);
        } else if (power < 0) {
            if (entity instanceof LivingEntity living) {
                living.heal(-power);
            }
        }
    }
}
