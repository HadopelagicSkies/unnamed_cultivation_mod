package com.cultivation_mod.element_setup;

import com.cultivation_mod.CultivationMod;
import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.text.Text;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.function.ValueLists;

import java.util.function.IntFunction;

public enum AxisElements implements StringIdentifiable {
    FIRE("fire"),
    WATER("water"),
    AIR("air"),
    EARTH("earth"),
    YIN("yin"),
    YANG("yang"),
    ;

    private final String name;
    private final Text translatableName;

    AxisElements(String name) {
        this.name = name;
        this.translatableName = Text.translatable(CultivationMod.MOD_ID+"."+name);
    }

    public Text getTranslatableName() {
        return translatableName;
    }

    @Override
    public String asString() {
        return name;
    }
    public static final IntFunction<AxisElements> INDEX_TO_VALUE = ValueLists.createIdToValueFunction(AxisElements::ordinal, values(), ValueLists.OutOfBoundsHandling.ZERO);
    public static final PacketCodec<ByteBuf, AxisElements> AXIS_ELEMENTS_PACKET_CODEC = PacketCodecs.indexed(INDEX_TO_VALUE, AxisElements::ordinal);
    public static final Codec<AxisElements> AXIS_ELEMENTS_CODEC = Codec.INT.xmap(INDEX_TO_VALUE::apply, AxisElements::ordinal);
}
