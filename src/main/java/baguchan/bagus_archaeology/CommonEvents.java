package baguchan.bagus_archaeology;

import baguchan.bagus_archaeology.element.AlchemyElement;
import baguchan.bagus_archaeology.material.AlchemyMaterial;
import baguchan.bagus_archaeology.registry.ModBlocks;
import baguchan.bagus_archaeology.registry.ModTags;
import baguchan.bagus_archaeology.util.AlchemyUtils;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.NoteBlockEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = RelicsAndAlchemy.MODID)
public class CommonEvents {
    @SubscribeEvent
    public static void noteBlockPlay(NoteBlockEvent.Play event) {
        BlockState stateAbove = event.getLevel().getBlockState(event.getPos().above());
        if (stateAbove.is(ModBlocks.PIGMAN_SKULL.get())) {
            event.setCanceled(true);
            event.getLevel().playSound(null, event.getPos(), SoundEvents.ZOMBIFIED_PIGLIN_ANGRY, SoundSource.RECORDS);
        }
    }

    @SubscribeEvent
    public static void rightClickEvent(PlayerInteractEvent.RightClickBlock event) {
        if (event.getLevel().getBlockState(event.getPos()).is(Blocks.CAULDRON)) {
            if (event.getItemStack().is(Items.WOODEN_SHOVEL)) {
                event.getEntity().playSound(SoundEvents.ARMOR_EQUIP_GENERIC);
                event.getItemStack().shrink(1);
                event.getLevel().setBlock(event.getPos(), ModBlocks.ALCHEMY_CAULDRON.get().defaultBlockState(), 3);
                event.setUseItem(Event.Result.ALLOW);
                event.setCanceled(true);
            }
        }
        if (event.getLevel().getBlockState(event.getPos()).is(Blocks.WATER_CAULDRON)) {
            if (event.getItemStack().is(Items.WOODEN_SHOVEL)) {
                event.getEntity().playSound(SoundEvents.ARMOR_EQUIP_GENERIC);
                event.getItemStack().shrink(1);
                event.getLevel().setBlock(event.getPos(), ModBlocks.ALCHEMY_CAULDRON.get().defaultBlockState(), 3);
                event.setUseItem(Event.Result.ALLOW);
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void modifier(ItemAttributeModifierEvent event) {
        ItemStack stack = event.getItemStack();
        if (!stack.isEmpty()) {

            float power = 0;
            float hardness = 0;
            float toughness = 0;

            if (AlchemyUtils.hasAlchemyMaterial(stack)) {
                Map<AlchemyMaterial, Float> alchemyMaterialList = AlchemyUtils.getAlchemyMaterials(stack);

                for (Map.Entry<AlchemyMaterial, Float> entry : alchemyMaterialList.entrySet()) {
                    hardness += entry.getKey().getHardness() * 0.1F;
                    toughness += entry.getKey().getToughness() * 0.2F;
                    power += entry.getKey().getPower() * entry.getValue() * 0.1F;
                    for (AlchemyElement alchemyElement : entry.getKey().getAlchemyElement()) {
                        power *= alchemyElement.getSelfScale();
                    }
                }
            }
            if (stack.getItem() instanceof ArmorItem armorItem) {
                if (armorItem.getEquipmentSlot() == event.getSlotType()) {
                    if (hardness > 0) {
                        event.addModifier(Attributes.ARMOR, new AttributeModifier(UUID.fromString("2598f9f2-865c-af38-2948-bf41e562c5bc"), "Alchemy Bonus", hardness, AttributeModifier.Operation.ADDITION));
                    }
                    if (toughness > 0) {
                        event.addModifier(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(UUID.fromString("5f9d8ca8-ba6d-2366-fe2c-7afffea9cf4b"), "Alchemy Bonus", toughness, AttributeModifier.Operation.ADDITION));
                    }
                }
            }
            if (stack.getItem() instanceof TieredItem tieredItem || stack.is(ModTags.Items.ALCHEMY_ALLOW_TOOL)) {
                if (event.getSlotType() == EquipmentSlot.MAINHAND) {

                    if (power > 0) {
                        event.addModifier(Attributes.ATTACK_DAMAGE, new AttributeModifier(UUID.fromString("77f447a4-5940-e156-4d61-4344d920ebb9"), "Alchemy Bonus", power, AttributeModifier.Operation.ADDITION));
                    }
                }
            }
        }
    }
}