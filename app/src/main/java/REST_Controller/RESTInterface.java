package REST_Controller;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import apps.gliger.isafe.RealtimeIncident;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RESTInterface {

    @Headers("Content-Type: application/json")
    @POST("open/find/roadsign")
    Call<List<TrafficSign>> getTrafficData(@Body NearbyRequest request);

    @Headers("Content-Type: application/json")
    @POST("open/find/blackspot")
    Call<List<Blackspot>> getBlackspotData(@Body NearbyRequest request);

    @Headers("Content-Type: application/json")
    @POST("open/find/criticalPoint")
    Call<List<CriticalLocation>> getCriticalData(@Body NearbyRequest request);

    @Headers("Content-Type: application/json")
    @POST("open/find/speedLimit")
    Call<List<SpeedLimit>> getSpeedLimitData(@Body NearbyRequest request);

    @Headers("Content-Type: application/json")
    @POST("open/find/recordsonpath")
    Call<StaticIncidentData> getStaticDataSet(@Body List<RouteRequest> routeRequests);

    @Headers("Content-Type: application/json")
    @POST("/user/add/accident")
    Call<Void> sendIncidetDatatoServer(@Header("Authorization") String token, RestRTIncident incident);

    @Headers("Content-Type: application/json")
    @POST("open/create")
    Call<String>newUserRegister(@Body UserRegister userRegister);

    @Headers("Content-Type: application/json")
    @POST("open/token")
    Call<LoginResponse> loginUser(@Body LoginRequest request);

}
