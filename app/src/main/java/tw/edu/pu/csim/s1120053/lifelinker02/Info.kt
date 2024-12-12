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
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore
import tw.edu.pu.csim.s1120053.lifelinker02.ui.theme.LifeLinker02Theme

class Info : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LifeLinker02Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Basic(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
@Composable  //3基本資料
fun Basic(modifier: Modifier) {
    var devices by remember { mutableStateOf("") }
    var userName by remember { mutableStateOf("") }
    var userTel by remember { mutableStateOf("") }
    var userID by remember { mutableStateOf("") }
    var disability by remember { mutableStateOf("") }
    var economic by remember { mutableStateOf("") }
    var contactName by remember { mutableStateOf("") }
    var contactTel by remember { mutableStateOf("") }
    var contactMail by remember { mutableStateOf("") }
    val context = LocalContext.current
    val db = Firebase.firestore
    val saveToFirebase: (String) -> Unit = { selectedValue ->
        val collectionName = when { // 根據不同的下拉式選單儲存到不同的集合
            selectedValue in listOf("一般", "低收", "中低收", "身障") -> "economic_status"
            selectedValue in listOf("無證明", "有證明") -> "disability_status"
            else -> "other_selections"
        }
        val data = hashMapOf(
            "value" to selectedValue,
            "timestamp" to FieldValue.serverTimestamp()
        )
        db.collection(collectionName)
            .add(data)
            .addOnSuccessListener {
                Log.d("Firebase", "Document added with ID: ${it.id}")
            }
            .addOnFailureListener { e ->
                Log.w("Firebase", "Error adding document", e)
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
                label = { Text("*需租借之輔具") },
                placeholder = { Text("輪椅/助行器/拐杖") }
            )
            TextField(
                value = userTel,
                onValueChange = { newText -> userTel = newText },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("*使用者電話") },
                placeholder = { Text("請輸入使用者電話") }
            )
            TextField(
                value = userID,
                onValueChange = { newText -> userID = newText },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("*使用者身分證或護照號") },
                placeholder = { Text(text = "請輸入使用者身分證或護照號") },
            )
            TextField(
                value = disability,
                onValueChange = { newText -> disability = newText },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("*身障資格") },
                placeholder = { Text("有/無") }
            )
            TextField(
                value = economic,
                onValueChange = { newText -> economic = newText },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("*經濟別") },
                placeholder = { Text("一般/低收/中低收/身障") }
            )
            TextField(
                value = contactName,
                onValueChange = { newText -> contactName = newText },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("*租借人(聯絡人)姓名") },
                placeholder = { Text("請輸入您的姓名") }
            )
            TextField(
                value = contactTel,
                onValueChange = { newText -> contactTel = newText },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("*租借人(聯絡人)電話") },
                placeholder = { Text("請輸入您的電話") }
            )
            TextField(
                value = contactMail,
                onValueChange = { newText -> contactMail = newText },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("*租借人(聯絡人)信箱") },
                placeholder = { Text("請輸入您的信箱") }
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
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
                    val userInfo = Borrow(
                        userName = userName,
                        devices = devices,
                        userTel = userTel,
                        userID = userID,
                        disability = disability,
                        economic = economic,
                        contactName = contactName,
                        contactTel = contactTel,
                        contactMail = contactMail
                    )
                    db.collection("info")
                        .document(userName)
                        .set(userInfo)
                        .addOnSuccessListener {
                            val intent = Intent(context, Age::class.java)
                            context.startActivity(intent)
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(context, "儲存失敗: ${e.message}", Toast.LENGTH_SHORT)
                                .show()
                        }
                }
            ) {
                Text(text = "下一頁")
            }
        }
    }
}
data class Borrow(
    var userName: String,
    var devices:String,
    var userTel: String,
    var userID:String,
    var disability:String,
    var economic:String,
    var contactName:String,
    var contactTel:String,
    var contactMail:String,
)