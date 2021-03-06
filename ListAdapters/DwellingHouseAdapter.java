package com.example.danilwelter.pjabuildings.ListAdapters;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.danilwelter.pjabuildings.Model.DwellingHouse;
import com.example.danilwelter.pjabuildings.R;
import com.example.danilwelter.pjabuildings.Singleton;

import java.util.List;

public class DwellingHouseAdapter extends RecyclerView.Adapter<DwellingHouseAdapter.ViewHolder> {

    FloatingActionButton fabAddItem;
    LinearLayout linearLayoutMuseum;
    LinearLayout linearLayoutDwellingHouse;
    LinearLayout linearLayoutListBuildings;
    EditText tbDwellingHouseAddress;
    EditText tbDwellingHouseFloorsCount;
    EditText tbDwellingHouseApartmentsCount;

    public void SettersViews(FloatingActionButton fab, LinearLayout llm,
                             LinearLayout lldw, LinearLayout lllb,
                             EditText tbdha, EditText tbdhfc,
                             EditText tbdhac){
        fabAddItem = fab;
        linearLayoutMuseum = llm;
        linearLayoutDwellingHouse = lldw;
        linearLayoutListBuildings = lllb;
        tbDwellingHouseAddress = tbdha;
        tbDwellingHouseFloorsCount = tbdhfc;
        tbDwellingHouseApartmentsCount = tbdhac;

    }



    private List<DwellingHouse> listItems;
    private Context mContext;
    private SQLiteDatabase db;

    public DwellingHouseAdapter(List<DwellingHouse> listItems, Context mContext, SQLiteDatabase database) {
        this.listItems = listItems;
        this.mContext = mContext;
        this.db = database;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final DwellingHouse dwellingHouse = listItems.get(position);
        holder.txtTitle.setText(dwellingHouse.get_address());
        holder.txtDescription.setText(dwellingHouse.GetInfo());

        //region Display menu
        holder.txtOptionDigit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Display option menu

                PopupMenu popupMenu = new PopupMenu(mContext, holder.txtOptionDigit);
                popupMenu.inflate(R.menu.option_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.menu_item_edit:

                                ///---///
                                linearLayoutDwellingHouse.setVisibility(View.VISIBLE);
                                linearLayoutMuseum.setVisibility(View.INVISIBLE);
                                linearLayoutListBuildings.setVisibility(View.INVISIBLE);
                                fabAddItem.setVisibility(View.VISIBLE);

                                Singleton.getInstance().setEditableDwellingHouseObject(dwellingHouse);

                                tbDwellingHouseAddress.setText(dwellingHouse.get_address());
                                tbDwellingHouseFloorsCount.setText(Integer.toString(dwellingHouse.get_floorsCount()));
                                tbDwellingHouseApartmentsCount.setText(Integer.toString(dwellingHouse.get_apartmentsCount()));
                                ///---///

                                break;
                            case R.id.menu_item_delete:
                                //Delete itemse
                                listItems.remove(position);
                                db.delete("dwellingHouses", "_id = " + dwellingHouse.get_id(), null);
                                notifyDataSetChanged();
                                Toast.makeText(mContext, "Deleted", Toast.LENGTH_LONG).show();
                                break;
                            case R.id.menu_item_share:
                                String sendingText = "Жилой дом.\nАдрес: " + dwellingHouse.get_address() + "\nКол-во этажей: " + dwellingHouse.get_floorsCount() + "\nКол-во квартир: " + dwellingHouse.get_apartmentsCount();
                                Intent sendIntent = new Intent();
                                sendIntent.setAction(Intent.ACTION_SEND);
                                sendIntent.putExtra(Intent.EXTRA_TEXT, sendingText);
                                sendIntent.setType("text/plain");
                                mContext.startActivity(sendIntent);
                                break;
                            default:
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });//endregion


    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView txtTitle;
        public TextView txtDescription;
        public TextView txtOptionDigit;
        public ViewHolder(View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            txtDescription = (TextView) itemView.findViewById(R.id.txtDescription);
            txtOptionDigit = (TextView) itemView.findViewById(R.id.txtOptionDigit);
        }
    }
}
