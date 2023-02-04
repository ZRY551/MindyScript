package xyz.yldk.mindy.script.objects;

import java.util.HashMap;

public class ScriptFunction {
    public String funcName = "";

    public String funcID = "";

    public int useCount = 0;

    public int compiledLine = 0;

    public String[] Argvs = new String[]{};

    public HashMap<String,String> ArgvsIDMap = new HashMap<>();


    public HashMap<String,Integer> compiledLinesMap = new HashMap<>();

    public HashMap<String,Integer> globalLinesVars = new HashMap<>();

    public String CodeData = "";


    public void use(){
        this.useCount++;
    }

}
