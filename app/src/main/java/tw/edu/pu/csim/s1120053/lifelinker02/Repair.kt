package tw.edu.pu.csim.s1120053.lifelinker02

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import tw.edu.pu.csim.s1120053.lifelinker02.ui.theme.LifeLinker02Theme

class Repair : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        FirebaseApp.initializeApp(this)
        setContent {
            LifeLinker02Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    repair(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
@Composable //修復
fun repair(modifier: Modifier) {
    var userName by remember { mutableStateOf("") }
    var devices by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var describe by remember { mutableStateOf("") }
    val context = LocalContext.current
    val db = FirebaseFirestore.getInstance()
    val saveToFirebase: (String) -> Unit = { selectedValue ->
        val data = hashMapOf(
            "sex" to selectedValue,
            "timestamp" to FieldValue.serverTimestamp()
        )
        db.collection("sex_selection")
            .add(data)
            .addOnSuccessListener {
                Log.d("Firebase", "Sex selection added with ID: ${it.id}")
            }
            .addOnFailureListener { e ->
                Log.w("Firebase", "Error adding sex selection", e)
            }
    }
    Image(
        painter = painterResource(id = R.drawable.bg),
        contentDescription = "bg",
        modifier = Modifier.fillMaxSize()
    )
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Text("")
            Text(
                text = "*為必填",
                style = TextStyle(
                    fontSize = 20.sp,
                    color = Color.Red
                )
            )
            TextField(
                value = userName,
                onValueChange = { newText -> userName = newText },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("*使用者姓名") },
                placeholder = { Text("請輸入使用者姓名") }
            )
            TextField(
                value = devices,
                onValueChange = { newText -> devices = newText },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("您所租借之輔具") },
                placeholder = { Text("輪椅/助行器/拐杖") }
            )
            TextField(
                value = date,
                onValueChange = { newText -> date = newText },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("*填寫維修日期") },
                placeholder = { Text("請輸入YYYY/MM/DD") }
            )
            TextField(
                value = describe,
                onValueChange = { newText -> describe = newText },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("*描述") },
                placeholder = { Text("請描述順壞狀況") }
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp,end = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            TextButton(
                onClick = {
                    val intent = Intent(context, Option::class.java)
                    context.startActivity(intent)
                }
            ) {
                Text(text = "回到主畫面")
            }
            TextButton(
                onClick = {
                    val repair = rep(
                        userName = userName,
                        devices = devices,
                        describe = describe,
                        date = date,
                    )
                    db.collection("repair0")
                        .document(userName)
                        .set(repair)
                        .addOnSuccessListener {
                            val intent = Intent(context, Success()::class.java)
                            context.startActivity(intent)
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(context, "儲存失敗: ${e.message}", Toast.LENGTH_SHORT)
                                .show()
                        }
                }
            ) {
                Text(text = "送出")
            }
        }
    }
}
data class rep(
    var userName: String,
    var devices:String,
    var describe: String,
    var date: String,
)