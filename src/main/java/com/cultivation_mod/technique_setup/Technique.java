package com.cultivation_mod.technique_setup;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record Technique(String id, List<String> nameParts, String slot, int mastery, int cost, int power, int range, List<String> modifiers) {

    public static Map<String,RegisteredTechnique> registeredTechniques = new HashMap<>();

    public static final Codec<Technique> TECHNIQUE_CODEC = RecordCodecBuilder.create(i -> i.group(
            Codec.STRING.fieldOf("id").forGetter(Technique::id),
            Codec.STRING.listOf().fieldOf("nameParts").forGetter(Technique::nameParts),
            Codec.STRING.fieldOf("slot").forGetter(Technique::slot),
            Codec.INT.fieldOf("mastery").forGetter(Technique::mastery),
            Codec.INT.fieldOf("cost").forGetter(Technique::cost),
            Codec.INT.fieldOf("power").forGetter(Technique::power),
            Codec.INT.fieldOf("range").forGetter(Technique::range),
            Codec.STRING.listOf().fieldOf("modifiers").forGetter(Technique::modifiers)
    ).apply(i, Technique::new));

    public static final PacketCodec<ByteBuf, Technique> TECHNIQUE_PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.STRING,
            Technique::id,
            PacketCodecs.STRING.collect(PacketCodecs.toList()),
            Technique::nameParts,
            PacketCodecs.STRING,
            Technique::slot,
            PacketCodecs.INTEGER,
            Technique::mastery,
            PacketCodecs.INTEGER,
            Technique::cost,
            PacketCodecs.INTEGER,
            Technique::power,
            PacketCodecs.INTEGER,
            Technique::range,
            PacketCodecs.STRING.collect(PacketCodecs.toList()),
            Technique::modifiers,
            Technique::new);


    public void activeEffect(PlayerEntity player) {
        registeredTechniques.get(id).activeEffect(player,nameParts,mastery,cost,power,range,modifiers);
    }

    public void passiveEffect(PlayerEntity player) {
        registeredTechniques.get(id).passiveEffect(player,nameParts,mastery,cost,power,range,modifiers);
    }

    public void onTickEffect(PlayerEntity player) {
        registeredTechniques.get(id).onTickEffect(player,nameParts,mastery,cost,power,range,modifiers);
    }
}
