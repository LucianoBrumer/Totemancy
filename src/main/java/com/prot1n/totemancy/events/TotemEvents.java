package com.prot1n.totemancy.events;

import com.google.common.eventbus.Subscribe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.profiling.jfr.event.WorldLoadFinishedEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.GameRules;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class TotemEvents {

    public static void runCommand(IntegratedServer server, String command){
        var stack = server.createCommandSourceStack();
        server.getCommands().performPrefixedCommand(stack, command);
    }

    @SubscribeEvent
    public void worldLoad(LevelEvent.Load event) {
        var server = Minecraft.getInstance().getSingleplayerServer();
        runCommand(server, "gamerule sendCommandFeedback false");

    }

    public static void placeTotem(BlockEvent.EntityPlaceEvent event, String effect){
        var placedBlock = event.getPlacedBlock();

        if(placedBlock.toString().contains("oak_" + effect + "_totem")){
            var entity = event.getEntity();
            var server = entity.getServer();

            var pos = event.getPos();

            String command = "summon marker " + (int)(pos.getX()) + " " + (int)(pos.getY()) + " " + (int)(pos.getZ()) + "  {Tags:[\"totemancy_" + effect + "\"]}";
            runCommand((IntegratedServer) server, command);
        }
    }

    @SubscribeEvent
    public void placeBlock(BlockEvent.EntityPlaceEvent event){
        placeTotem(event, "wither");
        placeTotem(event, "poison");
        placeTotem(event, "fire_resistance");

    }

    public static void TotemTick(IntegratedServer server, String effect, int effectID, int distance, int duration){
        runCommand((IntegratedServer) server, "execute as @e[tag=totemancy_" + effect + "] at @s if block ~ ~1 ~ oak_log if block ~ ~-1 ~ oak_log if block ~ ~ ~1 oak_stairs[half=top] if block ~ ~ ~-1 oak_stairs[half=top] if entity @e[tag=totemancy_" + effect + ",distance=..5] run effect give @e[nbt=!{ActiveEffects:[{Id:" + effectID + "}]},distance=.." + distance + "] " + effect + " " + duration + " 0");
        runCommand((IntegratedServer) server, "execute as @e[tag=totemancy_" + effect + "] at @s if block ~ ~1 ~ oak_log if block ~ ~-1 ~ oak_log if block ~1 ~ ~ oak_stairs[half=top] if block ~-1 ~ ~ oak_stairs[half=top] if entity @e[tag=totemancy_" + effect + ",distance=..5] run effect give @e[nbt=!{ActiveEffects:[{Id:" + effectID + "}]},distance=.." + distance + "] " + effect + " " + duration + " 0");
        runCommand((IntegratedServer) server, "execute as @e[tag=totemancy_" + effect + "] at @s unless block ~ ~ ~ totemancy:oak_" + effect + "_totem run kill @s");

    }

    @SubscribeEvent
    public void worldTick(TickEvent.LevelTickEvent event){
        var server = Minecraft.getInstance().getSingleplayerServer();

        if(server != null){
            TotemTick(server, "wither", 20, 8, 5);
            TotemTick(server, "poison",19,8 , 5);
            TotemTick(server, "fire_resistance",12,8, 10);
        }
    }
}
