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

public class Technique {
    public final String id;
    public final List<String> nameParts;
    public final int realm;
    public final int mastery;
    public final int cost;
    public final int power;
    public final int range;
    public final List<String> modifiers;

    public Technique(String id, List<String> nameParts, int realm, int mastery, int cost, int power, int range, List<String> modifiers) {
        this.id = id;
        this.nameParts = nameParts;
        this.realm = realm;
        this.mastery = mastery;
        this.cost = cost;
        this.power = power;
        this.range = range;
        this.modifiers = modifiers;
    }

    public static Map<String,RegisteredTechnique> registeredTechniques = new HashMap<>();

    public static final Codec<Technique> TECHNIQUE_CODEC = RecordCodecBuilder.create(i -> i.group(
            Codec.STRING.fieldOf("id").forGetter((technique) -> technique.id),
            Codec.STRING.listOf().fieldOf("nameParts").forGetter((technique) -> technique.nameParts),
            Codec.INT.fieldOf("realm").forGetter((technique) -> technique.realm),
            Codec.INT.fieldOf("mastery").forGetter((technique) -> technique.mastery),
            Codec.INT.fieldOf("cost").forGetter((technique) -> technique.cost),
            Codec.INT.fieldOf("power").forGetter((technique) -> technique.power),
            Codec.INT.fieldOf("range").forGetter((technique) -> technique.range),
            Codec.STRING.listOf().fieldOf("modifiers").forGetter((technique) -> technique.modifiers)
    ).apply(i, Technique::new));

    public static final PacketCodec<ByteBuf, Technique> TECHNIQUE_PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.STRING, (technique) -> technique.id,
            PacketCodecs.STRING.collect(PacketCodecs.toList()), (technique) -> technique.nameParts,
            PacketCodecs.INTEGER, (technique) -> technique.realm,
            PacketCodecs.INTEGER, (technique) -> technique.mastery,
            PacketCodecs.INTEGER, (technique) -> technique.cost,
            PacketCodecs.INTEGER, (technique) -> technique.power,
            PacketCodecs.INTEGER, (technique) -> technique.range,
            PacketCodecs.STRING.collect(PacketCodecs.toList()), (technique) -> technique.modifiers,
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
                registeredTechniques.get(techniqueId).createNameParts(user),
                user.getRandom().nextBetween(0,10), //make it close to their realm
                0,
                user.getRandom().nextBetween(5,20), //make ranges and powers and cost depend on player realm, item rarity
                user.getRandom().nextBetween(5,20),
                user.getRandom().nextBetween(5,20),
                registeredTechniques.get(techniqueId).createModifiers(user)
                );
    }

}
