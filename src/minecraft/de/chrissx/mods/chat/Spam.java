package de.chrissx.mods.chat;

import org.apache.commons.lang3.RandomStringUtils;

import de.chrissx.mods.Mod;
import de.chrissx.util.Util;

// TODO: this whole thing has to be reworked a lot
public class Spam extends Mod {

	String[] last = new String[] { "#spam", "20", "50", "You're getting flooded by CXClient! ;)" };

	public Spam() {
		super("Spam", new String[] { "spam", "clearspam" });
	}

	@Override
	public void toggle() {
	}

	@Override
	public void processCommand(String[] args) {
		if (args.length < 4) {
			Util.sendMessage("\u00a74Not enough arguments, usage: " + args[0] + " <times> <delay> <message>");
			return;
		}

		final int times;
		final StringBuilder msg = new StringBuilder();
		final long delay;
		final boolean clear;

		clear = args[0].equalsIgnoreCase("#clearspam");

		try {
			times = Integer.parseInt(args[1]);
		} catch (Exception e) {
			Util.sendMessage("\u00a74Error parsing times.");
			return;
		}

		try {
			delay = Long.parseLong(args[2]);
		} catch (Exception e) {
			Util.sendMessage("\u00a74Error parsing delay.");
			return;
		}

		msg.append(args[3]);
		for (int i = 4; i < args.length; i++)
			msg.append(" " + args[i]);

		new Thread(new Runnable() {
			@Override
			public void run() {
				enabled = true;
				String last = "-1";
				final String m = msg.toString();
				for (int i = 0; i < times; i++) {
					if (clear)
						Util.sendChat(m);
					else {
						String r = last;
						while (last == r)
							r = RandomStringUtils.randomAlphanumeric(2);
						Util.sendChat(m + " #" + r);
						last = r;
					}
					try {
						Thread.sleep(delay);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				enabled = false;
			}
		}).start();

		last = args;
	}

	@Override
	public void onHotkey() {
		processCommand(last);
	}
}