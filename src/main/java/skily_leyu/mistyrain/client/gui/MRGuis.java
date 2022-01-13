package skily_leyu.mistyrain.client.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class MRGuis implements IGuiHandler{

    public static final int GUI_MR_BOOK = 1;

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID){
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
