package com.cultivation_mod.technique_setup;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record Technique(String id, List<String> nameParts, int realm, int mastery, int cost, int power, int range, List<String> modifiers) {

    public static Map<String,RegisteredTechnique> registeredTechniques = new HashMap<>();

    public static final Codec<Technique> TECHNIQUE_CODEC = RecordCodecBuilder.create(i -> i.group(
            Codec.STRING.fieldOf("id").forGetter(Technique::id),
            Codec.STRING.listOf().fieldOf("nameParts").forGetter(Technique::nameParts),
            Codec.INT.fieldOf("realm").forGetter(Technique::realm),
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
            PacketCodecs.INTEGER,
            Technique::realm,
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

    public static Technique createRandomTechnique(ItemStack stack, LivingEntity user){
        ArrayList<String> techniqueIds = new ArrayList<>(registeredTechniques.keySet());
        String techniqueId = techniqueIds.get(user.getRandom().nextBetween(0, techniqueIds.size())-1);
        return new Technique(techniqueId,
                registeredTechniques.get(techniqueId).createNameParts(),
                user.getRandom().nextBetween(0,10), //make it close to their realm
                0,
                user.getRandom().nextBetween(5,20), //make ranges and powers and cost depend on player realm, item rarity
                user.getRandom().nextBetween(5,20),
                user.getRandom().nextBetween(5,20),
                registeredTechniques.get(techniqueId).createModifiers()
                );
    }

}
