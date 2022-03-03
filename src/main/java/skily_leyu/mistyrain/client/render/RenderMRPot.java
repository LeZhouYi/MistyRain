package skily_leyu.mistyrain.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ModelManager;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import skily_leyu.mistyrain.tileentity.TileEntityMRPot;

public class RenderMRPot extends TileEntitySpecialRenderer<TileEntityMRPot>{

    private RenderItem itemRender;
	private BlockRendererDispatcher blockDispatcher;
	private ModelManager modelManager;

    @Override
    public void render(TileEntityMRPot te, double x, double y, double z, float partialTicks, int destroyStage,
            float alpha) {
        super.render(te, x, y, z, partialTicks, destroyStage, alpha);
        if(this.itemRender == null) {
			this.itemRender = Minecraft.getMinecraft().getRenderItem();
		}
		if(this.blockDispatcher== null){
			this.blockDispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
			this.modelManager = blockDispatcher.getBlockModelShapes().getModelManager();
		}
		int facing = te.getFacing().getHorizontalIndex();

        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
		GlStateManager.rotate(0.0F, facing * 90.0F, 0.0F, 1.0F);

        RenderSoil(te);
		RenderPlant(te);

		GlStateManager.popMatrix();
    }

	protected void RenderPlant(TileEntityMRPot te){
		ModelResourceLocation plantModel = te.getPlantModel(0);
		if(plantModel!=null){
			IBakedModel ibakedmodel = modelManager.getModel(plantModel);

			GlStateManager.pushMatrix();
			GlStateManager.scale(0.85F, 0.85F, 0.85F);
			GlStateManager.translate(0.1F,0.425F,0.1F);
			blockDispatcher.getBlockModelRenderer().renderModelBrightnessColor(ibakedmodel, 1.0F, 1.0F, 1.0F, 1.0F);
			GlStateManager.popMatrix();
		}
	}

	/**
	 * 渲染泥土
	 * @param te
	 */
    protected void RenderSoil(TileEntityMRPot te){
		ItemStack rendItemStack = te.getSoil(0);
		if(!rendItemStack.isEmpty()){
			GlStateManager.pushMatrix();
			GlStateManager.disableLighting();

			GlStateManager.scale(1.0F, 0.5F, 1.0F);
			GlStateManager.translate(0.5F,0.5F,0.5F);

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