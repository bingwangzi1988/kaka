package org.eredlab.g4.rif.resource.handler;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.zip.GZIPOutputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eredlab.g4.rif.resource.HandleResourceException;
import org.eredlab.g4.rif.resource.HttpHolder;
import org.eredlab.g4.rif.resource.Resource;
import org.eredlab.g4.rif.resource.ResourceHandler;

/**
 * CSSMinResourceHandler
 * 
 * @author HuangYunHui|XiongChun
 * @since 2009-08-10
 */
public class GZipResourceHandler implements ResourceHandler{
	
	private final Log logger = LogFactory.getLog( this.getClass() );

	public void handle(Resource pResource) throws HandleResourceException {
		HttpServletRequest request = HttpHolder.getRequest();		
			logger.info("���ڶ���Դ:" + pResource.getUri() + "����gzipѹ��...");
			int before = pResource.getTreatedData().length;
			try {
				gzip(pResource);
				pResource.setGzip(true);				
			} catch (Exception e) {
				logger.warn("ѹ����Դ:" + pResource.getUri() + "ʧ�ܣ�δ��������ѹ��!");
				return;
			}
			int after = pResource.getTreatedData().length;
			logger.info("ѹ����Դ:" + pResource.getUri() + "�ɹ�." + getGZIPDesc(before, after) );
	}
	
	
	private String getGZIPDesc(int before, int after){
		StringBuffer sb = new StringBuffer();
		sb.append("ѹ��ǰ: ").append(getSize(before)).append(",ѹ����: ").append( getSize(after) ).append(".") ;
		sb.append("ѹ�������ǣ�").append(getPrecent(before, after));
		return sb.toString();
	}
	private static String getPrecent(int before, int after){
		DecimalFormat df=(DecimalFormat)NumberFormat.getInstance();		
		df.applyPattern("#.0%");
		BigDecimal bigBeore = BigDecimal.valueOf(before);
		BigDecimal bigAfter = BigDecimal.valueOf(after);
		return df.format( bigBeore.divide(bigAfter, 2, BigDecimal.ROUND_UP).doubleValue() );
	}
	 
	private static String getSize(int pLen){
		DecimalFormat df=(DecimalFormat)NumberFormat.getInstance();
		df.applyPattern("#.0");
		
		BigDecimal bigLen = BigDecimal.valueOf(pLen);
		BigDecimal bigK = BigDecimal.valueOf(1024);
		return df.format(bigLen.divide(bigK,  2, BigDecimal.ROUND_UP).doubleValue()) + " KB";
	}

	private void gzip(Resource pResource)throws Exception{
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		GZIPOutputStream outputStream = new GZIPOutputStream( out );
		outputStream.write(pResource.getTreatedData());
		outputStream.finish();
		outputStream.flush();
		outputStream.close();
		pResource.setTreatedData(out.toByteArray());	
		out.close();
	}
	
	public static void main(String[] args){
				
	}

}
