package com.common.util;


import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;  
import java.util.ArrayList;
import java.util.LinkedHashMap;  
import java.util.List;  
  
import org.apache.poi.hssf.usermodel.HSSFCell;  
import org.apache.poi.hssf.usermodel.HSSFCellStyle;  
import org.apache.poi.hssf.usermodel.HSSFFont;  
import org.apache.poi.hssf.usermodel.HSSFRichTextString;  
import org.apache.poi.hssf.usermodel.HSSFRow;  
import org.apache.poi.hssf.usermodel.HSSFSheet;  
import org.apache.poi.hssf.usermodel.HSSFWorkbook;  
import org.apache.poi.hssf.util.HSSFColor;  
  
/** 
 * ����EXCEL�����࣬�����ڵ��б�ͷ�ı�� 
 * @author wusq 
 * @since 2011-06-20 
 */  
public class ExportExcelUtils {  
      
    /** 
     * ����Excel�ķ��� 
     * @param title excel�е�sheet���� 
     * @param headers ��ͷ 
     * @param columns ��ͷ��Ӧ�����ݿ��е����� 
     * @param result ����� 
     * @param out ����� 
     * @param pattern ʱ���ʽ 
     * @throws Exception 
     */  
	public static void exportExcel(String title, String[] headers, String[] columns, List<LinkedHashMap<String, Object>> result, OutputStream out, String pattern) throws Exception{  
        // ����һ��������  
        HSSFWorkbook workbook = new HSSFWorkbook();  
        // ����һ�����  
        HSSFSheet sheet = workbook.createSheet(title);  
        // ���ñ��Ĭ���п��Ϊ20���ֽ�  
        sheet.setDefaultColumnWidth(Short.valueOf("20"));  
          
          
        // ����һ����ʽ  
        HSSFCellStyle style = workbook.createCellStyle();  
        // ������Щ��ʽ  
        style.setFillForegroundColor(HSSFColor.GOLD.index);  
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);  
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);  
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
        // ����һ������  
        HSSFFont font = workbook.createFont();  
        font.setColor(HSSFColor.VIOLET.index);  
        //font.setFontHeightInPoints((short) 12);  
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  
        // ������Ӧ�õ���ǰ����ʽ  
        style.setFont(font);  
          
        // ָ������Ԫ��������ʾ����ʱ�Զ�����  
        style.setWrapText(true);  
          
        /* 
          
         ���¿����������õ��������ݵ���ʽ 
           
        // ���ɲ�������һ����ʽ 
        HSSFCellStyle style2 = workbook.createCellStyle(); 
        style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index); 
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); 
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN); 
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN); 
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN); 
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN); 
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER); 
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); 
        // ������һ������ 
        HSSFFont font2 = workbook.createFont(); 
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL); 
        // ������Ӧ�õ���ǰ����ʽ 
        style2.setFont(font2); 
        // ����һ����ͼ�Ķ��������� 
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch(); 
         
         
        // ����ע�͵Ĵ�С��λ��,����ĵ� 
        HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 4, 2, (short) 6, 5)); 
        // ����ע������ 
        comment.setString(new HSSFRichTextString("������POI�����ע�ͣ�")); 
        // ����ע�����ߣ�������ƶ�����Ԫ�����ǿ�����״̬���п���������. 
        comment.setAuthor("leno");*/  
          
          
        // ������������  
        HSSFRow row = sheet.createRow(0);  
        for (short i = 0; i < headers.length; i++) {  
            HSSFCell cell = row.createCell(i);  
            cell.setCellStyle(style);  
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);  
            cell.setCellValue(text);  
        }  
        // �����������ݣ�����������  
        if(result != null){  
            int index = 1;  
            for(LinkedHashMap<String, Object> m:result){  
                row = sheet.createRow(index);  
                short cellIndex = 0;  
                for(String s:columns){  
                    HSSFCell cell = row.createCell(cellIndex);  
                    //cell.setCellStyle(style2);  
                    HSSFRichTextString richString = new HSSFRichTextString(m.get(s) == null ? "" : m.get(s).toString());  
                    //HSSFFont font3 = workbook.createFont();  
                    //font3.setColor(HSSFColor.BLUE.index);  
                    //richString.applyFont(font3);  
                    cell.setCellValue(richString);  
                    cellIndex++;  
                }  
                index++;  
            }     
        }  
        workbook.write(out);  
    }  
    
    public static void main(String[] args) throws Exception {
    	String title = "sheet1";
    	String[] headers = {"��Ŀ1","��Ŀ2","��Ŀ3"};
    	String[] columns = {"ID", "USER", "PASS"};  
    	List<LinkedHashMap<String, Object>> result = new ArrayList<LinkedHashMap<String,Object>>();
    	int i = 0;
    	while(i < 10){
	    	LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
	    	map.put("ID", "�ƹ�");
	    	map.put("USER", "����");
	    	map.put("PASS", "����");
	    	result.add(map);
	    	i++;
    	}
    	OutputStream out = new FileOutputStream(new File("c:/workbook.xls"));
    	String pattern = "";
    	exportExcel(title, headers, columns, result, out, pattern);
	}
}