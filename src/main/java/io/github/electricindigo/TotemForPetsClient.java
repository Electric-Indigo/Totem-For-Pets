package io.github.electricindigo;

import io.github.electricindigo.item.DogScreen;
import io.github.electricindigo.registry.ModMenuTypes;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@Mod(value = TotemForPets.MODID, dist = Dist.CLIENT)

@EventBusSubscriber(modid = TotemForPets.MODID, value = Dist.CLIENT)
public class TotemForPetsClient
{
    @SubscribeEvent
    static void registerScreens(RegisterMenuScreensEvent event)
    {
        event.register(ModMenuTypes.DOG_MENU.get(), DogScreen::new);
    }
}
