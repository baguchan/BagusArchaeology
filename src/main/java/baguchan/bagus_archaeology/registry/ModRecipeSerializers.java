package baguchan.bagus_archaeology.registry;

import baguchan.bagus_archaeology.BagusArchaeology;
import baguchan.bagus_archaeology.recipe.SoulRecoverRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, BagusArchaeology.MODID);

    public static final RegistryObject<RecipeSerializer<SoulRecoverRecipe>> SOUL_RECOVER = RECIPE_SERIALIZERS.register("soul_recover", SoulRecoverRecipe.Serializer::new);
}