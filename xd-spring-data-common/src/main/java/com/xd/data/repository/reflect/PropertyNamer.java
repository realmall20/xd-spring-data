package com.xd.data.repository.reflect;


import com.xd.data.repository.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * 属性获取
 *
 * @author xiaohei
 * @create 2019-12-10 上午11:03
 **/
public class PropertyNamer {

    public static final String IS = "is";
    public static final String GET = "get";
    public static final String SET = "set";


    public static String methodToProperty(String name) {
        if (name.startsWith(IS)) {
            name = name.substring(2);
        } else if (name.startsWith(GET) || name.startsWith(SET)) {
            name = name.substring(3);
        } else {
            throw new UnsupportedOperationException("");
        }
        return name.substring(0,1).toLowerCase(Locale.ENGLISH)+name.substring(1);
    }

    public static void main(String[] args){
      System.out.println(camelToUnderline("upperUid"));
    }

    /**
     * 驼峰转下划线
     * @param name
     * @return
     */
    public static String camelToUnderline(String name){
        // 快速检查
        if (StringUtils.isEmpty(name)) {
            // 没必要转换
            return "";
        }
        String tempName = name;
        // 大写数字下划线组成转为小写 , 允许混合模式转为小写

        StringBuilder result = new StringBuilder();
        // 用下划线将原始字符串分割
        String camels[] = StringUtils.splitByCharacterTypeCamelCase(name);
        List<String> list=new ArrayList<>(camels.length);
        for (String camel : camels) {
            // 跳过原始字符串中开头、结尾的下换线或双重下划线
            if (StringUtils.isEmpty(camel)) {
                continue;
            }
            list.add(StringUtils.lowerCase(camel));
        }
        return StringUtils.join(list,"_");
    }
}
