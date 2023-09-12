package baguchan.bagus_archaeology.element;

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

        if (!list1.isEmpty()) {
            for (Entity hurtEntity : list1) {
                if (hurtEntity.isAttackable()) {
                    float distance = hurtEntity.distanceTo(hurtEntity);
                    projectile.hurt(hurtEntity.damageSources().sonicBoom(projectile), (power * 4F / distance));

                }
            }
        }
    }

    @Override
    public void active(Entity entity, float power) {
        if (entity instanceof LivingEntity living) {
            List<Entity> list1 = living.level().getEntitiesOfClass(Entity.class, (new AABB(living.blockPosition()).inflate(2.0F * power)));

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
}
