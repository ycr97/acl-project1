package com.ycr.acl.model;

import com.ycr.acl.entity.Permission;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * @author ycr
 * @date 2020/11/27
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PermissionVO extends Permission implements Serializable {

    private static final long serialVersionUID = -2341183262828502188L;

    private int level;

    private List<PermissionVO> children;
}
