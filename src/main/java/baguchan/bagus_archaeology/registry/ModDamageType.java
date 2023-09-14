package baguchan.bagus_archaeology.registry;

import baguchan.bagus_archaeology.RelicsAndAlchemy;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;

public interface ModDamageType {
    ResourceKey<DamageType> FREEZE = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(RelicsAndAlchemy.MODID, "freeze"));

}