package xyz.yldk.mindy.script;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.NativeJSON;
import org.mozilla.javascript.Scriptable;
import xyz.yldk.mindy.script.compiler.CodeParser;
import xyz.yldk.mindy.script.tools.StringTools;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.File;

public class Main {
    public static void main(String[] args) {

        if(args.length != 1){
            System.out.println("Usage: java -jar MindyScript.jar <FileName> ");
        }else{
            File f = new File(args[0]);
            System.out.println("Compiling Code ......");
            CodeParser cp = new CodeParser();
            cp.debugMode = false;
            cp.run(f);
            if(cp.isError){
                System.out.println("Compilation failed! ");
                System.out.println("Please check your code! ");
                System.out.println("\n\n\n");
                System.exit(1);


            }else{
                System.out.println("Compilation completed!");
                System.out.println("Your logical code: \n\n\n");
                System.out.println(cp.compiledCodeData);
                System.out.println("\n\n\n");
                System.exit(0);

            }



        }


        /*File f = new File("demo.ms");
        System.out.print("File Exists : ");
        System.out.print(f.exists());
        System.out.print("\n");

        System.out.println("Hello world!");


        System.out.println("Loading Parser ......");

        CodeParser cp = new CodeParser();
        cp.debugMode = true;
        cp.run(f);

        System.out.println(cp.compiledCodeData);

        System.out.println(StringTools.getRamdomID());*/





    }
}
