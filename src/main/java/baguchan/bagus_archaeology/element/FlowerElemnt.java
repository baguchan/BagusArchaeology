package baguchan.bagus_archaeology.element;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class FlowerElemnt extends AlchemyElement {
    public FlowerElemnt(Properties properties) {
        super(properties);
    }

    @Override
    public void projectileHit(Projectile projectile, HitResult hitResult, Item item, float power) {
        if (hitResult instanceof EntityHitResult entityHitResult && entityHitResult.getEntity() instanceof LivingEntity living) {
            if (item instanceof BlockItem blockItem && blockItem.getBlock() instanceof FlowerBlock flowerBlock) {
                living.addEffect(new MobEffectInstance(flowerBlock.getSuspiciousEffect(), (int) (flowerBlock.getEffectDuration() + 20 * power)));
            }
        }
    }

    @Override
    public void active(Entity entity, Item item, float power) {
        if (entity instanceof LivingEntity living) {
            if (item instanceof BlockItem blockItem && blockItem.getBlock() instanceof FlowerBlock flowerBlock) {
                living.addEffect(new MobEffectInstance(flowerBlock.getSuspiciousEffect(), (int) (flowerBlock.getEffectDuration() + 20 * power)));
            }
        }
    }

    public boolean isUsableConstruct() {
        return false;
    }
}
