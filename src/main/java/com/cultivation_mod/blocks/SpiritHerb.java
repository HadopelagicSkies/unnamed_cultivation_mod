package com.cultivation_mod.blocks;

import com.cultivation_mod.CultivationModItems;
import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class SpiritHerb extends CropBlock {
    //public final IntProperty LOCAL_ELEMENT = IntProperty.of("local_element",0,4) ;
    public SpiritHerb(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().mapColor(MapColor.RED));
    }

    @Override
    protected ItemConvertible getSeedsItem() {
        return CultivationModItems.SPIRIT_HERB_SEEDS;
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        int localElement=0; //feed pos into some function for local
        //world.setBlockState(pos,state.with(LOCAL_ELEMENT,localElement));
        super.onPlaced(world, pos, state, placer, itemStack);
    }

    @Override
    public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {

        super.onBroken(world, pos, state);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return AGE_TO_SHAPE[getAge(state)];
    }

    public static void handleColors(){

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
