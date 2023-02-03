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

    public String codeData = "";


    public String compiledCodeData = "";



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
                    if (startString.startsWith("/*")) {
                        this.note = true;
                        continue;

                    }else {
                        if (startString.startsWith("*/")) {
                            this.note = false;
                            continue;

                        }
                    }
                    if(!note) {
                        if (startString.startsWith("//")) {
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
                            this.writeCode(new String[]{"set", line_spilt[1], String.valueOf(this.compiledLineNow)});


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


            }


            this.writeCode(new String[]{"end"});


            this.compiledCodeData = this.compiledCodeData.replaceAll("&#endLine#&",String.valueOf(this.compiledLineNow));
            this.compiledCodeData = this.compiledCodeData + getScriptID();






        } catch (Exception e) {
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



    public void exception(File file,String str,Exception ex){
        this.isError = true;
        System.out.println("ERROR : An exception occurred! ");
        System.out.println("At File : " + file.getPath());
        System.out.println("At Line : " + String.valueOf(lineNow));
        System.out.println("At Char : " + String.valueOf(charNow));
        if(ex != null){

            System.out.println("Java Exception : " + ex.toString());

        }


        System.out.println("Message : " + str);
        return;
    }

    public void writeCode(String str){
        this.compiledCodeData = this.compiledCodeData + str + "\n";
        compiledLineNow++;
    }

    public void writeCode(String[] stra){
        String strs = "";
        for (String str:stra) {
            strs = strs + str + " ";

        }
        this.compiledCodeData = this.compiledCodeData + strs + "\n";
        compiledLineNow++;
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




}
