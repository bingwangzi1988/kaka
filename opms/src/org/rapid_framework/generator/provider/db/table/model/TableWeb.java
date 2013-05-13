package org.rapid_framework.generator.provider.db.table.model;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ritchrs
 * Date: 11-6-13
 * Time: ?¦¶10:47
 * desc:
 */
public class TableWeb implements java.io.Serializable, Cloneable {
    private List<ColumnWeb> columns;
    private boolean paging = true;
    private boolean columnLock = false;
    private boolean canedit = true;
    private boolean stripeRows = true;
    private boolean columnLines = false;
    private List<String> gridPlugins;

    public List<ColumnWeb> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnWeb> columns) {
        this.columns = columns;
    }

    public boolean getPaging() {
        return paging;
    }

    public void setPaging(boolean paging) {
        this.paging = paging;
    }

    public boolean getColumnLock() {
        return columnLock;
    }

    public void setColumnLock(boolean columnLock) {
        this.columnLock = columnLock;
    }

    public boolean getCanedit() {
        return canedit;
    }

    public void setCanedit(boolean canedit) {
        this.canedit = canedit;
    }

    public boolean getStripeRows() {
        return stripeRows;
    }

    public void setStripeRows(boolean stripeRows) {
        this.stripeRows = stripeRows;
    }

    public boolean getColumnLines() {
        return columnLines;
    }

    public void setColumnLines(boolean columnLines) {
        this.columnLines = columnLines;
    }

    public List<String> getGridPlugins() {
        return gridPlugins;
    }

    public void setGridPlugins(List<String> gridPlugins) {
        this.gridPlugins = gridPlugins;
    }
}
