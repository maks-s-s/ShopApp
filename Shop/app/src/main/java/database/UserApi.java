package database;

import androidx.room.Delete;

import java.util.List;

import Chat.Abuse;
import Chat.message;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface UserApi {

    @POST("/users/register")
    Call<RDBUser> createUser(@Body RDBUser rdbUser);

    @GET("/users")
    Call<List<RDBUser>> getUsers();

    @GET("/users/getPasswordByEmail")
    Call<String> getPasswordByEmail(@Query("email") String email);

    @GET("/users/countUsersWithEmail")
    Call<Integer> countUsersWithEmail(@Query("email") String email);

    @PUT("/users/changePassword")
    Call<Boolean> changePassword(@Query("email") String email, @Query("newPassword") String newPassword);

    @GET("/users/getUserNameByEmail")
    Call<String> getUserNameByEmail(@Query("email") String email);

    @GET("/users/getNameColorByEmail")
    Call<String> getNameColorByEmail(@Query("email") String email);

    @PUT("/users/setNameColorByEmail")
    Call<Boolean> setNameColorByEmail(@Query("email") String email, @Query("newNameColor") String newNameColor);

    @GET("/users/getTagByEmail")
    Call<String> getTagByEmail(@Query("email") String email);

    @PUT("/users/setTagByEmail")
    Call<Boolean> setTagByEmail(@Query("email") String email, @Query("newTag") String newTag);

    @GET("/users/getTagColorByEmail")
    Call<String> getTagColorByEmail(@Query("email") String email);

    @PUT("/users/setTagColorByEmail")
    Call<Boolean> setTagColorByEmail(@Query("email") String email, @Query("newTagColor") String newTagColor);

    @GET("/users/getPermissionByEmail")
    Call<String> getPermissionByEmail(@Query("email") String email);

    @PUT("/users/setPermissionByEmail")
    Call<Boolean> setPermissionByEmail(@Query("email") String email, @Query("newPermission") String newPermission);

    @GET("/users/getUserByEmail")
    Call<RDBUser> getUserByEmail(@Query("email") String email);

    @GET("/message/getAllMessages")
    Call<List<message>> getAllMessages();

    @POST("/message/addNewMessage")
    Call<message> addNewMessage (@Body message message);

    @DELETE("/message/clearChat")
    Call<Void> clearChat();

    @PUT("/message/deleteMessage")
    Call<Void> deleteMessage(@Query("id") long id, @Query("ChangerEmail") String ChangerEmail);

    @PUT("/message/editMesage")
    Call<Void> editMesage(@Query("id") long id, @Query("ChangerEmail") String ChangerEmail, @Query("newText") String newText);
    @GET("/message/getIdForNextMessage")
    Call<Long> getIdForNextMessage();

    @PUT("/message/setUnChanged")
    Call<Void> setUnChanged(@Query("id") long id);

    @PUT("/message/setChanged")
    Call<Void> setChanged(@Query("id") long id);

    @PUT("/message/setPinned")
    Call<Void> setPinned(@Query("id") long id);

    @GET("/message/isPinnedById")
    Call<Boolean> isPinnedById(@Query("id") long id);

    @PUT("/message/setUnPinned")
    Call<Void> setUnPinned(@Query("id") long id);

    @GET("/message/getMessageById")
    Call<message> getMessageById(@Query("id") long id);

    @POST("/abuse/addNewAbuse")
    Call<String> addNewAbuse (@Body Abuse abuse);

    @PUT("/message/setAbused")
    Call<Void> setAbused(@Query("id") long id);

    @GET("/abuse/getAllAbuses")
    Call<List<Abuse>> getAllAbusesByMessageId (@Query("id") long id);

    @DELETE("/abuse/clearAbuses")
    Call<Void> clearAbuses();

    @DELETE("/abuse/deleteAbuse")
    Call<Void> deleteAbuse(@Query("id") long id);

    @PUT("/users/muteUser")
    Call<Void> muteUser(@Query("email") String email, @Query("time") long time);

    @GET("/users/getDateOfMute")
    Call<String> getDateOfMute(@Query("email") String email);

}
