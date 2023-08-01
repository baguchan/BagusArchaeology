package baguchan.bagus_archaeology.registry;

import baguchan.bagus_archaeology.BagusArchaeology;
import baguchan.bagus_archaeology.recipe.SoulRecoverRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipeTypes {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, BagusArchaeology.MODID);

    public static final RegistryObject<RecipeType<SoulRecoverRecipe>> SOUL_RECOVER = RECIPE_TYPES.register("soul_recover", () -> RecipeType.simple(new ResourceLocation(BagusArchaeology.MODID, "soul_recover")));
    public static final RecipeBookType SOUL_RECOVER_TYPE = RecipeBookType.create("SOUL_RECOVER");

}