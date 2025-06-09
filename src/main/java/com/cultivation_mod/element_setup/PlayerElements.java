package com.cultivation_mod.element_setup;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.encoding.VarInts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record PlayerElements(Map<AxisElements, Integer> playerElements, List<AxisElements> favoredElements) {

    public static final Codec<PlayerElements> PLAYER_ELEMENTS_CODEC = RecordCodecBuilder.create(i -> i.group(
            AxisElements.ELEMENT_MAP_CODEC.fieldOf("playerElements").forGetter(PlayerElements::playerElements),
            AxisElements.AXIS_ELEMENTS_CODEC.listOf().fieldOf("favoredElements").forGetter(PlayerElements::favoredElements)
    ).apply(i, PlayerElements::new));

    public static final PacketCodec<ByteBuf,PlayerElements> PLAYER_ELEMENTS_PACKET_CODEC = new PacketCodec<ByteBuf, PlayerElements>() {
        @Override
        public PlayerElements decode(ByteBuf buf) {
            Map<AxisElements, Integer> elementLevels = new HashMap<AxisElements, Integer>();
            elementLevels.put(AxisElements.FIRE,buf.readInt());
            elementLevels.put(AxisElements.WATER,buf.readInt());
            elementLevels.put(AxisElements.AIR,buf.readInt());
            elementLevels.put(AxisElements.EARTH,buf.readInt());
            elementLevels.put(AxisElements.LIGHTNING,buf.readInt());
            elementLevels.put(AxisElements.YIN,buf.readInt());
            elementLevels.put(AxisElements.YANG,buf.readInt());
            List<AxisElements> favored = new ArrayList<>();
            int favoredSize = buf.readInt();
            for (int i = 0; i < favoredSize; i++) {
                favored.add(AxisElements.INDEX_TO_VALUE.apply(buf.readInt()));
            }
            return new PlayerElements(elementLevels,favored);
        }

        @Override
        public void encode(ByteBuf buf, PlayerElements value) {
            VarInts.write(buf,value.playerElements.get(AxisElements.FIRE));
            VarInts.write(buf,value.playerElements.get(AxisElements.WATER));
            VarInts.write(buf,value.playerElements.get(AxisElements.AIR));
            VarInts.write(buf,value.playerElements.get(AxisElements.EARTH));
            VarInts.write(buf,value.playerElements.get(AxisElements.LIGHTNING));
            VarInts.write(buf,value.playerElements.get(AxisElements.YIN));
            VarInts.write(buf,value.playerElements.get(AxisElements.YANG));
            VarInts.write(buf,value.favoredElements.size());
            value.favoredElements.forEach(axisElements -> VarInts.write(buf,axisElements.ordinal()));
        }
    };
}
