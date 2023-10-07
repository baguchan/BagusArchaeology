package baguchan.bagus_archaeology.element;

import baguchan.bagus_archaeology.registry.ModDamageType;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class FireElement extends AlchemyElement {
    public FireElement(Properties properties) {
        super(properties);
    }

    @Override
    public void entityAttack(Mob entity, Entity target, Item item, float power) {
        super.entityAttack(entity, target, item, power);
        if (target instanceof LivingEntity living) {
            if (power < 0) {
                living.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, (int) (power * 10F)));
            } else {
                if (living.isOnFire()) {
                    living.setSecondsOnFire((int) (power * 0.1F));
                }
                living.hurt(entity.damageSources().source(ModDamageType.FIRE, entity), power * 0.1F);
            }
        }
    }

    @Override
    public void projectileHit(Projectile projectile, HitResult hitResult, Item item, float power) {
        if (hitResult instanceof EntityHitResult entityHitResult && entityHitResult.getEntity() instanceof LivingEntity living) {
            if (power < 0) {
                living.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, (int) (power * 10F)));
            } else {
                if (living.isOnFire()) {
                    living.setSecondsOnFire((int) (power * 0.1F));
                }
                living.hurt(projectile.damageSources().source(ModDamageType.FIRE, projectile, projectile.getOwner()), power * 0.1F);
            }
        }
    }

    @Override
    public void active(Entity entity, Item item, float power) {
        if (entity instanceof LivingEntity living) {
            if (power < 0) {
                living.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, (int) (power * 10F)));
            } else {
                if (living.isOnFire()) {
                    living.setSecondsOnFire((int) (power * 0.1F));
                }
                living.hurt(entity.damageSources().source(ModDamageType.FIRE, entity), power * 0.1F);
            }
        }
    }

    @Override
    public float getSelfPostScale() {
        return 1.1F;
    }

    @Override
    public float getProjectilePostScale() {
        return 0.8F;
    }
}
