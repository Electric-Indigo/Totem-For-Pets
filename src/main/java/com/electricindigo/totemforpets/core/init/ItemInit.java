package com.electricindigo.totemforpets.core.init;

import com.electricindigo.totemforpets.TotemForPets;
import com.electricindigo.totemforpets.common.items.TagItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Rarity;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit
{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TotemForPets.MOD_ID);

    public static final RegistryObject<TagItem> TOTEM_TAG = ITEMS.register("totem_tag", () -> new TagItem(new Item.Properties().tab(ItemGroup.TAB_COMBAT).stacksTo(1).rarity(Rarity.UNCOMMON)));
}
