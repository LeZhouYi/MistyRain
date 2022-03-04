package skily_leyu.mistyrain.command;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import skily_leyu.mistyrain.utility.type.MRSolarTerm;

public class CommandDebug extends CommandBase{

    private static final String NAME = "mrdebug";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return String.format("commands.%s.usage", NAME);
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args,
            BlockPos targetPos) {
        List<String> tabInfo = new ArrayList<>();
        if(args.length<2){
            tabInfo.add("solarterm");
        }else if(args.length<3){
            tabInfo.add("next");
        }
        return tabInfo;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if(args.length==2){
            if(args[0].equals("solarterm")){
                if(args[1].equals("next")){
                    World world = server.getEntityWorld();
                    MRSolarTerm.setNextSolarTerm(world);
                    String solarTermName = I18n.format(MRSolarTerm.getSolarTerm(world).getI18nKey());
                    String monthName = I18n.format(MRSolarTerm.getMonth(world).getI18nKey());
                    String spanName = I18n.format(MRSolarTerm.getTimeSpan(world).getI18nkey());

                    sender.sendMessage(new TextComponentTranslation(String.format("commands.%s.success", NAME),solarTermName,monthName,spanName));
                }
            }
        }
    }

}
