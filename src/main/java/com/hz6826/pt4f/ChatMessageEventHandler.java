package com.hz6826.pt4f;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.message.MessageType;
import net.minecraft.network.message.SignedMessage;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

import java.util.Objects;

public class ChatMessageEventHandler {
    public static boolean decorate(SignedMessage message, ServerPlayerEntity sender,  MessageType.Parameters params){
        try {
            if (params.targetName().isPresent()) return true;
            Objects.requireNonNull(sender.getServer()).getPlayerManager().broadcast(getPlayerTextWithTitle(sender).append(message.getContent()), false);
            return false;
        } catch (Exception e) {
            PlayerTitleForFabric.LOGGER.error(Config.prefix + e.getMessage(), e);
            return true;
        }
    }

    public static MutableText getPlayerTextWithTitle(PlayerEntity player){
        return getPlayerTitle(player).append(Text.literal("<")).append(player.getName()).append(Text.literal("> "));
    }

    public static MutableText getPlayerTitle(PlayerEntity player){  // format: uuid,titleText
        return PlayerTitleManager.getPlayerTitle(player.getUuid(), player.getName().getString()).copy();
    }
}
