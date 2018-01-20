package pl.atk.oeskandroidbenchmark.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import pl.atk.oeskandroidbenchmark.R;

/**
 * Created by Tomasz on 13.12.2017.
 */

public class DeviceInfoCellView extends FrameLayout {

    private TextView attributeName;
    private TextView attributeValue;

    public DeviceInfoCellView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DeviceInfoCellView, 0, 0);
        String attributeNameText = typedArray.getString(R.styleable.DeviceInfoCellView_attributeName);
        String attributeValueText = typedArray.getString(R.styleable.DeviceInfoCellView_attributeValue);
        typedArray.recycle();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_device_info_cell, this, true);

        attributeName = (TextView) view.findViewById(R.id.device_cell_name);
        attributeName.setText(attributeNameText);
        attributeValue = (TextView) view.findViewById(R.id.device_cell_value);
        attributeValue.setText(attributeValueText);
    }

    public void setAttributeName(String name) {
        attributeName.setText(name);
    }

    public void setAttributeValue(String value) {
        attributeValue.setText(value);
    }

    public String getAttributeValue(){
        return attributeValue.getText().toString();
    }
}
