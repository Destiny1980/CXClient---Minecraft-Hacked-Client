package de.chrissx.mods.combat;

import de.chrissx.mods.Mod;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;

public class FastBow extends Mod {

	public FastBow() {
		super("FastBow", "Shoots one arrow per tick when you right click while holding a bow");
	}

	@Override
	public void onTick() {
		EntityPlayerSP p = player();
		ItemStack is = inventory().getCurrentItem();
		if (settings().keyBindUseItem.isKeyDown() && p.onGround && p.getHealth() > 0 && is != null && is.stackSize > 0
		        && is.getItem() instanceof ItemBow) {
			click(false);
			for (int i = 0; i < 20; i++)
				sendPacket(new C03PacketPlayer(false));
			playerController().onStoppedUsingItem(p);
		}
	}
}
