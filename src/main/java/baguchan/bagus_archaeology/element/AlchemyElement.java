package baguchan.bagus_archaeology.element;

import baguchan.bagus_archaeology.registry.ModAlchemyElements;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Arrays;

public abstract class AlchemyElement {
    protected final AlchemyType[] alchemyTypes;
    public static final Codec<AlchemyElement> CODEC = net.minecraft.util.ExtraCodecs.lazyInitializedCodec(() -> ModAlchemyElements.ALCHEMY_ELEMENT_REGISTRY.get().getCodec());

    public AlchemyElement(Properties properties) {
        this.alchemyTypes = properties.alchemyType;
    }

    public AlchemyType[] getAlchemyTypes() {
        return alchemyTypes;
    }


    public float getProjectilePostScale() {
        return 1.0F;
    }

    public float getSelfPostScale() {
        return 1.0F;
    }


    @SubscribeEvent
    public static void projectileEvent(ProjectileImpactEvent projectileImpactEvent) {

    }

    public void entityAttack(Mob entity, Entity target, Item item, float power) {

    }


    public abstract void projectileHit(Projectile projectile, HitResult hitResult, Item item, float power);

    public abstract void active(Entity entity, Item item, float power);

    public final boolean isCompatibleWith(AlchemyElement alchemyElement) {
        return this.canApplyTogether(alchemyElement) && alchemyElement.canApplyTogether(this);
    }


    public boolean isUsableDrink() {
        return Arrays.stream(this.alchemyTypes).noneMatch(alchemyType -> {
            return alchemyType == AlchemyType.CONSTRUCT;
        });
    }

    public boolean isUsableConstruct() {
        return true;
    }

    /**
     * Determines if the enchantment passed can be applyied together with this enchantment.
     */
    protected boolean canApplyTogether(AlchemyElement alchemyElement) {
        return this != alchemyElement;
    }


    public static class Properties {
        private final AlchemyType[] alchemyType;

        public Properties(AlchemyType[] alchemyType) {
            this.alchemyType = alchemyType;
        }
    }

    public static enum AlchemyType {
        PROJECTILE(),
        NETURAL(),
        CONSTRUCT(),
        SELF(),
        CORE();
    }
}