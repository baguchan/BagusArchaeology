package baguchan.bagus_archaeology.registry;

import baguchan.bagus_archaeology.RelicsAndAlchemy;
import baguchan.bagus_archaeology.entity.AlchemyThrown;
import baguchan.bagus_archaeology.item.AlchemyItem;
import baguchan.bagus_archaeology.item.AlchemyMobItem;
import baguchan.bagus_archaeology.item.AlchemyPotionItem;
import baguchan.bagus_archaeology.item.AlchemyProjectileItem;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, RelicsAndAlchemy.MODID);

    public static final RegistryObject<Item> ALCHEMY_POTION = ITEMS.register("alchemy_potion", () -> new AlchemyPotionItem((new Item.Properties().stacksTo(1))));
    public static final RegistryObject<Item> ALCHEMY_PROJECTILE = ITEMS.register("alchemy_projectile", () -> new AlchemyProjectileItem((new Item.Properties().stacksTo(16))));
    public static final RegistryObject<Item> ALCHEMY_INGOT = ITEMS.register("alchemy_ingot", () -> new AlchemyItem((new Item.Properties().stacksTo(1))));
    public static final RegistryObject<Item> ALCHEMY_COMBAT_GOLEM = ITEMS.register("alchemy_combat_golem", () -> new AlchemyMobItem(ModEntities.ALCHEMY_COMBAT_GOLEM, (new Item.Properties())));
    public static final RegistryObject<Item> ALCHEMY_SLIME = ITEMS.register("alchemy_slime", () -> new AlchemyMobItem(ModEntities.ALCHEMY_SLIME, (new Item.Properties().stacksTo(1))));
    public static final RegistryObject<Item> GOLEM_COMBAT_CORE = ITEMS.register("golem_combat_core", () -> new Item((new Item.Properties().stacksTo(16))));

    public static void dispenserRegistry() {
        DispenserBlock.registerBehavior(ALCHEMY_PROJECTILE.get(), new AbstractProjectileDispenseBehavior() {
            protected Projectile getProjectile(Level p_123456_, Position p_123457_, ItemStack p_123458_) {
                AlchemyThrown thrown = new AlchemyThrown(ModEntities.ALCHEMY_THROWN.get(), p_123457_.x(), p_123457_.y(), p_123457_.z(), p_123456_);
                thrown.setItem(p_123458_);
                return thrown;
            }
        });
    }
}
