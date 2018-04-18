var menuData = [
	{ "id": "1", iconCls: "fa fa-send-o", text: "敏捷开发", children: [
         { "id": "1_1", iconCls: "fa fa-desktop", text: "代码生成器" },
         { "id": "1_2", iconCls: "fa fa-search", text: "图标查看" ,url:"icon/index.htm"},
         { "id": "1_3", iconCls: "fa fa-send-o", text: "插件演示",url:"plugin/index.htm" },
         { "id": "1_4", iconCls: "fa fa-window-restore", text: "开发示例", children: [
                    { "id": "1_4_1", iconCls: "fa fa-assistive-listening-systems", text: "商机管理" ,url:"modules/chance.htm"},
                    { "id": "khgl", iconCls: "fa fa-vcard", text: "客户管理", url: "modules/crmorder.htm" },
                    { "id": "kpxx", iconCls: "fa fa-file-excel-o", text: "开票信息", url: "modules/customer.htm" },
                    { "id": "khdd", iconCls: "fa fa-modx", text: "客户订单", url: "modules/invoice.htm" }
            ]
         }
	    ]
	},
    { "id": "xtgl", iconCls: "fa fa-desktop", text: "系统管理", children: [
        { "id": "xzgl", iconCls: "fa fa-leaf", text: "行政管理",url:"SystemManagement/administrative/unselectedarea.htm" },
        { "id": "sjzd", iconCls: "fa fa-book", text: "数据字典", url: "SystemManagement/datadictionary/DbVersion.htm" },
        { "id": "djbm", iconCls: "fa fa-barcode", text: "单据编码", url: "SystemManagement/Documentscoding.htm" },
        { "id": "xtgn", iconCls: "fa fa-navicon", text: "系统功能" },
        { "id": "excel", iconCls: "fa fa-file-excel-o", text: "Excel配置", children: [
                  { "id": "drpz", iconCls: "fa fa-sign-out", text: "导入配置" },
                  { "id": "dcpz", iconCls: "fa fa-sign-out", text: "导出配置" }
            ]
        },
        { "id": "sjgl", iconCls: "fa fa-database", text: "数据管理", children: [
                { "id": "sjklj", iconCls: "fa fa-plug", text: "数据库连接" },
                { "id": "sjbgl", iconCls: "fa fa-table", text: "数据表管理" },
                { "id": "sjygl", iconCls: "fa fa-bullseye", text: "数据源管理" }
            ]
        },
        { "id": "xtrz", iconCls: "fa fa-warning", text: "系统日志" },
        { "id": "sjqxgl", iconCls: "fa fa-briefcase", text: "数据权限管理" }
    ]
    },
{ "id": "dwzz", iconCls: "fa fa-coffee",  text: "单位组织", children: [
         { "id": "gsgl", iconCls: "fa fa-sitemap", text: "公司管理" },
         { "id": "bmgl", iconCls: "fa fa-th-list", text: "部门管理" },
         { "id": "gwgl", iconCls: "fa fa-graduation-cap", text: "岗位管理" },
         { "id": "jsgl", iconCls: "fa fa-paw", text: "角色管理" },
         { "id": "yhgl", iconCls: "fa fa-user", text: "用户管理" }
    ]
},
{ "id": "bdzx", iconCls: "fa fa-table", text: "表单中心", children: [
        { "id": "zdybd", iconCls: "fa fa-puzzle-piece", text: "自定义表单" },
        { "id": "fbbdgn", iconCls: "fa fa-list-alt", text: "发布表单功能" },
        { "id": "bdfbsl", iconCls: "fa fa-list-alt", text: "表单发布实例", children: [
              { "id": "hyda", iconCls: "fa fa-address-card-o", text: "会员档案" },
              { "id": "ddgn", iconCls: "fa fa-address-book", text: "订单功能" },
              { "id": "qjgl", iconCls: "fa fa-user-circle", text: "请假管理" },
              { "id": "csbd", iconCls: "fa fa-bandcamp", text: "测试表单" }
            ]
        }
    ]
},
{ "id": "lczx", iconCls: "fa fa-share-alt", text: "流程中心", children: [
          { "id": "mbgl", iconCls: "fa fa-share-alt", text: "模板管理" },
          { "id": "wdrw", iconCls: "fa fa-file-word-o", text: "我的任务" },
          { "id": "gzwt", iconCls: "fa fa-coffee", text: "工作委托" },
          { "id": "lcjk", iconCls: "fa fa-eye", text: "流程监控" },
          { "id": "xtlcgl", iconCls: "fa fa-industry", text: "系统流程案例" }
    ]
},
{ "id": "bbzx", iconCls: "fa fa-area-chart", text: "报表中心", children: [
        { "id": "bbgl", iconCls: "fa fa-cogs", text: "报表管理" },
        { "id": "bbsl", iconCls: "fa fa-file-powerpoint-o", text: "报表实例", children: [
                { "id": "xstb", iconCls: "fa fa-area-chart", text: "销售图表" },
                { "id": "xslb", iconCls: "fa fa-area-chart", text: "销售列表" },
                { "id": "xshh", iconCls: "fa fa-area-chart", text: "销售混合" }
            ]
        },
        { "id": "bbxq", iconCls: "fa fa-wpforms", text: "报表模板", children: [
                { "id": "cgbb", iconCls: "fa fa-bar-chart", text: "采购报表" },
                { "id": "sxbb", iconCls: "fa fa-line-chart", text: "销售报表" },
                { "id": "ccbb", iconCls: "fa fa-area-chart", text: "仓存报表" },
                { "id": "szbb", iconCls: "fa fa-pie-chart", text: "收支报表" }
            ]
        }
    ]
},
{ "id": "ggxx", iconCls: "fa fa-globe", text: "公共信息", children: [
        { "id": "xwzx", iconCls: "fa-feed", text: "新闻中心" },
        { "id": "khxq1", iconCls: "fa-braille", text: "客户详情" },
        { "id": "khxq2", iconCls: "fa fa-braille", text: "客户详情" },
        { "id": "khxq3", iconCls: "fa fa-braille", text: "客户详情" },
        { "id": "tzgg", iconCls: "fa fa-volume-up", text: "通知公告" },
        { "id": "wjzl", iconCls: "fa fa-jsfiddle", text: "文件资料" },
        { "id": "rcgl", iconCls: "fa fa-calendar", text: "日程管理" },
        { "id": "yjzx", iconCls: "fa fa-send", text: "邮件中心" },
        { "id": "dzqz", iconCls: "fa fa-registered", text: "电子签章" }
    ]
},
{ "id": "ydgl", iconCls: "fa fa-android", text: "移动管理", children: [
        { "id": "wxgl", iconCls: "fa fa-weixin", text: "微信管理", children: [
                { "id": "qyhsz", iconCls: "fa fa-plug", text: "企业号设置" }
            ]
        }
    ]
},
{ "id": "ggxx", iconCls: "fa fa-globe", text: "公共信息", children: [
        { "id": "xwzx", iconCls: "fa-feed", text: "新闻中心" },
        { "id": "khxq1", iconCls: "fa-braille", text: "客户详情" },
        { "id": "khxq2", iconCls: "fa fa-braille", text: "客户详情" },
        { "id": "khxq3", iconCls: "fa fa-braille", text: "客户详情" },
        { "id": "tzgg", iconCls: "fa fa-volume-up", text: "通知公告" },
        { "id": "wjzl", iconCls: "fa fa-jsfiddle", text: "文件资料" },
        { "id": "rcgl", iconCls: "fa fa-calendar", text: "日程管理" },
        { "id": "yjzx", iconCls: "fa fa-send", text: "邮件中心" },
        { "id": "dzqz", iconCls: "fa fa-registered", text: "电子签章" }
    ]
},
{ "id": "ydgl", iconCls: "fa fa-android", text: "移动管理", children: [
        { "id": "wxgl", iconCls: "fa fa-weixin", text: "微信管理", children: [
                { "id": "qyhsz", iconCls: "fa fa-plug", text: "企业号设置" }
            ]
        }
    ]
},
{ "id": "ggxx", iconCls: "fa fa-globe", text: "公共信息", children: [
        { "id": "xwzx", iconCls: "fa-feed", text: "新闻中心" },
        { "id": "khxq1", iconCls: "fa-braille", text: "客户详情" },
        { "id": "khxq2", iconCls: "fa fa-braille", text: "客户详情" },
        { "id": "khxq3", iconCls: "fa fa-braille", text: "客户详情" },
        { "id": "tzgg", iconCls: "fa fa-volume-up", text: "通知公告" },
        { "id": "wjzl", iconCls: "fa fa-jsfiddle", text: "文件资料" },
        { "id": "rcgl", iconCls: "fa fa-calendar", text: "日程管理" },
        { "id": "yjzx", iconCls: "fa fa-send", text: "邮件中心" },
        { "id": "dzqz", iconCls: "fa fa-registered", text: "电子签章" }
    ]
},
{ "id": "ydgl", iconCls: "fa fa-android", text: "移动管理", children: [
        { "id": "wxgl", iconCls: "fa fa-weixin", text: "微信管理", children: [
                { "id": "qyhsz", iconCls: "fa fa-plug", text: "企业号设置" }
            ]
        }
    ]
},{ "id": "ggxx", iconCls: "fa fa-globe", text: "公共信息", children: [
        { "id": "xwzx", iconCls: "fa-feed", text: "新闻中心" },
        { "id": "khxq1", iconCls: "fa-braille", text: "客户详情" },
        { "id": "khxq2", iconCls: "fa fa-braille", text: "客户详情" },
        { "id": "khxq3", iconCls: "fa fa-braille", text: "客户详情" },
        { "id": "tzgg", iconCls: "fa fa-volume-up", text: "通知公告" },
        { "id": "wjzl", iconCls: "fa fa-jsfiddle", text: "文件资料" },
        { "id": "rcgl", iconCls: "fa fa-calendar", text: "日程管理" },
        { "id": "yjzx", iconCls: "fa fa-send", text: "邮件中心" },
        { "id": "dzqz", iconCls: "fa fa-registered", text: "电子签章" }
    ]
},
{ "id": "ydgl", iconCls: "fa fa-android", text: "移动管理", children: [
        { "id": "wxgl", iconCls: "fa fa-weixin", text: "微信管理", children: [
                { "id": "qyhsz", iconCls: "fa fa-plug", text: "企业号设置" }
            ]
        }
    ]
},{ "id": "ggxx", iconCls: "fa fa-globe", text: "公共信息", children: [
        { "id": "xwzx", iconCls: "fa-feed", text: "新闻中心" },
        { "id": "khxq1", iconCls: "fa-braille", text: "客户详情" },
        { "id": "khxq2", iconCls: "fa fa-braille", text: "客户详情" },
        { "id": "khxq3", iconCls: "fa fa-braille", text: "客户详情" },
        { "id": "tzgg", iconCls: "fa fa-volume-up", text: "通知公告" },
        { "id": "wjzl", iconCls: "fa fa-jsfiddle", text: "文件资料" },
        { "id": "rcgl", iconCls: "fa fa-calendar", text: "日程管理" },
        { "id": "yjzx", iconCls: "fa fa-send", text: "邮件中心" },
        { "id": "dzqz", iconCls: "fa fa-registered", text: "电子签章" }
    ]
},
{ "id": "ydgl", iconCls: "fa fa-android", text: "移动管理", children: [
        { "id": "wxgl", iconCls: "fa fa-weixin", text: "微信管理", children: [
                { "id": "qyhsz", iconCls: "fa fa-plug", text: "企业号设置" }
            ]
        }
    ]
},{ "id": "ggxx", iconCls: "fa fa-globe", text: "公共信息", children: [
        { "id": "xwzx", iconCls: "fa-feed", text: "新闻中心" },
        { "id": "khxq1", iconCls: "fa-braille", text: "客户详情" },
        { "id": "khxq2", iconCls: "fa fa-braille", text: "客户详情" },
        { "id": "khxq3", iconCls: "fa fa-braille", text: "客户详情" },
        { "id": "tzgg", iconCls: "fa fa-volume-up", text: "通知公告" },
        { "id": "wjzl", iconCls: "fa fa-jsfiddle", text: "文件资料" },
        { "id": "rcgl", iconCls: "fa fa-calendar", text: "日程管理" },
        { "id": "yjzx", iconCls: "fa fa-send", text: "邮件中心" },
        { "id": "dzqz", iconCls: "fa fa-registered", text: "电子签章" }
    ]
},
{ "id": "ydgl", iconCls: "fa fa-android", text: "移动管理", children: [
        { "id": "wxgl", iconCls: "fa fa-weixin", text: "微信管理", children: [
                { "id": "qyhsz", iconCls: "fa fa-plug", text: "企业号设置" }
            ]
        }
    ]
},{ "id": "ggxx", iconCls: "fa fa-globe", text: "公共信息", children: [
        { "id": "xwzx", iconCls: "fa-feed", text: "新闻中心" },
        { "id": "khxq1", iconCls: "fa-braille", text: "客户详情" },
        { "id": "khxq2", iconCls: "fa fa-braille", text: "客户详情" },
        { "id": "khxq3", iconCls: "fa fa-braille", text: "客户详情" },
        { "id": "tzgg", iconCls: "fa fa-volume-up", text: "通知公告" },
        { "id": "wjzl", iconCls: "fa fa-jsfiddle", text: "文件资料" },
        { "id": "rcgl", iconCls: "fa fa-calendar", text: "日程管理" },
        { "id": "yjzx", iconCls: "fa fa-send", text: "邮件中心" },
        { "id": "dzqz", iconCls: "fa fa-registered", text: "电子签章" }
    ]
},
{ "id": "ydgl", iconCls: "fa fa-android", text: "移动管理", children: [
        { "id": "wxgl", iconCls: "fa fa-weixin", text: "微信管理", children: [
                { "id": "qyhsz", iconCls: "fa fa-plug", text: "企业号设置" }
            ]
        }
    ]
}
]