package com.electricindigo.totemforpets.client.data;

import com.electricindigo.totemforpets.TotemForPets;
import com.electricindigo.totemforpets.core.init.ItemInit;
import net.minecraft.client.audio.Sound;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.*;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

public class TaggedEventHandler
{
   /* public static void onAttachCapabilitiesEvent(AttachCapabilitiesEvent<Entity> event)
    {
        if(event.getObject() instanceof WolfEntity)
        {
            WolfTaggedProvider provider = new WolfTaggedProvider();
            event.addCapability(new ResourceLocation(TotemForPets.MOD_ID, "tagged"), provider);
            event.addListener(provider::invalidate);
        }
    }*/

    public static void onWolfInteract(PlayerInteractEvent.EntityInteractSpecific event)
    {
        PlayerEntity player = event.getPlayer();
        World world = event.getWorld();
        WolfEntity wolf = (WolfEntity) event.getTarget();
        ItemStack stack = player.getMainHandItem();

        if (!world.isClientSide)
        {
           //System.out.println("Is serverSide");

            if (event.getTarget() instanceof WolfEntity)
            {
                //System.out.println("Is wolf");


                if (player.isCrouching() && wolf.isTame() && wolf.getOwnerUUID().equals(player.getUUID()) && !wolf.getTags().contains("tagged") && stack.getItem() == ItemInit.TOTEM_TAG.get())
                {
                    //System.out.println("Wolf has taken totem!");
                    wolf.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP, 0.25F, 0.50F);
                    wolf.addTag("tagged");
                    player.displayClientMessage(new TranslationTextComponent("message.tagged_wolf"), true);
                    event.setCancellationResult(ActionResultType.SUCCESS);
                    player.getMainHandItem().shrink(1);
                }else if (player.isCrouching() && wolf.isTame() && wolf.getOwnerUUID().equals(player.getUUID()) && wolf.getTags().contains("tagged") && stack.getItem() == ItemInit.TOTEM_TAG.get())
                {
                    player.displayClientMessage(new TranslationTextComponent("message.pretagged_wolf"), true);
                    //System.out.println("Wolf has already been tagged!");
                }
            }
        }
        else
        {
            event.setCancellationResult(ActionResultType.FAIL);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void isDeadOrDying(LivingDeathEvent event)
    {
        event.isCancelable();
        Entity entity = event.getEntityLiving();
        if (entity instanceof WolfEntity)
        {
            WolfEntity wolf = (WolfEntity) entity;
            if (wolf.getTags().contains("tagged") && (wolf.getHealth() <= 2.5F))
            {
                event.setCanceled(true);
                wolf.setHealth(1.0F);
                wolf.removeTag("tagged");
                wolf.playSound(SoundEvents.TOTEM_USE, 0.25F, 0.50F);
                wolf.removeAllEffects();
                wolf.addEffect(new EffectInstance(Effects.REGENERATION, 900, 1));
                wolf.addEffect(new EffectInstance(Effects.ABSORPTION, 100, 1));
                wolf.addEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 800, 0));

            }
        }
        else
        {
            event.setCanceled(false);
        }
    }
/*
   @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onWolfDying(LivingDamageEvent event)
    {
        if (event.getEntity() instanceof WolfEntity && wolf)
        {
            System.out.println("Is wolf");
            WolfEntity wolf = (WolfEntity) event.getEntity();

            if (wolf.isDeadOrDying() && wolf.getTags().contains("tagged"))
            {
                System.out.println("Dog used totem");
                    wolf.setHealth(1.0F);
                    wolf.playSound(SoundEvents.TOTEM_USE, 0.25F, 0.50F);
                    wolf.removeAllEffects();
                    wolf.addEffect(new EffectInstance(Effects.REGENERATION, 900, 1));
                    wolf.addEffect(new EffectInstance(Effects.ABSORPTION, 100, 1));
                    wolf.addEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 800, 0));
                    wolf.removeTag("tagged");
                    event.setCanceled(true);
            }else{
                event.setCanceled(false);
            }
        }
    }*/
}
