// FIXME: THIS IS CLEARLY NOT DONE YET

package de.chrissx.mods.building;

import java.awt.Color;
import java.awt.image.BufferedImage;

import de.chrissx.mods.CommandExecutor;
import de.chrissx.mods.RenderedObject;
import de.chrissx.mods.StopListener;
import de.chrissx.mods.TickListener;
import de.chrissx.util.Util;
import net.minecraft.client.gui.FontRenderer;

public class MasterBuildersBot implements CommandExecutor, TickListener, StopListener, RenderedObject {

	boolean enabled = false;
	BufferedImage drawAfter;

	@Override
	public void onRender(FontRenderer r, int x, int y) {
		r.drawString(getRenderstring(), x, y, Color.WHITE.getRGB());
	}

	@Override
	public void processCommand(String[] args) {
		if (args.length < 1)
			Util.sendError("Usage: masterbuildersbot <name of the theme>");
		else {
			try {
				drawAfter = Util.scale(new BufferedImage(0, 0, 0), 33, 33); // FIRST GOOGLE IMAGE

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public String getName() {
		return "MasterBuildersBot";
	}

	@Override
	public void onTick() {
	}

	@Override
	public void onStop() {
	}

	@Override
	public String getRenderstring() {
		return getName();
	}

	@Override
	public String getArgv0() {
		return "masterbuildersbot";
	}

	public boolean isEnabled() {
		return enabled;
	}
}
