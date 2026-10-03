package io.github.electricindigo.item;

import io.github.electricindigo.registry.ModAttachments;
import io.github.electricindigo.registry.ModItems;
import io.github.electricindigo.registry.ModMenuTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.animal.wolf.Wolf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

public class DogMenu extends AbstractContainerMenu
{
    private static final int DOG_SLOTS = 2;
    private static final int PLAYER_SLOTS_END = DOG_SLOTS + 36;

    @Nullable
    private final Wolf wolf;

    public DogMenu(int containerId, Inventory playerInventory, RegistryFriendlyByteBuf buf)
    {
        this(containerId, playerInventory,
                playerInventory.player.level().getEntity(buf.readVarInt()) instanceof Wolf w ? w : null);
    }

    public DogMenu(int containerId, Inventory playerInventory, @Nullable Wolf wolf)
    {
        super(ModMenuTypes.DOG_MENU.get(), containerId);
        this.wolf = wolf;

        Container dogSlots = (wolf != null && wolf.level() instanceof ServerLevel)
                ? new DogContainer(wolf)
                :new SimpleContainer(DOG_SLOTS);

        addSlot(new Slot(dogSlots, 1, 8, 36)
        {
            @Override
            public boolean mayPlace(ItemStack itemStack) {
                return itemStack.is(ModItems.DOG_TOTEM);
            }

            @Override
            public int getMaxStackSize() {
                return 1;
            }
        });

        for (int row = 0; row < 3; row++)
        {
            for (int col = 0; col < 9; col++)
            {
                addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }

        for (int col = 0; col < 9; col++)
        {
            addSlot(new Slot(playerInventory, col, 8 + col * 18, 142));
        }
    }

    @Nullable
    public Wolf getWolf()
    {
        return wolf;
    }

    @Override
    public boolean stillValid(Player player) {
        return wolf != null && wolf.isAlive() && player.distanceToSqr(wolf) < 64.0;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        Slot slot = slots.get(i);
        if (!slot.hasItem()) return ItemStack.EMPTY;

        ItemStack stack = slot.getItem();
        ItemStack original = stack.copy();

        if (i < DOG_SLOTS)
        {
            if (!moveItemStackTo(stack, DOG_SLOTS, PLAYER_SLOTS_END, true)) return ItemStack.EMPTY;
        }
        else
        {
            if (!moveItemStackTo(stack, 0, DOG_SLOTS, false)) return ItemStack.EMPTY;
        }
        if (stack.isEmpty()) slot.setByPlayer(ItemStack.EMPTY);
        else slot.setChanged();

        return original;
    }

    private static class DogContainer implements Container
    {
        private final Wolf wolf;

        DogContainer(Wolf wolf)
        {
            this.wolf = wolf;
        }

        @Override
        public int getContainerSize() {
            return DOG_SLOTS;
        }

        @Override
        public boolean isEmpty() {
            return getItem(0).isEmpty() && getItem(1).isEmpty();
        }

        @Override
        public ItemStack getItem(int i) {
            return i == 0 ? wolf.getItemBySlot(EquipmentSlot.BODY) : wolf.getData(ModAttachments.TOTEM_SLOT);
        }

        @Override
        public ItemStack removeItem(int slot, int amount) {
            ItemStack current = getItem(slot);
            if (current.isEmpty()) return ItemStack.EMPTY;
            ItemStack taken = current.split(amount);
            setItem(slot, current);
            return taken;
        }

        @Override
        public ItemStack removeItemNoUpdate(int slot) {
            ItemStack current = getItem(slot);
            setItem(slot, ItemStack.EMPTY);
            return current;
        }

        @Override
        public void setItem(int slot, ItemStack stack) {
            if (slot == 0) wolf.setItemSlot(EquipmentSlot.BODY, stack);
            else wolf.setData(ModAttachments.TOTEM_SLOT, stack);
        }

        @Override
        public void setChanged() {

        }

        @Override
        public boolean stillValid(Player player) {
            return wolf.isAlive() && player.distanceToSqr(wolf) < 64.0;
        }

        @Override
        public void clearContent() {
            setItem(0, ItemStack.EMPTY);
            setItem(1, ItemStack.EMPTY);
        }
    }
}
