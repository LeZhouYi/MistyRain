package skily_leyu.mistyrain.client.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import skily_leyu.mistyrain.MistyRain;

@SideOnly(Side.CLIENT)
public class GuiMRBook extends GuiScreen{

    private static final ResourceLocation BOOK_GUI_TEXTURES = new ResourceLocation(MistyRain.MODID,"textures/gui/gui_mr_book.png");

    public GuiMRBook(EntityPlayer player, ItemStack book){
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(BOOK_GUI_TEXTURES);
        int x = (this.width - 230)/2;
        int y = (this.height- 156)/2;
        this.drawTexturedModalRect(x, y, 0, 0, 230, 156);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

}
