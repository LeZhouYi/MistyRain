package skily_leyu.mistyrain.tileentity.Render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
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
		GlStateManager.rotate(facing * 90.0F, 0.0F, 0.0F, 1.0F);
        RenderSoil(te);
    }

    private void RenderSoil(MRTileEntityPot te){

    }

}