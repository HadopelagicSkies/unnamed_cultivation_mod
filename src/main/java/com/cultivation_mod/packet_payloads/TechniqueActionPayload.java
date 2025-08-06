package com.cultivation_mod.packet_payloads;

import com.cultivation_mod.CultivationMod;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record TechniqueActionPayload(boolean isPressed, String keyPressed) implements CustomPayload {
    public static final Identifier TECH_ACTION_PACKET_ID = Identifier.of(CultivationMod.MOD_ID, "tech_action");
    public static final CustomPayload.Id<TechniqueActionPayload> ID = new CustomPayload.Id<>(TECH_ACTION_PACKET_ID);
    public static final PacketCodec<RegistryByteBuf, TechniqueActionPayload> TECH_ACTION_CODEC = PacketCodec.tuple(
            PacketCodecs.BOOLEAN, TechniqueActionPayload::isPressed,
            PacketCodecs.STRING, TechniqueActionPayload::keyPressed,
            TechniqueActionPayload::new);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

}


