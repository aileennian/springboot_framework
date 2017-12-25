package com.nian.utils.qrcode;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

public class EndeCodeUtil {
	protected static Logger logger = LogManager.getLogger(EndeCodeUtil.class);
	 
	  /** 
     * 生成图像 
     *  
     * @throws WriterException 
     * @throws IOException 
     */  
	
	/**
	 * 生成二维码图片
	 * @author nianxiaoling[14110310]
	 * @date 2015年12月12日
	 * @param filePath:图片保存路径
	 * @param fileName：图片文件
	 * @param json:内容
	 * @throws IOException
	 * @throws WriterException 
	 */
    public void Encode(Qrcode qrcode) throws IOException, WriterException {  
        String filePath = qrcode.getFilePath();//"D://";
        String fileName = qrcode.getFileName();
        //jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
//        JSONObject json =  new JSONObject();
//		String fileName = "zxing.png";          
//      JSONObject json = new JSONObject();  
//      json.put("zxing", "https://github.com/zxing/zxing/tree/zxing-3.0.0/javase/src/main/java/com/google/zxing");  
//      json.put("author", "shihy");  
// 		String content = JSONObject.valueToString(json);
        
//		json.putAll(qrcode.getContent());
        String content = qrcode.getContent(); 
        
        int width = qrcode.getWidth()==0?300: qrcode.getWidth(); // 图像宽度  
        int height = qrcode.getHeight()==0?300: qrcode.getHeight(); // 图像高度  
        
        String format = "png";// 图像类型  
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();  
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");  
        
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content,  BarcodeFormat.QR_CODE, width, height, hints);// 生成矩阵          
        
        Path path = FileSystems.getDefault().getPath(filePath, fileName);  
        MatrixToImageWriter.writeToPath(bitMatrix, format, path);// 输出图像  
        System.out.println("输出成功.");  
    }  
  
    
    
    
    /** 
     * 解析图像 6
     */  
    public String Decode(String filePathAndFileName) {  
        String filePath = filePathAndFileName ;//"D://诃德007_180_180.jpg"; 
        
        BufferedImage image;  
        try {  
            image = ImageIO.read(new File(filePath));  
            LuminanceSource source = new BufferedImageLuminanceSource(image);  
            Binarizer binarizer = new HybridBinarizer(source);  
            BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);  
            Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();  
            hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");  
            Result result = new MultiFormatReader().decode(binaryBitmap, hints);// 对图像进行解码  
            return result.getText();
           
//            Map<String, Object> content = JSONObject.parseObject(result.getText());  
//            
//            System.out.println("图片中内容：  ");  
//            System.out.println("author： " + content.get("author"));  
//            System.out.println("zxing：  " + content.get("zxing"));  	
//            System.out.println("图片中格式：  ");  
//            System.out.println("encode： " + result.getBarcodeFormat());  
//            
//            return content;
//            
            
        } catch (IOException e) {  
        	logger.error(e.getMessage(),e);
        } catch (NotFoundException e) {  
        	logger.error(e.getMessage(),e);
        }  
        return null;
    }  
    
	public static void main(String[] args) throws IOException, WriterException {
		// TODO Auto-generated method stub
		EndeCodeUtil util = new EndeCodeUtil();
		String content =  util.Decode("D://诃德007_180_180.jpg");
		System.out.println(content.toString());
		
		Qrcode qr = new Qrcode();
		qr.setFilePath("d://");
		qr.setFileName("name.png");
		qr.setContent("http://tpssit.cnsuning.com/tps/qr/index.htm?placeNum=1&sourceNum=1&userId=0&reURL=http://weixin.qq.com/r/W0WWjiHEp3zYrWXt9xBJ");
		
		
		
		util.Encode(qr);
		
	}
}
