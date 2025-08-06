package com.cultivation_mod.technique_setup;

import com.cultivation_mod.element_setup.CompoundElements;
import com.cultivation_mod.element_setup.PlayerElementAttachments;
import com.cultivation_mod.entity.projectiles.SwordSlashProjectile;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;

public class SwordSlashTechnique extends RegisteredTechnique{
    public List<String> nouns = List.of(
            "cultivation_mod.technique.name_parts.slash",
            "cultivation_mod.technique.name_parts.swipe",
            "cultivation_mod.technique.name_parts.strike"
            );

    public SwordSlashTechnique(String id) {
        super(id);
    }

    @Override
    public void activeEffect(PlayerEntity player, List<String> nameParts, int mastery, int cost, int power, int range, List<String> modifiers) {
        Vec3d velocity = player.getRotationVector(player.getPitch(), player.getYaw()).multiply(2);
        SwordSlashProjectile projectile = SwordSlashProjectile.createProjectile(player);
        projectile.setYaw(player.getYaw());
        projectile.setPitch(player.getPitch());
        projectile.setPos(player.getX(),player.getY()+1,player.getZ());
        projectile.setOwner(player);

        int color = this.getAxisElement() != null ? this.getAxisElement().getColor():this.getCompoundElement().getColor();
        projectile.setColor(color);

        int elementAffinity;
        if(this.getAxisElement() != null) {
            elementAffinity = PlayerElementAttachments.getElementLevel(player,this.getAxisElement());
        }
        else {
            int element1 = PlayerElementAttachments.getElementLevel(player, this.getCompoundElement().getElement1());
            int element2 = PlayerElementAttachments.getElementLevel(player, this.getCompoundElement().getElement2());
            elementAffinity = (element1 + element2) /2;
        }
        projectile.setDamage(power + elementAffinity/50);
        projectile.setCrit(player.getRandom().nextBetween(0,100)<=mastery/2);
        //what make range do?

        ProjectileEntity.spawnWithVelocity(projectile,(ServerWorld) player.getWorld(), ItemStack.EMPTY, velocity.x, velocity.y, velocity.z,1F,0.25F);
    }

    @Override
    public CompoundElements getCompoundElement() {
        return CompoundElements.METAL;
    }

    @Override
    public String getSlot() {
        return "martial";
    }

    @Override
    public String getDesc() {
        return "cultivation_mod.technique.sword_slash.desc";
    }

    @Override
    public List<String> createNameParts(LivingEntity entity) {
        List<String> nameParts = new ArrayList<>(3); //3? i dunno
        //add other parts here, ending on noun
        nameParts.add(nouns.get(entity.getRandom().nextBetween(0, nouns.size())-1));
        return nameParts;
    }

    @Override
    public List<String> createModifiers(LivingEntity entity) {
        List<String> modifiers = new ArrayList<>();
        // ????
        return modifiers;
    }

    @Override
    public String getModifierDesc(String modifier) {
        return super.getModifierDesc(modifier);
    }

}
