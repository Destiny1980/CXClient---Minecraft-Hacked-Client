package de.chrissx.mods.movement;

import de.chrissx.mods.Mod;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Nofall extends Mod {

	public Nofall() {
		super("NoFall", "Tries to avoid fall damage");
	}

	@Override
	public void onTick() {
		if (player().fallDistance > 2)
			sendPacket(new C03PacketPlayer(true));
	}
}