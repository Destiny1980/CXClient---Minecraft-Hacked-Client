package de.chrissx.mods.fun;

import de.chrissx.mods.Mod;

public class Twerk extends Mod {

	public Twerk() {
		super("Twerk", "twerk", "Makes you twerk");
	}

	@Override
	public void onTick() {
		settings().keyBindSneak.pressed = !settings().keyBindSneak.pressed;
	}
}
