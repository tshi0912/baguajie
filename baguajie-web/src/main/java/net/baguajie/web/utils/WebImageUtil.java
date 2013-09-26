package net.baguajie.web.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import net.baguajie.constants.ApplicationConfig;
import net.baguajie.vo.ImageReadyVo;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.baidu.bae.api.util.BaeEnv;

@Component
public class WebImageUtil implements ApplicationContextAware {
	
	private Logger logger = Logger.getLogger(WebImageUtil.class);
	
	private ApplicationContext ac;
	
	public WebImageUtil(){
//		System.setProperty("http.proxyHost", ApplicationConfig.httpProxyHost);
//		System.setProperty("http.proxyPort", ApplicationConfig.httpProxyPort + "");
	}
	
	public static String getImageUrl(String resId){
		return new StringBuilder()
			.append(ApplicationConfig.base)
			.append(ApplicationConfig.imageRefer)
			.append("/")
			.append(resId)
			.toString();
	}
	
	public static String getAvatarUrl(String resId){
		return new StringBuilder()
			.append(ApplicationConfig.base)
			.append(ApplicationConfig.avatarRefer)
			.append("/")
			.append(resId)
			.toString();
	}
	
	public ImageReadyVo prepareImageFromUrl(String url) throws Exception{
		if(url==null) return null;
		
		String addr = StringUtils.trimWhitespace(url);
		logger.info("image url:" + addr);
		URL u = null;
		BufferedImage img = null;
		File file = null;
		String ext = null;
		String separator = "/";
		if(addr.indexOf("http")!=-1){
			logger.info("it's a web link, we need crawle down the image");
			//SocketAddress address = new InetSocketAddress(
			//		ApplicationConfig.httpProxyHost, ApplicationConfig.httpProxyPort);
			//create an HTTP proxy using the above SocketAddress.
            //Proxy proxy = new Proxy(Proxy.Type.HTTP, address);
			u = new URL(url);
            URLConnection conn = u.openConnection();
            //set "user-agent" property to make sure the response code is not 404
            conn.setRequestProperty("User-Agent","Mozilla/5.0 (compatible)");
            conn.setRequestProperty("Accept","*/*");
            InputStream is = conn.getInputStream();
            ImageInputStream iis = ImageIO.createImageInputStream(is);
			// Find all image readers that recognize the image format
			Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
			// Use the first reader
			ImageReader reader = (ImageReader) iter.next();
			reader.setInput(iis, true);
            img = reader.read(0);
            ext = reader.getFormatName();
			// if can not determine the format name, use "jpg" as default
			if(ext==null){
				ext = "jpg";
			}
			String fileName = System.currentTimeMillis()+"." + ext;
			file = getTempFolder();
			if(file.isDirectory()){
				file = new File(file.getPath() + 
						File.separator + 
						fileName);
				logger.info("we are going to store the download image into " + file.getPath());
			}
			if(!"gif".equalsIgnoreCase(ext)){
				ImageIO.write(img, ext, file);
			}else{
				logger.info("woo..., it's a gif, we need download it piece by piece");
				OutputStream os = null;
				try{
					os = new FileOutputStream(file);
					byte[] buffer =new byte[512];
					int len;
					// reopen connection for image
					conn = u.openConnection();
					is = conn.getInputStream();
					while((len =is.read(buffer))!=-1){
						os.write(buffer,0,len);
					}
				}finally{
					if(is!=null)
						is.close();
					if(os!=null)
						os.close();
				}
			}
			addr = file.getPath();
			separator = File.separator;
			logger.info("complete download it");
		}
		int idx = addr.lastIndexOf(separator);
//		res = ac.getResource(ApplicationConfig.uploadTempRepository + "/"
//				+ addr.substring(idx + 1));
		
		file = getFile(addr.substring(idx + 1));
		if(file != null && ext == null){
			ext = FilenameUtils.getExtension(file.getName());
		}
		logger.info("the image file on temp folder " + file.getPath());
		img = ImageIO.read(file);
		ImageReadyVo ir = new ImageReadyVo();
		ir.setFile(file);
		ir.setExt(ext);
		ir.setOrgSize(new Integer[] { img.getHeight(), img.getWidth() });
		logger.info("generate ImageReadyVo" + ir);
		return ir;
	}
	
	// Returns the format of the image in the file 'f'.
	// Returns null if the format is not known.
	public String getFormatInFile(File f) {
	    return getFormatName(f);
	}

	// Returns the format of the image in the input stream 'is'.
	// Returns null if the format is not known.
	public String getFormatFromStream(InputStream is) {
	    return getFormatName(is);
	}
	
	// Returns the format name of the image in the object 'o'.
	// 'o' can be either a File or InputStream object.
	// Returns null if the format is not known.
	private String getFormatName(Object o) {
		try {
			// Create an image input stream on the image
			ImageInputStream iis = ImageIO.createImageInputStream(o);

			// Find all image readers that recognize the image format
			Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
			if (!iter.hasNext()) {
				// No readers found
				return null;
			}

			// Use the first reader
			ImageReader reader = (ImageReader) iter.next();

			// Close stream
			iis.close();

			// Return the format name
			return reader.getFormatName();
		} catch (IOException e) {

		}
		// The image could not be read
		return null;
	}
	
	public File getFile(String path){
		String tmpfsPath = BaeEnv.getTmpfsPath();
		File file = new File(tmpfsPath + File.separator + path);
		return file;
	}
	
	public File getTempFolder(){
		File file = new File(BaeEnv.getTmpfsPath());
		return file;
	}

	@Override
	public void setApplicationContext(ApplicationContext ac)
			throws BeansException {
		this.ac = ac;
	}
}
