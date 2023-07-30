package baguchan.bagus_archaeology.registry;

import baguchan.bagus_archaeology.BagusArchaeology;
import baguchan.bagus_archaeology.biome.RemoveEarthMobsBiomeModifier;
import com.mojang.serialization.Codec;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBiomeModifiers {
    public static final DeferredRegister<Codec<? extends BiomeModifier>> BIOME_MODIFIER_SERIALIZERS = DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, BagusArchaeology.MODID);

    public static final RegistryObject<Codec<RemoveEarthMobsBiomeModifier>> REMOVE_EARTH_MOBS = BIOME_MODIFIER_SERIALIZERS.register("remove_earth_mobs_spawns", () ->
            Codec.unit(RemoveEarthMobsBiomeModifier::new)
    );
}