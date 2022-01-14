package skily_leyu.mistyrain.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class ContainerMRBook extends Container{

    @Override
    public boolean canInteractWith(EntityPlayer playerIn){
        return true;
    }

}
