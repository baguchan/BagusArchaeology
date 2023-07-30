package baguchan.bagus_archeology.registry;

import baguchan.bagus_archeology.BagusArcheology;
import baguchan.bagus_archeology.loot.LootInLootModifier;
import baguchan.bagus_archeology.loot.OneItemLootModifier;
import com.mojang.serialization.Codec;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModLootModifiers {
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIERS = DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, BagusArcheology.MODID);

    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> LOOT_IN_LOOT = LOOT_MODIFIERS.register("loot_in_loot", LootInLootModifier.CODEC);
    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> ONE_ITEM_LOOT = LOOT_MODIFIERS.register("one_item_loot", OneItemLootModifier.CODEC);
}
