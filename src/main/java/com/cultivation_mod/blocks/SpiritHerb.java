package com.cultivation_mod.blocks;

import com.cultivation_mod.CultivationModBlocks;
import com.cultivation_mod.CultivationModItems;
import com.cultivation_mod.CultivationModTags;
import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class SpiritHerb extends CropBlock {
    public SpiritHerb(Settings settings) {
        super(settings.mapColor(MapColor.RED));
        this.setDefaultState(getDefaultState().with(CultivationModBlocks.LOCAL_ELEMENT,0).with(Properties.AGE_7,0));
    }

    @Override
    protected ItemConvertible getSeedsItem() {
        return CultivationModItems.SPIRIT_HERB_SEEDS;
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        int localElement=0; //feed pos into some function for local
        world.setBlockState(pos,state.with(CultivationModBlocks.LOCAL_ELEMENT,localElement));
        switch (localElement){
            case 0 -> this.settings.mapColor(MapColor.RED);
            case 1 -> this.settings.mapColor(MapColor.BLUE);
            case 2 -> this.settings.mapColor(MapColor.PALE_GREEN);
            case 3 -> this.settings.mapColor(MapColor.YELLOW);
            case 4 -> this.settings.mapColor(MapColor.PALE_PURPLE);
        };

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

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(CultivationModBlocks.LOCAL_ELEMENT);
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
