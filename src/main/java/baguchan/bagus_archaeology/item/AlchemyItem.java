package baguchan.bagus_archaeology.item;

import baguchan.bagus_archaeology.element.AlchemyElement;
import baguchan.bagus_archaeology.material.AlchemyMaterial;
import baguchan.bagus_archaeology.util.AlchemyUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public class AlchemyItem extends Item {
    public AlchemyItem(Properties properties) {
        super(properties);
    }

    public void appendHoverText(ItemStack p_42988_, @Nullable Level p_42989_, List<Component> p_42990_, TooltipFlag p_42991_) {
        Map<AlchemyMaterial, Float> alchemyMaterialList = AlchemyUtils.getAlchemyMaterials(p_42988_);
        float self = 0;
        float projectile = 0;
        float hardness = 0;
        float toughness = 0;
        for (Map.Entry<AlchemyMaterial, Float> alchemyMaterial : alchemyMaterialList.entrySet()) {
            p_42990_.add(alchemyMaterial.getKey().getName());
            self += alchemyMaterial.getKey().getPower() * alchemyMaterial.getValue();
            projectile += alchemyMaterial.getKey().getPower() * alchemyMaterial.getValue();
            hardness += alchemyMaterial.getKey().getHardness() * alchemyMaterial.getValue();
            toughness += alchemyMaterial.getKey().getToughness() * alchemyMaterial.getValue();
            for (AlchemyElement alchemyElement : alchemyMaterial.getKey().getAlchemyElement()) {
                self *= alchemyElement.getSelfScale();
                projectile *= alchemyElement.getProjectileScale();
            }
        }

        p_42990_.add(CommonComponents.EMPTY);
        p_42990_.add(Component.translatable("alchemy.when_used").withStyle(ChatFormatting.DARK_PURPLE));
        if (self > 0.0D) {
            p_42990_.add(Component.translatable("attribute.modifier.plus.0", ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(self), Component.translatable("alchemy.status.self_power")).withStyle(ChatFormatting.BLUE));
        } else if (self < 0.0D) {
            self *= -1.0D;
            p_42990_.add(Component.translatable("attribute.modifier.take.0", ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(self), Component.translatable("alchemy.status.self_power")).withStyle(ChatFormatting.RED));
        }
        if (projectile > 0.0D) {
            p_42990_.add(Component.translatable("attribute.modifier.plus.0", ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(projectile), Component.translatable("alchemy.status.projectile_power")).withStyle(ChatFormatting.BLUE));
        } else if (projectile < 0.0D) {
            projectile *= -1.0D;
            p_42990_.add(Component.translatable("attribute.modifier.take.0", ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(projectile), Component.translatable("alchemy.status.projectile_power")).withStyle(ChatFormatting.RED));
        }

        if (hardness > 0.0D) {
            p_42990_.add(Component.translatable("attribute.modifier.plus.0", ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(hardness), Component.translatable("alchemy.status.hardness")).withStyle(ChatFormatting.BLUE));
        } else if (hardness < 0.0D) {
            hardness *= -1.0F;
            p_42990_.add(Component.translatable("attribute.modifier.take.0", ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(hardness), Component.translatable("alchemy.status.hardness")).withStyle(ChatFormatting.RED));
        }

        if (toughness > 0.0D) {
            p_42990_.add(Component.translatable("attribute.modifier.plus.0", ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(toughness), Component.translatable("alchemy.status.toughness")).withStyle(ChatFormatting.BLUE));
        } else if (toughness < 0.0D) {
            toughness *= -1.0F;
            p_42990_.add(Component.translatable("attribute.modifier.take.0", ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(toughness), Component.translatable("alchemy.status.toughness")).withStyle(ChatFormatting.RED));
        }
    }
}
