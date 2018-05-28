package com.ats.exhibitionvisitor.util;

import com.ats.exhibitionvisitor.model.CityModel;
import com.ats.exhibitionvisitor.model.CompanyTypeModel;
import com.ats.exhibitionvisitor.model.EventGalleryModel;
import com.ats.exhibitionvisitor.model.EventListModel;
import com.ats.exhibitionvisitor.model.EventModel;
import com.ats.exhibitionvisitor.model.EventSubscribeModel;
import com.ats.exhibitionvisitor.model.ExhibitorLikeStatusModel;
import com.ats.exhibitionvisitor.model.ExhibitorListModel;
import com.ats.exhibitionvisitor.model.ExhibitorModel;
import com.ats.exhibitionvisitor.model.FloorMapModel;
import com.ats.exhibitionvisitor.model.LoginModel;
import com.ats.exhibitionvisitor.model.MaterialModel;
import com.ats.exhibitionvisitor.model.ProductByLikedStatus;
import com.ats.exhibitionvisitor.model.ProductModel;
import com.ats.exhibitionvisitor.model.SponsorModel;
import com.ats.exhibitionvisitor.model.VisitorModel;
import com.ats.exhibitionvisitor.model.VisitorProduct;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface InterfaceApi {

    @GET("getAllLocationByIsUsed")
    Call<ArrayList<CityModel>> getAllCities();

    @GET("getAllCompaniesByIsUsed")
    Call<ArrayList<CompanyTypeModel>> getAllCompanyType();

    @POST("saveVisitor")
    Call<VisitorModel> saveVisitor(@Body VisitorModel model);

    @POST("visitorLogin")
    Call<LoginModel> doLogin(@Query("visitorMobile") String visitorMobile);

    @POST("getEventsList")
    Call<ArrayList<EventListModel>> getEventList(@Query("companyTypeIdList") ArrayList<Integer> companyTypeIdList, @Query("locationIdList") ArrayList<Integer> locationIdList, @Query("visitorId") int visitorId, @Query("isCompany") int isCompany, @Query("isLocation") int isLocation);

    @POST("updateEventSubStatus")
    Call<EventSubscribeModel> updateSubscribeStatus(@Query("visitorId") int visitorId, @Query("eventId") int eventId, @Query("subscribeStatus") int subscribeStatus);

    @POST("getAllEventsInfoByEventId")
    Call<EventModel> getEventInfo(@Query("eventId") int eventId);

    @POST("getExhCountByEventId")
    Call<Integer> getEventExhibitorCount(@Query("eventId") int eventId);

    @POST("getAllSponsorByEventId")
    Call<ArrayList<SponsorModel>> getEventSponsorList(@Query("eventId") int eventId);

    @POST("getAllFloarMapByEventId")
    Call<ArrayList<FloorMapModel>> getEventFloorMaap(@Query("eventId") int eventId);

    @POST("getAllPhotoByEventId")
    Call<ArrayList<EventGalleryModel>> getEventGallery(@Query("eventId") int eventId);

    @POST("getAllExhListByEventIdAndVisitorId")
    Call<ArrayList<ExhibitorListModel>> getExhibitorList(@Query("visitorId") int visitorId, @Query("eventId") int eventId, @Query("companyTypeId") ArrayList<Integer> companyTypeId, @Query("status") int status);

    @POST("getExhibitorByExhId")
    Call<ExhibitorModel> getExhibitorInfo(@Query("exhId") int exhId);

    @POST("updateExhibitorLikeStatus")
    Call<ExhibitorLikeStatusModel> updateExhibitorLikeStatus(@Query("visitorId") int visitorId, @Query("exhibitorId") int exhibitorId, @Query("eventId") int eventId, @Query("likeStatus") int likeStatus);

    @POST("getAllProductsByExId")
    Call<ArrayList<ProductModel>> getExhibitorProducts(@Query("exhId") int exhId);

    @POST("getAllProductListByVisIdAndExhId")
    Call<ArrayList<ProductByLikedStatus>> getExhibitorProductsByLikeStatus(@Query("exhibitorId") int exhibitorId, @Query("visitorId") int visitorId);

    @POST("getAllMaterialByExhIdAndIsUsed")
    Call<ArrayList<MaterialModel>> getExhibitorPortfolio(@Query("exhId") int exhId);

    @POST("getAllExhibitorsByVisitorIdAndLikeStatus")
    Call<ArrayList<ExhibitorModel>> getLikedExhibitor(@Query("visitorId") int visitorId);

    @POST("updateProductLikeStatus")
    Call<VisitorProduct> updateProductLikeStatus(@Query("visitorId") int visitorId, @Query("exhibitorId") int exhibitorId, @Query("productId") int productId, @Query("eventId") int eventId, @Query("likeStatus") int likeStatus);

    @POST("getAllProductListByVisId")
    Call<ArrayList<ProductModel>> getLikedProducts(@Query("visitorId") int visitorId);

    @POST("getAllEventsWithVisitorId")
    Call<ArrayList<EventModel>> getSubscribedEvents(@Query("visitorId") int visitorId);


}
