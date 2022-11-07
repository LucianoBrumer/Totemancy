package com.prot1n.totemancy.events;

import com.google.common.eventbus.Subscribe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.profiling.jfr.event.WorldLoadFinishedEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
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
        runCommand((IntegratedServer) server, "gamerule sendCommandFeedback false");

    }

    @SubscribeEvent
    public void placeBlock(BlockEvent.EntityPlaceEvent event){
        var placedBlock = event.getPlacedBlock();

        if(placedBlock.toString().contains("oak_wither_totem")){
            var entity = event.getEntity();
            var server = entity.getServer();

            var pos = event.getPos();

            String command = "summon marker " + (int)(pos.getX()) + " " + (int)(pos.getY()) + " " + (int)(pos.getZ()) + "  {Tags:[\"totemancy_wither\"]}";
            runCommand((IntegratedServer) server, command);
        }

    }
//    @SubscribeEvent
//    public void rightClickBlock(PlayerInteractEvent.RightClickBlock event){
//        var entity = event.getEntity();
//        var block = event.getUseBlock();
//        var item = event.getItemStack();
//
//        var server = entity.getServer();
//
//        if(server != null){
//            if(item.getItem().toString() == "oak_wither_totem"){
//                entity.addEffect(new MobEffectInstance(MobEffects.WITHER, 100, 0));
//
//                var pos = entity.position();
//                String command = "summon marker " + (int)(pos.x) + " " + (int)(pos.y) + " " + (int)(pos.z) + "  {Tags:[\"totemancy_wither\"]}";
//                runCommand((IntegratedServer) server, command);
//
//            }
//        }
//    }

    @SubscribeEvent
    public void playerTick(TickEvent.PlayerTickEvent event){
        var player = event.player;
        var server = player.getServer();
        if(server != null){
            var pos = player.position();

            runCommand((IntegratedServer) server, "execute as @e[tag=totemancy_wither] at @s if block ~ ~1 ~ oak_log if block ~ ~-1 ~ oak_log if block ~ ~ ~1 oak_stairs[half=top] if block ~ ~ ~-1 oak_stairs[half=top] if entity @e[tag=totemancy_wither,distance=..5] run effect give @e[nbt=!{ActiveEffects:[{Id:20}]},distance=..5] wither 5 0");
            runCommand((IntegratedServer) server, "execute as @e[tag=totemancy_wither] at @s if block ~ ~1 ~ oak_log if block ~ ~-1 ~ oak_log if block ~1 ~ ~ oak_stairs[half=top] if block ~-1 ~ ~ oak_stairs[half=top] if entity @e[tag=totemancy_wither,distance=..5] run effect give @e[nbt=!{ActiveEffects:[{Id:20}]},distance=..5] wither 5 0");
            runCommand((IntegratedServer) server, "execute as @e[tag=totemancy_wither] at @s unless block ~ ~ ~ totemancy:oak_wither_totem run kill @s");
        }
    }
}
