package tw.edu.pu.csim.s1120053.lifelinker02

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import tw.edu.pu.csim.s1120053.lifelinker02.ui.theme.LifeLinker02Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LifeLinker02Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Logo(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
@Composable  //1logo頁
fun Logo(modifier: Modifier) {
    val context = LocalContext.current
    val activity = (context as Activity)
//    val Button
    Image(
        painter = painterResource(id = R.drawable.logo),
        contentDescription = "logo",
        modifier = Modifier
    )
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextButton(
            modifier = Modifier.padding(bottom = 16.dp),
            onClick = {
                val intent = Intent(context, Option::class.java)
                context.startActivity(intent)
            }
        ) {
            Text(text = "點擊進入")
        }
    }
}
