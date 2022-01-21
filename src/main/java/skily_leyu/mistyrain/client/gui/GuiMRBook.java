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
	/** 记录操作的页面记录*/
	private List<Page> pageRecord;

	protected GuiButton btnNextPage;
	protected GuiButton btnUpPage;
	protected GuiButton btnUpper;

	private int x;
	private int y;

	public GuiMRBook(EntityPlayer player, ItemStack book) {
		this.mrBook = MRSettings.getBook(book.getItem().getRegistryName().getResourcePath());

		this.pageRecord = new ArrayList<>();
		this.pageRecord.add(new Page(PageLayer.MAIN, 0, 0));

		this.x = (this.width-230)/2;
		this.y = (this.height- 156)/2;
	}

	public GuiMRBook setBook(MRBook book){
		this.mrBook = book;
		return this;
	}

	@Override
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

	protected void drawMain(int mouseX, int mouseY, float partialTicks, Page page) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

		// 渲染背景
		if (page.getPage() == 0) {
			this.mc.getTextureManager().bindTexture(BOOK_GUI_TEXTURES);
		} else {
			this.mc.getTextureManager().bindTexture(PAGE_GUI_TEXTURES);
		}
		this.drawTexturedModalRect(x, y -10, 0, 0, 230, 159);

		// 获取主目录内容
		List<MRBook.Directory> directories = mrBook.getDirectories();
		int size = directories.size();
		String keyPrefix = this.mrBook.getKey();

		for(int i = 0;i<2;i++){

			int pageCount = page.getPage()+i;
			int contentOffset = (pageCount%2==0)?0:115;

			//渲染页面内容
			if (pageCount>0 ) {
				for(int xIndex = 0; xIndex<3; xIndex++){
					for (int yIndex = 0; yIndex < 3; yIndex++) {
						int directIndex = (pageCount-1)*9+yIndex*3+xIndex;
						if(directIndex<size){

							//图标位置
							int xOffset = (int)((x+contentOffset)/1.6+8+20*xIndex);
							int yOffset = (int)((y-10)/1.6+8+30*yIndex);

							//文本位置
							int xText = x+26+32*xIndex+contentOffset;
							int yText = y+30+48*yIndex;

							//获取图标和标题文本
							MRBook.Directory directory = directories.get(directIndex);
							ItemStack itemStack = new ItemStack(Item.getByNameOrId(directory.getRegistryItem()));
							String title = I18n.format(directory.getTitle(keyPrefix), new Object());

							GlStateManager.pushMatrix();

							int textWidth = this.fontRenderer.getStringWidth(title);
							this.fontRenderer.drawString(title, xText-textWidth/2, yText, 0);

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
	}

	protected void drawCatalogue(int mouseX, int mouseY, float partialTicks, Page page) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

		//渲染背景
		this.mc.getTextureManager().bindTexture(PAGE_GUI_TEXTURES);
		this.drawTexturedModalRect(x, y-10, 0, 0, 230, 159);

		// 渲染主目录
		MRBook.Directory directory = mrBook.getDirectories().get(page.getIndex());
		List<MRBook.Items> items = directory.getItems();
		int size = items.size();
		String itemKeyPrefix = this.mrBook.getKey()+"."+directory.getKey();

		for(int i = 0;i<2;i++){

			int pageCount = page.getPage()+i;
			int contentOffset = (pageCount%2==0)?0:115;

			for(int xIndex = 0; xIndex<3; xIndex++){
				for (int yIndex = 0; yIndex < 3; yIndex++) {
					int directIndex = pageCount*9+yIndex*3+xIndex;
					if(directIndex<size){

						//图标位置
						int xOffset = (int)((x+contentOffset)/1.6+8+20*xIndex);
						int yOffset = (int)((y-10)/1.6+8+30*yIndex);

						//文本位置
						int xText = x+26+32*xIndex+contentOffset;
						int yText = y+30+48*yIndex;

						//获取图标和标题文本
						MRBook.Items item = items.get(directIndex);
						ItemStack itemStack = new ItemStack(Item.getByNameOrId(item.getRegistryItem()));
						String title = I18n.format(item.getTitle(itemKeyPrefix), new Object());

						GlStateManager.pushMatrix();

						int textWidth = this.fontRenderer.getStringWidth(title);
						this.fontRenderer.drawString(title, xText-textWidth/2, yText, 0);

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

	protected void drawDetail(int mouseX, int mouseY, float partialTicks, Page page) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

		// 渲染背景
		this.mc.getTextureManager().bindTexture(PAGE_GUI_TEXTURES);
		this.drawTexturedModalRect(x, y-10, 0, 0, 230, 159);

		//获取材质和文本
		Page upperPage = this.pageRecord.get(pageRecord.size()-2);
		MRBook.Directory directory = this.mrBook.getDirectories().get(upperPage.getIndex());
		MRBook.Items item = directory.getItems().get(page.getIndex());
		String itemKeyPrefix = this.mrBook.getKey()+"."+directory.getKey();
		int pageSize = item.getPageSize(itemKeyPrefix);

		//渲染左边页
		if(page.getPage()==0){
			ItemStack itemStack = new ItemStack(Item.getByNameOrId(item.getRegistryItem()));
			String title = I18n.format(item.getTitle(itemKeyPrefix), new Object());

			//图标位置
			int xOffset = (int)((this.x+36)/3.0);
			int yOffset = (int)(this.y/3.0);

			//渲染图标
			GlStateManager.pushMatrix();
			GlStateManager.scale(3.0, 3.0, 3.0);
			this.itemRender.zLevel = 100.0F;
			this.itemRender.renderItemAndEffectIntoGUI(itemStack, xOffset, yOffset);
			this.itemRender.zLevel = 0.0F;
			GlStateManager.popMatrix();

			//渲染标题
			this.fontRenderer.drawSplitString(title, x+12, y+55, 100, 0);
			if(pageSize>0){
				String content = I18n.format(item.getContent(itemKeyPrefix, 0), new Object());
				content = "    "+content;
				this.fontRenderer.drawSplitString(content, x+12, y+60, 100, 0);
			}

		}else if(page.getPage()%2==0){

		}

		//渲染右边页
		int nextPage = page.getPage()+1;
		if(nextPage%2==1&&nextPage<pageSize){

		}

	}

	/**
	 * 渲染按钮
	 * @param mouseX
	 * @param mouseY
	 * @param partialTicks
	 */
	protected void drawBotton(int mouseX, int mouseY, float partialTicks) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

		this.mc.getTextureManager().bindTexture(BOOK_GUI_TEXTURES);

		for(int i = 0;i<3;i++){
			this.drawTexturedModalRect(x+91+16*i, y+154, 16*i, 240, 16, 16);
		}
	}

	@Override
	public void initGui(){
        this.buttonList.clear();
        Keyboard.enableRepeatEvents(true);

		this.x = (this.width-230)/2;
		this.y = (this.height- 156)/2;

		//通用按钮
		GuiMRButton tempUpPage = new GuiMRButton(0, x+91+16*0, y+154,16,16,this.mrBook.getBotton(MRBook.ButtonType.UPPAGE)).setTexturePoint(BOOK_GUI_TEXTURES, 0, 240, 16, 16);
		GuiMRButton tempUpper = new GuiMRButton(1, x+91+16*1, y+154,16,16,this.mrBook.getBotton(MRBook.ButtonType.UPPER)).setTexturePoint(BOOK_GUI_TEXTURES, 16, 240, 16, 16);
		GuiMRButton tempNextPage = new GuiMRButton(2, x+91+16*2, y+154,16,16,this.mrBook.getBotton(MRBook.ButtonType.NEXTPAGE)).setTexturePoint(BOOK_GUI_TEXTURES, 32, 240, 16, 16);

		this.btnUpPage = this.addButton(tempUpPage);
		this.btnUpper = this.addButton(tempUpper);
		this.btnNextPage = this.addButton(tempNextPage);

		initPageBotton();

	}

	/**
	 * 初始化页面内动态的按钮
	 */
	protected void initPageBotton(){

		Page page = this.pageRecord.get(this.pageRecord.size()-1);
		if(page.getLayer() == PageLayer.MAIN){

			//主目录的按钮
			List<MRBook.Directory> directories = mrBook.getDirectories();
			int size = directories.size();

			for(int i = 0;i<2;i++){
				int pageCount = page.getPage()+i;
				int contentOffset = (pageCount%2==0)?0:115;
				if(pageCount>0){
					for(int xIndex = 0; xIndex<3; xIndex++){
						for (int yIndex = 0; yIndex < 3; yIndex++) {
							int directIndex = (pageCount-1)*9+yIndex*3+xIndex;
							if(directIndex<size){
								//图标位置
								int xOffset = this.x+contentOffset+11+32*xIndex;
								int yOffset = this.y-10+12+30*yIndex;
								GuiMRButton tempBtn = new GuiMRButton(100+directIndex,xOffset,yOffset,(int)(16*1.6),(int)(16*1.6),"");
								this.buttonList.add(tempBtn);
							}
						}
					}
				}
			}
		}
		else if(page.getLayer()==PageLayer.CATALOGUE){
			//次级目录的按钮
			Page upperPage = this.pageRecord.get(this.pageRecord.size()-2);
			List<MRBook.Items> items = mrBook.getDirectories().get(upperPage.getIndex()).getItems();
			int size = items.size();

			for(int i = 0;i<2;i++){
				int pageCount = page.getPage()+i;
				int contentOffset = (pageCount%2==0)?0:115;
				for(int xIndex = 0; xIndex<3; xIndex++){
					for (int yIndex = 0; yIndex < 3; yIndex++) {
						int directIndex = pageCount*9+yIndex*3+xIndex;
						if(directIndex<size){
							//图标位置
							int xOffset = this.x+contentOffset+11+32*xIndex;
							int yOffset = this.y-10+12+30*yIndex;
							GuiMRButton tempBtn = new GuiMRButton(200+directIndex,xOffset,yOffset,(int)(16*1.6),(int)(16*1.6),"");
							this.buttonList.add(tempBtn);
						}
					}
				}
			}
		}
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException{
        if (button.enabled){
			if(button.id==0){
				//上一页
				updatePageList(-2);
			}else if(button.id==1){
				//上一级
				updatePageRecord();
				initGui();
			}else if(button.id==2){
				//下一页
				updatePageList(2);
			}else if(button.id>=100&&button.id<200){
				//进入目录内容
				this.pageRecord.add(new Page(PageLayer.CATALOGUE,0,button.id-100));
				initGui();
			}else if(button.id>=200&&button.id<300){
				//进入详细内容
				this.pageRecord.add(new Page(PageLayer.DETAIL,0,button.id-200));
				initGui();
			}
		}
	}

	/**
	 * 返回上一级
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

	/**
	 * 页面，
	 */
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

	/**
	 * 页面类型:主目录，次级目录，详细内容
	 */
	public static enum PageLayer {
		MAIN, CATALOGUE, DETAIL;
	}

}
