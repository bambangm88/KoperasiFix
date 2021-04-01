package com.rsah.koperasi.Menu.Barang.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.rsah.koperasi.Helper.Helper;
import com.rsah.koperasi.Menu.Pinjaman.DetailPinjamanActivity;
import com.rsah.koperasi.Model.Data.DataPinjaman;
import com.rsah.koperasi.Model.Data.DataSaldo;
import com.rsah.koperasi.R;
import com.rsah.koperasi.sessionManager.SessionManager;

import java.util.List;

import butterknife.ButterKnife;


public class LastestSimpananAdapter extends RecyclerView.Adapter<LastestSimpananAdapter.AdapterHolder>{

    List<DataSaldo> AllPaymentItemList;
    Context mContext;

    SessionManager sessionManager ;


    public LastestSimpananAdapter(Context context, List<DataSaldo> paymentList){
        this.mContext = context;
        AllPaymentItemList = paymentList;
        sessionManager = new SessionManager(mContext);

    }

    @Override
    public AdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lastest_simpanan, parent, false);
        return new AdapterHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AdapterHolder holder, int position) {
        final DataSaldo responsePaymentMethod = AllPaymentItemList.get(position);

        String tgl_trf = responsePaymentMethod.getTanggal();
        String pokok_ = responsePaymentMethod.getPokok();
        String wajib_ = responsePaymentMethod.getWajib();
        String sukarela_= responsePaymentMethod.getSukarela();
        holder.date_.setText(tgl_trf.replace("00:00:00.000",""));
        holder.pokok.setText(Helper.changeToRupiah2(pokok_));
        holder.wajib.setText(Helper.changeToRupiah2(wajib_));
        holder.sukarela.setText(Helper.changeToRupiah2(sukarela_));



    }

    @Override
    public int getItemCount() {
        return AllPaymentItemList.size();
    }

    public class AdapterHolder extends RecyclerView.ViewHolder{



        TextView date_ , jumlah_ , status_ ,sukarela , wajib , pokok;
        RelativeLayout card ;



        public AdapterHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            date_ = itemView.findViewById(R.id.tanggal);
            wajib = itemView.findViewById(R.id.tvwajib);
            pokok = itemView.findViewById(R.id.tvpokok);
          //  card = itemView.findViewById(R.id.rootLayout);
            sukarela = itemView.findViewById(R.id.tvsukarela);

        }
    }






}
