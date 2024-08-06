package com.alinesno.infra.data.assets.sample.builder;

import com.alinesno.infra.data.assets.entity.AssetDataEntity;
import com.alinesno.infra.data.assets.enums.DataLocationEnums;
import com.alinesno.infra.data.assets.enums.DataTypeEnums;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class DataAssetGenerator {

    public static List<AssetDataEntity> generateSampleData() {
        List<AssetDataEntity> assetDataList = new ArrayList<>();

        // 1. 数据库
        AssetDataEntity dbAsset = new AssetDataEntity();
        dbAsset.setDataName("客户信息库");
        dbAsset.setDataDescription("存储客户基本信息及交易记录的数据库");
        dbAsset.setDataType(DataTypeEnums.DATABASE.getValue());
        dbAsset.setDataSource("销售部");
        dbAsset.setDataOwner("信息技术部");
        dbAsset.setDataStatus("normal");
        dbAsset.setDataSize(500L * 1024 * 1024); // 500 MB
        dbAsset.setDataLocation(DataLocationEnums.CLOUD_STORAGE.getValue());
        assetDataList.add(dbAsset);

        // 2. 图片库
        AssetDataEntity imageAsset = new AssetDataEntity();
        imageAsset.setDataName("产品图片库");
        imageAsset.setDataDescription("包含产品图片和宣传材料的图库");
        imageAsset.setDataType(DataTypeEnums.IMAGE.getValue());
        imageAsset.setDataSource("市场部");
        imageAsset.setDataOwner("市场部");
        imageAsset.setDataStatus("normal");
        imageAsset.setDataSize(20L * 1024 * 1024 * 1024); // 20 GB
        imageAsset.setDataLocation(DataLocationEnums.DISTRIBUTED_STORAGE.getValue());
        assetDataList.add(imageAsset);

        // 3. 文档库
        AssetDataEntity documentAsset = new AssetDataEntity();
        documentAsset.setDataName("公司政策文档库");
        documentAsset.setDataDescription("公司内部政策和流程的文档库");
        documentAsset.setDataType(DataTypeEnums.DOCUMENT.getValue());
        documentAsset.setDataSource("行政管理部");
        documentAsset.setDataOwner("行政管理部");
        documentAsset.setDataStatus("normal");
        documentAsset.setDataSize(5L * 1024 * 1024 * 1024); // 5 GB
        documentAsset.setDataLocation(DataLocationEnums.LOCAL_STORAGE.getValue());
        assetDataList.add(documentAsset);

        // 4. 视频资料
        AssetDataEntity videoAsset = new AssetDataEntity();
        videoAsset.setDataName("培训视频资料");
        videoAsset.setDataDescription("员工培训视频资料");
        videoAsset.setDataType(DataTypeEnums.VIDEO.getValue());
        videoAsset.setDataSource("人力资源部");
        videoAsset.setDataOwner("人力资源部");
        videoAsset.setDataStatus("normal");
        videoAsset.setDataSize(100L * 1024 * 1024 * 1024); // 100 GB
        videoAsset.setDataLocation(DataLocationEnums.CLOUD_STORAGE.getValue());
        assetDataList.add(videoAsset);

        // 5. 财务库
        AssetDataEntity financeAsset = new AssetDataEntity();
        financeAsset.setDataName("财务管理库");
        financeAsset.setDataDescription("用于财务管理的软件库");
        financeAsset.setDataType(DataTypeEnums.DATABASE.getValue());
        financeAsset.setDataSource("财务部");
        financeAsset.setDataOwner("财务部");
        financeAsset.setDataStatus("normal");
        financeAsset.setDataSize(300L * 1024 * 1024); // 300 MB
        financeAsset.setDataLocation(DataLocationEnums.LOCAL_STORAGE.getValue());
        assetDataList.add(financeAsset);

        // 6. 库存库
        AssetDataEntity inventoryAsset = new AssetDataEntity();
        inventoryAsset.setDataName("库存管理库");
        inventoryAsset.setDataDescription("用于跟踪和管理库存的软件库");
        inventoryAsset.setDataType(DataTypeEnums.DATABASE.getValue());
        inventoryAsset.setDataSource("物流部");
        inventoryAsset.setDataOwner("物流部");
        inventoryAsset.setDataStatus("normal");
        inventoryAsset.setDataSize(250L * 1024 * 1024); // 250 MB
        inventoryAsset.setDataLocation(DataLocationEnums.CLOUD_STORAGE.getValue());
        assetDataList.add(inventoryAsset);

        // 7. 人力资源库
        AssetDataEntity hrAsset = new AssetDataEntity();
        hrAsset.setDataName("人力资源信息库");
        hrAsset.setDataDescription("员工信息管理和人力资源流程的软件库");
        hrAsset.setDataType(DataTypeEnums.DATABASE.getValue());
        hrAsset.setDataSource("人力资源部");
        hrAsset.setDataOwner("人力资源部");
        hrAsset.setDataStatus("normal");
        hrAsset.setDataSize(400L * 1024 * 1024); // 400 MB
        hrAsset.setDataLocation(DataLocationEnums.LOCAL_STORAGE.getValue());
        assetDataList.add(hrAsset);

        // 8. 客户关系管理库
        AssetDataEntity crmAsset = new AssetDataEntity();
        crmAsset.setDataName("客户关系库");
        crmAsset.setDataDescription("用于客户关系管理和销售追踪的软件库");
        crmAsset.setDataType(DataTypeEnums.DATABASE.getValue());
        crmAsset.setDataSource("销售部");
        crmAsset.setDataOwner("销售部");
        crmAsset.setDataStatus("normal");
        crmAsset.setDataSize(600L * 1024 * 1024); // 600 MB
        crmAsset.setDataLocation(DataLocationEnums.CLOUD_STORAGE.getValue());
        assetDataList.add(crmAsset);

        // 9. 法律文档库
        AssetDataEntity legalAsset = new AssetDataEntity();
        legalAsset.setDataName("法律文档库");
        legalAsset.setDataDescription("法律协议和合同的文档库");
        legalAsset.setDataType(DataTypeEnums.DOCUMENT.getValue());
        legalAsset.setDataSource("法务部");
        legalAsset.setDataOwner("法务部");
        legalAsset.setDataStatus("normal");
        legalAsset.setDataSize(3L * 1024 * 1024 * 1024); // 3 GB
        legalAsset.setDataLocation(DataLocationEnums.LOCAL_STORAGE.getValue());
        assetDataList.add(legalAsset);

        // 10. 项目管理平台
        AssetDataEntity projectAsset = new AssetDataEntity();
        projectAsset.setDataName("项目管理平台");
        projectAsset.setDataDescription("用于项目规划和进度跟踪的软件平台");
        projectAsset.setDataType(DataTypeEnums.DATABASE.getValue());
        projectAsset.setDataSource("项目管理办公室");
        projectAsset.setDataOwner("项目管理办公室");
        projectAsset.setDataStatus("normal");
        projectAsset.setDataSize(450L * 1024 * 1024); // 450 MB
        projectAsset.setDataLocation(DataLocationEnums.CLOUD_STORAGE.getValue());
        assetDataList.add(projectAsset);

        // 11. 内部论坛
        AssetDataEntity forumAsset = new AssetDataEntity();
        forumAsset.setDataName("内部交流论坛");
        forumAsset.setDataDescription("员工之间交流和分享知识的在线平台");
        forumAsset.setDataType(DataTypeEnums.TEXT.getValue());
        forumAsset.setDataSource("信息技术部");
        forumAsset.setDataOwner("信息技术部");
        forumAsset.setDataStatus("normal");
        forumAsset.setDataSize(15L * 1024 * 1024 * 1024); // 15 GB
        forumAsset.setDataLocation(DataLocationEnums.DISTRIBUTED_STORAGE.getValue());
        assetDataList.add(forumAsset);

        // 12. 外部合作伙伴门户
        AssetDataEntity portalAsset = new AssetDataEntity();
        portalAsset.setDataName("外部合作伙伴门户");
        portalAsset.setDataDescription("与外部供应商和合作伙伴沟通的在线门户");
        portalAsset.setDataType(DataTypeEnums.TEXT.getValue());
        portalAsset.setDataSource("供应链管理部");
        portalAsset.setDataOwner("供应链管理部");
        portalAsset.setDataStatus("normal");
        portalAsset.setDataSize(10L * 1024 * 1024 * 1024); // 10 GB
        portalAsset.setDataLocation(DataLocationEnums.CLOUD_STORAGE.getValue());
        assetDataList.add(portalAsset);

        // 13. 质量管理库
        AssetDataEntity qmsAsset = new AssetDataEntity();
        qmsAsset.setDataName("质量管理库");
        qmsAsset.setDataDescription("用于质量控制和改进过程的软件库");
        qmsAsset.setDataType(DataTypeEnums.DATABASE.getValue());
        qmsAsset.setDataSource("质量管理部");
        qmsAsset.setDataOwner("质量管理部");
        qmsAsset.setDataStatus("normal");
        qmsAsset.setDataSize(200L * 1024 * 1024); // 200 MB
        qmsAsset.setDataLocation(DataLocationEnums.LOCAL_STORAGE.getValue());
        assetDataList.add(qmsAsset);

        // 14. 电子邮件服务器
        AssetDataEntity emailAsset = new AssetDataEntity();
        emailAsset.setDataName("企业电子邮件库");
        emailAsset.setDataDescription("公司内部使用的电子邮件服务器");
        emailAsset.setDataType(DataTypeEnums.TEXT.getValue());
        emailAsset.setDataSource("信息技术部");
        emailAsset.setDataOwner("信息技术部");
        emailAsset.setDataStatus("normal");
        emailAsset.setDataSize(50L * 1024 * 1024 * 1024); // 50 GB
        emailAsset.setDataLocation(DataLocationEnums.CLOUD_STORAGE.getValue());
        assetDataList.add(emailAsset);

        // 15. 文件存储
        AssetDataEntity fileAsset = new AssetDataEntity();
        fileAsset.setDataName("文件存库");
        fileAsset.setDataDescription("公司内部文件共享和存储的服务器");
        fileAsset.setDataType(DataTypeEnums.DOCUMENT.getValue());
        fileAsset.setDataSource("信息技术部");
        fileAsset.setDataOwner("信息技术部");
        fileAsset.setDataStatus("normal");
        fileAsset.setDataSize(10L * 1024 * 1024 * 1024); // 10 GB
        fileAsset.setDataLocation(DataLocationEnums.LOCAL_STORAGE.getValue());
        assetDataList.add(fileAsset);

        return assetDataList;
    }

}