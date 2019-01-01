package de.chrissx.util;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Consts {
	public static final String prefix = "\u00a7c[CXClient] \u00a7f";
	public static final int[] packetPlayerInventorySlots = new int[] {36, 37, 38, 39, 40, 41, 42, 43, 44,
			9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35};
	public static final int[] localPlayerInventorySlots = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11,
			12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35};
	public static final String dotMinecraftPath = new File("").getAbsoluteFile().getAbsolutePath().toString();
	public static final String configPath = Paths.get(dotMinecraftPath, "cxclient_config").toString();
	public static final String addonPath = Paths.get(dotMinecraftPath, "cxclient_addons").toString();
	public static final String eapiPath = Paths.get(dotMinecraftPath, "cxclient_eapi").toString();
	public static final String modsPath = Paths.get(eapiPath, "mods").toString();
	public static final String enabledPath = Paths.get(modsPath, "enabled").toString();
	public static final String togglePath = Paths.get(modsPath, "toggle").toString();
	public static final String runningFile = Paths.get(eapiPath, "running").toString();
	public static final String hotkeyFile = Paths.get(configPath, "hotkeys.cfg").toString();
	public static final String clientName = "CXClient";
	public static final String version = "alpha 3001";
	public static final String[] changelog = new String[] {
		clientName + " " + version + " Changelog:",
		"",
		"-Fixed hotkey saving once more"
	};

	public static final String[] credits = new String[] {
		clientName + " " + version + " Credits:",
		"",
		"Author: chrissx",
		"Released by: chrissx Media Inc.",
		"Licensed under: GNU GPLv3",
		"Official website (alpha): https://tinyurl.com/cxclhmpg",
		"Source branch: https://tinyurl.com/cxclmstr",
		"",
		"Thanks to:",
		"",
		"-Garkolym for showing a few exploits in his videos (for example #text)",
		"-A few other people we stealt the Fly- and Speed-Bypasses from",
		"-The developers of Wurst for making another open source client, we looked at, when we needed ideas for hacks or when we just f*ed up",
		"-Trace (german hacking youtuber, quit around 01/2018) for showing a few exploits in his videos: https://tinyurl.com/trcechnl"
	};

	public static final String help = "Commands: #text, #multitext, #killpotion, #automine, #spam, #clearspam, #kaboom, #twerk, #nocobweb, "
			+ "#timer, #spider, #speedac1, #speedlegit, #skinblink, #fastplace, #fastbreak, #throw, #tracer, #masstpa, #dolphin, "
			+ "#autoarmor, #say, #bedfucker, #aimbot, #fastbow, #stepjump, #autoswitch, #flip, #cmdblock, #nofall, #antipotion, "
			+ "#fullbright, #panic, #flyvanilla, #flyac1, #flyac2, #autowalk, #trollpotion, #givebypass, #reach, #regen, #fastfall, "
			+ "#xray, #fasthit, #autoclicker, #derp, #noswing, #phase, #nick, #authmecrack, #antiafk, #highjump, #give, #velocity, "
			+ "#sprint, #scaffoldwalk, #jetpack, #autosteal, #killaura, #tired, #parkour, #fasteat, #glide, #nuker, #antifire, #sneak, "
			+ "#norender, #rollhead, #autosoup, #autoleave, #dropinventory, #changelog, #credits, #bind, #unbind, #binds, #mods, #help";
}
