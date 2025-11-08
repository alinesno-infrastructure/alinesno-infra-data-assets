package com.alinesno.infra.data.assets.metrics.controller;

import com.alinesno.infra.common.core.constants.SpringInstanceScope;
import com.alinesno.infra.common.facade.pageable.DatatablesPageBean;
import com.alinesno.infra.common.facade.pageable.TableDataInfo;
import com.alinesno.infra.common.web.adapter.rest.BaseController;
import com.alinesno.infra.data.assets.metrics.entity.DataMetricEntity;
import com.alinesno.infra.data.assets.metrics.service.IDataMetricService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@Scope(SpringInstanceScope.PROTOTYPE)
@RequestMapping("/api/infra/data/assets/metric")
public class DataMetricController extends BaseController<DataMetricEntity, IDataMetricService> {

    @Autowired
    private IDataMetricService dataMetricService;

    @ResponseBody
    @PostMapping("/datatables")
    public TableDataInfo datatables(HttpServletRequest request, Model model, DatatablesPageBean page) {
        log.debug("page = {}", ToStringBuilder.reflectionToString(page));
        return this.toPage(model, this.getFeign() , page) ;
    }

    @PostMapping
    public DataMetricEntity create(@RequestBody DataMetricEntity dataMetric) {
        dataMetricService.save(dataMetric);
        return dataMetric;
    }

    @GetMapping("/{id}")
    public DataMetricEntity findById(@PathVariable Long id) {
        return dataMetricService.getById(id);
    }

    @PutMapping("/{id}")
    public DataMetricEntity update(@PathVariable Long id, @RequestBody DataMetricEntity dataMetric) {
        dataMetric.setId(id);
        dataMetricService.updateById(dataMetric);
        return dataMetric;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        dataMetricService.removeById(id);
    }

    @GetMapping
    public List<DataMetricEntity> findAll() {
        return dataMetricService.list();
    }
}