package com.mrcrayfish.device.app.components;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.mrcrayfish.device.app.ApplicationSettings;
import com.mrcrayfish.device.gui.GuiButtonArrow;
import com.mrcrayfish.device.gui.GuiButtonArrow.Type;
import com.mrcrayfish.device.gui.GuiLaptop;
import com.mrcrayfish.device.util.GuiHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.ResourceLocation;

public class ApplicationBar extends Component
{
	public static final ResourceLocation APP_BAR_GUI = new ResourceLocation("cdm:textures/gui/application_bar.png");
	
	private static final List<Application> APPS = new ArrayList<Application>();
	private static Application settings = new ApplicationSettings();
	
	private GuiButton btnLeft;
	private GuiButton btnRight;

	@Override
	public void init(List<GuiButton> buttons, int posX, int posY)
	{
		btnLeft = new GuiButtonArrow(0, posX + 3, posY + 3, Type.LEFT);
		btnRight = new GuiButtonArrow(0, posX + 100, posY + 3, Type.RIGHT);
		buttons.add(btnLeft);
		buttons.add(btnRight);
	}
	
	@Override
	public void render(GuiLaptop gui, Minecraft mc, int x, int y, int mouseX, int mouseY)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.75F);
		GlStateManager.enableBlend();
		mc.getTextureManager().bindTexture(APP_BAR_GUI);
		gui.drawTexturedModalRect(x, y, 0, 0, 1, 18);
		GuiHelper.drawModalRectWithUV(x + 1, y, 1, 0, 331, 18, 1, 18);
		gui.drawTexturedModalRect(x + 332, y, 2, 0, 33, 18);
		GlStateManager.disableBlend();
		
		for(int i = 0; i < APPS.size(); i++)
		{
			gui.drawTexturedModalRect(x + 18 + i * 18, y + 2, 0, 46, 14, 14);
		}
		
		/* Settings App */
		gui.drawTexturedModalRect(x + 182, y + 2, 16, 30, 14, 14);
		if(isMouseInside(mouseX, mouseY, x + 181, y + 1, x + 197, y + 16))
		{
			gui.drawTexturedModalRect(x + 181, y + 1, 0, 30, 16, 16);
			gui.drawHoveringText(Arrays.asList(settings.getDisplayName()), mouseX, mouseY);
		}
		
		/* Other Apps */
		if(isMouseInside(mouseX, mouseY, x + 18, y + 1, x + 236, y + 16))
		{
			int appIndex = (mouseX - x - 1) / 16 - 1;
			if(appIndex <= 8 && appIndex < APPS.size())
			{
				gui.drawTexturedModalRect(x + appIndex * 16 + 17, y + 1, 0, 30, 16, 16);
				gui.drawHoveringText(Arrays.asList(APPS.get(appIndex).getDisplayName()), mouseX, mouseY);
			}
		}
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		RenderHelper.disableStandardItemLighting();
		
		mc.fontRendererObj.drawString(timeToString(mc.thePlayer.worldObj.getWorldTime()), x + 336, y + 5, Color.WHITE.getRGB(), true);
	}
	
	@Override
	public void handleClick(GuiLaptop gui, int x, int y, int mouseX, int mouseY, int mouseButton) 
	{
		System.out.println(mouseX + " " + mouseY);
		if(isMouseInside(mouseX, mouseY, x + 181, y + 1, x + 197, y + 16))
		{
			gui.openApplication(settings);
			return;
		}
		
		if(isMouseInside(mouseX, mouseY, x + 18, y + 1, x + 236, y + 16))
		{
			int appIndex = (mouseX - x - 1) / 16 - 1;
			if(appIndex < APPS.size())
			{
				gui.openApplication(APPS.get(appIndex));
				return;
			}
		}
	}
	
	public boolean isMouseInside(int mouseX, int mouseY, int x1, int y1, int x2, int y2)
	{
		return mouseX >= x1 && mouseX <= x2 && mouseY >= y1 && mouseY <= y2;
	}
	
	public static void registerApplication(Application app)
	{
		if(app != null)
		{
			APPS.add(app);
		}
	}
	
	public String timeToString(long time) 
	{
	    int hours = (int) ((Math.floor(time / 1000.0) + 7) % 24);
	    int minutes = (int) Math.floor((time % 1000) / 1000.0 * 60);
	    return String.format("%02d:%02d", hours, minutes);
	}
}
