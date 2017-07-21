package com.example.android.inventorius;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.inventorius.data.ItemsContract;

/**
 * {@link ItemCursorAdapter} is an adapter for a list or grid view
 * that uses a {@link Cursor} of item data as its data source. This adapter knows
 * how to create list items for each row of item data in the {@link Cursor}.
 */
public class ItemCursorAdapter extends CursorAdapter {

    /**
     * Constructs a new {@link ItemCursorAdapter}.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     */
    public ItemCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    /**
     * Makes a new blank list item view. No data is set (or bound) to the views yet.
     *
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * @param parent  The parent to which the new view is attached to
     * @return the newly created list item view.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    /**
     * This method binds the item data (in the current row pointed to by cursor) to the given
     * list item layout. For example, the name for the current item can be set on the name TextView
     * in the list item layout.
     *
     * @param view    Existing view, returned earlier by newView() method
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already moved to the
     *                correct row.
     */
    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        // Find the individual views that we want to modify in the list item layout
        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        final TextView quantityTextView = (TextView) view.findViewById(R.id.quantity);
        TextView priceTextView = (TextView) view.findViewById(R.id.price);

        // Find the columns of item attributes that we're interested in
        int idColumnIndex = cursor.getColumnIndex(ItemsContract.ItemEntry._ID);
        int nameColumnIndex = cursor.getColumnIndex(ItemsContract.ItemEntry.COLUMN_ITEM_NAME);
        int quantityColumnIndex = cursor.getColumnIndex(ItemsContract.ItemEntry.COLUMN_ITEM_QUANTITY);
        int priceColumnIndex = cursor.getColumnIndex(ItemsContract.ItemEntry.COLUMN_ITEM_PRICE);

        // Read the item attributes from the Cursor for the current item
        final int itemId = cursor.getInt(idColumnIndex);
        String itemName = cursor.getString(nameColumnIndex);
        String itemQuantity = cursor.getString(quantityColumnIndex);
        String itemPrice = cursor.getString(priceColumnIndex);

        // Update the TextViews with the attributes for the current item
        nameTextView.setText(itemName);
        quantityTextView.setText(itemQuantity);
        priceTextView.setText(itemPrice);

        // Hide sale button if quantity is zero
        ImageButton saleButton = (ImageButton) view.findViewById(R.id.item_sale);

        // Decrease quantity of the current item when the sale button is clicked
        saleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the current quantity for the item
                int currentQuantity = Integer.parseInt(quantityTextView.getText().toString());
                // Decrease the quantity by one
                if (currentQuantity >0) {

                    currentQuantity--;
                } else {
                    Toast.makeText(context.getApplicationContext(),
                            R.string.quantity_greater_than_zero,Toast.LENGTH_SHORT).show();
                }
                // Display the changed quantity value
                quantityTextView.setText(Integer.toString(currentQuantity));
                // Store the change in the database
                ContentValues values = new ContentValues();
                values.put(ItemsContract.ItemEntry.COLUMN_ITEM_QUANTITY, currentQuantity);
                Uri currentItemUri =
                        ContentUris.withAppendedId(ItemsContract.ItemEntry.CONTENT_URI, itemId);
                // Check if database was successfully updated
                int rowsAffected = context.getContentResolver().update(currentItemUri, values, null, null);
                // Show a toast message depending on whether or not the update was successful.
                if (rowsAffected == 0) {
                    Toast.makeText(context.getApplicationContext(), "Error with quantity update.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
