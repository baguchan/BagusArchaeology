package baguchan.bagus_archaeology.element;

import baguchan.bagus_archaeology.entity.AlchemyGolem;
import baguchan.bagus_archaeology.registry.ModAlchemyElements;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Arrays;

public abstract class AlchemyElement {
    protected final AlchemyType[] alchemyTypes;
    private final float scale;
    public static final Codec<AlchemyElement> CODEC = net.minecraft.util.ExtraCodecs.lazyInitializedCodec(() -> ModAlchemyElements.ALCHEMY_ELEMENT_REGISTRY.get().getCodec());

    public AlchemyElement(Properties properties) {
        this.alchemyTypes = properties.alchemyType;
        this.scale = properties.scale;
    }

    public AlchemyType[] getAlchemyTypes() {
        return alchemyTypes;
    }

    protected float getScale() {
        return scale;
    }

    public float getProjectileScale() {
        return this.getScale();
    }

    public float getSelfScale() {
        return this.getScale();
    }


    @SubscribeEvent
    public static void projectileEvent(ProjectileImpactEvent projectileImpactEvent) {

    }

    public void entityAttack(AlchemyGolem entity, Entity target, float power) {

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
        private final float scale;

        public Properties(AlchemyType[] alchemyType, float scale) {
            this.alchemyType = alchemyType;
            this.scale = scale;
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