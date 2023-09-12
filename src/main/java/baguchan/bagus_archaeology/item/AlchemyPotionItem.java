package baguchan.bagus_archaeology.item;

import baguchan.bagus_archaeology.element.AlchemyElement;
import baguchan.bagus_archaeology.material.AlchemyMaterial;
import baguchan.bagus_archaeology.util.AlchemyUtils;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class AlchemyPotionItem extends Item {
    public AlchemyPotionItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack p_41348_, Level p_41349_, LivingEntity p_41350_) {
        super.finishUsingItem(p_41348_, p_41349_, p_41350_);
        if (p_41350_ instanceof ServerPlayer serverplayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger(serverplayer, p_41348_);
            serverplayer.awardStat(Stats.ITEM_USED.get(this));
        }

        float scale = 0F;
        if (AlchemyUtils.hasAlchemyMaterial(p_41348_)) {
            List<AlchemyMaterial> alchemyMaterialList = AlchemyUtils.getAlchemyMaterials(p_41348_);
            for (AlchemyMaterial alchemyMaterial : alchemyMaterialList) {
                scale += alchemyMaterial.getPower();
                for (AlchemyElement alchemyElement : alchemyMaterial.getAlchemyElement()) {
                    alchemyElement.active(p_41350_, scale);
                    scale *= alchemyElement.getSelfScale();
                }
            }
        }

        if (p_41348_.isEmpty()) {
            return new ItemStack(Items.GLASS_BOTTLE);
        } else {
            if (p_41350_ instanceof Player && !((Player) p_41350_).getAbilities().instabuild) {
                ItemStack itemstack = new ItemStack(Items.GLASS_BOTTLE);
                Player player = (Player) p_41350_;
                if (!player.getInventory().add(itemstack)) {
                    player.drop(itemstack, false);
                }
            }

            return p_41348_;
        }
    }


    public void appendHoverText(ItemStack p_42988_, @Nullable Level p_42989_, List<Component> p_42990_, TooltipFlag p_42991_) {
        List<AlchemyMaterial> alchemyMaterialList = AlchemyUtils.getAlchemyMaterials(p_42988_);

        for (AlchemyMaterial alchemyMaterial : alchemyMaterialList) {
            p_42990_.add(alchemyMaterial.getName());
        }
    }
}
