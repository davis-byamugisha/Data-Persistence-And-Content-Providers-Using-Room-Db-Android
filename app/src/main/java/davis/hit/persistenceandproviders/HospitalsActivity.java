/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package davis.hit.persistenceandproviders;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import davis.hit.persistenceandproviders.hospital_data.Hospitals;
import davis.hit.persistenceandproviders.provider.SampleContentProvider;


/**
 * Not very relevant to Room. This just shows hospital_data from {@link SampleContentProvider}.
 *
 * <p>Since the hospital_data is exposed through the ContentProvider, other apps can read and write the
 * content in a similar manner to this.</p>
 */
public class HospitalsActivity extends AppCompatActivity {

    private static final int LOADER_HOSPITALS = 1;

    private HospitalAdapter mHospitalAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospitals);

        final RecyclerView list = findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(list.getContext()));
        mHospitalAdapter = new HospitalAdapter();
        list.setAdapter(mHospitalAdapter);

        getSupportLoaderManager().initLoader(LOADER_HOSPITALS, null, mLoaderCallbacks);
    }

    private LoaderManager.LoaderCallbacks<Cursor> mLoaderCallbacks =
            new LoaderManager.LoaderCallbacks<Cursor>() {

                @Override
                public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                    switch (id) {
                        case LOADER_HOSPITALS:
                            return new CursorLoader(getApplicationContext(),
                                    SampleContentProvider.URI_HOSPITALS,
                                    new String[]{Hospitals.COLUMN_NAME},
                                    null, null, null);
                        default:
                            throw new IllegalArgumentException();
                    }
                }

                @Override
                public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
                    switch (loader.getId()) {
                        case LOADER_HOSPITALS:
                            mHospitalAdapter.setHospitals(data);
                            break;
                    }
                }

                @Override
                public void onLoaderReset(Loader<Cursor> loader) {
                    switch (loader.getId()) {
                        case LOADER_HOSPITALS:
                            mHospitalAdapter.setHospitals(null);
                            break;
                    }
                }

            };

    private static class HospitalAdapter extends RecyclerView.Adapter<HospitalAdapter.ViewHolder> {

        private Cursor mCursor;

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if (mCursor.moveToPosition(position)) {
                holder.mText.setText(mCursor.getString(
                        mCursor.getColumnIndexOrThrow(Hospitals.COLUMN_NAME)));
            }
        }

        @Override
        public int getItemCount() {
            return mCursor == null ? 0 : mCursor.getCount();
        }

        void setHospitals(Cursor cursor) {
            mCursor = cursor;
            notifyDataSetChanged();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {

            TextView mText;

            ViewHolder(ViewGroup parent) {
                super(LayoutInflater.from(parent.getContext()).inflate(
                        android.R.layout.simple_list_item_1, parent, false));
                mText = itemView.findViewById(android.R.id.text1);
            }

        }

    }

}
