package com.example.entreprenur.mobileshop.fragment;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.entreprenur.mobileshop.R;
import com.example.entreprenur.mobileshop.activity.MainActivity;
import com.example.entreprenur.mobileshop.api.ApiClient;
import com.example.entreprenur.mobileshop.api.ServiceGenerator;
import com.example.entreprenur.mobileshop.api.models.MobileModel;
import com.example.entreprenur.mobileshop.database.basemodels.BasketItemModel;
import com.example.entreprenur.mobileshop.database.basemodels.CpuModel;
import com.example.entreprenur.mobileshop.database.basemodels.NameModel;
import com.example.entreprenur.mobileshop.database.basemodels.OsModel;
import com.example.entreprenur.mobileshop.database.basemodels.RamModel;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@EFragment(R.layout.fragment_item_details)
public class ItemDetailsFragment extends Fragment {

    @FragmentArg
    Long mobileId;


    @ViewById(R.id.item_image)
    protected ImageView itemImage;
    @ViewById(R.id.item_name)
    protected TextView itemName;
    @ViewById(R.id.memo_text)
    protected TextView memoText;
    @ViewById(R.id.cpu_text)
    protected TextView cpuText;
    @ViewById(R.id.brand_text)
    protected TextView brandText;
    @ViewById(R.id.os_text)
    protected TextView osText;
    @ViewById(R.id.single_price)
    protected TextView singleItemPrice;
    @ViewById(R.id.item_amount)
    protected TextView itemAmount;
    @ViewById(R.id.total_cost)
    protected TextView totalCost;

    private int priceOfTheItem = 0;
    private int totalAmountOfItems = 1;
    private String imageUrlString = "";
    private String imageNameString = "";
    private int currentItemId = 0;

    @Click(R.id.increment)
    public void doIncrement() {

        totalAmountOfItems++;

        itemAmount.setText(String.valueOf(totalAmountOfItems));
        int total = priceOfTheItem * totalAmountOfItems;
        totalCost.setText(String.format("%s KM", String.valueOf(total)));
    }

    @Click(R.id.decrement)
    public void doDecrement() {
        if (totalAmountOfItems <= 1) {
            return;
        }

        totalAmountOfItems--;

        itemAmount.setText(String.valueOf(totalAmountOfItems));
        int total = priceOfTheItem * totalAmountOfItems;
        totalCost.setText(String.format("%s KM", String.valueOf(total)));
    }

    @Click(R.id.add_button)
    public void addToBasket() {
        MainActivity mainActivity = (MainActivity) getActivity();
        if ((mainActivity == null) || mainActivity.isFinishing() || !isAdded() || (mobileId == null)) {
            return;
        }

        BasketItemModel basketItemModel = new BasketItemModel();
        basketItemModel.setPrice(priceOfTheItem);
        basketItemModel.setAmoount(totalAmountOfItems);
        basketItemModel.setImage(imageUrlString);
        basketItemModel.setName(imageNameString);
        basketItemModel.setId(currentItemId);

        mainActivity.insertBasketItem(basketItemModel);
        Snackbar.make(getView(), "Uspjesno ste dodali u korpu", 500);

        close();
    }

    @Background(delay = 600)
    public void close() {
        MainActivity mainActivity = (MainActivity) getActivity();
        if ((mainActivity == null) || mainActivity.isFinishing() || !isAdded() || (mobileId == null)) {
            return;
        }

        mainActivity.onBackPressed();
    }

    @AfterViews
    public void init() {
        final MainActivity mainActivity = (MainActivity) getActivity();
        if ((mainActivity == null) || mainActivity.isFinishing() || !isAdded() || (mobileId == null)) {
            return;
        }

        Call<MobileModel> call = ServiceGenerator.createService(ApiClient.class).getMobileById((int) (long) mobileId);

        call.enqueue(new Callback<MobileModel>() {
            @Override
            public void onResponse(Call<MobileModel> call, Response<MobileModel> response) {
                if (!response.isSuccessful() || (response.body() == null)) {
                    Snackbar.make(getView(), "Dogodila se greska", 500).show();
                    return;
                }

                MobileModel mobileModel = response.body();
                if (mobileModel == null) {
                    Snackbar.make(getView(), "Dogodila se greska", 500).show();
                    return;
                }

                if (mobileModel.Id != null) {
                    currentItemId = (int) (long) mobileModel.Id;
                }

                if (mobileModel.SlikaUrl != null) {
                    imageUrlString = mobileModel.SlikaUrl;
                    Picasso.get().load(mobileModel.SlikaUrl).into(itemImage);
                }

                if (mobileModel.Naziv != null) {
                    imageNameString = mobileModel.Naziv;
                    itemName.setText(mobileModel.Naziv);
                }

                if (mobileModel.BrendoviId != null) {
                    NameModel br = mainActivity.getBrands(mobileModel.BrendoviId).get(0);
                    brandText.setText(br.getNaziv());
                }

                if (mobileModel.CpuJezgreId != null) {
                    CpuModel cpu = mainActivity.getCpu(mobileModel.CpuJezgreId).get(0);
                    cpuText.setText(cpu.getOpis());
                }

                if (mobileModel.MemorijaId != null) {
                    RamModel ramModel = mainActivity.getMemo(mobileModel.MemorijaId).get(0);
                    memoText.setText(String.format("%s GB", String.valueOf(ramModel.getKapacitet())));
                }

                if (mobileModel.OperativniSistemId != null) {
                    OsModel osModel = mainActivity.getOs(mobileModel.OperativniSistemId).get(0);
                    osText.setText(osModel.getNaziv());
                }

                if (mobileModel.Cijena != null) {
                    priceOfTheItem = mobileModel.Cijena;
                    singleItemPrice.setText(String.format("%s KM", String.valueOf(mobileModel.Cijena)));
                    totalCost.setText(String.format("%s KM", String.valueOf(mobileModel.Cijena)));
                }
            }

            @Override
            public void onFailure(Call<MobileModel> call, Throwable t) {
                Snackbar.make(getView(), "Dogodila se greska", 500).show();
            }
        });
    }
}
