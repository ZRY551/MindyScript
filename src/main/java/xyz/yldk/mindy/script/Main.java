package xyz.yldk.mindy.script;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.NativeJSON;
import org.mozilla.javascript.Scriptable;
import xyz.yldk.mindy.script.compiler.CodeParser;
import xyz.yldk.mindy.script.tools.StringTools;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.annotation.Retention;

public class Main {
    public static void main(String[] args) {
        try {

            if (args.length <= 1) {
                System.out.println("Usage: java -jar MindyScript.jar <CompilerType> <FileName> [OutFileName]");
                System.out.println("Compiler Type: script / pixel");
            } else if (args.length == 2) {
                if ("script".equals(args[0].toLowerCase())) {
                    File f = new File(args[1]);
                    System.out.println("Compiling Code ......");
                    CodeParser cp = new CodeParser();
                    cp.debugMode = false;
                    cp.run(f);
                    if (cp.isError) {
                        System.out.println("Compilation failed! ");
                        System.out.println("Please check your code! ");
                        System.out.println("\n\n\n");
                        System.exit(1);


                    } else {
                        System.out.println("Compilation completed!");
                        System.out.println("Your logical code: \n\n\n");
                        System.out.println(cp.compiledCodeData);
                        System.out.println("\n\n\n");
                        System.exit(0);

                    }

                } else if ("pixel".equals(args[0].toLowerCase())) {
                    System.out.println("ERROR: Pixel image mode must contain output file name!");
                    return;
                } else {
                    System.out.println("ERROR: Unknown Compiler Type.");
                    return;
                }


            } else {
                if ("script".equals(args[0].toLowerCase())) {
                    File f = new File(args[1]);
                    File out_f = new File(args[2]);
                    System.out.println("Compiling Code ......");
                    CodeParser cp = new CodeParser();
                    cp.debugMode = false;
                    cp.run(f);
                    if (cp.isError) {
                        System.out.println("Compilation failed! ");
                        System.out.println("Please check your code! ");
                        System.out.println("\n\n\n");
                        System.exit(1);


                    } else {
                        System.out.println("Compilation completed!");
                        System.out.println("Your logical code -> " + out_f.getPath() + " \n\n\n");
                        out_f.createNewFile();
                        FileWriter writer = new FileWriter(out_f);
                        BufferedWriter out = new BufferedWriter(writer);
                        //System.out.println(cp.compiledCodeData);

                        out.write(cp.compiledCodeData);
                        out.close();

                        System.out.println("\n\n\n");
                        System.exit(0);


                    }

                } else if ("pixel".equals(args[0].toLowerCase())) {
                    System.out.println("BTEA: Pixel Mode is beta and have so many bug not fixed! ");
                    return;


                } else {
                    System.out.println("ERROR: Unknow Compiler Type.");
                    return;
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


        }catch (Exception ex){
            System.out.println("ERROR: A serious error has occurred in the program!");
            System.out.println("ERROR: " + ex.toString());
            ex.printStackTrace();
        }
    }
}
