package com.cultivation_mod.element_setup;

import com.cultivation_mod.CultivationMod;
import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.minecraft.util.DyeColor;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.function.ValueLists;

import java.util.Map;
import java.util.function.IntFunction;

public enum AxisElements implements StringIdentifiable {
    FIRE("fire", DyeColor.RED.getEntityColor()),
    WATER("water", DyeColor.BLUE.getEntityColor()),
    AIR("air", DyeColor.YELLOW.getEntityColor()),
    EARTH("earth", DyeColor.BROWN.getEntityColor()),
    LIGHTNING("lightning", DyeColor.PURPLE.getEntityColor()),
    YIN("yin", DyeColor.GRAY.getEntityColor()),
    YANG("yang", DyeColor.WHITE.getEntityColor()),
    ;

    private final String name;
    private final Text translatableName;
    private final int color;

    AxisElements(String name,int color) {
        this.name = name;
        this.color = color;
        this.translatableName = Text.translatable(CultivationMod.MOD_ID+"."+name);
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
    public static final IntFunction<AxisElements> INDEX_TO_VALUE = ValueLists.createIdToValueFunction(AxisElements::ordinal, values(), ValueLists.OutOfBoundsHandling.ZERO);
    public static final PacketCodec<ByteBuf, AxisElements> AXIS_ELEMENTS_PACKET_CODEC = PacketCodecs.indexed(INDEX_TO_VALUE, AxisElements::ordinal);
    public static final Codec<AxisElements> AXIS_ELEMENTS_CODEC = StringIdentifiable.createCodec(AxisElements::values);
    public static final Codec<Map<AxisElements, Integer>> ELEMENT_MAP_CODEC = Codec.unboundedMap(AxisElements.AXIS_ELEMENTS_CODEC,Codec.INT);
}
