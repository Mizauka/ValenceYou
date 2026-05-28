package com.valenceyou

import androidx.compose.ui.graphics.Color

/**
 * PhenomenologicalAnchor: 32个现象学锚点
 *
 * 结构:
 * - Activation/Overdrive (8个): 能量过剩状态
 * - Numbness/Dissociation (8个): 解离麻木状态
 * - Attachment/Validation (8个): 依恋寻求状态
 * - Collapse/Depression-like (8个): 崩溃耗竭状态
 *
 * 不显示分类名,只显示体验句子.
 */
enum class PhenomenologicalAnchor(
    val experience: String,
    val category: AnchorCategory,
    val color: Color,
    val bodySignals: List<String>,
    val riskSigns: List<String>,
    val harmReduction: List<String>
) {
    // ===== Activation / Overdrive =====
    CANT_STOP(
        experience = "我停不下来",
        category = AnchorCategory.ACTIVATION,
        color = Color(0xFFFFCC80),
        bodySignals = listOf(
            "心跳加速,即使坐着也像在跑",
            "肩膀紧绷,牙关咬紧",
            "胃部紧缩或恶心",
            "手指颤抖或坐立不安"
        ),
        riskSigns = listOf(
            "连续48小时无法停止活动",
            "出现幻听或思维奔逸",
            "自伤冲动作为'减速'手段"
        ),
        harmReduction = listOf(
            "冷水冲手腕: 激活潜水反射,强制降速",
            "重毯子压身: 提供边界感,减少漂浮感",
            "写下来: 把循环思维外化,给大脑减负"
        )
    ),

    NEED_CONFIRM(
        experience = "我一直想确认什么",
        category = AnchorCategory.ACTIVATION,
        color = Color(0xFFFFB74D),
        bodySignals = listOf(
            "反复检查同一处身体感受",
            "呼吸浅而快",
            "眼睛干涩但无法停止注视",
            "手指无意识敲击"
        ),
        riskSigns = listOf(
            "检查行为影响正常生活",
            "出现强迫性计数或排序",
            "无法在没有确认的情况下行动"
        ),
        harmReduction = listOf(
            "设定'确认次数上限': 最多3次",
            "转移注意力到触觉: 摸墙壁,感受纹理",
            "延迟确认: 等10分钟再做"
        )
    ),

    INCREASINGLY_IMPULSIVE(
        experience = "我越来越冲动",
        category = AnchorCategory.ACTIVATION,
        color = Color(0xFFFFA726),
        bodySignals = listOf(
            "身体前倾,准备行动",
            "说话速度加快",
            "手心出汗但感觉热",
            "肌肉紧张像要弹跳"
        ),
        riskSigns = listOf(
            "冲动行为后果越来越严重",
            "事后后悔但无法停止",
            "开始伤害自己或他人"
        ),
        harmReduction = listOf(
            "'暂停盒': 把冲动写下来,放进盒子,明天再看",
            "物理隔离: 离开当前环境",
            "找人陪伴: 即使不说话,有人在场也能抑制冲动"
        )
    ),

    MIND_WONT_STOP(
        experience = "我脑子停不下来",
        category = AnchorCategory.ACTIVATION,
        color = Color(0xFFFF9800),
        bodySignals = listOf(
            "太阳穴跳动",
            "眼睛疲劳但大脑活跃",
            "颈部僵硬",
            "睡眠时身体还在动"
        ),
        riskSigns = listOf(
            "思维速度导致无法入睡超过72小时",
            "出现妄想或偏执",
            "身体开始出现震颤"
        ),
        harmReduction = listOf(
            "'思维倾倒': 不设限制地写,直到写不动",
            "白噪音: 统一频率帮助大脑同步",
            "冷水泡脚: 把能量从头部引向脚部"
        )
    ),

    CONSTANT_REFRESH(
        experience = "我一直在刷新东西",
        category = AnchorCategory.ACTIVATION,
        color = Color(0xFFFB8C00),
        bodySignals = listOf(
            "拇指肌肉酸痛",
            "眼睛快速移动",
            "颈部前倾",
            "呼吸变浅"
        ),
        riskSigns = listOf(
            "刷新行为占据大部分时间",
            "没有新信息时感到恐慌",
            "出现幻听提示音"
        ),
        harmReduction = listOf(
            "'手机监狱': 物理隔离手机30分钟",
            "替代性触觉刺激: 捏压力球",
            "设定'刷新间隔': 每30分钟一次"
        )
    ),

    CANT_SLEEP(
        experience = "我睡不着",
        category = AnchorCategory.ACTIVATION,
        color = Color(0xFFF57C00),
        bodySignals = listOf(
            "眼皮沉重但大脑活跃",
            "腿部不适,想移动",
            "体温波动(忽冷忽热)",
            "心跳在安静时变得明显"
        ),
        riskSigns = listOf(
            "连续72小时无法入睡",
            "出现偏执或被害妄想",
            "幻觉(通常从听觉开始)"
        ),
        harmReduction = listOf(
            "不要强迫入睡: 降低床=压力的条件反射",
            "起床做单调的事: 折袜子,数瓷砖",
            "降低核心体温: 热水澡后自然降温"
        )
    ),

    NEED_MORE_STIMULATION(
        experience = "我越来越需要刺激",
        category = AnchorCategory.ACTIVATION,
        color = Color(0xFFEF6C00),
        bodySignals = listOf(
            "皮肤感觉迟钝",
            "需要更强烈的触感",
            "声音需要更大",
            "食物味道变淡了"
        ),
        riskSigns = listOf(
            "寻求危险的刺激",
            "剂量递增",
            "出现自伤行为寻求感觉"
        ),
        harmReduction = listOf(
            "'感觉日记': 记录每天最强烈的5个感觉",
            "冷水/热水交替: 安全的感觉唤醒",
            "振动工具: 按摩器提供强烈但安全的刺激"
        )
    ),

    LOSING_CONTROL(
        experience = "我开始控制不住加量",
        category = AnchorCategory.ACTIVATION,
        color = Color(0xFFE65100),
        bodySignals = listOf(
            "手在准备剂量时颤抖",
            "心跳在用药前加速",
            "出汗增多",
            "胃部空虚感"
        ),
        riskSigns = listOf(
            "剂量超过安全范围",
            "无法按计划停止",
            "出现戒断症状"
        ),
        harmReduction = listOf(
            "'剂量伙伴': 让别人保管和分配",
            "记录每次剂量和时间",
            "设定'最低有效剂量'并坚持"
        )
    ),

    // ===== Numbness / Dissociation =====
    CANT_FEEL_SELF(
        experience = "我感觉不到自己",
        category = AnchorCategory.NUMBNESS,
        color = Color(0xFF90CAF9),
        bodySignals = listOf(
            "皮肤感觉像橡胶或棉花",
            "时间感扭曲(几分钟像几小时)",
            "声音听起来很远",
            "无法识别自己的情绪"
        ),
        riskSigns = listOf(
            "完全无法感知疼痛或温度",
            "出现人格解体(看自己像陌生人)",
            "现实解体(世界像假的)"
        ),
        harmReduction = listOf(
            "5-4-3-2-1 grounding: 看到5样,听到4样,摸到3样,闻到2样,尝到1样",
            "冷水洗脸: 唤醒面部神经,拉回身体",
            "咀嚼冰块: 强烈感官刺激,打破麻木"
        )
    ),

    WORLD_UNREAL(
        experience = "世界变得不真实",
        category = AnchorCategory.NUMBNESS,
        color = Color(0xFF64B5F6),
        bodySignals = listOf(
            "光线看起来过于明亮或暗淡",
            "物体边缘模糊",
            "声音有回声",
            "地面感觉不平整"
        ),
        riskSigns = listOf(
            "现实解体持续超过24小时",
            "无法区分梦境和现实",
            "出现幻觉"
        ),
        harmReduction = listOf(
            "触摸粗糙表面: 砂纸,树皮",
            "闻强烈气味: 薄荷,柠檬",
            "赤脚走路: 感受地面纹理"
        )
    ),

    BEHIND_GLASS(
        experience = "我像隔着玻璃",
        category = AnchorCategory.NUMBNESS,
        color = Color(0xFF42A5F5),
        bodySignals = listOf(
            "触摸物体感觉隔着一层",
            "拥抱感觉不到温度",
            "说话声音听起来被闷住",
            "眼泪流出来但没有感觉"
        ),
        riskSigns = listOf(
            "玻璃感持续超过一周",
            "开始享受这种隔离感",
            "拒绝身体接触"
        ),
        harmReduction = listOf(
            "打破玻璃: 用力拍手,制造突然的声音",
            "温度冲击: 冰火交替",
            "身体扫描: 从脚趾到头顶,逐个部位感受"
        )
    ),

    NOT_LIKE_MYSELF(
        experience = "我开始不像自己",
        category = AnchorCategory.NUMBNESS,
        color = Color(0xFF2196F3),
        bodySignals = listOf(
            "动作变得机械",
            "说话用词不像平时的自己",
            "对熟悉的事物感到陌生",
            "身体姿势改变"
        ),
        riskSigns = listOf(
            "完全否认过去的自己",
            "出现多重人格特征",
            "无法识别镜子中的自己"
        ),
        harmReduction = listOf(
            "看旧照片: 唤醒记忆",
            "听曾经喜欢的音乐",
            "做一件以前常做的事"
        )
    ),

    NO_EMOTIONS(
        experience = "我没有情绪了",
        category = AnchorCategory.NUMBNESS,
        color = Color(0xFF1E88E5),
        bodySignals = listOf(
            "面部肌肉松弛,没有表情",
            "对好消息/坏消息反应相同",
            "心跳平稳到异常",
            "呼吸非常浅"
        ),
        riskSigns = listOf(
            "情绪缺失导致无法做决策",
            "出现情感淡漠",
            "无法共情他人"
        ),
        harmReduction = listOf(
            "看悲伤电影: 安全的情绪触发",
            "回忆最快乐的时刻: 详细描述",
            "身体运动: 跳舞,跑步,唤醒身体记忆"
        )
    ),

    DONT_WANT_ANYTHING(
        experience = "我什么都不想做",
        category = AnchorCategory.NUMBNESS,
        color = Color(0xFF1976D2),
        bodySignals = listOf(
            "身体沉重,像灌了铅",
            "眼睛半闭,没有焦点",
            "肌肉无力",
            "对食物没有兴趣"
        ),
        riskSigns = listOf(
            "完全停止日常活动",
            "出现营养不良",
            "无法起床"
        ),
        harmReduction = listOf(
            "'最小行动': 只做一件事,比如刷牙",
            "改变环境: 去不同的房间",
            "接受'不想做'的状态,不强迫"
        )
    ),

    FLOATING(
        experience = "我像漂着",
        category = AnchorCategory.NUMBNESS,
        color = Color(0xFF1565C0),
        bodySignals = listOf(
            "平衡感变差",
            "走路像踩棉花",
            "头部轻飘飘",
            "手脚感觉分离"
        ),
        riskSigns = listOf(
            "漂浮感导致跌倒",
            "无法安全驾驶或操作工具",
            "出现眩晕呕吐"
        ),
        harmReduction = listOf(
            "锚定: 坐在椅子上,双脚平放,感受重量",
            "重力毯: 增加身体压力感",
            "靠墙站立: 感受背部支撑"
        )
    ),

    CANT_FEEL_BODY(
        experience = "我感觉不到身体",
        category = AnchorCategory.NUMBNESS,
        color = Color(0xFF0D47A1),
        bodySignals = listOf(
            "不知道手脚在哪里",
            "需要看才能确认身体位置",
            "疼痛感觉延迟",
            "温度感觉缺失"
        ),
        riskSigns = listOf(
            "身体感觉缺失导致受伤",
            "出现神经性症状",
            "无法感知内脏信号(饥饿,口渴)"
        ),
        harmReduction = listOf(
            "身体扫描冥想: 逐个部位感受",
            "按摩: 外力帮助感受身体",
            "温水浴: 全身温度唤醒"
        )
    ),

    // ===== Attachment / Validation =====
    DEPENDENT_ON_SOMEONE(
        experience = "我越来越依赖某个人",
        category = AnchorCategory.ATTACHMENT,
        color = Color(0xFFCE93D8),
        bodySignals = listOf(
            "想到那个人时胃部紧缩",
            "呼吸变浅,等待回复",
            "手指无意识刷新消息",
            "听到提示音时心跳加速"
        ),
        riskSigns = listOf(
            "完全无法独立做决定",
            "对方不回复时出现恐慌",
            "为了维持关系伤害自己"
        ),
        harmReduction = listOf(
            "'独立清单': 列出自己能做的事",
            "设定'不联系时段': 每天2小时",
            "建立其他支持关系"
        )
    ),

    AFRAID_OF_ABANDONMENT(
        experience = "我很怕被丢下",
        category = AnchorCategory.ATTACHMENT,
        color = Color(0xFFBA68C8),
        bodySignals = listOf(
            "胸口压迫感",
            "呼吸急促,像溺水",
            "手冷,出冷汗",
            "胃部下沉感"
        ),
        riskSigns = listOf(
            "被丢下恐惧导致控制行为",
            "威胁自伤阻止离开",
            "出现分离焦虑发作"
        ),
        harmReduction = listOf(
            "'安全锚': 准备一个随时能联系到的人",
            "写下'即使被丢下,我也能...'",
            "呼吸练习: 4-7-8呼吸法"
        )
    ),

    NEED_CONFIRMATION(
        experience = "我需要别人确认我",
        category = AnchorCategory.ATTACHMENT,
        color = Color(0xFFAB47BC),
        bodySignals = listOf(
            "发送消息后等待时身体僵硬",
            "收到肯定时瞬间放松",
            "频繁照镜子检查自己",
            "说话时刻意观察对方反应"
        ),
        riskSigns = listOf(
            "没有确认就无法行动",
            "确认需求越来越频繁",
            "开始操纵他人给予确认"
        ),
        harmReduction = listOf(
            "'自我确认': 每天对着镜子说3件做得好的事",
            "延迟确认: 等1小时再寻求",
            "记录'没有确认也成功'的时刻"
        )
    ),

    EXCESSIVE_CHECKING(
        experience = "我开始过度查看消息",
        category = AnchorCategory.ATTACHMENT,
        color = Color(0xFF9C27B0),
        bodySignals = listOf(
            "拇指酸痛",
            "眼睛干涩",
            "颈部前倾",
            "听到虚假提示音(幻听)"
        ),
        riskSigns = listOf(
            "查看行为影响睡眠",
            "出现焦虑发作",
            "无法专注其他事情"
        ),
        harmReduction = listOf(
            "'消息监狱': 设定查看时间,其他时间手机放另一个房间",
            "关闭提示音: 减少触发",
            "统计每天查看次数,逐步减少"
        )
    ),

    AFRAID_OF_LOSS(
        experience = "我很怕突然失去联系",
        category = AnchorCategory.ATTACHMENT,
        color = Color(0xFF8E24AA),
        bodySignals = listOf(
            "对方延迟回复时心跳加速",
            "开始想象最坏情况",
            "手抖,打字错误增多",
            "胃部紧缩,没有食欲"
        ),
        riskSigns = listOf(
            "恐惧导致跟踪行为",
            "出现强迫性确认",
            "为了维持联系做出危险决定"
        ),
        harmReduction = listOf(
            "'最坏情况计划': 如果真的失去联系,我会怎么做",
            "分散注意力: 列出5件不需要联系也能做的事",
            "接受不确定性: 练习'不知道也没关系'"
        )
    ),

    WAITING_FOR_REPLY(
        experience = "我一直在等待回复",
        category = AnchorCategory.ATTACHMENT,
        color = Color(0xFF7B1FA2),
        bodySignals = listOf(
            "身体朝向手机",
            "无法专注于其他事情",
            "时间感扭曲(几分钟像几小时)",
            "睡眠时手机放在枕边"
        ),
        riskSigns = listOf(
            "等待导致完全停止生活",
            "出现抑郁症状",
            "开始发送越来越多消息"
        ),
        harmReduction = listOf(
            "'等待仪式': 设定一个等待时做的固定活动",
            "物理隔离: 把手机放进抽屉",
            "设定'最后期限': 超过2小时不回复就去做别的事"
        )
    ),

    NO_ONE_UNDERSTANDS(
        experience = "我觉得没人真的理解我",
        category = AnchorCategory.ATTACHMENT,
        color = Color(0xFF6A1B9A),
        bodySignals = listOf(
            "说话时感觉声音被墙壁弹回",
            "胸口空洞感",
            "眼神回避",
            "身体蜷缩"
        ),
        riskSigns = listOf(
            "孤立感导致完全退缩",
            "出现自杀意念",
            "拒绝所有帮助"
        ),
        harmReduction = listOf(
            "'理解清单': 列出至少3个理解你的人",
            "写一封信给未来的自己",
            "加入社群: 找到有相似经历的人"
        )
    ),

    PEOPLE_PLEASING(
        experience = "我开始讨好所有人",
        category = AnchorCategory.ATTACHMENT,
        color = Color(0xFF4A148C),
        bodySignals = listOf(
            "说话时身体前倾",
            "面部肌肉紧张(保持微笑)",
            "呼吸浅,不敢深呼吸",
            "肩膀抬高,防御姿势"
        ),
        riskSigns = listOf(
            "讨好行为导致自我消失",
            "出现怨恨和愤怒爆发",
            "为了讨好做出违背原则的事"
        ),
        harmReduction = listOf(
            "'不讨好实验': 一天内拒绝3个请求",
            "写下'如果我不再讨好,我会...'",
            "练习说'我需要考虑一下'"
        )
    ),

    // ===== Collapse / Depression-like =====
    NO_STRENGTH(
        experience = "我没有力气继续了",
        category = AnchorCategory.COLLAPSE,
        color = Color(0xFFB0BEC5),
        bodySignals = listOf(
            "四肢沉重,像灌了铅",
            "呼吸浅,没有力气深呼吸",
            "眼皮沉重,一直想闭眼",
            "肌肉无力,握不住东西"
        ),
        riskSigns = listOf(
            "完全无法起床",
            "出现营养不良",
            "停止所有个人卫生"
        ),
        harmReduction = listOf(
            "'最小努力': 只做一件事,比如洗脸",
            "寻求帮助: 让别人帮你做",
            "接受'现在没有力气',不批评自己"
        )
    ),

    DONT_WANT_GO_OUT(
        experience = "我开始不想出门",
        category = AnchorCategory.COLLAPSE,
        color = Color(0xFF90A4AE),
        bodySignals = listOf(
            "想到出门时胃部紧缩",
            "身体向后缩,远离门",
            "呼吸加快,像缺氧",
            "手脚冰冷"
        ),
        riskSigns = listOf(
            "完全不出门超过一周",
            "出现社交恐惧",
            "拒绝所有邀请"
        ),
        harmReduction = listOf(
            "'微出门': 只走到门口,或者只去楼下",
            "带安全物: 一件让你安心的东西",
            "设定时间: 只出去10分钟"
        )
    ),

    NO_REAL_REST(
        experience = "我很久没真正休息了",
        category = AnchorCategory.COLLAPSE,
        color = Color(0xFF78909C),
        bodySignals = listOf(
            "睡眠浅,容易醒",
            "醒来仍然累",
            "白天打瞌睡但睡不着",
            "眼睛下方暗沉"
        ),
        riskSigns = listOf(
            "慢性疲劳持续超过一个月",
            "出现免疫系统问题",
            "无法恢复精力"
        ),
        harmReduction = listOf(
            "'休息许可': 告诉自己'我现在允许休息'",
            "创造休息环境: 黑暗,安静,舒适",
            "短暂休息: 即使5分钟也有帮助"
        )
    ),

    CANT_FINISH_ANYTHING(
        experience = "我什么都完成不了",
        category = AnchorCategory.COLLAPSE,
        color = Color(0xFF607D8B),
        bodySignals = listOf(
            "开始做事时心跳加速",
            "手抖,无法精细操作",
            "注意力分散,频繁切换",
            "胃部不适,想逃避"
        ),
        riskSigns = listOf(
            "完全停止尝试",
            "出现自我否定",
            "开始依赖他人完成基本任务"
        ),
        harmReduction = listOf(
            "'微完成': 只做一件事的一小部分",
            "降低标准: '完成'比'完美'重要",
            "计时: 只工作5分钟,然后休息"
        )
    ),

    HARD_TO_START(
        experience = "我越来越难开始事情",
        category = AnchorCategory.COLLAPSE,
        color = Color(0xFF546E7A),
        bodySignals = listOf(
            "想到开始时身体僵硬",
            "拖延时心跳加速",
            "开始后又立即停止",
            "肌肉紧张,准备逃避"
        ),
        riskSigns = listOf(
            "完全无法开始任何事情",
            "出现焦虑发作",
            "开始逃避所有责任"
        ),
        harmReduction = listOf(
            "'5秒法则': 数到5就立即行动",
            "减少选择: 只给一件事做",
            "身体先行: 先动身体,大脑会跟上"
        )
    ),

    TIME_STOPPED(
        experience = "我感觉时间停止了",
        category = AnchorCategory.COLLAPSE,
        color = Color(0xFF455A64),
        bodySignals = listOf(
            "时间感扭曲(几小时像几分钟)",
            "钟表看起来不动",
            "身体感觉凝固",
            "呼吸非常缓慢"
        ),
        riskSigns = listOf(
            "时间感丧失导致错过重要事件",
            "出现解离",
            "无法计划未来"
        ),
        harmReduction = listOf(
            "'时间锚': 每小时看一次钟表,记录时间",
            "设定闹钟: 强制感知时间流逝",
            "做有时间边界的事: 看一集电视剧"
        )
    ),

    STOP_CARING_SELF(
        experience = "我开始放弃照顾自己",
        category = AnchorCategory.COLLAPSE,
        color = Color(0xFF37474F),
        bodySignals = listOf(
            "身体异味",
            "衣服脏乱",
            "头发打结",
            "皮肤出现问题"
        ),
        riskSigns = listOf(
            "完全停止自我照顾",
            "出现健康问题",
            "拒绝医疗帮助"
        ),
        harmReduction = listOf(
            "'最小照顾': 只刷牙和洗脸",
            "让别人帮你: 接受帮助不是软弱",
            "设定'照顾闹钟': 提醒基本需求"
        )
    ),

    FEEL_CRUSHED(
        experience = "我像被压住了",
        category = AnchorCategory.COLLAPSE,
        color = Color(0xFF263238),
        bodySignals = listOf(
            "胸口压迫感,像有大石",
            "呼吸浅,无法深呼吸",
            "身体蜷缩,保护姿势",
            "肌肉紧张,无法放松"
        ),
        riskSigns = listOf(
            "压迫感导致无法行动",
            "出现惊恐发作",
            "开始自伤缓解压力"
        ),
        harmReduction = listOf(
            "'释放姿势': 伸展手臂,打开胸腔",
            "大声呼气: 发出声音,释放压力",
            "寻求帮助: 让别人知道你需要支持"
        )
    );

    companion object {
        fun byCategory(category: AnchorCategory): List<PhenomenologicalAnchor> {
            return entries.filter { it.category == category }
        }
    }
}

enum class AnchorCategory {
    ACTIVATION,   // 能量过剩
    NUMBNESS,     // 解离麻木
    ATTACHMENT,   // 依恋寻求
    COLLAPSE      // 崩溃耗竭
}
