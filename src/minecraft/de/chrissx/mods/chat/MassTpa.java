package de.chrissx.mods.chat;

import java.util.ArrayList;
import java.util.List;

import de.chrissx.mods.Mod;
import de.chrissx.util.Random;
import de.chrissx.util.Util;
import net.minecraft.entity.player.EntityPlayer;

public class MassTpa extends Mod {

	List<Integer> tpaed = new ArrayList<Integer>();
	List<EntityPlayer> players;

	public MassTpa() {
		super("MassTPA", "masstpa", "Send a /tpa request to all players on the server");
	}

	@Override
	public void onTick() {
		// dont spam too much
		if (Random.rand(20) != 5)
			return;
		int i = Random.rand(players.size() - 1);
		for (int a = 0; a < 50; a++)
			if (tpaed.contains(i))
				i = Random.rand(players.size() - 1);
		if (tpaed.contains(i))
			tpaed = new ArrayList<Integer>();
		Util.sendChat("/tpa " + players.get(i).getName());
	}

	@Override
	public void toggle() {
		enabled = !enabled;
		tpaed = new ArrayList<Integer>();
		players = world().playerEntities;
		if (players.size() < 2)
			enabled = false;
	}
}