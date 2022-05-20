package com.electricindigo.totemforpets;

import com.electricindigo.totemforpets.client.data.CapabilityWolfTagged;
import com.electricindigo.totemforpets.client.data.TaggedEventHandler;
import com.electricindigo.totemforpets.common.events.EventHandlers;
import com.electricindigo.totemforpets.common.items.TagItem;
import com.electricindigo.totemforpets.core.init.ItemInit;
import net.minecraft.entity.Entity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Mod(TotemForPets.MOD_ID)
public class TotemForPets
{
    private static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "totemforpets";

    public TotemForPets()
    {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::setup);

        ItemInit.ITEMS.register(bus);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        CapabilityWolfTagged.register();
       // MinecraftForge.EVENT_BUS.addGenericListener(Entity.class, TaggedEventHandler::onAttachCapabilitiesEvent);
        MinecraftForge.EVENT_BUS.addListener(TaggedEventHandler::onWolfInteract);
     //   MinecraftForge.EVENT_BUS.addListener(TaggedEventHandler::onDeath);
        MinecraftForge.EVENT_BUS.addListener(TaggedEventHandler::isDeadOrDying);
    }
}
