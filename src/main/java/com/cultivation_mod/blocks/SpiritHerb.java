package com.cultivation_mod.blocks;

import com.cultivation_mod.CultivationModBlocks;
import com.cultivation_mod.CultivationModItems;
import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class SpiritHerb extends CropBlock {
    public SpiritHerb(Settings settings) {
        super(settings.mapColor((state) ->{
            int localElement = state.get(CultivationModBlocks.LOCAL_ELEMENT);
            switch (localElement) {
                case 0 -> {
                    return MapColor.RED;
                }
                case 1 -> {
                    return MapColor.BLUE;
                }
                case 2 -> {
                    return MapColor.GREEN;
                }
                case 3 -> {
                    return MapColor.YELLOW;
                }
                case 4 -> {
                    return MapColor.PURPLE;
                }
            };
                    return MapColor.WHITE;
                }
        ));
            this.setDefaultState(getDefaultState().with(CultivationModBlocks.LOCAL_ELEMENT,0).with(CultivationModBlocks.LOCAL_QI,0).with(Properties.AGE_7,0));
    }

    @Override
    protected ItemConvertible getSeedsItem() {
        return CultivationModItems.SPIRIT_HERB_SEEDS;
    }

    public void setQiAndElement(World world, BlockPos pos){
        int localElement = Math.abs(pos.getX() % 5);  //feed pos into some function for local
        int localQi = 400; //feed pos into some function for local
        BlockState newState = world.getBlockState(pos);
        world.setBlockState(pos, newState.with(CultivationModBlocks.LOCAL_ELEMENT, localElement).with(CultivationModBlocks.LOCAL_QI, localQi).with(Properties.AGE_7,newState.get(Properties.AGE_7)));
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        if (!world.isClient) {
            setQiAndElement(world,pos);
        }
    }

    @Override
    public void applyGrowth(World world, BlockPos pos, BlockState state) {
        super.applyGrowth(world, pos, state);
        if (!world.isClient) {
            setQiAndElement(world,pos);
        }
    }

    @Override
    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        super.randomTick(state, world, pos, random);
        if (!world.isClient) {
            setQiAndElement(world,pos);
        }
    }

    @Override
    public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {

        super.onBroken(world, pos, state);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return AGE_TO_SHAPE[getAge(state)];
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(CultivationModBlocks.LOCAL_ELEMENT);
        builder.add(CultivationModBlocks.LOCAL_QI);
        builder.add(Properties.AGE_7);
    }

    private static final VoxelShape[] AGE_TO_SHAPE = new VoxelShape[]{Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D),
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 3.0D, 16.0D),
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D),
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 5.0D, 16.0D),
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D),
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 7.0D, 16.0D),
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 9.0D, 16.0D)
    };



}
