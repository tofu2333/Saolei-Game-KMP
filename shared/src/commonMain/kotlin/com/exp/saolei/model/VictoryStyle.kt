package com.exp.saolei.model

enum class VictoryDialogStyle(
    val title: String,
    val subtitle: String,
    val description: String
) {
    CYBERPUNK(
        title = "赛博朋克 · 霓虹幻境",
        subtitle = "NEON PROTOCOL CLEARED",
        description = "全息霓虹扫描，数字流光与赛博极客风"
    ),
    RETRO_PIXEL(
        title = "复古像素 · 8-Bit街机",
        subtitle = "STAGE CLEAR!! HIGH SCORE",
        description = "经典街机像素金杯，怀旧红白机通关质感"
    ),
    ROYAL_GOLD(
        title = "荣耀加冕 · 极光流金",
        subtitle = "VICTORY & SUPREME GLORY",
        description = "尊贵鎏金桂冠，奢华光耀与王者勋章"
    ),
    MINIMAL_GLASS(
        title = "极简禅意 · 晨雾拟态",
        subtitle = "Zen Elegance & Harmony",
        description = "高级磨砂透光玻璃拟态，纯净而克制"
    ),
    CARNIVAL_PARTY(
        title = "盛大狂欢 · 缤纷礼花",
        subtitle = "CARNIVAL CELEBRATION!",
        description = "漫天七彩礼花喷射，缤纷狂欢派对盛典"
    ),
    COSMIC_GALAXY(
        title = "浩瀚星河 · 深空漫游",
        subtitle = "COSMIC MISSION ACCOMPLISHED",
        description = "深邃星云与星轨，宇航开拓者通关勋章"
    ),
    EMERALD_NATURE(
        title = "翡翠秘境 · 自然之语",
        subtitle = "Forest Whispers & Peace",
        description = "苍翠森林绿意，清新露珠与生机盎然"
    ),
    STEAMPUNK_CLOCKWORK(
        title = "蒸汽朋克 · 精密黄铜",
        subtitle = "CHRONO GEAR DEFUSED",
        description = "咬合精巧黄铜齿轮，维多利亚工业机械美学"
    ),
    SWEET_MACARON(
        title = "甜心派对 · 马卡龙波普",
        subtitle = "SWEET VICTORY POP!",
        description = "软萌棉花糖渐变，梦幻气泡与心动甜趣"
    ),
    SECRET_AGENT(
        title = "绝密特工 · 谍影重重",
        subtitle = "TOP SECRET // MISSION PASSED",
        description = "机密档案袋解密，鲜红绝密通关防伪印章"
    );

    companion object {
        fun random(): VictoryDialogStyle {
            return entries.random()
        }
    }
}
