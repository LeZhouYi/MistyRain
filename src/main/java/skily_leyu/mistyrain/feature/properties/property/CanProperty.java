package skily_leyu.mistyrain.feature.properties.property;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;

public class CanProperty {

    private String name; //用于标记和获取CanProperty,大驼峰式
    private int volume; //物品流体容积
    private int radius; //浇水的范围，0 = 1格，1=3*3
    private int efficiency; //浇水的效率，每次消耗的流体量可以使浇到的花盆积水的量，需参考浇水范围综合计算
    private int volumePerCollect; //每次装水的流体量
    private List<Fluid> fluidList; //允许装填的流体

    /**
     * 该物品的流体是否在白名单中
     * @param itemStack
     * @return
     */
    public boolean containsFluid(ItemStack itemStack){
        FluidStack fluidStack = FluidUtil.getFluidContained(itemStack);
        if(fluidStack!=null){
            return this.fluidList.contains(fluidStack.getFluid());
        }
        return false;
    }

    public CanProperty setName(String name){
        this.name = name;
        return this;
    }

    public CanProperty setVolume(int volume){
        this.volume = volume;
        return this;
    }

    public CanProperty setRadius(int radius){
        this.radius = radius;
        return this;
    }

    public CanProperty setEfficiency(int efficiency){
        this.efficiency = efficiency;
        return this;
    }

    public CanProperty setVolumePerCollect(int volumePerCollect){
        this.volumePerCollect = volumePerCollect;
        return this;
    }

    public String getName(){
        return this.name;
    }

    public int getVolume(){
        return this.volume;
    }

    public int getRadius(){
        return this.radius;
    }

    public int getEfficiency(){
        return this.efficiency;
    }

    public int getVolumePerCollect(){
        return this.volumePerCollect;
    }

}
