package baguchan.bagus_archaeology.data;

import baguchan.bagus_archaeology.BagusArcheology;
import baguchan.bagus_archaeology.registry.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class CraftingGenerator extends RecipeProvider {
    public CraftingGenerator(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        helmetItem(consumer, "studded_helmet", ModItems.STUDDED_HELMET, ModItems.STUDDED_LEATHER);
        chestplateItem(consumer, "studded_chestplate", ModItems.STUDDED_CHESTPLATE, ModItems.STUDDED_LEATHER);
        leggingsItem(consumer, "studded_leggings", ModItems.STUDDED_LEGGINGS, ModItems.STUDDED_LEATHER);
        bootsItem(consumer, "studded_boots", ModItems.STUDDED_BOOTS, ModItems.STUDDED_LEATHER);

    }

    protected final void helmetItem(Consumer<FinishedRecipe> consumer, String name, Supplier<? extends ItemLike> result, Supplier<? extends ItemLike> material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result.get())
                .pattern("###")
                .pattern("# #")
                .define('#', material.get())
                .unlockedBy("has_item", has(material.get()))
                .save(consumer, locEquip(name));
    }

    protected final void chestplateItem(Consumer<FinishedRecipe> consumer, String name, Supplier<? extends ItemLike> result, Supplier<? extends ItemLike> material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result.get())
                .pattern("# #")
                .pattern("###")
                .pattern("###")
                .define('#', material.get())
                .unlockedBy("has_item", has(material.get()))
                .save(consumer, locEquip(name));
    }

    protected final void leggingsItem(Consumer<FinishedRecipe> consumer, String name, Supplier<? extends ItemLike> result, Supplier<? extends ItemLike> material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result.get())
                .pattern("###")
                .pattern("# #")
                .pattern("# #")
                .define('#', material.get())
                .unlockedBy("has_item", has(material.get()))
                .save(consumer, locEquip(name));
    }

    protected final void bootsItem(Consumer<FinishedRecipe> consumer, String name, Supplier<? extends ItemLike> result, Supplier<? extends ItemLike> material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result.get())
                .pattern("# #")
                .pattern("# #")
                .define('#', material.get());
    }

    protected final ResourceLocation locEquip(String name) {
        return BagusArcheology.prefix("equipment/" + name);
    }
}
