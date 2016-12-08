package java.com.qc.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Color;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

/**
 * ��ά������
 * @author Administrator
 *
 */
public class BitmapQRUtils {

	public static Bitmap generateQRCode(String content){
		try {
			QRCodeWriter writer = new QRCodeWriter();
			BitMatrix matrix = writer.encode(content, BarcodeFormat.QR_CODE,
					500, 500);
			return bitMatrix2Bitmap(matrix);
		} catch (WriterException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static Bitmap bitMatrix2Bitmap(BitMatrix matrix) {  
        int w = matrix.getWidth();  
        int h = matrix.getHeight();  
        int startX = 0;
        int startY = 0;
        
        int[] rawData = new int[w * h];  
        for (int i = 0; i < w; i++) {  
            for (int j = 0; j < h; j++) {  
                int color = Color.WHITE;  
                if (matrix.get(i, j)) {  
                    color = Color.BLACK; 
                    startX = w;
                    startY = h;
                }  
                rawData[i + (j * w)] = color;  
            }  
        }  
        Bitmap bitmap = Bitmap.createBitmap(w, h, Config.RGB_565);  
        bitmap.setPixels(rawData, 0, w, 0, 0, w, h);  
        
//     // �����м�Ķ�ά�����򣬼���padding����
//        if (startX <= PADDING_SIZE_MIN) 
//        	return bitmap;
//        
//        int x1 = startX - PADDING_SIZE_MIN;
//        int y1 = startY - PADDING_SIZE_MIN;
//        if (x1 < 0 || y1 < 0) 
//        	return bitmap;
// 
//        int w1 = w - x1 * 2;
//        int h1 = h - y1 * 2;
 
        Bitmap bitmapQR = Bitmap.createBitmap(bitmap, 40, 40, 420, 420);
        return bitmapQR;
    }  
	
}
