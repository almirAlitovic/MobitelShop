package com.example.entreprenur.mobileshop.fragment;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.entreprenur.mobileshop.R;
import com.example.entreprenur.mobileshop.activity.MainActivity;
import com.example.entreprenur.mobileshop.api.ApiClient;
import com.example.entreprenur.mobileshop.api.ServiceGenerator;
import com.example.entreprenur.mobileshop.api.models.MobileModel;
import com.example.entreprenur.mobileshop.database.basemodels.CpuModel;
import com.example.entreprenur.mobileshop.database.basemodels.NameModel;
import com.example.entreprenur.mobileshop.database.basemodels.OsModel;
import com.example.entreprenur.mobileshop.database.basemodels.RamModel;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@EFragment(R.layout.fragment_shop)
public class ShopFragment extends Fragment {

    @ViewById(R.id.filter_icon)
    protected ImageView filterIcon;
    @ViewById(R.id.searched_text)
    protected TextView searchedText;
    @ViewById(R.id.filter_holder)
    protected CardView filterHolder;
    @ViewById(R.id.search_name)
    protected EditText searchName;
    @ViewById(R.id.brand_selected)
    protected TextView brandSelected;
    @ViewById(R.id.brand_list)
    protected ListView brandList;
    @ViewById(R.id.cpu_selected)
    protected TextView cpuSelected;
    @ViewById(R.id.cpu_list)
    protected ListView cpuList;
    @ViewById(R.id.ram_selected)
    protected TextView ramSelected;
    @ViewById(R.id.ram_list)
    protected ListView ramList;
    @ViewById(R.id.os_selected)
    protected TextView osSelected;
    @ViewById(R.id.os_list)
    protected ListView osList;
    @ViewById(R.id.main_list)
    protected RecyclerView mainList;
    @ViewById(R.id.brand_holder)
    protected RelativeLayout brandHolder;
    @ViewById(R.id.cpu_holder)
    protected RelativeLayout cpuHolder;
    @ViewById(R.id.ram_holder)
    protected RelativeLayout ramHolder;
    @ViewById(R.id.os_holder)
    protected RelativeLayout osHolder;
    @ViewById(R.id.progress_holder)
    protected RelativeLayout progressHolder;

    List<CpuModel> cpuModels;
    List<NameModel> nameModels;
    List<OsModel> osModels;
    List<RamModel> ramModels;

    private Integer currentCpu = 0;
    private Integer currentName = 0;
    private Integer currentOs = 0;
    private Integer currentRam = 0;

    private boolean filterShowed = false;
    Snackbar snackbarApi = null;

    List<MobileModel> mobileModels = new ArrayList<>();
    MainShopAdapter adapter = null;

    @AfterViews
    public void init() {
        final MainActivity activity = (MainActivity) getActivity();
        if ((activity == null) || activity.isFinishing() || !isAdded()) {
            return;
        }
        cpuModels = activity.getCpu();
        nameModels = activity.getBrands();
        osModels = activity.getOs();
        ramModels = activity.getMemo();

        if (getView() != null) {
            snackbarApi = Snackbar.make(getView(), "Internet greska", 400);
        }

        initCpuList(activity);
        initNameList(activity);
        initOsList(activity);
        initRamList(activity);

        GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
        mainList.setLayoutManager(manager);

        searchName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                searchedText.setText(editable);
            }
        });


        Call<List<MobileModel>> call = ServiceGenerator.createService(ApiClient.class).getMobiles();

        progressHolder.setVisibility(View.VISIBLE);

        call.enqueue(new Callback<List<MobileModel>>() {
            @Override
            public void onResponse(Call<List<MobileModel>> call, Response<List<MobileModel>> response) {

                progressHolder.setVisibility(View.GONE);


                List<MobileModel> mobileModels = response.body();

                MainShopAdapter adapter = new MainShopAdapter(mobileModels, null, new MainShopAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClickListener(MobileModel mobileModel) {
                        activity.showItemDetailsFragment(getFragmentManager(), mobileModel.Id);
                    }
                }, null);

                mainList.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<MobileModel>> call, Throwable t) {

                progressHolder.setVisibility(View.GONE);
            }
        });
    }

    private void initCpuList(MainActivity activity) {
        final List<String> list = new ArrayList<>();
        for (CpuModel cpuModel : cpuModels) {
            String details = cpuModel.getOpis();
            if (details != null) {
                list.add(details);
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity,
                android.R.layout.simple_list_item_1, android.R.id.text1, list);

        cpuList.setAdapter(adapter);
        cpuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                currentCpu = (int) (long) cpuModels.get(position).getItemId();
                cpuSelected.setText(list.get(position));
                cpuHolder.setVisibility(View.GONE);
            }
        });
    }

    private void initNameList(MainActivity activity) {
        final List<String> list = new ArrayList<>();
        for (NameModel nameModel : nameModels) {
            list.add(nameModel.getNaziv());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity,
                android.R.layout.simple_list_item_1, android.R.id.text1, list);

        brandList.setAdapter(adapter);
        brandList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                currentName = (int) (long) nameModels.get(position).getItemId();
                brandSelected.setText(list.get(position));
                brandHolder.setVisibility(View.GONE);
            }
        });
    }

    private void initOsList(MainActivity activity) {
        final List<String> list = new ArrayList<>();
        for (OsModel osModel : osModels) {
            list.add(osModel.getNaziv());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity,
                android.R.layout.simple_list_item_1, android.R.id.text1, list);

        osList.setAdapter(adapter);
        osList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                currentOs = (int) (long) osModels.get(position).getItemId();
                osSelected.setText(list.get(position));
                osHolder.setVisibility(View.GONE);
            }
        });
    }

    private void initRamList(MainActivity activity) {
        final List<String> list = new ArrayList<>();
        for (RamModel ramModel : ramModels) {
            list.add(String.valueOf(ramModel.getKapacitet()));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity,
                android.R.layout.simple_list_item_1, android.R.id.text1, list);

        ramList.setAdapter(adapter);
        ramList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                currentRam = (int) (long) ramModels.get(position).getItemId();
                ramSelected.setText(list.get(position));
                ramHolder.setVisibility(View.GONE);
            }
        });
    }

    @Click(R.id.pick_brand)
    public void pickBrandClick() {
        brandHolder.setVisibility(View.VISIBLE);
    }

    @Click(R.id.pick_cpu)
    public void pickCpuClick() {
        cpuHolder.setVisibility(View.VISIBLE);
    }

    @Click(R.id.pick_ram)
    public void pickRamClick() {
        ramHolder.setVisibility(View.VISIBLE);
    }

    @Click(R.id.pick_os)
    public void pickOsClick() {
        osHolder.setVisibility(View.VISIBLE);
    }

    @Click(R.id.filter_icon_holder)
    public void onIconClick() {
        if (filterShowed) {
            filterShowed = false;

            filterIcon.animate().rotationBy(180).setDuration(300).start();
            filterHolder.setVisibility(View.GONE);
        } else {
            filterShowed = true;
            filterHolder.setVisibility(View.VISIBLE);
            filterIcon.animate().rotationBy(180).setDuration(300).start();
        }
    }

    @Click(R.id.search_holder)
    public void doSearch() {
        final MainActivity activity = (MainActivity) getActivity();
        if ((activity == null) || activity.isFinishing() || !isAdded()) {
            return;
        }

        if (!searchName.getText().toString().isEmpty()) {
            Call<List<MobileModel>> call = ServiceGenerator.createService(ApiClient.class).getMobilesByName(searchName.getText().toString());
            progressHolder.setVisibility(View.VISIBLE);
            if (filterShowed) {
                onIconClick();
            }
            call.enqueue(new Callback<List<MobileModel>>() {
                @Override
                public void onResponse(Call<List<MobileModel>> call, Response<List<MobileModel>> response) {
                    progressHolder.setVisibility(View.GONE);
                    if (response.body() == null) {
                        snackbarApi.show();
                    }

                    mobileModels = response.body();

                    adapter = new MainShopAdapter(mobileModels, null, new MainShopAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClickListener(MobileModel mobileModel) {
                            activity.showItemDetailsFragment(getFragmentManager(), mobileModel.Id);
                        }
                    }, null);

                    mainList.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<List<MobileModel>> call, Throwable t) {
                    if (snackbarApi != null) {
                        snackbarApi.show();
                    }

                    progressHolder.setVisibility(View.GONE);
                }
            });
        } else if ((currentOs != 0) || (currentRam != 0) || (currentCpu != 0) || (currentName != 0)) {
            Call<List<MobileModel>> call = ServiceGenerator.createService(ApiClient.class).getMobilesFilter(currentName, currentCpu, currentRam, currentOs);
            progressHolder.setVisibility(View.VISIBLE);
            if (filterShowed) {
                onIconClick();
            }
            call.enqueue(new Callback<List<MobileModel>>() {
                @Override
                public void onResponse(Call<List<MobileModel>> call, Response<List<MobileModel>> response) {
                    progressHolder.setVisibility(View.GONE);
                    if (response.body() == null) {
                        snackbarApi.show();
                    }

                    mobileModels = response.body();

                    adapter = new MainShopAdapter(mobileModels, null, new MainShopAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClickListener(MobileModel mobileModel) {
                            activity.showItemDetailsFragment(getFragmentManager(), mobileModel.Id);
                        }
                    }, null);

                    mainList.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<List<MobileModel>> call, Throwable t) {
                    if (snackbarApi != null) {
                        snackbarApi.show();
                    }

                    progressHolder.setVisibility(View.GONE);
                }
            });
        } else {
            Snackbar snackbar = Snackbar.make(getView(), "Morate izabrati ime ili filtere", 1000);
            snackbar.show();
        }
    }

    @Click(R.id.clear_brand)
    public void clearBrand() {
        currentName = 0;
        brandSelected.setText(R.string.niste_izabrali);
    }

    @Click(R.id.clear_cpu)
    public void clearCpu() {
        currentCpu = 0;
        cpuSelected.setText(R.string.niste_izabrali);
    }

    @Click(R.id.clear_ram)
    public void clearRam() {
        currentRam = 0;
        ramSelected.setText(R.string.niste_izabrali);
    }

    @Click(R.id.clear_os)
    public void clearOs() {
        currentOs = 0;
        osSelected.setText(R.string.niste_izabrali);

    }

    @Click(R.id.basket_fragment)
    public void navigateToBasket() {
        final MainActivity activity = (MainActivity) getActivity();
        if ((activity == null) || activity.isFinishing() || !isAdded()) {
            return;
        }

        activity.showBasketFragment(getFragmentManager());
    }
}
