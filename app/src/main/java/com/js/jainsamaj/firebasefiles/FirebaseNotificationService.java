package com.js.jainsamaj.firebasefiles;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.Map;

import com.js.jainsamaj.global.Global;

/**
 * Created by arbaz on 10/2/17.
 */
public class FirebaseNotificationService extends FirebaseMessagingService {
    private static final String TAG = "JainSamajFirebase";
    String message = "", body = "";
//    PlayersLiveGameList playersLiveGameList;
    Gson gson = new Gson();
    String loginJson;
//    UserResponse userResponse;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //Displaying data in log
        //It is optional
        //Log.d(TAG, "From: " + remoteMessage.getFrom());
        //Log.d(TAG, "Notification Message Body: " + remoteMessage.getData());
        //Calling method to generate notification

        loginJson = Global.getPreference("UserResponse", "");
      //  userResponse = gson.fromJson(loginJson.toString(), UserResponse.class);
        String str = "" + remoteMessage.getData().get("0");
        try {
            // JSONObject jsonObject = new JSONObject(str).getJSONObject("data");
            // String title = jsonObject.getString("title");
            //String message = jsonObject.getString("message");

            Map<String, String> params = remoteMessage.getData();
            JSONObject object = new JSONObject(params);
            if (object != null) {
                if (object.has("message")) {
                    message = (String) object.get("message");
                }
                if (object.has("body")) {
                    body = (String) object.get("body");
                   // playersLiveGameList = gson.fromJson(body, PlayersLiveGameList.class);
                }
            }
           // Log.e("JSON_OBJECT" + object.toString());
            //sendNotification(object.getString("default"),object.getString("default"));
            //sendNotification(message, playersLiveGameList.getTitle());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //This method is only generating push notification
    //It is same as we did in earlier posts
    private void sendNotification(String title, String messageBody) {
      /*  Intent intent;
        if (userResponse.getUserType() == (Constants.CONST_PLAYER)) {
            intent = new Intent(this, GameDetailActivity.class);
            intent.putExtra("GameDetails", playersLiveGameList);
            intent.putExtra("FromNotification", true);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        } else {
            intent = new Intent(this, PostedGameDetailActivity.class);
            intent.putExtra("GameDetails", playersLiveGameList);
            intent.putExtra("FromNotification", true);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());*/
    }
  /*  private int getNotificationIcon() {
        boolean useWhiteIcon = (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP);
        return useWhiteIcon ? R.drawable.ic_profile: R.mipmap.ic_launcher;
    }*/

}
