package skily_leyu.mistyrain.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class GuiMRButton extends GuiButton{

    public ResourceLocation bottonTexture;
    private int xTexture;
    private int yTexture;
    private int widthTexture;
    private int heightTexture;

    public GuiMRButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
    }

    /**
     * 设置要渲染的材质和位置
     * @param btnTexture
     * @param x
     * @param y
     * @param width
     * @param height
     * @return
     */
    public GuiMRButton setTexturePoint(ResourceLocation btnTexture,int xTexture, int yTexture, int widthTexture, int heightTexture){
        bottonTexture = btnTexture;
        this.xTexture = xTexture;
        this.yTexture = yTexture;
        this.widthTexture = widthTexture;
        this.heightTexture = heightTexture;
        return this;
    }

    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks){
        if (this.visible && bottonTexture!=null){

            mc.getTextureManager().bindTexture(bottonTexture);

            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;

            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

            this.drawTexturedModalRect(this.x, this.y, xTexture, yTexture, widthTexture, heightTexture);

            this.mouseDragged(mc, mouseX, mouseY);

        }
    }

}
