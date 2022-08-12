package com.nicanoritorma.qrattendance;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nicanoritorma.qrattendance.OfflineViewModels.QrViewModel;
import com.nicanoritorma.qrattendance.OnlineViewModels.GeneratedQrViewModel;
import com.nicanoritorma.qrattendance.model.QrModel;
import com.nicanoritorma.qrattendance.ui.adapter.QrAdapter;
import com.nicanoritorma.qrattendance.utils.EncryptorAndDecryptor;
import com.nicanoritorma.qrattendance.utils.RecyclerViewItemClickSupport;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nicanor Itorma
 */

public class GeneratedQr extends BaseActivity {

    private RecyclerView rv_generatedQr;
    private TextView tv_empty;
    private QrAdapter qrAdapter;
    private GeneratedQrViewModel qrViewModel;
    private final List<QrModel> selectedItem = new ArrayList<>();
    private ActionMode actionMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generated_qr);

        rv_generatedQr = findViewById(R.id.rv_generatedQr);
        tv_empty = findViewById(R.id.tv_emptyQr);
        initUI();
    }

    private void initUI() {
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle("Generated QR Codes");
        }

        rv_generatedQr.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rv_generatedQr.setHasFixedSize(true);
        qrAdapter = new QrAdapter();
        rv_generatedQr.setAdapter(qrAdapter);

        //offline db
        QrViewModel qrViewModel = new QrViewModel(getApplication());
        qrViewModel.getAllQr().observe(this, qrModels -> {
            if (qrModels.size() == 0)
            {
                tv_empty.setVisibility(View.VISIBLE);
            }

            qrAdapter.setList(qrModels);

            RecyclerViewItemClickSupport.addTo(rv_generatedQr).setOnItemClickListener((recyclerView, position, v) -> {
                if (actionMode == null) {
                    return;
                }
                //if in CAB mode
                toggleListViewItem(v, position);
                setCABTitle();
            }).setOnItemLongClickListener((recyclerView, position, v) -> {
                if (actionMode != null) {
                    return false;
                }
                // Start the CAB using the ActionMode.Callback defined below
                GeneratedQr.this.startActionMode(new ModeCallback());
                toggleListViewItem(v, position);
                setCABTitle();
                return true;
            });
        });
    }

    public void toggleListViewItem(View view, int position) {
        QrModel qrItem = qrAdapter.getItem(position);
        RelativeLayout item = view.findViewById(R.id.item_relative_layout);

        if (!getSelectedItem().contains(qrItem)) {
            getSelectedItem().add(qrItem);
            qrAdapter.addSelectedItem(position);
            item.setBackgroundResource(R.drawable.selected_item_border);
        } else {
            getSelectedItem().remove(qrItem);
            qrAdapter.removeSelectedItem(position);
            qrAdapter.restoreDrawable(item);
        }
        prepareActionModeMenu();

        if (selectedItem.isEmpty()) {
            finishActionMode();
        }

        setCABTitle();
    }

    private void prepareActionModeMenu() {
        Menu menu = actionMode.getMenu();
        if (qrAdapter.getItemCount() > 1) {
            menu.findItem(R.id.menuActionSelectAll).setVisible(true);

        }
        menu.findItem(R.id.menu_saveToDevice).setVisible(true);
        menu.findItem(R.id.menuActionDelete).setVisible(true);
    }

    private void setCABTitle() {
        if (actionMode != null) {
            int title = selectedItem.size();
            actionMode.setTitle(String.valueOf(title));
        }
    }

    private List<QrModel> getSelectedItem() {
        return selectedItem;
    }

    public void finishActionMode() {
        if (actionMode != null) {
            actionMode.finish();
        }
    }

    private void selectAllAttendance() {
        for (int i = 0; i < rv_generatedQr.getChildCount(); i++) {
            RelativeLayout item = rv_generatedQr.getChildAt(i).findViewById(R.id.item_relative_layout);
            item.setBackgroundResource(R.drawable.selected_item_border);
        }
        selectedItem.clear();
        for (int i = 0; i < qrAdapter.getItemCount(); i++) {
            selectedItem.add(qrAdapter.getItem(i));
            qrAdapter.addSelectedItem(i);
        }
        prepareActionModeMenu();
        setCABTitle();
    }

    private void deleteItem(QrModel qrItem) {
        QrViewModel qrViewModel = new QrViewModel(getApplication());
        qrViewModel.delete(qrItem);
    }

    private final class ModeCallback implements ActionMode.Callback {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            actionMode = mode;
            inflater.inflate(R.menu.app_menu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            prepareActionModeMenu();
            return true;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

            switch (item.getItemId()) {
                case R.id.menuActionSelectAll:
                    selectAllAttendance();
                    return true;
                case R.id.menuActionDelete:
                    for (int i = 0; i < selectedItem.size(); i++) {
                        QrModel qrItem = selectedItem.get(i);
                        deleteItem(qrItem);
                    }
                    selectedItem.clear();
                    finishActionMode();
                    return true;
                case R.id.menu_saveToDevice:
                    for (int i = 0; i < selectedItem.size(); ++i) {
                        String idNum = selectedItem.get(i).getIdNum();
                        String qrCode = selectedItem.get(i).getQrCode();
                        String decryptedData = EncryptorAndDecryptor.decrypt(qrCode);

                        saveQrToLocal(idNum, decryptedData);
                    }
                    finishActionMode();
                    return true;
            }
            mode.finish();
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            selectedItem.clear();
            qrAdapter.clearSelectedItems();
            qrAdapter.notifyDataSetChanged();
            actionMode = null;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    /**
     * function to save qr code to local storage with id number as filename
     * @param idNum - id number of the student
     * @param qrCode - the generated qr code
     */
    private void saveQrToLocal(String idNum, String qrCode) {
        byte[] data = Base64.decode(qrCode, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);

        OutputStream fos;
        try {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                ContentResolver resolver = getApplicationContext().getContentResolver();
//                ContentValues contentValues = new ContentValues();
//                contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, idNum);
//                contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/png");
//                contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, "Downloads/" + "QR_Attendance/QR_Codes");
//                Uri imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
//                fos = resolver.openOutputStream(imageUri);
//            } else {
                String imagesDir = Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES).toString() + File.separator + "QR_Attendance";

                File file = new File(imagesDir);

                if (!file.exists()) {
                    file.mkdirs();
                }

                File image = new File(imagesDir, idNum + ".png");
                fos = new FileOutputStream(image);
//            }
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            Toast.makeText(getApplicationContext(), "QR successfully saved", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error saving QR: " + e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}