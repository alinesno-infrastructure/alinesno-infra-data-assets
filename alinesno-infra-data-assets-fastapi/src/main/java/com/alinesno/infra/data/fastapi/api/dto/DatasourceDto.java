package com.alinesno.infra.data.fastapi.api.dto;


import com.alinesno.infra.common.facade.base.BaseDto;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author luoxiaodong
 * @since 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DatasourceDto extends BaseDto {
	
	@NotBlank(message = "数据库描述")
	private String dbDesc;
	
	private String dbName;
	
	@NotBlank(message = "数据库用户名为空")
	private String jdbcUrl ;
	
	@NotBlank(message = "数据库用户名为空")
	private String dbUsername;
	
	@NotBlank(message = "数据库密码为空")
	private String dbPasswd;
	
	@NotBlank(message = "数据库类型为空")
	private String dbType;

}
