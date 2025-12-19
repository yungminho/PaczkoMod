package org.paczkomod.paczkomod.world;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.NonNullList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

import java.util.List;
import java.util.Objects;

public class PaczkoModState extends SavedData {
    public final NonNullList<ItemStack> inventory = NonNullList.withSize(27, ItemStack.EMPTY);

    public PaczkoModState() {}

    public PaczkoModState(List<ItemStack> items) {
        for (int i = 0; i < Math.min(items.size(), this.inventory.size()); i++) {
            this.inventory.set(i, items.get(i));
        }
    }

    public static final Codec<PaczkoModState> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ItemStack.OPTIONAL_CODEC.listOf().fieldOf("inventory").forGetter(state -> state.inventory)
    ).apply(instance, PaczkoModState::new));


    public static final SavedDataType<PaczkoModState> TYPE = new SavedDataType<>(
            "paczkomod_global_inventory",
            (context) -> new PaczkoModState(),
            (context) -> CODEC,
            null
    );

    public static PaczkoModState getServerState(MinecraftServer server) {
        return Objects.requireNonNull(server.getLevel(Level.OVERWORLD)).getDataStorage().computeIfAbsent(TYPE);
    }
}