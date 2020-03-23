package com.example.contactosapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Log;
import android.widget.Toast;

import com.example.contactosapp.Modelos.contacto;
import com.example.contactosapp.Modelos.contactoContract;
import com.example.contactosapp.Modelos.contactoDBHelper;

import java.util.ArrayList;
import java.util.List;

public class recyclerViewAdapter extends RecyclerView.Adapter<recyclerViewAdapter.ViewHolder> implements Filterable {
    private static final String TAG = "recyclerViewAdapter";
    private ArrayList<contacto> contactos;
    private ArrayList<contacto> contactosFull;
    private Context mContext;
    private int position;
    private contactoDBHelper con;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public recyclerViewAdapter(ArrayList<contacto> contactos, Context mContext) {
        this.contactos = contactos;
        this.mContext = mContext;
        contactosFull = new ArrayList<>(contactos);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        Log.d(TAG, "onBindCalled");
        viewHolder.tvNombre.setText(contactos.get(i).getNombre() + " ");
        viewHolder.tvApellido.setText(contactos.get(i).getTelefono());
        viewHolder.tvGrupo.setText(contactos.get(i).getGrupo());
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                setPosition(viewHolder.getPosition());
                // notifyDataSetChanged();
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return contactos.size();
    }

    @Override
    public Filter getFilter() {

        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<contacto> filteredList = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(contactosFull);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for (contacto contact : contactosFull) {
                    if (contact.getNombre().toLowerCase().contains(filterPattern)) {
                        filteredList.add(contact);

                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            contactos.clear();
            contactos.addAll((ArrayList) results.values);
            notifyDataSetChanged();
            // Toast.makeText(mContext, "Resultados Publicados", Toast.LENGTH_SHORT).show();

        }
    };


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView tvNombre, tvApellido, tvGrupo;
        ImageView ivNombre, ivApellido, ivGrupo;
        ConstraintLayout parent_layout;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvApellido = itemView.findViewById(R.id.tvApellido);
            tvGrupo = itemView.findViewById(R.id.tvGrupo);

            ivNombre = itemView.findViewById(R.id.ivNombre);
            ivApellido = itemView.findViewById(R.id.ivApellido);
            ivGrupo = itemView.findViewById(R.id.ivGrupo);

            parent_layout = itemView.findViewById(R.id.parent_layout);
            parent_layout.setBackgroundResource(R.drawable.edit_text_white);
            itemView.setOnCreateContextMenuListener(this);


        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.setHeaderTitle("Opciones");
            contextMenu.add(0, 123, 0, "Eliminar");

        }


    }
}
