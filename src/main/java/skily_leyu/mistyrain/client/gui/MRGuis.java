package skily_leyu.mistyrain.client.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import skily_leyu.mistyrain.MistyRain;
import skily_leyu.mistyrain.container.ContainerMRBook;

public class MRGuis implements IGuiHandler{

    public static final int GUI_MR_BOOK = 1;

    public MRGuis(){
        NetworkRegistry.INSTANCE.registerGuiHandler(MistyRain.instance, this);
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID){
            case GUI_MR_BOOK:
                return new ContainerMRBook();
            default:
                return null;
        }
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch(ID){
            case GUI_MR_BOOK:
                return new GuiMRBook(player,player.getHeldItemMainhand());
            default:
                return null;
        }
    }

}
