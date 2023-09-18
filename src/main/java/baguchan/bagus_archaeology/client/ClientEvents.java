package baguchan.bagus_archaeology.client;

import baguchan.bagus_archaeology.RelicsAndAlchemy;
import baguchan.bagus_archaeology.material.AlchemyMaterial;
import baguchan.bagus_archaeology.registry.ModTags;
import baguchan.bagus_archaeology.util.AlchemyUtils;
import com.google.common.collect.Lists;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(modid = RelicsAndAlchemy.MODID, value = Dist.CLIENT)
public class ClientEvents {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void toolTip(ItemTooltipEvent event) {
        List<Component> list = Lists.newArrayList();
        ItemStack stack = event.getItemStack();
        Player player = event.getEntity();

        if (stack.getItem() instanceof ArmorItem armorItem || stack.getItem() instanceof TieredItem || stack.is(ModTags.Items.ALCHEMY_ALLOW_TOOL)) {
            Map<AlchemyMaterial, Float> alchemyMaterialList = AlchemyUtils.getAlchemyMaterials(stack);
            for (Map.Entry<AlchemyMaterial, Float> entry : alchemyMaterialList.entrySet()) {
                AlchemyMaterial alchemyMaterial = entry.getKey();
                list.add(alchemyMaterial.getName());
            }
        }
    }
}
