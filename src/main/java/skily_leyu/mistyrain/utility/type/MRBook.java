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
        private String domain;
        public Directory(String icon,String key, String domain,List<Items> items){
            this.icon = icon;
            this.key = key;
            this.domain = domain;
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

        public String getDomain(){
            return this.domain;
        }

    }

    public static class Items{
        private String icon;
        private String key;
        private String image;
        private String domain;
        public Items(String icon,String key, String image, String domain){
            this.icon = icon;
            this.key = key;
            this.image = image;
            this.domain = domain;
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

        public String getDomain(){
            return this.domain;
        }

        public String getTitle(){
            return this.key+".title";
        }

        public String getContent(){
            return this.key+".content";
        }

    }
}
