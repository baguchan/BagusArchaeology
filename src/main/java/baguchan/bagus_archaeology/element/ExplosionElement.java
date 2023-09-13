package baguchan.bagus_archaeology.element;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

public class ExplosionElement extends AlchemyElement {
    public ExplosionElement(Properties properties) {
        super(properties);
    }

    @Override
    public void projectileHit(Projectile projectile, HitResult hitResult, Item item, float power) {
        projectile.level().explode(projectile.getOwner() != null ? projectile.getOwner() : projectile, projectile.getX(), projectile.getY() - 0.01F, projectile.getZ(), power * 0.2F, Level.ExplosionInteraction.NONE);
    }

    @Override
    public void active(Entity entity, Item item, float power) {
        if (entity instanceof LivingEntity living) {
            entity.level().explode(null, entity.getX(), entity.getY() - 0.01F, entity.getZ(), power * 0.2F, Level.ExplosionInteraction.NONE);
        }
    }
}
