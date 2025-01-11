package com.hz6826.pt4f;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

public class DecoratorEventHandler {
    public static Text decorate(PlayerEntity sender, Text message){
        MutableText title = getPlayerTitle(sender);
        return title.append(message);
    }

    public static MutableText getPlayerTitle(PlayerEntity player){  // format: uuid,titleText,color
        return PlayerTitleManager.getPlayerTitle(player.getUuid(), player.getName().getString()).copy();
    }
}
