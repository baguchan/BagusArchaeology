package baguchan.bagus_archaeology.element;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class WarpedElement extends AlchemyElement {
    public WarpedElement(Properties properties) {
        super(properties);
    }

    @Override
    public void projectileHit(Projectile projectile, HitResult hitResult, float power) {
        if (power < 0) {
            if (hitResult instanceof EntityHitResult entityHitResult) {
                Entity entity = entityHitResult.getEntity();
                if (entity instanceof LivingEntity living) {
                    living.addEffect(new MobEffectInstance(MobEffects.POISON, (int) (-power * 40F)), projectile.getOwner() != null ? projectile.getOwner() : projectile);
                }
            }
        }
    }

    @Override
    public void active(Entity entity, float power) {
        if (power < 0) {
            if (entity instanceof LivingEntity living) {
                living.addEffect(new MobEffectInstance(MobEffects.POISON, (int) (-power * 40F)));
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
