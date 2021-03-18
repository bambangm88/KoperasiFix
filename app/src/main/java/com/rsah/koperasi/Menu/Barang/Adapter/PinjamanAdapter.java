package com.rsah.koperasi.Menu.Barang.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.rsah.koperasi.Helper.Helper;
import com.rsah.koperasi.Menu.Pinjaman.DetailPinjamanActivity;
import com.rsah.koperasi.Model.Data.DataPinjaman;
import com.rsah.koperasi.Model.Data.DataSimpanan;
import com.rsah.koperasi.R;
import com.rsah.koperasi.sessionManager.SessionManager;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;


public class PinjamanAdapter extends RecyclerView.Adapter<PinjamanAdapter.AdapterHolder>{

    List<DataPinjaman> AllPaymentItemList;
    Context mContext;

    SessionManager sessionManager ;


    public PinjamanAdapter(Context context, List<DataPinjaman> paymentList){
        this.mContext = context;
        AllPaymentItemList = paymentList;
        sessionManager = new SessionManager(mContext);

    }

    @Override
    public AdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pinjaman, parent, false);
        return new AdapterHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AdapterHolder holder, int position) {
        final DataPinjaman responsePaymentMethod = AllPaymentItemList.get(position);

        String tgl_trf = responsePaymentMethod.getTanggalPinjaman();
        String jumlah = responsePaymentMethod.getJumlahPinjaman();
        String status = responsePaymentMethod.getStatus();
        String statusDesc = responsePaymentMethod.getStatusDesc();
        String invoice = responsePaymentMethod.getOrderIdPinjaman();

        Helper.colostatuspinjaman(status,statusDesc,holder.status_,mContext);





        holder.jumlah_.setText(Helper.changeToRupiah2(jumlah));
        holder.invoice.setText(position+1+". "+invoice);

        holder.date_.setText(Helper.parseDate(tgl_trf ));


        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext,DetailPinjamanActivity.class);
                i.putExtra("invoice", invoice);
                mContext.startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return AllPaymentItemList.size();
    }

    public class AdapterHolder extends RecyclerView.ViewHolder{



        TextView date_ , jumlah_ , status_ ,invoice;
        RelativeLayout card ;



        public AdapterHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            date_ = itemView.findViewById(R.id.tvdate);
            jumlah_ = itemView.findViewById(R.id.tvjumlah);
            status_ = itemView.findViewById(R.id.tvstatus);
            card = itemView.findViewById(R.id.rootLayout);
            invoice = itemView.findViewById(R.id.invoice);

        }
    }






}
