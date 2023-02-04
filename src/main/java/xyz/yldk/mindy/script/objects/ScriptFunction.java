package xyz.yldk.mindy.script.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ScriptFunction {
    public String funcName = "";

    public String funcID = "";

    public int useCount = 0;

    public int useCountNever = 0;

    public int compiledLine = 0;

    // 程序的主入口
    public int mainJumpIn = 0;

    public int mainJumpInAtGlobal = 0;

    public String[] Argvs = new String[]{};

    public HashMap<String,String> ArgvsIDMap = new HashMap<>();


    public HashMap<String,Integer> compiledLinesMap = new HashMap<>();

    public HashMap<String,Integer> globalLinesVars = new HashMap<>();

    public HashMap<Integer,Integer> returnLinesVars = new HashMap<>();

    public String CodeData = "";



    public int returnLine = 0;


    public void use(){
        this.useCount++;
        this.useCountNever++;
    }

    public void use(int where){
        this.useCount++;
        this.useCountNever++;
        this.returnLinesVars.put(this.useCountNever,where);
    }

}
