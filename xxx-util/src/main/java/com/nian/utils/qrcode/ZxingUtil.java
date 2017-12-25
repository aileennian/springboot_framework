package com.nian.utils.qrcode;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.OutputStream;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
  

public class ZxingUtil {
	private static final String CHARSET = "utf-8";
	private static final String FORMAT_NAME = "jpg";
	// 二维码尺寸
	private static final int QRCODE_SIZE = 200;
	// LOGO宽度
	private static final int WIDTH = 40;
	// LOGO高度
	private static final int HEIGHT = 40;

	private static BufferedImage createImage(String content, String imgPath,
			boolean needCompress) throws Exception {
		Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
		// hints.put(EncodeHintType.MARGIN, 1);
		BitMatrix bitMatrix = new MultiFormatWriter().encode(content,
				BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE, hints);
		int width = bitMatrix.getWidth();
		int height = bitMatrix.getHeight();
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000
						: 0xFFFFFFFF);
			}
		}
		if (imgPath == null || "".equals(imgPath)) {
			return image;
		}
		// 插入图片
		ZxingUtil.insertImage(image, imgPath, needCompress);
		return image;
	}

	/**
	 * 插入LOGO
	 * 
	 * @param source
	 *            二维码图片
	 * @param imgPath
	 *            LOGO图片地址
	 * @param needCompress
	 *            是否压缩
	 * @throws Exception
	 */
	private static void insertImage(BufferedImage source, String imgPath,
			boolean needCompress) throws Exception {
		File file = new File(imgPath);
		if (!file.exists()) {
			System.err.println("" + imgPath + "   该文件不存在！");
			return;
		}
		Image src = ImageIO.read(new File(imgPath));
		int width = src.getWidth(null);
		int height = src.getHeight(null);
		if (needCompress) { // 压缩LOGO
			if (width > WIDTH) {
				width = WIDTH;
			}
			if (height > HEIGHT) {
				height = HEIGHT;
			}
			Image image = src.getScaledInstance(width, height,
					Image.SCALE_SMOOTH);
			BufferedImage tag = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics g = tag.getGraphics();
			g.drawImage(image, 0, 0, null); // 绘制缩小后的图
			g.dispose();
			src = image;
		}
		// 插入LOGO
		Graphics2D graph = source.createGraphics();
		int x = (QRCODE_SIZE - width) / 2;
		int y = (QRCODE_SIZE - height) / 2;
		graph.drawImage(src, x, y, width, height, null);
		Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
		graph.setStroke(new BasicStroke(3f));
		graph.draw(shape);
		graph.dispose();
	}

	

	
	/**
	 * 生成二维码，同时将图片保存在指定位置
	 * @author nianxiaoling[14110310]
	 * @date 2015年12月12日
	 * @param qrcode
	 * @return
	 * @throws Exception
	 */
	public static String encode(Qrcode qrcode) throws Exception {		 
		String destPath = qrcode.getFilePath();
		String destFileName = qrcode.getFileName();
		String logoImgPath= qrcode.getLogoImgPath();
		String content  = qrcode.getContent();
		boolean needCompress = true;
		
		
		mkdirs(destPath);
		
		String destFileFullPath="";
		if (destPath.endsWith("/")){
			destFileFullPath = destPath + destFileName;
		}else{
			destFileFullPath = destPath + "/" + destFileName;
		}
		
		BufferedImage image = ZxingUtil.createImage(content, logoImgPath, needCompress);
		ImageIO.write(image, FORMAT_NAME, new File(destFileFullPath));
		return destFileFullPath;
	}
	
	/**
	 * 生成二维码，将图片输出OutputStream
	 * @author nianxiaoling[14110310]
	 * @date 2015年12月12日
	 * @param qrcode
	 * @param output
	 * @return
	 * @throws Exception
	 */
	public static void encode(Qrcode qrcode,OutputStream output) throws Exception {
		String logoImgPath= qrcode.getLogoImgPath();
		String content  = qrcode.getContent();
		boolean needCompress = false;
		
		BufferedImage image = ZxingUtil.createImage(content, logoImgPath,
				needCompress);
		ImageIO.write(image, FORMAT_NAME, output);
	}
	
	
	/**
	 * 当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir．(mkdir如果父目录不存在则会抛出异常)
	 * 
	 * @author lanyuan Email: mmm333zzz520@163.com
	 * @date 2013-12-11 上午10:16:36
	 * @param destPath
	 *            存放目录
	 */
	public static void mkdirs(String destPath) {
		File file = new File(destPath);
		// 当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir．(mkdir如果父目录不存在则会抛出异常)
		if (!file.exists() && !file.isDirectory()) {
			file.mkdirs();
		}
	}



	
	/**
	 * 解析二维码
	 * 
	 * @param file
	 *            二维码图片
	 * @return
	 * @throws Exception
	 */
	public static String decode(String filePath) throws Exception {
		File file = new File(filePath);
		if (!file.exists()) {
			return null;
		}
		
		
		BufferedImage image;
		image = ImageIO.read(file);
		if (image == null) {
			return null;
		}
		BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(image);
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
		Result result;
		Hashtable<DecodeHintType, Object> hints = new Hashtable<DecodeHintType, Object>();
		hints.put(DecodeHintType.CHARACTER_SET, CHARSET);
		result = new MultiFormatReader().decode(bitmap, hints);
		String resultStr = result.getText();
		return resultStr;
	}



	public static void main(String[] args) throws Exception {
		String decodeText = ZxingUtil.decode("d:/qrcode.jpg");
		System.out.println("decodeText = " + decodeText);
		

		
		//http://weixin.qq.com/r/cUMaApnEi8T0rd1h9xZj
		//String content = "http://tpssit.cnsuning.com/tps/qr/index.htm?placeNum=1&sourceNum=1&userId=0&"+decodeText;
		//String content =decodeText;
		String content = "http://tpssit.cnsuning.com/tps/qr/index.htm?placeNum=1&sourceNum=1&userId=0&http://tpssit.cnsuning.com";

		Qrcode qrcode = new Qrcode();
		qrcode.setContent(content);
		qrcode.setFilePath("d:/");
		
		String destFileName = "destFileName.jpg";  
		qrcode.setFileName(destFileName);
		qrcode.setLogoImgPath("D:/logo.jpg");
		
		String destFileFullPath = ZxingUtil.encode(qrcode);
		System.out.println("destFileFullPath = " + destFileFullPath);

		
		Thread.sleep(100);
		
		decodeText = ZxingUtil.decode(destFileFullPath);
		System.out.println("1111decodeText = " + decodeText);
		
	}
	
	
}
