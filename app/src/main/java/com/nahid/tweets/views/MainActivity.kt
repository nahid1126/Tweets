package com.nahid.tweets.views

import android.os.Bundle
import android.util.Log
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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.nahid.tweets.model.networks.NetworkResponse
import com.nahid.tweets.ui.theme.TweetsTheme
import com.nahid.tweets.viewmodel.TweetsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var tweetsViewModel: TweetsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        tweetsViewModel = ViewModelProvider(this)[TweetsViewModel::class.java]
        tweetsViewModel.reqForTweetsCategory()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                tweetsViewModel.tweetsCategory.collect {
                    when (it) {
                        is NetworkResponse.Empty -> {

                        }

                        is NetworkResponse.Error -> {
                            Log.d(TAG, "tweetsCategory: ${it.message}")
                        }

                        is NetworkResponse.Loading -> {

                        }

                        is NetworkResponse.Success -> {
                            Log.d(TAG, ":: ${it.data}")
                        }
                    }
                }
            }
        }


        setContent {
            TweetsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }

    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TweetsTheme {
        Greeting("Android")
    }
}