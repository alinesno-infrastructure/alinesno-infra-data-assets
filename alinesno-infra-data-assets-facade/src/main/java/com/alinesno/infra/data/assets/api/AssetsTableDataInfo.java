package com.alinesno.infra.data.assets.api;

import com.alinesno.infra.common.facade.pageable.TableDataInfo;
import com.alinesno.infra.data.assets.entity.ManifestFieldEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class AssetsTableDataInfo extends TableDataInfo {
    private List<Field> fields; // 新增字段，用于存储字段列表信息

    @Data
    public static class Field{
        private String fieldName;
        private String fieldType;
        private String fieldComment;

        public Field(ManifestFieldEntity entity) {
            this.fieldName = entity.getFieldName();
            this.fieldType = entity.getFieldType();
            this.fieldComment = entity.getFieldComment();

            if(entity.getFieldComment() == null){
                this.fieldComment = entity.getFieldName() ;
            }
        }
    }

}
