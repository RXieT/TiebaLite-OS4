package com.huanchengfly.tieba.post.ui.widgets.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.huanchengfly.tieba.post.core.ui.context.LocalUISettings
import top.yukonga.miuix.kmp.blur.Backdrop
import top.yukonga.miuix.kmp.blur.isRuntimeShaderSupported
import top.yukonga.miuix.kmp.glass.GlassColorBlendMode
import top.yukonga.miuix.kmp.glass.GlassColorLayer
import top.yukonga.miuix.kmp.glass.GlassDefaults
import top.yukonga.miuix.kmp.glass.GlassMaterial
import top.yukonga.miuix.kmp.glass.GlassShape
import top.yukonga.miuix.kmp.glass.glass
import top.yukonga.miuix.kmp.theme.MiuixTheme

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
    val shape = GlassShape(if (rounded) 28.dp else 0.dp)
    if (backdrop == null || !isRuntimeShaderSupported() || settings.reduceEffect) {
        return this.clip(shape).background(surface)
    }
    val base = GlassDefaults.Style
    val style = base.copy(
        blend = base.blend.copy(saturation = settings.glassSaturation),
        background = base.background.copy(saturation = settings.glassSaturation),
        refract = base.refract.copy(ior = 1f + settings.glassRefraction),
    )
    return this.glass(
        backdrop = backdrop,
        shape = shape,
        style = style,
        material = GlassMaterial(
            blurRadius = settings.glassBlurRadius.dp,
            first = GlassColorLayer(
                color = surface.copy(alpha = settings.glassTintAlpha),
                mode = GlassColorBlendMode.SrcOver,
            ),
        ),
        noiseCoefficient = settings.glassNoise,
        // IOR=1 disables refraction independently of saturation and color treatment.
        shading = true,
    )
}
