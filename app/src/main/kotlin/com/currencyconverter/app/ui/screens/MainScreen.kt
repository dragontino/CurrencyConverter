package com.currencyconverter.app.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.CurrencyExchange
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.currencyconverter.app.R
import com.currencyconverter.app.util.title
import com.currencyconverter.app.viewmodel.MainViewModel
import com.currencyconverter.app.viewmodel.ViewModelState
import com.currencyconverter.domain.model.Currency

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel,
    title: String,
    navigateToSettings: () -> Unit,
    modifier: Modifier = Modifier
) {
    val snackbarHostState = remember(::SnackbarHostState)

    LaunchedEffect(Unit) {
        viewModel.snackbarFlow.collect { message ->
            snackbarHostState.showSnackbar(message)
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    OutlinedIconButton(onClick = navigateToSettings) {
                        Icon(
                            imageVector = Icons.Outlined.Settings,
                            contentDescription = null,
                            modifier = Modifier.scale(1.4f)
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                    actionIconContentColor = MaterialTheme.colorScheme.onBackground
                )
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = {
                    Text(
                        text = stringResource(R.string.convert_action),
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                icon = {
                    Icon(
                        imageVector = Icons.Rounded.CurrencyExchange,
                        contentDescription = null
                    )
                },
                onClick = viewModel::convert,
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                elevation = FloatingActionButtonDefaults.loweredElevation()
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
        snackbarHost = {
            SnackbarHost(snackbarHostState) {
                Snackbar(
                    snackbarData = it,
                    shape = MaterialTheme.shapes.medium,
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.onBackground
                )
            }
        },
        modifier = Modifier
            .then(modifier)
            .fillMaxSize(),
    ) { contentPadding ->
        when(viewModel.state) {
            ViewModelState.Loading -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
            ViewModelState.Stable -> MainContent(
                viewModel = viewModel,
                modifier = Modifier.padding(contentPadding)
            )
        }
    }
}


@Composable
private fun MainContent(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    if (viewModel.showFromCurrencySelectionDialog) {
        CurrencySelectionDialog(
            currency = viewModel.fromCurrency,
            onSave = { viewModel.fromCurrency = it },
            onClose = { viewModel.showFromCurrencySelectionDialog = false },
            title = stringResource(R.string.from_currency_title)
        )
    }

    if (viewModel.showToCurrencySelectionDialog) {
        CurrencySelectionDialog(
            currency = viewModel.toCurrency,
            onSave = { viewModel.toCurrency = it },
            onClose = { viewModel.showToCurrencySelectionDialog = false },
            title = stringResource(R.string.to_currency_title)
        )
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(top = 16.dp)
            .then(modifier)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        OutlinedTextField(
            value = viewModel.amount,
            onValueChange = viewModel::amount::set,
            trailingIcon = {
                Row(
                    modifier = Modifier
                        .clickable { viewModel.showFromCurrencySelectionDialog = true }
                        .clip(MaterialTheme.shapes.small)
                ) {
                    Text(
                        text = viewModel.fromCurrency.symbol,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold
                    )

                    Icon(
                        imageVector = Icons.Rounded.KeyboardArrowDown,
                        contentDescription = null
                    )
                }
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            shape = MaterialTheme.shapes.medium
        )

        ListItem(
            headlineContent = {
                Text(
                    text = stringResource(R.string.to_currency_title),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
            },
            trailingContent = {
                Text(
                    text = viewModel.toCurrency.symbol,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
            },
            modifier = Modifier
                .clickable { viewModel.showToCurrencySelectionDialog = true }
                .fillMaxWidth(),
            colors = ListItemDefaults.colors(
                containerColor = Color.Transparent,
                headlineColor = MaterialTheme.colorScheme.onBackground,
                trailingIconColor = MaterialTheme.colorScheme.onBackground
            )
        )

        if (viewModel.showResult) {
            OutlinedTextField(
                value = viewModel.result,
                onValueChange = {},
                readOnly = true,
                shape = MaterialTheme.shapes.medium,
                label = {
                    Text("Результат конвертации")
                }
            )
        }
    }
}


@Composable
private fun CurrencySelectionDialog(
    currency: Currency,
    onSave: (Currency) -> Unit,
    onClose: () -> Unit,
    title: String,
    modifier: Modifier = Modifier
) {
    var selectedCurrency by rememberSaveable { mutableStateOf(currency) }

    AlertDialog(
        onDismissRequest = onClose,
        confirmButton = {
            TextButton(
                onClick = {
                    onSave(selectedCurrency)
                    onClose()
                },
            ) {
                Text(
                    text = stringResource(R.string.save),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onClose) {
                Text(
                    text = stringResource(android.R.string.cancel),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        },
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                Currency.entries.forEach { currency ->
                    ListItem(
                        headlineContent = {
                            Text(
                                text = currency.title,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        },
                        leadingContent = {
                            Text(
                                text = currency.symbol,
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold
                            )
                        },
                        trailingContent = {
                            if (currency == selectedCurrency) {
                                Icon(
                                    imageVector = Icons.Rounded.Check,
                                    contentDescription = "checked"
                                )
                            }
                        },
                        colors = ListItemDefaults.colors(
                            containerColor = Color.Transparent,
                            headlineColor = MaterialTheme.colorScheme.onBackground,
                            leadingIconColor = MaterialTheme.colorScheme.onBackground,
                            trailingIconColor = MaterialTheme.colorScheme.primary
                        ),
                        modifier = Modifier
                            .clickable { selectedCurrency = currency }
                            .clip(MaterialTheme.shapes.medium)
                    )
                }
            }
        },
        modifier = modifier,
    )
}