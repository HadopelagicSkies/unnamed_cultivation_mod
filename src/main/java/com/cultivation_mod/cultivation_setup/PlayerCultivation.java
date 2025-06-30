package com.cultivation_mod.cultivation_setup;

import com.cultivation_mod.element_setup.PlayerElements;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.encoding.VarInts;

import java.util.HashMap;
import java.util.Map;

public record PlayerCultivation(int realm, int qi, Map<String, Integer> meridianProgress){

    public static final Codec<Map<String, Integer>> MERIDIAN_PROGRESS_CODEC = Codec.unboundedMap(Codec.STRING,Codec.INT);

    public static final Codec<PlayerCultivation> PLAYER_CULTIVATION_CODEC = RecordCodecBuilder.create(i -> i.group(
            Codec.INT.fieldOf("realm").forGetter(PlayerCultivation::realm),
            Codec.INT.fieldOf("qi").forGetter(PlayerCultivation::qi),
            MERIDIAN_PROGRESS_CODEC.fieldOf("meridianProgress").forGetter(PlayerCultivation::meridianProgress)
    ).apply(i, PlayerCultivation::new));

    public static final PacketCodec<ByteBuf,PlayerCultivation> PLAYER_CULTIVATION_PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER,
            PlayerCultivation::realm,
            PacketCodecs.INTEGER,
            PlayerCultivation::qi,
            PacketCodecs.map(HashMap::new,PacketCodecs.STRING,PacketCodecs.INTEGER,10),
            PlayerCultivation::meridianProgress,
            PlayerCultivation::new);
}
