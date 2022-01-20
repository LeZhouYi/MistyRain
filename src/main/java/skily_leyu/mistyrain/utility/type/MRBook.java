package skily_leyu.mistyrain.utility.type;

import java.util.List;

import net.minecraft.client.resources.I18n;

public class MRBook {

    private List<Directory> mainDirectories;
    private String key;

    public MRBook(List<Directory> directories,String key){
        this.mainDirectories = directories;
        this.key = key;
    }

    public List<Directory> getDirectories(){
        return this.mainDirectories;
    }

    public String getKey(){
        return this.key;
    }

    public String getBotton(ButtonType type){
        if(type==ButtonType.UPPAGE){
            return this.key+"."+"btnUppage";
        }else if(type==ButtonType.UPPER){
            return this.key+"."+"btnUpper";
        }else{
            return this.key+"."+"btnNextPage";
        }
    }

    public static class Directory{
        private String registryItem;
        private String key;
        private List<Items> items;
        public Directory(String registryItems,String key, String domain,List<Items> items){
            this.registryItem = registryItems;
            this.key = key;
            this.items = items;
        }
        public String getRegistryItem(){
            return this.registryItem;
        }

        public String getTitle(String key){
            return key+"."+this.key+".title";
        }

        public String getKey(){
            return this.key;
        }

        public List<Items> getItems(){
            return this.items;
        }

    }

    public static class Items{

        private String key;
        private String registryItem;
        public Items(String key,String registryItem){
            this.key = key;
            this.registryItem = registryItem;
        }

        public String getKey(){
            return this.key;
        }

        public String getRegistryItem(){
            return this.registryItem;
        }

        public String getTitle(String key){
            return key+"."+this.key+".title";
        }

        public String getContent(String key){
            return key+"."+this.key+".content";
        }

        public int getPageSize(String key){
            String number = I18n.format(key+"."+this.key+"."+"pageSize", new Object());
            return Integer.parseInt(number);
        }

        public String getContent(String key, int page){
            return key+"."+this.key+".content."+page;
        }

    }

    public static enum ButtonType{
        UPPAGE,UPPER,NEXTPAGE;
    }

}
