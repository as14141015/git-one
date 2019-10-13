package cn.itsource.basic.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页工具父类类
 */
public class PageList<T> {
    private Long total;
    private List<T> rows = new ArrayList<>();

    public PageList() {
    }

    public PageList(Long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
