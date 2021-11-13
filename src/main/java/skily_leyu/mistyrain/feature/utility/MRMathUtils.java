package skily_leyu.mistyrain.feature.utility;

/**
 * 一些基本的数学运算
 */
public class MRMathUtils {

    /**
     * 整型比大小并返回最小的数
     * @param a
     * @param b
     * @return
     */
    public static int minInteger(int a, int b){
        return (a>b)?b:a;
    }
}
