package com.lawu.framework.core.page;

import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Leach
 * @date 2017/3/23
 */
public class Page<T> {

    /**
     * 当前页码
     */
    @ApiModelProperty(value = "当前页码", required = true)
    private Integer currentPage;

    /**
     * 数据总条数
     */
    @ApiModelProperty(value = "数据总条数", required = true)
    private Integer totalCount;

    @ApiModelProperty(value = "当前页数据列表", required = true)
    private List<T> records = new ArrayList<>();

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }
}
