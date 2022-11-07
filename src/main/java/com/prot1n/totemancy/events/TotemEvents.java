package com.prot1n.totemancy.events;

import net.minecraft.client.server.IntegratedServer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class TotemEvents {

    public static void runCommand(IntegratedServer server, String command){
        var stack = server.createCommandSourceStack();
        server.getCommands().performPrefixedCommand(stack, command);
    }

    @SubscribeEvent
    public void rightClickBlock(PlayerInteractEvent.RightClickBlock event){
        var entity = event.getEntity();
        var block = event.getUseBlock();
        var item = event.getItemStack();

        var server = entity.getServer();

        if(server != null){
            System.out.println(server.getClass());
            if(item.getItem().toString() == "oak_wither_totem"){
                entity.addEffect(new MobEffectInstance(MobEffects.WITHER, 100, 0));


                var pos = entity.position();
                String command = "summon marker " + (int)(pos.x) + " " + (int)(pos.y) + " " + (int)(pos.z) + "  {Tags:[\"totemancy_wither\"]}";
                runCommand((IntegratedServer) server, command);

            }
        }
    }

    @SubscribeEvent
    public void playerTick(TickEvent.PlayerTickEvent event){
        var player = event.player;
        var server = player.getServer();
        if(server != null){
            var pos = player.position();

            String command = "execute as @a[nbt=!{ActiveEffects:[{Id:20}]}] at @s if entity @e[tag=totemancy_wither,distance=..5] run effect give @s wither 5 0";
            runCommand((IntegratedServer) server, command);
        }
    }
}
