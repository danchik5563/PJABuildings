package com.example.danilwelter.pjabuildings;

import android.content.Context;
import android.content.Intent;
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

import com.example.danilwelter.pjabuildings.Model.Museum;


import java.util.List;

public class MuseumAdapter extends RecyclerView.Adapter<MuseumAdapter.ViewHolder> {

    FloatingActionButton fabAddItem;
    LinearLayout linearLayoutMuseum;
    LinearLayout linearLayoutDwellingHouse;
    LinearLayout linearLayoutListBuildings;
    EditText tbMuseumAddress;
    EditText tbMuseumFloorsCount;
    EditText tbMuseumStartTime;
    EditText tbMuseumEndTime;

    public void SettersViews(FloatingActionButton fab, LinearLayout llm,
                             LinearLayout lldw, LinearLayout lllb,
                             EditText tbma, EditText tbmfc,
                             EditText tbmst, EditText tbmet){
        fabAddItem = fab;
        linearLayoutMuseum = llm;
        linearLayoutDwellingHouse = lldw;
        linearLayoutListBuildings = lllb;
        tbMuseumAddress = tbma;
        tbMuseumFloorsCount = tbmfc;
        tbMuseumStartTime = tbmst;
        tbMuseumEndTime = tbmet;
    }



    private List<Museum> listItems;
    private Context mContext;

    public MuseumAdapter(List<Museum> listItems, Context mContext) {
        this.listItems = listItems;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final Museum museum = listItems.get(position);
        holder.txtTitle.setText(museum.get_address());

        holder.txtDescription.setText(museum.GetInfo());

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
                                linearLayoutDwellingHouse.setVisibility(View.INVISIBLE);
                                linearLayoutMuseum.setVisibility(View.VISIBLE);
                                linearLayoutListBuildings.setVisibility(View.INVISIBLE);
                                fabAddItem.setVisibility(View.VISIBLE);

                                Singleton.getInstance().setEditableMuseumObject(museum);

                                tbMuseumAddress.setText(museum.get_address());
                                tbMuseumFloorsCount.setText(Integer.toString(museum.get_floorsCount()));
                                tbMuseumStartTime.setText(museum.get_startTime());
                                tbMuseumEndTime.setText(museum.get_endTime());
                                ///---///

                                break;
                            case R.id.menu_item_delete:
                                //Delete item
                                listItems.remove(position);
                                notifyDataSetChanged();
                                Toast.makeText(mContext, "Deleted", Toast.LENGTH_LONG).show();
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
