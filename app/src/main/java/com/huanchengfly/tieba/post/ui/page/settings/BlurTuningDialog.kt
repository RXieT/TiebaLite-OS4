package com.huanchengfly.tieba.post.ui.page.settings

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.huanchengfly.tieba.post.R
import com.huanchengfly.tieba.post.core.data.model.settings.Settings
import com.huanchengfly.tieba.post.core.data.model.settings.UISettings
import com.huanchengfly.tieba.post.core.ui.context.LocalUISettings
import top.yukonga.miuix.kmp.basic.Slider
import top.yukonga.miuix.kmp.basic.Switch

@Composable
fun BlurTuningDialog(settings: Settings<UISettings>, onDismissRequest: () -> Unit) {
    val currentPreference by settings.collectAsStateWithLifecycle(initialValue = LocalUISettings.current)
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(stringResource(R.string.blur_tuning_title)) },
        text = {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                BlurPreview(currentPreference)
                Text(stringResource(R.string.blur_tuning_priority))
                if (currentPreference.reduceEffect) {
                    Text(
                        text = stringResource(R.string.blur_tuning_reduced),
                        color = MaterialTheme.colorScheme.primary,
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(stringResource(R.string.blur_tuning_enabled), Modifier.weight(1f))
                    Switch(
                        checked = currentPreference.glassEnabled,
                        onCheckedChange = { enabled ->
                            settings.save { it.copy(glassEnabled = enabled) }
                        },
                    )
                }
                BlurTuningSlider(
                    label = R.string.blur_tuning_radius,
                    value = currentPreference.glassBlurRadius,
                    max = 80f,
                    enabled = currentPreference.glassEnabled,
                    valueFormat = R.string.blur_tuning_value_dp,
                ) { value -> settings.save { it.copy(glassBlurRadius = value) } }
                BlurTuningSlider(
                    label = R.string.blur_tuning_tint,
                    value = currentPreference.glassTintAlpha,
                    max = 1f,
                    enabled = currentPreference.glassEnabled,
                ) { value -> settings.save { it.copy(glassTintAlpha = value) } }
                BlurTuningSlider(
                    label = R.string.blur_tuning_noise,
                    value = currentPreference.glassNoise,
                    max = 0.3f,
                    enabled = currentPreference.glassEnabled,
                ) { value -> settings.save { it.copy(glassNoise = value) } }
                BlurTuningSlider(
                    label = R.string.blur_tuning_refraction,
                    value = currentPreference.glassRefraction,
                    max = 1f,
                    enabled = currentPreference.glassEnabled,
                ) { value -> settings.save { it.copy(glassRefraction = value) } }
                BlurTuningSlider(
                    label = R.string.blur_tuning_saturation,
                    value = currentPreference.glassSaturation,
                    max = 2f,
                    enabled = currentPreference.glassEnabled,
                ) { value -> settings.save { it.copy(glassSaturation = value) } }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismissRequest) {
                Text(stringResource(R.string.blur_tuning_close))
            }
        },
        dismissButton = {
            TextButton(onClick = {
                val defaults = UISettings()
                settings.save { old ->
                    old.copy(
                        glassEnabled = defaults.glassEnabled,
                        glassBlurRadius = defaults.glassBlurRadius,
                        glassTintAlpha = defaults.glassTintAlpha,
                        glassNoise = defaults.glassNoise,
                        glassRefraction = defaults.glassRefraction,
                        glassSaturation = defaults.glassSaturation,
                    )
                }
            }) {
                Text(stringResource(R.string.blur_tuning_reset))
            }
        },
    )
}

@Composable
private fun BlurTuningSlider(
    @StringRes label: Int,
    value: Float,
    max: Float,
    enabled: Boolean,
    @StringRes valueFormat: Int = R.string.blur_tuning_value,
    onValueChange: (Float) -> Unit,
) {
    // Keep thumb movement responsive while queued DataStore writes are processed.
    var position by remember(value) { mutableFloatStateOf(value) }
    Column {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(stringResource(label), Modifier.weight(1f))
            Text(stringResource(valueFormat, position))
        }
        Slider(
            value = position,
            onValueChange = {
                position = it
                onValueChange(it)
            },
            valueRange = 0f..max,
            enabled = enabled,
        )
    }
}
