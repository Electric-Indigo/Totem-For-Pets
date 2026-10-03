package io.github.electricindigo;

import io.github.electricindigo.registry.ModAttachments;
import io.github.electricindigo.registry.ModItems;
import io.github.electricindigo.registry.ModMenuTypes;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

@Mod(TotemForPets.MODID)
public class TotemForPets {
    public static final String MODID = "totemforpets";

    public TotemForPets(IEventBus modEventBus, ModContainer modContainer) {
        ModItems.register(modEventBus);
        ModAttachments.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        NeoForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.COMBAT) {
            event.accept(ModItems.DOG_TOTEM);
        }
    }
}
