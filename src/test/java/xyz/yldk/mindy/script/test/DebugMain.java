package xyz.yldk.mindy.script.test;

import xyz.yldk.mindy.script.compiler.CodeParser;
import xyz.yldk.mindy.script.compiler.PixelParser;
import xyz.yldk.mindy.script.tools.StringTools;
import xyz.yldk.mindy.script.compiler.CodeParser;
import xyz.yldk.mindy.script.tools.StringTools;

import java.io.File;



public class DebugMain {
    public static void main(String[] args) {


        File f = new File("demo.ms");
        System.out.print("File Exists : ");
        System.out.print(f.exists());
        System.out.print("\n");

        System.out.println("Hello world!");


        System.out.println("Loading Parser ......");

        CodeParser cp = new CodeParser();
        cp.debugMode = true;
        cp.run(f);

        /*System.out.println(cp.compiledCodeData);
        PixelParser pp = new PixelParser("E:\\Users\\zryyo\\Pictures\\yldk-176.176.jpg","E:\\Users\\zryyo\\Desktop\\test66666.zip");
        pp.runPixel();*/

        //System.out.println(StringTools.getRamdomID());




        /*for (String k:cp.functionCodeData.keySet()
             ) {
            System.out.println(k);

        }*/

        //System.out.println(cp.functionCodeData.get("test").CodeData);




    }
}

