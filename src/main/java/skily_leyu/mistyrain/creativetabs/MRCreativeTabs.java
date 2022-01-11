package skily_leyu.mistyrain.creativetabs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import skily_leyu.mistyrain.item.MRItems;

public class MRCreativeTabs {
    public static final CreativeTabs tabMistyRain = new CreativeTabs("herbalGarden") {

		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(MRItems.herbalsBook);
		}
	};
}
