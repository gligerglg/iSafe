package REST_Controller;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RESTInterface {

    @Headers("Content-Type: application/json")
    @POST("open/find/roadsign")
    Call<List<Traffic>> getTrafficData(@Body Request request);
}
