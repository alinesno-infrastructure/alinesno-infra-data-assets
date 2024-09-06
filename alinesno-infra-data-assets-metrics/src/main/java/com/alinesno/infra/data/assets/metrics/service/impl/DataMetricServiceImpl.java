package com.alinesno.infra.data.assets.metrics.service.impl;

import com.alinesno.infra.common.core.service.impl.IBaseServiceImpl;
import com.alinesno.infra.data.assets.metrics.entity.DataMetricEntity;
import com.alinesno.infra.data.assets.metrics.mapper.DataMetricMapper;
import com.alinesno.infra.data.assets.metrics.service.IDataMetricService;
import org.springframework.stereotype.Service;

@Service
public class DataMetricServiceImpl extends IBaseServiceImpl<DataMetricEntity, DataMetricMapper> implements IDataMetricService {
}