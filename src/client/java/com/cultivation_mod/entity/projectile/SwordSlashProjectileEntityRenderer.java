package com.cultivation_mod.entity.projectile;

import com.cultivation_mod.CultivationMod;
import com.cultivation_mod.CultivationModClient;
import com.cultivation_mod.entity.projectiles.SwordSlashProjectile;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

public class SwordSlashProjectileEntityRenderer extends EntityRenderer<SwordSlashProjectile, SwordSlashProjectileEntityRenderState> {
    private final SwordSlashProjectileEntityModel model;
    public SwordSlashProjectileEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.model = new SwordSlashProjectileEntityModel(context.getPart(CultivationModClient.SWORD_SLASH_PROJECTILE_MODEL_LAYER));
    }

    @Override
    public void render(SwordSlashProjectileEntityRenderState projectileEntityRenderState, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();
        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(projectileEntityRenderState.yaw - 90.0F));
        matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(projectileEntityRenderState.pitch));
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntityCutout(this.getTexture(projectileEntityRenderState)));
        this.model.setAngles(projectileEntityRenderState);
        this.model.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV);
        matrixStack.pop();
        super.render(projectileEntityRenderState, matrixStack, vertexConsumerProvider, i);
    }

    protected Identifier getTexture(SwordSlashProjectileEntityRenderState state) {
        return Identifier.of(CultivationMod.MOD_ID,"sword_slash_projectile");
    }

    @Override
    public SwordSlashProjectileEntityRenderState createRenderState() {
        return new SwordSlashProjectileEntityRenderState();
    }

    @Override
    public void updateRenderState(SwordSlashProjectile projectileEntity, SwordSlashProjectileEntityRenderState projectileEntityRenderState, float f) {
        super.updateRenderState(projectileEntity, projectileEntityRenderState, f);
        projectileEntityRenderState.color1 = projectileEntity.getColor();
        projectileEntityRenderState.pitch = projectileEntity.getLerpedPitch(f);
        projectileEntityRenderState.yaw = projectileEntity.getLerpedYaw(f);
    }
}
