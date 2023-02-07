package xyz.yldk.mindy.script.compiler;

import xyz.yldk.mindy.script.objects.ScriptFunction;
import xyz.yldk.mindy.script.pixel.PixelImageTools;
import xyz.yldk.mindy.script.tools.StringTools;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class PixelParser extends CodeParser{


    public File ImageFile;
    public File OutPutFile;
    public String p_ImageFilePath = "";
    public String p_OutPutFilePath = "";



    public int cpuNumNow = 0;
    public int displayNumNow = 1;


    public int c_CodeLineCount = 0;


    public HashMap<Integer,String> codeMap = new HashMap<>();



    public PixelParser(String ImageFilePath,String OutPutFilePath){
        this.p_ImageFilePath = ImageFilePath;
        this.p_OutPutFilePath = OutPutFilePath;

        this.ImageFile = new File(this.p_ImageFilePath);
        this.OutPutFile = new File(this.p_OutPutFilePath);






    }

    public void runPixel(){
        try {
            this.OutPutFile.createNewFile();
            ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(this.OutPutFile));


            if (!ImageFile.exists()) {
                exception(ImageFile, "Image file does not exist! Unable to get image content!", null);
                return;
            }

            BufferedImage imageReaderObj = ImageIO.read(ImageFile);

            int image_width = imageReaderObj.getWidth();
            int image_high = imageReaderObj.getHeight();

            int fW = image_width - 1;
            int fH = image_high -1;

            this.c_CodeLineCount = 0;
            String CodeData = "";

            //CodeData = this.writeCodePixel(CodeData,new String[]{"draw","clear","0", "0","0","0","0","0"});






            for (int w = 0; w < image_width; w++) {

                for (int h = 0; h < image_high; h++) {
                    Color color = new Color(imageReaderObj.getRGB(w,h));
                    int R = color.getRed();
                    int G = color.getGreen();
                    int B = color.getBlue();
                    int A = color.getAlpha();




                    CodeData = this.writeCodePixel(CodeData,new String[]{"draw","color", String.valueOf(R) , String.valueOf(G) ,String.valueOf(B) ,String.valueOf(A),"0","0"});
                    CodeData = this.writeCodePixel(CodeData,new String[]{"draw","rect",String.valueOf(fW), String.valueOf(fH) ,"1", "1","0","0"});


                    this.c_CodeLineCount = this.c_CodeLineCount + 2;



                    if(this.c_CodeLineCount >= 930){
                        //System.out.println(this.c_CodeLineCount);
                        this.cpuNumNow++;
                        CodeData = this.writeCodePixel(CodeData,new String[]{"drawflush","display" + String.valueOf(displayNumNow)});
                        this.codeMap.put(this.cpuNumNow,CodeData);
                        CodeData = "";
                        this.c_CodeLineCount = 0;



                    }




                    fH --;
                }
                fH = image_high -1;
                fW = fW - 1;



            }


            int cpuNum = this.cpuNumNow;
            int cpuZero = 0;
            for ( Integer Key : codeMap.keySet()){
                String data = codeMap.get(Key);
                if(data != null){
                    cpuZero++;
                    byte[] byteData = data.getBytes();
                    zipOutputStream.putNextEntry(new ZipEntry("CPU_" + String.valueOf(cpuZero) + ".mlog.txt"));
                    zipOutputStream.write(byteData);
                    zipOutputStream.closeEntry();

                }
            }









        }catch (Exception ex){
            super.exception(OutPutFile,"Pixel Error (Java): " + ex.toString(),ex);
        }

    }



    public String writeCodePixel(String str1,String[] stra){
        String strs = "";
        for (String str:stra) {
            strs = strs + str + " ";


        }
        return str1 + strs + "\n";

    }

    public String writeCodePixel(String str1,String str2){

        return str1 + str2 + "\n";



    }




}
