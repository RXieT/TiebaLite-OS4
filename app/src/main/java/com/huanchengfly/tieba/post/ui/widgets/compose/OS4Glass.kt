package com.huanchengfly.tieba.post.ui.widgets.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.huanchengfly.tieba.post.core.ui.context.LocalUISettings
import top.yukonga.miuix.kmp.blur.Backdrop
import top.yukonga.miuix.kmp.glass.GlassColorBlendMode
import top.yukonga.miuix.kmp.glass.GlassColorLayer
import top.yukonga.miuix.kmp.glass.GlassDefaults
import top.yukonga.miuix.kmp.glass.GlassMaterial
import top.yukonga.miuix.kmp.glass.GlassShape
import top.yukonga.miuix.kmp.glass.glassPanel
import top.yukonga.miuix.kmp.theme.MiuixTheme
import kotlin.random.Random

/** Only the backdrop is filtered. Text, icons and touch targets remain outside the shader. */
@Composable
internal fun OS4BarSurface(
    backdrop: Backdrop?,
    rounded: Boolean = false,
    content: @Composable () -> Unit,
) {
    Box(Modifier.os4Glass(backdrop, rounded)) { content() }
}

@Composable
internal fun Modifier.os4Glass(backdrop: Backdrop?, rounded: Boolean = false): Modifier {
    val settings = LocalUISettings.current
    val surface = MiuixTheme.colorScheme.background
    val base = GlassDefaults.Style
    val style = base.copy(
        blend = base.blend.copy(saturation = settings.glassSaturation),
        background = base.background.copy(saturation = settings.glassSaturation),
        refract = base.refract.copy(ior = 1f + settings.glassRefraction),
    )
    return this.glassPanel(
            backdrop = backdrop,
            shape = GlassShape(if (rounded) 28.dp else 0.dp),
            style = style,
            material = GlassMaterial(
                blurRadius = settings.glassBlurRadius.dp,
                first = GlassColorLayer(
                    color = surface.copy(alpha = settings.glassTintAlpha),
                    mode = GlassColorBlendMode.SrcOver,
                ),
            ),
            shadow = null,
            shading = settings.glassRefraction > 0f,
            fallback = Modifier.background(surface),
        ).drawWithCache {
            // Stable screen-space grain: never animate random values or add noise to foreground.
            val random = Random(4)
            val count = (size.width * size.height / 16f).toInt().coerceIn(0, 12000)
            val points = List(count) { Offset(random.nextFloat() * size.width, random.nextFloat() * size.height) }
            onDrawBehind {
                if (settings.glassNoise > 0f) {
                    points.forEachIndexed { index, point ->
                        drawCircle(
                            color = (if (index % 2 == 0) Color.White else Color.Black)
                                .copy(alpha = settings.glassNoise),
                            radius = 0.6f,
                            center = point,
                        )
                    }
                }
            }
        }
}
