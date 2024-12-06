package tw.edu.pu.csim.s1120053.lifelinker02

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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
                    test(m = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun test(m: Modifier){
    var userName by remember { mutableStateOf("") }
    var msg by remember { mutableStateOf("") }
    val db = Firebase.firestore

    Column {
        TextField(
            value = userName,
            onValueChange = { newText ->
                userName = newText
            },
            modifier = m
        )

        Text("您輸入的姓名是：$userName")
    }
    Button(onClick = {
        val user = Person(userName)
        db.collection("users")
            .add(user)
            .addOnSuccessListener { documentReference ->
                msg = "新增/異動資料成功"
            }
            .addOnFailureListener { e ->
                msg = "新增/異動資料失敗：" + e.toString()
            }
    })
    {
        Text("新增/修改資料")
    }
    Text(msg)
}
data class Person(
    var userName: String,
)
