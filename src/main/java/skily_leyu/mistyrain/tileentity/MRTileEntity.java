package skily_leyu.mistyrain.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.server.management.PlayerChunkMapEntry;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class MRTileEntity extends TileEntity{

	@Override
	public final SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(pos, 1, getUpdateTag());
	}

	@Override
	public final NBTTagCompound getUpdateTag() {
		return this.writeToNBT(new NBTTagCompound());
	}

	@Override
	public final void markDirty() {
		super.markDirty();
		syncToTrackingClients();
	}

	@Override
	public final void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		handleUpdateTag(pkt.getNbtCompound());
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
		return oldState.getBlock() != newSate.getBlock();
	}

	protected final void syncToTrackingClients() {
		if (!this.world.isRemote) {
			SPacketUpdateTileEntity packet = this.getUpdatePacket();
			PlayerChunkMapEntry trackingEntry = ((WorldServer) this.world).getPlayerChunkMap()
					.getEntry(this.pos.getX() >> 4, this.pos.getZ() >> 4);
			if (trackingEntry != null) {
				for (EntityPlayerMP player : trackingEntry.getWatchingPlayers()) {
					player.connection.sendPacket(packet);
				}
			}
		}
	}

}
