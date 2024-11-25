package com.alinesno.infra.data.assets.constants;

import com.alinesno.infra.data.assets.api.TableFieldRequestDto;

import java.util.Arrays;
import java.util.List;

public class AssetDataConstants {
    public static final List<TableFieldRequestDto> DEFAULT_FIELDS = Arrays.asList(
        new TableFieldRequestDto("id", "string", 64, false, true, "唯一ID号"),
        new TableFieldRequestDto("add_time", "timestamp", 18, false, false, "添加时间"),
        new TableFieldRequestDto("operator_id", "string", 32, false, false, "操作员"),
        new TableFieldRequestDto("org_id", "string", 32, false, false, "所属组织"),
        new TableFieldRequestDto("department_id", "string", 32, false, false, "所属部门")
    );
}