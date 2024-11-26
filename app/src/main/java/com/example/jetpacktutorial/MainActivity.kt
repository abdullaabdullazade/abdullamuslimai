package com.example.jetpacktutorial

import com.google.ai.client.generativeai.GenerativeModel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpacktutorial.ui.theme.JetpackTutorialTheme
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackTutorialTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val scrollState = rememberScrollState() // Scrollview

    Scaffold { innerPadding ->
        Box(//Scrollview etmek ucun
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(32.dp)

        ) {
            Column(//Sutun kimi duzulemsi ucun
                modifier = Modifier //style verme
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally, // Demek bu normal yuxaridan ortaya getirmekdir enine
                verticalArrangement = Arrangement.Center //Bu ise tam ortasina getirmek ucundur
            ) {
                mainSystem {} // functionu call etmek

            }
        }
    }
}

@Composable
fun mainSystem(onClick: () -> Unit) {
    var text by remember { mutableStateOf("") }//expodaki useState
    var response by remember { mutableStateOf<String?>(null) }

    TextField(//textField
        value = text,
        onValueChange = { text = it },//it = deyisen value
        label = { Text("Zəhmət olmasa sualınızı qeyd edin. ") }
    )
    Spacer(modifier = Modifier.height(16.dp)) //aralarında məsafə qoymaq üçün istifadə olunur
    //aralarindaki mesafe ucun spacer istifade olunur

    Button(onClick = {
        GlobalScope.launch {
            response = getMessage(text)
        }
    }) {
        Text("Göndər.")
    }

    response?.let {
        Text(it) // Yeni deyir ki response null olmasa it yeni response goster
    }

}


suspend fun getMessage(userPrompt: String): String {
    val configurationGemini = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = "AIzaSyC4NqRrb1yVioKcB4BFv6jLtKwxgoF5w0k"
    )

    return try {
        val response = configurationGemini.generateContent(userPrompt)
        response.text.toString() // Uğurlu cavab
    } catch (e: Exception) {
        "Xəta baş verdi: ${e.message}" // Xətanı idarə et
    }

}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    JetpackTutorialTheme {
        MainScreen()
    }
}
