package com.currencyconverter.app.ui.screens

import android.os.Build
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.currencyconverter.app.R
import com.currencyconverter.app.util.title
import com.currencyconverter.app.viewmodel.SettingsViewModel
import com.currencyconverter.domain.model.ColorScheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    title: String,
    popBackStack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = popBackStack) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBackIosNew,
                            contentDescription = null
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.background
    ) { contentPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
                .padding(contentPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.color_scheme) + ":",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.weight(1f)
                )

                SingleChoiceSegmentedButtonRow(modifier = Modifier.weight(3f)) {
                    ColorScheme.entries.forEachIndexed { index, scheme ->
                        val shape = when (index) {
                            0 -> MaterialTheme.shapes.medium.copy(
                                topEnd = CornerSize(0),
                                bottomEnd = CornerSize(0)
                            )
                            ColorScheme.entries.lastIndex -> MaterialTheme.shapes.medium.copy(
                                topStart = CornerSize(0),
                                bottomStart = CornerSize(0)
                            )
                            else -> RectangleShape
                        }

                        SegmentedButton(
                            selected = scheme == viewModel.settings.scheme,
                            onClick = { viewModel.updateScheme(scheme) },
                            shape = shape,
                            modifier = Modifier.padding(vertical = 16.dp)
                        ) {
                            Text(
                                text = scheme.title,
                                style = MaterialTheme.typography.bodySmall,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.padding(vertical = 16.dp)
                            )
                        }
                    }
                }
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = modifier
                        .clip(MaterialTheme.shapes.small)
                        .toggleable(
                            value = viewModel.settings.dynamicColorEnabled,
                            role = Role.Switch,
                            onValueChange = viewModel::updateDynamicColor
                        )
                        .padding(vertical = 16.dp)
                        .fillMaxWidth()
                ) {
                    Column(modifier = Modifier.weight(2f)) {
                        Text(
                            text = stringResource(R.string.dynamic_color),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )

                        Text(
                            text = stringResource(R.string.dynamic_color_desc),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.9f)
                        )
                    }

                    Switch(
                        checked = viewModel.settings.dynamicColorEnabled,
                        onCheckedChange = null,
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = MaterialTheme.colorScheme.onPrimary,
                            checkedTrackColor = MaterialTheme.colorScheme.primary,
                            uncheckedThumbColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            uncheckedTrackColor = MaterialTheme.colorScheme.primaryContainer,
                            uncheckedBorderColor = MaterialTheme.colorScheme.secondary,
                        ),
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
        }
    }
}