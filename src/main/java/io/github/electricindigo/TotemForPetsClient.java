package io.github.electricindigo;

import io.github.electricindigo.client.DogTotemLayer;
import io.github.electricindigo.item.DogScreen;
import io.github.electricindigo.registry.ModAttachments;
import io.github.electricindigo.registry.ModMenuTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.WolfRenderer;
import net.minecraft.client.renderer.entity.state.WolfRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.animal.wolf.Wolf;
import net.minecraft.world.item.ItemDisplayContext;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.renderstate.RegisterRenderStateModifiersEvent;

@Mod(value = TotemForPets.MODID, dist = Dist.CLIENT)

@EventBusSubscriber(modid = TotemForPets.MODID, value = Dist.CLIENT)
public class TotemForPetsClient
{
    @SubscribeEvent
    static void registerScreens(RegisterMenuScreensEvent event)
    {
        event.register(ModMenuTypes.DOG_MENU.get(), DogScreen::new);
    }

    @SubscribeEvent
    static void addLayers(EntityRenderersEvent.AddLayers event)
    {
        WolfRenderer renderer = event.getRenderer(EntityTypes.WOLF);
        if (renderer != null) renderer.addLayer(new DogTotemLayer(renderer));
    }

    @SubscribeEvent
    static void registerRenderStateModifiers(RegisterRenderStateModifiersEvent event)
    {
        event.<Wolf, WolfRenderState>registerEntityModifier(WolfRenderer.class, ((wolf, state) ->
        {
            ItemStackRenderState totem = state.getRenderData(DogTotemLayer.DOG_TOTEM);
            if (totem == null)
            {
                totem = new ItemStackRenderState();
                state.setRenderData(DogTotemLayer.DOG_TOTEM, totem);
            }
            Minecraft.getInstance().getItemModelResolver()
                    .updateForLiving(totem, wolf.getData(ModAttachments.TOTEM_SLOT), ItemDisplayContext.NONE, wolf);
        }));
    }
}
