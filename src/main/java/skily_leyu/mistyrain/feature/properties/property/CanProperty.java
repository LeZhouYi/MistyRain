package skily_leyu.mistyrain.feature.properties.property;

import java.util.List;

import net.minecraftforge.fluids.Fluid;

public class CanProperty {

    private String name; //用于标记和获取CanProperty,大驼峰式
    private int volume; //物品流体容积
    private int radius; //浇水的范围，0 = 1格，1=3*3
    private int efficiency; //浇水的效率，每次消耗的流体量可以使浇到的花盆积水的量，需参考浇水范围综合计算
    private int volumePerCollect; //每次装水的流体量
    private List<Fluid> fluids; //允许装填的流体
}
