package net.neptune.opms;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import net.neptune.opms.model.FileTransferException;
import net.neptune.opms.model.MyFile;

import org.apache.commons.io.IOUtils;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

public class FileTransferClient {

	public static FileTransferService getFileTransferService(final String ip, final String port) {
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();   
		//注册WebService接口   
		factory.setServiceClass(FileTransferService.class);   
		//设置WebService地址   
		StringBuffer addressBuf = new StringBuffer();
		addressBuf.append("http://").append(ip).append(":").append(port).append("/FileTransferService");
		factory.setAddress(addressBuf.toString());        
		FileTransferService fileTransferService = (FileTransferService)factory.create();   
		
		return fileTransferService;
	}
	
	public static void uploadFile(final String ip, final String port, File srcFile, String destPath) throws Exception {
		InputStream is = null;
		try {
			MyFile myFile = new MyFile();
			is = new FileInputStream(srcFile);
			byte[] bytes = new byte[1024 * 1024];
			while (true) {
				int size = is.read(bytes);
				if (size <= 0) {
					break;
				}

				byte[] fixedBytes = Arrays.copyOfRange(bytes, 0, size);
				myFile.setClientFile(srcFile.getAbsolutePath());
				myFile.setServerFile(destPath);
				myFile.setBytes(fixedBytes);

				getFileTransferService(ip, port).uploadFile(myFile);

				myFile.setPosition(myFile.getPosition() + fixedBytes.length);
			}
		} catch (IOException e) {
			throw new FileTransferException(e.getMessage(), e);
		} finally {
			IOUtils.closeQuietly(is);
		}
	}
	
}