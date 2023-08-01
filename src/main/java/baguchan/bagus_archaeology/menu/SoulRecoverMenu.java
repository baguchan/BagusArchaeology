package baguchan.bagus_archaeology.menu;

import baguchan.bagus_archaeology.recipe.SoulRecoverRecipe;
import baguchan.bagus_archaeology.registry.ModMenuTypes;
import baguchan.bagus_archaeology.registry.ModRecipeTypes;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;

import java.util.Optional;

public class SoulRecoverMenu extends RecipeBookMenu<Container> {
    public final Container container;
    public final ContainerData data;
    public final Level level;

    public SoulRecoverMenu(int containerId, Inventory playerInventory) {
        this(containerId, playerInventory, new SimpleContainer(2), new SimpleContainerData(5));
    }

    public SoulRecoverMenu(int containerId, Inventory playerInventory, Container container, ContainerData data) {
        super(ModMenuTypes.SOUL_RECOVER.get(), containerId);
        checkContainerSize(container, 2);
        checkContainerDataCount(data, 5);
        this.container = container;
        this.data = data;
        this.level = playerInventory.player.level();
        this.addSlot(new Slot(container, 0, 80, 17));
        this.addSlot(new Slot(container, 1, 80, 53));
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 142));
        }
        this.addDataSlots(data);
    }

    @Override
    public void fillCraftSlotsStackedContents(StackedContents helper) {
        for (int i = 0; i < this.container.getContainerSize(); i++) {
            helper.accountSimpleStack(this.container.getItem(i));
        }
    }

    @Override
    public void clearCraftingContent() {
        this.getSlot(0).set(ItemStack.EMPTY);
        this.getSlot(1).set(ItemStack.EMPTY);
    }

    @Override
    public boolean recipeMatches(Recipe<? super Container> recipe) {
        return recipe.matches(this.container, this.level);
    }

    @Override
    public int getResultSlotIndex() {
        return -1;
    }

    @Override
    public int getGridWidth() {
        return 1;
    }

    @Override
    public int getGridHeight() {
        return 1;
    }

    @Override
    public int getSize() {
        return 2;
    }

    @Override
    public boolean stillValid(Player player) {
        return this.container.stillValid(player);
    }

    @Override
    public ItemStack quickMoveStack(Player p_39490_, int p_39491_) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(p_39491_);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (p_39491_ == 0) {
                if (!this.moveItemStackTo(itemstack1, 2, 38, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (p_39491_ == 1) {
                if (!this.moveItemStackTo(itemstack1, 2, 38, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 2, 38, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(p_39490_, itemstack1);
        }

        return itemstack;
    }

    protected boolean canRecover(ItemStack stack) {
        Optional<SoulRecoverRecipe> recoverRecipe = this.level.getRecipeManager().getRecipeFor(ModRecipeTypes.SOUL_RECOVER.get(), new SimpleContainer(stack), this.level);
        return recoverRecipe.isPresent() && recoverRecipe.get().getIngredient().test(stack);
    }

    public boolean isFuel(ItemStack stack) {
        Optional<SoulRecoverRecipe> recoverRecipe = this.level.getRecipeManager().getRecipeFor(ModRecipeTypes.SOUL_RECOVER.get(), new SimpleContainer(stack), this.level);
        return recoverRecipe.isPresent() && recoverRecipe.get().getAddtionalIngredient().test(stack);

    }

    public int getProgressScaled() {
        int i = this.data.get(1);
        if (i == 0) {
            i = 200;
        }

        return this.data.get(0) * 13 / i;
    }

    public boolean isRecover() {
        return this.data.get(0) > 0;
    }

    @Override
    public RecipeBookType getRecipeBookType() {
        return ModRecipeTypes.SOUL_RECOVER_TYPE;
    }

    @Override
    public boolean shouldMoveToInventory(int slot) {
        return slot != this.getResultSlotIndex();
    }
}