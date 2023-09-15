package baguchan.bagus_archaeology.registry;

import baguchan.bagus_archaeology.RelicsAndAlchemy;
import baguchan.bagus_archaeology.recipe.AlchemySmithingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, RelicsAndAlchemy.MODID);

    public static final RegistryObject<RecipeSerializer<?>> RECIPE_ALCHEMY_SMITHING = RECIPE_SERIALIZERS.register("alchemy_smithing", AlchemySmithingRecipe.Serializer::new);

    static <T extends Recipe<?>> RecipeType<T> register(final String p_44120_) {
        return new RecipeType<T>() {
            public String toString() {
                return p_44120_;
            }
        };
    }
}