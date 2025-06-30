package com.cultivation_mod.element_setup;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record PlayerElements(Map<AxisElements, Integer> playerElements, List<AxisElements> favoredElements) {

    public static final Codec<PlayerElements> PLAYER_ELEMENTS_CODEC = RecordCodecBuilder.create(i -> i.group(
            AxisElements.ELEMENT_MAP_CODEC.fieldOf("playerElements").forGetter(PlayerElements::playerElements),
            AxisElements.AXIS_ELEMENTS_CODEC.listOf().fieldOf("favoredElements").forGetter(PlayerElements::favoredElements)
    ).apply(i, PlayerElements::new));

    public static final PacketCodec<ByteBuf,PlayerElements> PLAYER_ELEMENTS_PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.map(HashMap::new, AxisElements.AXIS_ELEMENTS_PACKET_CODEC, PacketCodecs.INTEGER, 10),
            PlayerElements::playerElements,
            AxisElements.AXIS_ELEMENTS_PACKET_CODEC.collect(PacketCodecs.toList()),
            PlayerElements::favoredElements,
            PlayerElements::new);
}
