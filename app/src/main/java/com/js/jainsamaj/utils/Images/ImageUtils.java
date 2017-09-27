package com.js.jainsamaj.utils.Images;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

/**
 * Created by arbaz on 14/2/17.
 */

public class ImageUtils {

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * Function to get Path of Gallery Image
     */
    public static String getGalleryImagePath(Context context, Intent imageReturnedIntent) {
        String filePath = "";
        Uri selectedImage = imageReturnedIntent.getData();
        boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;


        if (Build.VERSION.SDK_INT < 19) {

            if ("content".equalsIgnoreCase(selectedImage.getScheme())) {
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = context.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                filePath = cursor.getString(columnIndex);
                cursor.close();
            } else if ("file".equalsIgnoreCase(selectedImage.getScheme())) {
                filePath = selectedImage.getPath();
            }
        } else if (isKitKat && DocumentsContract.isDocumentUri(context, selectedImage)) {
            final int takeFlags = imageReturnedIntent.getFlags()
                    & (Intent.FLAG_GRANT_READ_URI_PERMISSION
                    | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            context.getContentResolver().takePersistableUriPermission(selectedImage, (Intent.FLAG_GRANT_READ_URI_PERMISSION
                    | Intent.FLAG_GRANT_WRITE_URI_PERMISSION));

            if (isExternalStorageDocument(selectedImage)) {
                final String docId = DocumentsContract.getDocumentId(selectedImage);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    filePath = Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(selectedImage)) {

                final String id = DocumentsContract.getDocumentId(selectedImage);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                filePath = getDataColumn(context, contentUri, null, null);
            } else if (isMediaDocument(selectedImage)) {
                final String docId = DocumentsContract.getDocumentId(selectedImage);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };
                filePath = getDataColumn(context, contentUri, selection, selectionArgs);
            } else if ("content".equalsIgnoreCase(selectedImage.getScheme())) {
                filePath = getDataColumn(context, selectedImage, null, null);
            } else if ("file".equalsIgnoreCase(selectedImage.getScheme())) {
                filePath = selectedImage.getPath();
            }
        }

        return filePath;
    }

}