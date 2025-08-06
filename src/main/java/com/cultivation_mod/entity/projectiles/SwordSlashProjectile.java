package com.cultivation_mod.entity.projectiles;

import com.cultivation_mod.CultivationModEntities;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ProjectileDeflection;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.network.packet.s2c.play.GameStateChangeS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class SwordSlashProjectile extends ProjectileEntity {
    private static final TrackedData<Integer> COLOR = DataTracker.registerData(SwordSlashProjectile.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> DAMAGE = DataTracker.registerData(SwordSlashProjectile.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> KNOCKBACK = DataTracker.registerData(SwordSlashProjectile.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> MAX_PIERCE = DataTracker.registerData(SwordSlashProjectile.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Boolean> CRIT = DataTracker.registerData(SwordSlashProjectile.class, TrackedDataHandlerRegistry.BOOLEAN);
    public SwordSlashProjectile(EntityType<SwordSlashProjectile> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        this.discard();
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        float f = (float)this.getVelocity().length();
        double d = this.getDamage();
        Entity entity2 = this.getOwner();
        DamageSource damageSource = this.getDamageSources().mobProjectile(this, (LivingEntity) entity2);
        if (this.getWeaponStack() != null) {
            World bl = this.getWorld();
            if (bl instanceof ServerWorld serverWorld) {
                d = EnchantmentHelper.getDamage(serverWorld, this.getWeaponStack(), entity, damageSource, (float)d);
            }
        }

        int i = MathHelper.ceil(MathHelper.clamp((double)f * d, 0.0F, Integer.MAX_VALUE));
        if (this.getMaxPierce() > 0) {
            this.setMaxPierce(getMaxPierce()-1);
        }

        if (this.getCrit()) {
            long l = this.random.nextInt(i / 2 + 2);
            i = (int)Math.min(l + (long)i, 2147483647L);
        }

        if (entity2 instanceof LivingEntity livingEntity) {
            livingEntity.onAttacking(entity);
        }

        boolean bl = entity.getType() == EntityType.ENDERMAN;
        int j = entity.getFireTicks();
        if (this.isOnFire() && !bl) {
            entity.setOnFireFor(5.0F);
        }

        if (entity.sidedDamage(damageSource, (float)i)) {
            if (bl) {
                return;
            }

            if (entity instanceof LivingEntity livingEntity2) {
                this.knockback(livingEntity2, damageSource);
                if (this.getWorld() instanceof ServerWorld serverWorld2) {
                    EnchantmentHelper.onTargetDamaged(serverWorld2, livingEntity2, damageSource, this.getWeaponStack());
                }

                this.onHit(livingEntity2);
                if (livingEntity2 != entity2 && livingEntity2 instanceof PlayerEntity && entity2 instanceof ServerPlayerEntity && !this.isSilent()) {
                    ((ServerPlayerEntity)entity2).networkHandler.sendPacket(new GameStateChangeS2CPacket(GameStateChangeS2CPacket.PROJECTILE_HIT_PLAYER, 0.0F));
                }
            }

            this.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
            if (this.getMaxPierce() <= 0) {
                this.discard();
            }
        } else {
            entity.setFireTicks(j);
            this.deflect(ProjectileDeflection.SIMPLE, entity, this.getOwner(), false);
            this.setVelocity(this.getVelocity().multiply(0.2));
            if (this.getWorld() instanceof ServerWorld) {
                if (this.getVelocity().lengthSquared() < 1.0E-7) {
                    this.discard();
                }
            }
        }
    }

    public void setColor(int color){
        this.dataTracker.set(COLOR,color);
    }
    public int getColor() {
        return this.dataTracker.get(COLOR);
    }

    public void setDamage(int damage){
        this.dataTracker.set(DAMAGE,damage);
    }
    public int getDamage() {
        return this.dataTracker.get(DAMAGE);
    }

    public void setKnockback(int knockback){
        this.dataTracker.set(KNOCKBACK,knockback);
    }
    public int getKnockback() {
        return this.dataTracker.get(KNOCKBACK);
    }

    public void setMaxPierce(int max_pierce){
        this.dataTracker.set(MAX_PIERCE,max_pierce);
    }
    public int getMaxPierce() {
        return this.dataTracker.get(MAX_PIERCE);
    }

    public void setCrit(boolean crit){
        this.dataTracker.set(CRIT,crit);
    }
    public boolean getCrit() {
        return this.dataTracker.get(CRIT);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        builder.add(COLOR, ColorHelper.getArgb(255,255,255));
        builder.add(DAMAGE, 2);
        builder.add(KNOCKBACK, 1);
        builder.add(MAX_PIERCE, 0);
        builder.add(CRIT, false);
    }

    public static SwordSlashProjectile createProjectile(LivingEntity shooter){
        return new SwordSlashProjectile(CultivationModEntities.SWORD_SLASH_PROJECTILE,shooter.getWorld());
    }

    public void onHit(LivingEntity entity){
        //if need do effects on hit, do here
    }

    public void knockback(LivingEntity target, DamageSource damageSource){
        double e = Math.max(0.0F, (double)1.0F - target.getAttributeValue(EntityAttributes.KNOCKBACK_RESISTANCE));
        Vec3d vec3d = this.getVelocity().multiply(1.0F, 0.0F, 1.0F).normalize().multiply(this.getKnockback() * 0.6 * e);
        if (vec3d.lengthSquared() > (double)0.0F) {
            target.addVelocity(vec3d.x, 0.1, vec3d.z);
        }
    }

    @Override
    public boolean hasNoGravity() {
        return true;
    }
}
