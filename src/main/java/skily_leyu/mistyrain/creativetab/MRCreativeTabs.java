package skily_leyu.mistyrain.creativetab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class MRCreativeTabs {
    public static final CreativeTabs tabMistyRain = new CreativeTabs("mistyRain") {

		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(Items.REEDS);
		}
	};
}
