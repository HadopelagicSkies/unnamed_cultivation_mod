package com.cultivation_mod.blocks;

import com.cultivation_mod.CultivationModBlockEntities;
import com.mojang.serialization.MapCodec;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class JadeCauldron extends Block implements BlockEntityProvider {
    protected static final VoxelShape SHAPE = Block.createCuboidShape(1.0F, 0.0F, 1.0F, 15.0F, 16.0F, 15.0F);

    public JadeCauldron(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(Properties.CONDITIONAL,false));
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

            if (!player.isSneaking()) {
                if(player.getStackInHand(hand).isOf(Items.WATER_BUCKET)){
                    try(Transaction transaction = Transaction.openOuter()) {
                        long amountInserted = blockEntity.fluidStorage.insert(FluidVariant.of(Fluids.WATER), FluidConstants.BUCKET, transaction);
                        if (amountInserted == FluidConstants.BUCKET) {
                            transaction.commit();
                            world.playSound(null,pos.getX(),pos.getY(),pos.getZ(), SoundEvents.ITEM_BUCKET_FILL, SoundCategory.PLAYERS,1F, MathHelper.nextBetween(player.getWorld().random, 0.8F, 1.2F));
                            world.setBlockState(pos,state.with(Properties.CONDITIONAL,true));

                            if(!player.isCreative()){
                                player.getInventory().setStack(player.getInventory().selectedSlot,new ItemStack(Items.BUCKET,1));
                                player.getInventory().markDirty();
                            }
                            return ActionResult.SUCCESS;
                        }
                    }
                } else if(player.getStackInHand(hand).isOf(Items.BUCKET)){
                    try(Transaction transaction = Transaction.openOuter()) {
                        long amountExtracted = blockEntity.fluidStorage.extract(FluidVariant.of(Fluids.WATER), FluidConstants.BUCKET, transaction);
                        if (amountExtracted == FluidConstants.BUCKET) {
                            transaction.commit();
                            world.playSound(null,pos.getX(),pos.getY(),pos.getZ(), SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.PLAYERS,1F, MathHelper.nextBetween(player.getWorld().random, 0.8F, 1.2F));
                            world.setBlockState(pos,state.with(Properties.CONDITIONAL,false));

                            if(!player.isCreative()) {
                                player.getInventory().setStack(player.getInventory().selectedSlot,stack.copyWithCount(stack.getCount() - 1));
                                player.getInventory().offerOrDrop(new ItemStack(Items.WATER_BUCKET, 1));
                                player.getInventory().markDirty();
                            }
                            return ActionResult.SUCCESS;
                        }
                    }
                } else if (!player.getStackInHand(hand).isEmpty()) {
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
                    world.playSound(null,pos.getX(),pos.getY(),pos.getZ(), SoundEvents.BLOCK_DECORATED_POT_INSERT, SoundCategory.PLAYERS,1F, MathHelper.nextBetween(player.getWorld().random, 0.8F, 1.2F));
                    player.getStackInHand(hand).setCount(countLeftover);
                    blockEntity.markDirty();
                    player.getInventory().markDirty();
                }
                else if(player.getStackInHand(hand).isEmpty()){
                    blockEntity.setCraftingProgress(0);
                }
            } else {
                if (player.getActiveItem().isEmpty()) {
                    world.playSound(null,pos.getX(),pos.getY(),pos.getZ(), SoundEvents.BLOCK_DECORATED_POT_INSERT_FAIL, SoundCategory.PLAYERS,1F, MathHelper.nextBetween(player.getWorld().random, 0.8F, 1.2F));
                    player.getInventory().offerOrDrop(blockEntity.getStack(lastFull));
                    blockEntity.setStack(lastFull, ItemStack.EMPTY);
                    blockEntity.markDirty();
                    player.getInventory().markDirty();
                }
            }
        }
        return ActionResult.SUCCESS;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(Properties.CONDITIONAL);
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
