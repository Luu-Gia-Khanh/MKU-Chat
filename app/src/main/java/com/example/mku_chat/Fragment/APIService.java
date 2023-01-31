package com.example.mku_chat.Fragment;

import com.example.mku_chat.Notification.MyResponse;
import com.example.mku_chat.Notification.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers({
            "Content-Type:application/json",
            "Authorization:key=AAAAqIiGtgo:APA91bFRGj1ciGATSAk9EOVroBTRkJmmLM_yapWp1SXyMRorGKVm47XpgVxJKpYR8f1f1CXgleg4kIOVrJbNcp-irz2aHk2UhJVTYfM4K-DIpYcIXgPnQmHfNhslKWMeSM8rcl3gpBi5"
    })

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
