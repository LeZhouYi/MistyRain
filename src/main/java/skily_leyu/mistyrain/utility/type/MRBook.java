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
        private String icon;
        private String key;
        private List<Items> items;
        public Directory(String icon,String key, List<Items> items){
            this.icon = icon;
            this.key = key;
            this.items = items;
        }
        public String getIcon(){
            return this.icon;
        }

        public String getKey(){
            return this.key;
        }

        public List<Items> getItems(){
            return this.items;
        }

    }

    public static class Items{
        private String icon;
        private String key;
        private String image;
        public Items(String icon,String key, String image){
            this.icon = icon;
            this.key = key;
            this.image = image;
        }
        public String getIcon(){
            return this.icon;
        }
        public String getKey(){
            return this.key;
        }
        public String getImage(){
            return this.image;
        }

        public String getTitle(){
            return this.key+".title";
        }

        public String getContent(){
            return this.key+".content";
        }

    }
}
