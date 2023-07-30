package baguchan.bagus_archeology.registry;

import baguchan.bagus_archeology.BagusArcheology;
import baguchan.bagus_archeology.biome.RemoveEarthMobsBiomeModifier;
import com.mojang.serialization.Codec;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBiomeModifiers {
    public static final DeferredRegister<Codec<? extends BiomeModifier>> BIOME_MODIFIER_SERIALIZERS = DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, BagusArcheology.MODID);

    public static final RegistryObject<Codec<RemoveEarthMobsBiomeModifier>> REMOVE_EARTH_MOBS = BIOME_MODIFIER_SERIALIZERS.register("remove_earth_mobs_spawns", () ->
            Codec.unit(RemoveEarthMobsBiomeModifier::new)
    );
}