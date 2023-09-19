package baguchan.bagus_archaeology.compat.jei;

import baguchan.bagus_archaeology.RelicsAndAlchemy;
import baguchan.bagus_archaeology.registry.ModBlocks;
import baguchan.bagus_archaeology.registry.ModItems;
import baguchan.bagus_archaeology.registry.ModTags;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class AlchemyCategory implements IRecipeCategory<AlchemyRecipe> {

    public static final ResourceLocation UID = new ResourceLocation(RelicsAndAlchemy.MODID, "alchemy");
    protected final IDrawableAnimated arrow;
    private final Component title;
    private final IDrawable background;
    private final IDrawable icon;

    public AlchemyCategory(IGuiHelper helper) {
        title = Component.translatable("relics_and_alchemy.jei.alchemy");
        ResourceLocation backgroundImage = new ResourceLocation(RelicsAndAlchemy.MODID, "textures/gui/general_jei_recipe.png");
        background = helper.createDrawable(backgroundImage, 16, 8, 144, 54);
        icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.ALCHEMY_CAULDRON.get()));
        arrow = helper.drawableBuilder(backgroundImage, 176, 14, 24, 17).buildAnimated(200, IDrawableAnimated.StartDirection.LEFT, false);
    }

    @Override
    public ResourceLocation getRegistryName(AlchemyRecipe recipe) {
        return UID;
    }

    @Override
    public RecipeType<AlchemyRecipe> getRecipeType() {
        return JEIPlugin.ALCHEMY_RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return title;
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, AlchemyRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 20, 18).addItemStacks(recipe.getInputs());
        builder.addSlot(RecipeIngredientRole.INPUT, 74, 11).addIngredients(Ingredient.of(ModTags.Items.INGOT_MATERIAL)).addIngredients(Ingredient.of(ModTags.Items.PROJECTILE_MATERIAL)).addIngredients(Ingredient.of(ModItems.GOLEM_COMBAT_CORE.get()));
    }


    @Override
    public void draw(AlchemyRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics stack, double mouseX, double mouseY) {
        arrow.draw(stack, 72 - 17, 35 - 17);
        if (mouseX > 60 && mouseX < 70 && mouseY > 36 && mouseY < 47) {
            stack.renderComponentHoverEffect(Minecraft.getInstance().font, Minecraft.getInstance().font.getSplitter().componentStyleAtWidth(Component.translatable("relics_and_alchemy.jei.alchemy_random"), 32), (int) mouseX, (int) mouseY);
        }
        if (mouseX > 118 && mouseX < 128 && mouseY > 36 && mouseY < 47) {
            stack.renderComponentHoverEffect(Minecraft.getInstance().font, Minecraft.getInstance().font.getSplitter().componentStyleAtWidth(Component.translatable("relics_and_alchemy.jei.alchemy_random_result"), 32), (int) mouseX, (int) mouseY);
        }
    }

}