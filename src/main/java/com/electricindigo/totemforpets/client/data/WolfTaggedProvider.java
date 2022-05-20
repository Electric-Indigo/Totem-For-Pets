package com.electricindigo.totemforpets.client.data;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class WolfTaggedProvider implements ICapabilitySerializable<CompoundNBT>
{
    private final DefaultWolfTagged tagged = new DefaultWolfTagged();
    private final LazyOptional<IWolfTag> tagOptional = LazyOptional.of(() -> tagged);

    public void invalidate()
    {
        tagOptional.invalidate();
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side)
    {
        return tagOptional.cast();
    }

    @Override
    public CompoundNBT serializeNBT()
    {
        if(CapabilityWolfTagged.WOLF_TAGGED_CAPABILITY == null)
        {
            return new CompoundNBT();
        }
        else
        {
            return (CompoundNBT) CapabilityWolfTagged.WOLF_TAGGED_CAPABILITY.writeNBT(tagged, null);
        }
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt)
    {
        if (CapabilityWolfTagged.WOLF_TAGGED_CAPABILITY !=null)
        {
            CapabilityWolfTagged.WOLF_TAGGED_CAPABILITY.readNBT(tagged, null, nbt);
        }
    }
}
