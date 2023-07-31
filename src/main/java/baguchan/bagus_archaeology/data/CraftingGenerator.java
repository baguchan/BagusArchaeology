package baguchan.bagus_archaeology.data;

import baguchan.bagus_archaeology.recipe.SoulBookCategory;
import baguchan.bagus_archaeology.registry.ModBlocks;
import baguchan.bagus_archaeology.registry.ModItems;
import baguchan.bagus_archaeology.registry.ModRecipeSerializers;
import baguchan.earthmobsmod.registry.ModEntities;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.function.Consumer;
import java.util.function.Supplier;

import static baguchan.bagus_archaeology.BagusArchaeology.prefix;

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
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModBlocks.SOUL_RECOVER.get())
                .pattern("III")
                .pattern("I#I")
                .pattern(" S ")
                .define('#', Items.ECHO_SHARD)
                .define('I', Items.IRON_BARS)
                .define('S', ItemTags.SOUL_FIRE_BASE_BLOCKS)
                .unlockedBy("has_item", has(Items.ECHO_SHARD))
                .save(consumer);
        soulRecivedBabySpawnRecipe(ModEntities.SKELETON_WOLF.get(), ModItems.SKELETON_WOLF_HEAD.get(), Items.LAPIS_LAZULI).save(consumer, prefix("skeleton_wolf"));
        soulRecivedBabySpawnRecipe(ModEntities.WITHER_SKELETON_WOLF.get(), ModItems.SKELETON_WOLF_HEAD.get(), Items.LAPIS_LAZULI).save(consumer, prefix("wither_skeleton_wolf"));
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
                .define('#', material.get())
                .unlockedBy("has_item", has(material.get()))
                .save(consumer, locEquip(name));
    }

    protected static SoulRecoverBuilder soulRecivedBabySpawnRecipe(EntityType<?> entity, ItemLike ingredient, ItemLike additionalIngredient) {
        CompoundTag tag = new CompoundTag();
        tag.putInt("Age", -6000);
        return SoulRecoverBuilder.soulRecoverBuilder(Ingredient.of(ingredient), Ingredient.of(additionalIngredient), entity, tag, 600, ModRecipeSerializers.SOUL_RECOVER.get(), SoulBookCategory.FOSSIL)
                .unlockedBy(getHasName(ingredient), has(ingredient));
    }

    protected final ResourceLocation locEquip(String name) {
        return prefix("equipment/" + name);
    }
}
