package baguchan.bagus_archaeology.data;

import baguchan.bagus_archaeology.RelicsAndAlchemy;
import baguchan.bagus_archaeology.registry.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import static baguchan.bagus_archaeology.RelicsAndAlchemy.prefix;

public class ItemModelGenerator extends ItemModelProvider {
    public ItemModelGenerator(PackOutput generator, ExistingFileHelper existingFileHelper) {
        super(generator, RelicsAndAlchemy.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        this.basicItem(ModItems.ALCHEMY_POTION.get());
        this.basicItem(ModItems.ALCHEMY_PROJECTILE.get());
        this.basicItem(ModItems.ALCHEMY_INGOT.get());
        this.basicItem(ModItems.ALCHEMY_SLIME.get());
        this.basicItem(ModItems.GOLEM_COMBAT_CORE.get());
    }

    private void toBlock(Block b) {
        toBlockModel(b, ForgeRegistries.BLOCKS.getKey(b).getPath());
    }

    private void toBlockModel(Block b, String model) {
        toBlockModel(b, prefix("block/" + model));
    }

    private void toBlockModel(Block b, ResourceLocation model) {
        withExistingParent(ForgeRegistries.BLOCKS.getKey(b).getPath(), model);
    }

    public ItemModelBuilder itemBlockFlat(Block block) {
        return itemBlockFlat(block, blockName(block));
    }

    public ItemModelBuilder itemBlockFlat(Block block, String name) {
        return withExistingParent(blockName(block), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/" + name));
    }

    public ItemModelBuilder egg(Item item) {
        return withExistingParent(ForgeRegistries.ITEMS.getKey(item).getPath(), mcLoc("item/template_spawn_egg"));
    }

    public String blockName(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block).getPath();
    }
}
