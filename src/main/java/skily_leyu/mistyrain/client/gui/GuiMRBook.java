package skily_leyu.mistyrain.client.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import skily_leyu.mistyrain.MistyRain;
import skily_leyu.mistyrain.config.MRSettings;
import skily_leyu.mistyrain.utility.type.MRBook;

@SideOnly(Side.CLIENT)
public class GuiMRBook extends GuiScreen {

	private static final ResourceLocation BOOK_GUI_TEXTURES = new ResourceLocation(MistyRain.MODID,
			"textures/gui/gui_mr_book.png");
	private static final ResourceLocation PAGE_GUI_TEXTURES = new ResourceLocation(MistyRain.MODID,
			"textures/gui/gui_mr_book_page.png");

	private MRBook mrBook;
	private List<Page> pageRecord;

	protected GuiButton btnNextPage;
	protected GuiButton btnUpPage;
	protected GuiButton btnUpper;

	public GuiMRBook(EntityPlayer player, ItemStack book) {
		this.mrBook = MRSettings.getBook(book.getItem().getRegistryName().getResourcePath());
		this.pageRecord = new ArrayList<>();
		this.pageRecord.add(new Page(PageLayer.MAIN, 0, 0));
	}

	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		Page page = pageRecord.get(pageRecord.size() - 1);
		if (page.getLayer() == PageLayer.MAIN) {
			drawMain(mouseX, mouseY, partialTicks, page);
		}else if(page.getLayer() == PageLayer.CATALOGUE){
			drawCatalogue(mouseX, mouseY, partialTicks, page);
		}else if(page.getLayer() == PageLayer.DETAIL){
			drawDetail(mouseX, mouseY, partialTicks, page);
		}
		drawBotton(mouseX, mouseY, partialTicks);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	public void drawMain(int mouseX, int mouseY, float partialTicks, Page page) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		int x = (this.width - 230) / 2;
		int y = (this.height - 156) / 2 - 10;

		// 渲染背景
		if (page.getPage() == 0) {
			this.mc.getTextureManager().bindTexture(BOOK_GUI_TEXTURES);
		} else {
			this.mc.getTextureManager().bindTexture(PAGE_GUI_TEXTURES);
		}
		this.drawTexturedModalRect(x, y, 0, 0, 230, 159);

		// 渲染主目录
		List<MRBook.Directory> directories = mrBook.getDirectories();
		int size = directories.size();

		//渲染左边页
		if (page.getPage()%2== 0 && page.getPage() != 0 ) {
			for(int xIndex = 0; xIndex<3; xIndex++){
				for (int yIndex = 0; yIndex < 3; yIndex++) {
					int directIndex = (page.getPage()-1)*9+yIndex*3+xIndex;
					if(directIndex<size){

						//图标位置
						int xOffset = (int)(x/1.6+8+20*xIndex);
						int yOffset = (int)(y/1.6+8+30*yIndex);

						//文本位置
						int xText = x+18+32*xIndex;
						int yText = y+40+48*yIndex;

						//获取图标和标题文本
						MRBook.Directory directory = directories.get(directIndex);
						ItemStack itemStack = new ItemStack(Item.getByNameOrId(directory.getRegistryItem()));
						String title = I18n.format(this.mrBook.getKey()+"."+directory.getTitle(), new Object());

						GlStateManager.pushMatrix();

						this.fontRenderer.drawString(title, xText, yText, 0);

						GlStateManager.scale(1.6, 1.6, 1.6);
						this.itemRender.zLevel = 100.0F;
						this.itemRender.renderItemAndEffectIntoGUI(itemStack, xOffset, yOffset);
						this.itemRender.zLevel = 0.0F;

						GlStateManager.popMatrix();

					}else{
						break;
					}
				}
			}
		}

		//渲染右边页
		if ((page.getPage()+1)%2 != 0) {
			for(int xIndex = 0; xIndex<3; xIndex++){
				for (int yIndex = 0; yIndex < 3; yIndex++) {
					int directIndex = (page.getPage())*9+yIndex*3+xIndex;
					if(directIndex<size){

						//图标位置
						int xOffset = (int)((x+115)/1.6+8+20*xIndex);
						int yOffset = (int)(y/1.6+8+30*yIndex);

						//文本位置
						int xText = x+18+32*xIndex+115;
						int yText = y+40+48*yIndex;

						//获取图标和标题文本
						MRBook.Directory directory = directories.get(directIndex);
						ItemStack itemStack = new ItemStack(Item.getByNameOrId(directory.getRegistryItem()));
						String title = I18n.format(this.mrBook.getKey()+"."+directory.getTitle(), new Object());

						GlStateManager.pushMatrix();

						this.fontRenderer.drawString(title, xText, yText, 0);

						GlStateManager.scale(1.6, 1.6, 1.6);
						this.itemRender.zLevel = 100.0F;
						this.itemRender.renderItemAndEffectIntoGUI(itemStack, xOffset, yOffset);
						this.itemRender.zLevel = 0.0F;

						GlStateManager.popMatrix();

					}else{
						break;
					}
				}
			}
		}
	}

	public void drawCatalogue(int mouseX, int mouseY, float partialTicks, Page page) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		int x = (this.width - 230) / 2;
		int y = (this.height - 156) / 2 - 10;

		//渲染背景
		this.mc.getTextureManager().bindTexture(PAGE_GUI_TEXTURES);
		this.drawTexturedModalRect(x, y, 0, 0, 230, 159);

		// 渲染主目录
		List<MRBook.Directory> directories = mrBook.getDirectories();
		List<MRBook.Items> items = directories.get(page.getIndex()).getItems();
		int size = items.size();

		//渲染左边页
		if (page.getPage()%2== 0) {
			for(int xIndex = 0; xIndex<3; xIndex++){
				for (int yIndex = 0; yIndex < 3; yIndex++) {
					int directIndex = (page.getPage())*9+yIndex*3+xIndex;
					if(directIndex<size){

						//图标位置
						int xOffset = (int)(x/1.6+8+20*xIndex);
						int yOffset = (int)(y/1.6+8+30*yIndex);

						//文本位置
						int xText = x+18+32*xIndex;
						int yText = y+40+48*yIndex;

						//获取图标和标题文本
						MRBook.Items item = items.get(directIndex);
						ItemStack itemStack = new ItemStack(Item.getByNameOrId(item.getRegistryItem()));
						String title = I18n.format(this.mrBook.getKey()+"."+item.getTitle(), new Object());

						GlStateManager.pushMatrix();

						this.fontRenderer.drawString(title, xText, yText, 0);

						GlStateManager.scale(1.6, 1.6, 1.6);
						this.itemRender.zLevel = 100.0F;
						this.itemRender.renderItemAndEffectIntoGUI(itemStack, xOffset, yOffset);
						this.itemRender.zLevel = 0.0F;

						GlStateManager.popMatrix();

					}else{
						break;
					}
				}
			}
		}

		//渲染右边页
		if ((page.getPage()+1)%2 != 0) {
			for(int xIndex = 0; xIndex<3; xIndex++){
				for (int yIndex = 0; yIndex < 3; yIndex++) {
					int directIndex = (page.getPage()+1)*9+yIndex*3+xIndex;
					if(directIndex<size){

						//图标位置
						int xOffset = (int)((x+115)/1.6+8+20*xIndex)+115;
						int yOffset = (int)(y/1.6+8+30*yIndex)+115;

						//文本位置
						int xText = x+18+32*xIndex+115;
						int yText = y+40+48*yIndex+115;

						//获取图标和标题文本
						MRBook.Items item = items.get(directIndex);
						ItemStack itemStack = new ItemStack(Item.getByNameOrId(item.getRegistryItem()));
						String title = I18n.format(this.mrBook.getKey()+"."+item.getTitle(), new Object());

						GlStateManager.pushMatrix();

						this.fontRenderer.drawString(title, xText, yText, 0);

						GlStateManager.scale(1.6, 1.6, 1.6);
						this.itemRender.zLevel = 100.0F;
						this.itemRender.renderItemAndEffectIntoGUI(itemStack, xOffset, yOffset);
						this.itemRender.zLevel = 0.0F;

						GlStateManager.popMatrix();

					}else{
						break;
					}
				}
			}
		}
	}

	public void drawDetail(int mouseX, int mouseY, float partialTicks, Page page) {

	}

	/**
	 * 渲染按钮
	 * @param mouseX
	 * @param mouseY
	 * @param partialTicks
	 */
	public void drawBotton(int mouseX, int mouseY, float partialTicks) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		int x = (this.width - 230) / 2;
		int y = (this.height - 156) / 2 - 5;

		this.mc.getTextureManager().bindTexture(BOOK_GUI_TEXTURES);

		for(int i = 0;i<3;i++){
			this.drawTexturedModalRect(x+91+16*i, y+159, 16*i, 240, 16, 16);
		}
	}

	@Override
	public void initGui(){
        this.buttonList.clear();
        Keyboard.enableRepeatEvents(true);

		int x = (this.width - 230) / 2;
		int y = (this.height - 156) / 2 - 5;

		this.btnUpPage = this.addButton(new GuiButton(0, x+91+16*0, y+159,16,16,this.mrBook.getBotton(MRBook.ButtonType.UPPAGE)));
		this.btnUpper = this.addButton(new GuiButton(1, x+91+16*1, y+159,16,16,this.mrBook.getBotton(MRBook.ButtonType.UPPER)));
		this.btnNextPage = this.addButton(new GuiButton(2, x+91+16*2, y+159,16,16,this.mrBook.getBotton(MRBook.ButtonType.NEXTPAGE)));
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException{
        if (button.enabled){
			if(button.id==0){
				updatePageList(-2);
			}else if(button.id==1){
				updatePageRecord();
			}else if(button.id==2){
				updatePageList(2);
			}
		}
	}

	/**
	 * 返回上一页
	 */
	protected void updatePageRecord(){
		if(this.pageRecord.size()>1){
			this.pageRecord.remove(this.pageRecord.size()-1);
		}
	}

	/**
	 * 更新页面记录，上一页或下一页
	 * @param oper
	 */
	protected void updatePageList(int oper){
		Page page = this.pageRecord.get(this.pageRecord.size()-1);
		if(oper>0){
			if(page.getLayer()==PageLayer.MAIN){
				int size = this.mrBook.getDirectories().size();
				if(size>(page.getPage()+1)*9){
					page.operPage(oper);
				}
			}else if(page.getLayer()==PageLayer.CATALOGUE){
				Page upperPage = this.pageRecord.get(this.pageRecord.size()-2);
				int size = this.mrBook.getDirectories().get(upperPage.getIndex()).getItems().size();
				if(size>(page.getPage()+2)*9){
					page.operPage(oper);
				}
			}else{

			}
		}else{
			page.operPage(oper);
		}
		this.pageRecord.set(this.pageRecord.size()-1, page);
	}

	@Override
	public void onGuiClosed(){
        Keyboard.enableRepeatEvents(false);
    }

	public static class Page {
		private PageLayer layer;
		private int page; // 页数
		private int index; // 上一级的Index

		public Page(PageLayer layer, int page, int index) {
			this.layer = layer;
			this.page = page;
			this.index = index;
		}

		public void operPage(int oper){
			this.page += oper;
			this.page = (this.page<0)?0:this.page;
		}

		public PageLayer getLayer() {
			return this.layer;
		}

		public int getPage() {
			return this.page;
		}

		@Nullable
		public int getIndex() {
			return this.index;
		}
	}

	public static enum PageLayer {
		MAIN, CATALOGUE, DETAIL;
	}

}
