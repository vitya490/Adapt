/*------------------------------------------------------------------------------
 -   Adapt is a Skill/Integration plugin  for Minecraft Bukkit Servers
 -   Copyright (c) 2022 Arcane Arts (Volmit Software)
 -
 -   This program is free software: you can redistribute it and/or modify
 -   it under the terms of the GNU General Public License as published by
 -   the Free Software Foundation, either version 3 of the License, or
 -   (at your option) any later version.
 -
 -   This program is distributed in the hope that it will be useful,
 -   but WITHOUT ANY WARRANTY; without even the implied warranty of
 -   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 -   GNU General Public License for more details.
 -
 -   You should have received a copy of the GNU General Public License
 -   along with this program.  If not, see <https://www.gnu.org/licenses/>.
 -----------------------------------------------------------------------------*/

package com.volmit.adapt.content.skill;

import com.volmit.adapt.Adapt;
import com.volmit.adapt.AdaptConfig;
import com.volmit.adapt.api.skill.SimpleSkill;
import com.volmit.adapt.content.adaptation.rift.*;
import com.volmit.adapt.util.C;
import com.volmit.adapt.util.M;
import lombok.NoArgsConstructor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.HashMap;
import java.util.Map;

public class SkillRift extends SimpleSkill<SkillRift.Config> {
    private final Map<Player, Long> lasttp = new HashMap<>();

    public SkillRift() {
        super("rift", Adapt.dLocalize("Skill", "Rift", "Icon"));
        registerConfiguration(Config.class);
        setDescription(Adapt.dLocalize("Skill", "Rift", "Description"));
        setDisplayName(Adapt.dLocalize("Skill", "Rift", "Name"));
        setColor(C.DARK_PURPLE);
        setInterval(1154);
        setIcon(Material.ENDER_EYE);
        registerAdaptation(new RiftResist());
        registerAdaptation(new RiftAccess());
        registerAdaptation(new RiftEnderchest());
        registerAdaptation(new RiftGate());
        registerAdaptation(new RiftBlink());
        registerAdaptation(new RiftDoor());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void on(PlayerTeleportEvent e) {
        if (e.isCancelled()) {
            return;
        }
        Player p = e.getPlayer();
        if (!AdaptConfig.get().isXpInCreative() && p.getGameMode().name().contains("CREATIVE")) {
            return;
        }
        if (!lasttp.containsKey(p)) {
            try {
                xpSilent(p, getConfig().teleportXP);
            } catch (Exception ignored) {
            }
            lasttp.put(p, M.ms());
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void on(ProjectileLaunchEvent e) {
        if (e.isCancelled()) {
            return;
        }
        if (e.getEntity() instanceof EnderPearl && e.getEntity().getShooter() instanceof Player p) {
            if (!AdaptConfig.get().isXpInCreative() && p.getGameMode().name().contains("CREATIVE")) {
                return;
            }
            xp(p, getConfig().throwEnderpearlXP);
        } else if (e.getEntity() instanceof EnderSignal && e.getEntity().getShooter() instanceof Player p) {
            if (!AdaptConfig.get().isXpInCreative() && p.getGameMode().name().contains("CREATIVE")) {
                return;
            }
            xp(p, getConfig().throwEnderEyeXP);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void on(EntityDamageByEntityEvent e) {
        if (e.isCancelled()) {
            return;
        }
        if (e.getEntity() instanceof Enderman && e.getDamager() instanceof Player p) {
            if (!AdaptConfig.get().isXpInCreative() && p.getGameMode().name().contains("CREATIVE")) {
                return;
            }
            xp(p, getConfig().damageEndermanXPMultiplier * Math.min(e.getDamage(), ((Enderman) e.getEntity()).getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue()));
        }
        if (e.getEntity() instanceof Endermite && e.getDamager() instanceof Player p) {
            if (!AdaptConfig.get().isXpInCreative() && p.getGameMode().name().contains("CREATIVE")) {
                return;
            }
            xp(p, getConfig().damageEndermiteXPMultiplier * Math.min(e.getDamage(), ((Endermite) e.getEntity()).getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue()));
        }
        if (e.getEntity() instanceof EnderDragon && e.getDamager() instanceof Player p) {
            if (!AdaptConfig.get().isXpInCreative() && p.getGameMode().name().contains("CREATIVE")) {
                return;
            }
            xp(p, getConfig().damageEnderdragonXPMultiplier * Math.min(e.getDamage(), ((EnderDragon) e.getEntity()).getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue()));
        }
        if (e.getEntity() instanceof EnderCrystal && e.getDamager() instanceof Player p) {
            if (!AdaptConfig.get().isXpInCreative() && p.getGameMode().name().contains("CREATIVE")) {
                return;
            }
            xp(p, getConfig().damageEndCrystalXP);
        }

        if (e.getEntity() instanceof Enderman && e.getDamager() instanceof Projectile j && j.getShooter() instanceof Player p) {
            if (!AdaptConfig.get().isXpInCreative() && p.getGameMode().name().contains("CREATIVE")) {
                return;
            }
            xp(p, getConfig().damageEndermanXPMultiplier * Math.min(e.getDamage(), ((Enderman) e.getEntity()).getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue()));
        }
        if (e.getEntity() instanceof Endermite && e.getDamager() instanceof Projectile j && j.getShooter() instanceof Player p) {
            if (!AdaptConfig.get().isXpInCreative() && p.getGameMode().name().contains("CREATIVE")) {
                return;
            }
            xp(p, getConfig().damageEndermiteXPMultiplier * Math.min(e.getDamage(), ((Endermite) e.getEntity()).getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue()));
        }
        if (e.getEntity() instanceof EnderDragon && e.getDamager() instanceof Projectile j && j.getShooter() instanceof Player p) {
            if (!AdaptConfig.get().isXpInCreative() && p.getGameMode().name().contains("CREATIVE")) {
                return;
            }
            xp(p, getConfig().damageEnderdragonXPMultiplier * Math.min(e.getDamage(), ((EnderDragon) e.getEntity()).getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue()));
        }
        if (e.getEntity() instanceof EnderCrystal && e.getDamager() instanceof Projectile j && j.getShooter() instanceof Player p) {
            if (!AdaptConfig.get().isXpInCreative() && p.getGameMode().name().contains("CREATIVE")) {
                return;
            }
            xp(p, getConfig().damageEndCrystalXP);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void on(EntityDeathEvent e) {
        if (e.getEntity() instanceof EnderCrystal && e.getEntity().getKiller() != null) {
            if (!AdaptConfig.get().isXpInCreative() && e.getEntity().getKiller().getGameMode().name().contains("CREATIVE")) {
                return;
            }
            xp(e.getEntity().getKiller(), getConfig().destroyEndCrystalXP);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void on(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        lasttp.remove(p);
    }

    @Override
    public void onTick() {
        for (Player i : lasttp.k()) {
            if (M.ms() - lasttp.get(i) > getConfig().teleportXPCooldown) {
                lasttp.remove(i);
            }
        }
    }

    @Override
    public boolean isEnabled() {
        return getConfig().enabled;
    }

    @NoArgsConstructor
    protected static class Config {
        boolean enabled = true;
        double destroyEndCrystalXP = 550;
        double damageEndCrystalXP = 110;
        double damageEndermanXPMultiplier = 4;
        double damageEndermiteXPMultiplier = 2;
        double damageEnderdragonXPMultiplier = 8;
        double throwEnderpearlXP = 105;
        double throwEnderEyeXP = 45;
        double teleportXP = 15;
        double teleportXPCooldown = 60000;
    }
}
