package cn.migu.log.utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/*
 * @version 1.0 created by LXW on 2018/10/30 14:25
 */
public class FileOperationUtil {

    /**
     * 追加方式写入文件数据，自动换行
     * @param filePath 文件完整路径
     * @param info 写入数据
     */
    public static void writeLog(String filePath, String info){
        String time = DateTimeUtil.getNowTimeByFormat(DateTimeUtil.FULLTIMEBY_yMdHmsS);
        String fullInfo = time.concat(" ").concat(info);
        File file = new File(filePath);
        try {
            //如果路径不存在则自动创建
            if (!file.exists()){
                file.createNewFile();
            }
            FileUtils.write(file, fullInfo.concat("\n"), "utf-8", true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
