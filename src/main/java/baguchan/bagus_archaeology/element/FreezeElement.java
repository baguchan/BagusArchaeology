package baguchan.bagus_archaeology.element;

import baguchan.bagus_archaeology.registry.ModDamageType;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class FreezeElement extends AlchemyElement {
    public FreezeElement(Properties properties) {
        super(properties);
    }

    @Override
    public void entityAttack(Mob entity, Entity target, Item item, float power) {
        super.entityAttack(entity, target, item, power);
        if (target instanceof LivingEntity living) {
            if (power < 0) {
                living.setTicksFrozen((int) (living.getTicksFrozen() + 20 + power * 20));
                living.hurt(entity.damageSources().source(ModDamageType.FREEZE, entity), power * 0.1F);
            } else {
                living.setTicksFrozen(Mth.clamp((int) (living.getTicksFrozen() - power * 20), 0, living.getTicksFrozen()));
            }
        }
    }

    @Override
    public void projectileHit(Projectile projectile, HitResult hitResult, Item item, float power) {
        if (hitResult instanceof EntityHitResult entityHitResult && entityHitResult.getEntity() instanceof LivingEntity living) {
            if (power < 0) {
                living.setTicksFrozen((int) (living.getTicksFrozen() + 20 + power * 20));
                living.hurt(projectile.damageSources().source(ModDamageType.FREEZE, projectile, projectile.getOwner()), power * 0.1F);
            } else {
                living.setTicksFrozen(Mth.clamp((int) (living.getTicksFrozen() - power * 20), 0, living.getTicksFrozen()));
            }
        }
    }

    @Override
    public void active(Entity entity, Item item, float power) {
        if (entity instanceof LivingEntity living) {
            if (power < 0) {
                living.setTicksFrozen((int) (living.getTicksFrozen() + 20 + power * 20));
                living.hurt(entity.damageSources().source(ModDamageType.FREEZE, entity), power * 0.1F);
            } else {
                living.setTicksFrozen(Mth.clamp((int) (living.getTicksFrozen() - power * 20), 0, living.getTicksFrozen()));
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
