package baguchan.bagus_archaeology.element;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.phys.HitResult;

public class ExtendElement extends AlchemyElement {
    public ExtendElement(Properties properties) {
        super(properties);
    }

    @Override
    public void projectileHit(Projectile projectile, HitResult hitResult, Item item, float power) {
    }

    @Override
    public void active(Entity entity, Item item, float power) {
    }

    public float getProjectilePostScale() {
        return 1.15F;
    }

    public float getSelfPostScale() {
        return 1.05F;
    }
}
