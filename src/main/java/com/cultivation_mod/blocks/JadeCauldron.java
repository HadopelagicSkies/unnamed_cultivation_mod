package com.cultivation_mod.blocks;

import com.cultivation_mod.CultivationModBlockEntities;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class JadeCauldron extends Block implements BlockEntityProvider {
    protected static final VoxelShape SHAPE = Block.createCuboidShape(3.0F, 0.0F, 3.0F, 13.0F, 16.0F, 13.0F);

    public JadeCauldron(Settings settings) {
        super(settings);
    }

    @Override
    protected ActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient()) {
            JadeCauldronEntity blockEntity = (JadeCauldronEntity) world.getBlockEntity(pos);

            int firstOpenOrMatching = 0;
            int lastFull = 0;
            for (int i = 0; i < 8; i++) {
                if (blockEntity.getStack(i).isEmpty() || blockEntity.getStack(i).isOf(stack.getItem())) {
                    firstOpenOrMatching = i;
                    break;
                }
            }
            for (int i = 7; i >= 0; i--) {
                if (!blockEntity.getStack(i).isEmpty()) {
                    lastFull = i;
                    break;
                }
            }

            //add in bucket water shit

            if (!player.isSneaking()) {
                if (!player.getStackInHand(hand).isEmpty()) {
                    int countLeftover = stack.getCount();
                    if (blockEntity.getStack(firstOpenOrMatching).isEmpty()) {
                        blockEntity.setStack(firstOpenOrMatching, stack.copy());
                        countLeftover = 0;
                    }
                    else if(blockEntity.getStack(firstOpenOrMatching).isOf(stack.getItem())){
                        int stackCount = blockEntity.getStack(firstOpenOrMatching).getCount() + stack.getCount();
                        blockEntity.setStack(firstOpenOrMatching, stack.copyWithCount(Math.min(stackCount, 64)));
                        if (stackCount > 64)
                            countLeftover = stackCount % 64;
                    }

                    player.getStackInHand(hand).setCount(countLeftover);
                    blockEntity.markDirty();
                    player.getInventory().markDirty();
                    return ActionResult.SUCCESS;
                }
            } else {
                if (player.getActiveItem().isEmpty()) {
                    player.getInventory().offerOrDrop(blockEntity.getStack(lastFull));
                    blockEntity.setStack(lastFull, ItemStack.EMPTY);
                    blockEntity.markDirty();
                    player.getInventory().markDirty();
                    return ActionResult.SUCCESS;
                }
            }
        }
        return ActionResult.FAIL;
    }

    @Override
    protected MapCodec<? extends JadeCauldron> getCodec() {
        return createCodec(JadeCauldron::new);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new JadeCauldronEntity(pos, state);
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }


    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        // Make sure to check world.isClient if you only want to tick only on serverside.
        return validateTicker(type, JadeCauldronEntity.jadeCauldronEntityTicker);
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof JadeCauldronEntity jadeCauldron) {
                ItemScatterer.spawn(world, pos, jadeCauldron);
                // update comparators
                world.updateComparators(pos,this);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }


    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }
    @Nullable
    protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> validateTicker(BlockEntityType<A> givenType, BlockEntityTicker<? super E> ticker) {
        return CultivationModBlockEntities.JADE_CAULDRON_BLOCK_ENTITY == givenType ? (BlockEntityTicker<A>) ticker : null;
    }
}
