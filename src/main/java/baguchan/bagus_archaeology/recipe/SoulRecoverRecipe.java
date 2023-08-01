package baguchan.bagus_archaeology.recipe;

import baguchan.bagus_archaeology.registry.ModBlocks;
import baguchan.bagus_archaeology.registry.ModRecipeSerializers;
import baguchan.bagus_archaeology.registry.ModRecipeTypes;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.crafting.CraftingHelper;

import javax.annotation.Nullable;

public class SoulRecoverRecipe implements Recipe<Container> {
    protected final ResourceLocation id;
    protected final String group;
    protected final Ingredient ingredient;
    protected final Ingredient addtionalIngredient;
    protected final EntityType<?> entity;
    protected final CompoundTag tag;
    protected final int recoverTime;
    private final SoulBookCategory category;

    public SoulRecoverRecipe(ResourceLocation id, String group, Ingredient ingredient, Ingredient addtionalIngredient, EntityType<?> entity, CompoundTag tag, int recoverTime, SoulBookCategory category) {
        this.category = category;
        this.id = id;
        this.group = group;
        this.ingredient = ingredient;
        this.addtionalIngredient = addtionalIngredient;
        this.entity = entity;
        this.tag = tag;
        this.recoverTime = recoverTime;
    }

    public Ingredient getAddtionalIngredient() {
        return addtionalIngredient;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    @Override
    public boolean matches(Container menu, Level level) {
        return this.ingredient.test(menu.getItem(0)) && this.addtionalIngredient.test(menu.getItem(1));
    }

    /**
     * @return An empty {@link ItemStack}, as there is no item output.
     */
    @Override
    public ItemStack assemble(Container menu, RegistryAccess registryAccess) {
        return ItemStack.EMPTY;
    }

    /**
     * @return The original {@link ItemStack} ingredient for Recipe Book display.
     */
    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return this.ingredient.getItems()[0];
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    public int getRecoverTime() {
        return this.recoverTime;
    }

    public EntityType<?> getEntity() {
        return this.entity;
    }

    public CompoundTag getTag() {
        return this.tag;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> nonNullList = NonNullList.create();
        nonNullList.add(this.ingredient);
        nonNullList.add(this.addtionalIngredient);
        return nonNullList;
    }

    @Override
    public String getGroup() {
        return this.group;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(ModBlocks.SOUL_RECOVER.get());
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.SOUL_RECOVER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipeTypes.SOUL_RECOVER.get();
    }

    public SoulBookCategory soulCategory() {
        return this.category;
    }

    public static class Serializer implements RecipeSerializer<SoulRecoverRecipe> {
        @Override
        public SoulRecoverRecipe fromJson(ResourceLocation recipeLocation, JsonObject jsonObject) {
            String group = GsonHelper.getAsString(jsonObject, "group", "");
            final String tabKeyIn = GsonHelper.getAsString(jsonObject, "category", null);
            SoulBookCategory soulBookCategory = SoulBookCategory.findByName(tabKeyIn);
            Ingredient ingredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(jsonObject, "ingredient"));
            Ingredient additional_Ingredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(jsonObject, "additional_ingredient"));
            EntityType<?> entityType = EntityType.byString(GsonHelper.getAsString(jsonObject, "entity")).orElseThrow(() -> new JsonSyntaxException("Entity type cannot be found"));
            CompoundTag tag = null;
            if (jsonObject.has("tag")) {
                tag = CraftingHelper.getNBT(jsonObject.get("tag"));
            }
            int recoverTime = GsonHelper.getAsInt(jsonObject, "recover_time", 2500);
            return new SoulRecoverRecipe(recipeLocation, group, ingredient, additional_Ingredient, entityType, tag, recoverTime, soulBookCategory);
        }

        @Nullable
        @Override
        public SoulRecoverRecipe fromNetwork(ResourceLocation recipeLocation, FriendlyByteBuf buffer) {
            String group = buffer.readUtf();
            SoulBookCategory tabIn = SoulBookCategory.findByName(buffer.readUtf());
            Ingredient ingredient = Ingredient.fromNetwork(buffer);
            Ingredient additional_Ingredient = Ingredient.fromNetwork(buffer);

            EntityType<?> entityType = EntityType.byString(buffer.readUtf()).orElseThrow(() -> new JsonSyntaxException("Entity type cannot be found"));
            CompoundTag tag = null;
            if (buffer.readBoolean()) {
                tag = buffer.readNbt();
            }
            int recoverTime = buffer.readVarInt();
            return new SoulRecoverRecipe(recipeLocation, group, ingredient, additional_Ingredient, entityType, tag, recoverTime, tabIn);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, SoulRecoverRecipe recipe) {
            buffer.writeUtf(recipe.group);
            buffer.writeUtf(recipe.soulCategory() != null ? recipe.soulCategory().toString() : "");
            recipe.ingredient.toNetwork(buffer);
            recipe.addtionalIngredient.toNetwork(buffer);
            buffer.writeUtf(EntityType.getKey(recipe.getEntity()).toString());
            if (recipe.tag != null) {
                buffer.writeBoolean(true);
                buffer.writeNbt(recipe.tag);
            } else {
                buffer.writeBoolean(false);
            }
            buffer.writeVarInt(recipe.getRecoverTime());
        }
    }
}