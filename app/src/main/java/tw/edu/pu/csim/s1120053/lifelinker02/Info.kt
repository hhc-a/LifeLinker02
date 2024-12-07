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
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun EconomicDropdown(selectedKind: (String) -> Unit) {
//    val economicOptions = listOf("一般", "低收", "中低收", "身障")
//    var selectedEconomic by remember { mutableStateOf(economicOptions.first()) }
//    var expanded by remember { mutableStateOf(false) }
//    ExposedDropdownMenuBox(
//        expanded = expanded,
//        onExpandedChange = { expanded = !expanded },
//    ) {
//        TextField(
//            value = selectedEconomic,
//            onValueChange = {},
//            readOnly = true,
//            label = { Text("*經濟別") },
//            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
//            colors = ExposedDropdownMenuDefaults.textFieldColors(),
//            modifier = Modifier
//                .menuAnchor()
//                .fillMaxWidth()
//        )
//        ExposedDropdownMenu(
//            expanded = expanded,
//            onDismissRequest = { expanded = false }
//        ) {
//            economicOptions.forEach { option ->
//                DropdownMenuItem(
//                    text = { Text(option) },
//                    onClick = {
//                        selectedEconomic = option
//                        expanded = false
//                    }
//                )
//            }
//        }
//    }
//}
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun Disability(selectedKind: (String) -> Unit) {
//    val economicOptions = listOf("無證明", "有證明")
//    var selectedDisable by remember { mutableStateOf(economicOptions.first()) }
//    var expanded by remember { mutableStateOf(false) }
//    ExposedDropdownMenuBox(
//        expanded = expanded,
//        onExpandedChange = { expanded = !expanded },
//    ) {
//        TextField(
//            value = selectedDisable,
//            onValueChange = {},
//            readOnly = true,
//            label = { Text("*身障資格") },
//            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
//            colors = ExposedDropdownMenuDefaults.textFieldColors(),
//            modifier = Modifier
//                .menuAnchor()
//                .fillMaxWidth()
//        )
//        ExposedDropdownMenu(
//            expanded = expanded,
//            onDismissRequest = { expanded = false }
//        ) {
//            economicOptions.forEach { option ->
//                DropdownMenuItem(
//                    text = { Text(option) },
//                    onClick = {
//                        selectedDisable = option
//                        expanded = false
//                    }
//                )
//            }
//        }
//    }
//}
//@Composable  //3基本資料
//fun Basic(modifier: Modifier) {
//    var userName by remember { mutableStateOf("") }
//    var userTel by remember { mutableStateOf("") }
//    var userID by remember { mutableStateOf("") }
//    var disability by remember { mutableStateOf("") }
//    var economic by remember { mutableStateOf("") }
//    var contactName by remember { mutableStateOf("") }
//    var contactTel by remember { mutableStateOf("") }
//    var contactMail by remember { mutableStateOf("") }
//    val context = LocalContext.current
//    val activity = (context as Activity)
//    val db = Firebase.firestore
//    val user = Borrow(userName, userTel, userID, contactName, contactTel, contactMail)
//    Image(
//        painter = painterResource(id = R.drawable.bg),
//        contentDescription = "bg",
//        modifier = Modifier.fillMaxSize()
//    )
//    Column(
//        modifier = Modifier.fillMaxSize(), // 填滿整個螢幕
//        verticalArrangement = Arrangement.SpaceBetween // 垂直方向分佈
//    ) {
//        Column(
//            modifier = Modifier.padding(horizontal = 16.dp)
//        ) {
//            Text("")
//            Text(
//                text = "*為必填",
//                style = TextStyle(
//                    fontSize = 20.sp,
//                    color = Color.Red
//                )
//            )
//            TextField(
//                value = userName,
//                onValueChange = { newText ->
//                    userName = newText
//                },
//                modifier = Modifier.fillMaxWidth(),
//                label = { Text("*使用者姓名") },
//                placeholder = { Text("請輸入使用者姓名") }
//            )
//            TextField(
//                value = userTel,
//                onValueChange = { newText ->
//                    userTel = newText
//                },
//                modifier = Modifier.fillMaxWidth(),
//                label = { Text("*使用者電話") },
//                placeholder = { Text("請輸入使用者電話") }
//            )
//            TextField(
//                value = userID,
//                onValueChange = { newText ->
//                    userID = newText
//                },
//                modifier = Modifier.fillMaxWidth(),
//                label = { Text("*使用者身分證或護照號") },
//                placeholder = { Text(text = "請輸入使用者身分證或護照號") },
//            )
//            Disability{selectedDisable -> disability = selectedDisable}
//            EconomicDropdown {selectedEconomic -> economic = selectedEconomic}
//            TextField(
//                value = contactName,
//                onValueChange = { newText ->
//                    contactName = newText
//                },
//                modifier = Modifier.fillMaxWidth(),
//                label = { Text("*租借人(聯絡人)姓名") },
//                placeholder = { Text("請輸入您的姓名") }
//            )
//            TextField(
//                value = contactTel,
//                onValueChange = { newText ->
//                    contactTel = newText
//                },
//                modifier = Modifier.fillMaxWidth(),
//                label = { Text("*租借人(聯絡人)電話") },
//                placeholder = { Text("請輸入您的電話") }
//            )
//            TextField(
//                value = contactMail,
//                onValueChange = { newText ->
//                    contactMail = newText
//                },
//                modifier = Modifier.fillMaxWidth(),
//                label = { Text("*租借人(聯絡人)信箱") },
//                placeholder = { Text("請輸入您的信箱") }
//            )
//        }
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(bottom = 16.dp),
//            horizontalArrangement = Arrangement.SpaceBetween,
//            verticalAlignment = Alignment.Bottom
//        ) {
//            TextButton(
//                onClick = {
////                    activity.finish()
//                    val intent = Intent(context, Option::class.java)
//                    context.startActivity(intent)
//                }
//            ) {
//                Text(text = "回到主畫面")
//            }
//            TextButton(
//                onClick = {
//                    db.collection("info")
//                        .document(userName)
//                        .set(user)
//                    val intent = Intent(context, Age::class.java)
//                    context.startActivity(intent)
//                }
//            ) {
//                Text(text = "下一頁")
//            }
//        }
//    }
//}
//data class Borrow(
//    var userName: String,
//    var userTel: String,
//    var userID:String,
//    var contactName:String,
//    var contactTel:String,
//    var contactMail:String,
//)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EconomicDropdown(
    selectedKind: (String) -> Unit,
    onSaveToFirebase: (String) -> Unit = {}
) {
    val economicOptions = listOf("一般", "低收", "中低收", "身障")
    var selectedEconomic by remember { mutableStateOf(economicOptions.first()) }
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
    ) {
        TextField(
            value = selectedEconomic,
            onValueChange = {},
            readOnly = true,
            label = { Text("*經濟別") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            economicOptions.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        selectedEconomic = option
                        expanded = false
                        // 呼叫傳入的回調函數
                        selectedKind(option)
                        // 儲存到 Firebase
                        onSaveToFirebase(option)
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Disability(
    selectedKind: (String) -> Unit,
    onSaveToFirebase: (String) -> Unit = {}
) {
    val disabilityOptions = listOf("無證明", "有證明")
    var selectedDisable by remember { mutableStateOf(disabilityOptions.first()) }
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
    ) {
        TextField(
            value = selectedDisable,
            onValueChange = {},
            readOnly = true,
            label = { Text("*身障資格") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            disabilityOptions.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        selectedDisable = option
                        expanded = false
                        // 呼叫傳入的回調函數
                        selectedKind(option)
                        // 儲存到 Firebase
                        onSaveToFirebase(option)
                    }
                )
            }
        }
    }
}

// 修改 Basic 函數以整合 Firebase 儲存
@Composable
fun Basic(modifier: Modifier) {
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

    // 建立整合 Firebase 儲存的函數
    val saveToFirebase: (String) -> Unit = { selectedValue ->
        // 根據不同的下拉式選單儲存到不同的集合
        val collectionName = when {
            selectedValue in listOf("一般", "低收", "中低收", "身障") -> "economic_status"
            selectedValue in listOf("無證明", "有證明") -> "disability_status"
            else -> "other_selections"
        }

        // 準備要儲存的資料
        val data = hashMapOf(
            "value" to selectedValue,
            "timestamp" to FieldValue.serverTimestamp()
        )

        // 新增到 Firebase
        db.collection(collectionName)
            .add(data)
            .addOnSuccessListener {
                // 成功儲存
                Log.d("Firebase", "Document added with ID: ${it.id}")
            }
            .addOnFailureListener { e ->
                // 儲存失敗
                Log.w("Firebase", "Error adding document", e)
            }
    }

    // 修改下拉式選項的呼叫方式
    Column {
        // ... 其他 TextField 保持不變

        Disability(
            selectedKind = { selectedDisable -> disability = selectedDisable },
            onSaveToFirebase = saveToFirebase
        )

        EconomicDropdown(
            selectedKind = { selectedEconomic -> economic = selectedEconomic },
            onSaveToFirebase = saveToFirebase
        )

        // 送出按鈕時一併儲存所有資料
        TextButton(
            onClick = {
                // 建立完整的使用者資料物件
                val userInfo = Borrow(
                    userName = userName,
                    userTel = userTel,
                    userID = userID,
                    contactName = contactName,
                    contactTel = contactTel,
                    contactMail = contactMail
                )

                // 儲存到 Firebase
                db.collection("info")
                    .document(userName)
                    .set(userInfo)
                    .addOnSuccessListener {
                        // 成功儲存後的操作
                        val intent = Intent(context, Age::class.java)
                        context.startActivity(intent)
                    }
                    .addOnFailureListener { e ->
                        // 處理儲存失敗的情況
                        Toast.makeText(context, "儲存失敗: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            }
        ) {
            Text(text = "下一頁")
        }
    }
}