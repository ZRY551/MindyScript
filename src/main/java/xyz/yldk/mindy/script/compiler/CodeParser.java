package xyz.yldk.mindy.script.compiler;

import xyz.yldk.mindy.script.objects.ScriptFunction;
import xyz.yldk.mindy.script.tools.StringTools;

import java.io.*;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

public class CodeParser {

    public boolean debugMode = false;

    public boolean isError = false;

    //public HashMap<String,Object> globalVars = new HashMap<>();

    public HashMap<String,Integer> globalLinesVars = new HashMap<>();

    public String codeData = "";


    public String compiledCodeData = "";

    public String ScriptID = StringTools.getRamdomID();



    public HashMap<String, ScriptFunction> functionCodeData = new HashMap<>();


    public int lineNow = 0;
    public int charNow = 0;

    public int compiledLineNow = -1;

    /*public int smallBracketsLeft = 0;
    public int smallBracketsRight = 0;
    public int middleBracketsLeft = 0;
    public int middleBracketsRight = 0;
    public int bigBracketsLeft = 0;
    public int bigBracketsRight = 0;

    public int doubleQuotes = 0;
    public int Quotes = 0;*/
    // todo

    public boolean note = false;

    public boolean inFunction = false;

    public String FunctionNameNow = "";












    public CodeParser(){




    }



    public void run(File file){
        if(!file.exists()){
            exception(file,"File not Found !", null);
            return;
        }
        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream(file));
            BufferedReader br = new BufferedReader(isr);
            Stream<String> linesAll = br.lines();
            List<String> strList = linesAll.toList();

            int allLinesSize = strList.size();

            for (String thisLine : strList) {
                String temp1 = thisLine.trim();
                String[] line_spilt = temp1.split(" ");

                if (line_spilt.length >= 1) {
                    String startString = line_spilt[0];
                    if (startString.trim().startsWith("/*")) {
                        this.note = true;
                        continue;

                    }else {
                        if (startString.trim().startsWith("*/")) {
                            this.note = false;
                            continue;

                        }
                    }

                    if(!note){
                        //String startString = line_spilt[0];
                        if (startString.startsWith("func")) {
                            String funcName = line_spilt[1];
                            /*if(line_spilt.length == 2){

                            }*/
                            String ftemp0 = thisLine.substring(0,thisLine.length() - 1).trim();
                            ftemp0 = ftemp0 + " {";
                            line_spilt = ftemp0.split(" ");

                            String ftemp3 = "";
                            String[] ftemp2 = funcName.split("\\(");
                            for (int i = 1; i < (line_spilt.length - 1); i++) {
                                ftemp3 = ftemp3 + line_spilt[i] + " ";

                            }



                            if(ftemp2.length >= 2){
                                /*for (int i = 1; i < (line_spilt.length - 1); i++) {

                                }*/
                            }

                            String[] ftemp4 = ftemp3.split("\\(");

                            String realFuncName = ftemp4[0];

                            String fArgvs = "";

                            for (int i = 1; i < ftemp4.length; i++) {
                                fArgvs = fArgvs + ftemp4[i];

                            }

                            String[] fArgvs_spilt = fArgvs.split("\\)");
                            fArgvs = fArgvs_spilt[0];


                            // 此时拆分为 a,b 形式


                            realFuncName = realFuncName.trim();




                            this.FunctionNameNow = realFuncName;
                            // System.out.println(fArgvs);
                            // 此处得到 a   ,b 形式的字符串
                            String[] fArgs2 = fArgvs.split(",");
                            for (int i = 0; i < fArgs2.length; i++) {
                                fArgs2[i] = fArgs2[i].trim();
                                // 去除了参数字符串前后的空格，但是保留了参数原本的值
                            }










                            ScriptFunction sfObj = new ScriptFunction();
                            sfObj.funcName = realFuncName;
                            sfObj.funcID = StringTools.getRamdomID();

                            // 将去除空格后的参数列表传入函数对象中
                            sfObj.Argvs = fArgs2;

                            // 再次遍历数组并为函数参数生成ID
                            for (int i = 0; i < fArgs2.length; i++) {
                                // 生成随机ID并以名称存入
                                sfObj.ArgvsIDMap.put(fArgs2[i],StringTools.getRamdomID());



                            }


                            functionCodeData.put(realFuncName,sfObj);












                            if(line_spilt[line_spilt.length - 1].equals("{") || thisLine.charAt(thisLine.length() - 1) == '{'){
                                this.inFunction = true;
                            }



                            continue;

                        }else {
                            if (startString.startsWith("}")) {
                                if(this.inFunction){
                                    this.inFunction = false;
                                    this.FunctionNameNow = "";
                                }

                                continue;

                            }
                        }
                    }

                    if(!note) {
                        if (startString.trim().startsWith("//")) {
                            continue;
                        } else if(startString.equals("\n") || startString.equals("\r")){
                            continue;

                        } else if (startString.equals("getEnabled")) {
                            this.writeCode(new String[]{"sensor", line_spilt[2], line_spilt[1], "@enabled"});


                        } else if (startString.equals("setEnabled")) {
                            this.writeCode(new String[]{"control", "enabled", line_spilt[1], line_spilt[2], "0", "0", "0"});


                        } else if (startString.equals("printMsg") || startString.equals("printMessage") || startString.equals("msgPrint") || startString.equals("messagePrint")) {
                            String tempStr = StringTools.Array2String(line_spilt," ",true,2,true);
                            this.writeCode(new String[]{"print", tempStr});
                            this.writeCode(new String[]{"printflush", line_spilt[1]});


                        } else if (startString.equals("getLine")) {
                            if(!this.inFunction) {
                                this.writeCode(new String[]{"set", line_spilt[1], String.valueOf(this.compiledLineNow)});
                            }else{
                                ScriptFunction SfObj = this.functionCodeData.get(this.FunctionNameNow);
                                if(SfObj != null){
                                    String fwc_temp_str1 = "@#func-lines=" + StringTools.getRamdomID();
                                    SfObj.compiledLinesMap.put(fwc_temp_str1,SfObj.compiledLine);
                                    this.writeCode(new String[]{"set", line_spilt[1], fwc_temp_str1});

                                }
                            }


                        }else if (startString.equals("getLineG") || startString.equals("getLineGlobal")) {
                            //this.writeCode(new String[]{"set", line_spilt[1], String.valueOf(this.compiledLineNow)});
                            globalLinesVars.put(line_spilt[1],this.compiledLineNow);

                            if(!this.inFunction) {
                                globalLinesVars.put(line_spilt[1],this.compiledLineNow);
                                //this.writeCode(new String[]{"set", line_spilt[1], String.valueOf(this.compiledLineNow)});
                            }else{
                                ScriptFunction SfObj = this.functionCodeData.get(this.FunctionNameNow);
                                if(SfObj != null){
                                    String fwc_temp_str1 = "@F=" + line_spilt[1];

                                    SfObj.globalLinesVars.put(fwc_temp_str1,SfObj.compiledLine);
                                    fwc_temp_str1 = "@=" + line_spilt[1];
                                    SfObj.globalLinesVars.put(fwc_temp_str1,SfObj.compiledLine);
                                    //this.writeCode(new String[]{"set", line_spilt[1], fwc_temp_str1});
                                    //globalLinesVars.put(line_spilt[1],this.compiledLineNow + SfObj.compiledLine);


                                }
                            }


                        }else if (startString.equals("ifEnd") || startString.equals("endIf")) {
                            /*String tempStr = StringTools.Array2String(line_spilt," ",true,1,true);
                            this.writeCode(new String[]{"jump","&#endLine#&", tempStr});*/
                            //String tempStr = StringTools.Array2String(line_spilt," ",true,1,true);
                            String tempStr = "";
                            if("@always".equals(line_spilt[1]) || "@true".equals(line_spilt[1])){
                                tempStr = "always";
                            }else {
                                if ("==".equals(line_spilt[2]) || "equal".equals(line_spilt[2])) {
                                    tempStr = "equal";
                                }
                                if ("!=".equals(line_spilt[2]) || "not".equals(line_spilt[2]) || "notEqual".equals(line_spilt[2])) {
                                    tempStr = "notEqual";
                                }
                                if ("<".equals(line_spilt[2]) || "lessThan".equals(line_spilt[2])) {
                                    tempStr = "lessThan";
                                }
                                if ("<=".equals(line_spilt[2]) || "=<".equals(line_spilt[2]) || "lessThanEq".equals(line_spilt[2])) {
                                    tempStr = "lessThanEq";
                                }
                                if (">".equals(line_spilt[2]) || "greaterThan".equals(line_spilt[2])) {
                                    tempStr = "greaterThan";
                                }
                                if (">=".equals(line_spilt[2]) || "=>".equals(line_spilt[2]) || "greaterThanEq".equals(line_spilt[2])) {
                                    tempStr = "greaterThanEq";
                                }
                                if ("===".equals(line_spilt[2]) || "strictEqual".equals(line_spilt[2])) {
                                    tempStr = "strictEqual";
                                }
                                if ("always".equals(line_spilt[2]) || "true".equals(line_spilt[2])) {
                                    tempStr = "always";
                                }
                            }

                            if(line_spilt.length <= 2){
                                this.writeCode(new String[]{"jump","&#endLine#&", tempStr,"null","null"});
                            } else {
                                this.writeCode(new String[]{"jump","&#endLine#&", tempStr,line_spilt[1],line_spilt[3]});
                            }






                        }else if (startString.equals("ifJump") || startString.equals("jumpIf")) {
                            /*String tempStr = StringTools.Array2String(line_spilt," ",true,1,true);
                            this.writeCode(new String[]{"jump","&#endLine#&", tempStr});*/
                            //String tempStr = StringTools.Array2String(line_spilt," ",true,1,true);
                            String tempStr = "";
                            if("@always".equals(line_spilt[2]) || "@true".equals(line_spilt[2])){
                                tempStr = "always";
                            }else {
                                if ("==".equals(line_spilt[3]) || "equal".equals(line_spilt[3])) {
                                    tempStr = "equal";
                                }
                                if ("!=".equals(line_spilt[3]) || "not".equals(line_spilt[3]) || "notEqual".equals(line_spilt[3])) {
                                    tempStr = "notEqual";
                                }
                                if ("<".equals(line_spilt[3]) || "lessThan".equals(line_spilt[3])) {
                                    tempStr = "lessThan";
                                }
                                if ("<=".equals(line_spilt[3]) || "=<".equals(line_spilt[3]) || "lessThanEq".equals(line_spilt[3])) {
                                    tempStr = "lessThanEq";
                                }
                                if (">".equals(line_spilt[3]) || "greaterThan".equals(line_spilt[3])) {
                                    tempStr = "greaterThan";
                                }
                                if (">=".equals(line_spilt[3]) || "=>".equals(line_spilt[3]) || "greaterThanEq".equals(line_spilt[2])) {
                                    tempStr = "greaterThanEq";
                                }
                                if ("===".equals(line_spilt[3]) || "strictEqual".equals(line_spilt[3])) {
                                    tempStr = "strictEqual";
                                }
                                if ("always".equals(line_spilt[3]) || "true".equals(line_spilt[3])) {
                                    tempStr = "always";
                                }
                            }

                            if(line_spilt.length <= 4){
                                this.writeCode(new String[]{"jump",line_spilt[1], tempStr,"null","null"});
                            } else {
                                this.writeCode(new String[]{"jump",line_spilt[1], tempStr,line_spilt[2],line_spilt[4]});
                            }






                        } else if (startString.equals("calc")){
                            // https://www.mindustry-logic.xyz/Guide/operation/operation.html
                            String tempStr = "";
                            if("+".equals(line_spilt[4]) || "add".equals(line_spilt[4])){
                                tempStr = "add";
                            }
                            if("-".equals(line_spilt[4]) || "sub".equals(line_spilt[4])){
                                tempStr = "sub";
                            }
                            if("*".equals(line_spilt[4]) || "mul".equals(line_spilt[4])){
                                tempStr = "mul";
                            }
                            if("/".equals(line_spilt[4]) || "div".equals(line_spilt[4])){
                                tempStr = "div";
                            }
                            if("//".equals(line_spilt[4]) || "idiv".equals(line_spilt[4])){
                                tempStr = "idiv";
                            }
                            if("%".equals(line_spilt[4]) || "mod".equals(line_spilt[4])){
                                tempStr = "mod";
                            }
                            if("^".equals(line_spilt[4]) || "pow".equals(line_spilt[4])){
                                tempStr = "pow";
                            }
                            if("==".equals(line_spilt[4]) || "equal".equals(line_spilt[4])){
                                tempStr = "equal";
                            }
                            if ("!=".equals(line_spilt[4]) || "notEqual".equals(line_spilt[4])) {
                                //  || "not".equals(line_spilt[4])
                                tempStr = "notEqual";
                            }
                            if ("<".equals(line_spilt[4]) || "lessThan".equals(line_spilt[4])) {
                                tempStr = "lessThan";
                            }
                            if ("<=".equals(line_spilt[4]) || "=<".equals(line_spilt[4]) || "lessThanEq".equals(line_spilt[4])) {
                                tempStr = "lessThanEq";
                            }
                            if (">".equals(line_spilt[4]) || "greaterThan".equals(line_spilt[4])) {
                                tempStr = "greaterThan";
                            }
                            if (">=".equals(line_spilt[4]) || "=>".equals(line_spilt[4]) || "greaterThanEq".equals(line_spilt[4])) {
                                tempStr = "greaterThanEq";
                            }
                            if ("===".equals(line_spilt[4]) || "strictEqual".equals(line_spilt[4])) {
                                tempStr = "strictEqual";
                            }
                            if ("<<".equals(line_spilt[4]) || "shl".equals(line_spilt[4])) {
                                tempStr = "shl";
                            }
                            if (">>".equals(line_spilt[4]) || "shr".equals(line_spilt[4])) {
                                tempStr = "shr";
                            }
                            if ("|".equals(line_spilt[4]) || "or".equals(line_spilt[4])) {
                                tempStr = "or";
                            }
                            if ("&".equals(line_spilt[4]) || "and".equals(line_spilt[4]) || "b-and".equals(line_spilt[4])) {
                                tempStr = "and";
                            }
                            if ("||".equals(line_spilt[4]) || "xor".equals(line_spilt[4])) {
                                tempStr = "xor";
                            }
                            if ("Flip".equals(line_spilt[4]) || "not".equals(line_spilt[4])) {
                                tempStr = "not";
                            }
                            if ("max".equals(line_spilt[4])) {
                                tempStr = "max";
                            }
                            if ("min".equals(line_spilt[4])) {
                                tempStr = "min";
                            }
                            if ("angle".equals(line_spilt[4])) {
                                tempStr = "angle";
                            }
                            if ("len".equals(line_spilt[4])) {
                                tempStr = "len";
                            }
                            if ("noise".equals(line_spilt[4])) {
                                tempStr = "noise";
                            }
                            if ("abs".equals(line_spilt[4])) {
                                tempStr = "abs";
                            }
                            if ("log".equals(line_spilt[4])) {
                                tempStr = "log";
                            }
                            if ("log10".equals(line_spilt[4])) {
                                tempStr = "log10";
                            }
                            if ("sin".equals(line_spilt[4])) {
                                tempStr = "sin";
                            }
                            if ("cos".equals(line_spilt[4])) {
                                tempStr = "cos";
                            }
                            if ("tan".equals(line_spilt[4])) {
                                tempStr = "tan";
                            }
                            if ("Floor".equals(line_spilt[4]) || "floor".equals(line_spilt[4])) {
                                tempStr = "floor";
                            }
                            if ("ceil".equals(line_spilt[4])) {
                                tempStr = "ceil";
                            }
                            if ("sqrt".equals(line_spilt[4])) {
                                tempStr = "sqrt";
                            }
                            if ("rand".equals(line_spilt[4]) || "random".equals(line_spilt[4])) {
                                tempStr = "rand";
                            }


                            if(line_spilt.length <= 5){
                                this.writeCode(new String[]{"op",tempStr,line_spilt[1],line_spilt[3],"null"});
                            }else{
                                this.writeCode(new String[]{"op",tempStr,line_spilt[1],line_spilt[3],line_spilt[5]});
                            }








                        } else if(startString.equals("ramRead") || startString.equals("readRam")){


                            this.writeCode(new String[]{"read",line_spilt[1],line_spilt[3],line_spilt[4]});

                        }else if(startString.equals("ramWrite") || startString.equals("writeRam")){

                            // write val cell index
                            // ramWrite cell index = val
                            this.writeCode(new String[]{"write",line_spilt[4],line_spilt[1],line_spilt[2]});

                        }else {
                            //String tempStr = StringTools.Array2String(line_spilt," ",false,0,true);
                            if("\n".equals(temp1) || "\r".equals(temp1) || "".equals(temp1)){
                                continue;
                            }
                            this.writeCode(temp1);
                        }
                    }else{


                    }


                }

                this.lineNow++;


            }


            this.writeCode(new String[]{"end"});


            this.compiledCodeData = this.compiledCodeData.replaceAll("&#endLine#&",String.valueOf(this.compiledLineNow));
            this.compiledCodeData = this.compiledCodeData.replaceAll("&#scriptID#&",this.ScriptID);

            for (String key : functionCodeData.keySet()) {
                ScriptFunction SfObj3 = functionCodeData.get(key);
                String CodeData = SfObj3.CodeData;
                this.compiledCodeData = this.compiledCodeData + CodeData;
                int Save_compiledLineNow = this.compiledLineNow;
                this.compiledLineNow = this.compiledLineNow + SfObj3.compiledLine;
                for (String key2 : SfObj3.compiledLinesMap.keySet()) {
                    int data = SfObj3.compiledLinesMap.get(key2);
                    this.compiledCodeData = this.compiledCodeData.replaceAll(key2, String.valueOf(data + Save_compiledLineNow));
                    /*System.out.println(">>>" + key2);
                    System.out.println(String.valueOf(data + Save_compiledLineNow));*/
                }
                for (String key3 : SfObj3.globalLinesVars.keySet()) {
                    int data = SfObj3.globalLinesVars.get(key3);
                    this.compiledCodeData = this.compiledCodeData.replaceAll(key3, String.valueOf(data + Save_compiledLineNow));
                    /*System.out.println(">>>" + key2);
                    System.out.println(String.valueOf(data + Save_compiledLineNow));*/
                }
                this.writeCode(new String[]{"end"});
            }



            for (String key : globalLinesVars.keySet()) {
                if(globalLinesVars.get(key) != null){
                    this.compiledCodeData = this.compiledCodeData.replaceAll("&#GLine_" + key + "#&",String.valueOf(globalLinesVars.get(key)));
                    this.compiledCodeData = this.compiledCodeData.replaceAll("@=" + key + "",String.valueOf(globalLinesVars.get(key)));
                }



            }



            this.compiledCodeData = this.compiledCodeData + getScriptID(this.ScriptID);






        } catch (IndexOutOfBoundsException e){
            exception(file,"Incorrect code format: missing parameter", e);
            return;
        }catch (Exception e) {
            exception(file,"Unknow Java Error", e);
            return;
        }


    }

    public String getScriptID(){
        String tempStr = "";
        tempStr = tempStr + "\n";
        tempStr = tempStr + "set" + " " + "@SCRIPT-ID" + " " + "\"" + StringTools.getRamdomID() + "\"";
        tempStr = tempStr + "\n";




        return tempStr;
    }

    public String getScriptID(String ID){
        String tempStr = "";
        tempStr = tempStr + "\n";
        tempStr = tempStr + "set" + " " + "@SCRIPT-ID" + " " + "\"" + ID + "\"";
        tempStr = tempStr + "\n";




        return tempStr;
    }



    public void exception(File file,String str,Exception ex){
        this.isError = true;
        System.out.println("ERROR : An exception occurred! ");
        System.out.println("At File : " + file.getPath());
        System.out.println("At Line : " + String.valueOf(this.lineNow));
        System.out.println("At Compiled Code Line : " + String.valueOf(this.compiledLineNow));
        System.out.println("At Char : " + String.valueOf(this.charNow));
        if(ex != null){

            System.out.println("Java Exception : " + ex.toString());

        }


        System.out.println("Message : " + str);
        return;
    }

    public void writeCode(String str){
        if(this.inFunction){
            ScriptFunction SfObjWriteTemp = functionCodeData.get(this.FunctionNameNow);
            if(SfObjWriteTemp != null){
                SfObjWriteTemp.CodeData = SfObjWriteTemp.CodeData + str + "\n";
                SfObjWriteTemp.compiledLine++;

            }

        }else{
            this.compiledCodeData = this.compiledCodeData + str + "\n";
            compiledLineNow++;
        }

    }

    public void writeCode(String[] stra){
        String strs = "";
        for (String str:stra) {
            strs = strs + str + " ";

        }
        if(this.inFunction){
            ScriptFunction SfObjWriteTemp = functionCodeData.get(this.FunctionNameNow);
            if(SfObjWriteTemp != null){
                SfObjWriteTemp.CodeData = SfObjWriteTemp.CodeData + strs + "\n";
                SfObjWriteTemp.compiledLine++;

            }

        }else{
            this.compiledCodeData = this.compiledCodeData + strs + "\n";
            compiledLineNow++;
        }

    }

    public class writeCodeObjects{
        public String compiledCode = "";
        public int LineNow = -1;

        public writeCodeObjects(){

        }

        public writeCodeObjects(String p_compiledCode, int p_LineNow){
            this.compiledCode = p_compiledCode;
            this.LineNow = p_LineNow;

        }

    }

    public writeCodeObjects writeCodeStr(String compiledCode,int LineNow,String str){
        compiledCode = compiledCode + str + "\n";
        int LineNow2 = LineNow + 1;
        writeCodeObjects wco = new writeCodeObjects(compiledCode,LineNow2);
        return  wco;
    }

    public writeCodeObjects writeCodeStr(String compiledCode,int LineNow,String[] stra){
        String strs = "";
        for (String str:stra) {
            strs = strs + str + " ";

        }
        compiledCode = compiledCode + strs + "\n";
        int LineNow2 = LineNow + 1;
        writeCodeObjects wco = new writeCodeObjects(compiledCode,LineNow2);
        return  wco;
    }


    public void writeCodeLocal(String str){
        this.compiledCodeData = this.compiledCodeData + str + "\n";
        compiledLineNow++;

    }

    public void writeCodeLocal(String[] stra){
        String strs = "";
        for (String str:stra) {
            strs = strs + str + " ";

        }
        this.compiledCodeData = this.compiledCodeData + strs + "\n";
        compiledLineNow++;


    }

    public void writeCodeFunctionNow(String str){

        ScriptFunction SfObjWriteTemp = functionCodeData.get(this.FunctionNameNow);
        if(SfObjWriteTemp != null){
            SfObjWriteTemp.CodeData = SfObjWriteTemp.CodeData + str + "\n";
            SfObjWriteTemp.compiledLine++;

        }


    }

    public void writeCodeFunctionNow(String[] stra){
        String strs = "";
        for (String str:stra) {
            strs = strs + str + " ";

        }
        ScriptFunction SfObjWriteTemp = functionCodeData.get(this.FunctionNameNow);
        if(SfObjWriteTemp != null){
            SfObjWriteTemp.CodeData = SfObjWriteTemp.CodeData + strs + "\n";
            SfObjWriteTemp.compiledLine++;

        }


    }



    public void writeCodeFunction(String Name,String str){

        ScriptFunction SfObjWriteTemp = functionCodeData.get(Name);
        if(SfObjWriteTemp != null){
            SfObjWriteTemp.CodeData = SfObjWriteTemp.CodeData + str + "\n";
            SfObjWriteTemp.compiledLine++;

        }


    }

    public void writeCodeFunction(String Name,String[] stra){
        String strs = "";
        for (String str:stra) {
            strs = strs + str + " ";

        }
        ScriptFunction SfObjWriteTemp = functionCodeData.get(Name);
        if(SfObjWriteTemp != null){
            SfObjWriteTemp.CodeData = SfObjWriteTemp.CodeData + strs + "\n";
            SfObjWriteTemp.compiledLine++;

        }


    }



}
