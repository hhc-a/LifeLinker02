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

class Repair : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LifeLinker02Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    repair(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun kindDropdown() {
    val kindOptions = listOf("輪椅", "助行器","拐杖")
    var selectedKind by remember { mutableStateOf(kindOptions.first()) }
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            value = selectedKind,
            onValueChange = {},
            readOnly = true,
            label = { Text("*借用輔具") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier.menuAnchor().fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            kindOptions.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        selectedKind = option
                        expanded = false
                    }
                )
            }
        }
    }
}
@Composable
fun repair(modifier: Modifier) {
//    var kind by remember { kindDropdown() }
    var kind by remember { mutableStateOf("") }
    var describe by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    val context = LocalContext.current
    val activity = (context as Activity)
    val db = Firebase.firestore
    val user = rep(describe,date)
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
            Text(
                text = "*為必填",
                style = TextStyle(
                    fontSize = 20.sp,
                    color = Color.Red
                )
            )
            kindDropdown()
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
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Bottom
        ) {
            TextButton(
                onClick = {
                    db.collection("repair")
//                   .add(user)
                        .document(describe)
                        .set(describe)
                    val intent = Intent(context, Option()::class.java)
                    context.startActivity(intent)
                }
            ) {
                Text(text = "送出")
            }
        }
    }
}
data class rep(
    var describe: String,
    var date: String,
)