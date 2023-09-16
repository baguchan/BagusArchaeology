package baguchan.bagus_archaeology.registry;

import baguchan.bagus_archaeology.RelicsAndAlchemy;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModTags {
    public static class Items {
        public static final TagKey<Item> ALCHEMY_ALLOW_TOOL = tag("alchemy_allow_tool");
        public static final TagKey<Item> PROJECTILE_MATERIAL = tag("projectile_material");
        public static final TagKey<Item> INGOT_MATERIAL = tag("ingot_material");
        private static TagKey<Item> tag(String name) {
            return ItemTags.create(new ResourceLocation(RelicsAndAlchemy.MODID, name));
        }

        private static TagKey<Item> forgeTag(String name) {
            return ItemTags.create(new ResourceLocation("forge", name));
        }
    }
}
