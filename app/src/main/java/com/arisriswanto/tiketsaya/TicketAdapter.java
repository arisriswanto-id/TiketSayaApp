package com.arisriswanto.tiketsaya;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

// <TicketAdapter.MyViewHolder> saling berhubungan
public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.MyViewHolder> {

    // daftar variabel baru, untuk content
    Context context;

    // data berupa array, dapatkan dari MyTicket
    ArrayList<MyTicket> myTicket;

    // c sebagai variabel
    public TicketAdapter(Context c, ArrayList<MyTicket> p) {
        context = c;
        myTicket = p;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_myticket, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        // bind data
        myViewHolder.xnama_wisata.setText(myTicket.get(i).getNama_wisata());
        myViewHolder.xlokasi.setText(myTicket.get(i).getLokasi());
        myViewHolder.xjumlah_ticket.setText(myTicket.get(i).getJumlah_ticket() + "Tickets");

        final String getNamaWisata = myTicket.get(i).getNama_wisata();

        // on click
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotomyticketdetails = new Intent(context, MyTicketDetailAct.class);
                gotomyticketdetails.putExtra("nama_wisata", getNamaWisata);
                context.startActivity(gotomyticketdetails);
            }
        });
    }

    @Override
    public int getItemCount() {

        // item berdasarkan ukuran myTickets, jika ada 5, maka itemnya 5
        return myTicket.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView xnama_wisata, xlokasi, xjumlah_ticket;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            xnama_wisata = itemView.findViewById(R.id.xnama_wisata);
            xlokasi = itemView.findViewById(R.id.xlokasi);
            xjumlah_ticket = itemView.findViewById(R.id.xjumlah_ticket);
        }
    }

}
