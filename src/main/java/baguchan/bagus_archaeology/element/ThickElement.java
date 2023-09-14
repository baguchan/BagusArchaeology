package baguchan.bagus_archaeology.element;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class ThickElement extends AlchemyElement {
    public ThickElement(Properties properties) {
        super(properties);
    }

    @Override
    public void projectileHit(Projectile projectile, HitResult hitResult, Item item, float power) {
        if (hitResult instanceof EntityHitResult entityHitResult) {
            Entity entity = entityHitResult.getEntity();
            if (entity instanceof LivingEntity living) {
                living.addEffect(new MobEffectInstance(MobEffects.GLOWING, (int) (20 * power)));
            }
        }
    }

    @Override
    public void active(Entity entity, Item item, float power) {
        if (entity instanceof LivingEntity living) {
            living.addEffect(new MobEffectInstance(MobEffects.GLOWING, (int) (20 * power)));
        }
    }

    public float getProjectileScale() {
        return 1.05F;
    }

    public float getSelfScale() {
        return 1.15F;
    }
}
