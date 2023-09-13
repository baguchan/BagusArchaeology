package baguchan.bagus_archaeology.element;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.phys.HitResult;

public class SugarElement extends AlchemyElement {
    public SugarElement(Properties properties) {
        super(properties);
    }

    @Override
    public void projectileHit(Projectile projectile, HitResult hitResult, Item item, float power) {
    }

    @Override
    public void active(Entity entity, Item item, float power) {
    }

    @Override
    public float getProjectileScale() {
        return super.getProjectileScale() * 0.2F;
    }

    @Override
    public float getSelfScale() {
        return super.getSelfScale() * 0.5F;
    }
}
