package com.hz6826.pt4f;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

import java.util.concurrent.CompletableFuture;

public class DecoratorEventHandler {
    public static CompletableFuture<Text> decorate(PlayerEntity sender, Text message){
        MutableText title = getPlayerTitle(sender);
        Text decoratedMessage = title.append(message);
        return CompletableFuture.completedFuture(decoratedMessage);
    }

    public static MutableText getPlayerTitle(PlayerEntity player){  // format: uuid,titleText,color
        return PlayerTitleManager.getPlayerTitle(player.getUuid(), player.getName().getString()).copy();
    }
}
