package com.cultivation_mod.packet_payloads;

import com.cultivation_mod.CultivationMod;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record CultScreenPayload(String pattern) implements CustomPayload {
    public static final Identifier CULT_SCREEN_PACKET_ID = Identifier.of(CultivationMod.MOD_ID, "cult_screen");
    public static final CultScreenPayload.Id<CultScreenPayload> ID = new CustomPayload.Id<>(CULT_SCREEN_PACKET_ID);
    public static final PacketCodec<RegistryByteBuf, CultScreenPayload> CULT_SCREEN_CODEC = PacketCodec.tuple(
            PacketCodecs.STRING, CultScreenPayload::pattern,
            CultScreenPayload::new);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}