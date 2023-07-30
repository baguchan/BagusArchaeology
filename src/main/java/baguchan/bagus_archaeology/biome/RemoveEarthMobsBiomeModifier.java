package baguchan.bagus_archaeology.biome;

import baguchan.bagus_archaeology.registry.ModBiomeModifiers;
import baguchan.earthmobsmod.EarthMobsMod;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.MobSpawnSettingsBuilder;
import net.minecraftforge.common.world.ModifiableBiomeInfo;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public record RemoveEarthMobsBiomeModifier() implements BiomeModifier {
    @Override
    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (phase == Phase.REMOVE) {
            MobSpawnSettingsBuilder spawnBuilder = builder.getMobSpawnSettings();
            for (MobCategory category : MobCategory.values()) {
                if (category.isFriendly()) {
                    List<MobSpawnSettings.SpawnerData> spawns = spawnBuilder.getSpawner(category);
                    spawns.removeIf(spawnerData -> ForgeRegistries.ENTITY_TYPES.getKey(spawnerData.type).getNamespace().contains(EarthMobsMod.MODID));
                } else {
                    List<MobSpawnSettings.SpawnerData> spawns = spawnBuilder.getSpawner(category);
                    spawns.removeIf(spawnerData -> ForgeRegistries.ENTITY_TYPES.getKey(spawnerData.type).getNamespace().contains(EarthMobsMod.MODID) && ForgeRegistries.ENTITY_TYPES.getKey(spawnerData.type).getPath().contains("skeleton_wolf"));

                }
            }
        }
    }

    @Override
    public Codec<? extends BiomeModifier> codec() {
        return ModBiomeModifiers.REMOVE_EARTH_MOBS.get();
    }
}
