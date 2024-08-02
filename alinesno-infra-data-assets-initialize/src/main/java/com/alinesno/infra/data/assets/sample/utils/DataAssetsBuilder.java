package com.alinesno.infra.data.assets.sample.utils;

import cn.hutool.core.util.IdUtil;
import com.alinesno.infra.data.assets.entity.AssetCatalogEntity;
import com.alinesno.infra.data.assets.entity.LabelEntity;

import java.util.ArrayList;
import java.util.List;

public class DataAssetsBuilder {

    public static void main(String[] args) {
        List<AssetCatalogEntity> dataAssets = buildDataAssets();

        // 打印或处理 dataAssets 列表
        for (AssetCatalogEntity entity : dataAssets) {
            System.out.println(entity.getName());
            System.out.println("\tIcon: " + entity.getIcon());
            System.out.println("\tRemark: " + entity.getRemark());
            System.out.println("\tChildren:");
            for (AssetCatalogEntity child : entity.getChildren()) {
                System.out.println("\t\t- " + child.getName() + ": " + child.getIcon() + ", " + child.getRemark());
            }
            System.out.println();
        }
    }

    public static List<AssetCatalogEntity> buildDataAssets() {
        List<AssetCatalogEntity> dataAssets = new ArrayList<>();

        // 示例教育域
        AssetCatalogEntity school = new AssetCatalogEntity();
        school.setId(1L);
        school.setParentId(0L);

        school.setName("教育学校域");
        school.setIcon("fa-brands fa-slack");
        school.setRemark("教育行业，学校领域数据资源管理(用于示例数据)");
        dataAssets.add(school) ;

        // 学生信息数据
        AssetCatalogEntity studentInfoData = new AssetCatalogEntity();

        studentInfoData.setId(IdUtil.getSnowflakeNextId());
        studentInfoData.setParentId(school.getId());

        studentInfoData.setName("学生信息数据");
        studentInfoData.setIcon("fa-brands fa-slack");
        studentInfoData.setRemark("学生的个人资料、学籍信息、成绩记录等");

        List<AssetCatalogEntity> studentChildren = new ArrayList<>();
        studentChildren.add(createChild("个人信息", "fa-solid fa-user", "学生的基本信息如姓名、性别、出生日期等"));
        studentChildren.add(createChild("学籍信息", "fa-solid fa-graduation-cap", "学生的入学信息、班级分配、毕业信息等"));
        studentChildren.add(createChild("成绩记录", "fa-solid fa-chart-bar", "学生的成绩单、考试成绩等"));

        studentChildren.forEach(child -> child.setParentId(studentInfoData.getId()));

        studentInfoData.setChildren(studentChildren);
        dataAssets.add(studentInfoData);
        dataAssets.addAll(studentChildren);

        // 教职工信息
        AssetCatalogEntity staffInfo = new AssetCatalogEntity();

        staffInfo.setId(IdUtil.getSnowflakeNextId());
        staffInfo.setParentId(school.getId());

        staffInfo.setName("教职工信息");
        staffInfo.setIcon("fa-solid fa-list-check");
        staffInfo.setRemark("教师和其他工作人员的个人资料、职称等");

        List<AssetCatalogEntity> staffChildren = new ArrayList<>();
        staffChildren.add(createChild("个人资料", "fa-solid fa-address-card", "教职工的基本信息如姓名、性别、入职日期等"));
        staffChildren.add(createChild("职称信息", "fa-solid fa-briefcase", "教职工的职称、岗位职责等"));
        staffChildren.add(createChild("工作经历", "fa-solid fa-history", "教职工的工作经历、教育背景等"));

        staffChildren.forEach(child -> child.setParentId(staffInfo.getId()));

        staffInfo.setChildren(staffChildren);
        dataAssets.add(staffInfo);
        dataAssets.addAll(staffChildren);

        // 课程资料数据
        AssetCatalogEntity courseMaterialsData = new AssetCatalogEntity();

        courseMaterialsData.setId(IdUtil.getSnowflakeNextId());
        courseMaterialsData.setParentId(school.getId());

        courseMaterialsData.setName("课程资料数据");
        courseMaterialsData.setIcon("fa-solid fa-at");
        courseMaterialsData.setRemark("课程大纲、教学材料、课程评估数据等");

        List<AssetCatalogEntity> courseMaterialsChildren = new ArrayList<>();
        courseMaterialsChildren.add(createChild("课程大纲", "fa-solid fa-book", "各门课程的教学大纲、学习目标等"));
        courseMaterialsChildren.add(createChild("教学材料", "fa-solid fa-folder-open", "教学PPT、讲义、参考书目等"));
        courseMaterialsChildren.add(createChild("课程评估", "fa-solid fa-star", "学生对课程的评价、教学效果评估等"));

        courseMaterialsChildren.forEach(child -> child.setParentId(courseMaterialsData.getId()));

        courseMaterialsData.setChildren(courseMaterialsChildren);
        dataAssets.add(courseMaterialsData);
        dataAssets.addAll(courseMaterialsChildren);

        // 学校财务数据
        AssetCatalogEntity schoolFinanceData = new AssetCatalogEntity();

        schoolFinanceData.setId(IdUtil.getSnowflakeNextId());
        schoolFinanceData.setParentId(school.getId());

        schoolFinanceData.setName("学校财务数据");
        schoolFinanceData.setIcon("fa-solid fa-list-check");
        schoolFinanceData.setRemark("学校的财务报表、预算、经费使用情况等");

        List<AssetCatalogEntity> financeChildren = new ArrayList<>();
        financeChildren.add(createChild("财务报表", "fa-solid fa-file-invoice-dollar", "年度财务报告、收支明细等"));
        financeChildren.add(createChild("预算规划", "fa-solid fa-money-bill-wave", "学校年度预算、项目预算等"));
        financeChildren.add(createChild("经费使用", "fa-solid fa-coins", "经费分配、使用记录等"));

        financeChildren.forEach(child -> child.setParentId(schoolFinanceData.getId()));

        schoolFinanceData.setChildren(financeChildren);
        dataAssets.add(schoolFinanceData);
        dataAssets.addAll(financeChildren);

        // 校园设施管理数据
        AssetCatalogEntity campusFacilityData = new AssetCatalogEntity();

        campusFacilityData.setId(IdUtil.getSnowflakeNextId());
        campusFacilityData.setParentId(school.getId());

        campusFacilityData.setName("校园设施管理数据");
        campusFacilityData.setIcon("fa-solid fa-file-word");
        campusFacilityData.setRemark("设施维护记录、安全检查记录、设备清单等");

        List<AssetCatalogEntity> facilityChildren = new ArrayList<>();
        facilityChildren.add(createChild("设施维护", "fa-solid fa-tools", "维修记录、保养计划等"));
        facilityChildren.add(createChild("安全检查", "fa-solid fa-shield-halved", "定期安全检查记录、隐患排查等"));
        facilityChildren.add(createChild("设备清单", "fa-solid fa-list-ul", "校园内所有设备的清单、位置信息等"));

        facilityChildren.forEach(child -> child.setParentId(campusFacilityData.getId()));

        campusFacilityData.setChildren(facilityChildren);
        dataAssets.add(campusFacilityData);
        dataAssets.addAll(facilityChildren);

        // 学生表现数据
        AssetCatalogEntity studentPerformanceData = new AssetCatalogEntity();

        studentPerformanceData.setId(IdUtil.getSnowflakeNextId());
        studentPerformanceData.setParentId(school.getId());

        studentPerformanceData.setName("学生表现数据");
        studentPerformanceData.setIcon("fa-solid fa-eye-slash");
        studentPerformanceData.setRemark("学生的考试成绩、出勤情况、行为表现等");

        List<AssetCatalogEntity> performanceChildren = new ArrayList<>();
        performanceChildren.add(createChild("考试成绩", "fa-solid fa-chart-line", "学生的各科成绩、排名等"));
        performanceChildren.add(createChild("出勤情况", "fa-solid fa-calendar-check", "学生的出勤率、请假记录等"));
        performanceChildren.add(createChild("行为表现", "fa-solid fa-hand-point-up", "学生的行为评分、奖惩记录等"));

        performanceChildren.forEach(child -> child.setParentId(studentPerformanceData.getId()));

        studentPerformanceData.setChildren(performanceChildren);
        dataAssets.add(studentPerformanceData);
        dataAssets.addAll(performanceChildren);

        // 学校教学数据
        AssetCatalogEntity schoolTeachingData = new AssetCatalogEntity();

        schoolTeachingData.setId(IdUtil.getSnowflakeNextId());
        schoolTeachingData.setParentId(school.getId());

        schoolTeachingData.setName("学校教学数据");
        schoolTeachingData.setIcon("fa-solid fa-file-word");
        schoolTeachingData.setRemark("教学效果评估、学生评价、教学资源使用情况等");

        List<AssetCatalogEntity> teachingChildren = new ArrayList<>();
        teachingChildren.add(createChild("教学评估", "fa-solid fa-star-half-stroke", "教学质量评估、课程满意度调查等"));
        teachingChildren.add(createChild("学生评价", "fa-solid fa-comments", "学生对教师的评价、课堂互动情况等"));
        teachingChildren.add(createChild("资源使用", "fa-solid fa-hard-drive", "教学资源的使用频率、利用率等"));

        teachingChildren.forEach(child -> child.setParentId(schoolTeachingData.getId()));

        schoolTeachingData.setChildren(teachingChildren);
        dataAssets.add(schoolTeachingData);
        dataAssets.addAll(teachingChildren);

        // 招生数据数据
        AssetCatalogEntity admissionData = new AssetCatalogEntity();

        admissionData.setId(IdUtil.getSnowflakeNextId());
        admissionData.setParentId(school.getId());

        admissionData.setName("招生数据数据");
        admissionData.setIcon("fa-solid fa-user-shield");
        admissionData.setRemark("招生计划、招生宣传材料、招生渠道数据等");

        List<AssetCatalogEntity> admissionChildren = new ArrayList<>();
        admissionChildren.add(createChild("招生计划", "fa-solid fa-calendar-days", "每年的招生名额、专业设置等"));
        admissionChildren.add(createChild("宣传材料", "fa-solid fa-bullhorn", "招生手册、宣传册等"));
        admissionChildren.add(createChild("渠道数据", "fa-solid fa-network-wired", "不同招生渠道的效果、报名人数等"));

        admissionChildren.forEach(child -> child.setParentId(admissionData.getId()));

        admissionData.setChildren(admissionChildren);
        dataAssets.add(admissionData);
        dataAssets.addAll(admissionChildren);

        // 校友关系数据
        AssetCatalogEntity alumniData = new AssetCatalogEntity();

        alumniData.setId(IdUtil.getSnowflakeNextId());
        alumniData.setParentId(school.getId());

        alumniData.setName("校友关系数据");
        alumniData.setIcon("fa-solid fa-comment-slash");
        alumniData.setRemark("校友基本信息、校友捐赠记录等");

        List<AssetCatalogEntity> alumniChildren = new ArrayList<>();
        alumniChildren.add(createChild("基本信息", "fa-solid fa-address-card", "校友的联系信息、毕业年份等"));
        alumniChildren.add(createChild("捐赠记录", "fa-solid fa-gift", "校友捐赠的金额、用途等"));

        alumniChildren.forEach(child -> child.setParentId(alumniData.getId()));

        alumniData.setChildren(alumniChildren);
        dataAssets.add(alumniData);
        dataAssets.addAll(alumniChildren);

        // >>>>>>>>>>>>>>>>>>>>>>>>>>>>> 电商域

        // 电商域
        AssetCatalogEntity ecommerceDomain = new AssetCatalogEntity();
        ecommerceDomain.setId(IdUtil.getSnowflakeNextId());
        ecommerceDomain.setParentId(0L);

        ecommerceDomain.setName("电商域");
        ecommerceDomain.setIcon("fa-brands fa-amazon");
        ecommerceDomain.setRemark("电子商务领域的数据资源管理");

        dataAssets.add(ecommerceDomain);

        // 产品信息
        AssetCatalogEntity productInfo = new AssetCatalogEntity();
        productInfo.setId(IdUtil.getSnowflakeNextId());
        productInfo.setParentId(ecommerceDomain.getId());

        productInfo.setName("产品信息");
        productInfo.setIcon("fa-solid fa-boxes-packing");
        productInfo.setRemark("产品的详细描述、图片、规格等");

        List<AssetCatalogEntity> productChildren = new ArrayList<>();
        productChildren.add(createChild("商品详情", "fa-solid fa-box", "商品的名称、描述、价格等"));
        productChildren.add(createChild("库存信息", "fa-solid fa-warehouse", "库存数量、仓库位置等"));
        productChildren.add(createChild("商品图片", "fa-solid fa-image", "商品的图片和视频"));

        productChildren.forEach(child -> child.setParentId(productInfo.getId()));

        productInfo.setChildren(productChildren);
        dataAssets.add(productInfo);
        dataAssets.addAll(productChildren);

        // 订单管理
        AssetCatalogEntity orderManagement = new AssetCatalogEntity();
        orderManagement.setId(IdUtil.getSnowflakeNextId());
        orderManagement.setParentId(ecommerceDomain.getId());

        orderManagement.setName("订单管理");
        orderManagement.setIcon("fa-solid fa-receipt");
        orderManagement.setRemark("订单的状态、支付信息、发货详情等");

        List<AssetCatalogEntity> orderChildren = new ArrayList<>();
        orderChildren.add(createChild("订单详情", "fa-solid fa-file-invoice", "订单号、购买商品、总价等"));
        orderChildren.add(createChild("支付信息", "fa-solid fa-credit-card", "支付方式、交易状态等"));
        orderChildren.add(createChild("物流跟踪", "fa-solid fa-truck-fast", "快递公司、运单号、配送进度等"));

        orderChildren.forEach(child -> child.setParentId(orderManagement.getId()));

        orderManagement.setChildren(orderChildren);
        dataAssets.add(orderManagement);
        dataAssets.addAll(orderChildren);

        // 用户行为分析
        AssetCatalogEntity userBehaviorAnalysis = new AssetCatalogEntity();
        userBehaviorAnalysis.setId(IdUtil.getSnowflakeNextId());
        userBehaviorAnalysis.setParentId(ecommerceDomain.getId());

        userBehaviorAnalysis.setName("用户行为分析");
        userBehaviorAnalysis.setIcon("fa-solid fa-chart-simple");
        userBehaviorAnalysis.setRemark("用户浏览、点击、购买行为等");

        List<AssetCatalogEntity> behaviorChildren = new ArrayList<>();
        behaviorChildren.add(createChild("浏览记录", "fa-solid fa-eye", "用户的浏览历史"));
        behaviorChildren.add(createChild("搜索关键词", "fa-solid fa-magnifying-glass", "用户搜索的关键词"));
        behaviorChildren.add(createChild("购物车记录", "fa-solid fa-cart-shopping", "用户加入购物车的商品"));

        behaviorChildren.forEach(child -> child.setParentId(userBehaviorAnalysis.getId()));

        userBehaviorAnalysis.setChildren(behaviorChildren);
        dataAssets.add(userBehaviorAnalysis);
        dataAssets.addAll(behaviorChildren);

        // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 项目管理域名

        // 项目管理域
        AssetCatalogEntity projectManagementDomain = new AssetCatalogEntity();
        projectManagementDomain.setId(IdUtil.getSnowflakeNextId());
        projectManagementDomain.setParentId(0L);

        projectManagementDomain.setName("项目管理域");
        projectManagementDomain.setIcon("fa-solid fa-diagram-project");
        projectManagementDomain.setRemark("项目管理相关的数据资源管理");

        dataAssets.add(projectManagementDomain);

        // 项目计划
        AssetCatalogEntity projectPlan = new AssetCatalogEntity();
        projectPlan.setId(IdUtil.getSnowflakeNextId());
        projectPlan.setParentId(projectManagementDomain.getId());

        projectPlan.setName("项目计划");
        projectPlan.setIcon("fa-solid fa-calendar-days");
        projectPlan.setRemark("项目的里程碑、时间线、关键节点等");

        List<AssetCatalogEntity> planChildren = new ArrayList<>();
        planChildren.add(createChild("里程碑", "fa-solid fa-flag", "项目的里程碑事件"));
        planChildren.add(createChild("时间线", "fa-solid fa-clock-rotate-left", "项目的各个阶段的时间安排"));
        planChildren.add(createChild("关键节点", "fa-solid fa-crosshairs", "项目的关键节点和决策点"));

        planChildren.forEach(child -> child.setParentId(projectPlan.getId()));

        projectPlan.setChildren(planChildren);
        dataAssets.add(projectPlan);
        dataAssets.addAll(planChildren);

        // 项目执行
        AssetCatalogEntity projectExecution = new AssetCatalogEntity();
        projectExecution.setId(IdUtil.getSnowflakeNextId());
        projectExecution.setParentId(projectManagementDomain.getId());

        projectExecution.setName("项目执行");
        projectExecution.setIcon("fa-solid fa-play");
        projectExecution.setRemark("项目实施过程中产生的数据");

        List<AssetCatalogEntity> executionChildren = new ArrayList<>();
        executionChildren.add(createChild("任务分配", "fa-solid fa-tasks", "任务的分配情况"));
        executionChildren.add(createChild("进度监控", "fa-solid fa-chart-line", "项目的进度和状态"));
        executionChildren.add(createChild("资源利用", "fa-solid fa-hard-drive", "项目资源的使用情况"));

        executionChildren.forEach(child -> child.setParentId(projectExecution.getId()));

        projectExecution.setChildren(executionChildren);
        dataAssets.add(projectExecution);
        dataAssets.addAll(executionChildren);

        // 项目风险管理
        AssetCatalogEntity projectRiskManagement = new AssetCatalogEntity();
        projectRiskManagement.setId(IdUtil.getSnowflakeNextId());
        projectRiskManagement.setParentId(projectManagementDomain.getId());

        projectRiskManagement.setName("项目风险管理");
        projectRiskManagement.setIcon("fa-solid fa-shield-halved");
        projectRiskManagement.setRemark("项目的风险识别、评估、应对策略等");

        List<AssetCatalogEntity> riskChildren = new ArrayList<>();
        riskChildren.add(createChild("风险识别", "fa-solid fa-exclamation-circle", "潜在的风险因素"));
        riskChildren.add(createChild("风险评估", "fa-solid fa-scale-balanced", "风险的概率和影响评估"));
        riskChildren.add(createChild("风险应对", "fa-solid fa-shield-cross", "风险的缓解措施和预案"));

        riskChildren.forEach(child -> child.setParentId(projectRiskManagement.getId()));

        projectRiskManagement.setChildren(riskChildren);
        dataAssets.add(projectRiskManagement);
        dataAssets.addAll(riskChildren);


        // 财务域
        AssetCatalogEntity financeDomain = new AssetCatalogEntity();
        financeDomain.setId(IdUtil.getSnowflakeNextId());
        financeDomain.setParentId(0L);

        financeDomain.setName("财务域");
        financeDomain.setIcon("fa-solid fa-money-bill-wave");
        financeDomain.setRemark("财务相关的数据资源管理");

        dataAssets.add(financeDomain);

        // 财务报表
        AssetCatalogEntity financialStatements = new AssetCatalogEntity();
        financialStatements.setId(IdUtil.getSnowflakeNextId());
        financialStatements.setParentId(financeDomain.getId());

        financialStatements.setName("财务报表");
        financialStatements.setIcon("fa-solid fa-file-invoice-dollar");
        financialStatements.setRemark("公司的财务状况、损益表、现金流量表等");

        List<AssetCatalogEntity> statementsChildren = new ArrayList<>();
        statementsChildren.add(createChild("资产负债表", "fa-solid fa-balance-scale", "公司的资产、负债和所有者权益"));
        statementsChildren.add(createChild("利润表", "fa-solid fa-chart-line", "公司的收入、成本和利润"));
        statementsChildren.add(createChild("现金流量表", "fa-solid fa-money-bill-wave", "公司的现金流入和流出"));

        statementsChildren.forEach(child -> child.setParentId(financialStatements.getId()));

        financialStatements.setChildren(statementsChildren);
        dataAssets.add(financialStatements);
        dataAssets.addAll(statementsChildren);

        // 预算管理
        AssetCatalogEntity budgetManagement = new AssetCatalogEntity();
        budgetManagement.setId(IdUtil.getSnowflakeNextId());
        budgetManagement.setParentId(financeDomain.getId());

        budgetManagement.setName("预算管理");
        budgetManagement.setIcon("fa-solid fa-calculator");
        budgetManagement.setRemark("预算编制、执行、调整等");

        List<AssetCatalogEntity> budgetChildren = new ArrayList<>();
        budgetChildren.add(createChild("预算编制", "fa-solid fa-file-contract", "预算的制定和审批过程"));
        budgetChildren.add(createChild("预算执行", "fa-solid fa-chart-line", "预算的实际执行情况"));
        budgetChildren.add(createChild("预算调整", "fa-solid fa-pen-to-square", "预算的调整和变更记录"));

        budgetChildren.forEach(child -> child.setParentId(budgetManagement.getId()));

        budgetManagement.setChildren(budgetChildren);
        dataAssets.add(budgetManagement);
        dataAssets.addAll(budgetChildren);

        // 成本控制
        AssetCatalogEntity costControl = new AssetCatalogEntity();
        costControl.setId(IdUtil.getSnowflakeNextId());
        costControl.setParentId(financeDomain.getId());

        costControl.setName("成本控制");
        costControl.setIcon("fa-solid fa-chart-column");
        costControl.setRemark("成本核算、成本优化、成本分析等");

        List<AssetCatalogEntity> costChildren = new ArrayList<>();
        costChildren.add(createChild("成本核算", "fa-solid fa-calculator", "成本的计算和分摊"));
        costChildren.add(createChild("成本优化", "fa-solid fa-chart-simple", "成本降低措施和建议"));
        costChildren.add(createChild("成本分析", "fa-solid fa-chart-pie", "成本构成和趋势分析"));

        costChildren.forEach(child -> child.setParentId(costControl.getId()));

        costControl.setChildren(costChildren);
        dataAssets.add(costControl);
        dataAssets.addAll(costChildren);

        // 人力域
        AssetCatalogEntity hrDomain = new AssetCatalogEntity();
        hrDomain.setId(IdUtil.getSnowflakeNextId());
        hrDomain.setParentId(0L);

        hrDomain.setName("人力域");
        hrDomain.setIcon("fa-solid fa-users");
        hrDomain.setRemark("人力资源相关的数据资源管理");

        dataAssets.add(hrDomain);

        // 员工档案
        AssetCatalogEntity employeeRecords = new AssetCatalogEntity();
        employeeRecords.setId(IdUtil.getSnowflakeNextId());
        employeeRecords.setParentId(hrDomain.getId());

        employeeRecords.setName("员工档案");
        employeeRecords.setIcon("fa-solid fa-address-card");
        employeeRecords.setRemark("员工的基本信息、入职离职记录等");

        List<AssetCatalogEntity> recordsChildren = new ArrayList<>();
        recordsChildren.add(createChild("基本信息", "fa-solid fa-user-tag", "员工的姓名、职位、部门等"));
        recordsChildren.add(createChild("入职离职", "fa-solid fa-calendar-check", "员工的入职日期、离职原因等"));
        recordsChildren.add(createChild("培训记录", "fa-solid fa-graduation-cap", "员工参加过的培训课程和证书"));

        recordsChildren.forEach(child -> child.setParentId(employeeRecords.getId()));

        employeeRecords.setChildren(recordsChildren);
        dataAssets.add(employeeRecords);
        dataAssets.addAll(recordsChildren);

        // 绩效考核
        AssetCatalogEntity performanceEvaluation = new AssetCatalogEntity();
        performanceEvaluation.setId(IdUtil.getSnowflakeNextId());
        performanceEvaluation.setParentId(hrDomain.getId());

        performanceEvaluation.setName("绩效考核");
        performanceEvaluation.setIcon("fa-solid fa-star");
        performanceEvaluation.setRemark("员工的工作表现、考核周期、考核结果等");

        List<AssetCatalogEntity> evaluationChildren = new ArrayList<>();
        evaluationChildren.add(createChild("考核标准", "fa-solid fa-ruler", "绩效考核的标准和指标"));
        evaluationChildren.add(createChild("考核周期", "fa-solid fa-calendar-days", "绩效考核的时间周期"));
        evaluationChildren.add(createChild("考核结果", "fa-solid fa-chart-simple", "员工的考核得分和排名"));

        evaluationChildren.forEach(child -> child.setParentId(performanceEvaluation.getId()));

        performanceEvaluation.setChildren(evaluationChildren);
        dataAssets.add(performanceEvaluation);
        dataAssets.addAll(evaluationChildren);

        // 招聘管理
        AssetCatalogEntity recruitmentManagement = new AssetCatalogEntity();
        recruitmentManagement.setId(IdUtil.getSnowflakeNextId());
        recruitmentManagement.setParentId(hrDomain.getId());

        recruitmentManagement.setName("招聘管理");
        recruitmentManagement.setIcon("fa-solid fa-user-plus");
        recruitmentManagement.setRemark("招聘计划、招聘流程、候选人信息等");

        List<AssetCatalogEntity> recruitmentChildren = new ArrayList<>();
        recruitmentChildren.add(createChild("招聘计划", "fa-solid fa-calendar-check", "招聘需求和计划时间表"));
        recruitmentChildren.add(createChild("面试记录", "fa-solid fa-user-secret", "面试官反馈和评分"));
        recruitmentChildren.add(createChild("录用通知", "fa-solid fa-envelope", "向候选人发出的录用通知书"));

        recruitmentChildren.forEach(child -> child.setParentId(recruitmentManagement.getId()));

        recruitmentManagement.setChildren(recruitmentChildren);
        dataAssets.add(recruitmentManagement);
        dataAssets.addAll(recruitmentChildren);

        // 用户运营域
        AssetCatalogEntity userOperationDomain = new AssetCatalogEntity();
        userOperationDomain.setId(IdUtil.getSnowflakeNextId());
        userOperationDomain.setParentId(0L);

        userOperationDomain.setName("用户运营域");
        userOperationDomain.setIcon("fa-solid fa-users");
        userOperationDomain.setRemark("用户运营相关的数据资源管理");

        dataAssets.add(userOperationDomain);

        // 用户画像
        AssetCatalogEntity userProfiles = new AssetCatalogEntity();
        userProfiles.setId(IdUtil.getSnowflakeNextId());
        userProfiles.setParentId(userOperationDomain.getId());

        userProfiles.setName("用户画像");
        userProfiles.setIcon("fa-solid fa-user-tag");
        userProfiles.setRemark("用户的属性标签、兴趣偏好等");

        List<AssetCatalogEntity> profilesChildren = new ArrayList<>();
        profilesChildren.add(createChild("基本属性", "fa-solid fa-user", "用户的年龄、性别、地域等"));
        profilesChildren.add(createChild("兴趣偏好", "fa-solid fa-heart", "用户的喜好和消费倾向"));
        profilesChildren.add(createChild("行为模式", "fa-solid fa-chart-line", "用户的活跃度、留存率等"));

        profilesChildren.forEach(child -> child.setParentId(userProfiles.getId()));

        userProfiles.setChildren(profilesChildren);
        dataAssets.add(userProfiles);
        dataAssets.addAll(profilesChildren);

        // 用户增长
        AssetCatalogEntity userGrowth = new AssetCatalogEntity();
        userGrowth.setId(IdUtil.getSnowflakeNextId());
        userGrowth.setParentId(userOperationDomain.getId());

        userGrowth.setName("用户增长");
        userGrowth.setIcon("fa-solid fa-arrow-up-wide-short");
        userGrowth.setRemark("用户获取、留存、转化等策略");

        List<AssetCatalogEntity> growthChildren = new ArrayList<>();
        growthChildren.add(createChild("获取策略", "fa-solid fa-user-plus", "吸引新用户的手段和活动"));
        growthChildren.add(createChild("留存策略", "fa-solid fa-user-lock", "提高用户粘性的措施"));
        growthChildren.add(createChild("转化策略", "fa-solid fa-exchange", "促进用户付费和升级的服务"));

        growthChildren.forEach(child -> child.setParentId(userGrowth.getId()));

        userGrowth.setChildren(growthChildren);
        dataAssets.add(userGrowth);
        dataAssets.addAll(growthChildren);

        // 用户反馈
        AssetCatalogEntity userFeedback = new AssetCatalogEntity();
        userFeedback.setId(IdUtil.getSnowflakeNextId());
        userFeedback.setParentId(userOperationDomain.getId());

        userFeedback.setName("用户反馈");
        userFeedback.setIcon("fa-solid fa-comment-dots");
        userFeedback.setRemark("用户的意见、建议和投诉");

        List<AssetCatalogEntity> feedbackChildren = new ArrayList<>();
        feedbackChildren.add(createChild("意见收集", "fa-solid fa-comment", "用户提交的意见和建议"));
        feedbackChildren.add(createChild("问题处理", "fa-solid fa-comment-medical", "用户遇到的问题及解决进展"));
        feedbackChildren.add(createChild("满意度调查", "fa-solid fa-star-half-stroke", "用户对产品的满意度评分"));

        feedbackChildren.forEach(child -> child.setParentId(userFeedback.getId()));

        userFeedback.setChildren(feedbackChildren);
        dataAssets.add(userFeedback);
        dataAssets.addAll(feedbackChildren);

        // 研发域
        AssetCatalogEntity rAndDDomain = new AssetCatalogEntity();
        rAndDDomain.setId(IdUtil.getSnowflakeNextId());
        rAndDDomain.setParentId(0L);

        rAndDDomain.setName("研发域");
        rAndDDomain.setIcon("fa-solid fa-code-branch");
        rAndDDomain.setRemark("研发相关的数据资源管理");

        dataAssets.add(rAndDDomain);

        // 产品设计
        AssetCatalogEntity productDesign = new AssetCatalogEntity();
        productDesign.setId(IdUtil.getSnowflakeNextId());
        productDesign.setParentId(rAndDDomain.getId());

        productDesign.setName("产品设计");
        productDesign.setIcon("fa-solid fa-drafting-compass");
        productDesign.setRemark("产品的功能设计、界面设计等");

        List<AssetCatalogEntity> designChildren = new ArrayList<>();
        designChildren.add(createChild("功能需求", "fa-solid fa-list-ul", "产品的功能需求文档"));
        designChildren.add(createChild("界面原型", "fa-solid fa-layer-group", "产品的UI/UX原型图"));
        designChildren.add(createChild("设计规范", "fa-solid fa-code", "产品的设计指南和规范"));

        designChildren.forEach(child -> child.setParentId(productDesign.getId()));

        productDesign.setChildren(designChildren);
        dataAssets.add(productDesign);
        dataAssets.addAll(designChildren);

        // 开发管理
        AssetCatalogEntity developmentManagement = new AssetCatalogEntity();
        developmentManagement.setId(IdUtil.getSnowflakeNextId());
        developmentManagement.setParentId(rAndDDomain.getId());

        developmentManagement.setName("开发管理");
        developmentManagement.setIcon("fa-solid fa-laptop-code");
        developmentManagement.setRemark("代码版本控制、开发流程、测试流程等");

        List<AssetCatalogEntity> devManagementChildren = new ArrayList<>();
        devManagementChildren.add(createChild("版本控制", "fa-solid fa-code-branch", "代码仓库和版本控制记录"));
        devManagementChildren.add(createChild("开发流程", "fa-solid fa-diagram-project", "软件开发生命周期"));
        devManagementChildren.add(createChild("测试流程", "fa-solid fa-bug", "软件测试的流程和结果"));

        devManagementChildren.forEach(child -> child.setParentId(developmentManagement.getId()));

        developmentManagement.setChildren(devManagementChildren);
        dataAssets.add(developmentManagement);
        dataAssets.addAll(devManagementChildren);

        // 技术文档
        AssetCatalogEntity technicalDocumentation = new AssetCatalogEntity();
        technicalDocumentation.setId(IdUtil.getSnowflakeNextId());
        technicalDocumentation.setParentId(rAndDDomain.getId());

        technicalDocumentation.setName("技术文档");
        technicalDocumentation.setIcon("fa-solid fa-file-code");
        technicalDocumentation.setRemark("技术手册、API文档、内部知识库等");

        List<AssetCatalogEntity> documentationChildren = new ArrayList<>();
        documentationChildren.add(createChild("技术手册", "fa-solid fa-book", "技术指导和操作手册"));
        documentationChildren.add(createChild("API文档", "fa-solid fa-file-code", "对外提供的API接口文档"));
        documentationChildren.add(createChild("内部知识", "fa-solid fa-folder-open", "内部共享的技术知识和经验"));

        documentationChildren.forEach(child -> child.setParentId(technicalDocumentation.getId()));

        technicalDocumentation.setChildren(documentationChildren);
        dataAssets.add(technicalDocumentation);
        dataAssets.addAll(documentationChildren);

        return dataAssets;
    }

    private static AssetCatalogEntity createChild(String name, String icon, String desc) {
        AssetCatalogEntity child = new AssetCatalogEntity();
        child.setName(name);
        child.setIcon(icon);
        child.setRemark(desc);
        return child;
    }

    public static List<LabelEntity> initializeProductionLabels() {
        List<LabelEntity> labels = new ArrayList<>();

        // 初始化9个生产实例的数据标签
        LabelEntity label1 = new LabelEntity();
        label1.setId(IdUtil.getSnowflakeNextId());
        label1.setLabelName("服务器型号");
        label1.setLabelKey("server_model");
        label1.setLabelValue("server_model");
        label1.setLabelType("硬件");
        labels.add(label1);

        LabelEntity label2 = new LabelEntity();
        label2.setId(IdUtil.getSnowflakeNextId());
        label2.setLabelName("操作系统版本");
        label2.setLabelKey("os_version");
        label2.setLabelValue("os_version");
        label2.setLabelType("软件");
        labels.add(label2);

        LabelEntity label3 = new LabelEntity();
        label3.setId(IdUtil.getSnowflakeNextId());
        label3.setLabelName("部署区域");
        label3.setLabelKey("deployment_region");
        label3.setLabelValue("deployment_region");
        label3.setLabelType("地理位置");
        labels.add(label3);

        LabelEntity label4 = new LabelEntity();
        label4.setId(IdUtil.getSnowflakeNextId());
        label4.setLabelName("服务端口");
        label4.setLabelKey("service_port");
        label4.setLabelValue("service_port");
        label4.setLabelType("网络");
        labels.add(label4);

        LabelEntity label5 = new LabelEntity();
        label5.setId(IdUtil.getSnowflakeNextId());
        label5.setLabelName("数据库类型");
        label5.setLabelKey("database_type");
        label5.setLabelValue("database_type");
        label5.setLabelType("数据库");
        labels.add(label5);

        LabelEntity label6 = new LabelEntity();
        label6.setId(IdUtil.getSnowflakeNextId());
        label6.setLabelName("应用版本");
        label6.setLabelKey("app_version");
        label6.setLabelValue("app_version");
        label6.setLabelType("软件");
        labels.add(label6);

        LabelEntity label7 = new LabelEntity();
        label7.setId(IdUtil.getSnowflakeNextId());
        label7.setLabelName("负载均衡器");
        label7.setLabelKey("load_balancer");
        label7.setLabelValue("load_balancer");
        label7.setLabelType("网络");
        labels.add(label7);

        LabelEntity label8 = new LabelEntity();
        label8.setId(IdUtil.getSnowflakeNextId());
        label8.setLabelName("存储容量");
        label8.setLabelKey("storage_capacity");
        label8.setLabelValue("storage_capacity");
        label8.setLabelType("硬件");
        labels.add(label8);

        LabelEntity label9 = new LabelEntity();
        label9.setId(IdUtil.getSnowflakeNextId());
        label9.setLabelName("安全组");
        label9.setLabelKey("security_group");
        label9.setLabelValue("security_group");
        label9.setLabelType("网络安全");
        labels.add(label9);

        return labels;
    }

}