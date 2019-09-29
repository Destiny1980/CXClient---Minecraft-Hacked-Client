package de.chrissx.mods.movement;

import org.lwjgl.input.Keyboard;

import de.chrissx.mods.Mod;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.KeyBinding;

public class InventoryWalk extends Mod {

	public InventoryWalk() {
		super("InventoryWalk");
	}

	//The inspiration for this is from the XIV client:
	//https://gitlab.com/Apteryx/XIV/blob/ae6f113fe29a0a62e6f9a9e0afc3720ef68503a5/src/main/java/pw/latematt/xiv/mod/mods/InventoryWalk.java
	@Override
	public void onTick()
	{
		if (mc.currentScreen == null || mc.currentScreen instanceof GuiChat) return;

        KeyBinding[] movebinds = {mc.gameSettings.keyBindForward, mc.gameSettings.keyBindBack, mc.gameSettings.keyBindLeft, mc.gameSettings.keyBindRight, mc.gameSettings.keyBindJump};

        for (KeyBinding kb : movebinds)
            KeyBinding.setKeyBindState(kb.getKeyCode(), Keyboard.isKeyDown(kb.getKeyCode()));

        if (Keyboard.isKeyDown(Keyboard.KEY_UP))
            mc.thePlayer.rotationPitch--;

        if (Keyboard.isKeyDown(Keyboard.KEY_DOWN))
            mc.thePlayer.rotationPitch++;

        if (Keyboard.isKeyDown(Keyboard.KEY_LEFT))
            mc.thePlayer.rotationYaw--;

        if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
            mc.thePlayer.rotationYaw++;
	}

}