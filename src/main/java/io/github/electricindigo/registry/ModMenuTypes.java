package io.github.electricindigo.registry;

import io.github.electricindigo.TotemForPets;
import io.github.electricindigo.item.DogMenu;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModMenuTypes
{
    public static final DeferredRegister<MenuType<?>> MENU_TYPES =
            DeferredRegister.create(BuiltInRegistries.MENU, TotemForPets.MODID);

    public static final DeferredHolder<MenuType<?>, MenuType<DogMenu>> DOG_MENU =
            MENU_TYPES.register("dog_menu",
                    () -> IMenuTypeExtension.create(DogMenu::new));

    public static void register(IEventBus modEventBus)
    {
        MENU_TYPES.register(modEventBus);
    }
}
