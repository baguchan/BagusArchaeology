package baguchan.bagus_archaeology.data;

import baguchan.bagus_archaeology.BagusArcheology;
import baguchan.bagus_archaeology.registry.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class ItemTagGenerator extends ItemTagsProvider {
    public ItemTagGenerator(PackOutput p_255871_, CompletableFuture<HolderLookup.Provider> p_256035_, CompletableFuture<TagsProvider.TagLookup<Block>> p_256467_, ExistingFileHelper exFileHelper) {
        super(p_255871_, p_256035_, p_256467_, BagusArcheology.MODID, exFileHelper);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ItemTags.TRIMMABLE_ARMOR).add(ModItems.STUDDED_BOOTS.get(), ModItems.STUDDED_LEGGINGS.get(), ModItems.STUDDED_CHESTPLATE.get(), ModItems.STUDDED_HELMET.get());
        tag(ItemTags.FREEZE_IMMUNE_WEARABLES).add(ModItems.STUDDED_BOOTS.get(), ModItems.STUDDED_LEGGINGS.get(), ModItems.STUDDED_CHESTPLATE.get(), ModItems.STUDDED_HELMET.get());
    }
}