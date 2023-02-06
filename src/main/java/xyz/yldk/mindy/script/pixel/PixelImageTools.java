package xyz.yldk.mindy.script.pixel;

public class PixelImageTools {
    public static int[] int2RGBArray(int intRGBValue){
        int rgb[] = new int[]{0,0,0};
        rgb[0] = (intRGBValue &  0xff0000) >> 16;
        rgb[1] = (intRGBValue &  0xff00) >> 8;
        rgb[2] = (intRGBValue &  0xff);
        return  rgb;
    }

}
