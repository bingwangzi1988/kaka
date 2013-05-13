package org.rapid_framework.generator.provider.db.table.model;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: ritchrs
 * Date: 11-7-4
 * Time: ÏÂÎç3:18
 * desc:
 */
public class IdName implements Serializable {
    private String id;
    private String name;

    public IdName(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
