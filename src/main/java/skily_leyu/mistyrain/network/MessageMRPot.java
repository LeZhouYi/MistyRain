package skily_leyu.mistyrain.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import skily_leyu.mistyrain.block.BlockMRWoodenPot;
import skily_leyu.mistyrain.tileentity.TileEntityMRPot;

public class MessageMRPot implements IMessage{

    private NBTTagCompound nbt;

    public MessageMRPot(){
        this.nbt = new NBTTagCompound();
    }

    public MessageMRPot setMessage(TileEntityMRPot te,NBTTagCompound nbt){
        this.nbt = nbt;
        this.nbt.setInteger("posX", te.getPos().getX());
        this.nbt.setInteger("posY", te.getPos().getY());
        this.nbt.setInteger("posZ", te.getPos().getZ());
        return this;
    }

    public NBTTagCompound getMessageCompound(){
        return this.nbt;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        nbt = ByteBufUtils.readTag(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeTag(buf, nbt);
    }

    public static class Handler implements IMessageHandler<MessageMRPot,IMessage>{

        @Override
        public IMessage onMessage(MessageMRPot message, MessageContext ctx) {
        	if(ctx.side==Side.SERVER) {
        		EntityPlayerMP serverPlayer = ctx.getServerHandler().player;
        		NBTTagCompound compound = message.getMessageCompound();
        		BlockPos pos = new BlockPos(compound.getInteger("posX"),compound.getInteger("posY"),compound.getInteger("posZ"));
        		serverPlayer.getServerWorld().addScheduledTask(() -> {
        			if(serverPlayer.getServerWorld().isAreaLoaded(pos, 1)){
        				TileEntityMRPot te = BlockMRWoodenPot.getTileEntity(serverPlayer.getEntityWorld(), pos);
        				if(te!=null){
        					System.out.print("TEST#@$()");
        					te.readFromNBT(compound);
        				}
        			}
        		});
        		
        	}
            return null;
        }

    }
}
