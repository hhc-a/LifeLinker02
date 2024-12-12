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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import tw.edu.pu.csim.s1120053.lifelinker02.ui.theme.LifeLinker02Theme

class Age : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        FirebaseApp.initializeApp(this)
        setContent {
            LifeLinker02Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Age(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
@Composable  //4進階資料
fun Age(modifier: Modifier) {
    var userName by remember { mutableStateOf("") }
    var sex by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
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
            Text(text = "*為必填",
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
                value = sex,
                onValueChange = { newText -> sex = newText },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("*使用者性別") },
                placeholder = { Text("男/女") }
            )
            TextField(
                value = age,
                onValueChange = { newText -> age = newText },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("*使用者年齡") },
                placeholder = { Text("請輸入使用者年齡") }
            )
            TextField(
                value = height,
                onValueChange = { newText -> height = newText },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("*使用者身高") },
                placeholder = { Text("請輸入使用者身高") }
            )
            TextField(
                value = weight,
                onValueChange = { newText -> weight = newText },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("*使用者體重") },
                placeholder = { Text("請輸入使用者體重") }
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp, start = 16.dp, end = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            TextButton(
                onClick = {
                    val intent = Intent(context, Info::class.java)
                    context.startActivity(intent)
                }
            ) {
                Text(text = "上一頁")
            }
            TextButton(
                onClick = {
                    val userAge = sah(
                        userName = userName,
                        sex = sex,
                        age = age,
                        height = height,
                        weight = weight,
                    )
                    db.collection("age")
                        .document(userName)
                        .set(userAge)
                        .addOnSuccessListener {
                            val intent = Intent(context, Success::class.java)
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
data class sah(
    var userName: String,
    var sex: String,
    var age: String,
    var height:String,
    var weight:String,
)