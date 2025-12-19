package org.paczkomod.paczkomod.screen;

import org.jetbrains.annotations.NotNull;
import org.paczkomod.paczkomod.PaczkoMod;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class PaczkoModScreenHandler extends AbstractContainerMenu {
    private final Container globalInventory;
    private final Container inputInventory;
    private final Player playerEntity;

    public PaczkoModScreenHandler(int syncId, Inventory playerInventory) {
        this(syncId, playerInventory, new SimpleContainer(27), new SimpleContainer(2));
    }

    public PaczkoModScreenHandler(int syncId, Inventory playerInventory, Container globalInv, Container inputInv) {
        super(PaczkoMod.PACZKOMOD_SCREEN_HANDLER, syncId);
        this.globalInventory = globalInv;
        this.inputInventory = inputInv;
        this.playerEntity = playerInventory.player;

        checkContainerSize(globalInv, 27);
        checkContainerSize(inputInv, 2);

        if (inputInv instanceof SimpleContainer simpleContainer) {
            simpleContainer.addListener(this::slotsChanged);
        }

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(globalInv, col + row * 9, 7 + col * 18, 17 + row * 18) {
                    @Override
                    public boolean mayPlace(ItemStack stack) {
                        return false;
                    }
                });
            }
        }

        this.addSlot(new Slot(inputInv, 0, 61, 89));
        this.addSlot(new Slot(inputInv, 1, 97, 89) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.is(Items.EMERALD);
            }
        });

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 140 + row * 18));
            }
        }
        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 198));
        }
    }

    @Override
    public void slotsChanged(Container container) {
        if (!playerEntity.level().isClientSide() && container == inputInventory) {

            ItemStack paczka = inputInventory.getItem(0);
            ItemStack zaplata = inputInventory.getItem(1);

            if (!paczka.isEmpty() && !zaplata.isEmpty() && zaplata.is(Items.EMERALD)) {

                int wolnySlot = -1;
                for (int i = 0; i < globalInventory.getContainerSize(); i++) {
                    if (globalInventory.getItem(i).isEmpty()) {
                        wolnySlot = i;
                        break;
                    }
                }

                if (wolnySlot != -1) {
                    globalInventory.setItem(wolnySlot, paczka.copy());
                    inputInventory.setItem(0, ItemStack.EMPTY);
                    zaplata.shrink(1);

                    globalInventory.setChanged();
                }
            }
        }
        super.slotsChanged(container);
    }

    @Override
    public boolean stillValid(Player player) {
        return globalInventory.stillValid(player);
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        this.clearContainer(player, inputInventory);
    }

    @Override
    public @NotNull ItemStack quickMoveStack(Player player, int index) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack originalStack = slot.getItem();
            newStack = originalStack.copy();

            if (index < 29) {
                if (!this.moveItemStackTo(originalStack, 29, 65, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(originalStack, 27, 29, false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }
        return newStack;
    }
}