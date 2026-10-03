package io.github.electricindigo.registry;

import com.mojang.serialization.Codec;
import io.github.electricindigo.TotemForPets;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ModAttachments
{
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, TotemForPets.MODID);

    public static final Supplier<AttachmentType<ItemStack>> TOTEM_SLOT =
            ATTACHMENT_TYPES.register("totem_slot", () -> AttachmentType.builder(() -> ItemStack.EMPTY)
                    .serialize(ItemStack.OPTIONAL_CODEC.fieldOf("item"))
                    .sync(ItemStack.OPTIONAL_STREAM_CODEC)
                    .build());

    public static void register(IEventBus mobEventBus)
    {
        ATTACHMENT_TYPES.register(mobEventBus);
    }
}
