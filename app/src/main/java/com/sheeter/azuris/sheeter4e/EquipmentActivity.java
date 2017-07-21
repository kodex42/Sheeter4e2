package com.sheeter.azuris.sheeter4e;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.sheeter.azuris.sheeter4e.Modules.Item;

import java.util.ArrayList;

/**
 * Created by Azuris on 2017-07-21.
 */

public class EquipmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ListView items = (ListView) findViewById(R.id.Equipment_Items);
        items.setAdapter(new itemsAdapter());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class itemsAdapter implements ListAdapter {
        ArrayList<Item> items;

        itemsAdapter() {
            super();
            this.items = MainActivity.sCharacter.sheet.getItems();
            sort();
        }

        private void sort() {
            ArrayList<Item> magic = new ArrayList<>();
            ArrayList<Item> weapons = new ArrayList<>();
            ArrayList<Item> armor = new ArrayList<>();
            ArrayList<Item> gear = new ArrayList<>();
            for (Item item : this.items) {
                if (item.isMagic())
                    magic.add(item);
                else
                    switch (item.getType()) {
                        case WEAPON:
                            weapons.add(item);
                            break;
                        case ARMOR:
                            armor.add(item);
                            break;
                        case GEAR:
                            gear.add(item);
                            break;
                        default:
                            gear.add(item);
                    }
            }
            this.items.clear();
            this.items.addAll(magic);
            this.items.addAll(weapons);
            this.items.addAll(armor);
            this.items.addAll(gear);
        }

        @Override
        public boolean areAllItemsEnabled() { return false; }

        @Override
        public boolean isEnabled(int i) { return false; }

        @Override
        public void registerDataSetObserver(DataSetObserver dataSetObserver) {}

        @Override
        public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {}

        @Override
        public int getCount() {
            return this.items.size();
        }

        @Override
        public Object getItem(int i) {
            return this.items.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public boolean hasStableIds() { return false; }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                LayoutInflater inflater = LayoutInflater.from(EquipmentActivity.this);
                view = inflater.inflate(R.layout.item_row, null);
            }

            ((TextView)view.findViewById(R.id.ItemName)).setText(this.items.get(i).getName());
            ((TextView)view.findViewById(R.id.ItemQuantity)).setText(String.valueOf(this.items.get(i).getQuantity()));

            if (this.items.get(i).isEquipped())
                view.findViewById(R.id.ItemIsEquipped).setVisibility(View.VISIBLE);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            return view;
        }

        @Override
        public int getItemViewType(int i) {
            return 0;
        }

        @Override
        public int getViewTypeCount() {
            return this.items.size();
        }

        @Override
        public boolean isEmpty() {
            return this.items.isEmpty();
        }
    }
}
