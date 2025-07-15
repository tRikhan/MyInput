package com.example.myinput;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MahasiswaAdapter extends RecyclerView.Adapter<MahasiswaAdapter.ViewHolder> {

    private Context context;
    private List<Mahasiswa> listMahasiswa;

    public MahasiswaAdapter(Context context, List<Mahasiswa> listMahasiswa) {
        this.context = context;
        this.listMahasiswa = listMahasiswa;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_mahasiswa, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Mahasiswa mhs = listMahasiswa.get(position);
        holder.tvNama.setText(mhs.getNama());
        holder.tvNpm.setText(mhs.getNpm());

        holder.itemView.setOnClickListener(v -> {
            // Tampilkan dialog pilihan saat klik item
            showDialogPilihan(mhs, position);
        });
    }

    private void showDialogPilihan(Mahasiswa mhs, int posisi) {
        String[] opsi = {"Lihat Data", "Update Data", "Hapus Data"};

        new AlertDialog.Builder(context)
                .setTitle("Pilih Aksi")
                .setItems(opsi, (dialog, which) -> {
                    switch (which) {

                        case 0: // Lihat Data
                            Intent intent = new Intent(context, MahasiswaDetailActivity.class);
                            intent.putExtra("npm", mhs.getNpm());
                            intent.putExtra("nama", mhs.getNama());
                            intent.putExtra("tanggal", mhs.getTanggalLahir());
                            intent.putExtra("alamat", mhs.getAlamat());
                            intent.putExtra("telepon", mhs.getNomorTelepon());
                            intent.putExtra("email", mhs.getEmail());
                            intent.putExtra("fakultas", mhs.getFakultas());
                            intent.putExtra("jurusan", mhs.getJurusan());
                            intent.putExtra("tahunMasuk", mhs.getTahunMasuk());
                            intent.putExtra("fotoProfil", mhs.getFotoProfil());
                            context.startActivity(intent);
                            break;

                        case 1: // Update Data
                            Intent i = new Intent(context, MahasiswaFormActivity.class);
                            i.putExtra("edit_mode", true);
                            i.putExtra("npm", mhs.getNpm());
                            i.putExtra("nama", mhs.getNama());
                            i.putExtra("tanggal", mhs.getTanggalLahir());
                            i.putExtra("alamat", mhs.getAlamat());
                            i.putExtra("telepon", mhs.getNomorTelepon());
                            i.putExtra("email", mhs.getEmail());
                            i.putExtra("fakultas", mhs.getFakultas());
                            i.putExtra("jurusan", mhs.getJurusan());
                            i.putExtra("tahunMasuk", mhs.getTahunMasuk());
                            i.putExtra("fotoProfil", mhs.getFotoProfil());
                            context.startActivity(i);
                            break;

                        case 2: // Hapus Data
                            DatabaseHelper db = new DatabaseHelper(context);
                            boolean sukses = db.hapusMahasiswa(mhs.getNpm());

                            if (sukses) {
                                Toast.makeText(context, "Data dihapus", Toast.LENGTH_SHORT).show();
                                listMahasiswa.remove(posisi);
                                notifyItemRemoved(posisi);
                                notifyItemRangeChanged(posisi, listMahasiswa.size());
                            } else {
                                Toast.makeText(context, "Gagal menghapus data", Toast.LENGTH_SHORT).show();
                            }
                            break;

                    }
                })
                .show();
    }

    @Override
    public int getItemCount() {
        return listMahasiswa.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNama, tvNpm;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.tvNama);
            tvNpm = itemView.findViewById(R.id.tvNpm);
        }
    }
}
