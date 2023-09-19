package baguchan.bagus_archaeology.compat.jei;

import baguchan.bagus_archaeology.RelicsAndAlchemy;
import baguchan.bagus_archaeology.material.AlchemyMaterial;
import baguchan.bagus_archaeology.registry.ModBlocks;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.List;
import java.util.stream.Stream;

@JeiPlugin
public class JEIPlugin implements IModPlugin {
    public static final ResourceLocation PLUGIN_ID = new ResourceLocation(RelicsAndAlchemy.MODID, "jei_plugin");

    private static final Minecraft MC = Minecraft.getInstance();

    private static <C extends Container, T extends Recipe<C>> List<T> findRecipesByType(RecipeType<T> type) {
        return MC.level.getRecipeManager().getAllRecipesFor(type);
    }

    public static final mezz.jei.api.recipe.RecipeType<AlchemyRecipe> ALCHEMY_RECIPE_TYPE =
            mezz.jei.api.recipe.RecipeType.create(RelicsAndAlchemy.MODID, "alchemy", AlchemyRecipe.class);


    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        registry.addRecipeCategories(new AlchemyCategory(registry.getJeiHelpers().getGuiHelper()));
    }

    public static List<AlchemyRecipe> getAlchemyRecipes() {
        Stream<Holder.Reference<AlchemyMaterial>> referenceOptional = RelicsAndAlchemy.registryAccess().lookup(AlchemyMaterial.REGISTRY_KEY).get().listElements();
        return referenceOptional.toList().stream().map(Holder::get)
                .map(AlchemyMaterial::getItem)
                .map(Item::getDefaultInstance)
                .<AlchemyRecipe>mapMulti((stack, consumer) -> {
                    consumer.accept(new AlchemyRecipe(List.of(stack)));

                })
                .toList();
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(ALCHEMY_RECIPE_TYPE, getAlchemyRecipes());
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.ALCHEMY_CAULDRON.get()), ALCHEMY_RECIPE_TYPE);
    }


    @Override
    public ResourceLocation getPluginUid() {
        return PLUGIN_ID;
    }
}