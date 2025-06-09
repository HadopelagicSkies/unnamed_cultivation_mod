package com.cultivation_mod.blocks;

import com.cultivation_mod.CultivationModBlockEntities;
import com.cultivation_mod.ImplementInventory;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class JadeCauldronEntity extends BlockEntity implements ImplementInventory, SidedInventory {
    public static BlockEntityTicker<JadeCauldronEntity> jadeCauldronEntityTicker = new JadeCauldronEntityTicker();

    public JadeCauldronEntity(BlockPos pos, BlockState state) {
        super(CultivationModBlockEntities.JADE_CAULDRON_BLOCK_ENTITY, pos, state);
    }

    public final DefaultedList<ItemStack> items = DefaultedList.ofSize(8, ItemStack.EMPTY);

    public final SingleVariantStorage<FluidVariant> fluidStorage = new SingleVariantStorage<>() {
        @Override
        protected FluidVariant getBlankVariant() {
            return FluidVariant.blank();
        }

        @Override
        protected long getCapacity(FluidVariant variant) {
            // Here, you can pick your capacity depending on the fluid variant.
            // For example, if we want to store 8 buckets of any fluid:
            return (FluidConstants.BUCKET) / 81; // This will convert it to mB amount to be consistent
        }

        @Override
        protected void onFinalCommit() {
            // Called after a successful insertion or extraction, markDirty to ensure the new amount and variant will be saved properly.
            markDirty();
            if (!world.isClient) {
//                var buf = PacketByteBufs.create();
//                // Write your data here.
//                PlayerLookup.tracking(JadeCauldronEntity.this).forEach(player -> {
//                    ServerPlayNetworking.send(player, CultivationModBlockEntities.JADE_CAULDRON_BLOCK_ENTITY, buf);
//                });
            }
        }

        @Override
        protected boolean canInsert(FluidVariant variant) {
            return FluidVariant.blank().isOf(Fluids.WATER);
        }

        @Override
        protected boolean canExtract(FluidVariant variant) {
            return FluidVariant.blank().isOf(Fluids.WATER);
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

    }

    @Override
    public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        Inventories.readNbt(nbt, items, registryLookup);
    }

    public static class JadeCauldronEntityTicker implements BlockEntityTicker<JadeCauldronEntity>{
        @Override
        public void tick(World world, BlockPos pos, BlockState state, JadeCauldronEntity blockEntity) {

        }
    }


}
