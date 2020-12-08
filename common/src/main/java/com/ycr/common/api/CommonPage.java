package com.ycr.common.api;


import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author ycr
 * @date 2020/10/22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonPage<T> {

    // 当前页码(默认为1)
    private Integer pageNum = 1;

    // 每页显示的行数
    private  Integer pageSize;

    // 总页数,根据总行数和每页显示多少条数据算出来的
    // totalPage = totalRow % pageSize == 0 ? totalRow / pageSize : (totalRow / pageSize + 1)
    private Integer totalPage;

    // 总行数
    private Long totalRow;

    // 存放返回的实体类数据
    private List<T> list;

    public static <T> CommonPage<T> restPage(Page<T> pageResult) {
        CommonPage<T> result = new CommonPage<>();
        result.setPageNum(Convert.toInt(pageResult.getCurrent()));
        result.setPageSize(Convert.toInt(pageResult.getSize()));
        result.setTotalRow(pageResult.getTotal());
        result.setTotalPage(Convert.toInt(pageResult.getTotal()/pageResult.getSize()+1));
        result.setList(pageResult.getRecords());
        return result;
    }

    public CommonPage(Long totalRow, Integer pageSize, Integer pageNum) {

    }

}
