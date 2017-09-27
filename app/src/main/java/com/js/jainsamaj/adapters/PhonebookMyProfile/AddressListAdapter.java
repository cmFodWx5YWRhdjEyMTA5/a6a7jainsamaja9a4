package com.js.jainsamaj.adapters.PhonebookMyProfile;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.js.jainsamaj.R;
import com.js.jainsamaj.activities.SelectionActivity;
import com.js.jainsamaj.global.AppDialog;
import com.js.jainsamaj.global.Global;
import com.js.jainsamaj.global.Log;
import com.js.jainsamaj.models.Phonebook.Response.AddressItem;
import com.js.jainsamaj.models.SelectionListMain;
import com.js.jainsamaj.models.Temple.searchTemple.Response.AreaId;
import com.js.jainsamaj.models.Temple.searchTemple.Response.CityId;
import com.js.jainsamaj.models.Temple.searchTemple.Response.CountryId;
import com.js.jainsamaj.models.Temple.searchTemple.Response.DistrictId;
import com.js.jainsamaj.models.Temple.searchTemple.Response.StateId;
import com.js.jainsamaj.utils.Location.GeocodingLocation;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

/**
 * Created by arbaz on 7/3/17.
 */

public class AddressListAdapter extends RecyclerView.Adapter<AddressListAdapter.ViewHolder> {
    public static final int REQUEST_TEMPLE_COUNTRY = 9901;
    public static final int REQUEST_TEMPLE_STATE = 9902;
    public static final int REQUEST_TEMPLE_DISTRICT = 9903;
    public static final int REQUEST_TEMPLE_CITY = 9904;
    public static final int REQUEST_TEMPLE_AREA = 9905;
    ArrayList<AddressItem> addressStringArrayList;
    Context context;
    String addressStr;
    Typeface tfRegular, tfBold;
    //private AddressItem addressItem;
    //result
    ArrayList<SelectionListMain> selectionListMainArrayList = new ArrayList<>();

    //All ids and Name
    String CountryName, StateName, DistrictName, CityName, AreaName;
    String TempleCountryId, TempleStateId, TempleDistrictId, TempleCityId, TempleAreaId;
    String tempTempleCountryId, tempTempleStateId, tempTempleDistrictId, tempTempleCityId, tempTempleAreaId;

    //For Setting tag
    int currentPosition;
    int etPosition;

    //For lat lang
    String[] tempLatLog;
    String lat;
    String lang;

    String CurrentLatStr, CurrentLangStr;

    public AddressListAdapter(ArrayList<AddressItem> addressStringArrayList, Context context, String CurrentLatStr, String CurrentLangStr) {
        this.addressStringArrayList = addressStringArrayList;
        this.context = context;
        this.CurrentLatStr = CurrentLatStr;
        this.CurrentLangStr = CurrentLangStr;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.add_address_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        //For Remove Row
        holder.ivAddressRemove.setTag(position);
        holder.ivAddressRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = (int) holder.ivAddressRemove.getTag();
                addressStringArrayList.remove(pos);
                notifyItemRemoved(pos);
                notifyItemRangeChanged(pos, addressStringArrayList.size());

            }
        });
        AddressItem addressItem = addressStringArrayList.get(position);
        tfRegular = Global.setRegularFont(context);
        tfBold = Global.setBoldFont(context);
        try {
            holder.tvMPaddress.setTypeface(tfBold);
            holder.etMPStreet.setTypeface(tfRegular);
            holder.etMPCountry.setTypeface(tfRegular);
            holder.etMPState.setTypeface(tfRegular);
            holder.etMPDistrict.setTypeface(tfRegular);
            holder.etMPCity.setTypeface(tfRegular);
            holder.etMPArea.setTypeface(tfRegular);
            holder.etMPZip.setTypeface(tfRegular);
            holder.etMPAddressType.setTypeface(tfRegular);


            holder.etMPCountry.setTag(position);
            holder.etMPState.setTag(position);
            holder.etMPDistrict.setTag(position);
            holder.etMPCity.setTag(position);
            holder.etMPArea.setTag(position);

            holder.etMPAddressType.setText("primary");


            int addPos = position + 1;
            holder.tvMPaddress.setText("Address "
                    + addPos);
            //For Street
            CustomTextWatcherStreet oldCustomTextWatcherStreet = (CustomTextWatcherStreet) holder.etMPStreet.getTag();
            if (oldCustomTextWatcherStreet != null)
                holder.etMPStreet.removeTextChangedListener(oldCustomTextWatcherStreet);

            CustomTextWatcherStreet customTextWatcherStreet = new CustomTextWatcherStreet(position);
            holder.etMPStreet.setTag(customTextWatcherStreet);
            holder.etMPStreet.addTextChangedListener(customTextWatcherStreet);

            //For Address Type
            CustomTextWatcherAddressType oldcustomTextWatcherAddressType = (CustomTextWatcherAddressType) holder.etMPAddressType.getTag();
            if (oldcustomTextWatcherAddressType != null)
                holder.etMPAddressType.removeTextChangedListener(oldcustomTextWatcherAddressType);

            CustomTextWatcherAddressType customTextWatcherAddressType = new CustomTextWatcherAddressType(position);
            holder.etMPAddressType.setTag(customTextWatcherAddressType);
            holder.etMPAddressType.addTextChangedListener(customTextWatcherAddressType);


            //For Zip
            CustomTextWatcherZip oldcustomTextWatcherZip = (CustomTextWatcherZip) holder.etMPZip.getTag();
            if (oldcustomTextWatcherAddressType != null)
                holder.etMPZip.removeTextChangedListener(oldcustomTextWatcherZip);

            CustomTextWatcherZip customTextWatcherZip = new CustomTextWatcherZip(position);
            holder.etMPZip.setTag(customTextWatcherZip);
            holder.etMPZip.addTextChangedListener(customTextWatcherZip);


            if (addressItem != null) {


                holder.etMPStreet.setText(addressItem.getAddress());
                holder.etMPCountry.setText(addressItem.getCountryId().getCountryName());
                holder.etMPState.setText(addressItem.getStateId().getStateName());
                holder.etMPDistrict.setText(addressItem.getDistrictId().getDistrictName());
                holder.etMPCity.setText(addressItem.getCityId().getCityName());
                holder.etMPArea.setText(addressItem.getAreaId().getAreaName());
                holder.etMPZip.setText(addressItem.getPincode());
                holder.etMPAddressType.setText(addressItem.getAddressType());
                if (addressItem.getId() == 0) {
                    holder.ivAddressRemove.setVisibility(View.VISIBLE);
                } else {
                    holder.ivAddressRemove.setVisibility(View.GONE);
                }


            }


            //For Getting Lat-Lang
            holder.ivAddressGlob.setTag(position);
            holder.ivAddressGlob.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int globPosition = (int) holder.ivAddressGlob.getTag();
                    String streetStr = addressStringArrayList.get(globPosition).getAddress();
                    String countryStr = addressStringArrayList.get(globPosition).getCountryId().getCountryName();
                    String stateStr = addressStringArrayList.get(globPosition).getStateId().getStateName();
                    String districtStr = addressStringArrayList.get(globPosition).getDistrictId().getDistrictName();
                    String cityStr = addressStringArrayList.get(globPosition).getCountryId().getCountryName();
                    String areaStr = addressStringArrayList.get(globPosition).getAreaId().getAreaName();
                    String pinStr = addressStringArrayList.get(globPosition).getPincode();
                    String MainAddress = streetStr + "," + countryStr + "," + stateStr + "," + districtStr + "," + cityStr + "," + areaStr + "," + pinStr;

                    if ((addressStringArrayList.get(globPosition).getLat().equals("") || addressStringArrayList.get(globPosition).getLat().equals("0.0")) ||
                            (addressStringArrayList.get(globPosition).getLang().equals("") || addressStringArrayList.get(globPosition).getLang().equals("0.0"))) {
                        GeocodingLocation.getAddressFromLocation(MainAddress, context, new GeocoderHandler());
                    } else {
                        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                                Uri.parse("http://maps.google.com/maps?q=" + MainAddress));
                        context.startActivity(intent);
                    }
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        return addressStringArrayList.size();
    }


    //Add record
    public void addItem(AddressItem addressItem1) {
//        addressStringArrayList.add(addressItem1);
        notifyItemInserted(addressStringArrayList.size());
    }

    //remove record
    public void removeItem(int position) {
        addressStringArrayList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, addressStringArrayList.size());
    }


    /***************************************
     * onActivity Result
     ************************************/


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            /******************************Result For Country**************************************/
            if (requestCode == REQUEST_TEMPLE_COUNTRY && resultCode == RESULT_OK) {
                selectionListMainArrayList = (ArrayList<SelectionListMain>) data.getSerializableExtra("SelectionList");

                String name = "";
                String id;
                CountryId countryId;
                for (int i = 0; i < selectionListMainArrayList.size(); i++) {
                    name = selectionListMainArrayList.get(i).getCountryName();
                    id = String.valueOf(selectionListMainArrayList.get(i).getId());
                    TempleCountryId = id;
                }

                CountryName = name;
                countryId = new CountryId(selectionListMainArrayList.get(0).getId(), selectionListMainArrayList.get(0).getCountryName());
                addressStringArrayList.get(currentPosition).setCountryId(countryId);
                notifyDataSetChanged();

            }
            /*****************************Result For State ***************************************/
            if (requestCode == REQUEST_TEMPLE_STATE && resultCode == RESULT_OK) {
                selectionListMainArrayList = (ArrayList<SelectionListMain>) data.getSerializableExtra("SelectionList");
                StateId stateId;

                String name = "";
                String id;
                for (int i = 0; i < selectionListMainArrayList.size(); i++) {
                    name = selectionListMainArrayList.get(i).getStateName();
                    id = String.valueOf(selectionListMainArrayList.get(i).getId());
                    TempleStateId = id;

                }
                StateName = name;
                stateId = new StateId(selectionListMainArrayList.get(0).getId(), selectionListMainArrayList.get(0).getStateName());
                addressStringArrayList.get(currentPosition).setStateId(stateId);
                notifyDataSetChanged();

            }
            /*****************************Result For District ************************************/
            if (requestCode == REQUEST_TEMPLE_DISTRICT && resultCode == RESULT_OK) {
                selectionListMainArrayList = (ArrayList<SelectionListMain>) data.getSerializableExtra("SelectionList");
                DistrictId districtId;
                String name = "";
                String id;
                for (int i = 0; i < selectionListMainArrayList.size(); i++) {
                    name = selectionListMainArrayList.get(i).getDistrictName();
                    id = String.valueOf(selectionListMainArrayList.get(i).getId());
                    TempleDistrictId = id;
                }
                DistrictName = name;
                districtId = new DistrictId(selectionListMainArrayList.get(0).getId(), selectionListMainArrayList.get(0).getDistrictName());
                addressStringArrayList.get(currentPosition).setDistrictId(districtId);
                notifyDataSetChanged();
            }
            /*****************************Result For City ****************************************/
            if (requestCode == REQUEST_TEMPLE_CITY && resultCode == RESULT_OK) {
                selectionListMainArrayList = (ArrayList<SelectionListMain>) data.getSerializableExtra("SelectionList");
                CityId cityId;
                String name = "";
                String id;
                for (int i = 0; i < selectionListMainArrayList.size(); i++) {
                    name = selectionListMainArrayList.get(i).getCityName();
                    id = String.valueOf(selectionListMainArrayList.get(i).getId());
                    TempleCityId = id;

                }
                CityName = name;
                cityId = new CityId(selectionListMainArrayList.get(0).getId(), selectionListMainArrayList.get(0).getCityName());
                addressStringArrayList.get(currentPosition).setCityId(cityId);
                notifyDataSetChanged();
            }
            /*****************************Result For Area ****************************************/
            if (requestCode == REQUEST_TEMPLE_AREA && resultCode == RESULT_OK) {
                selectionListMainArrayList = (ArrayList<SelectionListMain>) data.getSerializableExtra("SelectionList");
                AreaId areaId;
                String name = "";
                String id;
                for (int i = 0; i < selectionListMainArrayList.size(); i++) {
                    name = selectionListMainArrayList.get(i).getAreaName();
                    id = String.valueOf(selectionListMainArrayList.get(i).getId());
                    TempleAreaId = id;
                }
                AreaName = name;
                areaId = new AreaId(selectionListMainArrayList.get(0).getId(), selectionListMainArrayList.get(0).getAreaName());
                addressStringArrayList.get(currentPosition).setAreaId(areaId);
                notifyDataSetChanged();
            }
        }

    }

    public ArrayList<AddressItem> multipleAddress() {
        //set default coz of client told
        return (addressStringArrayList);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        EditText etMPStreet;
        EditText etMPCountry;
        EditText etMPState;
        EditText etMPDistrict;
        EditText etMPCity;
        EditText etMPArea;
        EditText etMPZip;
        EditText etMPAddressType;

        TextView tvMPaddress;
        ImageView ivAddressRemove,
                ivAddressGlob;

        public ViewHolder(View itemView) {
            super(itemView);

            tvMPaddress = (TextView) itemView.findViewById(R.id.tvMPaddress);
            etMPStreet = (EditText) itemView.findViewById(R.id.etMPStreet);
            etMPCountry = (EditText) itemView.findViewById(R.id.etMPCountry);
            etMPState = (EditText) itemView.findViewById(R.id.etMPState);
            etMPDistrict = (EditText) itemView.findViewById(R.id.etMPDistrict);
            etMPCity = (EditText) itemView.findViewById(R.id.etMPCity);
            etMPArea = (EditText) itemView.findViewById(R.id.etMPArea);
            etMPZip = (EditText) itemView.findViewById(R.id.etMPZip);
            etMPAddressType = (EditText) itemView.findViewById(R.id.etMPAddressType);

            ivAddressRemove = (ImageView) itemView.findViewById(R.id.ivAddressRemove);
            ivAddressGlob = (ImageView) itemView.findViewById(R.id.ivAddressGlob);

            etMPCountry.setOnClickListener(this);
            etMPState.setOnClickListener(this);
            etMPDistrict.setOnClickListener(this);
            etMPCity.setOnClickListener(this);
            etMPArea.setOnClickListener(this);


//            etMPAddressType.addTextChangedListener(new CustomTextWatcherAddressType(etMPAddressType));
//            etMPZip.addTextChangedListener(new CustomTextWatcherZip(etMPZip));


        }

        //click Here
        @Override
        public void onClick(View v) {
            try {
                switch (v.getId()) {
                    case R.id.etMPCountry:
                        Intent iTCounty = new Intent(context, SelectionActivity.class);
                        iTCounty.putExtra("isTCountry", true);
                        ((Activity) context).startActivityForResult(iTCounty, REQUEST_TEMPLE_COUNTRY);
                        currentPosition = (int) v.getTag();
                        break;
                    case R.id.etMPState:
                        if (TempleCountryId != null) {
                            int id = Integer.parseInt(TempleCountryId);
                            Intent iTState = new Intent(context, SelectionActivity.class);
                            iTState.putExtra("isTState", true);
                            iTState.putExtra("country", id);
                            ((Activity) context).startActivityForResult(iTState, REQUEST_TEMPLE_STATE);
                            currentPosition = (int) v.getTag();
                        } else {
                            AppDialog.showAlertDialog(context, null, context.getResources().getString(R.string.add_temple_country_error),
                                    context.getString(R.string.txt_ok), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                        }
                                    });
                        }
                        break;
                    case R.id.etMPDistrict:
                        if (TempleStateId != null) {
                            int id = Integer.parseInt(TempleStateId);
                            Intent iTState = new Intent(context, SelectionActivity.class);
                            iTState.putExtra("isTDistrict", true);
                            iTState.putExtra("state", id);
                            ((Activity) context).startActivityForResult(iTState, REQUEST_TEMPLE_DISTRICT);
                            currentPosition = (int) v.getTag();
                        } else {
                            AppDialog.showAlertDialog(context, null, context.getResources().getString(R.string.add_temple_state_error),
                                    context.getString(R.string.txt_ok), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                        }
                                    });
                        }
                        break;
                    case R.id.etMPCity:
                        if (TempleDistrictId != null) {
                            int id = Integer.parseInt(TempleDistrictId);
                            Intent iTState = new Intent(context, SelectionActivity.class);
                            iTState.putExtra("isTCity", true);
                            iTState.putExtra("district", id);
                            ((Activity) context).startActivityForResult(iTState, REQUEST_TEMPLE_CITY);
                            currentPosition = (int) v.getTag();
                        } else {
                            AppDialog.showAlertDialog(context, null, context.getResources().getString(R.string.add_temple_district_error),
                                    context.getString(R.string.txt_ok), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                        }
                                    });
                        }
                        break;
                    case R.id.etMPArea:
                        if (TempleCityId != null) {
                            int id = Integer.parseInt(TempleCityId);
                            Intent iTState = new Intent(context, SelectionActivity.class);
                            iTState.putExtra("isTArea", true);
                            iTState.putExtra("city", id);
                            ((Activity) context).startActivityForResult(iTState, REQUEST_TEMPLE_AREA);
                            currentPosition = (int) v.getTag();
                        } else {
                            AppDialog.showAlertDialog(context, null, context.getResources().getString(R.string.add_temple_city_error),
                                    context.getString(R.string.txt_ok), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                        }
                                    });
                        }
                        break;

                    default:
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //For Street
    private class CustomTextWatcherStreet implements TextWatcher {
        int streetPos;
        private EditText mEditText;

        public CustomTextWatcherStreet(int position) {
            streetPos = position;
            Log.e("Street Pos" + streetPos);

        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        public void afterTextChanged(Editable s) {
            addressStringArrayList.get(streetPos).setAddress(String.valueOf(s));

        }
    }

    //For address type
    private class CustomTextWatcherAddressType implements TextWatcher {
        int addTypePos;
        private EditText mEditText;

        public CustomTextWatcherAddressType(int position) {
            addTypePos = position;

        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        public void afterTextChanged(Editable s) {

            addressStringArrayList.get(addTypePos).setAddressType(String.valueOf(s));

        }
    }

    //for zip
    private class CustomTextWatcherZip implements TextWatcher {
        int zipPos;
        private EditText mEditText;

        public CustomTextWatcherZip(int position) {
            zipPos = position;

        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        public void afterTextChanged(Editable s) {

            addressStringArrayList.get(zipPos).setPincode(String.valueOf(s));
        }
    }

    //For Getting Lat lang
    private class GeocoderHandler extends Handler {

        @Override
        public void handleMessage(Message message) {

            String locationAddress;
            boolean InvalidLatLong = false;


            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    InvalidLatLong = bundle.getBoolean("InvalidLatLong");
                    break;
                default:


                    locationAddress = null;
            }
            if (InvalidLatLong) {
                Toast.makeText(context, "Unable to find latitude and langitude for this address!", Toast.LENGTH_LONG).show();
            } else {
                tempLatLog = locationAddress.split(",");
                lat = tempLatLog[0];
                lang = tempLatLog[1];
                // Toast.makeText(context, lat + "-" + lang, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?q=" + lat + "," + lang));
                context.startActivity(intent);


            }
        }
    }


}
