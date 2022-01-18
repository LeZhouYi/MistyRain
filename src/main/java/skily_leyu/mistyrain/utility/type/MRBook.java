package skily_leyu.mistyrain.utility.type;

import java.util.List;

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

    public static class Directory{
        private String registyItem;
        private String key;
        private List<Items> items;
        public Directory(String registyItems,String key, String domain,List<Items> items){
            this.registyItem = registyItems;
            this.key = key;
            this.items = items;
        }
        public String getRegistyItem(){
            return this.registyItem;
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

        public String getTitle(){
            return this.key+".title";
        }

        public String getContent(){
            return this.key+".content";
        }

    }
}
