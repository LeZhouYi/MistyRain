package skily_leyu.mistyrain.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.items.ItemStackHandler;
import skily_leyu.mistyrain.feature.property.PotProperty;

public class MRTileEntityPot extends TileEntity{

    protected ItemStackHandler soilInventory;
    protected PotProperty potProperty;

    public MRTileEntityPot(PotProperty potProperty){
        this.potProperty = potProperty;
        this.soilInventory = new ItemStackHandler(potProperty.getSlotSize()){
            @Override
            public int getSlotLimit(int slot) {
                return potProperty.getStackSize();
            }
        };
    }

    public int addItemStack(ItemStack itemStack){
        if(this.potProperty.containsSoil(itemStack)){

        }
        return 0;
    }

    protected int addSoil(ItemStack itemStack){
        return 0;
    }

}