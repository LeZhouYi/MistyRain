package skily_leyu.mistyrain.tileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.items.ItemStackHandler;
import skily_leyu.mistyrain.feature.property.PotProperty;

public class MRTileEntityPot extends TileEntity{

    protected ItemStackHandler soilInventory;

    public MRTileEntityPot(PotProperty potProperty){
        this.soilInventory = new ItemStackHandler(potProperty.getSlotSize()){
            @Override
            public int getSlotLimit(int slot) {
                return potProperty.getStackSize();
            }
        };
    }

}