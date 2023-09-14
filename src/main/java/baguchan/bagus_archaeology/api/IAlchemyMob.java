package baguchan.bagus_archaeology.api;

import net.minecraft.world.item.ItemStack;

public interface IAlchemyMob {
    void setItem(ItemStack p_37447_);

    void setStatsFromItem(ItemStack p_37447_);

    ItemStack getItemRaw();


    ItemStack getItem();
}
