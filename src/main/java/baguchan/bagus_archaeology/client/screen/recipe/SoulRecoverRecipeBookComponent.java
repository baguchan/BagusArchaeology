package baguchan.bagus_archaeology.client.screen.recipe;

import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class SoulRecoverRecipeBookComponent extends RecipeBookComponent {
    private static final Component FILTER_NAME = Component.translatable("gui.bagus_archaeology.recipebook.toggleRecipes.archaeology");

    @Override
    protected void initFilterButtonTextures() {
        this.filterButton.initTextureValues(152, 182, 28, 18, RECIPE_BOOK_LOCATION);
    }

    @Override
    public void slotClicked(@Nullable Slot slot) {
        super.slotClicked(slot);
        if (slot != null && slot.index < this.menu.getSize()) {
            this.ghostRecipe.clear();
        }
    }

    @Override
    public void setupGhostRecipe(@Nonnull Recipe<?> recipe, List<Slot> slots) {
        this.ghostRecipe.setRecipe(recipe);

        Ingredient ingredient = recipe.getIngredients().get(0);
        if (!ingredient.isEmpty()) {
            Slot slot1 = slots.get(0);
            this.ghostRecipe.addIngredient(ingredient, slot1.x, slot1.y);
        }

        Ingredient ingredient2 = recipe.getIngredients().get(1);
        if (!ingredient2.isEmpty()) {
            Slot slot = slots.get(1);
            this.ghostRecipe.addIngredient(ingredient2, slot.x, slot.y);
        }
    }

    @Override
    @Nonnull
    protected Component getRecipeFilterName() {
        return FILTER_NAME;
    }
}