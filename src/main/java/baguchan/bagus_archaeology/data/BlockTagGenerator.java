package baguchan.bagus_archaeology.data;

import baguchan.bagus_archaeology.BagusArchaeology;
import baguchan.bagus_archaeology.registry.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class BlockTagGenerator extends BlockTagsProvider {
    public BlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper exFileHelper) {
        super(output, lookupProvider, BagusArchaeology.MODID, exFileHelper);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(BlockTags.MINEABLE_WITH_SHOVEL).add(ModBlocks.SUSPICIOUS_SOUL_SAND.get()).add(ModBlocks.SUSPICIOUS_SOUL_SOIL.get());
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.SOUL_RECOVER.get());

        this.tag(BlockTags.SOUL_SPEED_BLOCKS).add(ModBlocks.SUSPICIOUS_SOUL_SAND.get()).add(ModBlocks.SUSPICIOUS_SOUL_SOIL.get());
        this.tag(BlockTags.SOUL_FIRE_BASE_BLOCKS).add(ModBlocks.SUSPICIOUS_SOUL_SAND.get()).add(ModBlocks.SUSPICIOUS_SOUL_SOIL.get());
    }
}