package baguchan.bagus_archaeology.element;

import baguchan.bagus_archaeology.entity.AlchemyGolem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class WarpedElement extends AlchemyElement {
    public WarpedElement(Properties properties) {
        super(properties);
    }


    @Override
    public void entityAttack(AlchemyGolem entity, Entity target, float power) {
        super.entityAttack(entity, target, power);
        if (!entity.level().isClientSide()) {
            if (target instanceof LivingEntity living) {
                living.addEffect(new MobEffectInstance(MobEffects.POISON, (int) (power * 20F)), entity);
            }

        }
    }

    @Override
    public void projectileHit(Projectile projectile, HitResult hitResult, float power) {
        if (!projectile.level().isClientSide()) {
            if (hitResult instanceof EntityHitResult entityHitResult) {
                Entity entity = entityHitResult.getEntity();
                if (entity instanceof LivingEntity living) {
                    living.addEffect(new MobEffectInstance(MobEffects.POISON, (int) (power * 20F)), projectile.getOwner() != null ? projectile.getOwner() : projectile);
                }

                }
            AreaEffectCloud areaeffectcloud = new AreaEffectCloud(projectile.level(), projectile.getX(), projectile.getY(), projectile.getZ());
            if (projectile.getOwner() instanceof LivingEntity) {
                areaeffectcloud.setOwner((LivingEntity) projectile.getOwner());
            }

            areaeffectcloud.setRadius(0.25F * power);
            areaeffectcloud.setRadiusOnUse(-0.25F);
            areaeffectcloud.setWaitTime(10);
            areaeffectcloud.setRadiusPerTick(-areaeffectcloud.getRadius() / (float) areaeffectcloud.getDuration());
            areaeffectcloud.setPotion(Potions.POISON);

            projectile.level().addFreshEntity(areaeffectcloud);
            }
    }

    @Override
    public void active(Entity entity, float power) {
        if (power < 0) {
            if (!entity.level().isClientSide()) {
                if (entity instanceof LivingEntity living) {
                    living.addEffect(new MobEffectInstance(MobEffects.POISON, (int) (-power * 20F)));
                }
            }
        }
    }

    @Override
    public float getProjectileScale() {
        return super.getProjectileScale() * 0.2F;
    }

    @Override
    public float getSelfScale() {
        return super.getSelfScale() * 2.0F;
    }
}
