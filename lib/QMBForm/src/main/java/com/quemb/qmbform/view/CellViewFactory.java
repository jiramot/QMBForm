package com.quemb.qmbform.view;

import com.quemb.qmbform.descriptor.FormItemDescriptor;
import com.quemb.qmbform.descriptor.RowDescriptor;
import com.quemb.qmbform.descriptor.SectionDescriptor;

import android.content.Context;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

/**
 * Created by tonimoeckel on 14.07.14.
 */
public class CellViewFactory {

    private static CellViewFactory instance = null;
    private HashMap<String, Class <? extends FormBaseCell>> mViewRowTypeMap;

    public static CellViewFactory getInstance() {
        if (instance == null) {
            instance = new CellViewFactory();
        }
        return instance;
    }

    public CellViewFactory(){

        mViewRowTypeMap = new HashMap<String, Class<? extends FormBaseCell>>();
        mViewRowTypeMap.put(RowDescriptor.FormRowDescriptorTypeName, FormDetailTextFieldCell.class);
        mViewRowTypeMap.put(RowDescriptor.FormRowDescriptorTypeText, FormEditTextFieldCell.class);
        mViewRowTypeMap.put(RowDescriptor.FormRowDescriptorTypeTextView, FormEditTextViewFieldCell.class);
        mViewRowTypeMap.put(RowDescriptor.FormRowDescriptorTypeBooleanSwitch, FormBooleanFieldCell.class);
        mViewRowTypeMap.put(RowDescriptor.FormRowDescriptorTypeBooleanCheck, FormCheckFieldCell.class);
        mViewRowTypeMap.put(RowDescriptor.FormRowDescriptorTypeButton, FormButtonFieldCell.class);
        mViewRowTypeMap.put(RowDescriptor.FormRowDescriptorTypeDate, FormDateDialogFieldCell.class);
        mViewRowTypeMap.put(RowDescriptor.FormRowDescriptorTypeDateInline, FormDateInlineFieldCell.class);
        mViewRowTypeMap.put(RowDescriptor.FormRowDescriptorTypeTime, FormTimeDialogFieldCell.class);
        mViewRowTypeMap.put(RowDescriptor.FormRowDescriptorTypeTimeInline, FormTimeInlineFieldCell.class);
        mViewRowTypeMap.put(RowDescriptor.FormRowDescriptorTypeNumber, FormEditNumberFieldCell.class);
        mViewRowTypeMap.put(RowDescriptor.FormRowDescriptorTypeInteger, FormEditIntegerFieldCell.class);
        mViewRowTypeMap.put(RowDescriptor.FormRowDescriptorTypePhone, FormEditPhoneFieldCell.class);
        mViewRowTypeMap.put(RowDescriptor.FormRowDescriptorTypeURL, FormEditURLFieldCell.class);
        mViewRowTypeMap.put(RowDescriptor.FormRowDescriptorTypePassword, FormEditPasswordFieldCell.class);
        mViewRowTypeMap.put(RowDescriptor.FormRowDescriptorTypeEmail, FormEditEmailFieldCell.class);
        mViewRowTypeMap.put(RowDescriptor.FormRowDescriptorTypeSelectorSpinner, FormSpinnerFieldCell.class);
        mViewRowTypeMap.put(RowDescriptor.FormRowDescriptorTypeSelectorPickerDialog, FormPickerDialogFieldCell.class);

    }

    public Cell createViewForFormItemDescriptor(Context context, FormItemDescriptor descriptor){

        Cell rowView = null;

        if (descriptor instanceof SectionDescriptor){

            SectionCell sectionCell = new SectionCell(context, (SectionDescriptor) descriptor);
            rowView = sectionCell;

        } else if (descriptor instanceof RowDescriptor){
            RowDescriptor row = (RowDescriptor) descriptor;
            try {
                FormBaseCell formBaseCell;

                formBaseCell = mViewRowTypeMap.get(row.getRowType()).getConstructor(Context.class, RowDescriptor.class).newInstance(
                        context, row);
                rowView = formBaseCell;
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }

        }

        return rowView;

    }

}