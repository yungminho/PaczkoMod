package org.paczkomod.paczkomod.world;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.saveddata.SavedData;
import org.jetbrains.annotations.NotNull;

public class PaczkoModState extends SavedData {
    public final NonNullList<ItemStack> inventory = NonNullList.withSize(27, ItemStack.EMPTY);

    public PaczkoModState() {}

    public static PaczkoModState load(CompoundTag tag, HolderLookup.Provider registries) {
        PaczkoModState state = new PaczkoModState();
        ContainerHelper.loadAllItems(tag, state.inventory, registries);
        return state;
    }

    @Override
    public @NotNull CompoundTag save(CompoundTag tag, HolderLookup.Provider registries) {
        ContainerHelper.saveAllItems(tag, inventory, registries);
        return tag;
    }

    private static final SavedData.Factory<PaczkoModState> FACTORY = new SavedData.Factory<>(
            PaczkoModState::new, PaczkoModState::load, null
    );

    public static PaczkoModState getServerState(MinecraftServer server) {
        return server.overworld().getDataStorage().computeIfAbsent(FACTORY, "paczkomod_global_inventory");
    }
}