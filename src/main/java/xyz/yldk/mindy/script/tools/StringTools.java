package xyz.yldk.mindy.script.tools;

import java.util.ArrayList;

public class StringTools {
    public static String Array2String(String[] strs,String spiltStr,boolean removeFirst,int removeNumber,boolean trim){
        String tempStr = "";
        if(removeFirst){
            for (int i = 0; i < strs.length; i++) {
                if(i <= (removeNumber - 1)){
                    continue;
                }
                tempStr = tempStr + strs[i] + spiltStr;

            }



        }else{
            for (String str: strs) {
                tempStr = tempStr + str + spiltStr;

            }


        }
        if(trim){
            tempStr = tempStr.trim();
        }

        return tempStr;
    }

    public static String Array2String(String[] strs,String spiltStr,boolean removeFirst,int removeNumber){
        return Array2String(strs,spiltStr,removeFirst,removeNumber,false);

    }
}
