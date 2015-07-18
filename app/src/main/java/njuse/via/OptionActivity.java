package njuse.via;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Lf on 2015/7/17.
 */
public class OptionActivity extends Activity {

    int button_show_number=2;
    int button_max_number=4;
    int true_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_option);
        switch(button_show_number){
            case 0:findViewById(R.id.option1_layout).setVisibility(View.GONE);
            case 1:findViewById(R.id.option2_layout).setVisibility(View.GONE);
            case 2:findViewById(R.id.option3_layout).setVisibility(View.GONE);
            case 3:findViewById(R.id.option4_layout).setVisibility(View.GONE);
        }
        if(button_show_number==button_max_number){
            findViewById(R.id.btn_option_add).setVisibility(View.GONE);
        }

        ((Button) findViewById(R.id.btn_option1)).setOnClickListener(new OptionButtonListener());
        ((Button) findViewById(R.id.btn_option1)).setOnLongClickListener(new OptionButtonListener());
        ((Button) findViewById(R.id.btn_option2)).setOnClickListener(new OptionButtonListener());
        ((Button) findViewById(R.id.btn_option2)).setOnLongClickListener(new OptionButtonListener());
        ((Button) findViewById(R.id.btn_option3)).setOnClickListener(new OptionButtonListener());
        ((Button) findViewById(R.id.btn_option3)).setOnLongClickListener(new OptionButtonListener());
        ((Button) findViewById(R.id.btn_option4)).setOnClickListener(new OptionButtonListener());
        ((Button) findViewById(R.id.btn_option4)).setOnLongClickListener(new OptionButtonListener());

//        ((EditText) findViewById(R.id.edt_option1)).setOnFocusChangeListener(new setOnFocusChangeListener());
//        ((EditText) findViewById(R.id.edt_option2)).setOnFocusChangeListener(new setOnFocusChangeListener());
//        ((EditText) findViewById(R.id.edt_option3)).setOnFocusChangeListener(new setOnFocusChangeListener());
//        ((EditText) findViewById(R.id.edt_option4)).setOnFocusChangeListener(new setOnFocusChangeListener());

        true_id=R.id.btn_option1;
        Button but=(Button)findViewById(true_id);
        but.setBackgroundResource(R.drawable.btn_choose_selector);
    }

    /**
     * ������ť�ĵ������
     * @param view
     */
    public void cancelOptionBtnListener(View view) {
        this.finish();
    }

    public void delOptionListener(View view){

        setEditTextGone();

        int note=view.getId();
        Button but1=(Button) findViewById(R.id.btn_option1);
        Button but2=(Button) findViewById(R.id.btn_option2);
        Button but3=(Button) findViewById(R.id.btn_option3);
        Button but4=(Button) findViewById(R.id.btn_option4);
        switch(note){
            case R.id.btn_option1_del1:
                but1.setText(but2.getText());
                if(true_id==but2.getId()){
                    but1.setBackgroundResource(R.drawable.btn_choose_selector);
                    but2.setBackgroundResource(R.drawable.btn_selector);
                    true_id=but1.getId();
                }else if(true_id==but1.getId()){
                    but1.setBackgroundResource(R.drawable.btn_selector);
                    true_id=0;
                }
            case R.id.btn_option1_del2:
                but2.setText(but3.getText());
                if(true_id==but3.getId()){
                    but2.setBackgroundResource(R.drawable.btn_choose_selector);
                    but3.setBackgroundResource(R.drawable.btn_selector);
                    true_id=but2.getId();
                }else if(true_id==but2.getId()){
                    but2.setBackgroundResource(R.drawable.btn_selector);
                    true_id=0;
                }
            case R.id.btn_option1_del3:
                but3.setText(but4.getText());
                if(true_id==but4.getId()){
                    but3.setBackgroundResource(R.drawable.btn_choose_selector);
                    but4.setBackgroundResource(R.drawable.btn_selector);
                    true_id=but3.getId();
                }else if(true_id==but3.getId()){
                    but3.setBackgroundResource(R.drawable.btn_selector);
                    true_id=0;
                }
        }
        switch(button_show_number){
            case 1:findViewById(R.id.option1_layout).setVisibility(View.GONE);break;
            case 2:findViewById(R.id.option2_layout).setVisibility(View.GONE);break;
            case 3:findViewById(R.id.option3_layout).setVisibility(View.GONE);break;
            case 4:findViewById(R.id.option4_layout).setVisibility(View.GONE);break;
        }
        if(button_show_number==button_max_number){
            findViewById(R.id.btn_option_add).setVisibility(View.VISIBLE);
        }

        button_show_number--;
    }

    public void ensureInputListener(View view){
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(OptionActivity.this.getCurrentFocus().getWindowToken()
                , InputMethodManager.HIDE_NOT_ALWAYS);
        view.setVisibility(View.GONE);
        switch(view.getId()){
            case R.id.edt_option1_ent1:
                EditText edt1=(EditText) findViewById(R.id.edt_option1);
                edt1.setVisibility(View.GONE);
                Button but1=(Button) findViewById(R.id.btn_option1);
                but1.setText(edt1.getText());
                but1.setVisibility(View.VISIBLE);
                ImageView img1=(ImageView) findViewById(R.id.btn_option1_del1);
                img1.setVisibility(View.VISIBLE);
                break;
            case R.id.edt_option1_ent2:
                EditText edt2=(EditText) findViewById(R.id.edt_option2);
                edt2.setVisibility(View.GONE);
                Button but2=(Button) findViewById(R.id.btn_option2);
                but2.setText(edt2.getText());
                but2.setVisibility(View.VISIBLE);
                ImageView img2=(ImageView) findViewById(R.id.btn_option1_del2);
                img2.setVisibility(View.VISIBLE);
                break;
            case R.id.edt_option1_ent3:
                EditText edt3=(EditText) findViewById(R.id.edt_option3);
                edt3.setVisibility(View.GONE);
                Button but3=(Button) findViewById(R.id.btn_option3);
                but3.setText(edt3.getText());
                but3.setVisibility(View.VISIBLE);
                ImageView img3=(ImageView) findViewById(R.id.btn_option1_del3);
                img3.setVisibility(View.VISIBLE);
                break;
            case R.id.edt_option1_ent4:
                EditText edt4=(EditText) findViewById(R.id.edt_option4);
                edt4.setVisibility(View.GONE);
                Button but4=(Button) findViewById(R.id.btn_option4);
                but4.setText(edt4.getText());
                but4.setVisibility(View.VISIBLE);
                ImageView img4=(ImageView) findViewById(R.id.btn_option1_del4);
                img4.setVisibility(View.VISIBLE);
                break;
        }
    }

    public void addOptionListener(View view){

        setEditTextGone();

        button_show_number++;
        switch(button_show_number){
            case 1:
                Button but1=(Button)findViewById(R.id.btn_option1);
                but1.setText(R.string.option1);
                but1.setBackgroundResource(R.drawable.btn_selector);
                findViewById(R.id.option1_layout).setVisibility(View.VISIBLE);
                break;
            case 2:
                Button but2=(Button)findViewById(R.id.btn_option2);
                but2.setText(R.string.option2);
                but2.setBackgroundResource(R.drawable.btn_selector);
                findViewById(R.id.option2_layout).setVisibility(View.VISIBLE);
                break;
            case 3:
                Button but3=(Button)findViewById(R.id.btn_option3);
                but3.setText(R.string.option3);
                but3.setBackgroundResource(R.drawable.btn_selector);
                findViewById(R.id.option3_layout).setVisibility(View.VISIBLE);
                break;
            case 4:
                Button but4=(Button)findViewById(R.id.btn_option4);
                but4.setText(R.string.option4);
                but4.setBackgroundResource(R.drawable.btn_selector);
                findViewById(R.id.option4_layout).setVisibility(View.VISIBLE);
                break;
        }
        if(button_show_number==button_max_number){
            findViewById(R.id.btn_option_add).setVisibility(View.GONE);
        }
    }

    private class OptionButtonListener implements View.OnClickListener, View.OnLongClickListener {

        public void onClick(View view){
            view.setVisibility(View.GONE);
            Button but=(Button) view;
            // 接受软键盘输入的编辑文本或其它视图
            switch (view.getId()){
                case R.id.btn_option1:
                    EditText edt1=(EditText) findViewById(R.id.edt_option1);
                    edt1.setText(but.getText());
                    edt1.setVisibility(View.VISIBLE);
                    ImageView del1=(ImageView) findViewById(R.id.btn_option1_del1);
                    del1.setVisibility(View.GONE);
                    ImageView ent1=(ImageView) findViewById(R.id.edt_option1_ent1);
                    ent1.setVisibility(View.VISIBLE);
                    edt1.requestFocus();
                    edt1.findFocus();
                    edt1.setSelection(edt1.getText().length());
                    break;
                case R.id.btn_option2:
                    EditText edt2=(EditText)findViewById(R.id.edt_option2);
                    edt2.setText(but.getText());
                    edt2.setVisibility(View.VISIBLE);
                    ImageView del2=(ImageView) findViewById(R.id.btn_option1_del2);
                    del2.setVisibility(View.GONE);
                    ImageView ent2=(ImageView) findViewById(R.id.edt_option1_ent2);
                    ent2.setVisibility(View.VISIBLE);
                    edt2.requestFocus();
                    edt2.findFocus();
                    edt2.setSelection(edt2.getText().length());
                    break;
                case R.id.btn_option3:
                    EditText edt3=(EditText)findViewById(R.id.edt_option3);
                    edt3.setText(but.getText());
                    edt3.setVisibility(View.VISIBLE);
                    ImageView del3=(ImageView) findViewById(R.id.btn_option1_del3);
                    del3.setVisibility(View.GONE);
                    ImageView ent3=(ImageView) findViewById(R.id.edt_option1_ent3);
                    ent3.setVisibility(View.VISIBLE);
                    edt3.requestFocus();
                    edt3.findFocus();
                    edt3.setSelection(edt3.getText().length());
                    break;
                case R.id.btn_option4:
                    EditText edt4=(EditText)findViewById(R.id.edt_option4);
                    edt4.setText(but.getText());
                    edt4.setVisibility(View.VISIBLE);
                    ImageView del4=(ImageView) findViewById(R.id.btn_option1_del4);
                    del4.setVisibility(View.GONE);
                    ImageView ent4=(ImageView) findViewById(R.id.edt_option1_ent4);
                    ent4.setVisibility(View.VISIBLE);
                    edt4.requestFocus();
                    edt4.findFocus();
                    edt4.setSelection(edt4.getText().length());
                    break;
            }
        }

        @Override
        public boolean onLongClick(View view) {
            if(true_id!=0){
                Button but1=(Button) findViewById(true_id);but1.setBackgroundResource(R.drawable.btn_selector);
            }
            Button but2=(Button) view;
            but2.setBackgroundResource(R.drawable.btn_choose_selector);
            true_id=view.getId();
            return true;
        }
    }

    public void setEditTextGone(){

        if(OptionActivity.this.getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(OptionActivity.this.getCurrentFocus().getWindowToken()
                    , InputMethodManager.HIDE_NOT_ALWAYS);
        }

        EditText edt1=(EditText) findViewById(R.id.edt_option1);
        EditText edt2=(EditText) findViewById(R.id.edt_option2);
        EditText edt3=(EditText) findViewById(R.id.edt_option3);
        EditText edt4=(EditText) findViewById(R.id.edt_option4);

        ImageView ent;
        Button but;
        ImageView del;

        if(edt1.getVisibility()==View.VISIBLE){
            edt1.setVisibility(View.GONE);
            ent=(ImageView) findViewById(R.id.edt_option1_ent1);
            ent.setVisibility(View.GONE);
            but=(Button) findViewById(R.id.btn_option1);
            but.setVisibility(View.VISIBLE);
            but.setText(edt1.getText());
            del=(ImageView) findViewById(R.id.btn_option1_del1);
            del.setVisibility(View.VISIBLE);
        }
        if(edt2.getVisibility()==View.VISIBLE){
            edt2.setVisibility(View.GONE);
            ent=(ImageView) findViewById(R.id.edt_option1_ent2);
            ent.setVisibility(View.GONE);
            but=(Button) findViewById(R.id.btn_option2);
            but.setVisibility(View.VISIBLE);
            but.setText(edt2.getText());
            del=(ImageView) findViewById(R.id.btn_option1_del2);
            del.setVisibility(View.VISIBLE);
        }
        if(edt3.getVisibility()==View.VISIBLE){
            edt3.setVisibility(View.GONE);
            ent=(ImageView) findViewById(R.id.edt_option1_ent3);
            ent.setVisibility(View.GONE);
            but=(Button) findViewById(R.id.btn_option3);
            but.setVisibility(View.VISIBLE);
            but.setText(edt3.getText());
            del=(ImageView) findViewById(R.id.btn_option1_del3);
            del.setVisibility(View.VISIBLE);
        }
        if(edt4.getVisibility()==View.VISIBLE){
            edt4.setVisibility(View.GONE);
            ent=(ImageView) findViewById(R.id.edt_option1_ent4);
            ent.setVisibility(View.GONE);
            but=(Button) findViewById(R.id.btn_option4);
            but.setVisibility(View.VISIBLE);
            but.setText(edt4.getText());
            del=(ImageView) findViewById(R.id.btn_option1_del4);
            del.setVisibility(View.VISIBLE);
        }

    }

    private class setOnFocusChangeListener implements View.OnFocusChangeListener {

        @Override
        public void onFocusChange(View v, boolean hasFocus) {

            if (v.getId() == R.id.edt_option1 || v.getId() == R.id.edt_option2 || v.getId() == R.id.edt_option3 || v.getId() == R.id.edt_option4) {

                if(hasFocus){
                    InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                }else{
                    InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(OptionActivity.this.getCurrentFocus().getWindowToken()
                            , InputMethodManager.HIDE_NOT_ALWAYS);
                }

            }

        }
    }

}
