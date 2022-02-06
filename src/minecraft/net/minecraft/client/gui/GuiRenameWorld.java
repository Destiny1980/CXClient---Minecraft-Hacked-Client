package net.minecraft.client.gui;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import de.chrissx.HackedClient;

// TODO: rework and rename all of this
public class GuiRenameWorld extends GuiScreen
{
    GuiScreen parentScreen;
    GuiTextField field_146583_f;

    public GuiRenameWorld(GuiScreen parentScreenIn)
    {
        this.parentScreen = parentScreenIn;
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        this.field_146583_f.updateCursorCounter();
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui()
    {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + 12, "Process alt-command"));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + 12, "Close"));
        mc.getSaveLoader();
        this.field_146583_f = new GuiTextField(2, this.fontRendererObj, this.width / 2 - 100, 60, 200, 20);
        this.field_146583_f.setFocused(true);
        this.field_146583_f.setText("");
    }

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    protected void actionPerformed(GuiButton button) throws IOException
    {
        if (button.enabled)
        {
            if (button.id == 1)
                this.mc.displayGuiScreen(this.parentScreen);
            else if (button.id == 0)
            	HackedClient.getClient().guiRenameWorld(field_146583_f.getText(), this);
        }
    }

    /**
     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     */
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        this.field_146583_f.textboxKeyTyped(typedChar, keyCode);
        ((GuiButton)this.buttonList.get(0)).enabled = this.field_146583_f.getText().length() > 0;

        if (keyCode == 28 || keyCode == 156)
        {
            this.actionPerformed((GuiButton)this.buttonList.get(0));
        }
    }

    /**
     * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.field_146583_f.mouseClicked(mouseX, mouseY, mouseButton);
    }

    /**
     * Draws the screen and all the components in it. Args : mouseX, mouseY, renderPartialTicks
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        drawDefaultBackground();
        drawCenteredString(this.fontRendererObj, "Alt-Manager", width / 2, 20, 16777215);
        drawString(this.fontRendererObj, "Enter command", width / 2 - 100, 47, 10526880);
        field_146583_f.drawTextBox();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

	public void setText(String string) {
		field_146583_f.setText(string);
	}
	
	public String getText() {
		return field_146583_f.getText();
	}
}
