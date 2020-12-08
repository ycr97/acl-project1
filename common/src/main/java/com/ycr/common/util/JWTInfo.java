package com.ycr.common.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ycr
 * @date 2020/11/10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JWTInfo {

    private String id;

    private String username;

}
