package com.cultivation_mod;

import com.mojang.datafixers.util.Function3;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.consume.ConsumeEffect;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public record CustomConsumeEffect(String effectKey) implements ConsumeEffect {
    public static final MapCodec<CustomConsumeEffect> codec = RecordCodecBuilder.mapCodec((instance) -> instance.group(
            Codec.STRING.fieldOf("effectKey").forGetter(CustomConsumeEffect::effectKey))
            .apply(instance, CustomConsumeEffect::new));
    public static final PacketCodec<RegistryByteBuf,CustomConsumeEffect> packetCodec = PacketCodec.tuple(
            PacketCodecs.STRING,CustomConsumeEffect::effectKey,
            CustomConsumeEffect::new);

    public static final ConsumeEffect.Type<CustomConsumeEffect> TYPE = Registry.register(Registries.CONSUME_EFFECT_TYPE,"custom_consume_effect", new Type<CustomConsumeEffect>(codec, packetCodec));

    public static Map<String,Function3<World,ItemStack,LivingEntity,Boolean>> consumeFunction = new HashMap<>();

    public static CustomConsumeEffect createConsumeEffect(String effectKey,Function3<World,ItemStack,LivingEntity,Boolean> consumeFunction){
        CustomConsumeEffect.consumeFunction.put(effectKey,consumeFunction);
        return new CustomConsumeEffect(effectKey);
    }

    @Override
    public Type<? extends ConsumeEffect> getType() {
        return TYPE;
    }

    @Override
    public boolean onConsume(World world, ItemStack stack, LivingEntity user) {
        return consumeFunction.get(effectKey).apply(world,stack,user);
    }
}
