package baguchan.bagus_archaeology.util;

import baguchan.bagus_archaeology.element.AlchemyElement;
import baguchan.bagus_archaeology.material.AlchemyMaterial;
import baguchan.bagus_archaeology.registry.ModAlchemyElements;
import baguchan.bagus_archaeology.registry.ModAlchemyMaterials;
import com.google.common.collect.Lists;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AlchemyUtils {
    public static final String TAG_ALCHEMY_ELEMENT = "AlchemyElement";
    public static final String TAG_STORED_ALCHEMY_MATERIAL = "StoredAlchemyMaterials";
    public static final String TAG_ALCHEMY_MATERIAL = "AlchemyMaterial";
    public static final String TAG_ALCHEMY_SCALE = "AlchemyMaterial";

    @Nullable
    public static AlchemyElement getAlchemyElementFromString(@Nullable String id) {
        if (id != null && ModAlchemyElements.getRegistry().get().containsKey(ResourceLocation.tryParse(id))) {
            return ModAlchemyElements.getRegistry().get().getValue(ResourceLocation.tryParse(id));
        } else {
            return null;
        }
    }

    /**
     * get AlchemyElement From NBT
     *
     * @param tag nbt tag
     */
    @Nullable
    public static AlchemyElement getAlchemyElementFromNBT(@Nullable CompoundTag tag) {
        if (tag != null && ModAlchemyElements.getRegistry().get().containsKey(ResourceLocation.tryParse(tag.getString(TAG_ALCHEMY_ELEMENT)))) {
            return ModAlchemyElements.getRegistry().get().getValue(ResourceLocation.tryParse(tag.getString(TAG_ALCHEMY_ELEMENT)));
        } else {
            return null;
        }
    }

    /**
     * get AlchemyMaterial From NBT
     *
     * @param tag nbt tag
     */
    @Nullable
    public static AlchemyMaterial getAlchemyMaterialFromNBT(@Nullable CompoundTag tag) {
        if (tag != null && ModAlchemyElements.getRegistry().get().containsKey(ResourceLocation.tryParse(tag.getString(TAG_ALCHEMY_MATERIAL)))) {
            return ModAlchemyMaterials.ALCHEMY_MATERIAL_REGISTRY.get().getValue(ResourceLocation.tryParse(tag.getString(TAG_ALCHEMY_MATERIAL)));
        } else {
            return null;
        }
    }

    @Nullable
    public static AlchemyMaterial getAlchemyMaterialFromString(@Nullable String id) {
        if (id != null && ModAlchemyElements.getRegistry().get().containsKey(ResourceLocation.tryParse(id))) {
            return ModAlchemyMaterials.ALCHEMY_MATERIAL_REGISTRY.get().getValue(ResourceLocation.tryParse(id));
        } else {
            return null;
        }
    }


    /**
     * check ItemStack has AlchemyElement
     *
     * @param stack AlchemyElemented Item
     */
    public static boolean hasAlchemyMaterial(ItemStack stack) {
        CompoundTag compoundnbt = stack.getTag();
        return compoundnbt != null && compoundnbt.contains(TAG_STORED_ALCHEMY_MATERIAL);
    }

    /**
     * check NBT has AlchemyElement
     *
     * @param compoundnbt nbt tag
     */
    public static ListTag getAlchemyMaterialListForNBT(CompoundTag compoundnbt) {
        return compoundnbt != null ? compoundnbt.getList(TAG_STORED_ALCHEMY_MATERIAL, 10) : new ListTag();
    }

    /**
     * get Mob Enchantments From ItemStack
     *
     * @param stack AlchemyElemented Item
     */
    public static List<AlchemyMaterial> getAlchemyMaterials(ItemStack stack) {
        ListTag listnbt = getAlchemyMaterialListForNBT(stack.getTag());
        return makeAlchemyMaterialListFromListNBT(listnbt);
    }

    private static List<AlchemyMaterial> makeAlchemyMaterialListFromListNBT(ListTag p_226652_0_) {
        List<AlchemyMaterial> linkedList = Lists.newLinkedList();

        for (int i = 0; i < p_226652_0_.size(); ++i) {
            CompoundTag compoundnbt = p_226652_0_.getCompound(i);
            AlchemyMaterial mobEnchant = getAlchemyMaterialFromString(compoundnbt.getString(TAG_ALCHEMY_MATERIAL));
            linkedList.add(mobEnchant);
        }

        return linkedList;
    }


    public static void addAlchemyMaterialToItemStack(ItemStack itemIn, AlchemyMaterial alchemyMaterial) {
        ListTag listnbt = getAlchemyMaterialListForNBT(itemIn.getTag());

        boolean flag = true;
        ResourceLocation resourcelocation = ModAlchemyMaterials.ALCHEMY_MATERIAL_REGISTRY.get().getKey(alchemyMaterial);


        for (int i = 0; i < listnbt.size(); ++i) {
            CompoundTag compoundnbt = listnbt.getCompound(i);
            ResourceLocation resourcelocation1 = ResourceLocation.tryParse(compoundnbt.getString(TAG_ALCHEMY_MATERIAL));
            if (resourcelocation1 != null && resourcelocation1.equals(resourcelocation)) {
                flag = false;
                break;
            }
        }

        if (flag) {
            CompoundTag compoundnbt1 = new CompoundTag();
            compoundnbt1.putString(TAG_ALCHEMY_MATERIAL, String.valueOf((Object) resourcelocation));
            listnbt.add(compoundnbt1);
        }

        itemIn.getTag().put(TAG_STORED_ALCHEMY_MATERIAL, listnbt);
    }

    public static boolean findAlchemyElementHandler(List<AlchemyElement> list, AlchemyElement findAlchemyElement) {
        for (AlchemyElement element : list) {
            if (element.equals(findAlchemyElement)) {
                return true;
            }
        }
        return false;
    }
}
