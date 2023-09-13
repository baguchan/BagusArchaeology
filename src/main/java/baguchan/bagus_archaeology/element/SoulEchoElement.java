package baguchan.bagus_archaeology.element;

import baguchan.bagus_archaeology.entity.AlchemyGolem;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.List;

public class SoulEchoElement extends AlchemyElement {
    public SoulEchoElement(Properties properties) {
        super(properties);
    }

    @Override
    public void entityAttack(AlchemyGolem entity, Entity target, Item item, float power) {
        super.entityAttack(entity, target, item, power);
        if (power > 0) {
            if (entity.level().isClientSide()) {
                entity.level().addParticle(ParticleTypes.SONIC_BOOM, entity.getX(), entity.getY(), entity.getZ(), 0, 0, 0);
            } else {
                List<Entity> list1 = entity.level().getEntitiesOfClass(Entity.class, (new AABB(entity.blockPosition()).inflate(power * 0.25F)));

                if (!list1.isEmpty()) {
                    for (Entity hurtEntity : list1) {
                        if (hurtEntity != entity && hurtEntity.isAttackable() && (!(hurtEntity instanceof LivingEntity hurtLiving) || entity.canAttack(hurtLiving))) {
                            float distance = entity.distanceTo(hurtEntity);
                            hurtEntity.hurt(hurtEntity.damageSources().sonicBoom(entity), (power * 1F / distance));
                        }
                    }
                }
            }
            entity.playSound(SoundEvents.WARDEN_SONIC_BOOM, 1.5F, 1.0F);
        }
    }

    @Override
    public void projectileHit(Projectile projectile, HitResult hitResult, Item item, float power) {
        if (power > 0) {
            if (projectile.level().isClientSide()) {
                projectile.level().addParticle(ParticleTypes.SONIC_BOOM, projectile.getX(), projectile.getY(), projectile.getZ(), 0, 0, 0);
            } else {
                List<Entity> list1 = projectile.level().getEntitiesOfClass(Entity.class, (new AABB(projectile.blockPosition()).inflate(power * 0.5F)));

                if (!list1.isEmpty()) {
                    for (Entity hurtEntity : list1) {
                        if (hurtEntity.isAttackable()) {
                            float distance = projectile.distanceTo(hurtEntity);
                            hurtEntity.hurt(hurtEntity.damageSources().sonicBoom(projectile.getOwner() != null ? projectile.getOwner() : projectile), (power * 1.5F / distance));

                        }
                    }
                }
            }
            projectile.playSound(SoundEvents.WARDEN_SONIC_BOOM, 1.5F, 1.0F);
        } else if (power < 0) {
            if (hitResult instanceof EntityHitResult hitResult1 && hitResult1.getEntity() instanceof LivingEntity living) {
                living.heal(-power);
            }
        }
    }

    @Override
    public void active(Entity entity, Item item, float power) {
        if (power > 10) {
            if (entity.level().isClientSide()) {
                entity.level().addParticle(ParticleTypes.SONIC_BOOM, entity.getX(), entity.getY(), entity.getZ(), 0, 0, 0);
            } else {
                if (entity instanceof LivingEntity living) {
                    List<Entity> list1 = living.level().getEntitiesOfClass(Entity.class, (new AABB(living.blockPosition()).inflate(power * 0.7F)));

                    if (!list1.isEmpty()) {
                        for (Entity hurtEntity : list1) {
                            if (hurtEntity.isAttackable()) {
                                float distance = entity.distanceTo(hurtEntity);
                                living.hurt(entity.damageSources().sonicBoom(living), (power / distance));

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
