package com.example.adipermana.hidroponik.LoginRegister;

import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.adipermana.hidroponik.Model.userModel;
import com.example.adipermana.hidroponik.Model.userResponModel;
import com.example.adipermana.hidroponik.R;
import com.example.adipermana.hidroponik.api.NetworkUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.example.adipermana.hidroponik.api.Validation.validateEmail;
import static com.example.adipermana.hidroponik.api.Validation.validateFields;


public class ContentRegis extends Fragment {
    private View view ;
    private CompositeSubscription mSubscriptions;
    @BindView(R.id.etNama)EditText etNama;
    @BindView(R.id.etEmail)EditText etEmail;
    @BindView(R.id.etPass)EditText etPass;
    @BindView(R.id.tiNama)TextInputLayout tiNama;
    @BindView(R.id.tiEmail)TextInputLayout tiEmail;
    @BindView(R.id.tiPass)TextInputLayout tiPass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.content_regis, container, false);
        ButterKnife.bind(this,view);
        mSubscriptions = new CompositeSubscription();
        return view;
    }

    @OnClick(R.id.btnRegis)
    public void btnRegis(){
        register();
    }


    private void register() {

        setError();

        String name = etNama.getText().toString();
        String email = etEmail.getText().toString();
        String password = etPass.getText().toString();

        int err = 0;

        if (!validateFields(name)) {

            err++;
            tiNama.setError("Name tidak boleh kosong !");
        }

        if (!validateEmail(email)) {

            err++;
            tiEmail.setError("Masukkan Email dengan Benar !");
        }

        if (!validateFields(password)) {

            err++;
            tiPass.setError("Password tidak boleh kosong !");
        }

        if (err == 0) {

            userModel user = new userModel(name,email,password);
            user.setName(name);
            user.setEmail(email);
            user.setPassword(password);

            registerProcess(user);

        } else {

            showSnackBarMessage("Enter Valid Details !");
        }
    }

    private void setError() {

        tiNama.setError(null);
        tiEmail.setError(null);
        tiPass.setError(null);
    }

    private void registerProcess(userModel user) {

        mSubscriptions.add(NetworkUtil.getRetrofit().register(user)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse,this::handleError));
    }

    private void handleResponse(userResponModel response) {

        showSnackBarMessage(response.getMessage());
    }

    private void handleError(Throwable error) {


        if (error instanceof HttpException) {

            Gson gson = new GsonBuilder().create();

            try {

                String errorBody = ((HttpException) error).response().errorBody().string();
                userResponModel response = gson.fromJson(errorBody,userResponModel.class);
                showSnackBarMessage(response.getMessage());

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

            showSnackBarMessage("Network Error !");
        }
    }

    private void showSnackBarMessage(String message) {

        if (getView() != null) {

            Snackbar.make(getView(),message,Snackbar.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mSubscriptions.unsubscribe();
    }


}
