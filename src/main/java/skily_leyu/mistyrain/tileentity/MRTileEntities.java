package skily_leyu.mistyrain.tileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import skily_leyu.mistyrain.MistyRain;

public class MRTileEntities {

    public MRTileEntities(FMLPreInitializationEvent event){
        registerTileEntity(TileEntityMRPot.class, "mr_pot");
    }

    public void registerTileEntity(Class<? extends TileEntity> tileEntityClass, String id){
        GameRegistry.registerTileEntity(tileEntityClass, new ResourceLocation(MistyRain.MODID,"id"));
    }

}
