package com.wk.apollographql

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wk.apollographql.presentation.CountriesViewModel
import com.wk.apollographql.presentation.HomeScreen
import com.wk.apollographql.ui.theme.ApolloGraphQlTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ApolloGraphQlTheme {
                val countriesViewModel: CountriesViewModel = hiltViewModel()
                val countries = countriesViewModel.state.collectAsStateWithLifecycle().value
                HomeScreen(
                    state = countries,
                    onSelectCountry = countriesViewModel::selectCountry,
                    onDismissCountryDialog = countriesViewModel::dismissCountryDialog
                )
            }
        }
    }
}
