package net.neptune.opms;  
 
import javax.jws.WebMethod;
import javax.jws.WebService;

import net.neptune.opms.model.FileTransferException;
import net.neptune.opms.model.MyFile;
 
@WebService 
public interface FileTransferService {  
      
    @WebMethod 
    void uploadFile(MyFile myFile) throws FileTransferException;  
 
    @WebMethod 
    MyFile downloadFile(MyFile myFile) throws FileTransferException;  

} 