package REST_Controller;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RESTClient {
    private static Retrofit retrofit;
    private static final String baseURL="http://206.189.91.154:8081/";

    public static Retrofit getInstance(){
        if(retrofit==null){
            retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
