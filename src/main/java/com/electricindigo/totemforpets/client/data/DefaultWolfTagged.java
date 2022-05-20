package com.electricindigo.totemforpets.client.data;

public class DefaultWolfTagged implements IWolfTag
{
    private boolean tagged;

    @Override
    public void setTagged(boolean tagged)
    {
        this.tagged = tagged;
    }

    @Override
    public boolean getTagged()
    {
        return tagged;
    }
}
