package baguchan.bagus_archaeology.recipe;

import baguchan.bagus_archaeology.registry.ModRecipes;
import baguchan.bagus_archaeology.util.AlchemyData;
import baguchan.bagus_archaeology.util.AlchemyUtils;
import com.google.gson.JsonObject;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmithingRecipe;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.stream.Stream;

public class AlchemySmithingRecipe implements SmithingRecipe {
    private final ResourceLocation id;
    final Ingredient base;
    final Ingredient addition;

    public AlchemySmithingRecipe(ResourceLocation p_267235_, Ingredient p_266862_, Ingredient p_267050_) {
        this.id = p_267235_;
        this.base = p_266862_;
        this.addition = p_267050_;
    }

    public boolean matches(Container p_267224_, Level p_266798_) {
        return this.base.test(p_267224_.getItem(1)) && !AlchemyUtils.hasAlchemyMaterial(p_267224_.getItem(1)) && this.addition.test(p_267224_.getItem(0)) && p_267224_.getItem(2).isEmpty();
    }

    public ItemStack assemble(Container p_267320_, RegistryAccess p_267280_) {
        ItemStack itemstack = p_267320_.getItem(1);
        ItemStack itemstack2 = itemstack.copy();
        if (this.base.test(itemstack2)) {
            if (AlchemyUtils.hasAlchemyMaterial(p_267320_.getItem(0))) {
                List<AlchemyData> alchemyMaterialList = AlchemyUtils.getAlchemyMaterials(p_267320_.getItem(0));
                alchemyMaterialList.forEach((alchemyMaterial) -> {
                    AlchemyUtils.addAlchemyMaterialToItemStack(itemstack2, alchemyMaterial.alchemy, alchemyMaterial.alchemyScale);
                });

            }
            return itemstack2;
        }

        return ItemStack.EMPTY;
    }

    public ItemStack getResultItem(RegistryAccess p_266948_) {
        ItemStack itemstack = new ItemStack(Items.IRON_CHESTPLATE);
        return itemstack;
    }

    public boolean isTemplateIngredient(ItemStack p_266762_) {
        return this.addition.test(p_266762_);
    }

    public boolean isBaseIngredient(ItemStack p_266795_) {
        return this.base.test(p_266795_);
    }

    public boolean isAdditionIngredient(ItemStack p_266922_) {
        return p_266922_.isEmpty();
    }

    public ResourceLocation getId() {
        return this.id;
    }

    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.RECIPE_ALCHEMY_SMITHING.get();
    }

    public boolean isIncomplete() {
        return Stream.of(this.base, this.addition).anyMatch(net.minecraftforge.common.ForgeHooks::hasNoElements);
    }

    public static class Serializer implements RecipeSerializer<AlchemySmithingRecipe> {
        public AlchemySmithingRecipe fromJson(ResourceLocation p_267037_, JsonObject p_267004_) {
            Ingredient ingredient1 = Ingredient.fromJson(GsonHelper.getNonNull(p_267004_, "base"));
            Ingredient ingredient2 = Ingredient.fromJson(GsonHelper.getNonNull(p_267004_, "addition"));
            return new AlchemySmithingRecipe(p_267037_, ingredient1, ingredient2);
        }

        public AlchemySmithingRecipe fromNetwork(ResourceLocation p_267169_, FriendlyByteBuf p_267251_) {
            Ingredient ingredient1 = Ingredient.fromNetwork(p_267251_);
            Ingredient ingredient2 = Ingredient.fromNetwork(p_267251_);
            return new AlchemySmithingRecipe(p_267169_, ingredient1, ingredient2);
        }

        public void toNetwork(FriendlyByteBuf p_266901_, AlchemySmithingRecipe p_266893_) {
            p_266893_.base.toNetwork(p_266901_);
            p_266893_.addition.toNetwork(p_266901_);
        }
    }
}
