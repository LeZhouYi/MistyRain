package skily_leyu.mistyrain.client.gui;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderTooltipEvent.Color;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import skily_leyu.mistyrain.MistyRain;
import skily_leyu.mistyrain.config.MRSettings;
import skily_leyu.mistyrain.utility.type.MRBook;
import skily_leyu.mistyrain.utility.type.MRBook.Directory;

@SideOnly(Side.CLIENT)
public class GuiMRBook extends GuiScreen {

	private static final ResourceLocation BOOK_GUI_TEXTURES = new ResourceLocation(MistyRain.MODID,
			"textures/gui/gui_mr_book.png");
	private static final ResourceLocation PAGE_GUI_TEXTURES = new ResourceLocation(MistyRain.MODID,
			"textures/gui/gui_mr_book_page.png");

	private MRBook mrBook;
	private List<Page> pageRecord;

	public GuiMRBook(EntityPlayer player, ItemStack book) {
		this.mrBook = MRSettings.getBook(book.getItem().getRegistryName().getResourcePath());
		this.pageRecord = new ArrayList<>();
		this.pageRecord.add(new Page(PageLayer.MAIN, 0, null));
	}

	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		Page page = pageRecord.get(pageRecord.size() - 1);
		if (page.getLayer() == PageLayer.MAIN) {
			drawMain(mouseX, mouseY, partialTicks, page);
		}
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	public void drawMain(int mouseX, int mouseY, float partialTicks, Page page) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		int x = (this.width - 230) / 2;
		int y = (this.height - 156) / 2;

		// 渲染背景
		if (page.getPage() == 0) {
			this.mc.getTextureManager().bindTexture(BOOK_GUI_TEXTURES);
		} else {
			this.mc.getTextureManager().bindTexture(PAGE_GUI_TEXTURES);
		}
		this.drawTexturedModalRect(x, y, 0, 0, 115, 159);
		this.mc.getTextureManager().bindTexture(PAGE_GUI_TEXTURES);
		this.drawTexturedModalRect(x + 115, y, 115, 0, 115, 159);

		// 渲染主目录
		List<MRBook.Directory> directories = mrBook.getDirectories();
		int size = directories.size();
		if (page.getPage() != 0) {
			for (int xIndex = 0; xIndex < 3; xIndex++) {
				for (int yIndex = 0; yIndex < 3; yIndex++) {
					int index = (page.getPage() - 1) * 9 + xIndex * 3 + yIndex;
					if (index < size) {
						Directory directory = directories.get(index);
					}
				}
			}
		}
		for(int xIndex = 0; xIndex<3; xIndex++){
			for (int yIndex = 0; yIndex < 3; yIndex++) {
				ItemStack itemStack = new ItemStack(Item.getByNameOrId("mistyrain:herbals_book"));
				GlStateManager.pushMatrix();
				int xOffset = (int)(x/1.6+8+20*xIndex);
				int yOffset = (int)(y/1.6+8+30*yIndex);
				int xText = x+18+32*xIndex;
				int yText = y+40+48*yIndex;
				this.fontRenderer.drawString("烟雨", xText, yText, 0xFFFFF);
				GlStateManager.scale(1.6, 1.6, 1.6);
				this.itemRender.zLevel = 100.0F;
				this.itemRender.renderItemAndEffectIntoGUI(itemStack, xOffset, yOffset);
				this.itemRender.zLevel = 0.0F;
				GlStateManager.popMatrix();
			}
		}
		x+=113;
		for(int xIndex = 0; xIndex<3; xIndex++){
			for (int yIndex = 0; yIndex < 3; yIndex++) {
				ItemStack itemStack = new ItemStack(Item.getByNameOrId("mistyrain:herbals_book"));
				GlStateManager.pushMatrix();
				int xOffset = (int)(x/1.6+8+20*xIndex);
				int yOffset = (int)(y/1.6+8+30*yIndex);
				int xText = x+18+32*xIndex;
				int yText = y+40+48*yIndex;
				this.fontRenderer.drawString("烟雨", xText, yText, 0xFFFFF);
				GlStateManager.scale(1.6, 1.6, 1.6);
				this.itemRender.zLevel = 100.0F;
				this.itemRender.renderItemAndEffectIntoGUI(itemStack, xOffset, yOffset);
				this.itemRender.zLevel = 0.0F;
				GlStateManager.popMatrix();
			}
		}
	}

	public static class Page {
		private PageLayer layer;
		private int page; // 页数
		private String key; // 内容主键

		public Page(PageLayer layer, int page, String key) {
			this.layer = layer;
			this.page = page;
			this.key = key;
		}

		public PageLayer getLayer() {
			return this.layer;
		}

		public int getPage() {
			return this.page;
		}

		@Nullable
		public String getKey() {
			return this.key;
		}
	}

	public static enum PageLayer {
		MAIN, CATALOGUE, DETAIL;
	}

}
