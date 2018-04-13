package com.example.adipermana.hidroponik.LoginRegister;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
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
import android.widget.Toast;

import com.example.adipermana.hidroponik.Beranda.Beranda;
import com.example.adipermana.hidroponik.Beranda.BerandaFragmentControll;
import com.example.adipermana.hidroponik.Model.userResponModel;
import com.example.adipermana.hidroponik.R;
import com.example.adipermana.hidroponik.api.NetworkUtil;
import com.example.adipermana.hidroponik.api.Server;
import com.example.adipermana.hidroponik.api.Session;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import design.MyEditText;
import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import static com.example.adipermana.hidroponik.api.Validation.validateEmail;
import static com.example.adipermana.hidroponik.api.Validation.validateFields;


public class ContentLogin extends Fragment {
    private View view ;
    /*private Context context ;
    ProgressDialog pDialog;
    @BindView(R.id.etEmail)
    MyEditText etEmail;
    @BindView(R.id.etPass)
    MyEditText etPass;*/


    private CompositeSubscription mSubscriptions;
    SharedPreferences prefs;
    Session session;
    String token , email ;
    @BindView(R.id.etEmail)EditText etEmail ;
    @BindView(R.id.etPass)EditText etPass ;
    @BindView(R.id.tiEmail)TextInputLayout mTiEmail;
    @BindView(R.id.tiPass)TextInputLayout mTiPassword;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view= inflater.inflate(R.layout.content_login, container, false);
        ButterKnife.bind(this,view);
        mSubscriptions = new CompositeSubscription();
        prefs = getActivity().getSharedPreferences("UserDetails",
                Context.MODE_PRIVATE);
        session = new Session(getActivity());
        token = prefs.getString("token", "");
        email = prefs.getString("email", "");
        if (session.isLoggedIn()) {
            Intent intent = new Intent(getActivity(), Beranda.class);
            intent.putExtra("token",token);
            intent.putExtra("email",email);
            startActivity(intent);
        }
        return view;
    }
    @OnClick(R.id.btnLogin)
    public void btnLogin(){
        login();
    }


    private void storeRegIdinSharedPref(Context context,String token,String email) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("token", token);
        editor.putString("email", email);
        editor.commit();
    }

    private void login() {

        setError();

        String email = etEmail.getText().toString();
        String password = etPass.getText().toString();

        int err = 0;

        if (!validateEmail(email)) {
            err++;
            mTiEmail.setError("Masukkan email dengan benar !");
        }

        if (!validateFields(password)) {
            err++;
            mTiPassword.setError("Password tidak boleh kosong !");
        }

        if (err == 0) {

            loginProcess(email,password);

        } else {

            showSnackBarMessage("Email atau Password salah !");
        }
    }

    private void setError() {

        mTiEmail.setError(null);
        mTiPassword.setError(null);
    }

    private void loginProcess(String email, String password) {

        mSubscriptions.add(NetworkUtil.getRetrofit(email,password).login()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse,this::handleError));
    }

    private void handleResponse(userResponModel response) {
        session.setLogin(true);
        storeRegIdinSharedPref(getActivity(), response.getToken(), response.getMessage());
        etEmail.setText(null);
        etPass.setText(null);
        Intent intent = new Intent(getActivity(), Beranda.class);
        intent.putExtra("token",response.getToken());
        intent.putExtra("email",response.getMessage());
        startActivity(intent);

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
