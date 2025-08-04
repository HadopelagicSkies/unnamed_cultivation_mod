package com.cultivation_mod.element_setup;

import com.cultivation_mod.CultivationMod;
import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.function.ValueLists;

import java.util.Map;
import java.util.function.IntFunction;

public enum CompoundElements implements StringIdentifiable {
    FIRE_YIN("fire",AxisElements.FIRE,AxisElements.YIN ,DyeColor.RED.getEntityColor()),
    WATER_YIN("water",AxisElements.WATER,AxisElements.YIN,DyeColor.BLUE.getEntityColor()),
    AIR_YIN("air",AxisElements.AIR,AxisElements.YIN,DyeColor.YELLOW.getEntityColor()),
    EARTH_YIN("earth",AxisElements.EARTH,AxisElements.YIN,DyeColor.BROWN.getEntityColor()),
    LIGHTNING_YIN("lightning",AxisElements.LIGHTNING,AxisElements.YIN,DyeColor.PURPLE.getEntityColor()),

    ASH("fire",AxisElements.FIRE,AxisElements.YANG ,DyeColor.RED.getEntityColor()),
    ICE("water",AxisElements.WATER,AxisElements.YANG,DyeColor.BLUE.getEntityColor()),
    AIR_YANG("air",AxisElements.AIR,AxisElements.YANG,DyeColor.YELLOW.getEntityColor()),
    WOOD("earth",AxisElements.EARTH,AxisElements.YANG,DyeColor.BROWN.getEntityColor()),
    METAL("lightning",AxisElements.LIGHTNING,AxisElements.YANG,DyeColor.PURPLE.getEntityColor()),
    ;

    private final String name;
    private final Text translatableName;
    private final AxisElements element1;
    private final AxisElements element2;
    private final int color;

    CompoundElements(String name,AxisElements element1,AxisElements element2,int color) {
        this.name = name;
        this.color = color;
        this.element1 = element1;
        this.element2 = element2;
        this.translatableName = Text.translatable(CultivationMod.MOD_ID+"."+name);
    }

    public AxisElements getElement1() {
        return element1;
    }

    public AxisElements getElement2() {
        return element2;
    }

    public Text getTranslatableName() {
        return translatableName;
    }

    public int getColor() {
        return color;
    }

    @Override
    public String asString() {
        return name;
    }
    public static final IntFunction<CompoundElements> INDEX_TO_VALUE = ValueLists.createIdToValueFunction(CompoundElements::ordinal, values(), ValueLists.OutOfBoundsHandling.ZERO);
    public static final PacketCodec<ByteBuf, CompoundElements> AXIS_ELEMENTS_PACKET_CODEC = PacketCodecs.indexed(INDEX_TO_VALUE, CompoundElements::ordinal);
    public static final Codec<CompoundElements> COMPOUND_ELEMENTS_CODEC = StringIdentifiable.createCodec(CompoundElements::values);
    public static final Codec<Map<CompoundElements, Integer>> ELEMENT_MAP_CODEC = Codec.unboundedMap(CompoundElements.COMPOUND_ELEMENTS_CODEC,Codec.INT);
}
