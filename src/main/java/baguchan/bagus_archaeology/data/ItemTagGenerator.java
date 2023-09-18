package baguchan.bagus_archaeology.data;

import baguchan.bagus_archaeology.RelicsAndAlchemy;
import baguchan.bagus_archaeology.registry.ModItems;
import baguchan.bagus_archaeology.registry.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class ItemTagGenerator extends ItemTagsProvider {
    public ItemTagGenerator(PackOutput p_255871_, CompletableFuture<HolderLookup.Provider> p_256035_, CompletableFuture<TagsProvider.TagLookup<Block>> p_256467_, ExistingFileHelper exFileHelper) {
        super(p_255871_, p_256035_, p_256467_, RelicsAndAlchemy.MODID, exFileHelper);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ItemTags.TRIMMABLE_ARMOR).add(ModItems.STUDDED_BOOTS.get(), ModItems.STUDDED_LEGGINGS.get(), ModItems.STUDDED_CHESTPLATE.get(), ModItems.STUDDED_HELMET.get());
        tag(ItemTags.FREEZE_IMMUNE_WEARABLES).add(ModItems.STUDDED_BOOTS.get(), ModItems.STUDDED_LEGGINGS.get(), ModItems.STUDDED_CHESTPLATE.get(), ModItems.STUDDED_HELMET.get());
        tag(ModTags.Items.ALCHEMY_ALLOW_TOOL).add(Items.TRIDENT).addTag(ItemTags.SWORDS).addTag(ItemTags.PICKAXES).addTag(ItemTags.SHOVELS).addTag(ItemTags.AXES);
        tag(ModTags.Items.INGOT_MATERIAL).add(Items.GOLD_INGOT);
        tag(ModTags.Items.PROJECTILE_MATERIAL).add(Blocks.CLAY.asItem());
    }
}