package REST_Controller;

import java.util.ArrayList;
import java.util.List;

import apps.gliger.isafe.RealtimeIncident;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
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


}
