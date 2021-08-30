package skily_leyu.mistyrain.creativetab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import skily_leyu.mistyrain.block.MRBlocks;

/**
 * MR相关创造物品栏
 * @author Skily
 * @version 1.0.0
 */
public class MRCreativeTabs {
    public static final CreativeTabs tabMistyRain = new CreativeTabs("mistyRain") {

		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(MRBlocks.springMud);
		}
	};
}
