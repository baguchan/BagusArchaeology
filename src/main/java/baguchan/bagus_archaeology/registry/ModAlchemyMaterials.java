package baguchan.bagus_archaeology.registry;

import baguchan.bagus_archaeology.RelicsAndAlchemy;
import baguchan.bagus_archaeology.material.AlchemyMaterial;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;

public class ModAlchemyMaterials {
    public static final DeferredRegister<AlchemyMaterial> ALCHEMY_MATERIAL = DeferredRegister.create(AlchemyMaterial.REGISTRY_KEY,
            RelicsAndAlchemy.MODID);

    public static final ResourceKey<AlchemyMaterial> GUNPOWDER = key(new ResourceLocation(RelicsAndAlchemy.MODID, "gunpowder"));

    public static final ResourceKey<AlchemyMaterial> GLOWSTONE_DUST = key(new ResourceLocation(RelicsAndAlchemy.MODID, "glowstone_dust"));
    public static final ResourceKey<AlchemyMaterial> ECHO_SHARD = key(new ResourceLocation(RelicsAndAlchemy.MODID, "echo_shard"));

    public static ResourceKey<AlchemyMaterial> key(ResourceLocation name) {
        return ResourceKey.create(AlchemyMaterial.REGISTRY_KEY, name);
    }

}