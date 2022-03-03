package skily_leyu.mistyrain.command;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import skily_leyu.mistyrain.utility.type.MRSolarTerm;

public class CommandMRTime extends CommandBase{

    private static final String NAME = "mrtime";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return String.format("commands.%s.usage", NAME);
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if(args.length>1){
            throw new WrongUsageException(String.format("commands.%s.usage", NAME));
        }else{
            if(args[0].equals("info")){
                MRSolarTerm solarTerm = MRSolarTerm.getSolarTerm(server.getEntityWorld());
                String solarTermName = I18n.format(solarTerm.getI18nKey());
                sender.sendMessage(new TextComponentTranslation(String.format("commands.%s.success", NAME),solarTermName));
            }
        }
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args,
            BlockPos targetPos) {
        List<String> tabInfo = new ArrayList<>();
        System.out.println(args.length);
        if(args.length<2){
            tabInfo.add("info");
        }
        return tabInfo;
    }

    @Override
    public int getRequiredPermissionLevel(){
        return 1;
    }

}
