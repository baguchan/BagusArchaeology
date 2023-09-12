package baguchan.bagus_archaeology.item;

import baguchan.bagus_archaeology.material.AlchemyMaterial;
import baguchan.bagus_archaeology.util.AlchemyUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class AlchemyItem extends Item {
    public AlchemyItem(Properties properties) {
        super(properties);
    }

    public void appendHoverText(ItemStack p_42988_, @Nullable Level p_42989_, List<Component> p_42990_, TooltipFlag p_42991_) {
        List<AlchemyMaterial> alchemyMaterialList = AlchemyUtils.getAlchemyMaterials(p_42988_);

        for (AlchemyMaterial alchemyMaterial : alchemyMaterialList) {
            p_42990_.add(alchemyMaterial.getName());
        }
    }
}
