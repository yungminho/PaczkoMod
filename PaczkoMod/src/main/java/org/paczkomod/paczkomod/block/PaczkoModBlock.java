package org.paczkomod.paczkomod.block;

import org.jetbrains.annotations.NotNull;
import org.paczkomod.paczkomod.screen.PaczkoModScreenHandler;
import org.paczkomod.paczkomod.world.PaczkoModState;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;

import java.util.*;

public class PaczkoModBlock extends Block {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty FORMED = BooleanProperty.create("formed");
    public static final IntegerProperty PART_ID = IntegerProperty.create("part_id", 0, 7);

    public PaczkoModBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(FORMED, false)
                .setValue(PART_ID, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, FORMED, PART_ID);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return this.defaultBlockState().setValue(FACING, ctx.getHorizontalDirection().getOpposite());
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        if (!level.isClientSide) {

            if (state.getValue(FORMED)) {
                openPaczkoModGui(level, (ServerPlayer) player);
                return InteractionResult.SUCCESS;
            }

            Set<BlockPos> structureBlocks = new HashSet<>();
            scanBlocks(level, pos, structureBlocks);

            if (structureBlocks.size() != 8 || !isValidShape(structureBlocks)) {
                player.sendSystemMessage(Component.literal("Niekompletny lub błędny kształt (wymagane 4x2)").withStyle(ChatFormatting.RED));
                return InteractionResult.SUCCESS;
            }

            Direction masterFacing = player.getDirection().getOpposite();

            formMultiblock(level, structureBlocks, masterFacing);

            player.sendSystemMessage(Component.literal("Paczkomat złożony!").withStyle(ChatFormatting.GREEN));
        }
        return InteractionResult.SUCCESS;
    }

    private void formMultiblock(Level level, Set<BlockPos> blocks, Direction facing) {
        List<BlockPos> allBlocks = new ArrayList<>(blocks);
        int minY = allBlocks.stream().mapToInt(BlockPos::getY).min().orElse(0);

        List<BlockPos> bottomRow = new ArrayList<>();
        List<BlockPos> topRow = new ArrayList<>();

        for (BlockPos pos : allBlocks) {
            if (pos.getY() == minY) bottomRow.add(pos);
            else topRow.add(pos);
        }

        Comparator<BlockPos> sortLeftToRight = switch (facing) {
            case NORTH -> (b1, b2) -> Integer.compare(b2.getX(), b1.getX());
            case WEST -> Comparator.comparingInt(BlockPos::getZ);
            case EAST -> (b1, b2) -> Integer.compare(b2.getZ(), b1.getZ());
            default -> Comparator.comparingInt(BlockPos::getX);
        };

        bottomRow.sort(sortLeftToRight);
        topRow.sort(sortLeftToRight);

        for (int i = 0; i < 4; i++) {
            if (i < bottomRow.size()) updateBlockState(level, bottomRow.get(i), i, facing);
            if (i < topRow.size()) updateBlockState(level, topRow.get(i), i + 4, facing);
        }
    }

    private void updateBlockState(Level level, BlockPos pos, int id, Direction facing) {
        BlockState oldState = level.getBlockState(pos);

        level.setBlock(pos, oldState
                .setValue(FORMED, true)
                .setValue(PART_ID, id)
                .setValue(FACING, facing), 3);
    }

    private void openPaczkoModGui(Level level, ServerPlayer player) {
        PaczkoModState paczkomodData = PaczkoModState.getServerState(Objects.requireNonNull(level.getServer()));
        SimpleContainer viewContainer = new SimpleContainer(27);
        for(int i = 0; i< paczkomodData.inventory.size(); i++) viewContainer.setItem(i, paczkomodData.inventory.get(i));
        viewContainer.addListener((c) -> {
            for(int i=0; i<27; i++) paczkomodData.inventory.set(i, c.getItem(i));
            paczkomodData.setDirty();
        });
        player.openMenu(new SimpleMenuProvider((syncId, inv, p) -> new PaczkoModScreenHandler(syncId, inv, viewContainer, new SimpleContainer(2)), Component.literal("Paczkomat")));
    }

    private void scanBlocks(Level level, BlockPos pos, Set<BlockPos> visited) {
        if (visited.size() >= 9) return;
        visited.add(pos);
        for (Direction direction : Direction.values()) {
            BlockPos offsetPos = pos.relative(direction);
            if (!visited.contains(offsetPos) && level.getBlockState(offsetPos).is(this)) {
                scanBlocks(level, offsetPos, visited);
            }
        }
    }

    private boolean isValidShape(Set<BlockPos> blocks) {
        int minX = Integer.MAX_VALUE, maxX = Integer.MIN_VALUE;
        int minY = Integer.MAX_VALUE, maxY = Integer.MIN_VALUE;
        int minZ = Integer.MAX_VALUE, maxZ = Integer.MIN_VALUE;
        for (BlockPos p : blocks) {
            if (p.getX() < minX) minX = p.getX(); if (p.getX() > maxX) maxX = p.getX();
            if (p.getY() < minY) minY = p.getY(); if (p.getY() > maxY) maxY = p.getY();
            if (p.getZ() < minZ) minZ = p.getZ(); if (p.getZ() > maxZ) maxZ = p.getZ();
        }
        int widthX = maxX - minX + 1, heightY = maxY - minY + 1, depthZ = maxZ - minZ + 1;
        return heightY == 2 && ((widthX == 4 && depthZ == 1) || (widthX == 1 && depthZ == 4));
    }
}