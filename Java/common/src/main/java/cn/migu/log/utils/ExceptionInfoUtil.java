package cn.migu.log.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/*
 * @version 1.0 created by LXW on 2018/10/31 9:44
 */
public class ExceptionInfoUtil {

    /**
     * 转换完整的异常的信息为单条数据并适应ES自动换行功能
     * @param e 异常
     * @return 转换后异常信息
     */
    public static String translateExceptionInfo(Exception e){
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw, true));
        String str = sw.toString();
        //获取系统信息
        String os_version = System.getProperty("os.name").toUpperCase();
        if (os_version.contains("WINDOWS")){
            //判断是否是windows系统
            str = str.replaceAll("\r\n", "\\\\n");
        }else {
            str = str.replaceAll("\n", "\\\\n");
        }
        str = "\\\\n".concat(str);
        return str;
    }

}
