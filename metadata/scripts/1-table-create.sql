/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2018-4-16 17:53:38                           */
/*==============================================================*/


/*==============================================================*/
/* Table: BPM_AGENT                                             */
/*==============================================================*/
CREATE TABLE BPM_AGENT
(
   AGENT_ID_            VARCHAR(64) NOT NULL COMMENT '代理ID',
   SUBJECT_             VARCHAR(100) NOT NULL,
   TO_USER_ID_          VARCHAR(64) NOT NULL COMMENT '代理人ID',
   AGENT_USER_ID_       VARCHAR(64) NOT NULL COMMENT '被代理人ID',
   START_TIME_          DATETIME NOT NULL COMMENT '开始时间',
   END_TIME_            DATETIME NOT NULL COMMENT '结束时间',
   TYPE_                VARCHAR(20) NOT NULL COMMENT '代理类型
            ALL=全部代理
            PART=部分代理',
   STATUS_              VARCHAR(20) NOT NULL COMMENT '状态
            ENABLED
            DISABLED',
   DESCP_               VARCHAR(300) COMMENT '描述',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (AGENT_ID_)
);

ALTER TABLE BPM_AGENT COMMENT '流程方案代理';

/*==============================================================*/
/* Table: BPM_AGENT_SOL                                         */
/*==============================================================*/
CREATE TABLE BPM_AGENT_SOL
(
   AS_ID_               VARCHAR(64) NOT NULL COMMENT '代理方案ID',
   AGENT_ID_            VARCHAR(64) COMMENT '代理ID',
   SOL_ID_              VARCHAR(64) NOT NULL COMMENT '解决方案ID',
   SOL_NAME_            VARCHAR(100) NOT NULL COMMENT '解决方案名称',
   AGENT_TYPE_          VARCHAR(20) COMMENT '代理类型
            全部=ALL
            条件代理=PART',
   CONDITION_           TEXT COMMENT '代理条件',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (AS_ID_)
);

ALTER TABLE BPM_AGENT_SOL COMMENT '部分代理的流程方案';

/*==============================================================*/
/* Table: BPM_AUTH_DEF                                          */
/*==============================================================*/
CREATE TABLE BPM_AUTH_DEF
(
   ID_                  VARCHAR(50) NOT NULL COMMENT '主键',
   SOL_ID_              VARCHAR(50) COMMENT '解决方案ID',
   RIGHT_JSON_          VARCHAR(1000) COMMENT '权限JSON',
   SETTING_ID_          VARCHAR(50),
   TREE_ID_             VARCHAR(50) COMMENT '分类ID',
   PRIMARY KEY (ID_)
);

ALTER TABLE BPM_AUTH_DEF COMMENT '授权流程定义';

/*==============================================================*/
/* Table: BPM_AUTH_RIGHTS                                       */
/*==============================================================*/
CREATE TABLE BPM_AUTH_RIGHTS
(
   ID_                  VARCHAR(50) NOT NULL COMMENT '主键',
   RIGHT_TYPE_          VARCHAR(50) COMMENT '权限类型(def,流程定义,inst,流程实例,task,待办任务,start,发起流程)',
   TYPE_                VARCHAR(50) COMMENT '授权类型(all,全部,user,用户,group,用户组)',
   AUTH_ID_             VARCHAR(50) COMMENT '授权对象ID',
   AUTH_NAME_           VARCHAR(50) COMMENT '授权对象名称',
   SETTING_ID_          VARCHAR(50),
   PRIMARY KEY (ID_)
);

ALTER TABLE BPM_AUTH_RIGHTS COMMENT '流程定义授权';

/*==============================================================*/
/* Table: BPM_AUTH_SETTING                                      */
/*==============================================================*/
CREATE TABLE BPM_AUTH_SETTING
(
   ID_                  VARCHAR(50) NOT NULL COMMENT '主键',
   NAME_                VARCHAR(50) COMMENT '授权名称',
   ENABLE_              VARCHAR(10) COMMENT '是否允许',
   TYPE_                VARCHAR(10) COMMENT '授权类型(sol,解决方案,form,表单)',
   TENANT_ID_           VARCHAR(50) COMMENT '租户ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   CREATE_BY_           VARCHAR(50) COMMENT '创建人',
   UPDATE_BY_           VARCHAR(50) COMMENT '更新人',
   PRIMARY KEY (ID_)
);

ALTER TABLE BPM_AUTH_SETTING COMMENT '流程定义授权';

/*==============================================================*/
/* Table: BPM_BUS_LINK                                          */
/*==============================================================*/
CREATE TABLE BPM_BUS_LINK
(
   BPM_BUS_LINK_ID_     VARCHAR(64) NOT NULL COMMENT '主键',
   FORM_INST_ID_        VARCHAR(64) COMMENT '表单实例ID',
   INST_ID_             VARCHAR(64) COMMENT '流程实例ID',
   LINK_PK_             VARCHAR(64) COMMENT '第三方物理表主键ID',
   TENANT_ID_           VARCHAR(64) COMMENT '租用用户Id',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (BPM_BUS_LINK_ID_)
);

ALTER TABLE BPM_BUS_LINK COMMENT 'BPM_BUS_LINK第三方业务链接表';

/*==============================================================*/
/* Table: BPM_CHECK_FILE                                        */
/*==============================================================*/
CREATE TABLE BPM_CHECK_FILE
(
   FILE_ID_             VARCHAR(64) NOT NULL,
   FILE_NAME_           VARCHAR(255),
   JUMP_ID_             VARCHAR(64) NOT NULL,
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   PRIMARY KEY (FILE_ID_, JUMP_ID_)
);

ALTER TABLE BPM_CHECK_FILE COMMENT '审批意见附件';

/*==============================================================*/
/* Table: BPM_DEF                                               */
/*==============================================================*/
CREATE TABLE BPM_DEF
(
   DEF_ID_              VARCHAR(64) NOT NULL,
   TREE_ID_             VARCHAR(64) COMMENT '分类Id',
   SUBJECT_             VARCHAR(255) NOT NULL COMMENT '标题',
   DESCP_               VARCHAR(1024) COMMENT '描述',
   KEY_                 VARCHAR(255) NOT NULL COMMENT '标识Key',
   ACT_DEF_ID_          VARCHAR(255) COMMENT 'Activiti流程定义ID',
   ACT_DEP_ID_          VARCHAR(255) COMMENT 'ACT流程发布ID',
   STATUS_              VARCHAR(20) NOT NULL COMMENT '状态',
   VERSION_             INT NOT NULL COMMENT '版本号',
   IS_MAIN_             VARCHAR(20) COMMENT '主版本',
   SETTING_             TEXT COMMENT '定义属性设置',
   MODEL_ID_            VARCHAR(64) NOT NULL COMMENT '设计模型ID
            关联Activiti中的ACT_RE_MODEL表主键',
   MAIN_DEF_ID_         VARCHAR(64) COMMENT '主定义ID',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (DEF_ID_)
);

ALTER TABLE BPM_DEF COMMENT '流程定义';

/*==============================================================*/
/* Table: BPM_FM_ATT                                            */
/*==============================================================*/
CREATE TABLE BPM_FM_ATT
(
   ATT_ID_              VARCHAR(64) NOT NULL,
   TITLE_               VARCHAR(64) NOT NULL COMMENT '属性标签',
   KEY_                 VARCHAR(100) NOT NULL COMMENT '属性标识',
   DATA_TYPE_           VARCHAR(64) NOT NULL COMMENT '属性数据类型
            String
            Date
            Float
            Double
            
            元数据类型或外部模型ID',
   TYPE_                VARCHAR(64) COMMENT '属性类型
            元数据=META
            集合=COLLECTION-MODEL
            外部数据模型=EXT_MODEL',
   DEF_VAL_             VARCHAR(255) COMMENT '默认值',
   REQUIRED_            VARCHAR(20) NOT NULL COMMENT '是否必须',
   LEN_                 INT COMMENT '长度',
   PREC_                INT COMMENT '精度(小数位)',
   SN_                  INT NOT NULL COMMENT '序号',
   GROUP_TITLE_         VARCHAR(100) COMMENT '分组标题',
   REMARK_              VARCHAR(512) COMMENT '属性帮助描述',
   CTL_TYPE_            VARCHAR(50) COMMENT '控件类型
            界面绑定的控件类型',
   CTL_CONFIG_          VARCHAR(512) COMMENT '控件配置参数(JSON配置)',
   REF_FM_ID_           VARCHAR(64),
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构Id',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (ATT_ID_)
);

ALTER TABLE BPM_FM_ATT COMMENT '数据模型属性定义';

/*==============================================================*/
/* Table: BPM_FORMBO_SETTING                                    */
/*==============================================================*/
CREATE TABLE BPM_FORMBO_SETTING
(
   ID_                  VARCHAR(64) NOT NULL COMMENT '主键',
   SOL_ID_              VARCHAR(64) COMMENT '节点配置ID',
   NODE_ID_             VARCHAR(64) COMMENT '节点ID',
   ENT_NAME_            VARCHAR(64) COMMENT '实体名称',
   ATTR_NAME_           VARCHAR(64) COMMENT '属性名称',
   INIT_SETTING_        VARCHAR(500) COMMENT '初始设定',
   SAVE_SETTING_        VARCHAR(500) COMMENT '保存时设定',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   CREATE_BY_           VARCHAR(64),
   UPDATE_BY_           VARCHAR(64),
   UPDATE_TIME_         DATETIME,
   TENANT_ID_           VARCHAR(64),
   PRIMARY KEY (ID_)
);

ALTER TABLE BPM_FORMBO_SETTING COMMENT '表单数据配置';

/*==============================================================*/
/* Table: BPM_FORM_INST                                         */
/*==============================================================*/
CREATE TABLE BPM_FORM_INST
(
   FORM_INST_ID_        VARCHAR(64) NOT NULL,
   SUBJECT_             VARCHAR(127) COMMENT '实例标题',
   INST_ID_             VARCHAR(64) COMMENT '流程实例ID',
   ACT_INST_ID_         VARCHAR(64) COMMENT 'ACT实例ID',
   ACT_DEF_ID_          VARCHAR(64) COMMENT 'ACT定义ID',
   DEF_ID_              VARCHAR(64) COMMENT '流程定义ID',
   SOL_ID_              VARCHAR(64) COMMENT '解决方案ID',
   FM_ID_               VARCHAR(64) COMMENT '数据模型ID',
   FM_VIEW_ID_          VARCHAR(64) COMMENT '表单视图ID',
   STATUS_              VARCHAR(20) COMMENT '状态',
   JSON_DATA_           TEXT COMMENT '数据JSON',
   IS_PERSIST_          VARCHAR(20) COMMENT '是否持久化',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构Id',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (FORM_INST_ID_)
);

ALTER TABLE BPM_FORM_INST COMMENT '流程数据模型实例';

/*==============================================================*/
/* Table: BPM_FORM_RIGHT                                        */
/*==============================================================*/
CREATE TABLE BPM_FORM_RIGHT
(
   ID_                  VARCHAR(64) NOT NULL COMMENT '主键',
   SOL_ID_              VARCHAR(64) COMMENT '流程解决方案ID',
   ACT_DEF_ID_          VARCHAR(64) COMMENT '流程定义ID',
   NODE_ID_             VARCHAR(255) COMMENT '节点ID',
   FORM_ALIAS_          VARCHAR(255) COMMENT '表单别名',
   JSON_                TEXT COMMENT '配置的JSON',
   BODEF_ID_            VARCHAR(64) COMMENT '流程的BO定义ID',
   TENANT_ID_           VARCHAR(64) COMMENT '租户ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (ID_)
);

ALTER TABLE BPM_FORM_RIGHT COMMENT '表单权限';

/*==============================================================*/
/* Table: BPM_FORM_TEMPLATE                                     */
/*==============================================================*/
CREATE TABLE BPM_FORM_TEMPLATE
(
   ID_                  VARCHAR(64) NOT NULL COMMENT '主键',
   NAME_                VARCHAR(50) COMMENT '模版名称',
   ALIAS_               VARCHAR(50) COMMENT '别名',
   TEMPLATE_            TEXT COMMENT '模版',
   TYPE_                VARCHAR(50) COMMENT '模版类型 (pc,mobile)',
   INIT_                SMALLINT COMMENT '初始添加的(1是,0否)',
   TENANT_ID_           VARCHAR(64) COMMENT '租户ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   CATEGORY_            VARCHAR(50) COMMENT '类别',
   PRIMARY KEY (ID_)
);

ALTER TABLE BPM_FORM_TEMPLATE COMMENT '表单模版';

/*==============================================================*/
/* Table: BPM_FORM_VIEW                                         */
/*==============================================================*/
CREATE TABLE BPM_FORM_VIEW
(
   VIEW_ID_             VARCHAR(64) NOT NULL,
   TREE_ID_             VARCHAR(64) COMMENT '分类ID',
   NAME_                VARCHAR(255) NOT NULL COMMENT '名称',
   KEY_                 VARCHAR(100) NOT NULL COMMENT '标识键',
   TYPE_                VARCHAR(50) NOT NULL COMMENT '类型
            ONLINE=在线表单
            URL=线下定制表单',
   RENDER_URL_          VARCHAR(255) COMMENT '表单展示URL',
   VERSION_             INT NOT NULL,
   IS_MAIN_             VARCHAR(20) NOT NULL COMMENT '是否为主版本',
   MAIN_VIEW_ID_        VARCHAR(64) COMMENT '隶属主版本视图ID',
   DESCP_               VARCHAR(500) COMMENT '视图说明',
   STATUS_              VARCHAR(20) NOT NULL COMMENT '状态',
   IS_BIND_MD_          VARCHAR(20) COMMENT '是否绑定业务模型
            YES=是
            NO=否',
   TEMPLATE_VIEW_       TEXT COMMENT '模板类型ID
            用于生成视图的模板类型ID',
   TEMPLATE_ID_         VARCHAR(64) COMMENT '模板内容',
   DISPLAY_TYPE_        VARCHAR(64) COMMENT 'tab展示模式normal first',
   TEMPLATE_            TEXT COMMENT '转换过的模板',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构Id',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   BO_DEFID_            VARCHAR(20) COMMENT 'BO定义ID',
   TITLE_               VARCHAR(1000) COMMENT '标题',
   BUTTON_DEF_          VARCHAR(1000),
   PDF_TEMP_            TEXT COMMENT 'PDF模板',
   PRIMARY KEY (VIEW_ID_)
);

ALTER TABLE BPM_FORM_VIEW COMMENT '业务表单视图';

/*==============================================================*/
/* Table: BPM_GROUP_SCRIPT                                      */
/*==============================================================*/
CREATE TABLE BPM_GROUP_SCRIPT
(
   SCRIPT_ID_           VARCHAR(64) NOT NULL COMMENT '主键',
   CLASS_NAME_          VARCHAR(200) COMMENT '类名',
   CLASS_INS_NAME_      VARCHAR(200) COMMENT '类实例名',
   METHOD_NAME_         VARCHAR(64) COMMENT '方法名',
   METHOD_DESC_         VARCHAR(200) COMMENT '方法描述',
   RETURN_TYPE_         VARCHAR(64) COMMENT '返回类型',
   ARGUMENT_            VARCHAR(1000) COMMENT '参数定义',
   TENANT_ID_           VARCHAR(64) COMMENT '租户ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (SCRIPT_ID_)
);

ALTER TABLE BPM_GROUP_SCRIPT COMMENT '人员脚本';

/*==============================================================*/
/* Table: BPM_INST                                              */
/*==============================================================*/
CREATE TABLE BPM_INST
(
   INST_ID_             VARCHAR(64) NOT NULL,
   DEF_ID_              VARCHAR(64) NOT NULL COMMENT '流程定义ID',
   ACT_INST_ID_         VARCHAR(64) COMMENT 'Activiti实例ID',
   ACT_DEF_ID_          VARCHAR(64) NOT NULL COMMENT 'Activiti定义ID',
   SOL_ID_              VARCHAR(64) COMMENT '解决方案ID_',
   INST_NO_             VARCHAR(50) COMMENT '流程实例工单号',
   IS_USE_BMODEL_       VARCHAR(20) COMMENT '单独使用业务模型
            YES=表示不带任何表单视图',
   SUBJECT_             VARCHAR(255) COMMENT '标题',
   STATUS_              VARCHAR(20) COMMENT '运行状态',
   VERSION_             INT COMMENT '版本',
   BUS_KEY_             VARCHAR(64) COMMENT '业务键ID',
   CHECK_FILE_ID_       VARCHAR(64) COMMENT '审批正文依据ID',
   FORM_INST_ID_        VARCHAR(64) COMMENT '业务表单数据ID',
   IS_TEST_             VARCHAR(20) COMMENT '是否为测试',
   ERRORS_              TEXT,
   END_TIME_            DATETIME COMMENT '结束时间',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   DATA_SAVE_MODE_      VARCHAR(10) COMMENT '数据保存模式(all,json,db)',
   SUPPORT_MOBILE_      INT COMMENT '支持手机端',
   BO_DEF_ID_           VARCHAR(20) COMMENT 'BO定义ID',
   BILL_NO_             VARCHAR(255) COMMENT '单号',
   START_DEP_ID_        VARCHAR(64) COMMENT '发起部门ID',
   START_DEP_FULL_      VARCHAR(300) COMMENT '发起部门全名',
   PRIMARY KEY (INST_ID_)
);

ALTER TABLE BPM_INST COMMENT '流程实例';

/*==============================================================*/
/* Table: BPM_INST_ATTACH                                       */
/*==============================================================*/
CREATE TABLE BPM_INST_ATTACH
(
   ID_                  VARCHAR(64) NOT NULL,
   INST_ID_             VARCHAR(64) NOT NULL COMMENT '实例Id',
   FILE_ID_             VARCHAR(64) NOT NULL COMMENT '文件ID',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (ID_)
);

ALTER TABLE BPM_INST_ATTACH COMMENT '流程实例附件';

/*==============================================================*/
/* Table: BPM_INST_CC                                           */
/*==============================================================*/
CREATE TABLE BPM_INST_CC
(
   CC_ID_               VARCHAR(64) NOT NULL,
   SUBJECT_             VARCHAR(255) NOT NULL COMMENT '抄送标题',
   NODE_ID_             VARCHAR(255) COMMENT '节点ID',
   NODE_NAME_           VARCHAR(255) COMMENT '节点名称',
   FROM_USER_           VARCHAR(50) COMMENT '抄送人',
   FROM_USER_ID_        VARCHAR(255) COMMENT '抄送人ID',
   SOL_ID_              VARCHAR(64) COMMENT '解决方案ID',
   TREE_ID_             VARCHAR(64) COMMENT '分类ID',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   INST_ID_             VARCHAR(64),
   PRIMARY KEY (CC_ID_)
);

ALTER TABLE BPM_INST_CC COMMENT '流程抄送';

/*==============================================================*/
/* Table: BPM_INST_CP                                           */
/*==============================================================*/
CREATE TABLE BPM_INST_CP
(
   ID_                  VARCHAR(64) NOT NULL,
   USER_ID_             VARCHAR(64) COMMENT '用户ID',
   GROUP_ID_            VARCHAR(64) COMMENT '用户组Id',
   IS_READ_             VARCHAR(10),
   CC_ID_               VARCHAR(64) COMMENT '抄送ID',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (ID_)
);

ALTER TABLE BPM_INST_CP COMMENT '流程抄送人员';

/*==============================================================*/
/* Table: BPM_INST_CTL                                          */
/*==============================================================*/
CREATE TABLE BPM_INST_CTL
(
   CTL_ID               VARCHAR(64) NOT NULL,
   BPM_INST_ID_         VARCHAR(64),
   INST_ID_             VARCHAR(64) COMMENT '流程实例ID',
   TYPE_                VARCHAR(50) COMMENT 'READ=阅读权限
            FILE=附件权限',
   RIGHT_               VARCHAR(60) COMMENT 'ALL=全部权限
            EDIT=编辑
            DEL=删除
            PRINT=打印
            DOWN=下载',
   ALLOW_ATTEND_        VARCHAR(20),
   ALLOW_STARTOR_       VARCHAR(20) COMMENT '允许发起人
            YES',
   GROUP_IDS_           TEXT COMMENT '用户组Ids（多个用户组Id用“，”分割）',
   USER_IDS_            TEXT COMMENT '用户Ids（多个用户Id用“，”分割）',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   PRIMARY KEY (CTL_ID)
);

ALTER TABLE BPM_INST_CTL COMMENT '流程附件权限';

/*==============================================================*/
/* Table: BPM_INST_DATA                                         */
/*==============================================================*/
CREATE TABLE BPM_INST_DATA
(
   ID_                  VARCHAR(64) NOT NULL COMMENT '主键',
   BO_DEF_ID_           VARCHAR(64) COMMENT '业务对象ID',
   INST_ID_             VARCHAR(64) COMMENT '实例ID_',
   PK_                  VARCHAR(64) COMMENT '业务表主键',
   TENANT_ID_           VARCHAR(64) COMMENT '租户ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (ID_)
);

ALTER TABLE BPM_INST_DATA COMMENT '关联关系';

/*==============================================================*/
/* Index: IDX_INSTDATA_INSTID                                   */
/*==============================================================*/
CREATE INDEX IDX_INSTDATA_INSTID ON BPM_INST_DATA
(
   INST_ID_
);

/*==============================================================*/
/* Table: BPM_INST_READ                                         */
/*==============================================================*/
CREATE TABLE BPM_INST_READ
(
   READ_ID_             VARCHAR(64) NOT NULL,
   INST_ID_             VARCHAR(64) COMMENT '实例ID',
   USER_ID_             VARCHAR(64) COMMENT '用户ID',
   STATE_               VARCHAR(50) COMMENT '流程阶段',
   DEP_ID_              VARCHAR(64) COMMENT '部门ID',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (READ_ID_)
);

ALTER TABLE BPM_INST_READ COMMENT '流程实例阅读';

/*==============================================================*/
/* Table: BPM_INST_TMP                                          */
/*==============================================================*/
CREATE TABLE BPM_INST_TMP
(
   TMP_ID_              VARCHAR(64) NOT NULL COMMENT '临时ID',
   BUS_KEY_             VARCHAR(64),
   INST_ID_             VARCHAR(64) NOT NULL COMMENT '流程实例ID',
   FORM_JSON_           TEXT NOT NULL COMMENT '流程JSON',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (TMP_ID_)
);

ALTER TABLE BPM_INST_TMP COMMENT '流程实例启动临时表';

/*==============================================================*/
/* Table: BPM_JUMP_RULE                                         */
/*==============================================================*/
CREATE TABLE BPM_JUMP_RULE
(
   ID_                  VARCHAR(64) NOT NULL COMMENT '主键',
   NAME_                VARCHAR(200) COMMENT '规则名',
   SOL_ID_              VARCHAR(64) COMMENT '解决方案ID',
   ACTDEF_ID_           VARCHAR(64) COMMENT '流程定义ID',
   NODE_ID_             VARCHAR(64) COMMENT '节点ID',
   NODE_NAME_           VARCHAR(200) COMMENT '源节点名称',
   TARGET_              VARCHAR(64) COMMENT '目标节点',
   TARGET_NAME_         VARCHAR(200) COMMENT '目标节点名称',
   RULE_                VARCHAR(4000),
   SN_                  INT(11) COMMENT '序号',
   TYPE_                INT(11) COMMENT '规则类型(0,配置,1,脚本)',
   DESCRIPTION_         VARCHAR(200) COMMENT '描述',
   TENANT_ID_           VARCHAR(64) COMMENT '租户ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (ID_)
);

ALTER TABLE BPM_JUMP_RULE COMMENT '流程跳转规则';

/*==============================================================*/
/* Table: BPM_LIB                                               */
/*==============================================================*/
CREATE TABLE BPM_LIB
(
   LIB_ID_              VARCHAR(64) NOT NULL COMMENT '共享库ID',
   SOL_ID_              VARCHAR(64) NOT NULL COMMENT '解决方案ID',
   TREE_ID_             VARCHAR(64) COMMENT '共享所属分类ID',
   NAME_                VARCHAR(255) NOT NULL COMMENT '解决方案名称',
   STATUS_              VARCHAR(20) NOT NULL COMMENT '状态',
   DESCP_               VARCHAR(1024) COMMENT '描述',
   HELP_ID              VARCHAR(64) COMMENT '帮助ID',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (LIB_ID_)
);

ALTER TABLE BPM_LIB COMMENT '业务流程解决方案共享库';

/*==============================================================*/
/* Table: BPM_LIB_CMT                                           */
/*==============================================================*/
CREATE TABLE BPM_LIB_CMT
(
   CMT_ID_              VARCHAR(64) NOT NULL,
   LIB_ID_              VARCHAR(64) COMMENT '共享库ID',
   CONTENT_             VARCHAR(512) NOT NULL COMMENT '评论内容',
   LEVEL_               INT NOT NULL COMMENT '评分(0-100)',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (CMT_ID_)
);

ALTER TABLE BPM_LIB_CMT COMMENT '共享库评论';

/*==============================================================*/
/* Table: BPM_LOG                                               */
/*==============================================================*/
CREATE TABLE BPM_LOG
(
   LOG_ID_              VARCHAR(64) NOT NULL,
   SOL_ID_              VARCHAR(64) COMMENT '解决方案ID',
   INST_ID_             VARCHAR(64) COMMENT '流程实例ID',
   TASK_ID_             VARCHAR(64) COMMENT '流程任务ID',
   LOG_TYPE_            VARCHAR(50) NOT NULL COMMENT '日志分类
            
            方案本身信息操作
            业务模型
            流程表单
            测试
            
            流程实例
            流程任务',
   OP_TYPE_             VARCHAR(50) NOT NULL COMMENT '操作类型
            
            更新
            删除
            备注
            沟通
            ',
   OP_CONTENT_          VARCHAR(512) NOT NULL COMMENT '操作内容',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (LOG_ID_)
);

ALTER TABLE BPM_LOG COMMENT '流程更新日志
包括流程任务日志、流程解决方案的更新，流程实例的更新日志等。';

/*==============================================================*/
/* Table: BPM_MESSAGE_BOARD                                     */
/*==============================================================*/
CREATE TABLE BPM_MESSAGE_BOARD
(
   ID_                  VARCHAR(64) NOT NULL COMMENT '主键',
   INST_ID_             VARCHAR(64) COMMENT '流程实例ID',
   MESSAGE_AUTHOR_      VARCHAR(64) COMMENT '留言用户',
   MESSAGE_AUTHOR_ID_   VARCHAR(64) COMMENT '留言用户ID',
   MESSAGE_CONTENT_     TEXT COMMENT '留言消息',
   FILE_ID_             VARCHAR(64) COMMENT '附件ID',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   PRIMARY KEY (ID_)
);

ALTER TABLE BPM_MESSAGE_BOARD COMMENT '流程沟通留言板';

/*==============================================================*/
/* Table: BPM_MOBILE_FORM                                       */
/*==============================================================*/
CREATE TABLE BPM_MOBILE_FORM
(
   ID_                  VARCHAR(64) NOT NULL COMMENT '主键',
   NAME_                VARCHAR(50) COMMENT '名称',
   ALIAS_               VARCHAR(50) COMMENT '别名',
   FORM_HTML            TEXT COMMENT '表单模版',
   TENANT_ID_           VARCHAR(64) COMMENT '租户ID',
   TREE_ID_             VARCHAR(64) COMMENT '表单分类ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   BO_DEF_ID_           VARCHAR(64) COMMENT 'bo定义ID',
   PRIMARY KEY (ID_)
);

ALTER TABLE BPM_MOBILE_FORM COMMENT '手机表单配置表';

/*==============================================================*/
/* Table: BPM_MODULE_BIND                                       */
/*==============================================================*/
CREATE TABLE BPM_MODULE_BIND
(
   BIND_ID_             VARCHAR(64) NOT NULL,
   MODULE_NAME_         VARCHAR(50) NOT NULL COMMENT '模块名称',
   MODULE_KEY_          VARCHAR(80) NOT NULL COMMENT '模块Key',
   SOL_ID_              VARCHAR(64) COMMENT '流程解决方案ID',
   SOL_KEY_             VARCHAR(60) COMMENT '流程解决方案Key',
   SOL_NAME_            VARCHAR(100) COMMENT '流程解决方案名称',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (BIND_ID_)
);

ALTER TABLE BPM_MODULE_BIND COMMENT '流程模块方案绑定';

/*==============================================================*/
/* Table: BPM_NODE_JUMP                                         */
/*==============================================================*/
CREATE TABLE BPM_NODE_JUMP
(
   JUMP_ID_             VARCHAR(64) NOT NULL,
   ACT_DEF_ID_          VARCHAR(64) COMMENT 'ACT流程定义ID',
   ACT_INST_ID_         VARCHAR(64) COMMENT 'ACT流程实例ID',
   NODE_NAME_           VARCHAR(255) COMMENT '节点名称',
   NODE_ID_             VARCHAR(255) NOT NULL COMMENT '节点Key',
   TASK_ID_             VARCHAR(64) COMMENT '任务ID',
   COMPLETE_TIME_       DATETIME COMMENT '完成时间',
   DURATION_            BIGINT COMMENT '持续时长',
   DURATION_VAL_        INT COMMENT '有效审批时长',
   OWNER_ID_            VARCHAR(64) COMMENT '任务所属人ID',
   HANDLER_ID_          VARCHAR(64) COMMENT '处理人ID',
   AGENT_USER_ID_       VARCHAR(64) COMMENT '被代理人',
   CHECK_STATUS_        VARCHAR(50) COMMENT '审批状态',
   JUMP_TYPE_           VARCHAR(50) COMMENT '跳转类型',
   REMARK_              VARCHAR(512) COMMENT '意见备注',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   ENABLE_MOBILE_       SMALLINT COMMENT '是否支持手机',
   OPINION_NAME_        VARCHAR(50) COMMENT '字段意见名称',
   HANDLE_DEP_ID_       VARCHAR(64) COMMENT '处理部门ID',
   HANDLE_DEP_FULL_     VARCHAR(300) COMMENT '处理部门全名',
   PRIMARY KEY (JUMP_ID_)
);

ALTER TABLE BPM_NODE_JUMP COMMENT '流程流转记录';

/*==============================================================*/
/* Table: BPM_NODE_SET                                          */
/*==============================================================*/
CREATE TABLE BPM_NODE_SET
(
   SET_ID_              VARCHAR(128) NOT NULL,
   SOL_ID_              VARCHAR(64) NOT NULL COMMENT '解决方案ID',
   ACT_DEF_ID_          VARCHAR(64) COMMENT 'ACT流程定义ID',
   NODE_ID_             VARCHAR(255) NOT NULL COMMENT '节点ID',
   NAME_                VARCHAR(255) COMMENT '节点名称',
   DESCP_               VARCHAR(255) COMMENT '节点描述',
   NODE_TYPE_           VARCHAR(100) NOT NULL COMMENT '节点类型',
   NODE_CHECK_TIP_      VARCHAR(1024),
   SETTINGS_            TEXT COMMENT '节点设置',
   PRE_HANDLE_          VARCHAR(255) COMMENT '前置处理器',
   AFTER_HANDLE_        VARCHAR(255) COMMENT '后置处理器',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (SET_ID_)
);

ALTER TABLE BPM_NODE_SET COMMENT '流程定义节点配置';

/*==============================================================*/
/* Table: BPM_OPINION_LIB                                       */
/*==============================================================*/
CREATE TABLE BPM_OPINION_LIB
(
   OP_ID_               VARCHAR(64) NOT NULL,
   USER_ID_             VARCHAR(64) NOT NULL COMMENT '用户ID，为0代表公共的',
   OP_TEXT_             VARCHAR(512) NOT NULL COMMENT '审批意见',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (OP_ID_)
);

ALTER TABLE BPM_OPINION_LIB COMMENT '意见收藏表';

/*==============================================================*/
/* Table: BPM_OPINION_TEMP                                      */
/*==============================================================*/
CREATE TABLE BPM_OPINION_TEMP
(
   ID_                  VARCHAR(64) NOT NULL COMMENT '主键',
   TYPE_                VARCHAR(20) COMMENT '类型(inst,task)',
   INST_ID_             VARCHAR(64) COMMENT '任务或实例ID',
   OPINION_             VARCHAR(1000) COMMENT '意见',
   ATTACHMENT_          VARCHAR(1000) COMMENT '附件',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人',
   TENANT_ID_           VARCHAR(64) COMMENT '租户ID',
   PRIMARY KEY (ID_)
);

ALTER TABLE BPM_OPINION_TEMP COMMENT '流程临时意见';

/*==============================================================*/
/* Table: BPM_PH_TABLE                                          */
/*==============================================================*/
CREATE TABLE BPM_PH_TABLE
(
   BPM_PH_TABLE_ID_     VARCHAR(64) NOT NULL COMMENT '主键',
   VIEW_ID_             VARCHAR(64) COMMENT '表单视图ID',
   FM_TREE_ID_          VARCHAR(64) COMMENT '业务模型树分类ID',
   FM_ID_               VARCHAR(64) COMMENT '模型ID',
   STATUS_              VARCHAR(64) COMMENT '状态',
   DS_ID_               VARCHAR(64) COMMENT '数据源ID',
   DS_NAME_             VARCHAR(256) COMMENT '数据源名称',
   JSON_DATA_           TEXT COMMENT '数据JSON映射
            ',
   TENANT_ID_           VARCHAR(64) COMMENT '租用用户Id',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (BPM_PH_TABLE_ID_)
);

ALTER TABLE BPM_PH_TABLE COMMENT 'BPM_PH_TABLE物理表';

/*==============================================================*/
/* Table: BPM_REMIND_DEF                                        */
/*==============================================================*/
CREATE TABLE BPM_REMIND_DEF
(
   ID_                  VARCHAR(50) NOT NULL COMMENT '主键',
   SOL_ID_              VARCHAR(50) COMMENT '方案ID',
   ACT_DEF_ID_          VARCHAR(64) COMMENT '流程定义ID',
   NODE_ID_             VARCHAR(50) COMMENT '节点ID',
   NAME_                VARCHAR(50) COMMENT '名称',
   ACTION_              VARCHAR(50) COMMENT '到期动作',
   REL_NODE_            VARCHAR(50) COMMENT '相对节点',
   EVENT_               VARCHAR(50) COMMENT '事件',
   DATE_TYPE_           VARCHAR(50) COMMENT '日期类型',
   EXPIRE_DATE_         VARCHAR(50) COMMENT '期限',
   CONDITION_           VARCHAR(1000) COMMENT '条件',
   SCRIPT_              VARCHAR(1000) COMMENT '到期执行脚本',
   NOTIFY_TYPE_         VARCHAR(50) COMMENT '通知类型',
   TIME_TO_SEND_        VARCHAR(50) COMMENT '开始发送消息时间点',
   SEND_TIMES_          INT COMMENT '发送次数',
   SEND_INTERVAL_       VARCHAR(50) COMMENT '发送时间间隔',
   SOLUTION_NAME_       VARCHAR(50) COMMENT '解决方案名称',
   NODE_NAME_           VARCHAR(50) COMMENT '节点名称',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   TENANT_ID_           VARCHAR(64) COMMENT '租用用户Id',
   PRIMARY KEY (ID_)
);

ALTER TABLE BPM_REMIND_DEF COMMENT '催办定义';

/*==============================================================*/
/* Table: BPM_REMIND_HISTORY                                    */
/*==============================================================*/
CREATE TABLE BPM_REMIND_HISTORY
(
   ID_                  VARCHAR(50) NOT NULL COMMENT '主键',
   REMINDER_INST_ID_    VARCHAR(50) COMMENT '催办实例ID',
   REMIND_TYPE_         VARCHAR(50) COMMENT '催办类型',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   TENANT_ID_           VARCHAR(64) COMMENT '租用用户Id',
   PRIMARY KEY (ID_)
);

ALTER TABLE BPM_REMIND_HISTORY COMMENT '催办历史';

/*==============================================================*/
/* Table: BPM_REMIND_INST                                       */
/*==============================================================*/
CREATE TABLE BPM_REMIND_INST
(
   ID_                  VARCHAR(50) NOT NULL COMMENT '主键',
   SOL_ID_              VARCHAR(50) COMMENT '方案ID',
   NODE_ID_             VARCHAR(50) COMMENT '节点ID',
   TASK_ID_             VARCHAR(50) COMMENT '任务ID',
   NAME_                VARCHAR(50) COMMENT '名称',
   ACTION_              VARCHAR(50) COMMENT '到期动作',
   EXPIRE_DATE_         DATETIME COMMENT '期限',
   SCRIPT_              VARCHAR(1000) COMMENT '到期执行脚本',
   NOTIFY_TYPE_         VARCHAR(50) COMMENT '通知类型',
   TIME_TO_SEND_        DATETIME COMMENT '开始发送消息时间点',
   SEND_TIMES_          INT COMMENT '发送次数',
   SEND_INTERVAL_       INT COMMENT '发送时间间隔',
   STATUS_              VARCHAR(10) COMMENT '状态(2,完成,0,创建,1,进行中)',
   SOLUTION_NAME_       VARCHAR(50) COMMENT '方案名称',
   NODE_NAME_           VARCHAR(50) COMMENT '节点名称',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   TENANT_ID_           VARCHAR(64) COMMENT '租用用户Id',
   ACT_INST_ID_         VARCHAR(64) COMMENT '流程实例ID',
   PRIMARY KEY (ID_)
);

ALTER TABLE BPM_REMIND_INST COMMENT '催办实例表';

/*==============================================================*/
/* Table: BPM_RU_PATH                                           */
/*==============================================================*/
CREATE TABLE BPM_RU_PATH
(
   PATH_ID_             VARCHAR(64) NOT NULL,
   INST_ID_             VARCHAR(64) NOT NULL COMMENT '流程实例ID',
   ACT_DEF_ID_          VARCHAR(64) NOT NULL COMMENT 'Act定义ID',
   ACT_INST_ID_         VARCHAR(64) NOT NULL COMMENT 'Act实例ID',
   SOL_ID_              VARCHAR(64) NOT NULL COMMENT '解决方案ID',
   NODE_ID_             VARCHAR(255) NOT NULL COMMENT '节点ID',
   NODE_NAME_           VARCHAR(255) COMMENT '节点名称',
   NODE_TYPE_           VARCHAR(50) COMMENT '节点类型',
   START_TIME_          DATETIME NOT NULL COMMENT '开始时间',
   END_TIME_            DATETIME COMMENT '结束时间',
   DURATION_            INT COMMENT '持续时长',
   DURATION_VAL_        INT COMMENT '有效审批时长',
   ASSIGNEE_            VARCHAR(64) COMMENT '处理人ID',
   TO_USER_ID_          VARCHAR(64) COMMENT '代理人ID',
   IS_MULTIPLE_         VARCHAR(20) COMMENT '是否为多实例',
   EXECUTION_ID_        VARCHAR(64) COMMENT '活动执行ID',
   USER_IDS_            VARCHAR(300) COMMENT '原执行人IDS',
   PARENT_ID_           VARCHAR(64) COMMENT '父ID',
   LEVEL_               INT COMMENT '层次',
   OUT_TRAN_ID_         VARCHAR(255) COMMENT '跳出路线ID',
   TOKEN_               VARCHAR(255) COMMENT '路线令牌',
   JUMP_TYPE_           VARCHAR(50) COMMENT '跳到该节点的方式
            正常跳转
            自由跳转
            回退跳转',
   NEXT_JUMP_TYPE_      VARCHAR(50) COMMENT '下一步跳转方式',
   OPINION_             VARCHAR(500) COMMENT '审批意见',
   REF_PATH_ID_         VARCHAR(64) COMMENT '引用路径ID
            当回退时，重新生成的结点，需要记录引用的回退节点，方便新生成的路径再次回退。',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   TIMEOUT_STATUS_      VARCHAR(20) COMMENT 'timeout_status_',
   PRIMARY KEY (PATH_ID_)
);

ALTER TABLE BPM_RU_PATH COMMENT '流程实例运行路线';

/*==============================================================*/
/* Table: BPM_SIGN_DATA                                         */
/*==============================================================*/
CREATE TABLE BPM_SIGN_DATA
(
   DATA_ID_             VARCHAR(64) NOT NULL COMMENT '主键',
   ACT_DEF_ID_          VARCHAR(64) NOT NULL COMMENT '流程定义ID',
   ACT_INST_ID_         VARCHAR(64) NOT NULL COMMENT '流程实例ID',
   NODE_ID_             VARCHAR(255) NOT NULL COMMENT '流程节点Id',
   USER_ID_             VARCHAR(64) NOT NULL COMMENT '投票人ID',
   VOTE_STATUS_         VARCHAR(50) NOT NULL COMMENT '投票状态
            同意
            反对
            弃权
            ',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (DATA_ID_)
);

ALTER TABLE BPM_SIGN_DATA COMMENT '任务会签数据
运行过程中会清空该表，一般为流程实例运行过程中清空';

/*==============================================================*/
/* Table: BPM_SOLUTION                                          */
/*==============================================================*/
CREATE TABLE BPM_SOLUTION
(
   SOL_ID_              VARCHAR(64) NOT NULL,
   TREE_ID_             VARCHAR(64) COMMENT '分类Id',
   TREE_PATH_           VARCHAR(512) COMMENT '是否跳过第一步，
            代表流程启动后，是否跳过第一步',
   NAME_                VARCHAR(100) NOT NULL COMMENT '解决方案名称',
   KEY_                 VARCHAR(100) NOT NULL COMMENT '标识键',
   DEF_KEY_             VARCHAR(255) COMMENT '绑定流程KEY',
   ACT_DEF_ID_          VARCHAR(64) COMMENT 'ACT流程定义ID',
   DESCP_               VARCHAR(512) COMMENT '解决方案描述',
   STEP_                INT NOT NULL COMMENT '完成的步骤',
   IS_USE_BMODEL_       VARCHAR(20) COMMENT '单独使用业务模型
            YES=表示不带任何表单视图',
   STATUS_              VARCHAR(64) NOT NULL COMMENT '状态
            INIT =创建状态
            DEPLOYED=发布状态
            DISABLED=禁用状态',
   SUBJECT_RULE_        VARCHAR(255) COMMENT '业务标题规则',
   HELP_ID_             VARCHAR(64) COMMENT '帮助ID',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   GRANT_TYPE_          SMALLINT COMMENT '授权类型
            0=全部
            1=部分授权',
   FORMAL_              VARCHAR(10) COMMENT '是否正式(yes,no)',
   BO_DEF_ID_           VARCHAR(64) COMMENT 'BO定义ID',
   DATA_SAVE_MODE_      VARCHAR(10) COMMENT 'BO数据保存模式',
   SUPPORT_MOBILE_      INT COMMENT '支持手机端表单',
   PRIMARY KEY (SOL_ID_)
);

ALTER TABLE BPM_SOLUTION COMMENT '业务流程方案定义';

/*==============================================================*/
/* Table: BPM_SOL_CTL                                           */
/*==============================================================*/
CREATE TABLE BPM_SOL_CTL
(
   RIGHT_ID_            VARCHAR(64) NOT NULL,
   SOL_ID_              VARCHAR(64) COMMENT '解决方案ID',
   USER_IDS_            TEXT COMMENT '用户Ids（多个用户Id用“，”分割）',
   GROUP_IDS_           TEXT COMMENT '用户组Ids（多个用户组Id用“，”分割）',
   ALLOW_STARTOR_       VARCHAR(20) COMMENT '允许发起人
            YES',
   ALLOW_ATTEND_        VARCHAR(20),
   RIGHT_               VARCHAR(60) COMMENT 'ALL=全部权限
            EDIT=编辑
            DEL=删除
            PRINT=打印
            DOWN=下载',
   TYPE_                VARCHAR(50) COMMENT 'READ=阅读权限
            FILE=附件权限',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (RIGHT_ID_)
);

ALTER TABLE BPM_SOL_CTL COMMENT '流程解决方案资源访问权限控制';

/*==============================================================*/
/* Table: BPM_SOL_FM                                            */
/*==============================================================*/
CREATE TABLE BPM_SOL_FM
(
   ID_                  VARCHAR(64) NOT NULL,
   SOL_ID_              VARCHAR(64) NOT NULL COMMENT '解决方案ID',
   MOD_KEY_             VARCHAR(100) NOT NULL COMMENT '业务模型标识键',
   IS_MAIN_             VARCHAR(20) NOT NULL COMMENT '是否为主模型',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构Id',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (ID_)
);

ALTER TABLE BPM_SOL_FM COMMENT '解决方案关联的业务模型';

/*==============================================================*/
/* Table: BPM_SOL_FV                                            */
/*==============================================================*/
CREATE TABLE BPM_SOL_FV
(
   ID_                  VARCHAR(64) NOT NULL,
   SOL_ID_              VARCHAR(64) NOT NULL COMMENT '解决方案ID',
   ACT_DEF_ID_          VARCHAR(64) COMMENT 'ACT流程定义ID',
   NODE_ID_             VARCHAR(255) NOT NULL COMMENT '节点ID',
   NODE_TEXT_           VARCHAR(255) COMMENT '节点名称',
   FORM_TYPE_           VARCHAR(30) COMMENT '表单类型
            ONLINE-DESIGN=在线表单
            SEL-DEV=自定义的URL表单',
   FORM_URI_            VARCHAR(255) COMMENT '表单地址',
   FORM_NAME_           VARCHAR(255) COMMENT '表单名称',
   PRINT_URI_           VARCHAR(255) COMMENT '打印表单地址',
   PRINT_NAME_          VARCHAR(255) COMMENT '打印表单名称',
   SN_                  INT COMMENT '序号',
   MOBILE_NAME_         VARCHAR(50) COMMENT '手机表单名称',
   MOBILE_ALIAS_        VARCHAR(50) COMMENT '手机表单别名',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构Id',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   TAB_RIGHTS_          TEXT COMMENT '表单TAB权限配置',
   IS_USE_CFORM_        VARCHAR(20) COMMENT '使用权限表单',
   COND_FORMS_          TEXT COMMENT '条件表单设置',
   DATA_CONFS_          TEXT COMMENT '表单数据设定',
   MOBILE_FORMS_        TEXT COMMENT '手机表单配置',
   PRINT_FORMS_         TEXT COMMENT '打印表单配置',
   PRIMARY KEY (ID_)
);

ALTER TABLE BPM_SOL_FV COMMENT '解决方案关联的表单视图';

/*==============================================================*/
/* Table: BPM_SOL_USER                                          */
/*==============================================================*/
CREATE TABLE BPM_SOL_USER
(
   ID_                  VARCHAR(64) NOT NULL,
   SOL_ID_              VARCHAR(64) COMMENT '业务流程解决方案ID',
   ACT_DEF_ID_          VARCHAR(64) COMMENT 'ACT流程定义ID',
   NODE_ID_             VARCHAR(255) NOT NULL COMMENT '节点ID',
   NODE_NAME_           VARCHAR(255) COMMENT '节点名称',
   USER_TYPE_           VARCHAR(50) NOT NULL COMMENT '用户类型',
   USER_TYPE_NAME_      VARCHAR(100) COMMENT '用户类型名称',
   CONFIG_DESCP_        VARCHAR(512) COMMENT '配置显示信息',
   CONFIG_              VARCHAR(512) COMMENT '节点配置',
   IS_CAL_              VARCHAR(20) NOT NULL COMMENT '是否计算用户',
   CAL_LOGIC_           VARCHAR(20) NOT NULL COMMENT '集合的人员运算',
   SN_                  INT COMMENT '序号',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构Id',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   CATEGORY_            VARCHAR(20) COMMENT '类别',
   GROUP_ID_            VARCHAR(50),
   PRIMARY KEY (ID_)
);

ALTER TABLE BPM_SOL_USER COMMENT '解决方案关联的人员配置';

/*==============================================================*/
/* Table: BPM_SOL_USERGROUP                                     */
/*==============================================================*/
CREATE TABLE BPM_SOL_USERGROUP
(
   ID_                  VARCHAR(50) NOT NULL COMMENT '主键',
   GROUP_NAME_          VARCHAR(50) COMMENT '名称',
   ACT_DEF_ID_          VARCHAR(50) COMMENT 'ACT流程定义ID',
   SOL_ID_              VARCHAR(50) COMMENT '方案ID',
   GROUP_TYPE_          VARCHAR(50) COMMENT '分组类型(flow,copyto)',
   NODE_ID_             VARCHAR(50) COMMENT '节点ID',
   NODE_NAME_           VARCHAR(50) COMMENT '节点名称',
   TENANT_ID_           VARCHAR(50) COMMENT '租户ID',
   SETTING_             VARCHAR(2000) COMMENT '配置',
   SN_                  INT COMMENT '序号',
   NOTIFY_TYPE_         VARCHAR(50),
   CREATE_BY_           VARCHAR(50) COMMENT '创建人',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(50) COMMENT '更新人',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (ID_)
);

ALTER TABLE BPM_SOL_USERGROUP COMMENT '流程配置用户组';

/*==============================================================*/
/* Table: BPM_SOL_VAR                                           */
/*==============================================================*/
CREATE TABLE BPM_SOL_VAR
(
   VAR_ID_              VARCHAR(64) NOT NULL COMMENT '变量ID',
   SOL_ID_              VARCHAR(64) COMMENT '解决方案ID',
   ACT_DEF_ID_          VARCHAR(64) COMMENT 'ACT流程定义ID',
   KEY_                 VARCHAR(255) NOT NULL COMMENT '变量Key',
   NAME_                VARCHAR(255) NOT NULL COMMENT '变量名称',
   TYPE_                VARCHAR(50) NOT NULL COMMENT '类型',
   SCOPE_               VARCHAR(128) NOT NULL COMMENT '作用域
            全局为_PROCESS
            节点范围时存储节点ID
            ',
   NODE_NAME_           VARCHAR(255) COMMENT '节点名称',
   DEF_VAL_             VARCHAR(100) COMMENT '缺省值',
   EXPRESS_             VARCHAR(512) COMMENT '计算表达式',
   IS_REQ_              VARCHAR(20) COMMENT '是否必须',
   SN_                  INT COMMENT '序号',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构Id',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   FORM_FIELD_          VARCHAR(100) COMMENT '表单字段',
   PRIMARY KEY (VAR_ID_)
);

ALTER TABLE BPM_SOL_VAR COMMENT '流程解决方案变量';

/*==============================================================*/
/* Table: BPM_SQL_NODE                                          */
/*==============================================================*/
CREATE TABLE BPM_SQL_NODE
(
   BPM_SQL_NODE_ID_     VARCHAR(64) NOT NULL COMMENT '主键',
   SOL_ID_              VARCHAR(64) COMMENT '解决方案ID',
   NODE_ID_             VARCHAR(64) COMMENT '节点ID',
   NODE_TEXT_           VARCHAR(256) COMMENT '节点名称',
   SQL_                 TEXT COMMENT 'SQL语句',
   DS_ID_               VARCHAR(64) COMMENT '数据源ID',
   DS_NAME_             VARCHAR(256) COMMENT '数据源名称',
   JSON_DATA_           TEXT COMMENT '数据JSON映射
            ',
   JSON_TABLE_          TEXT COMMENT '表映射数据',
   SQL_TYPE_            SMALLINT COMMENT 'SQL类型',
   TENANT_ID_           VARCHAR(64) COMMENT '租用用户Id',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (BPM_SQL_NODE_ID_)
);

ALTER TABLE BPM_SQL_NODE COMMENT 'BPM_SQL_NODE中间表';

/*==============================================================*/
/* Table: BPM_TEST_CASE                                         */
/*==============================================================*/
CREATE TABLE BPM_TEST_CASE
(
   TEST_ID_             VARCHAR(64) NOT NULL COMMENT '测试用例ID',
   TEST_SOL_ID_         VARCHAR(64) NOT NULL COMMENT '测试方案ID',
   ACT_DEF_ID_          VARCHAR(64),
   CASE_NAME_           VARCHAR(20) NOT NULL COMMENT '用例名称',
   PARAMS_CONF_         TEXT COMMENT '参数配置',
   START_USER_ID_       VARCHAR(64) COMMENT '发起人',
   USER_CONF_           TEXT COMMENT '用户干预配置',
   INST_ID_             VARCHAR(64) COMMENT '流程实例ID',
   LAST_STATUS_         VARCHAR(20) COMMENT '执行最终状态',
   EXE_EXCEPTIONS_      TEXT COMMENT '执行异常',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (TEST_ID_)
);

ALTER TABLE BPM_TEST_CASE COMMENT '测试用例';

/*==============================================================*/
/* Table: BPM_TEST_SOL                                          */
/*==============================================================*/
CREATE TABLE BPM_TEST_SOL
(
   TEST_SOL_ID_         VARCHAR(64) NOT NULL COMMENT '测试方案ID',
   TEST_NO_             VARCHAR(64) NOT NULL COMMENT '方案编号',
   SOL_ID_              VARCHAR(64) NOT NULL COMMENT '解决方案ID',
   ACT_DEF_ID_          VARCHAR(64) NOT NULL COMMENT 'Activiti定义ID',
   MEMO_                VARCHAR(1024) COMMENT '测试方案描述',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (TEST_SOL_ID_)
);

ALTER TABLE BPM_TEST_SOL COMMENT '流程测试方案';

/*==============================================================*/
/* Table: CAL_CALENDAR                                          */
/*==============================================================*/
CREATE TABLE CAL_CALENDAR
(
   CALENDER_ID_         VARCHAR(64) NOT NULL COMMENT '日历Id',
   SETTING_ID_          VARCHAR(64) COMMENT '设定ID',
   START_TIME_          DATETIME COMMENT '开始时间',
   END_TIME_            DATETIME COMMENT '结束时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构Id',
   PRIMARY KEY (CALENDER_ID_)
);

ALTER TABLE CAL_CALENDAR COMMENT '工作日历安排';

/*==============================================================*/
/* Table: CAL_GRANT                                             */
/*==============================================================*/
CREATE TABLE CAL_GRANT
(
   GRANT_ID_            VARCHAR(64) NOT NULL,
   SETTING_ID_          VARCHAR(64),
   GRANT_TYPE_          VARCHAR(64) COMMENT '分配类型 USER/GROUP',
   BELONG_WHO_          VARCHAR(64),
   UPDATE_TIME_         DATETIME,
   UPDATE_BY_           VARCHAR(64),
   CREATE_TIME_         DATETIME,
   CREATE_BY_           VARCHAR(64),
   TENANT_ID_           VARCHAR(64),
   PRIMARY KEY (GRANT_ID_)
);

ALTER TABLE CAL_GRANT COMMENT '日历分配';

/*==============================================================*/
/* Table: CAL_SETTING                                           */
/*==============================================================*/
CREATE TABLE CAL_SETTING
(
   SETTING_ID_          VARCHAR(64) NOT NULL COMMENT '设定ID',
   CAL_NAME_            VARCHAR(64) COMMENT '日历名称',
   IS_COMMON_           VARCHAR(64) COMMENT '默认',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构Id',
   PRIMARY KEY (SETTING_ID_)
);

ALTER TABLE CAL_SETTING COMMENT '日历设定';

/*==============================================================*/
/* Table: CAL_TIME_BLOCK                                        */
/*==============================================================*/
CREATE TABLE CAL_TIME_BLOCK
(
   SETTING_ID_          VARCHAR(64) NOT NULL,
   SETTING_NAME_        VARCHAR(128),
   UPDATE_TIME_         DATETIME,
   UPDATE_BY_           VARCHAR(64),
   CREATE_TIME_         DATETIME,
   CREATE_BY_           VARCHAR(64),
   TENANT_ID_           VARCHAR(64),
   TIME_INTERVALS_      VARCHAR(1024),
   PRIMARY KEY (SETTING_ID_)
);

ALTER TABLE CAL_TIME_BLOCK COMMENT '工作时间段设定';

/*==============================================================*/
/* Table: CRM_PROVIDER                                          */
/*==============================================================*/
CREATE TABLE CRM_PROVIDER
(
   PRO_ID_              VARCHAR(64) NOT NULL COMMENT '供应商ID',
   NAME_                VARCHAR(80) NOT NULL COMMENT '供应商名',
   SHORT_DESC_          VARCHAR(100) NOT NULL COMMENT '供应商简称',
   CMP_LEVEL_           VARCHAR(20) COMMENT '单位级别',
   CMP_TYPE_            VARCHAR(20) COMMENT '单位类型
            
            来自数据字典',
   CREDIT_TYPE_         VARCHAR(20) COMMENT '信用级别
            AAAA
            AAA
            AA
            A',
   CREDIT_LIMIT_        INT COMMENT '信用额度
            元
            ',
   CREDIT_PERIOD_       INT COMMENT '信用账期 
             天
            ',
   WEB_SITE_            VARCHAR(200) COMMENT '网站',
   ADDRESS_             VARCHAR(200) COMMENT '地址',
   ZIP_                 VARCHAR(20) COMMENT '邮编',
   CONTACTOR_           VARCHAR(32) COMMENT '联系人名',
   MOBILE_              VARCHAR(20) COMMENT '联系人手机',
   PHONE_               VARCHAR(20) COMMENT '固定电话',
   WEIXIN_              VARCHAR(50) COMMENT '微信号',
   WEIBO_               VARCHAR(80) COMMENT '微博号',
   MEMO_                TEXT COMMENT '备注',
   ADDTION_FIDS_        VARCHAR(512) COMMENT '附件IDS',
   CHARGE_ID_           VARCHAR(64) COMMENT '负责人ID',
   STATUS_              VARCHAR(20) COMMENT '状态',
   ACT_INST_ID_         VARCHAR(64) COMMENT '流程实例ID',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (PRO_ID_)
);

ALTER TABLE CRM_PROVIDER COMMENT '供应商管理';

/*==============================================================*/
/* Table: HR_DUTY_INST                                          */
/*==============================================================*/
CREATE TABLE HR_DUTY_INST
(
   DUTY_INST_ID_        VARCHAR(64) NOT NULL COMMENT '排班实例ID',
   HOLIDAY_ID_          VARCHAR(64) COMMENT '假期ID',
   USER_ID_             VARCHAR(64) NOT NULL COMMENT '用户ID',
   USER_NAME_           VARCHAR(64) COMMENT '用户名称',
   DEP_ID_              VARCHAR(64) COMMENT '部门ID',
   DEP_NAME_            VARCHAR(64) COMMENT '部门名称',
   SECTION_ID_          VARCHAR(64) COMMENT '班次ID',
   SECTION_NAME_        VARCHAR(16) COMMENT '班次名称',
   SECTION_SHORT_NAME_  VARCHAR(4) COMMENT '班次简称',
   SYSTEM_ID_           VARCHAR(64) COMMENT '班制ID',
   SYSTEM_NAME_         VARCHAR(100) COMMENT '班制名字',
   TYPE_                VARCHAR(10) COMMENT '实例类型',
   DATE_                DATETIME COMMENT '日期',
   VAC_APP_             TEXT COMMENT '请假申请',
   OT_APP_              TEXT COMMENT '加班申请',
   TR_APP_              TEXT COMMENT '调休申请',
   OUT_APP_             TEXT COMMENT '出差申请',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构Id',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (DUTY_INST_ID_)
);

ALTER TABLE HR_DUTY_INST COMMENT '排班实例';

/*==============================================================*/
/* Table: HR_DUTY_INST_EXT                                      */
/*==============================================================*/
CREATE TABLE HR_DUTY_INST_EXT
(
   EXT_ID_              VARCHAR(64) NOT NULL COMMENT '排班实例扩展ID',
   DUTY_INST_ID_        VARCHAR(64) COMMENT '排班实例ID',
   START_SIGN_IN_       INT COMMENT '开始签到',
   DUTY_START_TIME_     VARCHAR(20) COMMENT '上班时间',
   END_SIGN_IN_         INT COMMENT '签到结束时间',
   EARLY_OFF_TIME_      INT COMMENT '早退计时',
   DUTY_END_TIME_       VARCHAR(20) COMMENT '下班时间',
   SIGN_OUT_TIME_       INT COMMENT '签退结束',
   SECTION_ID_          VARCHAR(64) COMMENT '班次ID',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构Id',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (EXT_ID_)
);

ALTER TABLE HR_DUTY_INST_EXT COMMENT '排班实例扩展表';

/*==============================================================*/
/* Table: INF_DOC                                               */
/*==============================================================*/
CREATE TABLE INF_DOC
(
   DOC_ID_              VARCHAR(64) NOT NULL COMMENT '文档ID',
   FOLDER_ID_           VARCHAR(64) NOT NULL COMMENT '文件夹ID',
   NAME_                VARCHAR(100) NOT NULL COMMENT '文档名称',
   CONTENT_             TEXT COMMENT '内容',
   SUMMARY_             VARCHAR(512) COMMENT '摘要',
   HAS_ATTACH_          VARCHAR(8) COMMENT '是否包括附件',
   IS_SHARE_            VARCHAR(8) NOT NULL COMMENT '是否共享',
   AUTHOR_              VARCHAR(64) COMMENT '作者',
   KEYWORDS_            VARCHAR(256) COMMENT '关键字',
   DOC_TYPE_            VARCHAR(64) COMMENT '文档类型',
   DOC_PATH_            VARCHAR(255) COMMENT '文档路径',
   SWF_PATH_            VARCHAR(256) COMMENT 'SWF文件f路径',
   USER_ID_             VARCHAR(64) COMMENT '用户ID',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (DOC_ID_)
);

ALTER TABLE INF_DOC COMMENT '文档';

/*==============================================================*/
/* Table: INF_DOC_FILE                                          */
/*==============================================================*/
CREATE TABLE INF_DOC_FILE
(
   DOC_ID_              VARCHAR(64) NOT NULL,
   FILE_ID_             VARCHAR(64) NOT NULL,
   PRIMARY KEY (DOC_ID_, FILE_ID_)
);

/*==============================================================*/
/* Table: INF_DOC_FOLDER                                        */
/*==============================================================*/
CREATE TABLE INF_DOC_FOLDER
(
   FOLDER_ID_           VARCHAR(64) NOT NULL,
   NAME_                VARCHAR(128) NOT NULL COMMENT '目录名称',
   PARENT_              VARCHAR(64) COMMENT '父目录',
   PATH_                VARCHAR(128) COMMENT '路径',
   DEPTH_               INT NOT NULL COMMENT '层次',
   SN_                  INT COMMENT '序号',
   SHARE_               VARCHAR(8) NOT NULL COMMENT '共享',
   DESCP                VARCHAR(256) COMMENT '文档描述',
   USER_ID_             VARCHAR(64) NOT NULL COMMENT '用户ID',
   TYPE_                VARCHAR(20) COMMENT '个人文档文件夹=PERSONAL
            公共文档文件夹=PUBLIC
            默认为PERSONAL',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (FOLDER_ID_)
);

ALTER TABLE INF_DOC_FOLDER COMMENT '文档文件夹';

/*==============================================================*/
/* Table: INF_DOC_RIGHT                                         */
/*==============================================================*/
CREATE TABLE INF_DOC_RIGHT
(
   RIGHT_ID_            VARCHAR(64) NOT NULL COMMENT '权限ID',
   DOC_ID_              VARCHAR(64) COMMENT '文档ID',
   FOLDER_ID_           VARCHAR(64) COMMENT '文件夹ID',
   RIGHTS_              INT NOT NULL COMMENT '权限
            文档或目录的读写修改权限
            1=读
            2=修改
            4=删除
            
            权限值可以为上面的值之和
            如：3则代表进行读，修改的操作
            
            
            ',
   IDENTITY_TYPE_       VARCHAR(64) COMMENT '授权类型',
   IDENTITY_ID_         VARCHAR(64) COMMENT '用户或组ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (RIGHT_ID_)
);

ALTER TABLE INF_DOC_RIGHT COMMENT '文档或目录的权限，只要是针对公共目录下的文档，或个人的文档的共享

某个目录或文档若没有指定某部';

/*==============================================================*/
/* Table: INF_INBOX                                             */
/*==============================================================*/
CREATE TABLE INF_INBOX
(
   REC_ID_              VARCHAR(64) NOT NULL COMMENT '接收ID',
   MSG_ID_              VARCHAR(64) NOT NULL COMMENT '消息ID',
   REC_USER_ID_         VARCHAR(64) COMMENT '接收人ID',
   REC_TYPE_            VARCHAR(20) NOT NULL COMMENT '收信=REC
            发信=SEND',
   FULLNAME_            VARCHAR(50) COMMENT '接收人名称',
   GROUP_ID_            VARCHAR(64) COMMENT '用户组ID
            0代表全公司',
   GROUP_NAME_          VARCHAR(64) COMMENT '组名',
   IS_READ_             VARCHAR(20) COMMENT '是否阅读',
   IS_DEL_              VARCHAR(20) COMMENT '是否删除',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   CREATE_BY_           VARCHAR(64) NOT NULL COMMENT '创建人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   PRIMARY KEY (REC_ID_)
);

ALTER TABLE INF_INBOX COMMENT '内部短消息收件箱';

/*==============================================================*/
/* Table: INF_INNER_MAIL                                        */
/*==============================================================*/
CREATE TABLE INF_INNER_MAIL
(
   MAIL_ID_             VARCHAR(64) NOT NULL,
   USER_ID_             VARCHAR(64) NOT NULL COMMENT '用户ID',
   SENDER_              VARCHAR(32) NOT NULL,
   CC_IDS_              TEXT COMMENT '抄送人ID列表
            用'',''分开',
   CC_NAMES_            TEXT COMMENT '抄送人姓名列表',
   SUBJECT_             VARCHAR(256) NOT NULL COMMENT '邮件标题',
   CONTENT_             TEXT NOT NULL COMMENT '邮件内容',
   SENDER_ID_           VARCHAR(64) NOT NULL,
   URGE_                VARCHAR(32) NOT NULL COMMENT '1=一般
            2=重要
            3=非常重要',
   SENDER_TIME_         DATETIME NOT NULL,
   REC_NAMES_           TEXT NOT NULL COMMENT '收件人姓名列表',
   REC_IDS_             TEXT NOT NULL COMMENT '收件人ID列表
            用'',''分隔',
   STATUS_              SMALLINT NOT NULL COMMENT '邮件状态
            1=正式邮件
            0=草稿邮件',
   FILE_IDS_            VARCHAR(500) COMMENT '附件Ids，多个附件的ID，通过,分割',
   FILE_NAMES_          VARCHAR(500) COMMENT '附件名称列表，通过,进行分割',
   FOLDER_ID_           VARCHAR(64) COMMENT '文件夹ID',
   DEL_FLAG_            VARCHAR(20) COMMENT '删除标识
            YES
            NO',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (MAIL_ID_)
);

ALTER TABLE INF_INNER_MAIL COMMENT '内部邮件';

/*==============================================================*/
/* Table: INF_INNER_MSG                                         */
/*==============================================================*/
CREATE TABLE INF_INNER_MSG
(
   MSG_ID_              VARCHAR(64) NOT NULL COMMENT '消息ID',
   CONTENT_             VARCHAR(512) NOT NULL COMMENT '消息内容',
   LINK_MSG_            VARCHAR(1024) COMMENT '消息携带连接,
            生成的消息带有连接，但本地的连接不加contextPath',
   CATEGORY_            VARCHAR(50) COMMENT '消息分类',
   SENDER_ID_           VARCHAR(50),
   SENDER_              VARCHAR(50) COMMENT '发送人名',
   DEL_FLAG_            VARCHAR(20) COMMENT '删除标识',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   CREATE_BY_           VARCHAR(64) NOT NULL COMMENT '创建人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   PRIMARY KEY (MSG_ID_)
);

ALTER TABLE INF_INNER_MSG COMMENT '内部短消息';

/*==============================================================*/
/* Table: INF_MAIL                                              */
/*==============================================================*/
CREATE TABLE INF_MAIL
(
   MAIL_ID_             VARCHAR(64) NOT NULL,
   UID_                 VARCHAR(512) COMMENT '外部邮箱标识ID',
   USER_ID_             VARCHAR(64) NOT NULL COMMENT '用户ID',
   CONFIG_ID_           VARCHAR(64) NOT NULL COMMENT '邮箱设置ID',
   FOLDER_ID_           VARCHAR(64) COMMENT '文件夹ID',
   SUBJECT_             VARCHAR(512) NOT NULL COMMENT '主题',
   CONTENT_             TEXT COMMENT '内容',
   SENDER_ADDRS_        TEXT NOT NULL COMMENT '发件人地址',
   SENDER_ALIAS_        TEXT COMMENT '发件人地址别名',
   REC_ADDRS_           TEXT NOT NULL COMMENT '收件人地址',
   REC_ALIAS_           TEXT COMMENT '收件人地址别名',
   CC_ADDRS_            TEXT COMMENT '抄送人地址',
   CC_ALIAS_            TEXT COMMENT '抄送人地址别名',
   BCC_ADDRS_           TEXT COMMENT '暗送人地址',
   BCC_ALIAS_           TEXT COMMENT '暗送人地址别名',
   SEND_DATE_           DATETIME NOT NULL COMMENT '发送日期',
   READ_FLAG_           VARCHAR(8) NOT NULL COMMENT '0:未阅
            1:已阅',
   REPLY_FLAG_          VARCHAR(8) NOT NULL COMMENT '0:未回复
            1;已回复',
   STATUS_              VARCHAR(20) COMMENT '状态
            COMMON 正常
            DELETED 删除',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (MAIL_ID_)
);

ALTER TABLE INF_MAIL COMMENT '外部邮件';

/*==============================================================*/
/* Table: INF_MAIL_BOX                                          */
/*==============================================================*/
CREATE TABLE INF_MAIL_BOX
(
   BOX_ID_              VARCHAR(64) NOT NULL,
   MAIL_ID_             VARCHAR(64) COMMENT '邮件ID',
   FOLDER_ID_           VARCHAR(64) COMMENT '文件夹ID',
   USER_ID_             VARCHAR(64) COMMENT '员工ID',
   IS_DEL_              VARCHAR(20) NOT NULL COMMENT '删除标识=YES',
   IS_READ_             VARCHAR(20) NOT NULL COMMENT '阅读标识',
   REPLY_               VARCHAR(20) NOT NULL COMMENT '回复标识',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (BOX_ID_)
);

ALTER TABLE INF_MAIL_BOX COMMENT '内部邮件收件箱';

/*==============================================================*/
/* Table: INF_MAIL_CONFIG                                       */
/*==============================================================*/
CREATE TABLE INF_MAIL_CONFIG
(
   CONFIG_ID_           VARCHAR(64) NOT NULL COMMENT '配置ID',
   USER_ID_             VARCHAR(64) NOT NULL COMMENT '用户ID',
   USER_NAME_           VARCHAR(128) COMMENT '用户名称',
   ACCOUNT_             VARCHAR(128) COMMENT '帐号名称',
   MAIL_ACCOUNT_        VARCHAR(128) NOT NULL COMMENT '外部邮件地址',
   MAIL_PWD_            VARCHAR(128) NOT NULL COMMENT '外部邮件密码',
   PROTOCOL_            VARCHAR(32) NOT NULL COMMENT '协议类型
            IMAP
            POP3',
   SSL_                 VARCHAR(12) COMMENT '启用SSL
            true or false',
   SMTP_HOST_           VARCHAR(128) NOT NULL COMMENT '邮件发送主机',
   SMTP_PORT_           VARCHAR(64) NOT NULL COMMENT '邮件发送端口',
   RECP_HOST_           VARCHAR(128) NOT NULL COMMENT '接收主机',
   RECP_PORT_           VARCHAR(64) NOT NULL COMMENT '接收端口',
   IS_DEFAULT_          VARCHAR(20) NOT NULL COMMENT '是否默认
            YES
            NO',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (CONFIG_ID_)
);

ALTER TABLE INF_MAIL_CONFIG COMMENT '外部邮箱设置';

/*==============================================================*/
/* Table: INF_MAIL_FILE                                         */
/*==============================================================*/
CREATE TABLE INF_MAIL_FILE
(
   FILE_ID_             VARCHAR(64) NOT NULL,
   MAIL_ID_             VARCHAR(64) NOT NULL,
   PRIMARY KEY (FILE_ID_, MAIL_ID_)
);

ALTER TABLE INF_MAIL_FILE COMMENT '外部邮箱附件';

/*==============================================================*/
/* Table: INF_MAIL_FOLDER                                       */
/*==============================================================*/
CREATE TABLE INF_MAIL_FOLDER
(
   FOLDER_ID_           VARCHAR(64) NOT NULL COMMENT '文件夹编号',
   CONFIG_ID_           VARCHAR(64) COMMENT '配置ID',
   USER_ID_             VARCHAR(64) NOT NULL COMMENT '主键',
   NAME_                VARCHAR(128) NOT NULL COMMENT '文件夹名称',
   PARENT_ID_           VARCHAR(64) COMMENT '父目录',
   DEPTH_               INT NOT NULL COMMENT '目录层',
   PATH_                VARCHAR(256) COMMENT '目录路径',
   TYPE_                VARCHAR(32) NOT NULL COMMENT '文件夹类型
            RECEIVE-FOLDER=收信箱
            SENDER-FOLDEr=发信箱
            DRAFT-FOLDER=草稿箱
            DEL-FOLDER=删除箱
            OTHER-FOLDER=其他',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   IN_OUT_              VARCHAR(20) COMMENT '内部外部邮箱标识
            IN=内部
            OUT=外部',
   PRIMARY KEY (FOLDER_ID_)
);

ALTER TABLE INF_MAIL_FOLDER COMMENT '邮件文件夹';

/*==============================================================*/
/* Table: INS_COLUMN_DEF                                        */
/*==============================================================*/
CREATE TABLE INS_COLUMN_DEF
(
   COL_ID_              VARCHAR(64) NOT NULL COMMENT '栏目ID',
   NAME_                VARCHAR(100) COMMENT '栏目名',
   KEY_                 VARCHAR(64) COMMENT '栏目别名',
   DATA_URL_            VARCHAR(200) COMMENT '更多URL',
   IS_DEFAULT_          VARCHAR(20) COMMENT '是否默认',
   TEMPLET_             VARCHAR(4000) COMMENT '模板',
   FUNCTION_            VARCHAR(100) COMMENT '获取数据方法',
   IS_NEWS_             VARCHAR(20) COMMENT '是否为新闻公告栏目',
   TENANT_ID_           VARCHAR(64) COMMENT '租户ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (COL_ID_)
);

ALTER TABLE INS_COLUMN_DEF COMMENT '栏目定义';

/*==============================================================*/
/* Table: INS_COL_NEW                                           */
/*==============================================================*/
CREATE TABLE INS_COL_NEW
(
   ID_                  VARCHAR(64) NOT NULL COMMENT 'ID_',
   NEW_ID_              VARCHAR(64) NOT NULL COMMENT '新闻ID',
   SN_                  INT NOT NULL COMMENT '序号',
   START_TIME_          DATETIME NOT NULL COMMENT '有效开始时间',
   END_TIME_            DATETIME NOT NULL COMMENT '有效结束时间',
   IS_LONG_VALID_       VARCHAR(20) COMMENT '是否长期有效',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (ID_)
);

ALTER TABLE INS_COL_NEW COMMENT '信息所属栏目';

/*==============================================================*/
/* Table: INS_COL_NEW_DEF                                       */
/*==============================================================*/
CREATE TABLE INS_COL_NEW_DEF
(
   ID_                  VARCHAR(64) NOT NULL COMMENT '主键',
   COL_ID_              VARCHAR(64) COMMENT '栏目ID',
   NEW_ID_              VARCHAR(64) COMMENT '新闻ID',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (ID_)
);

ALTER TABLE INS_COL_NEW_DEF COMMENT '栏目新闻关联表';

/*==============================================================*/
/* Table: INS_MSGBOX_BOX_DEF                                    */
/*==============================================================*/
CREATE TABLE INS_MSGBOX_BOX_DEF
(
   ID_                  VARCHAR(64) NOT NULL COMMENT '主键',
   BOX_ID_              VARCHAR(64) COMMENT '盒子ID',
   MSG_ID_              VARCHAR(64) COMMENT '消息ID',
   SN_                  VARCHAR(16) COMMENT '序号',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (ID_)
);

ALTER TABLE INS_MSGBOX_BOX_DEF COMMENT '消息和消息盒子关联表';

/*==============================================================*/
/* Table: INS_MSGBOX_DEF                                        */
/*==============================================================*/
CREATE TABLE INS_MSGBOX_DEF
(
   BOX_ID_              VARCHAR(64) NOT NULL COMMENT '盒子ID',
   COL_ID_              VARCHAR(64) COMMENT '栏目ID',
   KEY_                 VARCHAR(64) COMMENT '标识键',
   NAME_                VARCHAR(64) COMMENT '名字',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (BOX_ID_)
);

ALTER TABLE INS_MSGBOX_DEF COMMENT '栏目消息盒子表';

/*==============================================================*/
/* Table: INS_MSG_DEF                                           */
/*==============================================================*/
CREATE TABLE INS_MSG_DEF
(
   MSG_ID_              VARCHAR(64) NOT NULL COMMENT '主键',
   COLOR_               VARCHAR(64) COMMENT '颜色',
   URL_                 VARCHAR(256) COMMENT '更多URL',
   ICON_                VARCHAR(64) COMMENT '图标',
   CONTENT_             VARCHAR(64) COMMENT '标题',
   DS_NAME_             VARCHAR(64) COMMENT '数据库名字',
   DS_ALIAS_            VARCHAR(64) COMMENT '数据库别名',
   SQL_FUNC_            VARCHAR(512) COMMENT 'SQL语句',
   TYPE_                VARCHAR(64) COMMENT '类型',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (MSG_ID_)
);

ALTER TABLE INS_MSG_DEF COMMENT 'INS_MSG_DEF';

/*==============================================================*/
/* Table: INS_NEWS                                              */
/*==============================================================*/
CREATE TABLE INS_NEWS
(
   NEW_ID_              VARCHAR(64) NOT NULL,
   SUBJECT_             VARCHAR(120) NOT NULL COMMENT '标题',
   TAG_                 VARCHAR(80) COMMENT '标签',
   KEYWORDS_            VARCHAR(255) COMMENT '关键字',
   CONTENT_             TEXT COMMENT '内容',
   IS_IMG_              VARCHAR(20) COMMENT '是否为图片新闻',
   IMG_FILE_ID_         VARCHAR(64) COMMENT '图片文件ID',
   READ_TIMES_          INT NOT NULL COMMENT '阅读次数',
   AUTHOR_              VARCHAR(50) COMMENT '作者',
   ALLOW_CMT_           VARCHAR(20) COMMENT '是否允许评论',
   STATUS_              VARCHAR(20) NOT NULL COMMENT '状态',
   FILES_               VARCHAR(512) COMMENT '附件',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (NEW_ID_)
);

ALTER TABLE INS_NEWS COMMENT '信息公告';

/*==============================================================*/
/* Table: INS_NEWS_CM                                           */
/*==============================================================*/
CREATE TABLE INS_NEWS_CM
(
   COMM_ID_             VARCHAR(64) NOT NULL COMMENT '评论ID',
   NEW_ID_              VARCHAR(64) NOT NULL COMMENT '信息ID',
   FULL_NAME_           VARCHAR(50) NOT NULL COMMENT '评论人名',
   CONTENT_             VARCHAR(1024) NOT NULL COMMENT '评论内容',
   AGREE_NUMS_          INT NOT NULL COMMENT '赞同与顶',
   REFUSE_NUMS_         INT NOT NULL COMMENT '反对与鄙视次数',
   IS_REPLY_            VARCHAR(20) NOT NULL COMMENT '是否为回复',
   REP_ID_              VARCHAR(64) COMMENT '回复评论ID',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   CREATE_BY_           VARCHAR(64) NOT NULL COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (COMM_ID_)
);

ALTER TABLE INS_NEWS_CM COMMENT '公司或新闻评论';

/*==============================================================*/
/* Table: INS_NEWS_CTL                                          */
/*==============================================================*/
CREATE TABLE INS_NEWS_CTL
(
   CTL_ID_              VARCHAR(64) NOT NULL COMMENT '主键',
   NEWS_ID_             VARCHAR(64) COMMENT '新闻ID',
   USER_ID_             VARCHAR(512) COMMENT '用户ID',
   GROUP_ID_            VARCHAR(512) COMMENT '组ID',
   RIGHT_               VARCHAR(16) COMMENT '权限分类',
   TYPE_                VARCHAR(16) COMMENT '权限类型',
   TENANT_ID_           VARCHAR(64) COMMENT '租户ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (CTL_ID_)
);

ALTER TABLE INS_NEWS_CTL COMMENT '新闻公告权限表';

/*==============================================================*/
/* Table: INS_PORTAL_DEF                                        */
/*==============================================================*/
CREATE TABLE INS_PORTAL_DEF
(
   PORT_ID_             VARCHAR(64) NOT NULL COMMENT '门户ID',
   NAME_                VARCHAR(128) COMMENT '名称',
   KEY_                 VARCHAR(64) COMMENT '别名',
   IS_DEFAULT_          VARCHAR(64) COMMENT '是否默认',
   USER_ID_             VARCHAR(64) COMMENT '用户ID',
   LAYOUT_HTML_         TEXT COMMENT '布局HTML',
   PRIORITY_            INT COMMENT '优先级',
   EDIT_HTML_           TEXT COMMENT '编辑界面HTML字段',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (PORT_ID_)
);

ALTER TABLE INS_PORTAL_DEF COMMENT '门户定义';

/*==============================================================*/
/* Table: INS_PORTAL_PERMISSION                                 */
/*==============================================================*/
CREATE TABLE INS_PORTAL_PERMISSION
(
   ID_                  VARCHAR(64) NOT NULL COMMENT '权限ID',
   LAYOUT_ID_           VARCHAR(64) COMMENT '门户ID',
   TYPE_                VARCHAR(32) COMMENT '类型',
   OWNER_ID_            VARCHAR(32) COMMENT '用户或组ID',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (ID_)
);

ALTER TABLE INS_PORTAL_PERMISSION COMMENT '布局权限设置';

/*==============================================================*/
/* Table: KD_DOC                                                */
/*==============================================================*/
CREATE TABLE KD_DOC
(
   DOC_ID_              VARCHAR(64) NOT NULL,
   TREE_ID_             VARCHAR(64) COMMENT '所属分类',
   SUBJECT_             VARCHAR(128) NOT NULL COMMENT '文档标题',
   TEMP_ID_             VARCHAR(64) COMMENT '词条或知识模板ID',
   IS_ESSENCE_          VARCHAR(20) COMMENT '是否精华',
   AUTHOR_              VARCHAR(64) NOT NULL COMMENT '作者',
   AUTHOR_TYPE_         VARCHAR(20) COMMENT '作者类型
            内部=INNER
            外部=OUTER',
   AUTHOR_POS_          VARCHAR(64) COMMENT '所属岗位',
   BELONG_DEPID_        VARCHAR(64) COMMENT '所属部门ID',
   KEYWORDS_            VARCHAR(128) COMMENT '关键字',
   APPROVAL_ID_         VARCHAR(64) COMMENT '审批人ID',
   ISSUED_TIME_         DATETIME COMMENT '发布日期',
   VIEW_TIMES_          INT COMMENT '浏览次数',
   SUMMARY_             VARCHAR(512) COMMENT '摘要',
   CONTENT_             TEXT COMMENT '知识内容',
   COMP_SCORE_          NUMERIC(8,2) COMMENT '综合评分',
   TAGS                 VARCHAR(200) COMMENT '标签',
   STORE_PEROID_        INT COMMENT '存放年限
            单位为年',
   COVER_IMG_ID_        VARCHAR(64) COMMENT '封面图',
   IMG_MAPS_            TEXT COMMENT '知识地图描点信息',
   BPM_INST_ID_         VARCHAR(64) COMMENT '流程实例ID',
   ATT_FILEIDS_         VARCHAR(512) COMMENT '文档附件',
   ARCH_CLASS_          VARCHAR(20) COMMENT '归档分类
            知识文档
            知识地图
            词条',
   STATUS_              VARCHAR(20) NOT NULL COMMENT '文档状态
            废弃=abandon
            草稿=draft
            驳回=back
            待审=pending
            发布=issued
            过期=overdue
            归档=archived',
   DOC_TYPE_            VARCHAR(20) COMMENT '知识文档=KD_DOC
            词条=KD_WORD
            知识地图=KD_MAP',
   VERSION_             INT,
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   PRIMARY KEY (DOC_ID_)
);

ALTER TABLE KD_DOC COMMENT '知识文档、地图、词条';

/*==============================================================*/
/* Table: KD_DOC_CMMT                                           */
/*==============================================================*/
CREATE TABLE KD_DOC_CMMT
(
   COMMENT_ID_          VARCHAR(64) NOT NULL COMMENT '点评ID',
   DOC_ID_              VARCHAR(64) COMMENT '知识ID',
   SCORE_               INT NOT NULL COMMENT '分数',
   CONTENT_             VARCHAR(1024) COMMENT '点评内容',
   LEVEL_               VARCHAR(20) COMMENT '非常好
            很好
            一般
            差
            很差',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   PRIMARY KEY (COMMENT_ID_)
);

ALTER TABLE KD_DOC_CMMT COMMENT '知识文档点评';

/*==============================================================*/
/* Table: KD_DOC_CONTR                                          */
/*==============================================================*/
CREATE TABLE KD_DOC_CONTR
(
   CONT_ID_             VARCHAR(64) NOT NULL,
   DOC_ID_              VARCHAR(64) COMMENT '词条',
   MOD_TYPE_            VARCHAR(50) NOT NULL COMMENT '更正错误
            内容扩充
            删除冗余
            目录结构
            概述
            基本信息栏
            内链
            排版
            参考资料
            图片',
   REASON_              VARCHAR(500),
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   PRIMARY KEY (CONT_ID_)
);

ALTER TABLE KD_DOC_CONTR COMMENT '知识文档贡献者';

/*==============================================================*/
/* Table: KD_DOC_DIR                                            */
/*==============================================================*/
CREATE TABLE KD_DOC_DIR
(
   DIR_ID_              VARCHAR(64) NOT NULL,
   DOC_ID_              VARCHAR(64) NOT NULL COMMENT '文档ID',
   LEVEL_               VARCHAR(20) NOT NULL COMMENT '标题等级
            1级标题
            2组标题',
   SUBJECT_             VARCHAR(120) NOT NULL COMMENT '标题',
   ANCHOR_              VARCHAR(255) COMMENT '标题连接锚点',
   PARENT_ID_           VARCHAR(64) COMMENT '上级目录ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   PRIMARY KEY (DIR_ID_)
);

ALTER TABLE KD_DOC_DIR COMMENT '文档索引目录';

/*==============================================================*/
/* Table: KD_DOC_FAV                                            */
/*==============================================================*/
CREATE TABLE KD_DOC_FAV
(
   FAV_ID_              VARCHAR(64) NOT NULL,
   DOC_ID_              VARCHAR(64) NOT NULL COMMENT '文档ID',
   QUE_ID_              VARCHAR(64),
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   PRIMARY KEY (FAV_ID_)
);

ALTER TABLE KD_DOC_FAV COMMENT '文档知识收藏';

/*==============================================================*/
/* Table: KD_DOC_HANDLE                                         */
/*==============================================================*/
CREATE TABLE KD_DOC_HANDLE
(
   HANDLE_ID_           VARCHAR(64) NOT NULL COMMENT '办理ID',
   DOC_ID_              VARCHAR(64) COMMENT '收发文ID',
   TYPE_                VARCHAR(20) COMMENT '办理类型
            分发
            传阅
            拟办
            批办
            承办
            注办',
   USER_ID_             VARCHAR(64) NOT NULL COMMENT '办理人ID',
   IS_READ_             SMALLINT COMMENT '是否已阅',
   OPINION_             VARCHAR(1024) COMMENT '处理意见',
   TENANT_ID_           VARCHAR(64) COMMENT '公共 - 创建者所属SAAS ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (HANDLE_ID_)
);

ALTER TABLE KD_DOC_HANDLE COMMENT '公文办理';

/*==============================================================*/
/* Table: KD_DOC_HIS                                            */
/*==============================================================*/
CREATE TABLE KD_DOC_HIS
(
   HIS_ID_              VARCHAR(64) NOT NULL,
   DOC_ID_              VARCHAR(64) COMMENT '文档ID',
   VERSION_             INT NOT NULL COMMENT '版本号',
   SUBJECT_             VARCHAR(128) NOT NULL COMMENT '文档标题',
   CONTENT_             TEXT NOT NULL COMMENT '文档内容',
   AUTHOR_              VARCHAR(50) NOT NULL COMMENT '文档作者',
   COVER_FILE_ID_       VARCHAR(64) COMMENT '文档封面',
   ATTACH_IDS_          VARCHAR(512) COMMENT '文档附件IDS',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   PRIMARY KEY (HIS_ID_)
);

ALTER TABLE KD_DOC_HIS COMMENT '知识文档历史版本';

/*==============================================================*/
/* Table: KD_DOC_READ                                           */
/*==============================================================*/
CREATE TABLE KD_DOC_READ
(
   READ_ID_             VARCHAR(64) NOT NULL COMMENT '阅读人ID',
   DOC_ID_              VARCHAR(64),
   DOC_STATUS_          VARCHAR(50) COMMENT '阅读文档阶段',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   PRIMARY KEY (READ_ID_)
);

ALTER TABLE KD_DOC_READ COMMENT '知识文档阅读';

/*==============================================================*/
/* Table: KD_DOC_REM                                            */
/*==============================================================*/
CREATE TABLE KD_DOC_REM
(
   REM_ID_              VARCHAR(64) NOT NULL COMMENT '推荐ID',
   DOC_ID_              VARCHAR(64) NOT NULL,
   DEP_ID_              VARCHAR(64),
   USER_ID_             VARCHAR(64),
   LEVEL_               INT,
   MEMO_                VARCHAR(1024),
   REC_TREE_ID_         VARCHAR(64) COMMENT '推荐精华库分类ID',
   NOTICE_CREATOR_      VARCHAR(20),
   NOTICE_TYPE_         VARCHAR(20),
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   PRIMARY KEY (REM_ID_)
);

ALTER TABLE KD_DOC_REM COMMENT '文档推荐';

/*==============================================================*/
/* Table: KD_DOC_RIGHT                                          */
/*==============================================================*/
CREATE TABLE KD_DOC_RIGHT
(
   RIGHT_ID_            VARCHAR(64) NOT NULL COMMENT '权限ID',
   DOC_ID_              VARCHAR(64) NOT NULL COMMENT '文档ID',
   IDENTITY_TYPE_       VARCHAR(20) NOT NULL COMMENT '授权类型
            USER=用户
            GROUP=用户组',
   IDENTITY_ID_         VARCHAR(64) NOT NULL COMMENT '用户或组ID',
   RIGHT_               VARCHAR(60) COMMENT 'READ=可读
            EDIT=可编辑
            PRINT=打印
            DOWN_FILE=可下载附件',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (RIGHT_ID_)
);

ALTER TABLE KD_DOC_RIGHT COMMENT '文档权限';

/*==============================================================*/
/* Table: KD_DOC_ROUND                                          */
/*==============================================================*/
CREATE TABLE KD_DOC_ROUND
(
   ROUND_ID_            VARCHAR(64) NOT NULL,
   DOC_ID_              VARCHAR(64) COMMENT '文档ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   PRIMARY KEY (ROUND_ID_)
);

ALTER TABLE KD_DOC_ROUND COMMENT '文档传阅';

/*==============================================================*/
/* Table: KD_DOC_TEMPLATE                                       */
/*==============================================================*/
CREATE TABLE KD_DOC_TEMPLATE
(
   TEMP_ID_             VARCHAR(64) NOT NULL COMMENT '模板ID',
   TREE_ID_             VARCHAR(64) COMMENT '模板分类ID',
   NAME_                VARCHAR(80) NOT NULL COMMENT '模板名称',
   CONTENT_             TEXT COMMENT '模板内容',
   TYPE_                VARCHAR(20) COMMENT '模板类型
            词条模板
            文档模板',
   STATUS_              VARCHAR(20) COMMENT '模板状态',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (TEMP_ID_)
);

ALTER TABLE KD_DOC_TEMPLATE COMMENT '文档模板';

/*==============================================================*/
/* Table: KD_QUESTION                                           */
/*==============================================================*/
CREATE TABLE KD_QUESTION
(
   QUE_ID_              VARCHAR(64) NOT NULL COMMENT '问题ID',
   TREE_ID_             VARCHAR(64) COMMENT '分类Id',
   SUBJECT_             VARCHAR(80) NOT NULL COMMENT '问题内容',
   QUESTION_            TEXT COMMENT '详细问题',
   FILE_IDS_            VARCHAR(512) COMMENT '附件',
   TAGS_                VARCHAR(128) COMMENT '标签',
   REWARD_SCORE_        INT NOT NULL COMMENT '悬赏货币',
   REPLY_TYPE_          VARCHAR(80) COMMENT '所有人
            专家个人
            领域专家',
   REPLIER_ID_          VARCHAR(64) COMMENT '回复人ID',
   STATUS_              VARCHAR(20) COMMENT '待解决=UNSOLVED
            已解决=SOLVED',
   REPLY_COUNTS_        INT NOT NULL COMMENT '回复数',
   VIEW_TIMES_          INT COMMENT '浏览次数',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   PRIMARY KEY (QUE_ID_)
);

ALTER TABLE KD_QUESTION COMMENT '文档知识收藏';

/*==============================================================*/
/* Table: KD_QUES_ANSWER                                        */
/*==============================================================*/
CREATE TABLE KD_QUES_ANSWER
(
   ANSWER_ID_           VARCHAR(64) NOT NULL,
   QUE_ID_              VARCHAR(64) NOT NULL COMMENT '问题ID',
   REPLY_CONTENT_       TEXT NOT NULL,
   IS_BEST_             VARCHAR(20),
   AUTHOR_ID_           VARCHAR(64) NOT NULL,
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   PRIMARY KEY (ANSWER_ID_)
);

ALTER TABLE KD_QUES_ANSWER COMMENT '问题答案';

/*==============================================================*/
/* Table: KD_USER                                               */
/*==============================================================*/
CREATE TABLE KD_USER
(
   KUSER_ID             VARCHAR(64) NOT NULL COMMENT '用户ID',
   POINT_               INT NOT NULL COMMENT '积分',
   DOC_SCORE_           INT,
   GRADE_               VARCHAR(20),
   USER_TYPE_           VARCHAR(20) COMMENT '专家个人=PERSON
            领域专家=DOMAIN
            
            ',
   FULLNAME_            VARCHAR(32),
   SN_                  INT COMMENT '序号',
   KN_SYS_ID_           VARCHAR(64) COMMENT '知识领域',
   REQ_SYS_ID_          VARCHAR(64) COMMENT '爱问领域',
   SIGN_                VARCHAR(80) COMMENT '个性签名',
   PROFILE_             VARCHAR(100) COMMENT '个人简介',
   HEAD_ID_             VARCHAR(64) COMMENT '头像',
   SEX_                 VARCHAR(64) COMMENT '性别',
   OFFICE_PHONE_        VARCHAR(20) COMMENT '办公电话',
   MOBILE_              VARCHAR(16) COMMENT '手机号码',
   EMAIL_               VARCHAR(80) COMMENT '电子邮箱',
   USER_ID_             VARCHAR(64) NOT NULL COMMENT '从属用户ID',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   PRIMARY KEY (KUSER_ID)
);

ALTER TABLE KD_USER COMMENT '知识用户信息';

/*==============================================================*/
/* Table: KD_USER_LEVEL                                         */
/*==============================================================*/
CREATE TABLE KD_USER_LEVEL
(
   CONF_ID_             VARCHAR(32) NOT NULL COMMENT '配置ID',
   START_VAL_           INT NOT NULL COMMENT '起始值',
   END_VAL_             INT NOT NULL COMMENT '结束值',
   LEVEL_NAME_          VARCHAR(100) NOT NULL COMMENT '等级名称',
   HEADER_ICON_         VARCHAR(128) COMMENT '头像Icon',
   MEMO_                VARCHAR(500) COMMENT '备注',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   PRIMARY KEY (CONF_ID_)
);

ALTER TABLE KD_USER_LEVEL COMMENT '用户知识等级配置';

/*==============================================================*/
/* Table: LOG_MODULE                                            */
/*==============================================================*/
CREATE TABLE LOG_MODULE
(
   ID_                  VARCHAR(64) NOT NULL COMMENT '主键',
   MODULE_              VARCHAR(128) COMMENT '模块',
   SUB_MODULE           VARCHAR(128) COMMENT '子模块',
   ENABLE_              VARCHAR(64) COMMENT '启用',
   PRIMARY KEY (ID_)
);

ALTER TABLE LOG_MODULE COMMENT '日志模块';

/*==============================================================*/
/* Table: MI_DB_ID                                              */
/*==============================================================*/
CREATE TABLE MI_DB_ID
(
   ID_                  INT NOT NULL COMMENT '机器ID,
            默认为1',
   START_               NUMERIC(20,0) NOT NULL COMMENT '开始值',
   MAX_                 NUMERIC(20,0) NOT NULL COMMENT '增长值',
   MAC_NAME_            VARCHAR(256) NOT NULL COMMENT '服务器的机器名称，由程序启动时自动读取并且加入数据库',
   PRIMARY KEY (ID_)
);

ALTER TABLE MI_DB_ID COMMENT '系统表主键增长表';

/*==============================================================*/
/* Table: MOBILE_TOKEN                                          */
/*==============================================================*/
CREATE TABLE MOBILE_TOKEN
(
   ID_                  VARCHAR(64) NOT NULL COMMENT '主键',
   ACCOUNT_             VARCHAR(64) NOT NULL COMMENT 'ACCOUNT',
   USER_ID_             VARCHAR(64) COMMENT '用户ID',
   TOKEN                VARCHAR(64) COMMENT '令牌',
   STATUS_              SMALLINT COMMENT '状态',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   EXPIRED_TIME_        DATETIME COMMENT '失效时间',
   PRIMARY KEY (ID_)
);

ALTER TABLE MOBILE_TOKEN COMMENT '手机端令牌';

/*==============================================================*/
/* Table: OA_ADDR_BOOK                                          */
/*==============================================================*/
CREATE TABLE OA_ADDR_BOOK
(
   ADDR_ID_             VARCHAR(64) NOT NULL COMMENT '联系人ID',
   NAME_                VARCHAR(50) NOT NULL COMMENT '姓名',
   COMPANY_             VARCHAR(120) COMMENT '公司',
   DEP_                 VARCHAR(50) COMMENT '部门',
   POS_                 VARCHAR(50) COMMENT '职务',
   MAIL_                VARCHAR(255) COMMENT '主邮箱',
   COUNTRY_             VARCHAR(32) COMMENT '国家',
   SATE_                VARCHAR(32) COMMENT '省份',
   CITY_                VARCHAR(32) COMMENT '城市',
   STREET_              VARCHAR(80) COMMENT '街道',
   ZIP_                 VARCHAR(20) COMMENT '邮编',
   BIRTHDAY_            DATETIME COMMENT '生日',
   MOBILE_              VARCHAR(16) COMMENT '主手机',
   PHONE_               VARCHAR(16) COMMENT '主电话',
   WEIXIN_              VARCHAR(80) COMMENT '主微信号',
   QQ_                  VARCHAR(32) COMMENT 'QQ',
   PHOTO_ID_            VARCHAR(64) COMMENT '头像文件ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   PRIMARY KEY (ADDR_ID_)
);

ALTER TABLE OA_ADDR_BOOK COMMENT '通讯录联系人';

/*==============================================================*/
/* Table: OA_ADDR_CONT                                          */
/*==============================================================*/
CREATE TABLE OA_ADDR_CONT
(
   CONT_ID_             VARCHAR(64) NOT NULL COMMENT '联系信息ID',
   ADDR_ID_             VARCHAR(64) COMMENT '联系人ID',
   TYPE_                VARCHAR(50) NOT NULL COMMENT '类型
            手机号=MOBILE
            家庭地址=HOME_ADDRESS
            工作地址=WORK_ADDRESS
            QQ=QQ
            微信=WEI_XIN
            GoogleTalk=GOOGLE-TALK
            工作=WORK_INFO
            备注=MEMO',
   CONTACT_             VARCHAR(255) COMMENT '联系主信息',
   EXT1_                VARCHAR(100) COMMENT '联系扩展字段1',
   EXT2_                VARCHAR(100) COMMENT '联系扩展字段2',
   EXT3_                VARCHAR(100) COMMENT '联系扩展字段3',
   EXT4_                VARCHAR(100) COMMENT '联系扩展字段4',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   PRIMARY KEY (CONT_ID_)
);

ALTER TABLE OA_ADDR_CONT COMMENT '通讯录联系信息';

/*==============================================================*/
/* Table: OA_ADDR_GPB                                           */
/*==============================================================*/
CREATE TABLE OA_ADDR_GPB
(
   GROUP_ID_            VARCHAR(64) NOT NULL COMMENT '分组ID',
   ADDR_ID_             VARCHAR(64) NOT NULL COMMENT '联系人ID',
   PRIMARY KEY (GROUP_ID_, ADDR_ID_)
);

ALTER TABLE OA_ADDR_GPB COMMENT '通讯录分组下的联系人';

/*==============================================================*/
/* Table: OA_ADDR_GRP                                           */
/*==============================================================*/
CREATE TABLE OA_ADDR_GRP
(
   GROUP_ID_            VARCHAR(64) NOT NULL COMMENT '分组ID',
   NAME_                VARCHAR(50) NOT NULL COMMENT '名字',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   PRIMARY KEY (GROUP_ID_)
);

ALTER TABLE OA_ADDR_GRP COMMENT '通讯录分组';

/*==============================================================*/
/* Table: OA_ASSETS                                             */
/*==============================================================*/
CREATE TABLE OA_ASSETS
(
   ASS_ID_              VARCHAR(64) NOT NULL COMMENT '资产ID',
   PROD_DEF_ID_         VARCHAR(64) COMMENT '参数定义ID',
   CODE_                VARCHAR(64) COMMENT '资产编号',
   NAME_                VARCHAR(64) COMMENT '资产名称',
   JSON_                TEXT COMMENT '参数JSON',
   DESC_                VARCHAR(256) COMMENT '描述',
   STATUS_              VARCHAR(20) COMMENT '状态',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (ASS_ID_)
);

ALTER TABLE OA_ASSETS COMMENT '资产信息';

/*==============================================================*/
/* Table: OA_ASSETS_BID                                         */
/*==============================================================*/
CREATE TABLE OA_ASSETS_BID
(
   BID_ID_              VARCHAR(64) NOT NULL COMMENT '申请ID',
   ASS_ID_              VARCHAR(64) COMMENT '资产ID',
   PARA_ID_             VARCHAR(64) COMMENT '参数ID(不做关联)',
   START_               DATETIME NOT NULL COMMENT '开始时间',
   END_                 DATETIME NOT NULL COMMENT '结束时间',
   MEMO_                TEXT COMMENT '申请说明',
   USE_MANS_            VARCHAR(20) COMMENT '申请人员',
   STATUS_              VARCHAR(20) COMMENT '状态',
   BPM_INST_ID_         VARCHAR(64) COMMENT '流程实例ID',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (BID_ID_)
);

ALTER TABLE OA_ASSETS_BID COMMENT '【资产申请】';

/*==============================================================*/
/* Table: OA_ASS_PARAMETER                                      */
/*==============================================================*/
CREATE TABLE OA_ASS_PARAMETER
(
   PARA_ID_             VARCHAR(64) NOT NULL COMMENT '参数ID',
   VALUE_ID_            VARCHAR(64) COMMENT '参数VALUE主键',
   KEY_ID_              VARCHAR(64) COMMENT '参数KEY主键',
   ASS_ID_              VARCHAR(64) COMMENT '资产ID',
   CUSTOM_VALUE_NAME_   VARCHAR(255) COMMENT '自定义VALUE名',
   CUSTOM_KEY_NAME_     VARCHAR(255) COMMENT '自定义KEY名',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (PARA_ID_)
);

ALTER TABLE OA_ASS_PARAMETER COMMENT '资产参数';

/*==============================================================*/
/* Table: OA_CAR                                                */
/*==============================================================*/
CREATE TABLE OA_CAR
(
   CAR_ID_              VARCHAR(64) NOT NULL COMMENT '车辆ID',
   SYS_DIC_ID_          VARCHAR(64),
   NAME_                VARCHAR(60) NOT NULL COMMENT '车辆名称',
   CAR_NO_              VARCHAR(20) NOT NULL COMMENT '车牌号',
   TRAVEL_MILES_        INT COMMENT '行驶里程',
   ENGINE_NUM_          VARCHAR(20) COMMENT '发动机号',
   FRAME_NO_            VARCHAR(20) COMMENT '车辆识别代号',
   BRAND_               VARCHAR(64) COMMENT '品牌',
   MODEL_               VARCHAR(64) COMMENT '型号',
   WEIGHT_              INT COMMENT '车辆载重',
   SEATS_               INT COMMENT '车辆座位',
   BUY_DATE_            DATETIME COMMENT '购买日期',
   PRICE_               NUMERIC(18,4) COMMENT '购买价格',
   ANNUAL_INSP_         TEXT COMMENT '年检情况',
   INSURANCE_           TEXT COMMENT '保险情况',
   MAINTENS_            TEXT COMMENT '保养维修情况',
   MEMO_                TEXT COMMENT '备注信息',
   PHOTO_IDS_           VARCHAR(512) COMMENT '车辆照片',
   STATUS_              VARCHAR(20) NOT NULL COMMENT '车辆状态
            IN_USED=在使用
            IN_FREE=空闲
            SCRAP=报废',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (CAR_ID_)
);

ALTER TABLE OA_CAR COMMENT '车辆信息';

/*==============================================================*/
/* Table: OA_CAR_APP                                            */
/*==============================================================*/
CREATE TABLE OA_CAR_APP
(
   APP_ID_              VARCHAR(64) NOT NULL,
   CAR_CAT_             VARCHAR(50) NOT NULL COMMENT '汽车类别',
   CAR_ID_              VARCHAR(64) NOT NULL COMMENT '车辆ID',
   CAR_NAME_            VARCHAR(50) NOT NULL COMMENT '车辆名称',
   START_TIME_          DATETIME NOT NULL COMMENT '起始时间',
   END_TIME_            DATETIME NOT NULL COMMENT '终止时间',
   DRIVER_              VARCHAR(20) COMMENT '驾驶员',
   CATEGORY_            VARCHAR(64) COMMENT '行程类别',
   DEST_LOC_            VARCHAR(100) COMMENT '目的地点',
   TRAV_MILES_          INT COMMENT '行驶里程',
   USE_MANS_            VARCHAR(20) COMMENT '使用人员',
   MEMO_                TEXT COMMENT '使用说明',
   STATUS_              VARCHAR(20) COMMENT '申请状态',
   BPM_INST_            VARCHAR(64) COMMENT '流程实例ID',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (APP_ID_)
);

ALTER TABLE OA_CAR_APP COMMENT '车辆申请';

/*==============================================================*/
/* Table: OA_COM_BOOK                                           */
/*==============================================================*/
CREATE TABLE OA_COM_BOOK
(
   COM_ID_              VARCHAR(64) NOT NULL,
   NAME_                VARCHAR(64) NOT NULL,
   FIRST_LETTER_        VARCHAR(16),
   DEPNAME_             VARCHAR(64),
   MOBILE_              VARCHAR(64),
   MOBILE2_             VARCHAR(64),
   PHONE_               VARCHAR(64),
   EMAIL_               VARCHAR(64),
   QQ_                  VARCHAR(32),
   IS_EMPLOYEE_         VARCHAR(16) NOT NULL,
   REMARK_              VARCHAR(500),
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (COM_ID_)
);

/*==============================================================*/
/* Table: OA_COM_RIGHT                                          */
/*==============================================================*/
CREATE TABLE OA_COM_RIGHT
(
   RIGHT_ID_            VARCHAR(64) NOT NULL COMMENT '权限ID',
   COMBOOK_ID_          VARCHAR(64) NOT NULL COMMENT '文档ID',
   IDENTITY_TYPE_       VARCHAR(20) NOT NULL COMMENT '授权类型
            USER=用户
            GROUP=用户组',
   IDENTITY_ID_         VARCHAR(64) NOT NULL COMMENT '用户或组ID',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (RIGHT_ID_)
);

/*==============================================================*/
/* Table: OA_MEETING                                            */
/*==============================================================*/
CREATE TABLE OA_MEETING
(
   MEET_ID_             VARCHAR(64) NOT NULL,
   NAME_                VARCHAR(255) NOT NULL,
   DESCP_               TEXT,
   START_               DATETIME NOT NULL,
   END_                 DATETIME NOT NULL,
   LOCATION_            VARCHAR(255),
   ROOM_ID_             VARCHAR(64),
   BUDGET_              NUMERIC(18,4),
   HOST_UID_            VARCHAR(64),
   RECORD_UID_          VARCHAR(64),
   IMP_DEGREE_          VARCHAR(20) COMMENT '重要程度',
   STATUS_              VARCHAR(20),
   SUMMARY_             TEXT,
   BPM_INST_ID_         VARCHAR(64),
   FILE_IDS_            VARCHAR(512),
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (MEET_ID_)
);

ALTER TABLE OA_MEETING COMMENT '会议申请';

/*==============================================================*/
/* Table: OA_MEET_ATT                                           */
/*==============================================================*/
CREATE TABLE OA_MEET_ATT
(
   ATT_ID_              VARCHAR(64) NOT NULL,
   MEET_ID_             VARCHAR(64) NOT NULL COMMENT '会议ID',
   USER_ID_             VARCHAR(64) NOT NULL COMMENT '用户ID',
   FULLNAME_            VARCHAR(20) COMMENT '姓名',
   SUMMARY_             TEXT COMMENT '总结',
   STATUS_              VARCHAR(20) COMMENT '总结状态
            INIT
            SUBMITED',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (ATT_ID_)
);

ALTER TABLE OA_MEET_ATT COMMENT '会议参与人';

/*==============================================================*/
/* Table: OA_MEET_ROOM                                          */
/*==============================================================*/
CREATE TABLE OA_MEET_ROOM
(
   ROOM_ID_             VARCHAR(64) NOT NULL,
   NAME_                VARCHAR(100) NOT NULL COMMENT '会议室名称',
   LOCATION_            VARCHAR(255) COMMENT '地址',
   DESCP_               VARCHAR(512) COMMENT '描述',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (ROOM_ID_)
);

ALTER TABLE OA_MEET_ROOM COMMENT '会议室';

/*==============================================================*/
/* Table: OA_PLAN_TASK                                          */
/*==============================================================*/
CREATE TABLE OA_PLAN_TASK
(
   PLAN_ID_             VARCHAR(64) NOT NULL,
   PROJECT_ID_          VARCHAR(64) COMMENT '项目或产品ID',
   REQ_ID_              VARCHAR(64) COMMENT '需求ID',
   VERSION_             VARCHAR(50) COMMENT '版本号',
   SUBJECT_             VARCHAR(128) NOT NULL COMMENT '计划标题',
   CONTENT_             TEXT COMMENT '计划内容',
   PSTART_TIME_         DATETIME NOT NULL COMMENT '计划开始时间',
   PEND_TIME_           DATETIME COMMENT '计划结束时间',
   START_TIME_          DATETIME COMMENT '实际开始时间',
   END_TIME_            DATETIME COMMENT '实际结束时间',
   STATUS_              VARCHAR(20) COMMENT '状态
            未开始
            执行中
            延迟
            暂停
            中止
            完成',
   LAST_                INT COMMENT '耗时(分）',
   ASSIGN_ID_           VARCHAR(64) COMMENT '分配人',
   OWNER_ID_            VARCHAR(64) COMMENT '所属人',
   EXE_ID_              VARCHAR(64) COMMENT '执行人',
   BPM_DEF_ID_          VARCHAR(64) COMMENT '流程定义ID',
   BPM_INST_ID_         VARCHAR(64) COMMENT '流程实例ID',
   BPM_TASK_ID_         VARCHAR(64) COMMENT '流程任务ID',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构Id',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (PLAN_ID_)
);

ALTER TABLE OA_PLAN_TASK COMMENT '工作计划任务';

/*==============================================================*/
/* Table: OA_PRODUCT_DEF                                        */
/*==============================================================*/
CREATE TABLE OA_PRODUCT_DEF
(
   PROD_DEF_ID_         VARCHAR(64) NOT NULL COMMENT '参数定义ID',
   TREE_ID_             VARCHAR(64) COMMENT '分类Id',
   NAME_                VARCHAR(64) COMMENT '名称',
   DESC_                VARCHAR(256) COMMENT '描述',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   PRIMARY KEY (PROD_DEF_ID_)
);

ALTER TABLE OA_PRODUCT_DEF COMMENT '产品分类定义';

/*==============================================================*/
/* Table: OA_PRODUCT_DEF_PARA                                   */
/*==============================================================*/
CREATE TABLE OA_PRODUCT_DEF_PARA
(
   ID_                  VARCHAR(64) NOT NULL COMMENT '主键',
   KEY_ID_              VARCHAR(64) COMMENT '参数KEY主键',
   VALUE_ID_            VARCHAR(64) COMMENT '参数VALUE主键',
   PROD_DEF_ID_         VARCHAR(64) COMMENT '参数定义ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   PRIMARY KEY (ID_)
);

ALTER TABLE OA_PRODUCT_DEF_PARA COMMENT '产品定义参数表';

/*==============================================================*/
/* Table: OA_PRODUCT_DEF_PARA_KEY                               */
/*==============================================================*/
CREATE TABLE OA_PRODUCT_DEF_PARA_KEY
(
   KEY_ID_              VARCHAR(64) NOT NULL COMMENT '参数KEY主键',
   TREE_ID_             VARCHAR(64) COMMENT '分类Id',
   NAME_                VARCHAR(64) COMMENT '名称',
   CONTROL_TYPE_        VARCHAR(64) COMMENT '类型(radio-list checkbox-list textbox number date textarea)',
   DATA_TYPE_           VARCHAR(20) COMMENT '数据类型(string number date 大文本)',
   IS_UNIQUE_           SMALLINT COMMENT '是否唯一属性',
   DESC_                VARCHAR(256) COMMENT '描述',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   PRIMARY KEY (KEY_ID_)
);

ALTER TABLE OA_PRODUCT_DEF_PARA_KEY COMMENT '产品定义参数KEY(产品类型)';

/*==============================================================*/
/* Table: OA_PRODUCT_DEF_PARA_VALUE                             */
/*==============================================================*/
CREATE TABLE OA_PRODUCT_DEF_PARA_VALUE
(
   VALUE_ID_            VARCHAR(64) NOT NULL COMMENT '参数VALUE主键',
   KEY_ID_              VARCHAR(64) COMMENT '参数KEY主键',
   TREE_ID_             VARCHAR(64) COMMENT '分类Id',
   NAME_                VARCHAR(64) COMMENT '名称',
   NUMBER_              DOUBLE COMMENT '数字类型',
   STRING_              VARCHAR(20) COMMENT '字符串类型',
   TEXT_                VARCHAR(4000) COMMENT '文本类型',
   DATETIME__           DATETIME COMMENT '时间类型',
   DESC_                VARCHAR(256) COMMENT '描述',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   PRIMARY KEY (VALUE_ID_)
);

ALTER TABLE OA_PRODUCT_DEF_PARA_VALUE COMMENT '产品定义参数VALUE(产品属性)';

/*==============================================================*/
/* Table: OA_PROJECT                                            */
/*==============================================================*/
CREATE TABLE OA_PROJECT
(
   PROJECT_ID_          VARCHAR(64) NOT NULL,
   TREE_ID_             VARCHAR(64) COMMENT '分类Id',
   PRO_NO_              VARCHAR(50) NOT NULL COMMENT '编号',
   TAG_                 VARCHAR(50) COMMENT '标签名称',
   NAME_                VARCHAR(100) NOT NULL COMMENT '名称',
   DESCP_               TEXT COMMENT '描述',
   REPOR_ID_            VARCHAR(64) NOT NULL COMMENT '负责人',
   COSTS_               NUMERIC(16,4) COMMENT '预计费用',
   START_TIME_          DATETIME COMMENT '启动时间',
   END_TIME_            DATETIME COMMENT '结束时间',
   STATUS_              VARCHAR(20) COMMENT '状态
            未开始=UNSTART
            暂停中=SUSPEND
            已延迟=DELAYED
            已取消=CANCELED
            进行中=UNDERWAY
            已完成=FINISHED',
   VERSION_             VARCHAR(50) COMMENT '当前版本',
   TYPE_                VARCHAR(20) COMMENT '类型
            PROJECT=项目
            PRODUCT=产品',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构Id',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (PROJECT_ID_)
);

ALTER TABLE OA_PROJECT COMMENT '项目或产品';

/*==============================================================*/
/* Table: OA_PRO_ATTEND                                         */
/*==============================================================*/
CREATE TABLE OA_PRO_ATTEND
(
   ATT_ID_              VARCHAR(64) NOT NULL,
   PROJECT_ID_          VARCHAR(64),
   USER_ID_             TEXT COMMENT '参与人ID',
   GROUP_ID_            TEXT COMMENT '参与组ID',
   PART_TYPE_           VARCHAR(20) NOT NULL COMMENT '参与类型
            Participate
            
                  JOIN=参与
                  RESPONSE=负责
                  APPROVE=审批
                  PAY_ATT=关注',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构Id',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (ATT_ID_)
);

ALTER TABLE OA_PRO_ATTEND COMMENT '项目或产品参与人、负责人、关注人';

/*==============================================================*/
/* Table: OA_PRO_VERS                                           */
/*==============================================================*/
CREATE TABLE OA_PRO_VERS
(
   VERSION_ID_          VARCHAR(64) NOT NULL,
   PROJECT_ID_          VARCHAR(64) COMMENT '项目或产品ID',
   START_TIME_          DATETIME COMMENT '开始时间',
   END_TIME_            DATETIME COMMENT '结束时间',
   STATUS_              VARCHAR(20) COMMENT '状态
            DRAFTED=草稿
            DEPLOYED=发布
            RUNNING=进行中
            FINISHED=完成
            ',
   VERSION_             VARCHAR(50) NOT NULL COMMENT '版本号',
   DESCP_               TEXT COMMENT '描述',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构Id',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (VERSION_ID_)
);

ALTER TABLE OA_PRO_VERS COMMENT '项目或产品版本';

/*==============================================================*/
/* Table: OA_REQ_MGR                                            */
/*==============================================================*/
CREATE TABLE OA_REQ_MGR
(
   REQ_ID_              VARCHAR(64) NOT NULL,
   PROJECT_ID_          VARCHAR(64),
   REQ_CODE_            VARCHAR(50) NOT NULL COMMENT '需求编码',
   SUBJECT_             VARCHAR(128) NOT NULL COMMENT '标题',
   PATH_                VARCHAR(512),
   DESCP_               TEXT COMMENT '描述',
   PARENT_ID_           VARCHAR(64) COMMENT '父需求ID',
   STATUS_              VARCHAR(50) COMMENT '状态',
   LEVEL_               INT COMMENT '层次',
   CHECKER_ID_          VARCHAR(64) COMMENT '审批人',
   REP_ID_              VARCHAR(64) COMMENT '负责人',
   EXE_ID_              VARCHAR(64) COMMENT '执行人',
   VERSION_             VARCHAR(20) NOT NULL COMMENT '版本号',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构Id',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (REQ_ID_)
);

ALTER TABLE OA_REQ_MGR COMMENT '产品或项目需求';

/*==============================================================*/
/* Table: OA_WORK_ATT                                           */
/*==============================================================*/
CREATE TABLE OA_WORK_ATT
(
   ATT_ID_              VARCHAR(64) NOT NULL,
   USER_ID_             VARCHAR(64) NOT NULL COMMENT '关注人ID',
   ATT_TIME_            DATETIME COMMENT '关注时间',
   NOTICE_TYPE_         VARCHAR(50) NOT NULL COMMENT '通知方式
            Mail,ShortMsg,WeiXin',
   TYPE_                VARCHAR(50) NOT NULL COMMENT '关注类型
            项目=PROJECT
            工作计划=PLAN
            需求=REQ',
   TYPE_PK_             VARCHAR(64) NOT NULL COMMENT '类型主键ID
            当类型主键为需求类型时，即存入需求ID',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构Id',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (ATT_ID_)
);

ALTER TABLE OA_WORK_ATT COMMENT '工作动态关注';

/*==============================================================*/
/* Table: OA_WORK_LOG                                           */
/*==============================================================*/
CREATE TABLE OA_WORK_LOG
(
   LOG_ID_              VARCHAR(64) NOT NULL,
   PLAN_ID_             VARCHAR(64) COMMENT '计划任务ID',
   CONTENT_             VARCHAR(1024) NOT NULL COMMENT '内容',
   START_TIME_          DATETIME NOT NULL COMMENT '开始时间',
   END_TIME_            DATETIME NOT NULL COMMENT '结束时间',
   STATUS_              VARCHAR(20) COMMENT '状态',
   LAST_                INT COMMENT '耗时',
   CHECKER_             VARCHAR(64) COMMENT '审批人',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构Id',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (LOG_ID_)
);

ALTER TABLE OA_WORK_LOG COMMENT '工作日志';

/*==============================================================*/
/* Table: OA_WORK_MAT                                           */
/*==============================================================*/
CREATE TABLE OA_WORK_MAT
(
   ACTION_ID_           VARCHAR(64) NOT NULL,
   CONTENT_             VARCHAR(512) NOT NULL COMMENT '评论内容',
   TYPE_                VARCHAR(50) NOT NULL COMMENT '类型
            项目=PROJECT
            工作计划=PLAN
            需求=REQ',
   TYPE_PK_             VARCHAR(64) NOT NULL COMMENT '类型主键ID
            当类型主键为需求类型时，即存入需求ID',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构Id',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (ACTION_ID_)
);

ALTER TABLE OA_WORK_MAT COMMENT '工作动态';

/*==============================================================*/
/* Table: OD_DOCUMENT                                           */
/*==============================================================*/
CREATE TABLE OD_DOCUMENT
(
   DOC_ID_              VARCHAR(64) NOT NULL,
   TREE_ID_             VARCHAR(64) COMMENT '发文分类ID',
   ISSUE_NO_            VARCHAR(100) NOT NULL COMMENT '发文字号',
   ISSUE_DEP_ID_        VARCHAR(64) COMMENT '发文机关或部门',
   IS_JOIN_ISSUE_       VARCHAR(20) COMMENT '是否联合发文件',
   JOIN_DEP_IDS_        VARCHAR(512) COMMENT '联合发文单位或部门',
   MAIN_DEP_ID_         VARCHAR(64) COMMENT '主送单位',
   CC_DEP_ID_           VARCHAR(64) COMMENT '抄送单位或部门',
   TAKE_DEP_ID_         VARCHAR(64) COMMENT '承办部门ID',
   ASS_DEP_ID_          VARCHAR(64) COMMENT '协办部门ID',
   SUBJECT_             VARCHAR(200) NOT NULL COMMENT '文件标题',
   PRIVACY_LEVEL_       VARCHAR(50) COMMENT '秘密等级
            普通=COMMON
            秘密=SECURITY
            机密=MIDDLE-SECURITY
            绝密=TOP SECURITY
            ',
   SECRECY_TERM_        INT COMMENT '保密期限(年)
            ',
   PRINT_COUNT_         INT COMMENT '打印份数',
   KEYWORDS_            VARCHAR(256) COMMENT '主题词',
   URGENT_LEVEL_        VARCHAR(50) COMMENT '紧急程度
            普通=COMMON
            紧急=URGENT
            特急=URGENTEST
            特提=MORE_URGENT',
   SUMMARY_             VARCHAR(1024) COMMENT '内容简介',
   BODY_FILE_PATH_      VARCHAR(255) COMMENT '正文路径',
   FILE_IDS_            VARCHAR(512) COMMENT '附件IDs',
   FILE_NAMES_          VARCHAR(512) COMMENT '附件名称',
   ISSUE_USR_ID_        VARCHAR(64) COMMENT '发文人ID',
   ARCH_TYPE_           SMALLINT NOT NULL COMMENT '0=发文
            1=收文',
   ORG_ARCH_ID_         VARCHAR(64) COMMENT '用于收文时使用，指向原公文ID',
   SELF_NO_             VARCHAR(100) COMMENT '用于收文时，部门对自身的公文自编号',
   STATUS_              VARCHAR(256) NOT NULL COMMENT '公文状态
            0=拟稿、修改状态
            1=发文状态
            2=归档状态',
   BPM_INST_ID_         VARCHAR(64) COMMENT '流程运行id
            通过该id可以查看该公文的审批历史',
   BPM_SOL_ID_          VARCHAR(64) COMMENT '流程方案ID',
   DOC_TYPE_            VARCHAR(20) COMMENT '公文的类型
            上行文
               报告
               请示
               议案
               签报
               
            下行文
               命令
               决定
               公告
               通告
               通知
               通报
               批复
               会议纪要
            来文
            其他
            平行文
             函',
   ISSUED_DATE_         DATETIME COMMENT '发布日期',
   TENANT_ID_           VARCHAR(64) COMMENT '公共 - 创建者所属SAAS ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (DOC_ID_)
);

/*==============================================================*/
/* Table: OD_DOC_FLOW                                           */
/*==============================================================*/
CREATE TABLE OD_DOC_FLOW
(
   SCHEME_ID_           VARCHAR(64) NOT NULL COMMENT '方案ID',
   DEP_ID_              VARCHAR(64) NOT NULL COMMENT '部门ID',
   SEND_SOL_ID_         VARCHAR(64) COMMENT '发文流程方案ID',
   SEND_SOL_NAME_       VARCHAR(128) COMMENT '发文流程方案名称',
   REC_SOL_ID_          VARCHAR(64) COMMENT '收文流程方案ID',
   REC_SOL_NAME_        VARCHAR(128) COMMENT '收文流程方案名称',
   TENANT_ID_           VARCHAR(64) COMMENT '公共 - 创建者所属SAAS ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (SCHEME_ID_)
);

ALTER TABLE OD_DOC_FLOW COMMENT '收发文流程方案';

/*==============================================================*/
/* Table: OD_DOC_REC                                            */
/*==============================================================*/
CREATE TABLE OD_DOC_REC
(
   REC_ID_              VARCHAR(64) NOT NULL COMMENT '接收ID',
   DOC_ID_              VARCHAR(64) NOT NULL COMMENT '收发文ID',
   REC_DEP_ID_          VARCHAR(64) NOT NULL COMMENT '收文部门ID',
   REC_DTYPE_           VARCHAR(20) COMMENT '收文单位类型
            接收单位
            抄送单位
            ',
   IS_READ_             VARCHAR(20) COMMENT '是否阅读',
   READ_TIME_           DATETIME COMMENT '阅读时间',
   FEED_BACK_           VARCHAR(200) COMMENT '反馈意见',
   SIGN_USR_ID_         VARCHAR(64) COMMENT '签收人ID',
   SIGN_STATUS_         VARCHAR(20) COMMENT '签收状态',
   SIGN_TIME_           DATETIME COMMENT '签收时间',
   TENANT_ID_           VARCHAR(64) COMMENT '公共 - 创建者所属SAAS ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (REC_ID_)
);

/*==============================================================*/
/* Table: OD_DOC_REMIND_                                        */
/*==============================================================*/
CREATE TABLE OD_DOC_REMIND_
(
   REMIND_ID_           VARCHAR(64) NOT NULL,
   DOC_ID_              VARCHAR(64) COMMENT '收发文ID',
   CONTENT_             VARCHAR(1024) COMMENT '催办内容',
   MIND_USR_ID_         VARCHAR(64) COMMENT '催办人',
   TAKE_USR_ID_         VARCHAR(64) COMMENT '承办人',
   TENANT_ID_           VARCHAR(64) COMMENT '公共 - 创建者所属SAAS ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (REMIND_ID_)
);

ALTER TABLE OD_DOC_REMIND_ COMMENT '收发文办理催办';

/*==============================================================*/
/* Table: OD_DOC_TEMPLATE                                       */
/*==============================================================*/
CREATE TABLE OD_DOC_TEMPLATE
(
   TEMP_ID_             VARCHAR(64) NOT NULL,
   NAME_                VARCHAR(20) NOT NULL COMMENT '模板名称',
   DESCP_               VARCHAR(512) COMMENT '模板描述',
   STATUS_              VARCHAR(20) NOT NULL COMMENT '模板状态
            启用=ENABLED
            禁用=DISABLED',
   TREE_ID_             VARCHAR(64) COMMENT '模板分类ID',
   FILE_ID_             VARCHAR(64) COMMENT '文件ID',
   FILE_PATH_           VARCHAR(255) COMMENT '模板文件路径',
   TEMP_TYPE_           VARCHAR(20) COMMENT '套红模板
            公文发文模板
            收文模板',
   TENANT_ID_           VARCHAR(64) COMMENT '公共 - 创建者所属SAAS ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (TEMP_ID_)
);

ALTER TABLE OD_DOC_TEMPLATE COMMENT '公文模板';

/*==============================================================*/
/* Table: OS_ATTRIBUTE_VALUE                                    */
/*==============================================================*/
CREATE TABLE OS_ATTRIBUTE_VALUE
(
   ID_                  VARCHAR(64) NOT NULL COMMENT '主键ID',
   VALUE_               VARCHAR(256) COMMENT '参数值',
   ATTRIBUTE_ID_        VARCHAR(256) COMMENT '属性ID',
   TARGET_ID_           VARCHAR(64) COMMENT '目标ID',
   TENANT_ID_           VARCHAR(64) COMMENT '机构ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (ID_)
);

ALTER TABLE OS_ATTRIBUTE_VALUE COMMENT '人员属性值';

/*==============================================================*/
/* Table: OS_CUSTOM_ATTRIBUTE                                   */
/*==============================================================*/
CREATE TABLE OS_CUSTOM_ATTRIBUTE
(
   ID                   VARCHAR(64) NOT NULL COMMENT '主键',
   ATTRIBUTE_NAME_      VARCHAR(64) COMMENT '属性名称',
   KEY_                 VARCHAR(64) COMMENT 'KEY',
   ATTRIBUTE_TYPE_      VARCHAR(64) COMMENT '属性类型',
   TREE_ID_             VARCHAR(64) COMMENT '分类ID_',
   WIDGET_TYPE_         VARCHAR(64) COMMENT '控件类型',
   VALUE_SOURCE_        TEXT COMMENT '值来源',
   SOURCE_TYPE_         VARCHAR(64) COMMENT '来源类型',
   DIM_ID_              VARCHAR(64) COMMENT '维度ID',
   TENANT_ID_           VARCHAR(64) COMMENT '机构ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (ID)
);

ALTER TABLE OS_CUSTOM_ATTRIBUTE COMMENT '自定义属性';

/*==============================================================*/
/* Table: OS_DIMENSION                                          */
/*==============================================================*/
CREATE TABLE OS_DIMENSION
(
   DIM_ID_              VARCHAR(64) NOT NULL COMMENT '维度ID',
   NAME_                VARCHAR(40) NOT NULL COMMENT '维度名称',
   DIM_KEY_             VARCHAR(64) NOT NULL COMMENT '维度业务主键',
   IS_COMPOSE_          VARCHAR(10) NOT NULL COMMENT '是否组合维度',
   IS_SYSTEM_           VARCHAR(10) NOT NULL COMMENT '是否系统预设维度',
   STATUS_              VARCHAR(40) NOT NULL COMMENT '状态
            actived 已激活；locked 锁定（禁用）；deleted 已删除',
   SN_                  INT NOT NULL COMMENT '排序号',
   SHOW_TYPE_           VARCHAR(40) NOT NULL COMMENT '数据展示类型
            tree=树型数据。flat=平铺数据',
   IS_GRANT_            VARCHAR(10) COMMENT '是否参与授权',
   DESC_                VARCHAR(255) COMMENT '描述',
   TENANT_ID_           VARCHAR(64) COMMENT '机构ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (DIM_ID_)
);

ALTER TABLE OS_DIMENSION COMMENT '组织维度';

/*==============================================================*/
/* Table: OS_DIMENSION_RIGHT                                    */
/*==============================================================*/
CREATE TABLE OS_DIMENSION_RIGHT
(
   RIGHT_ID_            VARCHAR(64) NOT NULL COMMENT '主键ID',
   USER_ID_             TEXT COMMENT '用户ID',
   GROUP_ID_            TEXT COMMENT '组ID',
   DIM_ID_              VARCHAR(64) COMMENT '维度ID',
   TENANT_ID_           VARCHAR(64) COMMENT '机构ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (RIGHT_ID_)
);

ALTER TABLE OS_DIMENSION_RIGHT COMMENT '维度授权';

/*==============================================================*/
/* Table: OS_GROUP                                              */
/*==============================================================*/
CREATE TABLE OS_GROUP
(
   GROUP_ID_            VARCHAR(64) NOT NULL COMMENT '用户组ID',
   DIM_ID_              VARCHAR(64) COMMENT '维度ID',
   NAME_                VARCHAR(64) NOT NULL COMMENT '用户组名称',
   KEY_                 VARCHAR(64) NOT NULL COMMENT '用户组业务主键',
   RANK_LEVEL_          INT COMMENT '所属用户组等级值',
   STATUS_              VARCHAR(40) NOT NULL COMMENT '状态
            inactive 未激活；actived 已激活；locked 锁定；deleted 已删除',
   DESCP_               VARCHAR(255) COMMENT '描述',
   SN_                  INT NOT NULL COMMENT '排序号',
   PARENT_ID_           VARCHAR(64) COMMENT '父用户组ID',
   DEPTH_               INT COMMENT '层次',
   PATH_                VARCHAR(1024) COMMENT '路径',
   CHILDS_              INT COMMENT '下级数量',
   AREA_CODE_           VARCHAR(50) COMMENT '区域编码',
   FORM_                VARCHAR(20) COMMENT '来源
            sysem,系统添加,import导入,grade,分级添加的',
   SYNC_WX_             INT COMMENT '同步到微信',
   WX_PARENT_PID_       INT COMMENT '微信内部维护父部门ID',
   WX_PID_              INT COMMENT '微信平台部门唯一ID',
   IS_DEFAULT_          VARCHAR(40) COMMENT '是否默认，代表系统自带，不允许删除',
   TENANT_ID_           VARCHAR(64) COMMENT '公共 - 创建者所属SAAS ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (GROUP_ID_)
);

ALTER TABLE OS_GROUP COMMENT '系统用户组';

/*==============================================================*/
/* Table: OS_GROUP_MENU                                         */
/*==============================================================*/
CREATE TABLE OS_GROUP_MENU
(
   ID_                  VARCHAR(64) NOT NULL,
   MENU_ID_             VARCHAR(64) NOT NULL COMMENT '菜单ID',
   GROUP_ID_            VARCHAR(64) NOT NULL COMMENT '用户组ID',
   SYS_ID_              VARCHAR(64) NOT NULL,
   PRIMARY KEY (ID_)
);

ALTER TABLE OS_GROUP_MENU COMMENT '用户组下的授权菜单';

/*==============================================================*/
/* Table: OS_GROUP_SYS                                          */
/*==============================================================*/
CREATE TABLE OS_GROUP_SYS
(
   ID_                  VARCHAR(64) NOT NULL,
   GROUP_ID_            VARCHAR(64) NOT NULL COMMENT '用户组ID',
   SYS_ID_              VARCHAR(64),
   PRIMARY KEY (ID_)
);

/*==============================================================*/
/* Table: OS_RANK_TYPE                                          */
/*==============================================================*/
CREATE TABLE OS_RANK_TYPE
(
   ID_                  VARCHAR(64) NOT NULL COMMENT '主键',
   DIM_ID_              VARCHAR(64) COMMENT '维度ID',
   NAME_                VARCHAR(255) NOT NULL COMMENT '名称',
   KEY_                 VARCHAR(64) NOT NULL COMMENT '分类业务键',
   LEVEL_               INT NOT NULL COMMENT '级别数值',
   TENANT_ID_           VARCHAR(64) COMMENT '租用用户Id',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (ID_)
);

ALTER TABLE OS_RANK_TYPE COMMENT '用户组等级分类，放置组织的等级分类定义
如集团，分公司，部门等级别';

/*==============================================================*/
/* Table: OS_REL_INST                                           */
/*==============================================================*/
CREATE TABLE OS_REL_INST
(
   INST_ID_             VARCHAR(64) NOT NULL COMMENT '用户组关系ID',
   REL_TYPE_ID_         VARCHAR(64) COMMENT '关系类型ID',
   REL_TYPE_KEY_        VARCHAR(64) COMMENT '关系类型KEY_
            ',
   PARTY1_              VARCHAR(64) NOT NULL COMMENT '当前方ID',
   PARTY2_              VARCHAR(64) NOT NULL COMMENT '关联方ID',
   DIM1_                VARCHAR(64) COMMENT '当前方维度ID',
   DIM2_                VARCHAR(64) COMMENT '关联方维度ID',
   IS_MAIN_             VARCHAR(20) NOT NULL COMMENT 'IS_MAIN_',
   STATUS_              VARCHAR(40) NOT NULL COMMENT '状态
            ENABLED
            DISABLED',
   ALIAS_               VARCHAR(80) COMMENT '别名',
   REL_TYPE_            VARCHAR(64) COMMENT '关系类型',
   PATH_                VARCHAR(1024) COMMENT '路径',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   PRIMARY KEY (INST_ID_)
);

ALTER TABLE OS_REL_INST COMMENT '关系实例';

/*==============================================================*/
/* Table: OS_REL_TYPE                                           */
/*==============================================================*/
CREATE TABLE OS_REL_TYPE
(
   ID_                  VARCHAR(64) NOT NULL COMMENT '关系类型ID',
   NAME_                VARCHAR(64) NOT NULL COMMENT '关系名',
   KEY_                 VARCHAR(64) NOT NULL COMMENT '关系业务主键',
   REL_TYPE_            VARCHAR(40) NOT NULL COMMENT '关系类型。用户关系=USER-USER；用户组关系=GROUP-GROUP；用户与组关系=USER-GROUP；组与用户关系=GROUP-USER',
   CONST_TYPE_          VARCHAR(40) NOT NULL COMMENT '关系约束类型。1对1=one2one；1对多=one2many；多对1=many2one；多对多=many2many',
   PARTY1_              VARCHAR(128) NOT NULL COMMENT '关系当前方名称',
   PARTY2_              VARCHAR(128) NOT NULL COMMENT '关系关联方名称',
   DIM_ID1_             VARCHAR(64) COMMENT '当前方维度ID（仅对用户组关系）',
   DIM_ID2_             VARCHAR(64) COMMENT '关联方维度ID（用户关系忽略此值）',
   STATUS_              VARCHAR(40) NOT NULL COMMENT '状态。actived 已激活；locked 锁定；deleted 已删除',
   IS_SYSTEM_           VARCHAR(10) NOT NULL COMMENT '是否系统预设',
   IS_DEFAULT_          VARCHAR(10) NOT NULL COMMENT '是否默认',
   IS_TWO_WAY_          VARCHAR(10) NOT NULL COMMENT '是否是双向',
   MEMO_                VARCHAR(255) COMMENT '关系备注',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   TENANT_ID_           VARCHAR(64) COMMENT '公共 - 创建者所属SAAS ID',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (ID_)
);

ALTER TABLE OS_REL_TYPE COMMENT '关系类型定义';

/*==============================================================*/
/* Table: OS_USER                                               */
/*==============================================================*/
CREATE TABLE OS_USER
(
   USER_ID_             VARCHAR(64) NOT NULL COMMENT '用户ID',
   FULLNAME_            VARCHAR(64) NOT NULL COMMENT '姓名',
   USER_NO_             VARCHAR(64) NOT NULL,
   ENTRY_TIME_          DATETIME COMMENT '入职时间',
   QUIT_TIME_           DATETIME COMMENT '离职时间',
   STATUS_              VARCHAR(20) NOT NULL COMMENT '状态
            
            在职=ON_JOB
            离职=OUT_JOB
            ',
   FROM_                VARCHAR(20) COMMENT '来源
            system,系统添加,import,导入,grade,分级添加的',
   BIRTHDAY_            DATETIME COMMENT '出生日期',
   SEX_                 VARCHAR(10) COMMENT '姓别',
   MOBILE_              VARCHAR(20) COMMENT '手机',
   EMAIL_               VARCHAR(100) COMMENT '邮件',
   ADDRESS_             VARCHAR(255) COMMENT '地址',
   URGENT_              VARCHAR(64) COMMENT '紧急联系人',
   SYNC_WX_             INT COMMENT '是否同步到微信',
   URGENT_MOBILE_       VARCHAR(20) COMMENT '紧急联系人手机',
   QQ_                  VARCHAR(20) COMMENT 'QQ号',
   PHOTO_               VARCHAR(255) COMMENT '照片',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   TENANT_ID_           VARCHAR(64) COMMENT '机构ID',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (USER_ID_)
);

ALTER TABLE OS_USER COMMENT '用户信息表';

/*==============================================================*/
/* Table: PRO_ARTICLE                                           */
/*==============================================================*/
CREATE TABLE PRO_ARTICLE
(
   ID_                  VARCHAR(64) NOT NULL COMMENT '主键',
   BELONG_PRO_ID_       VARCHAR(64) COMMENT '所属项目ID',
   TITLE_               VARCHAR(128) COMMENT '标题',
   AUTHOR_              VARCHAR(64) COMMENT 'AUTHOR_',
   OUT_LINE_            VARCHAR(64) COMMENT '概要',
   TYPE_                VARCHAR(64) COMMENT '类型',
   PARENT_ID_           VARCHAR(64) COMMENT '父ID',
   SN_                  VARCHAR(64) COMMENT '序号',
   CONTENT_             TEXT COMMENT '内容',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (ID_)
);

ALTER TABLE PRO_ARTICLE COMMENT '文章';

/*==============================================================*/
/* Table: PRO_ITEM                                              */
/*==============================================================*/
CREATE TABLE PRO_ITEM
(
   ID                   VARCHAR(64) NOT NULL COMMENT '主键',
   NAME_                VARCHAR(128) COMMENT '项目名',
   DESC_                TEXT COMMENT '描述',
   VERSION_             VARCHAR(64) COMMENT '版本',
   GEN_SRC_             VARCHAR(512) COMMENT '文档生成路径',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (ID)
);

ALTER TABLE PRO_ITEM COMMENT '项目';

/*==============================================================*/
/* Table: SMS_MSG_INFO                                          */
/*==============================================================*/
CREATE TABLE SMS_MSG_INFO
(
   SMS_ID_              VARCHAR(64) NOT NULL COMMENT '主键',
   SEND_USER_           VARCHAR(64),
   RECEIVE_USER_        VARCHAR(64) COMMENT '用户ID',
   MOBILE_              VARCHAR(20) COMMENT '手机',
   CONTENT_             TEXT COMMENT '内容',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   STATUS_              SMALLINT COMMENT '1保存2未发送3发送成功4发送失败',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   PRIMARY KEY (SMS_ID_)
);

ALTER TABLE SMS_MSG_INFO COMMENT '短信信息表';

/*==============================================================*/
/* Table: SMS_MSG_SEND                                          */
/*==============================================================*/
CREATE TABLE SMS_MSG_SEND
(
   SEND_ID_             VARCHAR(64) NOT NULL COMMENT '主键',
   GATEWAY_ID_          VARCHAR(64) COMMENT '代理应用ID',
   SEND_USER_           VARCHAR(64),
   RECEIVE_USER_        VARCHAR(64) COMMENT '用户ID',
   MOBILE_              VARCHAR(20) COMMENT '手机',
   MSG_ID_              VARCHAR(64),
   CONTENT_             TEXT COMMENT '内容',
   RECEIPT_ID_          VARCHAR(512),
   STATUS_              SMALLINT COMMENT '1保存2未发送3发送成功4发送失败',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   PRIMARY KEY (SEND_ID_)
);

ALTER TABLE SMS_MSG_SEND COMMENT '短信发送表';

/*==============================================================*/
/* Table: SYS_ACCOUNT                                           */
/*==============================================================*/
CREATE TABLE SYS_ACCOUNT
(
   ACCOUNT_ID_          VARCHAR(64) NOT NULL,
   NAME_                VARCHAR(50) NOT NULL COMMENT '账号名称',
   PWD_                 VARCHAR(64) NOT NULL COMMENT '密码',
   ENC_TYPE_            VARCHAR(20) COMMENT '加密算法
            MD5
            SHA-256
            PLAINTEXT',
   FULLNAME_            VARCHAR(32) NOT NULL COMMENT '用户名',
   USER_ID_             VARCHAR(64) COMMENT '绑定用户ID',
   REMARK_              VARCHAR(200) COMMENT '备注',
   STATUS_              VARCHAR(20) NOT NULL COMMENT '状态
            ENABLED=可用
            DISABLED=禁用
            DELETED=删除',
   DOMAIN_              VARCHAR(64) COMMENT '域名',
   CREAT_ORG_ID_        VARCHAR(64) COMMENT '创建人组织ID',
   TENANT_ID_           VARCHAR(64) COMMENT '租用用户Id',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (ACCOUNT_ID_)
);

ALTER TABLE SYS_ACCOUNT COMMENT '登录账号';

/*==============================================================*/
/* Table: SYS_AUDIT                                             */
/*==============================================================*/
CREATE TABLE SYS_AUDIT
(
   ID_                  VARCHAR(64) NOT NULL COMMENT '主键',
   MODULE_              VARCHAR(128) COMMENT '所属模块',
   SUB_MODULE_          VARCHAR(128) COMMENT '功能',
   ACTION_              VARCHAR(128) COMMENT '操作名',
   IP_                  VARCHAR(128) COMMENT '操作IP',
   USER_AGENT_          VARCHAR(1024) COMMENT '设备信息',
   TARGET_              TEXT COMMENT '操作目标',
   MAIN_GROUP_NAME_     VARCHAR(500) COMMENT '主部门名',
   MAIN_GROUP_          VARCHAR(64) COMMENT '主部门ID',
   DURATION_            INT COMMENT '持续时长',
   TENANT_ID_           VARCHAR(64) COMMENT '租户ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (ID_)
);

ALTER TABLE SYS_AUDIT COMMENT '日志实体';

/*==============================================================*/
/* Table: SYS_BO_ATTR                                           */
/*==============================================================*/
CREATE TABLE SYS_BO_ATTR
(
   ID_                  VARCHAR(20) NOT NULL COMMENT '主键',
   ENT_ID_              VARCHAR(20) COMMENT '实体ID',
   NAME_                VARCHAR(64) COMMENT '名称',
   FIELD_NAME_          VARCHAR(64) COMMENT '字段名',
   COMMENT_             VARCHAR(100) COMMENT '备注',
   DATA_TYPE_           VARCHAR(10) COMMENT '类型',
   LENGTH_              INT COMMENT '数据长度',
   DECIMAL_LENGTH_      INT COMMENT '数据精度',
   CONTROL_             VARCHAR(30) COMMENT '控件类型',
   EXT_JSON_            VARCHAR(4000) COMMENT '扩展JSON',
   HAS_GEN_             VARCHAR(10) COMMENT '是否生成字段',
   STATUS_              VARCHAR(10) COMMENT '状态',
   IS_SINGLE_           INT COMMENT '是否单个属性字段',
   TENANT_ID_           VARCHAR(20) COMMENT '租户ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建时间',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (ID_)
);

ALTER TABLE SYS_BO_ATTR COMMENT '业务实体属性定义';

/*==============================================================*/
/* Table: SYS_BO_DEFINITION                                     */
/*==============================================================*/
CREATE TABLE SYS_BO_DEFINITION
(
   ID_                  VARCHAR(20) NOT NULL COMMENT '主键',
   NAME_                VARCHAR(64) COMMENT '名称',
   ALAIS_               VARCHAR(64) COMMENT '别名',
   COMMENT_             VARCHAR(200) COMMENT '备注',
   SUPPORT_DB_          VARCHAR(20) COMMENT '是否支持数据库',
   GEN_MODE_            VARCHAR(20) COMMENT '生成模式',
   TREE_ID_             VARCHAR(20) COMMENT '分类ID',
   TENANT_ID_           VARCHAR(20) COMMENT '租户ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建时间',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   OPINION_DEF_         VARCHAR(2000) COMMENT '表单意见定义',
   PRIMARY KEY (ID_)
);

ALTER TABLE SYS_BO_DEFINITION COMMENT '业务对象定义';

/*==============================================================*/
/* Table: SYS_BO_ENTITY                                         */
/*==============================================================*/
CREATE TABLE SYS_BO_ENTITY
(
   ID_                  VARCHAR(20) NOT NULL COMMENT '主键',
   NAME_                VARCHAR(64) COMMENT '名称',
   COMMENT_             VARCHAR(64) COMMENT '注释',
   TABLE_NAME_          VARCHAR(64) COMMENT '表名',
   DS_NAME_             VARCHAR(64) COMMENT '数据源名称',
   EXT_JSON_            VARCHAR(1000) COMMENT '扩展配置数据',
   GEN_TABLE_           VARCHAR(20) COMMENT '是否生成物理表',
   TREE_                VARCHAR(20) COMMENT '是否树形(YES,NO)',
   TENANT_ID_           VARCHAR(20) COMMENT '租户ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建时间',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (ID_)
);

ALTER TABLE SYS_BO_ENTITY COMMENT '业务实体对象';

/*==============================================================*/
/* Table: SYS_BO_LIST                                           */
/*==============================================================*/
CREATE TABLE SYS_BO_LIST
(
   ID_                  VARCHAR(64) NOT NULL COMMENT 'ID',
   SOL_ID_              VARCHAR(64) COMMENT '解决方案ID',
   NAME_                VARCHAR(200) NOT NULL COMMENT '名称',
   KEY_                 VARCHAR(120) NOT NULL COMMENT '标识键',
   DESCP_               VARCHAR(500) COMMENT '描述',
   ID_FIELD_            VARCHAR(60) COMMENT '主键字段',
   TEXT_FIELD_          VARCHAR(60) COMMENT '显示字段(树)',
   PARENT_FIELD_        VARCHAR(60) COMMENT '父ID(树)',
   IS_TREE_DLG_         VARCHAR(20) COMMENT '是否树对话框',
   ONLY_SEL_LEAF_       VARCHAR(20) COMMENT '仅可选择树节点',
   URL_                 VARCHAR(256) COMMENT '数据地址',
   MULTI_SELECT_        VARCHAR(20) COMMENT '是否多选择',
   IS_LEFT_TREE_        VARCHAR(20) COMMENT '是否显示左树',
   LEFT_NAV_            VARCHAR(80) COMMENT '左树SQL，格式如"select * from abc"##"select * from abc2"',
   LEFT_TREE_JSON_      TEXT COMMENT '左树字段映射',
   SQL_                 VARCHAR(2000) NOT NULL COMMENT 'SQL语句',
   USE_COND_SQL_        VARCHAR(20),
   COND_SQLS_           TEXT,
   DB_AS_               VARCHAR(64) COMMENT '数据源ID',
   FIELDS_JSON_         TEXT COMMENT '列字段JSON',
   COLS_JSON_           TEXT COMMENT '列的JSON',
   LIST_HTML_           TEXT COMMENT '列表显示模板',
   SEARCH_JSON_         TEXT COMMENT '搜索条件HTML',
   BPM_SOL_ID_          VARCHAR(64) COMMENT '绑定流程方案',
   FORM_ALIAS_          VARCHAR(64) COMMENT '绑定表单方案',
   TOP_BTNS_JSON_       TEXT COMMENT '头部按钮配置',
   BODY_SCRIPT_         TEXT COMMENT '脚本JS',
   IS_DIALOG_           VARCHAR(20) COMMENT '是否对话框',
   IS_PAGE_             VARCHAR(20) COMMENT '是否分页',
   IS_EXPORT_           VARCHAR(20) COMMENT '是否允许导出',
   HEIGHT_              INT COMMENT '高',
   WIDTH_               INT COMMENT '宽',
   ENABLE_FLOW_         VARCHAR(20) COMMENT '是否启用流程',
   IS_GEN_              VARCHAR(20) COMMENT '是否已产生HTML',
   IS_SHARE_            VARCHAR(20) COMMENT '是否共享',
   TREE_ID_             VARCHAR(64) COMMENT '分类ID',
   DRAW_CELL_SCRIPT_    TEXT COMMENT '脚本',
   MOBILE_HTML_         TEXT COMMENT '手机表单模板',
   DATA_STYLE_          VARCHAR(20) COMMENT '数据风格',
   ROW_EDIT_            VARCHAR(20) COMMENT '行数据编辑',
   DATA_RIGHT_JSON_     TEXT COMMENT '数据权限',
   START_FRO_COL_       INT COMMENT '锁定开始列',
   END_FRO_COL_         INT COMMENT '锁定结束列',
   SHOW_SUMMARY_ROW_    VARCHAR(20) COMMENT '是否显示汇总行',
   TENANT_ID_           VARCHAR(64) COMMENT '租户ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (ID_)
);

ALTER TABLE SYS_BO_LIST COMMENT '系统自定义业务管理列表';

/*==============================================================*/
/* Table: SYS_BO_RELATION                                       */
/*==============================================================*/
CREATE TABLE SYS_BO_RELATION
(
   ID_                  VARCHAR(20) NOT NULL COMMENT '主键',
   BO_DEFID_            VARCHAR(20) COMMENT 'BO定义ID',
   RELATION_TYPE_       VARCHAR(20) COMMENT '关系类型(main,sub)',
   FORM_ALIAS_          VARCHAR(64) COMMENT '表单别名',
   IS_REF_              INT COMMENT '是否引用实体',
   BO_ENTID_            VARCHAR(20) COMMENT 'BO实体ID',
   TENANT_ID_           VARCHAR(20) COMMENT '租户ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建时间',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (ID_)
);

ALTER TABLE SYS_BO_RELATION COMMENT '业务对象定义';

/*==============================================================*/
/* Table: SYS_BUTTON                                            */
/*==============================================================*/
CREATE TABLE SYS_BUTTON
(
   BUTTON_ID_           VARCHAR(64) NOT NULL COMMENT '按钮ID',
   MODULE_ID_           VARCHAR(64) COMMENT '模块ID',
   NAME_                VARCHAR(50) NOT NULL COMMENT '按钮名称',
   ICON_CLS_            VARCHAR(50) COMMENT '按钮ICONCLS',
   GLYPH_               VARCHAR(50) COMMENT 'GLYPH',
   SN_                  INT NOT NULL COMMENT '序号',
   BTN_TYPE_            VARCHAR(20) NOT NULL COMMENT '按钮类型
            
            明细=DETAIL
            新建=NEW
            修改=EDIT
            删除=DEL
            高级查询=SEARCH_COMPOSE
            新增附件=NEW_ATTACH
            打印=PRINT
            导出=EXPORT
            按字段查询=SEARCH_FIELD
            保存=SAVE
            上一条=PREV
            下一条=NEXT
            自定义=CUSTOM',
   KEY_                 VARCHAR(50) NOT NULL COMMENT '按钮Key',
   POS_                 VARCHAR(50) NOT NULL COMMENT '按钮位置
            TOP=表头工具栏
            MANAGE=管理列
            FORM_BOTTOM=表单底部按钮栏
            FORM_TOP=表单的头部
            DETAIL_TOP=明细的头部
            DETAIL_BOTTOM=表单底部明细
            
            ',
   CUSTOM_HANDLER_      TEXT COMMENT '自定义执行处理',
   LINK_MODULE_ID_      VARCHAR(64) COMMENT '关联模块ID',
   PRIMARY KEY (BUTTON_ID_)
);

ALTER TABLE SYS_BUTTON COMMENT '系统功能按钮管理
包括列表表头的按钮、管理列的按钮、表单按钮、子模块（明细）按钮';

/*==============================================================*/
/* Table: SYS_CUSTOMFORM_SETTING                                */
/*==============================================================*/
CREATE TABLE SYS_CUSTOMFORM_SETTING
(
   ID_                  VARCHAR(64) NOT NULL COMMENT '主键',
   NAME_                VARCHAR(64) COMMENT '名称',
   ALIAS_               VARCHAR(64) COMMENT '别名',
   PRE_JS_SCRIPT_       VARCHAR(1000) COMMENT '前置JS',
   AFTER_JS_SCRIPT_     VARCHAR(1000) COMMENT '后置JS',
   PRE_JAVA_SCRIPT_     VARCHAR(1000) COMMENT '前置JAVA脚本',
   AFTER_JAVA_SCRIPT_   VARCHAR(1000) COMMENT '后置JAVA脚本',
   SOL_NAME_            VARCHAR(64) COMMENT '解决方案',
   SOL_ID_              VARCHAR(64) COMMENT '解决方案ID',
   FORM_NAME_           VARCHAR(100) COMMENT '表单名称',
   FORM_ALIAS_          VARCHAR(64) COMMENT '表单别名',
   BODEF_ID_            VARCHAR(64) COMMENT '业务模型ID',
   BODEF_NAME_          VARCHAR(100) COMMENT '业务模型',
   IS_TREE_             INT COMMENT '树形表单(0,普通表单,1,树形表单)',
   TENANT_ID_           VARCHAR(64) COMMENT '租户ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人',
   EXPAND_LEVEL_        INT COMMENT '展开级别',
   LOAD_MODE_           INT COMMENT '树形加载方式0,一次性加载,1,懒加载',
   DISPLAY_FIELDS_      VARCHAR(64) COMMENT '显示字段',
   BUTTON_DEF_          VARCHAR(1000) COMMENT '自定义按钮',
   DATA_HANDLER_        VARCHAR(100) COMMENT '数据处理器',
   TABLE_RIGHT_JSON_    VARCHAR(1000) COMMENT '子表权限配置',
   MOBILE_FORM_ALIAS_   VARCHAR(64) COMMENT '手机表单模版别名',
   MOBILE_FORM_NAME_    VARCHAR(64) COMMENT '手机表单模版名称',
   TREE_ID_             VARCHAR(64) COMMENT '分类ID',
   PRIMARY KEY (ID_)
);

ALTER TABLE SYS_CUSTOMFORM_SETTING COMMENT '自定义表单设定';

/*==============================================================*/
/* Table: SYS_CUSTOM_QUERY                                      */
/*==============================================================*/
CREATE TABLE SYS_CUSTOM_QUERY
(
   ID_                  VARCHAR(64) NOT NULL COMMENT '主键',
   NAME_                VARCHAR(64) COMMENT '名称',
   KEY_                 VARCHAR(64) COMMENT '标识名 租户中唯一',
   TABLE_NAME_          VARCHAR(64) COMMENT '对象名称(表名或视图名)',
   IS_PAGE_             INT COMMENT '支持分页(1,支持,0不支持)',
   PAGE_SIZE_           INT COMMENT '分页大小',
   WHERE_FIELD_         TEXT COMMENT '条件字段定义',
   RESULT_FIELD_        VARCHAR(2000) COMMENT '结果字段定义',
   ORDER_FIELD_         VARCHAR(1024) COMMENT '排序字段',
   DS_ALIAS_            VARCHAR(64) COMMENT '数据源名称',
   TABLE_               INT COMMENT '是否为表(1,表,0视图)',
   SQL_DIY_             TEXT COMMENT '自定sql',
   SQL_BUILD_TYPE_      VARCHAR(20) COMMENT 'SQL构建类型',
   TENANT_ID_           VARCHAR(64) COMMENT '租户ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   SQL_                 VARCHAR(2000) COMMENT 'SQL',
   PRIMARY KEY (ID_)
);

ALTER TABLE SYS_CUSTOM_QUERY COMMENT '自定查询';

/*==============================================================*/
/* Table: SYS_DATASOURCE_DEF                                    */
/*==============================================================*/
CREATE TABLE SYS_DATASOURCE_DEF
(
   ID_                  VARCHAR(64) NOT NULL COMMENT '主键',
   NAME_                VARCHAR(64) COMMENT '数据源名称',
   ALIAS_               VARCHAR(64) COMMENT '别名',
   ENABLE_              VARCHAR(10) COMMENT '是否使用',
   SETTING_             VARCHAR(2000) COMMENT '数据源设定',
   DB_TYPE_             VARCHAR(10) COMMENT '数据库类型',
   INIT_ON_START_       VARCHAR(10) COMMENT '启动时初始化',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (ID_)
);

ALTER TABLE SYS_DATASOURCE_DEF COMMENT '数据源定义管理';

/*==============================================================*/
/* Table: SYS_DB_SQL                                            */
/*==============================================================*/
CREATE TABLE SYS_DB_SQL
(
   ID_                  VARCHAR(64) NOT NULL COMMENT 'ID_',
   KEY_                 VARCHAR(256) NOT NULL COMMENT 'KEY_',
   NAME_                VARCHAR(256) NOT NULL COMMENT '公司英文名',
   HEADER_              TEXT NOT NULL COMMENT '表头中文名称,以JSON存储',
   DS_NAME_             VARCHAR(256) COMMENT '数据源名称',
   DS_ID_               VARCHAR(64) COMMENT '数据源ID',
   SQL_                 TEXT COMMENT 'SQL语句',
   TENANT_ID_           VARCHAR(64) COMMENT '租用用户Id',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (ID_)
);

ALTER TABLE SYS_DB_SQL COMMENT '自定义SQL';

/*==============================================================*/
/* Table: SYS_DIC                                               */
/*==============================================================*/
CREATE TABLE SYS_DIC
(
   DIC_ID_              VARCHAR(64) NOT NULL COMMENT '主键',
   TYPE_ID_             VARCHAR(64) COMMENT '分类Id',
   KEY_                 VARCHAR(64) COMMENT '项Key',
   NAME_                VARCHAR(64) NOT NULL COMMENT '项名',
   VALUE_               VARCHAR(100) NOT NULL COMMENT '项值',
   DESCP_               VARCHAR(256) COMMENT '描述',
   SN_                  INT COMMENT '序号',
   PATH_                VARCHAR(256) COMMENT '路径',
   PARENT_ID_           VARCHAR(64) COMMENT '父ID',
   TENANT_ID_           VARCHAR(64) COMMENT '租用用户Id',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (DIC_ID_)
);

/*==============================================================*/
/* Table: SYS_ELEM_RIGHT                                        */
/*==============================================================*/
CREATE TABLE SYS_ELEM_RIGHT
(
   RIGHT_ID_            VARCHAR(64) NOT NULL COMMENT '权限ID',
   COMP_ID_             VARCHAR(64) NOT NULL COMMENT '组件ID
            表单ID/组/字段ID/按钮ID',
   COMP_TYPE_           VARCHAR(20) NOT NULL COMMENT '组件类型
            Form=表单
            Group=组
            Field=字段
            Button=按钮
            ',
   RIGHT_TYPE_          VARCHAR(20) NOT NULL COMMENT '权限类型
            ReadOnly=只读
            Edit=可用
            Hidden=隐藏',
   IDENTITY_ID_         VARCHAR(64) NOT NULL COMMENT '用户标识ID',
   IDENTITY_TYPE_       VARCHAR(20) NOT NULL COMMENT '用户=User
            用户组=Group',
   PRIMARY KEY (RIGHT_ID_)
);

ALTER TABLE SYS_ELEM_RIGHT COMMENT '系统元素权限
表单、组、字段、按钮权限';

/*==============================================================*/
/* Table: SYS_FIELD                                             */
/*==============================================================*/
CREATE TABLE SYS_FIELD
(
   FIELD_ID_            VARCHAR(64) NOT NULL COMMENT '字段ID',
   MODULE_ID_           VARCHAR(64) NOT NULL COMMENT '模块ID',
   TITLE_               VARCHAR(50) NOT NULL COMMENT '标题',
   ATTR_NAME_           VARCHAR(50) NOT NULL COMMENT '字段名称',
   LINK_MOD_ID_         VARCHAR(64) COMMENT '关联模块ID',
   FIELD_TYPE_          VARCHAR(50) NOT NULL COMMENT '字段类型',
   FIELD_GROUP_         VARCHAR(50) COMMENT '字段分组',
   FIELD_LENGTH_        INT COMMENT '字段长度',
   PRECISION_           INT COMMENT '字段精度',
   SN_                  INT COMMENT '字段序号',
   COLSPAN_             INT COMMENT '跨列数',
   FIELD_CAT_           VARCHAR(20) COMMENT '字段分类
            FIELD_COMMON=普通字段
            FIELD_PK=主键字段
            FIELD_RELATION=关系字段
            ',
   RELATION_TYPE_       VARCHAR(20) COMMENT 'OneToMany
            ManyToOne
            OneToOne
            ManyToMany',
   EDIT_RIGHT_          VARCHAR(20) COMMENT '编辑权限',
   ADD_RIGHT_           VARCHAR(20) COMMENT '添加权限',
   IS_HIDDEN_           VARCHAR(6) COMMENT '是否隐藏',
   IS_READABLE_         VARCHAR(6) COMMENT '是否只读',
   IS_REQUIRED_         VARCHAR(6) COMMENT '是否必须',
   IS_DISABLED_         VARCHAR(6) COMMENT '是否禁用',
   ALLOW_GROUP_         VARCHAR(6) COMMENT '是否允许分组',
   ALLOW_SORT_          VARCHAR(6),
   ALLOW_SUM_           VARCHAR(6) COMMENT '是否允许统计',
   IS_DEFAULT_COL_      VARCHAR(8) COMMENT '是否缺省显示列',
   IS_QUERY_COL_        VARCHAR(8) COMMENT '是否缺省查询列',
   DEF_VALUE_           VARCHAR(50) COMMENT '缺省值',
   REMARK_              TEXT COMMENT '备注',
   SHOW_NAV_TREE_       VARCHAR(6) COMMENT '是否在导航树上展示',
   DB_FIELD_NAME_       VARCHAR(50) COMMENT '数据库字段名',
   DB_FIELD_FORMULA_    TEXT COMMENT '数据库字段公式',
   ALLOW_EXCEL_INSERT_  VARCHAR(6) COMMENT '是否允许Excel插入',
   ALLOW_EXCEL_EDIT_    VARCHAR(6) COMMENT '是否允许Excel编辑',
   HAS_ATTACH_          VARCHAR(6) COMMENT '是否允许有附件',
   IS_CHAR_CAT_         VARCHAR(6) COMMENT '是否图表分类',
   RENDERER_            VARCHAR(512),
   IS_USER_DEF_         VARCHAR(6) COMMENT '用户定义字段
            当为用户定义字段时，其展示方式则由JS上的字段展示控制',
   COMP_TYPE_           VARCHAR(50),
   JSON_CONFIG_         TEXT,
   LINK_ADD_MODE_       VARCHAR(16) COMMENT '关联字段值新增方式，只对关联的字段才有效
            有三种值，
            WINDOW=通过弹出对话框进行新增加
            SELECT=通过弹出窗口进行选择
            INNER=通过在列表中进行编辑增加处理',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (FIELD_ID_)
);

ALTER TABLE SYS_FIELD COMMENT '功能模块字段';

/*==============================================================*/
/* Table: SYS_FILE                                              */
/*==============================================================*/
CREATE TABLE SYS_FILE
(
   FILE_ID_             VARCHAR(64) NOT NULL,
   TYPE_ID_             VARCHAR(64) COMMENT '分类ID',
   FILE_NAME_           VARCHAR(100) NOT NULL COMMENT '文件名',
   NEW_FNAME_           VARCHAR(100) COMMENT '新文件名',
   PATH_                VARCHAR(255) NOT NULL COMMENT '文件路径',
   THUMBNAIL_           VARCHAR(120) COMMENT '图片缩略图',
   EXT_                 VARCHAR(20) COMMENT '扩展名',
   MINE_TYPE_           VARCHAR(50) COMMENT '附件类型',
   DESC_                VARCHAR(255) COMMENT '说明',
   TOTAL_BYTES_         INT COMMENT '总字节数',
   DEL_STATUS_          VARCHAR(20) COMMENT '删除标识',
   MODULE_ID_           VARCHAR(64) COMMENT '模块ID',
   RECORD_ID_           VARCHAR(64) COMMENT '记录ID',
   FROM_                VARCHAR(20) COMMENT '来源类型
            APPLICATION=应用级上传类型
            SELF=个性上传',
   TENANT_ID_           VARCHAR(64) COMMENT '租用用户Id',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   COVER_STATUS_        VARCHAR(20) COMMENT '生成PDF状态',
   PRIMARY KEY (FILE_ID_)
);

ALTER TABLE SYS_FILE COMMENT '系统附件';

/*==============================================================*/
/* Table: SYS_FORM_FIELD                                        */
/*==============================================================*/
CREATE TABLE SYS_FORM_FIELD
(
   FORM_FIELD_ID_       VARCHAR(64) NOT NULL COMMENT '表单字段ID',
   GROUP_ID_            VARCHAR(64) COMMENT '分组ID',
   FIELD_ID_            VARCHAR(64) COMMENT '字段ID',
   FIELD_NAME_          VARCHAR(50) NOT NULL,
   FIELD_LABEL_         VARCHAR(64) NOT NULL,
   SN_                  INT NOT NULL COMMENT '序号',
   HEIGHT_              INT COMMENT '高',
   WIDTH_               INT COMMENT '宽',
   COLSPAN_             INT COMMENT '列跨度',
   JSON_CONF_           TEXT COMMENT '其他JSON设置',
   COMP_TYPE_           VARCHAR(50),
   TENANT_ID_           VARCHAR(64) COMMENT '租用用户Id',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (FORM_FIELD_ID_)
);

ALTER TABLE SYS_FORM_FIELD COMMENT '表单组内字段';

/*==============================================================*/
/* Table: SYS_FORM_GROUP                                        */
/*==============================================================*/
CREATE TABLE SYS_FORM_GROUP
(
   GROUP_ID_            VARCHAR(64) NOT NULL,
   FORM_SCHEMA_ID_      VARCHAR(64) COMMENT '表单方案ID',
   TITLE_               VARCHAR(50) NOT NULL COMMENT '组标题',
   SN_                  INT NOT NULL COMMENT '序号',
   DISPLAY_MODE_        VARCHAR(50) COMMENT '显示模式',
   COLLAPSIBLE_         VARCHAR(8) COMMENT '是否可收缩',
   COLLAPSED_           VARCHAR(8) COMMENT '默认收缩',
   SUB_MODEL_ID_        VARCHAR(64) COMMENT '子模块ID',
   JSON_CONFIG_         TEXT COMMENT '其他JSON配置',
   COL_NUMS_            INT COMMENT '列数',
   PRIMARY KEY (GROUP_ID_)
);

ALTER TABLE SYS_FORM_GROUP COMMENT '系统表单字段分组';

/*==============================================================*/
/* Table: SYS_FORM_SCHEMA                                       */
/*==============================================================*/
CREATE TABLE SYS_FORM_SCHEMA
(
   FORM_SCHEMA_ID_      VARCHAR(64) NOT NULL COMMENT '表单方案ID',
   MODULE_ID_           VARCHAR(64) COMMENT '模块ID',
   SCHEMA_NAME_         VARCHAR(64) NOT NULL COMMENT '方案名称',
   TITLE_               VARCHAR(50) COMMENT '表单标题',
   SN_                  INT NOT NULL COMMENT '方案排序',
   IS_SYSTEM_           VARCHAR(8) NOT NULL COMMENT '是否为系统默认',
   SCHEMA_KEY_          VARCHAR(50) NOT NULL COMMENT '方案Key',
   WIN_WIDTH_           INT NOT NULL COMMENT '窗口宽',
   WIN_HEIGHT_          INT NOT NULL COMMENT '窗口高',
   COL_NUMS_            INT NOT NULL COMMENT '列数',
   DISPLAY_MODE_        VARCHAR(50) NOT NULL COMMENT '显示模式',
   JSON_CONFIG_         TEXT COMMENT '其他JSON配置',
   PRIMARY KEY (FORM_SCHEMA_ID_)
);

ALTER TABLE SYS_FORM_SCHEMA COMMENT '表单方案';

/*==============================================================*/
/* Table: SYS_GRID_FIELD                                        */
/*==============================================================*/
CREATE TABLE SYS_GRID_FIELD
(
   GD_FIELD_ID_         VARCHAR(64) NOT NULL,
   FIELD_ID_            VARCHAR(64) COMMENT '字段ID',
   FIELD_NAME_          VARCHAR(50),
   FIELD_TITLE_         VARCHAR(50) NOT NULL,
   GRID_VIEW_ID_        VARCHAR(64) COMMENT '所属图视图ID
            当不属于任何分组时，需要填写该值',
   PARENT_ID_           VARCHAR(64),
   PATH_                VARCHAR(255),
   ITEM_TYPE_           VARCHAR(20) COMMENT '项类型
            GROUP=分组
            FIELD=字段',
   SN_                  INT NOT NULL COMMENT '序号',
   IS_LOCK_             VARCHAR(8) COMMENT '是否锁定',
   ALLOW_SORT_          VARCHAR(8) COMMENT '是否允许排序',
   IS_HIDDEN_           VARCHAR(8) COMMENT '是否隐藏',
   ALLOW_SUM_           VARCHAR(8) COMMENT '是否允许总计',
   COL_WIDTH_           INT COMMENT '列宽',
   IS_EXPORT_           VARCHAR(8) COMMENT '是否允许导出',
   FOMART_              VARCHAR(250) COMMENT '格式化',
   REMARK_              TEXT COMMENT '备注',
   TENANT_ID_           VARCHAR(64) COMMENT '租用用户Id',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (GD_FIELD_ID_)
);

ALTER TABLE SYS_GRID_FIELD COMMENT '列表视图分组及字段';

/*==============================================================*/
/* Table: SYS_GRID_VIEW                                         */
/*==============================================================*/
CREATE TABLE SYS_GRID_VIEW
(
   GRID_VIEW_ID_        VARCHAR(64) NOT NULL,
   MODULE_ID_           VARCHAR(64) COMMENT '模块ID',
   NAME_                VARCHAR(60) NOT NULL COMMENT '名称',
   IS_SYSTEM_           VARCHAR(8) COMMENT '是否系统默认',
   IS_DEFAULT_          VARCHAR(8),
   ALLOW_EDIT_          VARCHAR(8) COMMENT '是否在表格中编辑',
   CLICK_ROW_ACTION_    VARCHAR(120) COMMENT '点击行动作',
   DEF_SORT_FIELD_      VARCHAR(50) COMMENT '默认排序',
   SN_                  INT NOT NULL COMMENT '序号',
   REMARK_              TEXT COMMENT '备注',
   TENANT_ID_           VARCHAR(64) COMMENT '租用用户Id',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (GRID_VIEW_ID_)
);

ALTER TABLE SYS_GRID_VIEW COMMENT '列表展示视图';

/*==============================================================*/
/* Table: SYS_INST                                              */
/*==============================================================*/
CREATE TABLE SYS_INST
(
   INST_ID_             VARCHAR(64) NOT NULL,
   NAME_CN_             VARCHAR(256) NOT NULL COMMENT '公司中文名',
   NAME_EN_             VARCHAR(256) NOT NULL COMMENT '公司英文名',
   BUS_LICE_NO_         VARCHAR(50) NOT NULL,
   INST_NO_             VARCHAR(50) NOT NULL COMMENT '机构编码',
   BUS_LICE_FILE_ID_    VARCHAR(64) COMMENT '公司营业执照图片',
   REG_CODE_FILE_ID_    VARCHAR(64) COMMENT '组织机构代码证图',
   DOMAIN_              VARCHAR(100) NOT NULL COMMENT '公司域名
            唯一，用户后续的账号均是以此为缀，如公司的域名为abc.com,管理员的账号为admin@abc.com',
   NAME_CN_S_           VARCHAR(80) COMMENT '公司简称(中文)',
   NAME_EN_S_           VARCHAR(80) COMMENT '公司简称(英文)',
   LEGAL_MAN_           VARCHAR(64) COMMENT '公司法人',
   DESCP_               TEXT COMMENT '公司描述',
   ADDRESS_             VARCHAR(128) COMMENT '地址',
   PHONE_               VARCHAR(30) COMMENT '联系电话',
   EMAIL_               VARCHAR(255),
   FAX_                 VARCHAR(30) COMMENT '传真',
   CONTRACTOR_          VARCHAR(30) COMMENT '联系人',
   DS_NAME_             VARCHAR(64) COMMENT '数据源名称',
   DS_ALIAS_            VARCHAR(64) COMMENT '数据源别名',
   HOME_URL_            VARCHAR(120),
   INST_TYPE_           VARCHAR(50) COMMENT '机构类型',
   STATUS_              VARCHAR(30) COMMENT '状态',
   PARENT_ID_           VARCHAR(64) COMMENT '父ID',
   PATH_                VARCHAR(1024) COMMENT '路径',
   TENANT_ID_           VARCHAR(64) COMMENT '租用用户Id',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (INST_ID_)
);

ALTER TABLE SYS_INST COMMENT '注册机构';

/*==============================================================*/
/* Table: SYS_INST_TYPE                                         */
/*==============================================================*/
CREATE TABLE SYS_INST_TYPE
(
   TYPE_ID_             VARCHAR(64) NOT NULL COMMENT '类型',
   TYPE_CODE_           VARCHAR(50) COMMENT '类型编码',
   TYPE_NAME_           VARCHAR(100) COMMENT '类型名称',
   ENABLED_             VARCHAR(20) COMMENT '是否启用',
   IS_DEFAULT_          VARCHAR(20) COMMENT '是否系统缺省',
   HOME_URL_            VARCHAR(200),
   DESCP_               VARCHAR(500) COMMENT '描述',
   TENANT_ID_           VARCHAR(64) COMMENT '租用用户Id',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (TYPE_ID_)
);

ALTER TABLE SYS_INST_TYPE COMMENT '机构类型';

/*==============================================================*/
/* Table: SYS_INST_TYPE_MENU                                    */
/*==============================================================*/
CREATE TABLE SYS_INST_TYPE_MENU
(
   ID_                  VARCHAR(64) NOT NULL COMMENT '主键',
   INST_TYPE_ID_        VARCHAR(64) COMMENT '机构类型ID',
   SYS_ID_              VARCHAR(64) COMMENT '子系统ID',
   MENU_ID_             VARCHAR(64) COMMENT '菜单ID',
   PRIMARY KEY (ID_)
);

ALTER TABLE SYS_INST_TYPE_MENU COMMENT '机构类型授权菜单';

/*==============================================================*/
/* Table: SYS_LDAP_CN                                           */
/*==============================================================*/
CREATE TABLE SYS_LDAP_CN
(
   SYS_LDAP_USER_ID_    VARCHAR(64) NOT NULL COMMENT 'LDAP用户（主键）',
   USER_ID_             VARCHAR(64),
   SYS_LDAP_OU_ID_      VARCHAR(64) COMMENT '组织单元（主键）',
   USER_ACCOUNT_        VARCHAR(64) COMMENT '账户',
   USER_CODE_           VARCHAR(64) COMMENT '用户编号',
   NAME_                VARCHAR(64) COMMENT '名称',
   TEL_                 VARCHAR(64) COMMENT '电话',
   MAIL_                VARCHAR(512) COMMENT '邮件',
   USN_CREATED_         VARCHAR(64) COMMENT 'USN_CREATED',
   USN_CHANGED_         VARCHAR(64) COMMENT 'USN_CHANGED',
   WHEN_CREATED_        VARCHAR(64) COMMENT 'LDAP创建时间',
   WHEN_CHANGED_        VARCHAR(64) COMMENT 'LDAP更新时间',
   STATUS_              VARCHAR(64) COMMENT '状态',
   USER_PRINCIPAL_NAME_ VARCHAR(512) COMMENT '用户主要名称',
   DN_                  VARCHAR(512) COMMENT '区分名',
   OC_                  VARCHAR(512) COMMENT '对象类型',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   PRIMARY KEY (SYS_LDAP_USER_ID_)
);

ALTER TABLE SYS_LDAP_CN COMMENT 'SYS_LDAP_CN【LADP用户】';

/*==============================================================*/
/* Table: SYS_LDAP_CONFIG                                       */
/*==============================================================*/
CREATE TABLE SYS_LDAP_CONFIG
(
   SYS_LDAP_CONFIG_ID_  VARCHAR(64) NOT NULL COMMENT 'LDAP配置(主键)',
   STATUS_              VARCHAR(64) COMMENT '状态',
   STATUS_CN_           VARCHAR(64) COMMENT '状态',
   DN_BASE_             VARCHAR(1024) COMMENT '基本DN',
   DN_DATUM_            VARCHAR(1024) COMMENT '基准DN',
   URL_                 VARCHAR(1024) COMMENT '地址',
   ACCOUNT_             VARCHAR(64) COMMENT '账号名称',
   PASSWORD_            VARCHAR(64) COMMENT '密码',
   DEPT_FILTER_         VARCHAR(1024) COMMENT '部门过滤器',
   USER_FILTER_         VARCHAR(1024) COMMENT '用户过滤器',
   ATT_USER_NO_         VARCHAR(64) COMMENT '用户编号属性',
   ATT_USER_ACC_        VARCHAR(64) COMMENT '用户账户属性',
   ATT_USER_NAME_       VARCHAR(64) COMMENT '用户名称属性',
   ATT_USER_PWD_        VARCHAR(1024) COMMENT '用户密码属性',
   ATT_USER_TEL_        VARCHAR(64) COMMENT '用户电话属性',
   ATT_USER_MAIL_       VARCHAR(64) COMMENT '用户邮件属性',
   ATT_DEPT_NAME_       VARCHAR(64) COMMENT '部门名称属性',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   PRIMARY KEY (SYS_LDAP_CONFIG_ID_)
);

/*==============================================================*/
/* Table: SYS_LDAP_LOG                                          */
/*==============================================================*/
CREATE TABLE SYS_LDAP_LOG
(
   LOG_ID_              VARCHAR(64) NOT NULL COMMENT '日志主键',
   LOG_NAME_            VARCHAR(256) COMMENT '日志名称',
   CONTENT_             TEXT COMMENT '日志内容',
   START_TIME_          DATETIME COMMENT '开始时间',
   END_TIME_            DATETIME COMMENT '结束时间',
   RUN_TIME_            INT COMMENT '持续时间',
   STATUS_              VARCHAR(64) COMMENT '状态',
   SYNC_TYPE_           VARCHAR(64) COMMENT '同步类型',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   PRIMARY KEY (LOG_ID_)
);

ALTER TABLE SYS_LDAP_LOG COMMENT 'SYS_LDAP_LOG【LDAP同步日志】
';

/*==============================================================*/
/* Table: SYS_LDAP_OU                                           */
/*==============================================================*/
CREATE TABLE SYS_LDAP_OU
(
   SYS_LDAP_OU_ID_      VARCHAR(64) NOT NULL COMMENT '组织单元（主键）',
   GROUP_ID_            VARCHAR(64) COMMENT '用户组ID',
   SN_                  INT COMMENT '序号',
   DEPTH_               INT COMMENT '层次',
   PATH_                VARCHAR(1024) COMMENT '路径',
   PARENT_ID_           VARCHAR(64) COMMENT '父目录',
   STATUS_              VARCHAR(64) COMMENT '状态',
   OU_                  VARCHAR(512) COMMENT '组织单元',
   NAME_                VARCHAR(512) COMMENT '组织单元名称',
   DN_                  VARCHAR(512) COMMENT '区分名',
   OC_                  VARCHAR(512) COMMENT '对象类型',
   USN_CREATED_         VARCHAR(64) COMMENT 'USN_CREATED',
   USN_CHANGED_         VARCHAR(64) COMMENT 'USN_CHANGED',
   WHEN_CREATED_        VARCHAR(64) COMMENT 'LDAP创建时间',
   WHEN_CHANGED_        VARCHAR(64) COMMENT 'LDAP更新时间',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   PRIMARY KEY (SYS_LDAP_OU_ID_)
);

ALTER TABLE SYS_LDAP_OU COMMENT 'SYS_LDAP_OU【LDAP组织单元】';

/*==============================================================*/
/* Table: SYS_LIST_SOL                                          */
/*==============================================================*/
CREATE TABLE SYS_LIST_SOL
(
   SOL_ID_              VARCHAR(64) NOT NULL COMMENT '解决方案ID',
   KEY_                 VARCHAR(100) NOT NULL COMMENT '标识健',
   NAME_                VARCHAR(128) NOT NULL COMMENT '名称',
   DESCP_               VARCHAR(256) COMMENT '描述',
   RIGHT_CONFIGS_       TEXT NOT NULL COMMENT '权限配置',
   TENANT_ID_           VARCHAR(64) COMMENT '租户ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (SOL_ID_)
);

ALTER TABLE SYS_LIST_SOL COMMENT '系统列表方案';

/*==============================================================*/
/* Table: SYS_MENU                                              */
/*==============================================================*/
CREATE TABLE SYS_MENU
(
   MENU_ID_             VARCHAR(64) NOT NULL,
   SYS_ID_              VARCHAR(64) COMMENT '所属子系统',
   NAME_                VARCHAR(60) NOT NULL COMMENT '菜单名称',
   KEY_                 VARCHAR(80) COMMENT '菜单Key',
   FORM_                VARCHAR(80) COMMENT '实体表单',
   ENTITY_NAME_         VARCHAR(100) COMMENT '模块实体名',
   MODULE_ID_           VARCHAR(64) COMMENT '模块ID',
   ICON_CLS_            VARCHAR(32) COMMENT '图标样式',
   IMG_                 VARCHAR(50) COMMENT '图标',
   PARENT_ID_           VARCHAR(64) NOT NULL COMMENT '上级父ID',
   DEPTH_               INT COMMENT '层次',
   PATH_                VARCHAR(256) COMMENT '路径',
   SN_                  INT COMMENT '序号',
   URL_                 VARCHAR(256) COMMENT '访问地址URL',
   SHOW_TYPE_           VARCHAR(20) COMMENT '访问方式
             缺省打开
            在新窗口打开',
   IS_BTN_MENU_         VARCHAR(20) NOT NULL COMMENT '表示是否为按钮菜单
            YES=为按钮菜单
            NO=非按钮菜单',
   CHILDS_              INT,
   TENANT_ID_           VARCHAR(64) COMMENT '租用用户Id',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   BO_LIST_ID_          VARCHAR(256) COMMENT 'BO列表ID',
   PRIMARY KEY (MENU_ID_)
);

ALTER TABLE SYS_MENU COMMENT '菜单项目';

/*==============================================================*/
/* Table: SYS_MODULE                                            */
/*==============================================================*/
CREATE TABLE SYS_MODULE
(
   MODULE_ID_           VARCHAR(64) NOT NULL COMMENT '模块ID',
   TITLE_               VARCHAR(50) NOT NULL COMMENT '模块标题',
   DESCP_               VARCHAR(50) COMMENT '描述',
   REQ_URL_             VARCHAR(150) COMMENT '映射URL地址',
   ICON_CLS_            VARCHAR(20) COMMENT 'icon地址样式',
   SHORT_NAME_          VARCHAR(20) COMMENT '简称',
   SYS_ID_              VARCHAR(64) COMMENT '所属子系统',
   TABLE_NAME_          VARCHAR(50) COMMENT '表名',
   ENTITY_NAME_         VARCHAR(100) COMMENT '实体名',
   NAMESPACE_           VARCHAR(100) COMMENT '命名空间',
   PK_FIELD_            VARCHAR(50) NOT NULL COMMENT '主键字段名',
   PK_DB_FIELD_         VARCHAR(50),
   CODE_FIELD_          VARCHAR(50) COMMENT '编码字段名',
   ORDER_FIELD_         VARCHAR(50) COMMENT '排序字段名',
   DATE_FIELD_          VARCHAR(50) COMMENT '日期字段',
   YEAR_FIELD_          VARCHAR(50) COMMENT '年份字段',
   MONTH_FIELD_         VARCHAR(50) COMMENT '月份字段',
   SENSON_FIELD_        VARCHAR(50) COMMENT '季度字段',
   FILE_FIELD_          VARCHAR(50) COMMENT '文件字段',
   IS_ENABLED_          VARCHAR(6) NOT NULL COMMENT '是否启用',
   ALLOW_AUDIT_         VARCHAR(6) NOT NULL COMMENT '是否审计执行日记',
   ALLOW_APPROVE_       VARCHAR(6) NOT NULL COMMENT '是否启动审批',
   HAS_ATTACHS_         VARCHAR(6) NOT NULL COMMENT '是否有附件',
   DEF_ORDER_FIELD_     VARCHAR(50) COMMENT '缺省排序字段',
   SEQ_CODE_            VARCHAR(50) COMMENT '编码流水键',
   HAS_CHART_           VARCHAR(6) NOT NULL COMMENT '是否有图表',
   HELP_HTML_           TEXT COMMENT '帮助内容',
   IS_DEFAULT_          VARCHAR(8) COMMENT '是否系统默认
            YES
            NO',
   TENANT_ID_           VARCHAR(64) COMMENT '租用用户Id',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (MODULE_ID_)
);

ALTER TABLE SYS_MODULE COMMENT '系统功能模块';

/*==============================================================*/
/* Table: SYS_OFFICE                                            */
/*==============================================================*/
CREATE TABLE SYS_OFFICE
(
   ID_                  VARCHAR(64) NOT NULL COMMENT '主键',
   NAME_                VARCHAR(200) COMMENT '名称',
   SUPPORT_VERSION_     VARCHAR(64) COMMENT '支持版本',
   VERSION_             INT COMMENT '版本',
   TENANT_ID_           VARCHAR(64) COMMENT '租户ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (ID_)
);

ALTER TABLE SYS_OFFICE COMMENT 'OFFICE附件';

/*==============================================================*/
/* Table: SYS_OFFICE_TEMPLATE                                   */
/*==============================================================*/
CREATE TABLE SYS_OFFICE_TEMPLATE
(
   ID_                  VARCHAR(64) NOT NULL COMMENT '主键',
   NAME_                VARCHAR(200) COMMENT '名称',
   TYPE_                VARCHAR(20) COMMENT '类型(normal,red)',
   DOC_ID_              VARCHAR(200) COMMENT '文档ID',
   DOC_NAME_            VARCHAR(200) COMMENT '文件名',
   DESCRIPTION_         VARCHAR(255) COMMENT '描述',
   TENANT_ID_           VARCHAR(64) COMMENT '租户ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (ID_)
);

ALTER TABLE SYS_OFFICE_TEMPLATE COMMENT 'OFFICE模板';

/*==============================================================*/
/* Table: SYS_OFFICE_VER                                        */
/*==============================================================*/
CREATE TABLE SYS_OFFICE_VER
(
   ID_                  VARCHAR(64) NOT NULL COMMENT '主键',
   OFFICE_ID_           VARCHAR(64) COMMENT 'OFFICE主键',
   VERSION_             INT COMMENT '版本',
   FILE_ID_             VARCHAR(64) COMMENT '附件ID',
   FILE_NAME_           VARCHAR(200) COMMENT '文件名',
   TENANT_ID_           VARCHAR(64) COMMENT '租户ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (ID_)
);

ALTER TABLE SYS_OFFICE_VER COMMENT 'OFFICE版本';

/*==============================================================*/
/* Table: SYS_PRIVATE_PROPERTIES                                */
/*==============================================================*/
CREATE TABLE SYS_PRIVATE_PROPERTIES
(
   ID_                  VARCHAR(64) NOT NULL COMMENT '主键',
   PRO_ID_              VARCHAR(64) COMMENT '参数主键',
   PRI_VALUE_           VARCHAR(2000) COMMENT '参数值',
   TENANT_ID_           VARCHAR(64) COMMENT '租户ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (ID_)
);

ALTER TABLE SYS_PRIVATE_PROPERTIES COMMENT '租户参数';

/*==============================================================*/
/* Table: SYS_PROPERTIES                                        */
/*==============================================================*/
CREATE TABLE SYS_PROPERTIES
(
   PRO_ID_              VARCHAR(64) NOT NULL COMMENT '属性ID',
   NAME_                VARCHAR(64) COMMENT '名称',
   ALIAS_               VARCHAR(64) COMMENT '别名',
   GLOBAL_              VARCHAR(64) COMMENT '是否全局',
   ENCRYPT_             VARCHAR(64) COMMENT '是否加密',
   VALUE_               VARCHAR(2000) COMMENT '属性值',
   CATEGORY_            VARCHAR(100) COMMENT '分类',
   DESCRIPTION_         VARCHAR(200) COMMENT '描述',
   TENANT_ID_           VARCHAR(64) COMMENT '租户ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (PRO_ID_)
);

ALTER TABLE SYS_PROPERTIES COMMENT '系统属性表';

/*==============================================================*/
/* Table: SYS_QUARTZ_LOG                                        */
/*==============================================================*/
CREATE TABLE SYS_QUARTZ_LOG
(
   LOG_ID_              VARCHAR(64) NOT NULL COMMENT '日志??键ID',
   ALIAS_               VARCHAR(256) COMMENT '任务别名',
   JOB_NAME_            VARCHAR(256) COMMENT '任务名称',
   TRIGGER_NAME_        VARCHAR(256) COMMENT '计划名称',
   CONTENT_             TEXT COMMENT '日志内容',
   START_TIME_          DATETIME COMMENT '开始时间',
   END_TIME_            DATETIME COMMENT '结束时间',
   RUN_TIME_            INT COMMENT '持续时间',
   STATUS_              VARCHAR(64) COMMENT '状态STATUS_',
   TENANT_ID_           VARCHAR(64) COMMENT '租用ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (LOG_ID_)
);

ALTER TABLE SYS_QUARTZ_LOG COMMENT '定时器日志
';

/*==============================================================*/
/* Table: SYS_REPORT                                            */
/*==============================================================*/
CREATE TABLE SYS_REPORT
(
   REP_ID_              VARCHAR(64) NOT NULL COMMENT '报表ID',
   TREE_ID_             VARCHAR(64) COMMENT '分类Id',
   SUBJECT_             VARCHAR(128) NOT NULL COMMENT '标题',
   KEY_                 VARCHAR(128) COMMENT '标识key',
   DESCP_               VARCHAR(500) COMMENT '描述',
   FILE_PATH_           VARCHAR(128) NOT NULL COMMENT '报表模块的jasper文件的路径',
   SELF_HANDLE_BEAN_    VARCHAR(100) COMMENT '报表参数自定义处理Bean',
   FILE_ID_             VARCHAR(64),
   IS_DEFAULT_          VARCHAR(20) COMMENT '是否缺省
            1=缺省
            0=非缺省',
   PARAM_CONFIG_        TEXT COMMENT '参数配置',
   ENGINE_              VARCHAR(30) COMMENT '报表解析引擎，可同时支持多种报表引擎类型，如
            JasperReport
            FineReport',
   TENANT_ID_           VARCHAR(64) COMMENT '租用用户Id',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   DS_ALIAS_            VARCHAR(64) COMMENT '数据源',
   PRIMARY KEY (REP_ID_)
);

ALTER TABLE SYS_REPORT COMMENT '系统报表';

/*==============================================================*/
/* Table: SYS_RES_AUTH                                          */
/*==============================================================*/
CREATE TABLE SYS_RES_AUTH
(
   AUTH_ID_             VARCHAR(64) NOT NULL COMMENT '授权ID',
   RES_ID_              VARCHAR(64) NOT NULL COMMENT '资源主键，其值为不同表的主键',
   GROUP_ID_            VARCHAR(64) NOT NULL COMMENT '用户组ID',
   RES_TYPE_            VARCHAR(80) NOT NULL COMMENT '资源类型
            暂时使用表名',
   RIGHT_               VARCHAR(20) NOT NULL COMMENT '权限
            ALL=所有权限
            GET=查看
            DEL=删除
            EDIT=编辑
            QERY=查询
            ',
   VISIT_SUB_           VARCHAR(20) COMMENT 'YES
            NO',
   PRIMARY KEY (AUTH_ID_)
);

ALTER TABLE SYS_RES_AUTH COMMENT '系统资源权限表';

/*==============================================================*/
/* Table: SYS_SEARCH                                            */
/*==============================================================*/
CREATE TABLE SYS_SEARCH
(
   SEARCH_ID_           VARCHAR(64) NOT NULL,
   NAME_                VARCHAR(100) NOT NULL COMMENT '搜索名称',
   ENTITY_NAME_         VARCHAR(100) NOT NULL COMMENT '实体名称',
   ENABLED_             VARCHAR(8) NOT NULL COMMENT '是否启用',
   IS_DEFAULT_          VARCHAR(8),
   TENANT_ID_           VARCHAR(64) COMMENT '租用用户Id',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (SEARCH_ID_)
);

ALTER TABLE SYS_SEARCH COMMENT '高级搜索';

/*==============================================================*/
/* Table: SYS_SEARCH_ITEM                                       */
/*==============================================================*/
CREATE TABLE SYS_SEARCH_ITEM
(
   ITEM_ID_             VARCHAR(64) NOT NULL,
   SEARCH_ID_           VARCHAR(64) NOT NULL,
   NODE_TYPE_           VARCHAR(20) NOT NULL COMMENT '条件类型',
   NODE_TYPE_LABEL_     VARCHAR(20),
   PARENT_ID_           VARCHAR(64) NOT NULL COMMENT '父ID',
   PATH_                VARCHAR(256) COMMENT '路径',
   SN_                  INT,
   FIELD_TYPE_          VARCHAR(20) COMMENT '字段类型',
   LABEL_               VARCHAR(100) NOT NULL COMMENT '条件标签',
   FIELD_OP_            VARCHAR(20),
   FIELD_OP_LABEL_      VARCHAR(32),
   FIELD_TITLE_         VARCHAR(50) COMMENT '字段标签',
   FIELD_ID_            VARCHAR(64) COMMENT '字段ID',
   FIELD_NAME_          VARCHAR(64) COMMENT '字段名称',
   FIELD_VAL_           VARCHAR(80) COMMENT '字段值',
   CTL_TYPE_            VARCHAR(50) COMMENT '控件类型',
   FORMAT_              VARCHAR(50) COMMENT '值格式',
   PRE_HANDLE_          TEXT COMMENT '预处理',
   TENANT_ID_           VARCHAR(64) COMMENT '租用ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (ITEM_ID_)
);

ALTER TABLE SYS_SEARCH_ITEM COMMENT '搜索条件项';

/*==============================================================*/
/* Table: SYS_SEQ_ID                                            */
/*==============================================================*/
CREATE TABLE SYS_SEQ_ID
(
   SEQ_ID_              VARCHAR(64) NOT NULL COMMENT '流水号ID',
   NAME_                VARCHAR(80) NOT NULL COMMENT '名称',
   ALIAS_               VARCHAR(50) COMMENT '别名',
   CUR_DATE_            DATETIME COMMENT '当前日期',
   RULE_                VARCHAR(100) NOT NULL COMMENT '规则',
   RULE_CONF_           VARCHAR(512) COMMENT '规则配置',
   INIT_VAL_            INT COMMENT '初始值',
   GEN_TYPE_            VARCHAR(20) COMMENT '生成方式
            DAY=每天
            WEEK=每周
            MONTH=每月
            YEAR=每年
            AUTO=一直增长',
   LEN_                 INT COMMENT '流水号长度',
   CUR_VAL              INT COMMENT '当前值',
   STEP_                SMALLINT COMMENT '步长',
   MEMO_                VARCHAR(512) COMMENT '备注',
   IS_DEFAULT_          VARCHAR(20) COMMENT '系统缺省
            YES
            NO',
   TENANT_ID_           VARCHAR(64) COMMENT '租用用户Id',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (SEQ_ID_)
);

ALTER TABLE SYS_SEQ_ID COMMENT '系统流水号';

/*==============================================================*/
/* Table: SYS_STAMP                                             */
/*==============================================================*/
CREATE TABLE SYS_STAMP
(
   ID_                  VARCHAR(64) NOT NULL COMMENT '主键',
   NAME_                VARCHAR(200) COMMENT '签章名称',
   SIGN_USER_           VARCHAR(64) COMMENT '签章用户',
   PASSWORD_            VARCHAR(64) COMMENT '签章密码',
   STAMP_ID_            VARCHAR(64) COMMENT '印章文件ID',
   DESCRIPTION_         VARCHAR(255) COMMENT '描述',
   TENANT_ID_           VARCHAR(64) COMMENT '租户ID',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (ID_)
);

ALTER TABLE SYS_STAMP COMMENT 'office印章';

/*==============================================================*/
/* Table: SYS_SUBSYS                                            */
/*==============================================================*/
CREATE TABLE SYS_SUBSYS
(
   SYS_ID_              VARCHAR(64) NOT NULL,
   NAME_                VARCHAR(80) NOT NULL COMMENT '系统名称',
   KEY_                 VARCHAR(64) NOT NULL COMMENT '系统Key',
   LOGO_                VARCHAR(120) COMMENT '系统Logo',
   IS_DEFAULT_          VARCHAR(12) NOT NULL COMMENT '是否缺省',
   HOME_URL_            VARCHAR(120) COMMENT '首页地址',
   STATUS_              VARCHAR(20) NOT NULL COMMENT '状态
            YES/NO',
   DESCP_               VARCHAR(256) COMMENT '描述',
   ICON_CLS_            VARCHAR(50) COMMENT '图标样式',
   SN_                  INT,
   INST_TYPE_           VARCHAR(50),
   IS_SAAS_             VARCHAR(20),
   TENANT_ID_           VARCHAR(64) COMMENT '租用用户Id',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (SYS_ID_)
);

ALTER TABLE SYS_SUBSYS COMMENT '子系统';

/*==============================================================*/
/* Table: SYS_TEMPLATE                                          */
/*==============================================================*/
CREATE TABLE SYS_TEMPLATE
(
   TEMP_ID_             VARCHAR(64) NOT NULL COMMENT '模板ID',
   TREE_ID_             VARCHAR(64) COMMENT '分类Id',
   NAME_                VARCHAR(80) NOT NULL COMMENT '模板名称',
   KEY_                 VARCHAR(50) NOT NULL COMMENT '模板KEY',
   CAT_KEY_             VARCHAR(64) NOT NULL COMMENT '模板分类Key',
   IS_DEFAULT_          VARCHAR(20) NOT NULL COMMENT '是否系统缺省',
   CONTENT_             TEXT NOT NULL COMMENT '模板内容',
   DESCP                VARCHAR(500) COMMENT '模板描述',
   TENANT_ID_           VARCHAR(64) COMMENT '租用用户Id',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (TEMP_ID_)
);

ALTER TABLE SYS_TEMPLATE COMMENT '系统模板';

/*==============================================================*/
/* Table: SYS_TREE                                              */
/*==============================================================*/
CREATE TABLE SYS_TREE
(
   TREE_ID_             VARCHAR(64) NOT NULL COMMENT '分类Id',
   NAME_                VARCHAR(128) NOT NULL COMMENT '名称',
   PATH_                VARCHAR(1024) COMMENT '路径',
   DEPTH_               INT NOT NULL COMMENT '层次',
   PARENT_ID_           VARCHAR(64) COMMENT '父节点',
   KEY_                 VARCHAR(64) NOT NULL COMMENT '节点的分类Key',
   CODE_                VARCHAR(50) COMMENT '同级编码',
   DESCP_               VARCHAR(512) COMMENT '描述',
   CAT_KEY_             VARCHAR(64) NOT NULL COMMENT '系统树分类key',
   SN_                  INT NOT NULL COMMENT '序号',
   DATA_SHOW_TYPE_      VARCHAR(20) COMMENT '数据项展示类型
            默认为:FLAT=平铺类型
            TREE=树类型',
   CHILDS_              INT,
   USER_ID_             VARCHAR(64) COMMENT '用户ID
            树所属的用户ID,可空',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构Id',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (TREE_ID_)
);

ALTER TABLE SYS_TREE COMMENT '系统分类树
用于显示树层次结构的分类
可以允许任何层次结构';

/*==============================================================*/
/* Table: SYS_TREE_CAT                                          */
/*==============================================================*/
CREATE TABLE SYS_TREE_CAT
(
   CAT_ID_              VARCHAR(64) NOT NULL,
   KEY_                 VARCHAR(64) NOT NULL COMMENT '分类Key',
   NAME_                VARCHAR(64) NOT NULL COMMENT '分类名称',
   SN_                  INT COMMENT '序号',
   DESCP_               VARCHAR(255) COMMENT '描述',
   TENANT_ID_           VARCHAR(64) COMMENT '租用用户Id',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (CAT_ID_)
);

ALTER TABLE SYS_TREE_CAT COMMENT '系统树分类类型';

/*==============================================================*/
/* Table: SYS_TYPE_SUB_REF                                      */
/*==============================================================*/
CREATE TABLE SYS_TYPE_SUB_REF
(
   ID_                  VARCHAR(64) NOT NULL COMMENT '主键',
   INST_TYPE_ID_        VARCHAR(64) COMMENT '机构类型ID',
   SUB_SYS_ID_          VARCHAR(64) COMMENT '子系统ID',
   TENANT_ID_           VARCHAR(64) COMMENT '租用用户Id',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (ID_)
);

ALTER TABLE SYS_TYPE_SUB_REF COMMENT '机构子系统关系表';

/*==============================================================*/
/* Table: TASK_NODE_MSG                                         */
/*==============================================================*/
CREATE TABLE TASK_NODE_MSG
(
   MSG_ID_              VARCHAR(64) NOT NULL COMMENT '消息ID',
   SUBJECT_             VARCHAR(128) COMMENT '主题',
   CONTENT_             VARCHAR(4000) COMMENT '内容',
   LINKED_              VARCHAR(512) COMMENT '链接',
   TASK_ID_             VARCHAR(64) COMMENT '任务ID',
   TASK_NODE_NAME_      VARCHAR(128) COMMENT '任务节点名称',
   DEPLOY_ID_           VARCHAR(64) COMMENT '部署ID',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   PRIMARY KEY (MSG_ID_)
);

ALTER TABLE TASK_NODE_MSG COMMENT '任务节点消息表(用于第三方接收)

';

/*==============================================================*/
/* Table: TASK_TIP_MSG                                          */
/*==============================================================*/
CREATE TABLE TASK_TIP_MSG
(
   ID_                  VARCHAR(64) NOT NULL COMMENT '提示ID',
   TASK_ID_             VARCHAR(64) COMMENT '任务ID',
   SENDER_ID_           VARCHAR(64) COMMENT '发送者ID',
   SENDER_TIME_         DATETIME COMMENT '发送时间',
   RECEIVER_ID_         VARCHAR(64) COMMENT '接收者ID',
   SUBJECT_             VARCHAR(256) COMMENT '主题',
   CONTENT_             VARCHAR(4000) COMMENT '内容',
   LINKED_              VARCHAR(512) COMMENT '链接',
   SENDBACK_STATUS_     VARCHAR(2) COMMENT '返回状态',
   SHORT_CONTENT_       VARCHAR(128) COMMENT '简介',
   STATUS_              VARCHAR(2) COMMENT '状态',
   IMPORTANT_STATUS_    VARCHAR(2) COMMENT '主要状态',
   MSG_STATUS_          VARCHAR(32) COMMENT '消息状态',
   READ_TIME_           DATETIME COMMENT '阅读时间',
   GEN_TIME_            DATETIME COMMENT '资料时间',
   ISINVALID_           VARCHAR(64) COMMENT '撤销',
   INCALID_TIME_        DATETIME COMMENT '撤销时间',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   PRIMARY KEY (ID_)
);

ALTER TABLE TASK_TIP_MSG COMMENT '任务提示消息表(用于第三方接收)

';

/*==============================================================*/
/* Table: WX_ENT_AGENT                                          */
/*==============================================================*/
CREATE TABLE WX_ENT_AGENT
(
   ID_                  VARCHAR(64) NOT NULL COMMENT '主键',
   NAME_                VARCHAR(100),
   DESCRIPTION_         VARCHAR(200),
   DOMAIN_              VARCHAR(64) COMMENT '信任域名',
   HOME_URL_            VARCHAR(200),
   ENT_ID_              VARCHAR(64) COMMENT '企业主键',
   CORP_ID_             VARCHAR(64) COMMENT '企业ID',
   AGENT_ID_            VARCHAR(64) COMMENT '应用ID',
   SECRET_              VARCHAR(64) COMMENT '密钥',
   DEFAULT_AGENT_       INT COMMENT '是否默认',
   TENANT_ID_           VARCHAR(64) COMMENT '租户ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人',
   PRIMARY KEY (ID_)
);

ALTER TABLE WX_ENT_AGENT COMMENT '微信应用';

/*==============================================================*/
/* Table: WX_ENT_CORP                                           */
/*==============================================================*/
CREATE TABLE WX_ENT_CORP
(
   ID_                  VARCHAR(64) NOT NULL COMMENT '主键',
   CORP_ID_             VARCHAR(64) COMMENT '企业ID',
   SECRET_              VARCHAR(64) COMMENT '通讯录密钥',
   ENABLE_              INT COMMENT '是否启用企业微信',
   TENANT_ID_           VARCHAR(64) COMMENT '租户ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人',
   PRIMARY KEY (ID_)
);

ALTER TABLE WX_ENT_CORP COMMENT '微信企业配置';

/*==============================================================*/
/* Table: WX_KEY_WORD_REPLY                                     */
/*==============================================================*/
CREATE TABLE WX_KEY_WORD_REPLY
(
   ID_                  VARCHAR(64) NOT NULL COMMENT 'ID_',
   PUB_ID_              VARCHAR(64) COMMENT '公众号ID',
   KEY_WORD_            VARCHAR(512) COMMENT '关键字',
   REPLY_TYPE_          VARCHAR(64) COMMENT '回复方式',
   REPLY_CONTENT_       VARCHAR(1024) COMMENT '回复内容',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   PRIMARY KEY (ID_)
);

ALTER TABLE WX_KEY_WORD_REPLY COMMENT '公众号关键字回复';

/*==============================================================*/
/* Table: WX_MESSAGE_SEND                                       */
/*==============================================================*/
CREATE TABLE WX_MESSAGE_SEND
(
   ID                   VARCHAR(64) NOT NULL COMMENT 'ID',
   PUB_ID_              VARCHAR(64) COMMENT '公众号ID',
   MSG_TYPE_            VARCHAR(64) COMMENT '消息类型',
   SEND_TYPE_           VARCHAR(64) COMMENT '发送类型',
   RECEIVER_            VARCHAR(1024) COMMENT '接收者',
   CONTENT_             TEXT COMMENT '消息内容',
   SEND_STATE_          VARCHAR(64) COMMENT '发送状态',
   CONFIG_              TEXT COMMENT '备用配置',
   TENANT_ID_           VARCHAR(64) COMMENT '租户ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   PRIMARY KEY (ID)
);

ALTER TABLE WX_MESSAGE_SEND COMMENT '发送消息';

/*==============================================================*/
/* Table: WX_METERIAL                                           */
/*==============================================================*/
CREATE TABLE WX_METERIAL
(
   ID_                  VARCHAR(64) NOT NULL COMMENT '素材ID',
   PUB_ID_              VARCHAR(64) COMMENT '公众号ID',
   TERM_TYPE_           VARCHAR(64) COMMENT '期限类型',
   MEDIA_TYPE_          VARCHAR(64) COMMENT '素材类型',
   NAME_                VARCHAR(64) COMMENT '素材名',
   MEDIA_ID_            VARCHAR(64) COMMENT '微信后台指定ID',
   ART_CONFIG_          TEXT COMMENT '图文配置',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   PRIMARY KEY (ID_)
);

ALTER TABLE WX_METERIAL COMMENT '微信素材';

/*==============================================================*/
/* Table: WX_PUB_APP                                            */
/*==============================================================*/
CREATE TABLE WX_PUB_APP
(
   ID_                  VARCHAR(64) NOT NULL COMMENT 'ID',
   WX_NO_               VARCHAR(64) COMMENT '微信号',
   APP_ID_              VARCHAR(64) COMMENT 'APP_ID_',
   SECRET_              VARCHAR(64) COMMENT '密钥',
   TYPE_                VARCHAR(64) COMMENT '类型',
   AUTHED_              VARCHAR(64) COMMENT '是否认证',
   INTERFACE_URL_       VARCHAR(200) COMMENT '接口消息地址',
   TOKEN                VARCHAR(64) COMMENT 'token',
   JS_DOMAIN_           VARCHAR(200) COMMENT 'js安全域名',
   NAME_                VARCHAR(64) COMMENT '名称',
   ALIAS_               VARCHAR(64) COMMENT '别名',
   DESCRIPTION_         VARCHAR(200) COMMENT '描述',
   MENU_CONFIG_         TEXT COMMENT '菜单配置',
   OTHER_CONFIG_        TEXT COMMENT '其他配置',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   PRIMARY KEY (ID_)
);

ALTER TABLE WX_PUB_APP COMMENT '公众号管理';

/*==============================================================*/
/* Table: WX_SUBSCRIBE_                                         */
/*==============================================================*/
CREATE TABLE WX_SUBSCRIBE_
(
   ID_                  VARCHAR(64) NOT NULL COMMENT 'ID',
   PUB_ID_              VARCHAR(64) COMMENT '公众号ID',
   SUBSCRIBE_           VARCHAR(64) COMMENT 'SUBSCRIBE',
   OPEN_ID_             VARCHAR(64) COMMENT 'OPENID',
   NICK_NAME_           VARCHAR(64) COMMENT '昵称',
   LANGUAGE_            VARCHAR(64) COMMENT '语言',
   CITY_                VARCHAR(64) COMMENT '城市',
   PROVINCE_            VARCHAR(64) COMMENT '省份',
   COUNTRY_             VARCHAR(64) COMMENT '国家',
   UNIONID_             VARCHAR(64) COMMENT '绑定ID',
   SUBSCRIBE_TIME_      DATETIME COMMENT '最后的关注时间',
   REMARK_              VARCHAR(200) COMMENT '粉丝备注',
   GROUPID_             VARCHAR(64) COMMENT '用户分组ID',
   TAGID_LIST_          VARCHAR(512) COMMENT '标签ID列表',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   PRIMARY KEY (ID_)
);

ALTER TABLE WX_SUBSCRIBE_ COMMENT '微信关注者';

/*==============================================================*/
/* Table: WX_TAG_USER                                           */
/*==============================================================*/
CREATE TABLE WX_TAG_USER
(
   ID_                  VARCHAR(64) NOT NULL COMMENT '主键',
   PUB_ID_              VARCHAR(64) COMMENT '公众号Id',
   TAG_ID_              VARCHAR(64) COMMENT '标签名',
   USER_ID_             VARCHAR(64) COMMENT '用户ID',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   PRIMARY KEY (ID_)
);

ALTER TABLE WX_TAG_USER COMMENT '微信用户标签';

/*==============================================================*/
/* Table: WX_TICKET                                             */
/*==============================================================*/
CREATE TABLE WX_TICKET
(
   ID_                  VARCHAR(64) NOT NULL COMMENT 'ID_',
   PUB_ID_              VARCHAR(64) COMMENT '公众号ID',
   CARD_TYPE_           VARCHAR(64) COMMENT '卡券类型',
   LOGO_URL_            VARCHAR(128) COMMENT '卡券的商户logo',
   CODE_TYPE_           VARCHAR(16) COMMENT '码型',
   BRAND_NAME_          VARCHAR(36) COMMENT '商户名字',
   TITLE_               VARCHAR(27) COMMENT '卡券名',
   COLOR_               VARCHAR(16) COMMENT '券颜色',
   NOTICE_              VARCHAR(64) COMMENT '卡券使用提醒',
   DESCRIPTION_         VARCHAR(3072) COMMENT '卡券使用说明',
   SKU_                 TEXT COMMENT '商品信息',
   DATE_INFO            TEXT COMMENT '使用日期',
   BASE_INFO_           TEXT COMMENT '基础非必须信息',
   ADVANCED_INFO_       TEXT COMMENT '高级非必填信息',
   SPECIAL_CONFIG_      TEXT COMMENT '专用配置',
   CHECKED_             VARCHAR(64) COMMENT '审核是否通过',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   PRIMARY KEY (ID_)
);

ALTER TABLE WX_TICKET COMMENT '微信卡卷';

/*==============================================================*/
/* Table: WX_WEB_GRANT                                          */
/*==============================================================*/
CREATE TABLE WX_WEB_GRANT
(
   ID_                  VARCHAR(64) NOT NULL COMMENT '主键',
   PUB_ID_              VARCHAR(64) COMMENT '公众号ID',
   URL_                 VARCHAR(300) COMMENT '链接',
   TRANSFORM_URL_       VARCHAR(300) COMMENT '转换后的URL',
   CONFIG_              TEXT COMMENT '配置信息',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   PRIMARY KEY (ID_)
);

ALTER TABLE WX_WEB_GRANT COMMENT '微信网页授权';

/*==============================================================*/
/* Table: W_TOPCONTACTS                                         */
/*==============================================================*/
CREATE TABLE W_TOPCONTACTS
(
   ID_                  VARCHAR(64) NOT NULL COMMENT '主键',
   REF_ID_              VARCHAR(64) COMMENT '外键',
   F_LXRFL              VARCHAR(50) COMMENT '联系人分类ID',
   F_LXRFL_NAME         VARCHAR(50) COMMENT '联系人分类',
   F_LXR                VARCHAR(50) COMMENT '联系人ID',
   F_LXR_NAME           VARCHAR(50) COMMENT '联系人',
   INST_ID_             VARCHAR(64) COMMENT '流程实例ID',
   INST_STATUS_         VARCHAR(20) COMMENT '状态',
   CREATE_USER_ID_      CHAR(10) COMMENT '用户ID',
   CREATE_GROUP_ID_     CHAR(10) COMMENT '组ID',
   TENANT_ID_           VARCHAR(64) COMMENT '机构ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (ID_)
);

ALTER TABLE W_TOPCONTACTS COMMENT '常用联系人';

/*==============================================================*/
/* Table: HR_DUTY                                               */
/*==============================================================*/
CREATE TABLE HR_DUTY
(
   DUTY_ID_             VARCHAR(64) NOT NULL COMMENT '排班ID',
   DUTY_NAME_           VARCHAR(50) NOT NULL COMMENT '排班名称',
   SYSTEM_ID_           VARCHAR(64) COMMENT '班制编号',
   START_TIME_          DATETIME NOT NULL COMMENT '开始时间',
   END_TIME_            DATETIME NOT NULL COMMENT '结束时间',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构Id',
   USER_ID_             TEXT COMMENT '使用者ID',
   GROUP_ID_            TEXT COMMENT '用户组ID',
   USER_NAME_           TEXT COMMENT '使用者名字',
   GROUP_NAME_          TEXT COMMENT '用户组名字',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (DUTY_ID_)
);

ALTER TABLE HR_DUTY COMMENT '排班';

/*==============================================================*/
/* Table: HR_DUTY_REGISTER                                      */
/*==============================================================*/
CREATE TABLE HR_DUTY_REGISTER
(
   REGISTER_ID_         VARCHAR(64) NOT NULL COMMENT '登记ID',
   REGISTER_TIME_       DATETIME NOT NULL COMMENT '登记时间',
   REG_FLAG_            SMALLINT NOT NULL COMMENT '登记标识
            1=正常登记（上班，下班）
            2＝迟到
            3=早退
            4＝休息
            5＝旷工
            6=放假
            
            ',
   REG_MINS_            INT NOT NULL COMMENT '迟到或早退分钟
            正常上班时为0',
   REASON_              VARCHAR(128) COMMENT '迟到原因',
   DAYOFWEEK_           INT NOT NULL COMMENT '周几
            1=星期日
            ..
            7=日期六',
   IN_OFF_FLAG_         VARCHAR(8) NOT NULL COMMENT '上下班标识
            1=签到
            2=签退',
   USER_NAME_           VARCHAR(64) COMMENT '用户名',
   SYSTEM_ID_           VARCHAR(64) COMMENT '班制ID',
   SECTION_ID_          VARCHAR(64) COMMENT '班次ID',
   LONGITUDE_           NUMERIC(11,3) COMMENT '经度',
   LATITUDE_            NUMERIC(11,3) COMMENT '维度',
   ADDRESSES_           VARCHAR(64) COMMENT '地址详情',
   SIGNREMARK_          VARCHAR(300) COMMENT '签到备注',
   DISTANCE_            INT COMMENT '签到增加距离',
   DATE_                DATETIME,
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构Id',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (REGISTER_ID_)
);

ALTER TABLE HR_DUTY_REGISTER COMMENT '考勤登记记录';

/*==============================================================*/
/* Table: HR_DUTY_SECTION                                       */
/*==============================================================*/
CREATE TABLE HR_DUTY_SECTION
(
   SECTION_ID_          VARCHAR(64) NOT NULL COMMENT '班次编号',
   SECTION_NAME_        VARCHAR(16) NOT NULL COMMENT '班次名称',
   SECTION_SHORT_NAME_  VARCHAR(4) NOT NULL COMMENT '班次简称',
   START_SIGN_IN_       INT COMMENT '开始签到',
   DUTY_START_TIME_     VARCHAR(20) COMMENT '上班时间',
   END_SIGN_IN_         INT COMMENT '签到结束时间',
   EARLY_OFF_TIME_      INT COMMENT '早退计时',
   DUTY_END_TIME_       VARCHAR(20) COMMENT '下班时间',
   SIGN_OUT_TIME_       INT COMMENT '签退结束',
   IS_TWO_DAY_          VARCHAR(8) COMMENT '是否跨日',
   GROUP_LIST_          TEXT COMMENT '组合班次列表',
   IS_GROUP_            VARCHAR(8) NOT NULL COMMENT '是否组合班次',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构Id',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (SECTION_ID_)
);

ALTER TABLE HR_DUTY_SECTION COMMENT '班次
';

/*==============================================================*/
/* Table: HR_DUTY_SYSTEM                                        */
/*==============================================================*/
CREATE TABLE HR_DUTY_SYSTEM
(
   SYSTEM_ID_           VARCHAR(64) NOT NULL COMMENT '班制编号',
   NAME_                VARCHAR(100) NOT NULL COMMENT '名称',
   IS_DEFAULT           VARCHAR(8) NOT NULL COMMENT '是否缺省
            1＝缺省
            0＝非缺省',
   TYPE_                VARCHAR(20) COMMENT '分类
            一周=WEEKLY
            周期性=PERIODIC',
   WORK_SECTION_        VARCHAR(64) COMMENT '作息班次',
   WK_REST_SECTION_     VARCHAR(64) COMMENT '休息日加班班次',
   REST_SECTION_        VARCHAR(100) COMMENT '休息日',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构Id',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (SYSTEM_ID_)
);

ALTER TABLE HR_DUTY_SYSTEM COMMENT '班制
';

/*==============================================================*/
/* Table: HR_ERRANDS_REGISTER                                   */
/*==============================================================*/
CREATE TABLE HR_ERRANDS_REGISTER
(
   ER_ID_               VARCHAR(64) NOT NULL COMMENT 'erID',
   REASON_              VARCHAR(500),
   START_TIME_          DATETIME NOT NULL COMMENT '开始日期',
   END_TIME_            DATETIME NOT NULL COMMENT '结束日期',
   FLAG_                SMALLINT COMMENT '标识
            0=加班
            1=请假
            2=外出',
   BPM_INST_ID_         VARCHAR(64) COMMENT '流程实例ID',
   TYPE_                VARCHAR(20) NOT NULL COMMENT '类型',
   STATUS_              VARCHAR(20),
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构Id',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (ER_ID_)
);

ALTER TABLE HR_ERRANDS_REGISTER COMMENT '请假、加班、外出登记';

/*==============================================================*/
/* Table: HR_HOLIDAY                                            */
/*==============================================================*/
CREATE TABLE HR_HOLIDAY
(
   HOLIDAY_ID_          VARCHAR(64) NOT NULL COMMENT '假期ID',
   NAME_                VARCHAR(32) NOT NULL COMMENT '假期名称',
   START_DAY_           DATETIME NOT NULL COMMENT '开始日期',
   END_DAY_             DATETIME NOT NULL COMMENT '结束日期',
   DESC_                VARCHAR(512) COMMENT '假期描述',
   SYSTEM_ID_           TEXT COMMENT '班次ID',
   USER_ID_             TEXT COMMENT '所属用户
            若为某员工的假期，则为员工自己的id',
   USER_NAME_           TEXT COMMENT '用户名',
   GROUP_ID_            TEXT COMMENT '所属用户组',
   GROUP_NAME_          TEXT COMMENT '用户组名',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构Id',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (HOLIDAY_ID_)
);

ALTER TABLE HR_HOLIDAY COMMENT '放假记录';

/*==============================================================*/
/* Table: HR_OVERTIME_EXT                                       */
/*==============================================================*/
CREATE TABLE HR_OVERTIME_EXT
(
   OT_ID_               VARCHAR(64) NOT NULL COMMENT '加班ID',
   ER_ID_               VARCHAR(64),
   TYPE_                VARCHAR(20) COMMENT '加班类型',
   TITLE_               VARCHAR(50) COMMENT '标题',
   DESC_                VARCHAR(500) COMMENT '备注',
   PAY_                 VARCHAR(20) COMMENT '结算',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构Id',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (OT_ID_)
);

ALTER TABLE HR_OVERTIME_EXT COMMENT '加班';

/*==============================================================*/
/* Table: HR_SECTION_CONFIG                                     */
/*==============================================================*/
CREATE TABLE HR_SECTION_CONFIG
(
   CONFIG_ID_           VARCHAR(64) NOT NULL COMMENT '参数标号',
   IN_START_TIME_       INT COMMENT '上班开始签到时间',
   IN_END_TIME_         INT COMMENT '上班结束签到时间',
   OUT_START_TIME_      INT COMMENT '下班开始签到时间',
   OUT_END_TIME_        INT COMMENT '下班结束签到时间',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构Id',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (CONFIG_ID_)
);

/*==============================================================*/
/* Table: HR_SYSTEM_SECTION                                     */
/*==============================================================*/
CREATE TABLE HR_SYSTEM_SECTION
(
   SYSTEM_SECTION_ID_   VARCHAR(64) NOT NULL COMMENT '主键',
   SECTION_ID_          VARCHAR(64) COMMENT '班次ID',
   SYSTEM_ID_           VARCHAR(64) COMMENT '班制ID',
   WORKDAY_             INT,
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构Id',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (SYSTEM_SECTION_ID_)
);

ALTER TABLE HR_SYSTEM_SECTION COMMENT '班制班次表';

/*==============================================================*/
/* Table: HR_TRANS_REST_EXT                                     */
/*==============================================================*/
CREATE TABLE HR_TRANS_REST_EXT
(
   TR_ID_               VARCHAR(64) NOT NULL COMMENT '调休ID',
   ER_ID_               VARCHAR(64),
   TYPE_                VARCHAR(20) COMMENT '调休类型',
   WORK_                DATETIME COMMENT '上班时间',
   REST_                DATETIME COMMENT '休息时间',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构Id',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (TR_ID_)
);

ALTER TABLE HR_TRANS_REST_EXT COMMENT '调休扩展表';

/*==============================================================*/
/* Table: HR_VACATION_EXT                                       */
/*==============================================================*/
CREATE TABLE HR_VACATION_EXT
(
   VAC_ID_              VARCHAR(64) NOT NULL COMMENT '请假扩展ID',
   ER_ID_               VARCHAR(64),
   TYPE_                VARCHAR(20) COMMENT '请假类型',
   WORK_PLAN_           VARCHAR(500) COMMENT '工作安排',
   TENANT_ID_           VARCHAR(64) COMMENT '租用机构Id',
   CREATE_BY_           VARCHAR(64) COMMENT '创建人ID',
   CREATE_TIME_         DATETIME COMMENT '创建时间',
   UPDATE_BY_           VARCHAR(64) COMMENT '更新人ID',
   UPDATE_TIME_         DATETIME COMMENT '更新时间',
   PRIMARY KEY (VAC_ID_)
);

ALTER TABLE HR_VACATION_EXT COMMENT '请假扩展表';

CREATE TABLE `ins_news_column` (
  `ID_` varchar(64) NOT NULL default '' COMMENT '主键',
  `NAME_` varchar(50) default NULL COMMENT '栏目名称',
  `DESCRIPTION_` varchar(200) default NULL COMMENT '描述',
  `CREATE_BY_` varchar(64) default NULL COMMENT '用户ID',
  `TENANT_ID_` varchar(64) default NULL COMMENT '租户ID',
  `CREATE_TIME_` datetime default NULL COMMENT '创建时间',
  `UPDATE_TIME_` datetime default NULL COMMENT '更新时间',
  `UPDATE_BY_` varchar(64) default NULL,
  PRIMARY KEY  (`ID_`)
) ;

ALTER TABLE ins_news_column COMMENT '公告栏目管理';

ALTER TABLE BPM_AGENT_SOL ADD CONSTRAINT AGT_SOL_R_AGT FOREIGN KEY (AGENT_ID_)
      REFERENCES BPM_AGENT (AGENT_ID_) ON DELETE CASCADE ON UPDATE RESTRICT;

ALTER TABLE BPM_AGENT_SOL ADD CONSTRAINT BPM_AGENT_R_BSOL FOREIGN KEY (SOL_ID_)
      REFERENCES BPM_SOLUTION (SOL_ID_) ON DELETE CASCADE ON UPDATE RESTRICT;

ALTER TABLE BPM_CHECK_FILE ADD CONSTRAINT FK_CKFILE_R_NODE_JUMP FOREIGN KEY (JUMP_ID_)
      REFERENCES BPM_NODE_JUMP (JUMP_ID_) ON DELETE CASCADE ON UPDATE RESTRICT;

ALTER TABLE BPM_DEF ADD CONSTRAINT BPM_DEF_R_SYSTREE FOREIGN KEY (TREE_ID_)
      REFERENCES SYS_TREE (TREE_ID_) ON DELETE SET NULL ON UPDATE RESTRICT;

ALTER TABLE BPM_INST ADD CONSTRAINT BPM_INST_R_DEF FOREIGN KEY (DEF_ID_)
      REFERENCES BPM_DEF (DEF_ID_) ON DELETE CASCADE ON UPDATE RESTRICT;

ALTER TABLE BPM_INST_ATTACH ADD CONSTRAINT FK_BPM_INST_ATT_R_BPM_INST FOREIGN KEY (INST_ID_)
      REFERENCES BPM_INST (INST_ID_) ON DELETE CASCADE ON UPDATE RESTRICT;

ALTER TABLE BPM_INST_CP ADD CONSTRAINT FK_INST_CP_R_INSTCC FOREIGN KEY (CC_ID_)
      REFERENCES BPM_INST_CC (CC_ID_) ON DELETE CASCADE ON UPDATE RESTRICT;

ALTER TABLE BPM_INST_CTL ADD CONSTRAINT FK_FK_BPM_INSTCL_R_BPM_INST FOREIGN KEY (BPM_INST_ID_)
      REFERENCES BPM_INST (INST_ID_) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE BPM_INST_READ ADD CONSTRAINT FK_BPM_INS_RD_R_BPM_INST FOREIGN KEY (INST_ID_)
      REFERENCES BPM_INST (INST_ID_) ON DELETE CASCADE ON UPDATE RESTRICT;

ALTER TABLE BPM_LIB ADD CONSTRAINT BM_LIB_R_SOLUT FOREIGN KEY (SOL_ID_)
      REFERENCES BPM_SOLUTION (SOL_ID_) ON DELETE CASCADE ON UPDATE RESTRICT;

ALTER TABLE BPM_LIB ADD CONSTRAINT BPM_LIB_R_SYSTREE FOREIGN KEY (TREE_ID_)
      REFERENCES SYS_TREE (TREE_ID_) ON DELETE SET NULL ON UPDATE RESTRICT;

ALTER TABLE BPM_LIB_CMT ADD CONSTRAINT BPM_LIBCMT_R_LIB FOREIGN KEY (LIB_ID_)
      REFERENCES BPM_LIB (LIB_ID_) ON DELETE CASCADE ON UPDATE RESTRICT;

ALTER TABLE BPM_NODE_SET ADD CONSTRAINT NODE_SET_R_BPMSOL FOREIGN KEY (SOL_ID_)
      REFERENCES BPM_SOLUTION (SOL_ID_) ON DELETE CASCADE ON UPDATE RESTRICT;

ALTER TABLE BPM_SOLUTION ADD CONSTRAINT BM_SON_R_SYS_TREE FOREIGN KEY (TREE_ID_)
      REFERENCES SYS_TREE (TREE_ID_) ON DELETE SET NULL ON UPDATE RESTRICT;

ALTER TABLE BPM_SOL_CTL ADD CONSTRAINT FK_BPM_SOL_CTL_R_BPM_SOL FOREIGN KEY (SOL_ID_)
      REFERENCES BPM_SOLUTION (SOL_ID_) ON DELETE CASCADE ON UPDATE RESTRICT;

ALTER TABLE BPM_SOL_FM ADD CONSTRAINT SOL_FM_R_BPM_SOL FOREIGN KEY (SOL_ID_)
      REFERENCES BPM_SOLUTION (SOL_ID_) ON DELETE CASCADE ON UPDATE RESTRICT;

ALTER TABLE BPM_SOL_FV ADD CONSTRAINT SOL_FV_R_BPM_SOL FOREIGN KEY (SOL_ID_)
      REFERENCES BPM_SOLUTION (SOL_ID_) ON DELETE CASCADE ON UPDATE RESTRICT;

ALTER TABLE BPM_SOL_USER ADD CONSTRAINT FK_BPM_SOL_USR_R_BPM_SOL FOREIGN KEY (SOL_ID_)
      REFERENCES BPM_SOLUTION (SOL_ID_) ON DELETE CASCADE ON UPDATE RESTRICT;

ALTER TABLE BPM_SOL_VAR ADD CONSTRAINT BPM_SOL_VAR_R_BPMSOL FOREIGN KEY (SOL_ID_)
      REFERENCES BPM_SOLUTION (SOL_ID_) ON DELETE SET NULL ON UPDATE RESTRICT;

ALTER TABLE BPM_TEST_CASE ADD CONSTRAINT FK_TEST_CASE_R_TESTSOL FOREIGN KEY (TEST_SOL_ID_)
      REFERENCES BPM_TEST_SOL (TEST_SOL_ID_) ON DELETE CASCADE ON UPDATE RESTRICT;

ALTER TABLE CAL_CALENDAR ADD CONSTRAINT FK_REFERENCE_22 FOREIGN KEY (SETTING_ID_)
      REFERENCES CAL_SETTING (SETTING_ID_) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE CAL_GRANT ADD CONSTRAINT FK_REFERENCE_25 FOREIGN KEY (SETTING_ID_)
      REFERENCES CAL_SETTING (SETTING_ID_) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE HR_DUTY_INST ADD CONSTRAINT INST_R_HOLIDAY FOREIGN KEY (HOLIDAY_ID_)
      REFERENCES HR_HOLIDAY (HOLIDAY_ID_) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE HR_DUTY_INST_EXT ADD CONSTRAINT INSTEXT_R_INST FOREIGN KEY (DUTY_INST_ID_)
      REFERENCES HR_DUTY_INST (DUTY_INST_ID_) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE INF_DOC ADD CONSTRAINT FK_DT_R_DF FOREIGN KEY (FOLDER_ID_)
      REFERENCES INF_DOC_FOLDER (FOLDER_ID_) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE INF_DOC_FILE ADD CONSTRAINT FK_DF_F_DT FOREIGN KEY (DOC_ID_)
      REFERENCES INF_DOC (DOC_ID_) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE INF_DOC_FILE ADD CONSTRAINT FK_DOC_FILE_R_SYSFILE FOREIGN KEY (FILE_ID_)
      REFERENCES SYS_FILE (FILE_ID_) ON DELETE CASCADE ON UPDATE RESTRICT;

ALTER TABLE INF_DOC_RIGHT ADD CONSTRAINT FK_DP_R_DF FOREIGN KEY (FOLDER_ID_)
      REFERENCES INF_DOC_FOLDER (FOLDER_ID_) ON DELETE CASCADE ON UPDATE RESTRICT;

ALTER TABLE INF_DOC_RIGHT ADD CONSTRAINT FK_DP_R_DT FOREIGN KEY (DOC_ID_)
      REFERENCES INF_DOC (DOC_ID_) ON DELETE CASCADE ON UPDATE RESTRICT;

ALTER TABLE INF_INBOX ADD CONSTRAINT FK_INF_INBOX_R_INMSG FOREIGN KEY (MSG_ID_)
      REFERENCES INF_INNER_MSG (MSG_ID_) ON DELETE CASCADE ON UPDATE RESTRICT;

ALTER TABLE INF_MAIL ADD CONSTRAINT FK_OUT_MAIL_R_MAIL_CFG FOREIGN KEY (CONFIG_ID_)
      REFERENCES INF_MAIL_CONFIG (CONFIG_ID_) ON DELETE CASCADE ON UPDATE RESTRICT;

ALTER TABLE INF_MAIL ADD CONSTRAINT FK_INF_MAIL_R_FOLDER FOREIGN KEY (FOLDER_ID_)
      REFERENCES INF_MAIL_FOLDER (FOLDER_ID_) ON DELETE SET NULL ON UPDATE RESTRICT;

ALTER TABLE INF_MAIL_BOX ADD CONSTRAINT FK_MAILBOX_R_INMAIL FOREIGN KEY (MAIL_ID_)
      REFERENCES INF_INNER_MAIL (MAIL_ID_) ON DELETE CASCADE ON UPDATE RESTRICT;

ALTER TABLE INF_MAIL_BOX ADD CONSTRAINT FK_MAILBOX_R_MAILFOLDER FOREIGN KEY (FOLDER_ID_)
      REFERENCES INF_MAIL_FOLDER (FOLDER_ID_) ON DELETE SET NULL ON UPDATE RESTRICT;

ALTER TABLE INF_MAIL_FILE ADD CONSTRAINT FK_MAIL_FILE_R_OUT_MAIL FOREIGN KEY (MAIL_ID_)
      REFERENCES INF_MAIL (MAIL_ID_) ON DELETE CASCADE ON UPDATE RESTRICT;

ALTER TABLE INF_MAIL_FOLDER ADD CONSTRAINT FK_MAIL_FO_R_MAIL_CFG FOREIGN KEY (CONFIG_ID_)
      REFERENCES INF_MAIL_CONFIG (CONFIG_ID_) ON DELETE SET NULL ON UPDATE RESTRICT;

ALTER TABLE INS_COL_NEW ADD CONSTRAINT IS_CN_R_NEWS FOREIGN KEY (NEW_ID_)
      REFERENCES INS_NEWS (NEW_ID_) ON DELETE CASCADE ON UPDATE RESTRICT;

ALTER TABLE INS_NEWS_CM ADD CONSTRAINT FK_INS_NEWCM_R_INS_NEW FOREIGN KEY (NEW_ID_)
      REFERENCES INS_NEWS (NEW_ID_) ON DELETE CASCADE ON UPDATE RESTRICT;

ALTER TABLE KD_DOC ADD CONSTRAINT DOC_R_DOC_TMP FOREIGN KEY (TEMP_ID_)
      REFERENCES KD_DOC_TEMPLATE (TEMP_ID_) ON DELETE SET NULL ON UPDATE RESTRICT;

ALTER TABLE KD_DOC ADD CONSTRAINT KD_DOC_R_SYSTREE FOREIGN KEY (TREE_ID_)
      REFERENCES SYS_TREE (TREE_ID_) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE KD_DOC_CMMT ADD CONSTRAINT DOCMT_R_DOC FOREIGN KEY (DOC_ID_)
      REFERENCES KD_DOC (DOC_ID_) ON DELETE CASCADE ON UPDATE RESTRICT;

ALTER TABLE KD_DOC_CONTR ADD CONSTRAINT DOC_CONT_R_KD_DOC FOREIGN KEY (DOC_ID_)
      REFERENCES KD_DOC (DOC_ID_) ON DELETE CASCADE ON UPDATE RESTRICT;

ALTER TABLE KD_DOC_DIR ADD CONSTRAINT KD_DOCDIR_R_KDDOC FOREIGN KEY (DOC_ID_)
      REFERENCES KD_DOC (DOC_ID_) ON DELETE CASCADE ON UPDATE RESTRICT;

ALTER TABLE KD_DOC_FAV ADD CONSTRAINT DOC_FAV_R_KD_DOC FOREIGN KEY (DOC_ID_)
      REFERENCES KD_DOC (DOC_ID_) ON DELETE CASCADE ON UPDATE RESTRICT;

ALTER TABLE KD_DOC_FAV ADD CONSTRAINT KD_DOC_FAV_R_QUE FOREIGN KEY (QUE_ID_)
      REFERENCES KD_QUESTION (QUE_ID_) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE KD_DOC_HANDLE ADD CONSTRAINT FK_REFERENCE_79 FOREIGN KEY (DOC_ID_)
      REFERENCES OD_DOCUMENT (DOC_ID_) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE KD_DOC_HIS ADD CONSTRAINT DOCVER_R_KDDOC FOREIGN KEY (DOC_ID_)
      REFERENCES KD_DOC (DOC_ID_) ON DELETE CASCADE ON UPDATE RESTRICT;

ALTER TABLE KD_DOC_READ ADD CONSTRAINT DOC_RD_R_KD_DOC FOREIGN KEY (DOC_ID_)
      REFERENCES KD_DOC (DOC_ID_) ON DELETE CASCADE ON UPDATE RESTRICT;

ALTER TABLE KD_DOC_REM ADD CONSTRAINT DOC_REM_R_KD_DOC FOREIGN KEY (DOC_ID_)
      REFERENCES KD_DOC (DOC_ID_) ON DELETE CASCADE ON UPDATE RESTRICT;

ALTER TABLE KD_DOC_RIGHT ADD CONSTRAINT DOC_RHT_R_KD_DOC FOREIGN KEY (DOC_ID_)
      REFERENCES KD_DOC (DOC_ID_) ON DELETE CASCADE ON UPDATE RESTRICT;

ALTER TABLE KD_DOC_ROUND ADD CONSTRAINT DOC_ROUND_R_DOC FOREIGN KEY (DOC_ID_)
      REFERENCES KD_DOC (DOC_ID_) ON DELETE CASCADE ON UPDATE RESTRICT;

ALTER TABLE KD_DOC_TEMPLATE ADD CONSTRAINT DOC_TMPL_R_SYSTREE FOREIGN KEY (TREE_ID_)
      REFERENCES SYS_TREE (TREE_ID_) ON DELETE SET NULL ON UPDATE RESTRICT;

ALTER TABLE KD_QUESTION ADD CONSTRAINT QUES_R_SYSTREE FOREIGN KEY (TREE_ID_)
      REFERENCES SYS_TREE (TREE_ID_) ON DELETE SET NULL ON UPDATE RESTRICT;

ALTER TABLE KD_QUES_ANSWER ADD CONSTRAINT QUE_AW_R_QUES FOREIGN KEY (QUE_ID_)
      REFERENCES KD_QUESTION (QUE_ID_) ON DELETE CASCADE ON UPDATE RESTRICT;

ALTER TABLE OA_ADDR_CONT ADD CONSTRAINT FK_ADD_CNT_R_ADD_BK FOREIGN KEY (ADDR_ID_)
      REFERENCES OA_ADDR_BOOK (ADDR_ID_) ON DELETE CASCADE ON UPDATE RESTRICT;

ALTER TABLE OA_ADDR_GPB ADD CONSTRAINT FK_ADD_GPB_R_ADD_GRP FOREIGN KEY (GROUP_ID_)
      REFERENCES OA_ADDR_GRP (GROUP_ID_) ON DELETE CASCADE ON UPDATE RESTRICT;

ALTER TABLE OA_ADDR_GPB ADD CONSTRAINT FK_ADD_GPB_R_ADD_BK FOREIGN KEY (ADDR_ID_)
      REFERENCES OA_ADDR_BOOK (ADDR_ID_) ON DELETE CASCADE ON UPDATE RESTRICT;

ALTER TABLE OA_ASSETS ADD CONSTRAINT FK_ASSERTS_R_PRODUCTDEF FOREIGN KEY (PROD_DEF_ID_)
      REFERENCES OA_PRODUCT_DEF (PROD_DEF_ID_) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE OA_ASSETS_BID ADD CONSTRAINT ASSETS_BID_R_ASSETS FOREIGN KEY (ASS_ID_)
      REFERENCES OA_ASSETS (ASS_ID_) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE OA_ASS_PARAMETER ADD CONSTRAINT ASSPARA_R_PK FOREIGN KEY (KEY_ID_)
      REFERENCES OA_PRODUCT_DEF_PARA_KEY (KEY_ID_) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE OA_ASS_PARAMETER ADD CONSTRAINT ASSPARA_R_PV FOREIGN KEY (VALUE_ID_)
      REFERENCES OA_PRODUCT_DEF_PARA_VALUE (VALUE_ID_) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE OA_ASS_PARAMETER ADD CONSTRAINT ASS_PARA_R_ASS FOREIGN KEY (ASS_ID_)
      REFERENCES OA_ASSETS (ASS_ID_) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE OA_CAR_APP ADD CONSTRAINT CAR_APP_R_CAR FOREIGN KEY (CAR_ID_)
      REFERENCES OA_CAR (CAR_ID_) ON DELETE CASCADE ON UPDATE RESTRICT;

ALTER TABLE OA_COM_RIGHT ADD CONSTRAINT COM_RT_R_COM_BK FOREIGN KEY (COMBOOK_ID_)
      REFERENCES OA_COM_BOOK (COM_ID_) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE OA_MEETING ADD CONSTRAINT OA_MET_R_ROOM FOREIGN KEY (ROOM_ID_)
      REFERENCES OA_MEET_ROOM (ROOM_ID_) ON DELETE SET NULL ON UPDATE RESTRICT;

ALTER TABLE OA_MEET_ATT ADD CONSTRAINT MEET_ATT_R_MEET FOREIGN KEY (MEET_ID_)
      REFERENCES OA_MEETING (MEET_ID_) ON DELETE CASCADE ON UPDATE RESTRICT;

ALTER TABLE OA_PLAN_TASK ADD CONSTRAINT PLAN_R_PROJ FOREIGN KEY (PROJECT_ID_)
      REFERENCES OA_PROJECT (PROJECT_ID_) ON DELETE SET NULL ON UPDATE RESTRICT;

ALTER TABLE OA_PLAN_TASK ADD CONSTRAINT PLAN_R_REQ_MGR FOREIGN KEY (REQ_ID_)
      REFERENCES OA_REQ_MGR (REQ_ID_) ON DELETE SET NULL ON UPDATE RESTRICT;

ALTER TABLE OA_PRODUCT_DEF ADD CONSTRAINT PRODUCTDEF_R_SYSTREE FOREIGN KEY (TREE_ID_)
      REFERENCES SYS_TREE (TREE_ID_) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE OA_PRODUCT_DEF_PARA ADD CONSTRAINT PRODDEFPPARA_R_PRODDEF FOREIGN KEY (PROD_DEF_ID_)
      REFERENCES OA_PRODUCT_DEF (PROD_DEF_ID_) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE OA_PRODUCT_DEF_PARA ADD CONSTRAINT PRODUCTDEFPARA_R_PK FOREIGN KEY (KEY_ID_)
      REFERENCES OA_PRODUCT_DEF_PARA_KEY (KEY_ID_) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE OA_PRODUCT_DEF_PARA ADD CONSTRAINT PRODUCTDEFPARA_R_PV FOREIGN KEY (VALUE_ID_)
      REFERENCES OA_PRODUCT_DEF_PARA_VALUE (VALUE_ID_) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE OA_PRODUCT_DEF_PARA_KEY ADD CONSTRAINT PRODUCTDEF_PK_R_ST FOREIGN KEY (TREE_ID_)
      REFERENCES SYS_TREE (TREE_ID_) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE OA_PRODUCT_DEF_PARA_VALUE ADD CONSTRAINT FK_PRODUCTDEF_PV_R_PK FOREIGN KEY (KEY_ID_)
      REFERENCES OA_PRODUCT_DEF_PARA_KEY (KEY_ID_) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE OA_PRODUCT_DEF_PARA_VALUE ADD CONSTRAINT PRODUCTDEF_PV_R_ST FOREIGN KEY (TREE_ID_)
      REFERENCES SYS_TREE (TREE_ID_) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE OA_PROJECT ADD CONSTRAINT PROJ_R_SYSTREE FOREIGN KEY (TREE_ID_)
      REFERENCES SYS_TREE (TREE_ID_) ON DELETE SET NULL ON UPDATE RESTRICT;

ALTER TABLE OA_PRO_ATTEND ADD CONSTRAINT PRO_ATT_R_PROJ FOREIGN KEY (PROJECT_ID_)
      REFERENCES OA_PROJECT (PROJECT_ID_) ON DELETE CASCADE ON UPDATE RESTRICT;

ALTER TABLE OA_PRO_VERS ADD CONSTRAINT PRO_VER_R_PROJECT FOREIGN KEY (PROJECT_ID_)
      REFERENCES OA_PROJECT (PROJECT_ID_) ON DELETE CASCADE ON UPDATE RESTRICT;

ALTER TABLE OA_REQ_MGR ADD CONSTRAINT REQ_MGR_R_PROJECT FOREIGN KEY (PROJECT_ID_)
      REFERENCES OA_PROJECT (PROJECT_ID_) ON DELETE SET NULL ON UPDATE RESTRICT;

ALTER TABLE OA_WORK_LOG ADD CONSTRAINT WORKLOG_R_PLANTASK FOREIGN KEY (PLAN_ID_)
      REFERENCES OA_PLAN_TASK (PLAN_ID_) ON DELETE SET NULL ON UPDATE RESTRICT;

ALTER TABLE OD_DOCUMENT ADD CONSTRAINT FK_OD_DOC_R_SYSTREE FOREIGN KEY (TREE_ID_)
      REFERENCES SYS_TREE (TREE_ID_) ON DELETE SET NULL ON UPDATE RESTRICT;

ALTER TABLE OD_DOC_REC ADD CONSTRAINT FK_DOC_REC_R_ODDOC FOREIGN KEY (DOC_ID_)
      REFERENCES OD_DOCUMENT (DOC_ID_) ON DELETE CASCADE ON UPDATE RESTRICT;

ALTER TABLE OD_DOC_REMIND_ ADD CONSTRAINT FK_DOC_RM_R_ODDOC FOREIGN KEY (DOC_ID_)
      REFERENCES OD_DOCUMENT (DOC_ID_) ON DELETE CASCADE ON UPDATE RESTRICT;

ALTER TABLE OD_DOC_TEMPLATE ADD CONSTRAINT FK_DOC_TMP_R_SYSTREE FOREIGN KEY (TREE_ID_)
      REFERENCES SYS_TREE (TREE_ID_) ON DELETE SET NULL ON UPDATE RESTRICT;

ALTER TABLE OS_GROUP ADD CONSTRAINT FK_GP_R_DMN FOREIGN KEY (DIM_ID_)
      REFERENCES OS_DIMENSION (DIM_ID_) ON DELETE CASCADE ON UPDATE RESTRICT;

ALTER TABLE OS_GROUP_MENU ADD CONSTRAINT FK_FK_GRP_MN_R_OS_GRP FOREIGN KEY (GROUP_ID_)
      REFERENCES OS_GROUP (GROUP_ID_) ON DELETE CASCADE ON UPDATE RESTRICT;

ALTER TABLE OS_GROUP_MENU ADD CONSTRAINT FK_GRP_MN_R_SYS_MENU FOREIGN KEY (MENU_ID_)
      REFERENCES SYS_MENU (MENU_ID_) ON DELETE CASCADE ON UPDATE RESTRICT;

ALTER TABLE OS_GROUP_SYS ADD CONSTRAINT FK_GRP_SYS_R_GRP FOREIGN KEY (GROUP_ID_)
      REFERENCES OS_GROUP (GROUP_ID_) ON DELETE CASCADE ON UPDATE RESTRICT;

ALTER TABLE OS_RANK_TYPE ADD CONSTRAINT FK_ORK_TYPE_R_OSDIM FOREIGN KEY (DIM_ID_)
      REFERENCES OS_DIMENSION (DIM_ID_) ON DELETE CASCADE ON UPDATE RESTRICT;

ALTER TABLE OS_REL_INST ADD CONSTRAINT FK_OS_RIST_R_RTYPE FOREIGN KEY (REL_TYPE_ID_)
      REFERENCES OS_REL_TYPE (ID_) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE OS_REL_TYPE ADD CONSTRAINT FK_REL_PAR1_R_DIM FOREIGN KEY (DIM_ID1_)
      REFERENCES OS_DIMENSION (DIM_ID_) ON DELETE CASCADE ON UPDATE RESTRICT;

ALTER TABLE OS_REL_TYPE ADD CONSTRAINT FK_REFERENCE_29 FOREIGN KEY (DIM_ID2_)
      REFERENCES OS_DIMENSION (DIM_ID_) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE SYS_BO_LIST ADD CONSTRAINT FK_SYS_BO_LIST_R_LIS_SOL FOREIGN KEY (SOL_ID_)
      REFERENCES SYS_LIST_SOL (SOL_ID_) ON DELETE SET NULL ON UPDATE RESTRICT;

ALTER TABLE SYS_BUTTON ADD CONSTRAINT SYS_BTN_R_SYS_MOD FOREIGN KEY (MODULE_ID_)
      REFERENCES SYS_MODULE (MODULE_ID_) ON DELETE CASCADE ON UPDATE RESTRICT;

ALTER TABLE SYS_DIC ADD CONSTRAINT GL_TP_R_DIC FOREIGN KEY (TYPE_ID_)
      REFERENCES SYS_TREE (TREE_ID_) ON DELETE CASCADE ON UPDATE RESTRICT;

ALTER TABLE SYS_FIELD ADD CONSTRAINT FK_SYS_FIELD_R_SM FOREIGN KEY (MODULE_ID_)
      REFERENCES SYS_MODULE (MODULE_ID_) ON DELETE CASCADE ON UPDATE RESTRICT;

ALTER TABLE SYS_FILE ADD CONSTRAINT SYS_FILE_TREE FOREIGN KEY (TYPE_ID_)
      REFERENCES SYS_TREE (TREE_ID_) ON DELETE CASCADE ON UPDATE RESTRICT;

ALTER TABLE SYS_FORM_FIELD ADD CONSTRAINT FORM_FD_R_FORM_GP FOREIGN KEY (GROUP_ID_)
      REFERENCES SYS_FORM_GROUP (GROUP_ID_) ON DELETE CASCADE ON UPDATE RESTRICT;

ALTER TABLE SYS_FORM_FIELD ADD CONSTRAINT ORM_FD_R_FIELD FOREIGN KEY (FIELD_ID_)
      REFERENCES SYS_FIELD (FIELD_ID_) ON DELETE SET NULL ON UPDATE RESTRICT;

ALTER TABLE SYS_FORM_GROUP ADD CONSTRAINT FORM_GP_R_FORM_SMA FOREIGN KEY (FORM_SCHEMA_ID_)
      REFERENCES SYS_FORM_SCHEMA (FORM_SCHEMA_ID_) ON DELETE CASCADE ON UPDATE RESTRICT;

ALTER TABLE SYS_FORM_SCHEMA ADD CONSTRAINT FORM_SM_R_SYS_MOD FOREIGN KEY (MODULE_ID_)
      REFERENCES SYS_MODULE (MODULE_ID_) ON DELETE SET NULL ON UPDATE RESTRICT;

ALTER TABLE SYS_GRID_FIELD ADD CONSTRAINT SGF_R_SFD FOREIGN KEY (FIELD_ID_)
      REFERENCES SYS_FIELD (FIELD_ID_) ON DELETE CASCADE ON UPDATE RESTRICT;

ALTER TABLE SYS_GRID_FIELD ADD CONSTRAINT SGF_R_SGV FOREIGN KEY (GRID_VIEW_ID_)
      REFERENCES SYS_GRID_VIEW (GRID_VIEW_ID_) ON DELETE CASCADE ON UPDATE RESTRICT;

ALTER TABLE SYS_GRID_VIEW ADD CONSTRAINT GV_R_SM FOREIGN KEY (MODULE_ID_)
      REFERENCES SYS_MODULE (MODULE_ID_) ON DELETE SET NULL ON UPDATE RESTRICT;

ALTER TABLE SYS_LDAP_CN ADD CONSTRAINT FK_LDAP_CN_R_LDAP_OU FOREIGN KEY (SYS_LDAP_OU_ID_)
      REFERENCES SYS_LDAP_OU (SYS_LDAP_OU_ID_) ON DELETE SET NULL ON UPDATE RESTRICT;

ALTER TABLE SYS_LDAP_CN ADD CONSTRAINT FK_SYS_LDAP_CN_R_OSUSER FOREIGN KEY (USER_ID_)
      REFERENCES OS_USER (USER_ID_) ON DELETE SET NULL ON UPDATE RESTRICT;

ALTER TABLE SYS_LDAP_OU ADD CONSTRAINT LADP_OU_R_OS_GROUP FOREIGN KEY (GROUP_ID_)
      REFERENCES OS_GROUP (GROUP_ID_) ON DELETE SET NULL ON UPDATE RESTRICT;

ALTER TABLE SYS_MENU ADD CONSTRAINT SYS_MENU_R_SUBS FOREIGN KEY (SYS_ID_)
      REFERENCES SYS_SUBSYS (SYS_ID_) ON DELETE CASCADE ON UPDATE RESTRICT;

ALTER TABLE SYS_MODULE ADD CONSTRAINT SYS_R_MODULE FOREIGN KEY (SYS_ID_)
      REFERENCES SYS_SUBSYS (SYS_ID_) ON DELETE SET NULL ON UPDATE RESTRICT;

ALTER TABLE SYS_REPORT ADD CONSTRAINT REP_R_SYSTREE FOREIGN KEY (TREE_ID_)
      REFERENCES SYS_TREE (TREE_ID_) ON DELETE SET NULL ON UPDATE RESTRICT;

ALTER TABLE SYS_SEARCH_ITEM ADD CONSTRAINT SEARCH_ITM_R_SF FOREIGN KEY (FIELD_ID_)
      REFERENCES SYS_FIELD (FIELD_ID_) ON DELETE CASCADE ON UPDATE RESTRICT;

ALTER TABLE SYS_SEARCH_ITEM ADD CONSTRAINT SFIELD_R_SEARCH FOREIGN KEY (SEARCH_ID_)
      REFERENCES SYS_SEARCH (SEARCH_ID_) ON DELETE CASCADE ON UPDATE RESTRICT;

ALTER TABLE SYS_TEMPLATE ADD CONSTRAINT SYS_TMP_R_SYSTREE FOREIGN KEY (TREE_ID_)
      REFERENCES SYS_TREE (TREE_ID_) ON DELETE SET NULL ON UPDATE RESTRICT;

ALTER TABLE HR_DUTY ADD CONSTRAINT DUY_R_DS FOREIGN KEY (SYSTEM_ID_)
      REFERENCES HR_DUTY_SYSTEM (SYSTEM_ID_) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE HR_OVERTIME_EXT ADD CONSTRAINT OTEXT_R_ERRREG FOREIGN KEY (ER_ID_)
      REFERENCES HR_ERRANDS_REGISTER (ER_ID_) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE HR_SYSTEM_SECTION ADD CONSTRAINT SYSSEC_R_SEC FOREIGN KEY (SECTION_ID_)
      REFERENCES HR_DUTY_SECTION (SECTION_ID_) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE HR_SYSTEM_SECTION ADD CONSTRAINT SYSSEC_R_SYS FOREIGN KEY (SYSTEM_ID_)
      REFERENCES HR_DUTY_SYSTEM (SYSTEM_ID_) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE HR_TRANS_REST_EXT ADD CONSTRAINT TSEXT_R_ERRREG FOREIGN KEY (ER_ID_)
      REFERENCES HR_ERRANDS_REGISTER (ER_ID_) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE HR_VACATION_EXT ADD CONSTRAINT VAC_R_ERRREG FOREIGN KEY (ER_ID_)
      REFERENCES HR_ERRANDS_REGISTER (ER_ID_) ON DELETE RESTRICT ON UPDATE RESTRICT;

