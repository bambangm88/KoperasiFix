package com.rsah.koperasi.Menu.Barang.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.rsah.koperasi.Helper.Helper;
import com.rsah.koperasi.Model.Data.DataRecordPinjaman;
import com.rsah.koperasi.R;
import com.rsah.koperasi.sessionManager.SessionManager;

import java.util.List;

import butterknife.ButterKnife;

import static android.content.ContentValues.TAG;


public class SimulasiPinjamanAdapter extends RecyclerView.Adapter<SimulasiPinjamanAdapter.AdapterHolder>{

    List<DataRecordPinjaman> AllPaymentItemList;
    Context mContext;

    SessionManager sessionManager ;


    public SimulasiPinjamanAdapter(Context context, List<DataRecordPinjaman> paymentList){
        this.mContext = context;
        AllPaymentItemList = paymentList;
        sessionManager = new SessionManager(mContext);

    }

    @Override
    public AdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_simulasi_cicilan, parent, false);
        return new AdapterHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AdapterHolder holder, int position) {
        final DataRecordPinjaman responsePaymentMethod = AllPaymentItemList.get(position);

        String reference = responsePaymentMethod.getOrderIdPinjaman();
        String statusDesc = responsePaymentMethod.getStatusDesc();
        String tanggal = responsePaymentMethod.getModifiedDate();
        String desc = responsePaymentMethod.getDeskripsi();

        int cicilke = position+1 ;
        String cicilan = ""+cicilke;

        String jatuhtempo = responsePaymentMethod.getTanggalBayar();

        Log.e(TAG, "onBindViewHolder: "+"ANGSURAN" );

        holder.cicilan.setText(cicilan);

        if (responsePaymentMethod.getStatus() == null){
            holder.jatuhTempo.setText("-");
        }else{
            String status = responsePaymentMethod.getStatus();
            if (status.equals("0")){
                holder.jatuhTempo.setText("-");
            }else{
                holder.jatuhTempo.setText(Helper.parseDate(jatuhtempo));
            }

        }

        if (responsePaymentMethod.getJumlahBayar() == null) {
            holder.jumlah.setText("-");
        }else{
            String jumlah = responsePaymentMethod.getJumlahBayar();
            holder.jumlah.setText(Helper.changeToRupiah2(jumlah));
        }


    }

    @Override
    public int getItemCount() {
        return AllPaymentItemList.size();
    }

    public class AdapterHolder extends RecyclerView.ViewHolder{



        TextView cicilan , jatuhTempo , jumlah;
        LinearLayout card ;



        public AdapterHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            cicilan = itemView.findViewById(R.id.cicilan);
            jatuhTempo= itemView.findViewById(R.id.jatuhTempo);
            jumlah = itemView.findViewById(R.id.jumlah);

        }
    }






}
