import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface OpenLibraryService {
    @GET("isbn/{isbn}.json")
    fun getBookByISBN(@Path("isbn") isbn: String): Call<OpenLibraryResponse>
}