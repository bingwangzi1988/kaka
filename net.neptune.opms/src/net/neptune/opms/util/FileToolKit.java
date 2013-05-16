package net.neptune.opms.util;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import org.apache.commons.io.FileUtils; 
import org.apache.commons.io.filefilter.AndFileFilter;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.NotFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;

/**
 * 对文件操作的工具类(复制,删除,移动) 需引入包commons-io.jar
 * 
 * @author bingwangzi 2012-11-19
 *
 */
public class FileToolKit {
	
	/**  
	  * 复制文件或者目录,复制前后文件完全一样。如果目标目录不存在，则会创建。如果有相同的目标文件，则会被覆盖 
	  * 
	  * @param srcFilePath  
	  *            源文件路径
	  * @param distFolder  
	  *            目标文件夹  
	  * @IOException 当操作发生异常时抛出  
	  */ 
	public static void copyFile(String srcFilePath, String distFolder)  throws IOException {  
		File srcFile = new File(srcFilePath); 
		File distFile = new File(distFolder);  
		if (srcFile.isDirectory()) { // 目录时 
			FileUtils.copyDirectoryToDirectory(srcFile, distFile);  
		} else if (srcFile.isFile()) { // 文件时  
			// FileUtils.copyFileToDirectory(srcFile, distFile, true);  // true表示目标文件的日期和源文件的一样
			FileUtils.copyFileToDirectory(srcFile, distFile);  
		}  
	}
		 
	/**  
	 * 删除一个文件或者目录，如果为目录，则其包含的文件或目录均被删除  
	 * 
	 * @param targetPath  
	 *            文件或者目录路径  
	 * @IOException 当操作发生异常时抛出  
	 */ 
	public static void deleteFile(String targetPath) throws IOException {  
		File targetFile = new File(targetPath);  
		if (targetFile.isDirectory()) {  
			FileUtils.deleteDirectory(targetFile);  
		} else if (targetFile.isFile()) {  
			targetFile.delete();  
		}  
	}
	
	/**  
	 * 移动文件或者目录,移动前后文件完全一样,如果目标文件夹不存在则创建。 
	 * 
	 * @param srcFilePath  
	 *            源文件路径  
	 * @param distFolder  
	 *            目标文件夹  
	 * @IOException 当操作发生异常时抛出 
	 */ 
	public static void moveFile(String srcFilePath, String distFolder) throws IOException {  
		File srcFile = new File(srcFilePath);  
		File distFile = new File(distFolder);  
		if (srcFile.isDirectory()) {  
			FileUtils.moveDirectoryToDirectory(srcFile, distFile, true);  // true表示如果目标目录不存在，则创建，如为false，则目标目录不存在时报错
		} else if (srcFile.isFile()) {  
			FileUtils.moveFileToDirectory(srcFile, distFile, true);  // true表示如果目标目录不存在，则创建，如为false，则目标目录不存在时报错
		} 
	}
	
	/**  
	 * 重命名文件或文件夹 
	 * 
	 * @param srcFilePath  
	 * 				源文件路径      
	 * @param newFileName  
	 * 				重命名 
	 * @return 操作成功标识 
	 */
	public static boolean renameFile(String srcFilePath, String newFileName) {  
		// 格式化路径 
		String newFilePath = StringToolkit.formatPath(StringToolkit.getParentPath(srcFilePath) + "/" + newFileName);  
		File srcFile = new File(srcFilePath);  
		File newFile = new File(newFilePath);  
		return srcFile.renameTo(newFile);  
	}
	
	/**  
	 * 读取文件或者目录的大小 
	 * 
	 * @param distFilePath  
	 *            目标文件或者文件夹  
	 * @return 文件或者目录的大小，如果获取失败，则返回-1  
	 */ 
	public static long genFileSize(String distFilePath) { 
		File distFile = new File(distFilePath);  
		if (distFile.isFile()) {  
			return distFile.length();  
		} else if (distFile.isDirectory()) {  
			return FileUtils.sizeOfDirectory(distFile); 
		}  
		return -1L;  
	} 
	
	/**  
	 * 判断一个文件是否存在  
	 * 
	 * @param filePath  
	 *            文件路径  
	 * @return 存在返回true，否则返回false  
	 */
	public static boolean isExist(String filePath) {  
		return new File(filePath).exists();  
	}
	
	 /**  
	  * 本地某个目录下的文件列表（不递归）  
	  * 
	  * @param folder  
	  *            ftp上的某个目录 
	  * @param suffix  
	  *            文件的后缀名（比如.mov.xml)  
	  * @return 文件名称列表  
	  */
	public static String[] listFilebySuffix(String folder, String suffix) {  
		IOFileFilter fileFilter1 = new SuffixFileFilter(suffix);  
		IOFileFilter fileFilter2 = new NotFileFilter(DirectoryFileFilter.INSTANCE);  
		FilenameFilter filenameFilter = new AndFileFilter(fileFilter1, fileFilter2);  
		return new File(folder).list(filenameFilter);  
	}
	
	public static void main(String[] args) throws IOException {
//		FileToolKit.copyFile("D:/osgi.jar", "D:/test");
//		FileToolKit.deleteFile("D:/test");
//		FileToolKit.moveFile("D:/test/osgi.jar", "D:/test1");
//		FileToolKit.renameFile("D:/test1", "test2");
//		System.out.println(FileToolKit.genFileSize("D:/test2"));
		String[] files = FileToolKit.listFilebySuffix("D:/test2", ".jar");
		for(String file : files) {
			System.out.println(file);
		}
	}
	
}