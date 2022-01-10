package de.chrissx.mods.fun;

import de.chrissx.mods.Semimod;
import de.chrissx.util.Consts;
import de.chrissx.util.Util;

public class MultiText extends Semimod {

	public MultiText() {
		super("MultiText", "multitext",
		      "Gives you multiple armor stands that will show a multi-line message when placed");
	}

	@Override
	public void processCommand(String[] args) {
		if (args.length < 2) {
			Util.sendMessage("\u00a74Not enough args.");
			return;
		} else {
			for (int i = 1; i < args.length; i++) {
				Util.cheatArmorStand(args[i], player().posX, player().posY + ((args.length - 2) * 0.3) - (i * 0.3),
				                     player().posZ, Consts.packetPlayerInventorySlots[i - 1]);
			}
		}
	}

	@Override
	public void onHotkey() {
		processCommand(new String[] { "#multitext", "CXClient is the best client in the world!",
		                              "Writing random text is easily possible because your server kinda sucks.",
		                              "Just fix that by installing Creative Item Control!"
		                            });
	}

	@Override
	public void toggle() {
	}
}
