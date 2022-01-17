package de.chrissx.mods.fun;

import de.chrissx.mods.Semimod;
import de.chrissx.util.Util;

public class Throw extends Semimod {

	public Throw() {
		super("Throw", "throw", "Throws items, many, quickly");
	}

	long throwCount = 500;
	long delay = 1;

	@Override
	public void processCommand(String[] args) {
		if (args.length < 3 || args.length > 3)
			Util.sendError("throw <count> <delay>");
		else {
			try {
				throwCount = Long.parseLong(args[1]);
				delay = Long.parseLong(args[2]);
			} catch (Exception e) {
				Util.sendError("Error parsing longs.");
			}
			toggle();
		}
	}

	@Override
	public void toggle() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (long i = 1; i <= throwCount; i++) {
					try {
						Thread.sleep(delay);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					click(false);
					Util.sendMessage(i + "/" + throwCount + " thrown.");
				}
			}
		}).start();
	}
}