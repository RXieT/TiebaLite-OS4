package com.huanchengfly.tieba.post.core.data.model.settings

enum class DarkPreference {
    FOLLOW_SYSTEM, ALWAYS, DISABLED
}

/**
 * 底部导航栏标签显示模式
 *
 * @since 4.0.0-beta.4.3
 * */
enum class NavigationLabel {
    ALWAYS, SELECTED, NONE
}

/**
 * User UI Settings
 *
 * @param appIcon 应用图标
 * @param appIconThemed 应用图标使用动态取色
 * @param bottomNavFloating 主页底部导航栏悬浮模式
 * @param bottomNavLabel 主页底部导航栏标签显示模式
 * @param darkAmoled 纯黑背景颜色
 * @param darkPreference 夜间模式偏好
 * @param darkenImage 夜间模式压暗缩略图
 * @param hideExplore 隐藏主页「动态」入口
 * @param reduceEffect 减弱模糊效果
 * @param reduceMotion 减弱动态效果
 * @param setupFinished 设置向导已完成
 * @param homeForumList 吧列表单列显示
 * @param showHistoryInHome 首页显示最近逛的吧
 * */
data class UISettings(
    val appIcon: LauncherIcons = LauncherIcons.NEW_ICON,
    val appIconThemed: Boolean = false,
    val bottomNavFloating: Boolean = false,
    val bottomNavLabel: NavigationLabel = NavigationLabel.ALWAYS,
    val darkAmoled: Boolean = false,
    val darkPreference: DarkPreference = DarkPreference.FOLLOW_SYSTEM,
    val darkenImage: Boolean = true,
    val hideExplore: Boolean = false,
    val reduceEffect: Boolean = false,
    val reduceMotion: Boolean = false,
    val setupFinished: Boolean = false,
    val homeForumList: Boolean = false,
    val showHistoryInHome: Boolean = true,
    // reduceEffect takes precedence over these glass effect settings.
    val glassEnabled: Boolean = true,
    val glassBlurRadius: Float = 28f, // 0..80 dp
    val glassTintAlpha: Float = 0.35f, // 0..1
    val glassNoise: Float = 0.05f, // 0..0.3
    val glassRefraction: Float = 0.5f, // 0..1
    val glassSaturation: Float = 1.2f, // 0..2
)