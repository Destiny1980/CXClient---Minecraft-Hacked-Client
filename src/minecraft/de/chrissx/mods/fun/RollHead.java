package de.chrissx.mods.fun;

import de.chrissx.mods.Mod;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.MathHelper;

public class RollHead extends Mod {

	public RollHead() {
		// TODO:
		super("RollHead", "rollhead", "i dont remember");
	}

	@Override
	public void onTick() {
		EntityPlayerSP p = player();
		float f = (float) (p.ticksExisted % 20 / 10F * Math.PI);
		sendPacket(new C03PacketPlayer.C05PacketPlayerLook(p.rotationYaw, MathHelper.sin(f) * 90, p.onGround));
	}
}
