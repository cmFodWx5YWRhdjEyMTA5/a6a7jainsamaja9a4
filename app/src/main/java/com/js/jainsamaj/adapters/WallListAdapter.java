package com.js.jainsamaj.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.js.jainsamaj.R;
import com.js.jainsamaj.activities.WebsiteActivity;
import com.js.jainsamaj.global.Constants;
import com.js.jainsamaj.global.Global;
import com.js.jainsamaj.global.Log;
import com.js.jainsamaj.models.Advertisement.ImagesLinkMain;
import com.js.jainsamaj.models.Wall.AdvertisementMain;
import com.js.jainsamaj.models.Wall.WallMain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by arbaz on 6/2/17.
 */
public class WallListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    ArrayList<Object> wallMainArrayList;
    Context context;

    WallMain wallMain;
    AdvertisementMain advertisementMain;
    Typeface tfRegular, tfBold;
    View view;

    String companyUrl;
    List<ImagesLinkMain> imagesLinkMains;
    Random randomGenerator;


    public WallListAdapter(ArrayList<Object> wallMainArrayList, List<ImagesLinkMain> imagesLinkMains, String companyUrl, Context context) {
        this.wallMainArrayList = wallMainArrayList;
        this.imagesLinkMains = imagesLinkMains;
        this.companyUrl = companyUrl;
        this.context = context;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case Constants.WALL_POST_CANDIDATE_INT:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wall_candidate_posting_row, parent, false);
                return new CandidateViewHolder(view);
            case Constants.WALL_POST_EVENT_INT:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wall_event_posting_row, parent, false);
                return new EventViewHolder(view);
            case Constants.WALL_POST_JOB_INT:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wall_job_posting_row, parent, false);
                return new JobPostViewHolder(view);
            case Constants.WALL_POST_PRODUCT_INT:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wall_product_posting_row, parent, false);
                return new ProductViewHolder(view);
            case Constants.WALL_POST_TEMPLE_FINDER_INT:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wall_temple_finder_posting_row, parent, false);
                return new TempleHolder(view);
            //Unknown Post Tpe
            case Constants.WALL_POST_UNKNOWN:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wall_unknown_row, parent, false);
                return new UnknownHolder(view);
            case Constants.WALL_ADVERTISEMENT:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wall_addvertisment_row, parent, false);
                return new AdvertisementHolder(view);

        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        //For Set Font
        tfRegular = Global.setRegularFont(context);
        tfBold = Global.setBoldFont(context);
        Object o = wallMainArrayList.get(position);
        if (o.getClass() == WallMain.class) {
            wallMain = (WallMain) o;
            if (wallMain != null) {
                try {
                    if (wallMain.getPostType().equalsIgnoreCase(Constants.WALL_POST_CANDIDATE)) {
                        //setting font Here
                        ((CandidateViewHolder) holder).tvWCPName.setTypeface(tfBold);
                        ((CandidateViewHolder) holder).tvWCPStatus.setTypeface(tfBold);
                        ((CandidateViewHolder) holder).tvWCPAge.setTypeface(tfBold);
                        ((CandidateViewHolder) holder).tvWCPLiveIn.setTypeface(tfRegular);
                        ((CandidateViewHolder) holder).tvWCPEdu.setTypeface(tfRegular);
                        ((CandidateViewHolder) holder).tvWCPWork.setTypeface(tfRegular);
                        ((CandidateViewHolder) holder).tvWCPHeight.setTypeface(tfRegular);
                        ((CandidateViewHolder) holder).tvWCPWeight.setTypeface(tfRegular);


                        //setting data Here
                        ((CandidateViewHolder) holder).sdWCPImg.setImageURI(Uri.parse(wallMain.getUserProfileImageLink()));
                        ((CandidateViewHolder) holder).tvWCPStatus.setText(wallMain.getMaritalStatus());
                        ((CandidateViewHolder) holder).tvWCPName.setText(wallMain.getUserName());
                        ((CandidateViewHolder) holder).tvWCPAge.setText(String.valueOf(wallMain.getAge()));
                        ((CandidateViewHolder) holder).tvWCPLiveIn.setText(wallMain.getCityLiving().getCityName() + "," + wallMain.getStateLiving().getStateName());
                        ((CandidateViewHolder) holder).tvWCPEdu.setText(wallMain.getEducationLevelDetails() + "(" + wallMain.getEducationFieldDetails() + ")");
                        ((CandidateViewHolder) holder).tvWCPWork.setText(wallMain.getWorkWithStr());
                        //For Height Split
                        String[] tempHeight = wallMain.getHeight().getHeight().split("cm");
                        ((CandidateViewHolder) holder).tvWCPHeight.setText(tempHeight[0]);
                        ((CandidateViewHolder) holder).tvWCPWeight.setText(wallMain.getWeight());

                    }
                    if (wallMain.getPostType().equalsIgnoreCase(Constants.WALL_POST_EVENT)) {
                        //Setting font here
                        ((EventViewHolder) holder).tvWEPTitle.setTypeface(tfBold);
                        ((EventViewHolder) holder).tvWEPDes.setTypeface(tfRegular);
                        ((EventViewHolder) holder).tvWEPLocation.setTypeface(tfRegular);
                        ((EventViewHolder) holder).tvWEPSDate.setTypeface(tfRegular);
                        ((EventViewHolder) holder).tvWEPEDate.setTypeface(tfRegular);

                        //Setting data here
                        // ((EventViewHolder)holder).sdWEPImg.setImageURI(wallMain.getu);
//                    ((EventViewHolder) holder).tvWEPTitle.setText(wallMain.getUserName() + "," + wallMain.getAge());
//                    ((EventViewHolder) holder).tvWJPDes.setText(wallMain.getUserName() + "," + wallMain.getAge());
//                    ((EventViewHolder) holder).tvWEPLocation.setText(wallMain.getUserName() + "," + wallMain.getAge());
//                    ((EventViewHolder) holder).tvWEPSDate.setText(wallMain.getUserName() + "," + wallMain.getAge());
//                    ((EventViewHolder) holder).tvWEPEDate.setText(wallMain.getUserName() + "," + wallMain.getAge());
                    }
                    if (wallMain.getPostType().equalsIgnoreCase(Constants.WALL_POST_JOB)) {
                        //Setting font Here
                        ((JobPostViewHolder) holder).tvWJPName.setTypeface(tfBold);
                        ((JobPostViewHolder) holder).tvWJPRole.setTypeface(tfRegular);
                        ((JobPostViewHolder) holder).tvWJPExp.setTypeface(tfRegular);
                        ((JobPostViewHolder) holder).tvWJPTitle.setTypeface(tfBold);
                        ((JobPostViewHolder) holder).tvWJPDes.setTypeface(tfRegular);
                        ((JobPostViewHolder) holder).tvWJPEdu.setTypeface(tfRegular);
                        ((JobPostViewHolder) holder).tvWJPLoc.setTypeface(tfRegular);
                        ((JobPostViewHolder) holder).tvWJPPackage.setTypeface(tfRegular);
                        ((JobPostViewHolder) holder).tvWJPContact.setTypeface(tfRegular);

                        //Setting data here
                        ((JobPostViewHolder) holder).sdWJPImg.setImageURI(Uri.parse(wallMain.getUploadMediaLink()));
                        ((JobPostViewHolder) holder).tvWJPName.setText(wallMain.getCompanyName());
                        ((JobPostViewHolder) holder).tvWJPExp.setText(wallMain.getExperience());
                        ((JobPostViewHolder) holder).tvWJPRole.setText(wallMain.getJobRole());
                        ((JobPostViewHolder) holder).tvWJPTitle.setText(wallMain.getJobTitle());
                        ((JobPostViewHolder) holder).tvWJPDes.setText(wallMain.getJobDescription());
                        ((JobPostViewHolder) holder).tvWJPEdu.setText(wallMain.getEducationLevelDetails() + " " + wallMain.getEducationFieldDetails());
                        ((JobPostViewHolder) holder).tvWJPLoc.setText(wallMain.getJobLocation());
                        ((JobPostViewHolder) holder).tvWJPPackage.setText(wallMain.getPackageFrom() + "-" + wallMain.getPackageTo());
                        ((JobPostViewHolder) holder).tvWJPContact.setText(wallMain.getContactNumber());
                    }
                    if (wallMain.getPostType().equalsIgnoreCase(Constants.WALL_POST_PRODUCT)) {
                        //Setting font here
                        ((ProductViewHolder) holder).tvWPPCName.setTypeface(tfBold);
                        ((ProductViewHolder) holder).tvWPPCategory.setTypeface(tfRegular);
                        ((ProductViewHolder) holder).tvWPPPService.setTypeface(tfRegular);
                        ((ProductViewHolder) holder).tvWPPTitle.setTypeface(tfBold);
                        ((ProductViewHolder) holder).tvWPPDes.setTypeface(tfRegular);
                        ((ProductViewHolder) holder).tvWPPContact.setTypeface(tfRegular);

                        //setting data Here
                        ((ProductViewHolder) holder).sdWPPImg.setImageURI(Uri.parse(wallMain.getProductServiceImageLink()));

                        ((ProductViewHolder) holder).tvWPPCName.setText(wallMain.getCompanyName());
                        ((ProductViewHolder) holder).tvWPPCategory.setText(wallMain.getProductServiceCategoryDetails());
                        ((ProductViewHolder) holder).tvWPPPService.setText(wallMain.getProductService());
                        ((ProductViewHolder) holder).tvWPPTitle.setText(wallMain.getTitle());
                        ((ProductViewHolder) holder).tvWPPDes.setText(wallMain.getDescription());
                        ((ProductViewHolder) holder).tvWPPContact.setText(wallMain.getContact());
                    }

                    if (wallMain.getPostType().equalsIgnoreCase(Constants.WALL_POST_TEMPLE_FINDER)) {
                        //Setting font here
                        ((TempleHolder) holder).tvWTPTitle.setTypeface(tfBold);
                        ((TempleHolder) holder).tvWTPDes.setTypeface(tfRegular);
                        ((TempleHolder) holder).tvWTPCategory.setTypeface(tfRegular);
                        ((TempleHolder) holder).tvWTPAddress.setTypeface(tfRegular);
                        ((TempleHolder) holder).tvWTPContact.setTypeface(tfRegular);

                        //Setting data here
                        ((TempleHolder) holder).sdWTPImg.setImageURI(Uri.parse(wallMain.getLogoLink().get(0)));
                        ((TempleHolder) holder).tvWTPTitle.setText(wallMain.getTempleName());
                        ((TempleHolder) holder).tvWTPDes.setText(wallMain.getDescription());
                        ((TempleHolder) holder).tvWTPCategory.setText(wallMain.getTempleCategory().getCategoryName());
                        ((TempleHolder) holder).tvWTPAddress.setText(wallMain.getAddress());
                        ((TempleHolder) holder).tvWTPContact.setText(wallMain.getContact());
                    } else {
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (o.getClass() == AdvertisementMain.class) {
            advertisementMain = (AdvertisementMain) o;
            try {
                //For Getting Data from database(Medium Table)
                //Shuffle array list
                //Collections.shuffle(imagesLinkMains);
                String ImageLink = String.valueOf(advertisementMain.getMediumImageUrl());
                byte[] decodedString = Base64.decode(ImageLink, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                //set Banner Image Here
//                ((AdvertisementHolder) holder).sdWAPImg.setImageBitmap(decodedByte);

                ((AdvertisementHolder) holder).tvWAPCName.setTypeface(tfBold);
                ((AdvertisementHolder) holder).tvWAPEmail.setTypeface(tfRegular);
                ((AdvertisementHolder) holder).tvWAPTitle.setTypeface(tfBold);
                ((AdvertisementHolder) holder).tvWAPDes.setTypeface(tfRegular);
                ((AdvertisementHolder) holder).tvWAPContact.setTypeface(tfRegular);
                ((AdvertisementHolder) holder).tvWAPAddress.setTypeface(tfRegular);

                //Setting data here
                ((AdvertisementHolder) holder).sdWAPImg.setImageBitmap(decodedByte);
                ((AdvertisementHolder) holder).sdWAPImg.setOnClickListener(this);
                ((AdvertisementHolder) holder).sdWAPImg.setTag(advertisementMain.getCompanyImageUrl());

                ((AdvertisementHolder) holder).tvWAPCName.setText(advertisementMain.getCompanyName());
                ((AdvertisementHolder) holder).tvWAPEmail.setText(advertisementMain.getCompanyEmail());
                ((AdvertisementHolder) holder).tvWAPTitle.setText(advertisementMain.getTitle());
                ((AdvertisementHolder) holder).tvWAPDes.setText(advertisementMain.getDescription());
                ((AdvertisementHolder) holder).tvWAPContact.setText(advertisementMain.getCompanyName());


            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    @Override
    public int getItemViewType(int position) {


      /*  if (position == 4 || position == 8 || position == 12 || position == 16) {
            return Constants.WALL_ADVERTISEMENT;
        }*/
        Object o = wallMainArrayList.get(position);
        if (o.getClass() == WallMain.class) {
            if (wallMainArrayList != null) {
                WallMain wallMain = (WallMain) o;
                if (wallMain != null) {
                    if (wallMain.getPostType().equalsIgnoreCase(Constants.WALL_POST_CANDIDATE)) {
                        return Constants.WALL_POST_CANDIDATE_INT;
                    }
                    if (wallMain.getPostType().equalsIgnoreCase(Constants.WALL_POST_EVENT)) {
                        return Constants.WALL_POST_EVENT_INT;
                    }
                    if (wallMain.getPostType().equalsIgnoreCase(Constants.WALL_POST_JOB)) {
                        return Constants.WALL_POST_JOB_INT;
                    }
                    if (wallMain.getPostType().equalsIgnoreCase(Constants.WALL_POST_PRODUCT)) {
                        return Constants.WALL_POST_PRODUCT_INT;
                    }
                    if (wallMain.getPostType().equalsIgnoreCase(Constants.WALL_POST_TEMPLE_FINDER)) {
                        return Constants.WALL_POST_TEMPLE_FINDER_INT;
                    } else {
                        return Constants.WALL_POST_UNKNOWN;
                    }
                }
            }
        } else if (o.getClass() == AdvertisementMain.class) {
            Log.e("Advertisement data=" + o.getClass());
            return Constants.WALL_ADVERTISEMENT;
        }
        return 0;
    }


    @Override
    public int getItemCount() {
        return wallMainArrayList.size();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sdWAPImg:
                companyUrl = (String) v.getTag();
                if (companyUrl != null) {
                    Intent iCompanyUrl = new Intent(context, WebsiteActivity.class);
                    iCompanyUrl.putExtra("CompanyUrl", companyUrl);
                    context.startActivity(iCompanyUrl);
                }
                break;
            default:
                break;

        }
    }

    public static class CandidateViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView sdWCPImg, sdCupid;
        TextView tvWCPStatus, tvWCPName, tvWCPAge, tvWCPLiveIn, tvWCPEdu, tvWCPWork, tvWCPHeight, tvWCPWeight;

        public CandidateViewHolder(View itemView) {
            super(itemView);
            sdCupid = (SimpleDraweeView) itemView.findViewById(R.id.sdCupid);
            sdCupid.setImageURI(Uri.parse("res:///" + R.drawable.ic_cupid));
            sdWCPImg = (SimpleDraweeView) itemView.findViewById(R.id.sdWCPImg);

            tvWCPStatus = (TextView) itemView.findViewById(R.id.tvWCPStatus);
            tvWCPName = (TextView) itemView.findViewById(R.id.tvWCPName);
            tvWCPAge = (TextView) itemView.findViewById(R.id.tvWCPAge);
            tvWCPLiveIn = (TextView) itemView.findViewById(R.id.tvWCPLiveIn);
            tvWCPEdu = (TextView) itemView.findViewById(R.id.tvWCPEdu);
            tvWCPWork = (TextView) itemView.findViewById(R.id.tvWCPWork);
            tvWCPHeight = (TextView) itemView.findViewById(R.id.tvWCPHeight);
            tvWCPWeight = (TextView) itemView.findViewById(R.id.tvWCPWeight);

        }
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        TextView tvWEPTitle, tvWEPDes, tvWEPLocation, tvWEPSDate, tvWEPEDate;
        SimpleDraweeView sdWEPImg;

        public EventViewHolder(View itemView) {
            super(itemView);

            sdWEPImg = (SimpleDraweeView) itemView.findViewById(R.id.sdWEPImg);

            tvWEPTitle = (TextView) itemView.findViewById(R.id.tvWEPTitle);
            tvWEPDes = (TextView) itemView.findViewById(R.id.tvWEPDes);
            tvWEPLocation = (TextView) itemView.findViewById(R.id.tvWEPLocation);
            tvWEPSDate = (TextView) itemView.findViewById(R.id.tvWEPSDate);
            tvWEPEDate = (TextView) itemView.findViewById(R.id.tvWEPEDate);
        }
    }

    public static class JobPostViewHolder extends RecyclerView.ViewHolder {
        TextView tvWJPName, tvWJPExp, tvWJPRole, tvWJPTitle, tvWJPDes, tvWJPEdu, tvWJPLoc, tvWJPPackage, tvWJPContact;
        SimpleDraweeView sdWJPImg;

        public JobPostViewHolder(View itemView) {
            super(itemView);
            sdWJPImg = (SimpleDraweeView) itemView.findViewById(R.id.sdWJPImg);

            tvWJPName = (TextView) itemView.findViewById(R.id.tvWJPName);
            tvWJPExp = (TextView) itemView.findViewById(R.id.tvWJPExp);
            tvWJPRole = (TextView) itemView.findViewById(R.id.tvWJPRole);
            tvWJPTitle = (TextView) itemView.findViewById(R.id.tvWJPTitle);
            tvWJPDes = (TextView) itemView.findViewById(R.id.tvWJPDes);
            tvWJPEdu = (TextView) itemView.findViewById(R.id.tvWJPEdu);
            tvWJPLoc = (TextView) itemView.findViewById(R.id.tvWJPLoc);
            tvWJPPackage = (TextView) itemView.findViewById(R.id.tvWJPPackage);
            tvWJPContact = (TextView) itemView.findViewById(R.id.tvWJPContact);
        }
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView sdWPPImg;
        private TextView tvWPPCName, tvWPPCategory, tvWPPPService, tvWPPTitle, tvWPPDes, tvWPPContact;

        public ProductViewHolder(View itemView) {
            super(itemView);
            sdWPPImg = (SimpleDraweeView) itemView.findViewById(R.id.sdWPPImg);


            tvWPPCName = (TextView) itemView.findViewById(R.id.tvWPPCName);
            tvWPPCategory = (TextView) itemView.findViewById(R.id.tvWPPCategory);
            tvWPPPService = (TextView) itemView.findViewById(R.id.tvWPPPService);
            tvWPPTitle = (TextView) itemView.findViewById(R.id.tvWPPTitle);
            tvWPPDes = (TextView) itemView.findViewById(R.id.tvWPPDes);
            tvWPPContact = (TextView) itemView.findViewById(R.id.tvWPPContact);
        }
    }

    public static class AdvertisementHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView sdWAPImg;
        TextView tvWAPCName, tvWAPEmail, tvWAPTitle, tvWAPDes, tvWAPContact, tvWAPAddress;

        public AdvertisementHolder(View itemView) {
            super(itemView);
            sdWAPImg = (SimpleDraweeView) itemView.findViewById(R.id.sdWAPImg);
            tvWAPCName = (TextView) itemView.findViewById(R.id.tvWAPCName);
            tvWAPEmail = (TextView) itemView.findViewById(R.id.tvWAPEmail);
            tvWAPTitle = (TextView) itemView.findViewById(R.id.tvWAPTitle);
            tvWAPDes = (TextView) itemView.findViewById(R.id.tvWAPDes);
            tvWAPContact = (TextView) itemView.findViewById(R.id.tvWAPContact);
            tvWAPAddress = (TextView) itemView.findViewById(R.id.tvWAPAddress);


        }
    }

    public static class TempleHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView sdWTPImg;
        TextView tvWTPTitle, tvWTPDes, tvWTPCategory, tvWTPAddress, tvWTPContact;

        public TempleHolder(View itemView) {
            super(itemView);
            sdWTPImg = (SimpleDraweeView) itemView.findViewById(R.id.sdWTPImg);
            tvWTPTitle = (TextView) itemView.findViewById(R.id.tvWTPTitle);
            tvWTPDes = (TextView) itemView.findViewById(R.id.tvWTPDes);
            tvWTPCategory = (TextView) itemView.findViewById(R.id.tvWTPCategory);
            tvWTPAddress = (TextView) itemView.findViewById(R.id.tvWTPAddress);
            tvWTPContact = (TextView) itemView.findViewById(R.id.tvWTPContact);


        }
    }

    public static class UnknownHolder extends RecyclerView.ViewHolder {

        public UnknownHolder(View itemView) {
            super(itemView);
        }
    }


}
