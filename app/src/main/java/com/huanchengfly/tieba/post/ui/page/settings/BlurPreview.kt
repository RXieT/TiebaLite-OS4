package com.huanchengfly.tieba.post.ui.page.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.huanchengfly.tieba.post.R
import com.huanchengfly.tieba.post.core.data.model.settings.UISettings
import com.huanchengfly.tieba.post.core.ui.context.LocalUISettings
import com.huanchengfly.tieba.post.ui.widgets.compose.OS4BarSurface
import top.yukonga.miuix.kmp.blur.layerBackdrop
import top.yukonga.miuix.kmp.blur.rememberLayerBackdrop

@Composable
internal fun BlurPreview(settings: UISettings) {
    val backdrop = rememberLayerBackdrop()
    CompositionLocalProvider(LocalUISettings provides settings) {
        Box(Modifier.fillMaxWidth().height(104.dp).clip(RoundedCornerShape(20.dp))) {
            Box(
                Modifier.matchParentSize().layerBackdrop(backdrop).background(
                    Brush.linearGradient(listOf(Color(0xFF5C8DFF), Color(0xFFEBA6CD), Color(0xFFFFCC72)))
                ),
            ) {
                Column(Modifier.padding(8.dp)) {
                    repeat(4) { index ->
                        Box(Modifier.fillMaxWidth(if (index % 2 == 0) 0.7f else 0.4f)
                            .padding(4.dp).height(10.dp).background(Color.White.copy(alpha = 0.7f)))
                    }
                }
            }
            Box(Modifier.align(Alignment.Center)) {
                val label: @Composable () -> Unit = {
                    Text(
                        text = stringResource(R.string.blur_tuning_preview),
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(horizontal = 28.dp, vertical = 18.dp),
                    )
                }
                if (settings.glassEnabled && !settings.reduceEffect) {
                    OS4BarSurface(backdrop, rounded = true, content = label)
                } else {
                    Box(Modifier.background(MaterialTheme.colorScheme.surface, RoundedCornerShape(28.dp))) { label() }
                }
            }
        }
    }
}
