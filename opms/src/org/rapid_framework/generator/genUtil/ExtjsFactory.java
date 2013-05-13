package org.rapid_framework.generator.genUtil;

import org.rapid_framework.generator.provider.db.table.model.ColumnWeb;

/**
 * Created by IntelliJ IDEA.
 * User: ritchrs
 * Date: 11-6-3
 * Time: ?¦¶1:20
 * desc:
 */
public class ExtjsFactory {
    public static String genFormField(ColumnWeb column) {
//        if ("radio".equals(column.getxType()))
//            return FieldConverter.ConverterToFormRadio(column);
//        else
            return FieldConverter.ConverterToForm(column);
    }

    public static String genSearchField(ColumnWeb column) {
        if ("datefield".equals(column.getxType()))
            return FieldConverter.ConverterToDateSearch(column);
        else
            return FieldConverter.ConverterToSearch(column);
    }

//    public static String genSearchFieldForDate(ColumnWeb column) {
//        String name = column.getColumnName();
//        String alias = column.getColumnAlias();
//
//        String namebegin = name + "begin";
//        String nameend = name + "end";
//
//        String aliasbegin = alias + "??
//        String aliaseend = alias + "?ÑL
//        column.setColumnName(namebegin);
//        column.setColumnAlias(aliasbegin);
//        column.setColumnAlias(aliasbegin);
//        String dateBegin = FieldConverter.ConverterToSearch(column);
//        column.setColumnName(nameend);
//        column.setColumnAlias(aliaseend);
//        String dateEnd = FieldConverter.ConverterToSearch(column);
//        column.setColumnName(name);
//        column.setColumnAlias(alias);
//        return dateBegin + "," + dateEnd;
//    }
}
