package de.chrissx.util;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import de.chrissx.HackedClient;
import de.chrissx.hotkeys.Hotkey;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C07PacketPlayerDigging.Action;
import net.minecraft.network.play.client.C10PacketCreativeInventoryAction;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class Util {

	static Minecraft mc;
	static HackedClient hc;

	public static void init() {
		mc = Minecraft.getMinecraft();
		hc = HackedClient.getClient();
	}

	public static void removeHotkeyFromList(List<Hotkey> hotkeys, int key) {
		for (int i = 0; i < hotkeys.size(); i++) {
			if (hotkeys.get(i).key == key) {
				hotkeys.remove(i);
				removeHotkeyFromList(hotkeys, key);
				return;
			}
		}
	}

	public static void info(String s) {
		Minecraft.logger.info(s);
	}

	public static void warn(String s) {
		Minecraft.logger.warn(s);
	}

	public static void fatal(String s) {
		Minecraft.logger.fatal(s);
	}

	public static void checkIfExistsAndMake(String dir, String name) {
		File f = new File(dir);
		if (!f.exists()) {
			f.mkdirs();
			info("Made " + name + ".");
		}
	}

	// TODO: lets grep for usages of `for` to achieve the same thing
	public static String combineParts(String[] strings, int startIndex, String seperator) {
		if (startIndex >= strings.length)
			return "";
		StringBuilder sb = new StringBuilder();
		sb.append(strings[startIndex]);
		for (int i = startIndex + 1; i < strings.length; i++) {
			sb.append(seperator);
			sb.append(strings[i]);
		}
		return sb.toString();
	}

	public static BufferedImage scale(BufferedImage src, int w, int h) {
		BufferedImage i = new BufferedImage(w, h, 1); // type_int_rgb
		for (int x = 0; x < w; x++)
			for (int y = 0; y < h; y++)
				i.setRGB(x, y, src.getRGB(x * src.getWidth() / w, y * src.getHeight() / h));
		return i;
	}

	/**
	 * Splits the string without trying something with regexes but just a single
	 * character separator.
	 *
	 * @param s         The string to split
	 * @param separator The separator at which the string should be splitted.
	 * @return The splitted string (as an array).
	 */
	public static String[] split(String s, char separator) {
		int i = 0;
		char[] chars = s.toCharArray();
		for (char c : chars)
			if (c == separator)
				i++;
		String[] split = new String[i];
		int j = 0;
		int l = 0;
		for (int k = 0; k < s.length(); k++)
			if (chars[k] == separator) {
				split[j++] = s.substring(l, k);
				l = k + 1;
			}
		return split;
	}

	public static void swapSlots(int slot1, int slot2, int windowId) {
		mc.playerController.windowClick(windowId, slot1, 0, 1, mc.thePlayer);
		mc.playerController.windowClick(windowId, slot2, 0, 1, mc.thePlayer);
	}

	/**
	 * Sends the packet to break the block instantly.
	 *
	 * @param block The block to break.
	 */
	public static void breakBlock(BlockPos block) {
		mc.thePlayer.sendQueue
		.addToSendQueue(new C07PacketPlayerDigging(Action.START_DESTROY_BLOCK, block, EnumFacing.UP));
		mc.thePlayer.sendQueue
		.addToSendQueue(new C07PacketPlayerDigging(Action.STOP_DESTROY_BLOCK, block, EnumFacing.UP));
	}

	public static void downloadFile(final String url, final String file) throws IOException {
		final BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
		final FileOutputStream out = new FileOutputStream(file);

		final byte[] bfr = new byte[4096];
		int count = 0;

		while ((count = in.read(bfr, 0, 4096)) != -1)
			out.write(bfr, 0, count);

		out.close();
		in.close();
	}

	public static void cheatArmorStand(String msg, double x, double y, double z, int slot) {
		ItemStack itm = new ItemStack(Items.armor_stand);

		NBTTagCompound base = new NBTTagCompound();
		NBTTagCompound entityTag = new NBTTagCompound();

		entityTag.setInteger("Invisible", 1);
		entityTag.setString("CustomName", msg);
		entityTag.setInteger("CustomNameVisible", 1);
		entityTag.setInteger("NoGravity", 1);

		NBTTagList position = new NBTTagList();

		position.appendTag(new NBTTagDouble(x));
		position.appendTag(new NBTTagDouble(y));
		position.appendTag(new NBTTagDouble(z));

		entityTag.setTag("Pos", position);

		base.setTag("EntityTag", entityTag);
		itm.setTagCompound(base);

		itm.setStackDisplayName(msg);

		cheatItem(itm, slot);
	}

	public static void cheatCmdBlock(String command) {
		ItemStack itm = new ItemStack(Blocks.command_block);

		NBTTagCompound base = new NBTTagCompound();
		NBTTagCompound entityTag = new NBTTagCompound();
		NBTTagCompound display = new NBTTagCompound();
		NBTTagList lore = new NBTTagList();

		entityTag.setString("Command", "gamerule commandBlockOutput false");
		entityTag.setString("CustomName", Consts.clientName);
		entityTag.setBoolean("TrackOutput", false);

		lore.appendTag(new NBTTagString("gamerule commandBlockOutput false"));
		lore.appendTag(new NBTTagString("created using " + Consts.clientName));
		lore.appendTag(new NBTTagString("by chrissx"));

		display.setTag("Lore", lore);
		base.setTag("BlockEntityTag", entityTag);
		base.setTag("display", display);

		itm.setTagCompound(base);
		itm.setStackDisplayName("output disabler");

		cheatItem(itm, 36);

		cheatCmd(command);

		cheatLever();
	}

	static void cheatCmd(String cmd) {
		ItemStack itm = new ItemStack(Blocks.command_block);

		NBTTagCompound base = new NBTTagCompound();
		NBTTagCompound entityTag = new NBTTagCompound();
		NBTTagCompound display = new NBTTagCompound();
		NBTTagList lore = new NBTTagList();

		entityTag.setString("Command", cmd);
		entityTag.setString("CustomName", Consts.clientName);
		entityTag.setBoolean("TrackOutput", false);

		lore.appendTag(new NBTTagString(cmd));
		lore.appendTag(new NBTTagString("created using " + Consts.clientName));
		lore.appendTag(new NBTTagString("by chrissx"));

		display.setTag("Lore", lore);
		base.setTag("BlockEntityTag", entityTag);
		base.setTag("display", display);

		itm.setTagCompound(base);
		itm.setStackDisplayName(Consts.clientName);

		cheatItem(itm, 37);
	}

	static void cheatLever() {
		ItemStack itm = new ItemStack(Blocks.lever);

		NBTTagCompound base = new NBTTagCompound();
		NBTTagCompound display = new NBTTagCompound();
		NBTTagList lore = new NBTTagList();

		lore.appendTag(new NBTTagString("created using " + Consts.clientName));
		lore.appendTag(new NBTTagString("by chrissx"));

		display.setTag("Lore", lore);
		base.setTag("display", display);

		itm.setTagCompound(base);
		itm.setStackDisplayName("use this to enable the blocks");

		cheatItem(itm, 38);
	}

	public static NBTTagList newEffects() {
		return new NBTTagList();
	}

	public static NBTTagList addEffect(NBTTagList effects, int effect, int amplifier, int duration) {
		NBTTagCompound eff = new NBTTagCompound();
		eff.setInteger("Amplifier", amplifier);
		eff.setInteger("Duration", duration);
		eff.setInteger("Id", effect);
		effects.appendTag(eff);
		return effects;
	}

	public static ItemStack getCustomPotion(NBTTagList effects, String name) {
		ItemStack i = new ItemStack(Items.potionitem);
		i.setItemDamage(16384);

		i.setTagInfo("CustomPotionEffects", effects);
		i.setStackDisplayName(name);

		return i;
	}

	public static void cheatItem(ItemStack itm, int slot) {
		mc.thePlayer.sendQueue.addToSendQueue(new C10PacketCreativeInventoryAction(slot, itm));
	}

	/**
	 * Sends msg to the server as if it was typed into the ingame chat.
	 */
	public static void sendChat(String msg) {
		mc.thePlayer.sendChatMessage(msg);
	}

	/**
	 * Sends the player a msg by showing it to them in the chat. (not sent anywhere
	 * at all)
	 */
	public static void sendMessage(String msg) {
		hc.sendMessage.accept(msg);
	}

	/**
	 * Shows the player a msg in the chat, with the color code for red prepended.
	 */
	public static void sendError(String msg) {
		sendMessage("\u00a74" + msg);
	}

	public static boolean isWater(Block b) {
		int id = Block.getIdFromBlock(b);
		return id == 8 || id == 9;
	}

	public static void faceBlock(BlockPos p) {
		faceCoords(p.getX(), p.getY(), p.getZ(), p.getX() + 1, p.getY() + 1, p.getZ() + 1);
	}

	public static void faceBounds(AxisAlignedBB box) {
		faceCoords(box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ);
	}

	public static void faceCoords(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
		applyRotations(getRotationsNeeded(minX, minY, minZ, maxX, maxY, maxZ));
	}

	static void applyRotations(float[] rotations) {
		mc.thePlayer.rotationYaw = rotations[0];
		mc.thePlayer.rotationPitch = rotations[1] + 8.1f;
	}

	static float[] getRotationsNeeded(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
		double x = Random.randDouble(minX, maxX) - mc.thePlayer.posX;
		double y = Random.randDouble(minY, maxY) - (mc.thePlayer.posY + mc.thePlayer.getEyeHeight());
		double z = Random.randDouble(minZ, maxZ) - mc.thePlayer.posZ;
		return new float[] {
		           mc.thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float(
		               ((float) (Math.atan2(z, x) * 180 / Math.PI) - 90) - mc.thePlayer.rotationYaw),
		           mc.thePlayer.rotationPitch + MathHelper
		           .wrapAngleTo180_float(((float) -(Math.atan2(y, MathHelper.sqrt(x * x + z * z)) * 180 / Math.PI))
		                                 - mc.thePlayer.rotationPitch)
		       };
	}

	/**
	 * TODO dot hit ihtit nit nt n
	 * @param e   the entity
	 * @param r   the range around e to search in
	 * @param mbv whether the block must be visible
	 * @return
	 */
	public static BlockPos[] getBlocksAround(Entity e, double r, boolean mbv) {
		final double px = e.posX;
		final double py = e.posY;
		final double pz = e.posZ;
		final long ex = (long) (px + r);
		final long ey = (long) (py + r);
		final long ez = (long) (pz + r);
		final long sx = (long) (px - r);
		final long sy = (long) (py - r);
		final long sz = (long) (pz - r);
		List<BlockPos> b = new ArrayList<BlockPos>();
		for (long x = sx; x < ex; x++)
			for (long y = sy; y < ey; y++)
				for (long z = sz; z < ez; z++) {
					BlockPos pos = new BlockPos(x, y, z);
					if (!mc.theWorld.isAirBlock(pos) && (!mbv || isBlockVisible(pos)))
						b.add(pos);
				}
		return b.toArray(new BlockPos[b.size()]);
	}

	static boolean isBlockVisible(BlockPos pos) {
		World w = mc.theWorld;
		EntityPlayerSP p = mc.thePlayer;
		int i = pos.getX();
		int j = pos.getY();
		int k = pos.getZ();
		double d = p.posX;
		double e = p.posY + p.getEyeHeight();
		double f = p.posZ;
		boolean b = w.isAirBlock(new BlockPos(i - 1, j, k));
		boolean c = w.isAirBlock(new BlockPos(i + 1, j, k));
		boolean g = w.isAirBlock(new BlockPos(i, j, k - 1));
		boolean h = w.isAirBlock(new BlockPos(i, j, k + 1));
		boolean l = w.isAirBlock(new BlockPos(i, j + 1, k));
		boolean m = w.isAirBlock(new BlockPos(i, j - 1, k));
		if ((l && e > j) || (m && e < j) || (b && d < i) || (c && d > i) || (g && f < k) || (h && f > k))
			return true;
		else
			return false;
	}

	static float[] getNeededRotations(Vec3 vec) {
		double x = vec.xCoord - mc.thePlayer.posX;
		double y = vec.yCoord - mc.thePlayer.posY - mc.thePlayer.getEyeHeight();
		double z = vec.zCoord - mc.thePlayer.posZ;

		return new float[] { MathHelper.wrapAngleTo180_float((float) Math.toDegrees(Math.atan2(z, x)) - 90),
		                     MathHelper.wrapAngleTo180_float((float) -Math.toDegrees(Math.atan2(y, Math.sqrt(x * x + z * z))))
		                   };
	}

	public static <T> int firstHotbarIndex(Class<T> item, ItemStack[] inventory) {
		for (int i = 27; i < 36; i++)
			if (item.isAssignableFrom(inventory[i].getItem().getClass()))
				return i - 27;
		return -1;
	}

	public static String enc64(byte[] b) {
		return Base64.getEncoder().encodeToString(b);
	}

	public static byte[] dec64(String s) {
		return Base64.getDecoder().decode(s);
	}

	public static String chatFilter(String s) {
		StringBuilder b = new StringBuilder();
		for (char c : s.toCharArray())
			if (ChatAllowedCharacters.isAllowedCharacter(c))
				b.append(c);
		return b.toString();
	}

	public static int getKeyId(String key) {
		int keyId = Keyboard.getKeyIndex(key);
		if (keyId == Keyboard.KEY_NONE)
			keyId = Keyboard.getKeyIndex(key.toUpperCase());
		if (keyId == Keyboard.KEY_NONE)
			keyId = Keyboard.getKeyIndex(key.toLowerCase());
		return keyId;
	}

	public static void drawLine(Vec3 s, Vec3 e, Entity p) {
		// System.out.println("drawing line from {" + s.x + ", " + s.y + ", " + s.z + "}
		// to {" + e.x + ", " + e.y + ", " + e.z + "}.");
		GL11.glBlendFunc(770, 771);
		GL11.glLineWidth(2.0F);
		GL11.glPushMatrix();
		GL11.glTranslated(-mc.getRenderManager().viewerPosX, -mc.getRenderManager().viewerPosY,
		                  -mc.getRenderManager().viewerPosZ);
		double f = Math.cos(-mc.thePlayer.rotationYaw * 0.017453292 - Math.PI);
		double f1 = Math.sin(-mc.thePlayer.rotationYaw * 0.017453292 - Math.PI);
		double f2 = -Math.cos(-mc.thePlayer.rotationPitch * 0.017453292);
		double f3 = Math.sin(-mc.thePlayer.rotationPitch * 0.017453292);
		double startX = (f1 * f2) + mc.getRenderManager().viewerPosX;
		double startY = f3 + mc.thePlayer.getEyeHeight() + mc.getRenderManager().viewerPosY;
		double startZ = (f * f2) + mc.getRenderManager().viewerPosZ;
		GL11.glBegin(GL11.GL_LINES);
		AxisAlignedBB bb = p.getEntityBoundingBox();
		double endX = bb.minX + (bb.maxX - bb.minX) / 2 - p.posX - p.prevPosX;
		double endY = bb.minY + (bb.maxY - bb.minY) / 2 - p.posY - p.prevPosY;
		double endZ = bb.minZ + (bb.maxZ - bb.minZ) / 2 - p.posZ - p.prevPosZ;
		GL11.glColor4f(2.0F, 1.0F, 0.0F, 0.5F);
		GL11.glVertex3d(startX, startY, startZ);
		GL11.glVertex3d(endX, endY, endZ);
		GL11.glEnd();
		GL11.glPopMatrix();
	}

	public static <T> int indexOf(T[] array, T element) {
		for(int i = 0; i < array.length; i++)
			if(array[i] == element)
				return i;
		return -1;
	}
}
