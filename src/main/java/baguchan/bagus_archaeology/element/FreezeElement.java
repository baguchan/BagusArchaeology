package baguchan.bagus_archaeology.element;

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
            living.setTicksFrozen((int) (living.getTicksFrozen() + 20 + power * 10));
            living.hurt(entity.damageSources().freeze(), 2F);
        }
    }

    @Override
    public void projectileHit(Projectile projectile, HitResult hitResult, Item item, float power) {
        if (hitResult instanceof EntityHitResult entityHitResult && entityHitResult.getEntity() instanceof LivingEntity living) {
            living.setTicksFrozen((int) (living.getTicksFrozen() + 20 + power * 10));
            living.hurt(projectile.damageSources().freeze(), 2F);
        }
    }

    @Override
    public void active(Entity entity, Item item, float power) {
        if (entity instanceof LivingEntity living) {
            living.setTicksFrozen((int) (living.getTicksFrozen() + 20 + power * 10));
            living.hurt(entity.damageSources().freeze(), 2F);

        }
    }

    @Override
    public float getSelfScale() {
        return 1.1F;
    }

    @Override
    public float getProjectileScale() {
        return 0.8F;
    }
}
