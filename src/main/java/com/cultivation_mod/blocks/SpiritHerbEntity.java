package com.cultivation_mod.blocks;

import com.cultivation_mod.CultivationModBlockEntities;
import com.cultivation_mod.element_setup.AxisElements;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.*;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;

import java.util.HashMap;
import java.util.Map;

public class SpiritHerbEntity extends BlockEntity {
    private Map<AxisElements, Integer> storedElements = new HashMap<>();
    private int growthsTicked =0;
    public SpiritHerbEntity(BlockPos pos, BlockState state) {
        super(CultivationModBlockEntities.SPIRIT_HERB_BLOCK_ENTITY,pos, state);
    }

    public Map<AxisElements, Integer> getStoredElements() {
        return storedElements;
    }

    public void setStoredElements(Map<AxisElements, Integer> storedElements) {
        this.storedElements = storedElements;
    }

    public int getGrowthsTicked() {
        return growthsTicked;
    }

    public void setGrowthsTicked(int growthsTicked) {
        this.growthsTicked = growthsTicked;
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        AxisElements.ELEMENT_MAP_CODEC.encode(storedElements,registries.getOps(NbtOps.INSTANCE),nbt);
        Codec.INT.encode(growthsTicked,registries.getOps(NbtOps.INSTANCE),nbt);
        super.writeNbt(nbt, registries);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        this.storedElements = AxisElements.ELEMENT_MAP_CODEC.parse(registries.getOps(NbtOps.INSTANCE), nbt.get("elements")).result().orElse(new HashMap<>());
        this.growthsTicked = Codec.INT.parse(registries.getOps(NbtOps.INSTANCE), nbt.get("growths")).result().orElse(0);
        super.readNbt(nbt, registries);
    }
}
