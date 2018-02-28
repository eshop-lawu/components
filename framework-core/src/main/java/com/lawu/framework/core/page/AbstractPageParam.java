package com.lawu.framework.core.page;

import javax.validation.constraints.Min;

import io.swagger.annotations.ApiModelProperty;

/**
 * 分页参数
 * @author Leach
 * @date 2017/4/1
 */
public abstract class AbstractPageParam {

    /**
     * 当前页码
     */
    @Min(value = 1, message = "页码从1开始")
    @ApiModelProperty(value = "当前页码（从1开始）", required = true)
    private Integer currentPage = 1;

    /**
     * 每页数量
     */
    @Min(value = 1, message = "每页数量必须大于0")
    @ApiModelProperty(value = "每页数量", required = true)
    private Integer pageSize = 10;


    public int getOffset() {
        return this.pageSize * (this.currentPage - 1);
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

}
