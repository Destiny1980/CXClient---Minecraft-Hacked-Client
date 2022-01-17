package de.chrissx.mods.combat;

import java.io.File;

import de.chrissx.mods.Mod;
import de.chrissx.mods.options.FloatOption;

public class Reach extends Mod {

	FloatOption range = new FloatOption("range", "The range (6 in Vanilla)", 7);

	public Reach() {
		super("Reach", "reach", "Increases your range");
		addOption(range);
	}

	public float getReach() {
		return range.value;
	}
}
