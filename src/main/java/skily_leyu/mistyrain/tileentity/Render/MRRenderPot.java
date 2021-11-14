package skily_leyu.mistyrain.tileentity.Render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import skily_leyu.mistyrain.tileentity.MRTileEntityPot;

public class MRRenderPot extends TileEntitySpecialRenderer<MRTileEntityPot>{

    private RenderItem itemRender;

    @Override
    public void render(MRTileEntityPot te, double x, double y, double z, float partialTicks, int destroyStage,
    		float alpha) {
    	super.render(te, x, y, z, partialTicks, destroyStage, alpha);
    	GlStateManager.pushMatrix();
    	GlStateManager.translate(x + 0.5D, y + 0.5, z + 0.5D);

    	if (this.itemRender == null) {
			this.itemRender = Minecraft.getMinecraft().getRenderItem();
		}
		int facing = te.getFacing().getHorizontalIndex();
		GlStateManager.rotate(0.0F, facing * 90.0F, 0.0F, 1.0F);
        RenderSoil(te);

		GlStateManager.popMatrix();
    }

    private void RenderSoil(MRTileEntityPot te){
		ItemStack rendItemStack = te.getSoilItemStack();
		if(!rendItemStack.isEmpty()){
			GlStateManager.pushMatrix();
			GlStateManager.disableLighting();

			GlStateManager.scale(1.0F, 0.5F, 1.0F);
			GlStateManager.translate(0.0F,-0.5F,0.0F);

			GlStateManager.pushAttrib();
			RenderHelper.enableStandardItemLighting();
			this.itemRender.renderItem(rendItemStack, ItemCameraTransforms.TransformType.FIXED);
			RenderHelper.disableStandardItemLighting();
			GlStateManager.popAttrib();

			GlStateManager.enableLighting();
			GlStateManager.popMatrix();
		}
    }

}