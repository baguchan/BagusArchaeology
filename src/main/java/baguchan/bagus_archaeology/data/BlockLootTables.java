package baguchan.bagus_archaeology.data;

import baguchan.bagus_archaeology.registry.ModBlocks;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.HashSet;
import java.util.Set;

public class BlockLootTables extends BlockLootSubProvider {
    private final Set<Block> knownBlocks = new HashSet<>();
    // [VanillaCopy] super
    private static final float[] DEFAULT_SAPLING_DROP_RATES = new float[]{0.05F, 0.0625F, 0.083333336F, 0.1F};
    private static final float[] RARE_SAPLING_DROP_RATES = new float[]{0.025F, 0.027777778F, 0.03125F, 0.041666668F, 0.1F};

    private static final Set<Item> EXPLOSION_RESISTANT = Set.of();


    protected BlockLootTables() {
        super(EXPLOSION_RESISTANT, FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void add(Block block, LootTable.Builder builder) {
        super.add(block, builder);
        knownBlocks.add(block);
    }

    @Override
    protected void generate() {
        dropSelf(ModBlocks.PIGMAN_SKULL.get());
        dropOther(ModBlocks.PIGMAN_SKULL_WALL.get(), ModBlocks.PIGMAN_SKULL.get());
        registerEmpty(ModBlocks.SUSPICIOUS_SOUL_SAND.get());
        registerEmpty(ModBlocks.SUSPICIOUS_SOUL_SOIL.get());
        dropOther(ModBlocks.ALCHEMY_CAULDRON.get(), Blocks.CAULDRON);
    }


    private void registerEmpty(Block b) {
        add(b, LootTable.lootTable());
    }

    private void registerSlab(Block b) {
        add(b, createSlabItemTable(b));
    }

    @Override
    public Set<Block> getKnownBlocks() {
        return knownBlocks;
    }
}
