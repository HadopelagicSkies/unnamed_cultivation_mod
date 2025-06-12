package com.cultivation_mod.blocks;

import com.cultivation_mod.CultivationModBlockEntities;
import com.cultivation_mod.CultivationModComponents;
import com.cultivation_mod.FengShuiCalc;
import com.cultivation_mod.ImplementInventory;
import com.cultivation_mod.element_setup.AxisElements;
import com.cultivation_mod.items.AspectedQiItem;
import com.cultivation_mod.items.UnaspectedQiItem;
import com.cultivation_mod.recipes.AlchemyRecipe;
import com.cultivation_mod.recipes.AlchemyRecipeInput;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.entity.ItemEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.recipe.input.RecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class JadeCauldronEntity extends BlockEntity implements ImplementInventory, SidedInventory {
    public static BlockEntityTicker<JadeCauldronEntity> jadeCauldronEntityTicker = new JadeCauldronEntityTicker();

    public JadeCauldronEntity(BlockPos pos, BlockState state) {
        super(CultivationModBlockEntities.JADE_CAULDRON_BLOCK_ENTITY, pos, state);
    }
    private final int craftTime =200;
    private int craftingProgress = craftTime;

    public final DefaultedList<ItemStack> items = DefaultedList.ofSize(8, ItemStack.EMPTY);

    public final SingleVariantStorage<FluidVariant> fluidStorage = new SingleVariantStorage<>() {
        @Override
        protected FluidVariant getBlankVariant() {
            return FluidVariant.blank();
        }

        @Override
        protected long getCapacity(FluidVariant variant) {
            return FluidConstants.BUCKET;
        }

        @Override
        protected void onFinalCommit() {
            markDirty();
            if (!world.isClient) {

            }
        }

        @Override
        protected boolean canInsert(FluidVariant variant) {
            return variant.isOf(Fluids.WATER);
        }

        @Override
        protected boolean canExtract(FluidVariant variant) {
            return variant.isOf(Fluids.WATER);
        }
    };

    @Override
    public DefaultedList<ItemStack> getItems() {
        return items;
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        return new int[] {0,1,2,3,4,5,6,7};
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        return true;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return true;
    }

    @Override
    public void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        Inventories.writeNbt(nbt, items, registryLookup);
        SingleVariantStorage.writeNbt(fluidStorage,FluidVariant.CODEC,nbt,registryLookup);
    }

    @Override
    public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        Inventories.readNbt(nbt, items, registryLookup);
        SingleVariantStorage.readNbt(fluidStorage,FluidVariant.CODEC, FluidVariant::blank,nbt,registryLookup);
    }

    public int getCraftingProgress() {
        return craftingProgress;
    }

    public void setCraftingProgress(int craftingProgress) {
        this.craftingProgress = craftingProgress;
    }

    public void processCrafting(World world, BlockPos pos){
        List<ItemStack> inputItems = new ArrayList<>(this.items);
        inputItems.removeIf(ItemStack::isEmpty);
        RecipeInput input = AlchemyRecipeInput.create(inputItems);
        if(world.getServer() != null) {
            Optional<RecipeEntry<AlchemyRecipe>> recipeOptional = world.getServer().getRecipeManager().getFirstMatch(AlchemyRecipe.Type.INSTANCE, input, world);
            if(recipeOptional.isPresent()){
                int minIngredientCount = 65;
                for(ItemStack item:inputItems){
                    if(minIngredientCount > item.getCount() )
                        minIngredientCount = item.getCount();
                }
                AlchemyRecipe recipe = recipeOptional.get().value();
                int outputCount = recipe.getOutput().getCount();
                double fengShuiScore = FengShuiCalc.calculateFengShui(world, pos);
                // modify output count here
                outputCount += (int) (fengShuiScore / 1200);


                double qiComponentEfficiency = 0.5+ (fengShuiScore/ 1200);
                for (int i = 0; i < minIngredientCount; i++) {
                    ItemStack outputStack = recipe.craft(input,null).copyWithCount(outputCount);

                    if(outputStack.getItem() instanceof AspectedQiItem){
                        List<AxisElements> presentElements= new ArrayList<>();
                        for(ItemStack elementItem : inputItems) {
                            if (elementItem.get(CultivationModComponents.ITEM_ELEMENTS) != null) {
                                elementItem.get(CultivationModComponents.ITEM_ELEMENTS).forEach((element, integer) -> presentElements.add(element));
                            }
                        }
                        Map<AxisElements, Integer> elementTotals = new HashMap<>();
                        for(ItemStack item : inputItems){
                            if(item.get(CultivationModComponents.ITEM_QI) != null){
                                presentElements.forEach((element) -> elementTotals.put(element,elementTotals.get(element) + (int) ((double) item.get(CultivationModComponents.ITEM_QI) / presentElements.size() *qiComponentEfficiency)));
                            }else if(item.get(CultivationModComponents.ITEM_ELEMENTS) != null){
                                item.get(CultivationModComponents.ITEM_ELEMENTS).forEach((element,integer) -> elementTotals.put(element, elementTotals.get(element) + (int) (integer*qiComponentEfficiency)));
                            }
                        }
                        outputStack.set(CultivationModComponents.ITEM_ELEMENTS,elementTotals);
                    }else if(outputStack.getItem() instanceof UnaspectedQiItem) {
                        int qiTotal = outputStack.get(CultivationModComponents.ITEM_QI).intValue();
                        for(ItemStack item : inputItems) {
                            if (item.get(CultivationModComponents.ITEM_QI) != null) {
                                qiTotal += item.get(CultivationModComponents.ITEM_QI);
                            } else if (item.get(CultivationModComponents.ITEM_ELEMENTS) != null) {
                                for(int elements: item.get(CultivationModComponents.ITEM_ELEMENTS).values())
                                    qiTotal += elements;
                            }
                        }
                        outputStack.set(CultivationModComponents.ITEM_QI,(int)(qiTotal*qiComponentEfficiency));
                    }
                    // Further modify output components here




                    world.spawnEntity(new ItemEntity(world,pos.getX(),pos.getY()+1,pos.getZ(),outputStack));
                }

                for(ItemStack item: this.items){
                    item.decrement(minIngredientCount);
                }
                try(Transaction transaction = Transaction.openOuter()) {
                    fluidStorage.extract(fluidStorage.variant, fluidStorage.getAmount(), transaction);
                    world.setBlockState(pos,world.getBlockState(pos).with(Properties.CONDITIONAL,false));
                    transaction.commit();
                }
                this.markDirty();
                world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_BREWING_STAND_BREW, SoundCategory.BLOCKS, 2F, MathHelper.nextBetween(world.random, 0.8F, 1.2F));
            }
            else
                world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 2F, MathHelper.nextBetween(world.random, 0.8F, 1.2F));
        }
    }


    public static class JadeCauldronEntityTicker implements BlockEntityTicker<JadeCauldronEntity>{
        @Override
        public void tick(World world, BlockPos pos, BlockState state, JadeCauldronEntity blockEntity) {
            if (!world.isClient) {
                int progress = blockEntity.getCraftingProgress();
                if (progress < blockEntity.craftTime && blockEntity.fluidStorage.getAmount() >= blockEntity.fluidStorage.getCapacity()) {
                    blockEntity.setCraftingProgress(progress + 1);
                    if (progress % 20 == 0) {
                        world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_AMETHYST_BLOCK_STEP, SoundCategory.BLOCKS, 2F, MathHelper.nextBetween(world.random, 0.8F, 1.2F));
                    }
                } else if (progress == blockEntity.craftTime && blockEntity.fluidStorage.getAmount() >= blockEntity.fluidStorage.getCapacity()) {
                    blockEntity.processCrafting(world,pos);
                    blockEntity.setCraftingProgress(progress + 1);
                } else if (progress > 0 && blockEntity.fluidStorage.getAmount() < blockEntity.fluidStorage.getCapacity()) {
                    blockEntity.setCraftingProgress(0);
                }
            }
        }
    }


}
