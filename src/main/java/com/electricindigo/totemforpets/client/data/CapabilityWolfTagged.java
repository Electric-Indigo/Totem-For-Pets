package com.electricindigo.totemforpets.client.data;


import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.*;

import javax.annotation.Nullable;

public class CapabilityWolfTagged
{
    @CapabilityInject(IWolfTag.class)
    static Capability<IWolfTag> WOLF_TAGGED_CAPABILITY = null;

    public static void register()
    {
        CapabilityManager.INSTANCE.register(IWolfTag.class, new Storage(), DefaultWolfTagged::new);
    }

    public static class Storage implements Capability.IStorage<IWolfTag>
    {
        @Nullable
        @Override
        public INBT writeNBT(Capability<IWolfTag> capability, IWolfTag instance, Direction side)
        {
            CompoundNBT tag = new CompoundNBT();
            tag.putBoolean("tagged", instance.getTagged());
            return tag;
        }

        @Override
        public void readNBT(Capability<IWolfTag> capability, IWolfTag instance, Direction side, INBT nbt)
        {
            boolean tagged = ((CompoundNBT) nbt).getBoolean("tagged");
            instance.setTagged(tagged);
        }
    }
}