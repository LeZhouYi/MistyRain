package skily_leyu.mistyrain.tileentity;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.server.management.PlayerChunkMapEntry;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.WorldServer;

public abstract class TileEntityMR extends TileEntity{


	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(pos, 1, this.writeToNBT(new NBTTagCompound()));
	}

	/**
	 * 更新要同步的数据
	 */
	public abstract NBTTagCompound writeUpdatePacket(NBTTagCompound nbt);

	@Override
	public void onDataPacket(NetworkManager manager, SPacketUpdateTileEntity packet) {
		readFromNBT(packet.getNbtCompound());
	}

	public void syncToTrackingClients() {
		if (!this.world.isRemote) {
			SPacketUpdateTileEntity packet = this.getUpdatePacket();
			PlayerChunkMapEntry trackingEntry = ((WorldServer)this.getWorld()).getPlayerChunkMap().getEntry(this.getPos().getX()>>4, this.getPos().getZ()>>4);
			if (trackingEntry != null) {
				for (EntityPlayerMP player : trackingEntry.getWatchingPlayers()) {
					player.connection.sendPacket(packet);
				}
			}
		}
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		return this.writeToNBT(new NBTTagCompound());
	}

	@Override
	public void handleUpdateTag(NBTTagCompound tag) {
		this.readFromNBT(tag);
	}

}
