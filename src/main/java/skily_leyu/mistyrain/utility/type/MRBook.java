package skily_leyu.mistyrain.utility.type;

import java.util.List;

import net.minecraft.client.resources.I18n;

public class MRBook {

    protected List<Directory> mainDirectories;
    protected String key;

    public MRBook(List<Directory> directories,String key){
        this.mainDirectories = directories;
        this.key = key;
    }

    /**
     * 获取目录
     * @return
     */
    public List<Directory> getDirectories(){
        return this.mainDirectories;
    }

    public String getKey(){
        return this.key;
    }

    /**
     * 返回其按钮对应的文本格式
     * @param type
     * @return
     */
    public String getBotton(ButtonType type){
        if(type==ButtonType.UPPAGE){
            return this.key+"."+"btnUppage";
        }else if(type==ButtonType.UPPER){
            return this.key+"."+"btnUpper";
        }else{
            return this.key+"."+"btnNextPage";
        }
    }

    /**
     * 目录结构，存储目录相关信息和其包含的物品详细内容
     */
    public static class Directory{

        private String registryItem;
        private String key;
        private List<Items> items;
        public Directory(String registryItems,String key, String domain,List<Items> items){
            this.registryItem = registryItems;
            this.key = key;
            this.items = items;
        }

        /**
         * 获取物品的RegistryName
         * @return
         */
        public String getRegistryItem(){
            return this.registryItem;
        }

        /**
         * 获取目录标题
         * @param key
         * @return
         */
        public String getTitle(String key){
            return key+"."+this.key+".title";
        }

        public String getKey(){
            return this.key;
        }

        /**
         * 获取其包含的物品列表
         * @return
         */
        public List<Items> getItems(){
            return this.items;
        }

    }

    /**
     * 某一项物品的详细内容
     */
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

        /**
         * 获取物品的RegistryName
         * @return
         */
        public String getRegistryItem(){
            return this.registryItem;
        }

        /**
         * 获取标题
         * @param key
         * @return
         */
        public String getTitle(String key){
            return key+"."+this.key+".title";
        }

        /**
         * 获取该物品详细文本的页数
         * @param key
         * @return
         */
        public int getPageSize(String key){
            String number = I18n.format(key+"."+this.key+"."+"pageSize", new Object());
            return Integer.parseInt(number);
        }

        /**
         * 用于I18n获取对应内容文本
         * @param key
         * @param page
         * @return
         */
        public String getContent(String key, int page){
            return key+"."+this.key+".content."+page;
        }

    }

    /**
     * 按钮的类型
     */
    public static enum ButtonType{
        UPPAGE,UPPER,NEXTPAGE;
    }

}
