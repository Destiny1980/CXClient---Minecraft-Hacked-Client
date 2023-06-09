package de.chrissx.mods.combat;

import de.chrissx.mods.Mod;
import de.chrissx.mods.options.IntOption;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;

public class FastEat extends Mod {

	IntOption speed = new IntOption("speed", "Packets per tick", 100);

	public FastEat() {
		super("FastEat", "Allows you to eat faster");
		addOption(speed);
	}

	@Override
	public void onTick() {
		EntityPlayerSP p = player();
		ItemStack is = p.getHeldItem();
		if (p.getHealth() > 0 && p.onGround && settings().keyBindUseItem.isKeyDown() && p.getFoodStats().needFood()
		        && is != null && is.getItem() instanceof ItemFood)
			for (int i = 0; i < speed.value; i++)
				sendPacket(new C03PacketPlayer(false));
	}
}
