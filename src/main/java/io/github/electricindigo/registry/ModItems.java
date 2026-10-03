package io.github.electricindigo.registry;

import io.github.electricindigo.TotemForPets;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems
{
    public static DeferredRegister.Items ITEMS = DeferredRegister.createItems(TotemForPets.MODID);

    public static DeferredItem<Item> DOG_TOTEM = ITEMS.registerSimpleItem("dog_totem",
            props -> props.stacksTo(1));

    public static void register(IEventBus modEventBus)
    {
        ITEMS.register(modEventBus);
    }
}
