package com.covidapitest.oriolcasas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.covidapitest.oriolcasas.model.CasesModel;
import com.covidapitest.oriolcasas.presenter.MainPresenter;
import com.covidapitest.oriolcasas.presenter.MainPresenterImpl;
import com.covidapitest.oriolcasas.utils.SortingType;
import com.covidapitest.oriolcasas.views.CountriesListFragment;
import com.covidapitest.oriolcasas.views.CountrySearchFragment;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MainPresenter.View, PopupMenu.OnMenuItemClickListener {

    private CasesModel casesModel;
    private TextView textView, retryButton;
    private FrameLayout container;
    private CircularProgressIndicator progressIndicator;
    private MainPresenter presenter;
    private boolean showMenu = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setPresenter();
        bindViews();
        setData();

        presenter.requestData();
    }

    private void bindViews() {
        textView = findViewById(R.id.central_text);
        retryButton = findViewById(R.id.retry_button);
        container = findViewById(R.id.container);
        progressIndicator = findViewById(R.id.main_progress_indicator);
    }

    private void setData() {
        textView.setVisibility(View.GONE);
        retryButton.setVisibility(View.GONE);
        retryButton.setOnClickListener(v -> presenter.requestData());
    }

    private void setPresenter() {
        presenter = new MainPresenterImpl(this);
        presenter.setView(this);
    }

    public void navigateToFragment(Fragment fragment) {
        setMenuVisibility(fragment instanceof CountriesListFragment);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // The showMenu is only available when its CountriesListFragment which is the first view (unless there's been an error)
        // Other fragments should add to stack in order to go back until this fragment is visible again
        if (!showMenu) transaction.addToBackStack(null);
        transaction.replace(container.getId(), fragment, fragment.getClass().getSimpleName())
                .commitAllowingStateLoss();
    }

    private void showSortingList() {
        PopupMenu popup = new PopupMenu(this, findViewById(R.id.menu_sort)); // Submenu to show the user the options to sort
        popup.setOnMenuItemClickListener(this); // The click is controlled in this activity
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.sorting_menu, popup.getMenu()); // The menu to show
        popup.show();
    }

    private void sortList(SortingType sortingType) {
        CountriesListFragment fragment = (CountriesListFragment) getSupportFragmentManager()
                .findFragmentByTag(CountriesListFragment.class.getSimpleName()); // The fragments are added using their class name as the tag
        if (fragment != null) {
            fragment.sortList(sortingType);
        }
    }

    private void search() {
        CountrySearchFragment fragment = CountrySearchFragment.newInstance(new ArrayList<>(casesModel.getCountriesData().keySet()), casesModel);
        navigateToFragment(fragment);
    }

    public void setMenuVisibility(boolean visible) {
        showMenu = visible;
        invalidateOptionsMenu(); // The menu is recreated
    }

    @Override
    public void onDataRetrieved(CasesModel data) {
        setMenuVisibility(true);
        casesModel = data;
        textView.setVisibility(View.GONE);
        retryButton.setVisibility(View.GONE);
        CountriesListFragment fragment = CountriesListFragment.newInstance(data);
        navigateToFragment(fragment);
        progressIndicator.hide();
    }

    @Override
    public void showError(String message) {
        setMenuVisibility(false);
        textView.setTextColor(getColor(R.color.design_default_color_error));
        textView.setText(message);
        textView.setVisibility(View.VISIBLE);
        retryButton.setVisibility(View.VISIBLE);
        progressIndicator.hide();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        menu.clear(); // Used to avoid adding the same options multiple times
        if (showMenu) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.main_menu, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_sort:
                showSortingList();
                return true;
            case R.id.menu_search:
                search();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sorting_default:
                sortList(SortingType.NONE);
                return true;
            case R.id.sorting_active:
                sortList(SortingType.ACTIVE);
                return true;
            case R.id.sorting_deaths:
                sortList(SortingType.DEATHS);
                return true;
            case R.id.sorting_active100k:
                sortList(SortingType.ACTIVE100K);
                return true;
            case R.id.sorting_deaths100k:
                sortList(SortingType.DEATHS100K);
                return true;
            default:
                return false;
        }
    }
}