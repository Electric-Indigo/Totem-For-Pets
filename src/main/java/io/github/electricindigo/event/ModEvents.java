package io.github.electricindigo.event;

import io.github.electricindigo.TotemForPets;
import io.github.electricindigo.item.DogMenu;
import io.github.electricindigo.registry.ModAttachments;
import io.github.electricindigo.registry.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.animal.wolf.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.DeathProtection;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber(modid = TotemForPets.MODID)
public class ModEvents
{
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onDogDeath(LivingDeathEvent event)
    {
        if (!(event.getEntity() instanceof Wolf wolf) || !(wolf.level() instanceof ServerLevel)) return;

        ItemStack totem = wolf.getData(ModAttachments.TOTEM_SLOT);
        if (totem.isEmpty()) return;
        if (event.getSource().is(DamageTypeTags.BYPASSES_INVULNERABILITY)) return;

        event.setCanceled(true);
        wolf.setData(ModAttachments.TOTEM_SLOT, ItemStack.EMPTY);
        wolf.setHealth(1.0F);
        DeathProtection.TOTEM_OF_UNDYING.applyEffects(totem, wolf);
        wolf.level().broadcastEntityEvent(wolf, (byte) 35);
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onDogInteract(PlayerInteractEvent.EntityInteract event)
    {
        Player player = event.getEntity();
        ItemStack stack = event.getItemStack();

        // This event fires once per hand. Only care about the main hand.
        if (event.getHand() != InteractionHand.MAIN_HAND) return;
        if (!(event.getTarget() instanceof Wolf wolf) || !wolf.isOwnedBy(player) || !player.isCrouching()) return;

        // Crouch + empty hand: open the dog's menu
        if (stack.isEmpty())
        {
            event.setCanceled(true);
            event.setCancellationResult(InteractionResult.SUCCESS);

            if (wolf.level() instanceof ServerLevel)
            {
                player.openMenu(
                        new SimpleMenuProvider((id, inventory, p) -> new DogMenu(id, inventory, wolf), wolf.getDisplayName()),
                        buf -> buf.writeVarInt(wolf.getId()));
            }
            return;
        }

        // Crouch + totem: shortcut that puts it straight in the totem slot
        if (stack.is(ModItems.DOG_TOTEM))
        {
            event.setCanceled(true);
            event.setCancellationResult(InteractionResult.SUCCESS);

            if (wolf.level() instanceof ServerLevel)
            {
                if (wolf.getData(ModAttachments.TOTEM_SLOT).isEmpty())
                {
                    wolf.setData(ModAttachments.TOTEM_SLOT, stack.copyWithCount(1));
                    stack.consume(1, player);
                }
                else player.sendSystemMessage(Component.literal("Dog already has totem!"));
            }
        }
    }
}