package com.electricindigo.totemforpets.common.events;

import com.electricindigo.totemforpets.TotemForPets;
import net.minecraft.data.DataGenerator;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;

public class EventHandlers
{

//Wolf Totem Apply
 /*   @SubscribeEvent
    public void interactWithWolf(PlayerInteractEvent.EntityInteractSpecific event)
    {
        PlayerEntity player = event.getPlayer();
        World world = event.getWorld();

        if(event.getTarget() instanceof WolfEntity)
        {
            WolfEntity wolf = (WolfEntity)event.getTarget();

            if(!world.isClientSide)
            {
                if(player.isCrouching() && player.getMainHandItem().getItem().equals(ForgeRegistries.ITEMS.getValue(new ResourceLocation("totemforpets:totem_tag"))) && wolf.getOwnerUUID().equals(player.getUUID()))
                {
                    wolf.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP, 0.25F, 0.50F);
                    System.out.println("Dog Received Totem");
                    event.setCancellationResult(ActionResultType.SUCCESS);
                }else{
                    event.setCancellationResult(ActionResultType.FAIL);
                }
                event.setCanceled(true);
            }
        }
    }*/


}
