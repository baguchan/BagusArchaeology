package baguchan.bagus_archaeology.data;

import baguchan.bagus_archaeology.recipe.SoulBookCategory;
import baguchan.bagus_archaeology.recipe.SoulRecoverRecipe;
import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class SoulRecoverBuilder implements RecipeBuilder {
    private final Ingredient ingredient;
    private final Ingredient additionalIngredient;
    private final EntityType<?> entity;
    @Nullable
    private final CompoundTag tag;
    private final int recoverTime;
    private final Advancement.Builder advancement = Advancement.Builder.advancement();
    @Nullable
    private String group;
    private final SoulBookCategory soulBookCategory;
    private final RecipeSerializer<SoulRecoverRecipe> serializer;

    public SoulRecoverBuilder(Ingredient ingredient, Ingredient additionalIngredient, EntityType<?> entity, @Nullable CompoundTag tag, int recoverTime, RecipeSerializer<SoulRecoverRecipe> serializer, SoulBookCategory soulBookCategory) {
        this.ingredient = ingredient;
        this.additionalIngredient = additionalIngredient;
        this.entity = entity;
        this.tag = tag;
        this.recoverTime = recoverTime;
        this.soulBookCategory = soulBookCategory;
        this.serializer = serializer;
    }

    public static SoulRecoverBuilder soulRecoverBuilder(Ingredient ingredient, Ingredient additionalIngredient, EntityType<?> entity, CompoundTag tag, int recoverTime, RecipeSerializer<SoulRecoverRecipe> serializer, SoulBookCategory soulBookCategory) {
        return new SoulRecoverBuilder(ingredient, additionalIngredient, entity, tag, recoverTime, serializer, soulBookCategory);
    }

    @Override
    public SoulRecoverBuilder group(@Nullable String group) {
        this.group = group;
        return this;
    }

    @Override
    public Item getResult() {
        return Items.AIR;
    }

    @Override
    public SoulRecoverBuilder unlockedBy(String criterionName, CriterionTriggerInstance criterionTrigger) {
        this.advancement.addCriterion(criterionName, criterionTrigger);
        return this;
    }

    @Override
    public void save(Consumer<FinishedRecipe> consumer, ResourceLocation id) {
        this.ensureValid(id);
        this.advancement.parent(new ResourceLocation("recipes/root")).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id)).rewards(AdvancementRewards.Builder.recipe(id)).requirements(RequirementsStrategy.OR);
        consumer.accept(new SoulRecoverBuilder.Result(id, this.group == null ? "" : this.group, this.ingredient, this.additionalIngredient, this.entity, this.tag, this.recoverTime, this.advancement, new ResourceLocation(id.getNamespace(), "recipes/soul_recover/" + id.getPath()), this.serializer, this.soulBookCategory));
    }

    private void ensureValid(ResourceLocation id) {
        if (this.advancement.getCriteria().isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + id);
        }
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final String group;
        private final Ingredient ingredient;
        private final Ingredient additional_ingredient;
        private final EntityType<?> entity;
        private final CompoundTag tag;
        private final int recoverTime;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;
        private final RecipeSerializer<SoulRecoverRecipe> serializer;
        private final SoulBookCategory soulBookCategory;

        public Result(ResourceLocation id, String group, Ingredient ingredient, Ingredient additional_ingredient, EntityType<?> entity, @Nullable CompoundTag tag, int recoverTime, Advancement.Builder advancement, ResourceLocation advancementId, RecipeSerializer<SoulRecoverRecipe> serializer, SoulBookCategory soulBookCategory) {
            this.id = id;
            this.group = group;
            this.ingredient = ingredient;
            this.additional_ingredient = additional_ingredient;
            this.entity = entity;
            this.tag = tag;
            this.recoverTime = recoverTime;
            this.advancement = advancement;
            this.advancementId = advancementId;
            this.serializer = serializer;
            this.soulBookCategory = soulBookCategory;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            if (!this.group.isEmpty()) {
                json.addProperty("group", this.group);
            }
            json.addProperty("category", this.soulBookCategory.getSerializedName());
            json.add("ingredient", this.ingredient.toJson());
            json.add("additional_ingredient", this.additional_ingredient.toJson());
            json.addProperty("entity", EntityType.getKey(this.entity).toString());
            if (this.tag != null && !this.tag.isEmpty()) {
                json.addProperty("tag", this.tag.toString());
            }
            json.addProperty("recover_time", this.recoverTime);
        }

        @Override
        public RecipeSerializer<?> getType() {
            return this.serializer;
        }

        @Override
        public ResourceLocation getId() {
            return this.id;
        }

        @Nullable
        @Override
        public JsonObject serializeAdvancement() {
            return this.advancement.serializeToJson();
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementId() {
            return this.advancementId;
        }

        public SoulBookCategory getSoulBookCategory() {
            return soulBookCategory;
        }
    }
}