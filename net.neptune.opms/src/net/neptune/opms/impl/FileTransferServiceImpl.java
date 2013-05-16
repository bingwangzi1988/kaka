package net.neptune.opms.impl;  

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

import javax.jws.WebService;

import net.neptune.opms.FileTransferService;
import net.neptune.opms.model.FileTransferException;
import net.neptune.opms.model.MyFile;
import net.neptune.opms.util.OpmsConstants;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebService(endpointInterface="net.neptune.opms.FileTransferService") 
public class FileTransferServiceImpl implements FileTransferService {  
 
	Logger logger = LoggerFactory.getLogger(FileTransferServiceImpl.class);  
	
    public void uploadFile(MyFile myFile) throws FileTransferException {  
        OutputStream os = null;  
        logger.info("FilePath: {}", OpmsConstants.PROJECT_BUNDLE_PATH + myFile.getServerFile());
        try {  
            if (myFile.getPosition() != 0) {  
                os = FileUtils.openOutputStream(new File(OpmsConstants.PROJECT_BUNDLE_PATH + myFile.getServerFile()), true);  
            } else {  
                os = FileUtils.openOutputStream(new File(OpmsConstants.PROJECT_BUNDLE_PATH + myFile.getServerFile()), false);  
            }  
            os.write(myFile.getBytes());  
        } catch(IOException e) {  
            throw new FileTransferException(e.getMessage(), e);  
        } finally {  
            IOUtils.closeQuietly(os);  
        }  
    }  
 
    public MyFile downloadFile(MyFile myFile) throws FileTransferException {  
        InputStream is = null;  
          
        try {  
            is = new FileInputStream(myFile.getServerFile());  
            is.skip(myFile.getPosition());  
            byte[] bytes = new byte[1024 * 1024];  
            int size = is.read(bytes);  
            if (size > 0) {  
                byte[] fixedBytes = Arrays.copyOfRange(bytes, 0, size);  
                myFile.setBytes(fixedBytes);  
            } else {  
                myFile.setBytes(new byte[0]);  
            }  
        } catch(IOException e) {  
            throw new FileTransferException(e.getMessage(), e);  
        } finally {  
            IOUtils.closeQuietly(is);  
        }  
        return myFile;  
    }  
}  