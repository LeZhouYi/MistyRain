public class BlockFctBranch extends BlockMRPlant{
    
    public BlockFctBranch(){
        super(Material.WOOD, MapColor.GREEN_STAINED_HARDENED_CLAY);
        this.setHardness(MRProperty.woodHardness);
        this.setResistance(MRProperty.woodHardness);
        this.setSoundType(SoundType.WOOD);
        this.setDefaultState(blockState.getBaseState().withProperty(VERTICAL, false).withProperty(FACING, EnumFacing.EAST));
        this.setTickRandomly(true);
    }

}
