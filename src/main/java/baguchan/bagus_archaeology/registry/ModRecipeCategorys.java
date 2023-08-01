package baguchan.bagus_archaeology.registry;

import baguchan.bagus_archaeology.BagusArchaeology;
import baguchan.bagus_archaeology.recipe.SoulBookCategory;
import baguchan.bagus_archaeology.recipe.SoulRecoverRecipe;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.RecipeBookCategories;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterRecipeBookCategoriesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = BagusArchaeology.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModRecipeCategorys {
    public static final Supplier<RecipeBookCategories> SOUL_RECOVER_SEARCH = Suppliers.memoize(() -> RecipeBookCategories.create("SOUL_RECOVER_SEARCH", new ItemStack(Items.COMPASS)));
    public static final Supplier<RecipeBookCategories> SOUL_RECOVER_FOSSIL = Suppliers.memoize(() -> RecipeBookCategories.create("SOUL_RECOVER_FOSSIL", new ItemStack(ModItems.PIGMAN_SKULL.get())));

    public static final Supplier<RecipeBookCategories> SOUL_RECOVER_MISC = Suppliers.memoize(() -> RecipeBookCategories.create("SOUL_RECOVER_MISC", new ItemStack(Items.ARMOR_STAND)));
    public static final Supplier<RecipeBookCategories> SOUL_RECOVER_GOLEM = Suppliers.memoize(() -> RecipeBookCategories.create("SOUL_RECOVER_GOLEM", new ItemStack(Items.CARVED_PUMPKIN)));

    @SubscribeEvent
    public static void registerRecipeCategories(RegisterRecipeBookCategoriesEvent event) {
        event.registerBookCategories(ModRecipeTypes.SOUL_RECOVER_TYPE, ImmutableList.of(SOUL_RECOVER_SEARCH.get(), SOUL_RECOVER_MISC.get(), SOUL_RECOVER_FOSSIL.get(), SOUL_RECOVER_GOLEM.get()));
        event.registerAggregateCategory(SOUL_RECOVER_SEARCH.get(), ImmutableList.of(SOUL_RECOVER_MISC.get(), SOUL_RECOVER_FOSSIL.get(), SOUL_RECOVER_GOLEM.get()));
        event.registerRecipeCategoryFinder(ModRecipeTypes.SOUL_RECOVER.get(), (recipe) -> {
            if (recipe instanceof SoulRecoverRecipe recoverRecipe) {
                SoulBookCategory tab = recoverRecipe.soulCategory();
                if (tab != null) {
                    return switch (tab) {
                        case FOSSIL -> SOUL_RECOVER_FOSSIL.get();
                        case GOLEM -> SOUL_RECOVER_GOLEM.get();
                        case MISC -> SOUL_RECOVER_MISC.get();
                    };
                }
            }
            return SOUL_RECOVER_MISC.get();
        });
    }
}
