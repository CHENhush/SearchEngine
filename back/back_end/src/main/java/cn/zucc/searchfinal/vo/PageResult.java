package cn.zucc.searchfinal.vo;


import java.util.ArrayList;
import java.util.List;

public class PageResult<T> {
    private long total;
    private List<T> rows;

    public static PageResult ofEmpty() {
        return new PageResult(0L, new ArrayList());
    }

    public static PageResult ofList(List list) {
        return new PageResult((long)list.size(), list);
    }

    public static PageResult of(long total, List list) {
        return new PageResult(total, list);
    }

    public long getTotal() {
        return this.total;
    }

    public List<T> getRows() {
        return this.rows;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof PageResult)) {
            return false;
        } else {
            PageResult<?> other = (PageResult)o;
            if (!other.canEqual(this)) {
                return false;
            } else if (this.getTotal() != other.getTotal()) {
                return false;
            } else {
                Object this$rows = this.getRows();
                Object other$rows = other.getRows();
                if (this$rows == null) {
                    if (other$rows != null) {
                        return false;
                    }
                } else if (!this$rows.equals(other$rows)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof PageResult;
    }

    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        long $total = this.getTotal();
        result = result * 59 + (int) ($total >>> 32 ^ $total);
        Object $rows = this.getRows();
        result = result * 59 + ($rows == null ? 43 : $rows.hashCode());
        return result;
    }

    public String toString() {
        return "PageResult(total=" + this.getTotal() + ", rows=" + this.getRows() + ")";
    }

    public PageResult() {
    }

    public PageResult(long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }
}
