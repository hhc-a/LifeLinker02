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
import com.google.firebase.firestore.firestore
import tw.edu.pu.csim.s1120053.lifelinker02.ui.theme.LifeLinker02Theme

class Age : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LifeLinker02Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Age(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SexDropdown() {
    val sexOptions = listOf("男", "女")
    var selectedSex by remember { mutableStateOf(sexOptions.first()) }
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            value = selectedSex,
            onValueChange = {},
            readOnly = true,
            label = { Text("*性別") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier.menuAnchor().fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            sexOptions.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        selectedSex = option
                        expanded = false
                    }
                )
            }
        }
    }
}
@Composable  //4進階資料
fun Age(modifier: Modifier) {
    var sex by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    val context = LocalContext.current
    val activity = (context as Activity)
    val db = Firebase.firestore
    val user = sah(sex,age,height,weight)
    Image(
        painter = painterResource(id = R.drawable.bg),
        contentDescription = "bg",
        modifier = Modifier.fillMaxSize()
    )
    Column(
        modifier = Modifier.fillMaxSize(), // 填滿整個螢幕
        verticalArrangement = Arrangement.SpaceBetween // 垂直方向分佈
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
            SexDropdown()
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
                onClick = { activity.finish() }
            ) {
                Text(text = "上一頁")
            }
            TextButton(
                onClick = {
                    db.collection("age")
//                    .add(user)
                        .document(sex)
                        .set(sex)
                    val intent = Intent(context, Success::class.java)
                    context.startActivity(intent)
                }
            ) {
                Text(text = "送出")
            }
        }
    }
}
data class sah(
    var sex: String,
    var age: String,
    var height:String,
    var weight:String,
)