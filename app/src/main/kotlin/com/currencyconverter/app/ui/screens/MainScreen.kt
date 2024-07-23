package com.currencyconverter.app.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
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
import com.currencyconverter.app.util.reversed
import com.currencyconverter.app.util.title
import com.currencyconverter.app.viewmodel.MainViewModel
import com.currencyconverter.app.viewmodel.ViewModelState
import com.currencyconverter.domain.model.ConversionResponse
import com.currencyconverter.domain.model.Currency

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel,
    title: String,
    navigateToSettings: () -> Unit,
    navigateToResult: (ConversionResponse) -> Unit,
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
            TopAppBar(
                title = {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    IconButton(onClick = navigateToSettings) {
                        Icon(
                            imageVector = Icons.Outlined.Settings,
                            contentDescription = null,
                            modifier = Modifier.scale(1.2f)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
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
                onClick = { viewModel.convert(navigateToResult) },
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
                    containerColor = MaterialTheme.colorScheme.background.reversed,
                    contentColor = MaterialTheme.colorScheme.onBackground.reversed
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
            initialCurrency = viewModel.fromCurrency,
            onSave = { viewModel.fromCurrency = it },
            onClose = { viewModel.showFromCurrencySelectionDialog = false },
            title = stringResource(R.string.from_currency_title)
        )
    }

    if (viewModel.showToCurrencySelectionDialog) {
        CurrencySelectionDialog(
            initialCurrency = viewModel.toCurrency,
            onSave = { viewModel.toCurrency = it },
            onClose = { viewModel.showToCurrencySelectionDialog = false },
            title = stringResource(R.string.to_currency_title),
            disabledCurrencies = setOf(viewModel.fromCurrency)
        )
    }

    Column(
        verticalArrangement = Arrangement.Center,
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
            placeholder = {
                Text(
                    text = stringResource(R.string.amount_placeholder),
                    style = MaterialTheme.typography.bodyMedium,
                )
            },
            textStyle = MaterialTheme.typography.bodyLarge,
            suffix = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.small)
                        .clickable { viewModel.showFromCurrencySelectionDialog = true }
                        .padding(4.dp)
                ) {
                    CurrencySymbol(currency = viewModel.fromCurrency)
                    Icon(
                        imageVector = Icons.Rounded.KeyboardArrowDown,
                        contentDescription = null,
                        modifier = Modifier.scale(1.1f)
                    )
                }
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            shape = MaterialTheme.shapes.medium,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.onBackground,
                focusedPlaceholderColor = Color.Gray,
                unfocusedPlaceholderColor = Color.Gray,
                focusedSuffixColor = MaterialTheme.colorScheme.primary,
                unfocusedSuffixColor = MaterialTheme.colorScheme.onBackground,
            ),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        )


        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .clip(MaterialTheme.shapes.small)
                .clickable { viewModel.showToCurrencySelectionDialog = true }
                .padding(vertical = 16.dp, horizontal = 4.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.to_currency_title) + ":",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                CurrencySymbol(
                    currency = viewModel.toCurrency,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = viewModel.toCurrency.code,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                )
                Icon(
                    imageVector = Icons.Rounded.KeyboardArrowDown,
                    contentDescription = null,
                    modifier = Modifier.scale(1.1f)
                )
            }
        }
    }
}


@Composable
private fun CurrencySelectionDialog(
    initialCurrency: Currency,
    title: String,
    onSave: (Currency) -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier,
    disabledCurrencies: Set<Currency> = emptySet()
) {
    AlertDialog(
        onDismissRequest = onClose,
        confirmButton = {
            TextButton(
                onClick = {
                    onSave(initialCurrency)
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
                    val isEnabled = currency !in disabledCurrencies
                    ListItem(
                        headlineContent = {
                            Text(
                                text = currency.title,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        },
                        leadingContent = {
                            CurrencySymbol(
                                currency = currency,
                                color = when {
                                    isEnabled -> MaterialTheme.colorScheme.onBackground
                                    else -> Color.Gray
                                }
                            )
                        },
                        trailingContent = {
                            if (currency == initialCurrency) {
                                Icon(
                                    imageVector = Icons.Rounded.Check,
                                    contentDescription = "checked"
                                )
                            }
                        },
                        colors = ListItemDefaults.colors(
                            containerColor = Color.Transparent,
                            headlineColor = when {
                                isEnabled -> MaterialTheme.colorScheme.onBackground
                                else -> Color.Gray
                            },
                            leadingIconColor = when {
                                isEnabled -> MaterialTheme.colorScheme.onBackground
                                else -> Color.Gray
                            },
                            trailingIconColor = MaterialTheme.colorScheme.primary
                        ),
                        modifier = Modifier
                            .clickable(enabled = isEnabled) {
                                onSave(currency)
                                onClose()
                            }
                            .clip(MaterialTheme.shapes.medium)
                    )
                }
            }
        },
        modifier = modifier,
    )
}



@Composable
private fun CurrencySymbol(
    currency: Currency,
    modifier: Modifier = Modifier,
    color: Color = LocalContentColor.current
) {
    Text(
        text = currency.symbol,
        style = MaterialTheme.typography.bodyLarge,
        fontWeight = FontWeight.Bold,
        color = color,
        modifier = modifier
    )
}