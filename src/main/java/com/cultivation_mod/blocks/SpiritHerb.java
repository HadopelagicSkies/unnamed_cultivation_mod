package com.cultivation_mod.blocks;

import com.cultivation_mod.CultivationModComponents;
import com.cultivation_mod.CultivationModItems;
import com.cultivation_mod.CultivationModTags;
import com.cultivation_mod.element_setup.AxisElements;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class SpiritHerb extends CropBlock implements BlockEntityProvider {
    private Item cropItem = Items.AIR;
    private Item seedsItem= Items.AIR;
    public SpiritHerb(Settings settings) {
        super(settings);
        this.setDefaultState(getDefaultState().with(Properties.AGE_7,0));
    }
    public Item getCropItem() {
        return cropItem;
    }

    @Override
    protected ItemConvertible getSeedsItem() {
        return seedsItem;
    }

    public <T extends SpiritHerb> T setDropItems(Item cropItem,Item seedsItem){
        this.cropItem = cropItem != null ? cropItem:Items.AIR;
        this.seedsItem = seedsItem != null ? seedsItem:Items.AIR;
        return (T) this;
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        if (!world.isClient) {
            Map<AxisElements, Integer> seedElements = itemStack.get(CultivationModComponents.ITEM_ELEMENTS);
            SpiritHerbEntity blockEntity = (SpiritHerbEntity) world.getBlockEntity(pos);
            if(blockEntity != null) {
                blockEntity.setStoredElements(seedElements);
                blockEntity.markDirty();
            }
        }
    }

    @Override
    protected boolean hasRandomTicks(BlockState state) {
        return true;
    }

    @Override
    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        super.randomTick(state, world, pos, random);
        if (!world.isClient) {
            SpiritHerbEntity blockEntity = (SpiritHerbEntity) world.getBlockEntity(pos);
            if(blockEntity != null && blockEntity.getGrowthsTicked() <= 50) {
                Map<AxisElements, Integer> storedElements = new HashMap<>(blockEntity.getStoredElements());
                RegistryEntry<Biome> biome = world.getBiome(pos);
                for(AxisElements element: storedElements.keySet()){
                    if(element == AxisElements.FIRE && biome.isIn(CultivationModTags.FIRE_BIOME)){ // OR if matches local qi
                        storedElements.put(element,storedElements.get(element)+5);  // make based on qi value rather than 5 once set up for that
                    }
                    else if(element == AxisElements.WATER && biome.isIn(CultivationModTags.WATER_BIOME)){
                        storedElements.put(element,storedElements.get(element)+5);
                    }
                    else if(element == AxisElements.AIR && biome.isIn(CultivationModTags.AIR_BIOME)){
                        storedElements.put(element,storedElements.get(element)+5);
                    }
                    else if(element == AxisElements.EARTH && biome.isIn(CultivationModTags.EARTH_BIOME)){
                        storedElements.put(element,storedElements.get(element)+5);
                    }
                    else if(element == AxisElements.LIGHTNING && biome.isIn(CultivationModTags.LIGHTNING_BIOME)){
                        storedElements.put(element,storedElements.get(element)+5);
                    }
                    else if(element == AxisElements.YIN && biome.isIn(CultivationModTags.YIN_BIOME)){
                        storedElements.put(element,storedElements.get(element)+5);
                    }
                    else if(element == AxisElements.YANG && biome.isIn(CultivationModTags.YANG_BIOME)){
                        storedElements.put(element,storedElements.get(element)+5);
                    }
                }
                blockEntity.setGrowthsTicked(blockEntity.getGrowthsTicked()+1);
                blockEntity.setStoredElements(storedElements);
                blockEntity.markDirty();
                world.spawnParticles(ParticleTypes.HAPPY_VILLAGER,pos.getX()+0.5,pos.getY(),pos.getZ()+0.5,6,0.5,0.5,0.5,0.1);
            }
        }
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if(!world.isClient() && !player.isCreative()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            ItemStack tool = player.getActiveItem();
            String enchants = tool.getEnchantments().toString();
            int seedsCount;
            int cropCount;
            if (enchants.contains("Enchantment Fortune}=>")) {
                int startIndex = enchants.indexOf("Enchantment Fortune}=>") + 22;
                String fortuneString = enchants.substring(startIndex, enchants.indexOf("}", startIndex));
                int fortuneLevel = Integer.parseInt(fortuneString);
                seedsCount = player.getRandom().nextBetween(fortuneLevel + 1, 2 * fortuneLevel + 2);
                cropCount = player.getRandom().nextBetween(fortuneLevel + 1, 2 * fortuneLevel + 2);
            } else {
                seedsCount = player.getRandom().nextBetween(1, 3);
                cropCount = player.getRandom().nextBetween(1, 3);
            }

            ItemStack seedItem = new ItemStack(this.getSeedsItem(), seedsCount);
            ItemStack cropItem = new ItemStack(this.getCropItem(), cropCount);

            if (state.get(Properties.AGE_7) < 7) {
                seedItem.setCount(1);
                world.spawnEntity(new ItemEntity(world, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, seedItem));
                return super.onBreak(world, pos, state, player);
            }

            if (blockEntity instanceof SpiritHerbEntity) {
                Map<AxisElements, Integer> seedElements = new HashMap<>(((SpiritHerbEntity) blockEntity).getStoredElements());
                for(AxisElements element: seedElements.keySet()){
                    seedElements.put(element, (int) (seedElements.get(element) * 0.5));
                }
                seedItem.set(CultivationModComponents.ITEM_ELEMENTS, seedElements);

                Map<AxisElements, Integer> cropElements = ((SpiritHerbEntity) blockEntity).getStoredElements();
                cropItem.set(CultivationModComponents.ITEM_ELEMENTS, cropElements);
            }
            world.spawnEntity(new ItemEntity(world, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, seedItem));
            world.spawnEntity(new ItemEntity(world, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, cropItem));
        }

        return super.onBreak(world, pos, state, player);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return AGE_TO_SHAPE[getAge(state)];
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
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


    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new SpiritHerbEntity(pos,state);
    }
}
